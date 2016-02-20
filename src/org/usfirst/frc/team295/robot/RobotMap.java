package org.usfirst.frc.team295.robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Victor;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    // For example to map the left and right motors, you could define the
    // following variables to use with your drivetrain subsystem.
    // public static int leftMotor = 1;
    // public static int rightMotor = 2;
    
    // If you are using multiple modules, make sure to define both the port
    // number and the module. For example you with a rangefinder:
    // public static int rangefinderPort = 1;
    // public static int rangefinderModule = 1;
	public static Jaguar jaguarLeftBack;
	public static Jaguar jaguarLeftFront;
	public static Victor victorRightFront;
	public static Victor victorRightBack;
	
	public static AHRS ahrs;
	public static RobotDrive driveTrain;
	public static void init(){
		jaguarLeftBack = new Jaguar(2);
    	jaguarLeftFront = new Jaguar(3);
    	victorRightFront = new Victor(0);
    	victorRightBack = new Victor(1);
    	driveTrain = new RobotDrive(
    	        	jaguarLeftBack,
    	        	jaguarLeftFront,
    	        	victorRightFront,
    	        	victorRightBack
    	  		); 
    	ahrs = new AHRS(SPI.Port.kMXP);
    	driveTrain.setExpiration(.1);
	}
}
