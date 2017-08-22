package com.myprojects.ticket.service.dao;

import java.util.List;

import com.myprojects.ticket.service.beans.Seat;
import com.myprojects.ticket.service.beans.SeatHold;
import com.myprojects.ticket.service.exception.TicketServiceException;

/**
 * DAO Interface for ticket-service 
 */
public interface TicketServiceDAO {
	/**
	 * Returns all available seats from database
	 * @return List<Seat> Returns all available seats from database
	 * @throws TicketServiceException
	 */
	public List<Seat> getAvailSeats() throws TicketServiceException;
	
	/**
	 * This puts the seat back in availability list after SeatHold is expired  
	 * @param Seat to be made available
	 * @throws TicketServiceException
	 */
	public void makeSeatAvailable(Seat seat) throws TicketServiceException;
	
	/**
	 * Holds seat for reservation and makes it unavailable for other seat hold requests
	 * @param seatId of seat to be hold
	 * @return Seat to be made unavailable
	 * @throws TicketServiceException
	 */
	public Seat makeSeatUnavailable(String seatId) throws TicketServiceException;
	
	/**
	 * Returns all seat hold objects including open, expired and reserved
	 * @return List<SeatHold> of SeatHold objects
	 * @throws TicketServiceException
	 */
	public List<SeatHold> getAllHoldSeatHolds() throws TicketServiceException;
	
	/**
	 * Returns particular SeatHold reservation
	 * @param seatHoldId of desired SeatHold object
	 * @return SeatHold desired object
	 * @throws TicketServiceException
	 */
	public SeatHold getHoldReservations(int seatHoldId) throws TicketServiceException;
	
	/**
	 * Inserts new SeatHold reservation
	 * @param seatHold new SeatHold reservation
	 * @throws TicketServiceException
	 */
	public void putHoldReservation(SeatHold seatHold) throws TicketServiceException;
	
	/**
	 * Updates existing SeatHold reservation
	 * @param seatHold existing SeatHold reservation
	 * @throws TicketServiceException
	 */
	public void updateHoldReservation(SeatHold seatHold) throws TicketServiceException;
	
	/**
	 * Retrieve desired confirmed reservation in form of SeatHold object based on its confirmationId
	 * @param confirmationId of previously confirmed SeatHold
	 * @return SeatHold Confirmed SeatHold reservation
	 * @throws TicketServiceException
	 */
	public SeatHold getConfirmedReservation(String confirmationId) throws TicketServiceException;
	
	/**
	 * Confirm the held reservation
	 * @param seatHold
	 * @throws TicketServiceException
	 */
	public void confirmReservation(SeatHold seatHold) throws TicketServiceException;
}
