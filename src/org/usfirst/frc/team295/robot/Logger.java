package org.usfirst.frc.team295.robot;

public class Logger {
	
	private Logger(){}
	
	public static void log(Exception exception)
	{
		Logger.log(exception.toString());
	}
	public static void log(String message)
	{
		System.out.println("Log : " + message);
	}
	public static void log(String name, double message)
	{
		System.out.println("Log " + name + " : " + message);
	} 
}
