package org.usfirst.frc.team295.robot;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;





public class Logger {
	

	public static final String LOGGER_LOGDIR = "/home/lvuser/logs/";
	public static final String LOGGER_DELIMETER = ";";
	
	private static Logger singleton = null;
	
	private final String logPrefix;
	private int logNumber = 0;
	
	private File outputFile = null;
	private BufferedWriter outputWriter = null;
	
	
	private Logger() {
		logPrefix =
				LOGGER_LOGDIR
				+ "log_"
				+ new SimpleDateFormat("MMdd-HHmmss").format(new Date()) // Current time
				+ "_";
		
		startLog();
	}
	
	/*
	 * Starts a new log file.
	 */
	private void startLog() {
		outputFile = null;
		outputWriter = null;

		outputFile = new File(logPrefix + String.format("%02d", logNumber)+ ".csv");
		
		try {
			outputWriter = new BufferedWriter(new FileWriter(outputFile));
			System.out.println("Created new log at " + outputFile.getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static Logger getInstance() {
		if (singleton == null) {
			singleton = new Logger();
		}
		return singleton;
	}
	
	/**
	 * Adds an entry to the log file in the following format (delimited by LOGGER_DELIMETER):<br>
	 * 	- system time (ms)<br>
	 * 	- robot session uptime (s)<br>
	 * 	- robot session iteration<br>
	 * 	- event type (user-defined String)<br>
	 * 	- message tokens (user-defined String(s))
	 * 
	 * @param eventType an unique identifier for type of event
	 * @param messageTokens zero or more Strings to be printed in the entry
	 */
	public void log(String eventType, String... messageTokens) {
		try{
			outputWriter.write(getLogHeader());
			
			outputWriter.write(LOGGER_DELIMETER);
			outputWriter.write(eventType);
			
			for (String s : messageTokens) {
				outputWriter.write(LOGGER_DELIMETER);
				outputWriter.write(s);
			}
			outputWriter.newLine();
			outputWriter.flush();
		}
		 catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	/**
	 * Closes the existing log file, and prepares a new log file.
	 */
	public void endLog() {
//		if (Robot.getSessionIteration() < 1) {
//			outputFile.delete(); // If no iterations have passed, delete log
//			System.out.println("Deleted empty log at " + outputFile.getAbsolutePath());
//		} else {
//			System.out.println("Completed log at " + outputFile.getAbsolutePath());
//		}
		
		try {
			outputWriter.flush();
			outputWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		logNumber++;
		startLog();
	}
	
	private String getLogHeader() {
		return String.valueOf(System.currentTimeMillis())
				+ LOGGER_DELIMETER + String.valueOf(Robot.getSessionIteration());
	}
	
}
