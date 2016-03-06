package org.usfirst.frc.team295.robot;

import java.net.Socket;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.vision.USBCamera;

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
	public static VictorSP victorLeftBack;
	public static VictorSP victorLeftFront;
	public static VictorSP victorRightFront;
	public static VictorSP victorRightBack;
	private static final short BACK_LEFT_PORT = 2;
	private static final short FRONT_LEFT_PORT = 1;
	private static final short BACK_RIGHT_PORT = 4;
	private static final short FRONT_RIGHT_PORT = 3;
	public static USBCamera cameraFront;
	
	public static AHRS ahrs;
	public static RobotDrive driveTrain;
	
	public static Socket clientSocket = null;
	public static boolean clientConnected = false;
	
	
	public static void init(){
		victorLeftBack = new VictorSP(BACK_LEFT_PORT);
    	victorLeftFront = new VictorSP(FRONT_LEFT_PORT);
    	victorRightFront = new VictorSP(FRONT_RIGHT_PORT);
    	victorRightBack = new VictorSP(BACK_RIGHT_PORT);
    	driveTrain = new RobotDrive(
    	        	victorLeftBack,
    	        	victorLeftFront,
    	        	victorRightFront,
    	        	victorRightBack
    	  		); 
    	driveTrain.setSafetyEnabled(false);
    	ahrs = new AHRS(SPI.Port.kMXP);
//    	cameraFront = new USBCamera("cam0");
	}
}
