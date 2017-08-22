package com.myprojects.ticket.service.dao.config;

import java.util.ResourceBundle;

/**
 * This class is to fetch the configurations from properties file<br> 
 * <b>ticketData.properties</b> is mainly database related configuration and help setup in-memory database<br>
 * <b>application.properties</b> gives application configuration params
 */
final public class TicketServiceConfig {
	
	private static int rows;
	private static int cols;
	
	private static int maxGoldRows;
	private static int maxSilverRows;
	private static float goldSeatRate;
	private static float silverSeatRate;
	private static float bronzeSeatRate;
	private static int maxSeatsRequest;
	private static int emailThreadPoolSize;
	private static int seatHoldExpiry;
	private static int holdReservationTimeout;
	private static int confirmReservationTimeout;
	private static int availableSeatsLockTimeout;
	private static int seatHoldExpiryTimerFrequency;
	
	static {
		try {
			ResourceBundle ticketDataConfig = ResourceBundle.getBundle("ticketData");
			rows = Integer.parseInt(ticketDataConfig.getString("rows"));
			cols = Integer.parseInt(ticketDataConfig.getString("cols"));
			maxGoldRows = Integer.parseInt(ticketDataConfig.getString("maxGoldRows"));
			maxSilverRows = Integer.parseInt(ticketDataConfig.getString("maxSilverRows"));
			goldSeatRate = Float.parseFloat(ticketDataConfig.getString("goldSeatRate"));
			silverSeatRate = Float.parseFloat(ticketDataConfig.getString("silverSeatRate"));
			bronzeSeatRate = Float.parseFloat(ticketDataConfig.getString("bronzeSeatRate"));
			
			ResourceBundle appConfig = ResourceBundle.getBundle("application");
			seatHoldExpiry = Integer.parseInt(appConfig.getString("seatHoldExpiry"));
			holdReservationTimeout = Integer.parseInt(appConfig.getString("holdReservationTimeout"));
			confirmReservationTimeout = Integer.parseInt(appConfig.getString("confirmReservationTimeout"));
			availableSeatsLockTimeout = Integer.parseInt(appConfig.getString("availableSeatsLockTimeout"));
			seatHoldExpiryTimerFrequency = Integer.parseInt(appConfig.getString("seatHoldExpiryTimerFrequency"));
			maxSeatsRequest = Integer.parseInt(appConfig.getString("maxSeatsRequest"));
			emailThreadPoolSize = Integer.parseInt(appConfig.getString("emailThreadPoolSize"));
			
			
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	private TicketServiceConfig() {
	}
	
	public static int getRows() {
		return rows;
	}
	public static int getCols() {
		return cols;
	}
	public static int getSeatHoldExpiry() {
		return seatHoldExpiry;
	}
	public static int getMaxGoldRows() {
		return maxGoldRows;
	}
	public static int getMaxSilverRows() {
		return maxSilverRows;
	}
	public static float getGoldSeatRate() {
		return goldSeatRate;
	}
	public static float getSilverSeatRate() {
		return silverSeatRate;
	}
	public static float getBronzeSeatRate() {
		return bronzeSeatRate;
	}
	
	public static int getHoldReservationTimeout() {
		return holdReservationTimeout;
	}

	public static int getConfirmReservationTimeout() {
		return confirmReservationTimeout;
	}

	public static int getAvailableSeatsLockTimeout() {
		return availableSeatsLockTimeout;
	}

	public static int getSeatHoldExpiryTimerFrequency() {
		return seatHoldExpiryTimerFrequency;
	}

	public static int getMaxSeatsRequest() {
		return maxSeatsRequest;
	}

	public static int getEmailThreadPoolSize() {
		return emailThreadPoolSize;
	}
	
}
