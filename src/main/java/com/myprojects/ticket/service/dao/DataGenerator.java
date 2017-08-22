package com.myprojects.ticket.service.dao;

import static com.myprojects.ticket.service.dao.config.TicketServiceConfig.getBronzeSeatRate;
import static com.myprojects.ticket.service.dao.config.TicketServiceConfig.getCols;
import static com.myprojects.ticket.service.dao.config.TicketServiceConfig.getGoldSeatRate;
import static com.myprojects.ticket.service.dao.config.TicketServiceConfig.getMaxGoldRows;
import static com.myprojects.ticket.service.dao.config.TicketServiceConfig.getMaxSilverRows;
import static com.myprojects.ticket.service.dao.config.TicketServiceConfig.getRows;
import static com.myprojects.ticket.service.dao.config.TicketServiceConfig.getSilverSeatRate;
import static com.myprojects.ticket.service.utils.TicketServiceUtils.calculateSeatRank;
import static com.myprojects.ticket.service.utils.TicketServiceUtils.identifySeatType;

import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.myprojects.ticket.service.beans.Seat;
import com.myprojects.ticket.service.beans.SeatHold;
import com.myprojects.ticket.service.beans.comparators.SeatRankComparator;
import com.myprojects.ticket.service.enums.SeatType;
import com.myprojects.ticket.service.enums.TicketServiceErrorType;
import com.myprojects.ticket.service.exception.TicketServiceException;

/**
 * This class is just acting in-memory database
 */
@Component
public class DataGenerator {
	private static final Logger LOGGER = Logger.getLogger(TicketServiceDAOImpl.class);
	private Map<String, Seat> availSeatsMap;
	private SortedSet<Seat> availSeatsSet;
	private Map<Integer, SeatHold> holdReservationsMap;
	private Map<String, SeatHold> confirmedReservationsMap;

	public DataGenerator() throws TicketServiceException {
		try {
			
			Map<SeatType, Float> seatRatesMap = initSeatRates();
			availSeatsMap = initAvailSeats(seatRatesMap);
			availSeatsSet = new TreeSet<Seat>(new SeatRankComparator());
			availSeatsSet.addAll(availSeatsMap.values());
			holdReservationsMap = new ConcurrentHashMap<Integer, SeatHold>();
			confirmedReservationsMap = new ConcurrentHashMap<String, SeatHold>();
			LOGGER.info("*****************************************************************************************");
			LOGGER.info("************************* TICKET SERVICE DATA STORE INITIALIZED *************************");
			LOGGER.info("*****************************************************************************************");

		} catch (Exception e) {
			LOGGER.error("Error occurred while accessing the database/maps", e);
			throw new TicketServiceException(TicketServiceErrorType.DATA_BASE_ERROR, e);
		}
	}

	public Map<String, Seat> getAvailSeats() {
		return availSeatsMap;
	}
	
	public SortedSet<Seat> getAvailSeatsSet() {
		return availSeatsSet;
	}

	public Map<Integer, SeatHold> getHoldReservations() {
		return holdReservationsMap;
	}

	public Map<String, SeatHold> getConfirmedReservations() {
		return confirmedReservationsMap;
	}
	
	private Map<SeatType, Float> initSeatRates() {
		Map<SeatType, Float> seatRatesMap = new ConcurrentHashMap<SeatType, Float>();
		seatRatesMap.put(SeatType.GOLD, getGoldSeatRate());
		seatRatesMap.put(SeatType.SILVER, getSilverSeatRate());
		seatRatesMap.put(SeatType.BRONZE, getBronzeSeatRate());
		return seatRatesMap;
	}
	
	private Map<String, Seat> initAvailSeats(Map<SeatType, Float> seatRatesMap) {
		Map<String, Seat> availSeatsMap = new ConcurrentHashMap<String, Seat>();
		int maxRows = getRows();
		int maxCols = getCols();
		int maxGoldRows = getMaxGoldRows();
		int maxSilverRows = getMaxSilverRows();
		for(int rowId=1 ; rowId<=maxRows ; rowId++) {
			for(int colId=1 ; colId<=maxCols ; colId++) {
				SeatType seatType = identifySeatType(rowId, maxGoldRows, maxSilverRows);
				int rank = calculateSeatRank(rowId, colId, maxRows, maxCols);
				float rate = seatRatesMap.get(seatType);
				Seat seat = new Seat(rowId, colId, seatType, rank, rate);
				availSeatsMap.put(seat.getSeatId(), seat);
			}
		}
		return availSeatsMap;
	}
}
