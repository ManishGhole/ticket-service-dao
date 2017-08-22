package com.myprojects.ticket.service.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myprojects.ticket.service.beans.Seat;
import com.myprojects.ticket.service.beans.SeatHold;
import com.myprojects.ticket.service.enums.TicketServiceErrorType;
import com.myprojects.ticket.service.exception.TicketServiceException;

/**
 * DAO Interface implementation for ticket-service 
 */
@Component
public final class TicketServiceDAOImpl implements TicketServiceDAO {

	private static final Logger LOGGER = Logger.getLogger(TicketServiceDAOImpl.class);

	/**
	 * DataGenerator object is more like acting database (but in memory)
	 */
	@Autowired
	private DataGenerator dataGenerator;

	public void setDataGenerator(DataGenerator dataGenerator) {
		this.dataGenerator = dataGenerator;
	}

	@Override
	public List<Seat> getAvailSeats() throws TicketServiceException {
		// Fetch from the database query
		checkForNull(dataGenerator.getAvailSeats(), "Available Seat Map");
		List<Seat> seats = new ArrayList<Seat>(dataGenerator.getAvailSeats().values());
		return seats;
	}

	@Override
	public void makeSeatAvailable(Seat seat) throws TicketServiceException {
		// Add seat back in the database to make seat Available
		checkForNull(dataGenerator.getAvailSeats(), "Available Seat Map");
		dataGenerator.getAvailSeats().put(seat.getSeatId(), seat);
	}

	@Override
	public Seat makeSeatUnavailable(String seatId) throws TicketServiceException {
		// Remove seat from the database to make seat Unavailable for other
		// users
		checkForNull(dataGenerator.getAvailSeats(), "Available Seat Map");
		Seat seat = dataGenerator.getAvailSeats().remove(seatId);
		return seat;
	}

	@Override
	public List<SeatHold> getAllHoldSeatHolds() throws TicketServiceException {
		// Fetch from the database query
		checkForNull(dataGenerator.getHoldReservations(), "Hold Reservations Map");
		List<SeatHold> seatHolds = new ArrayList<SeatHold>(dataGenerator.getHoldReservations().values());
		return seatHolds;
	}

	@Override
	public SeatHold getHoldReservations(int seatHoldId) throws TicketServiceException {
		// Fetch seatHold from database
		checkForNull(dataGenerator.getHoldReservations(), "Hold Reservations Map");
		SeatHold seatHold = dataGenerator.getHoldReservations().get(seatHoldId);
		return seatHold;
	}

	@Override
	public void putHoldReservation(SeatHold seatHold) throws TicketServiceException {
		// Insert new seatHold in the database
		checkForNull(dataGenerator.getHoldReservations(), "Hold Reservations Map");
		dataGenerator.getHoldReservations().put(seatHold.getSeatHoldId(), seatHold);
	}

	@Override
	public void updateHoldReservation(SeatHold seatHold) throws TicketServiceException {
		// Update existing seatHold in the database
		checkForNull(dataGenerator.getHoldReservations(), "Hold Reservations Map");
		dataGenerator.getHoldReservations().put(seatHold.getSeatHoldId(), seatHold);
	}

	@Override
	public SeatHold getConfirmedReservation(String confirmationId) throws TicketServiceException {
		// Fetches the desired already confirmed reservation seatHold object
		checkForNull(dataGenerator.getHoldReservations(), "Hold Reservations Map");
		checkForNull(dataGenerator.getConfirmedReservations(), "Confirmed Reservations Map");
		SeatHold seatHold = dataGenerator.getHoldReservations().get(dataGenerator.getConfirmedReservations().get(confirmationId));
		return seatHold;
	}

	@Override
	public void confirmReservation(SeatHold seatHold) throws TicketServiceException {
		// Confirm the reservation and update the flag in the database
		checkForNull(dataGenerator.getConfirmedReservations(), "Confirmed Reservations Map");
		dataGenerator.getConfirmedReservations().put(seatHold.getConfirmationId(), seatHold);
	}

	/**
	 * This method will check for map to be null or not
	 * @param map
	 * @param label
	 * @throws TicketServiceException
	 */
	private void checkForNull(Map<?, ?> map, String label) throws TicketServiceException {
		if (map == null) {
			String msg = label + " is null";
			LOGGER.error(msg);
			throw new TicketServiceException(msg, TicketServiceErrorType.DATA_BASE_ERROR);
		}
	}

}
