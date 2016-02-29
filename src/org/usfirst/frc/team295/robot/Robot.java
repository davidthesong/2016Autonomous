
package org.usfirst.frc.team295.robot;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

import javax.swing.InputMap;

import org.usfirst.frc.team295.robot.commands.AutoTurn;
import org.usfirst.frc.team295.robot.commands.PIDTurnRight;
import org.usfirst.frc.team295.robot.subsystems.Drivetrain;
import org.usfirst.frc.team295.robot.subsystems.ExampleSubsystem;

import com.kauailabs.navx.frc.AHRS;
import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.USBCamera;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static Drivetrain drivetrain;
	public static OI oi;

    Command autonomousCommand;
    SendableChooser chooser;
    public static Logger logger;
    private static Timer sessionTimer  = null;
    private static long sessionIteration = 0;
    boolean cameraDirection; /* true = front; false = back */
    Image frame;
    public USBCamera cameraFront;
    public USBCamera cameraBack;
    AHRS ahrs;
    ByteBuffer data;
    Socket s;
    ServerSocket serversocket = null;
    Thread ServerThread;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    static{
    	logger  = Logger.getInstance();
    }
    CameraServer server;
    
    public void robotInit() {
//    	data = ByteBuffer.allocate(100);
		RobotMap.init();
		drivetrain  = new Drivetrain();
    	oi = new OI();
		ahrs = RobotMap.ahrs;
		sessionTimer = new Timer();

		try {
			serversocket = new ServerSocket(5800);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		cameraDirection = true;
//		cameraFront = RobotMap.cameraFront;
//		cameraFront.openCamera();
//		cameraFront.setFPS(30);
		//TODO: SET SIZE OF DISPLAY
//		cameraFront.updateSettings();
//		cameraFront.startCapture();
//		
//		cameraBack = new USBCamera("cam1");
//		cameraBack.openCamera();
//		cameraBack.setFPS(30);
//		cameraBack.updateSettings();
		
		
		
		
//		
//		frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
//		server = CameraServer.getInstance();
//        server.setQuality(20);
    }
        
//        //the camera name (ex "cam0") can be found through the roborio web interface
//        server.startAutomaticCapture("cam0");
    public void disabledInit(){
    	logger.endLog();
    	
    	sessionTimer.reset();
		sessionIteration = 0;
		ahrs.resetDisplacement();
		
		if(ServerThread != null){
			ServerThread.interrupt();
		}
		
    }
    public void enabledPeriodic() throws IOException{
//    	sessionIteration++;
//    	sessionTimer.start();
  
//    	if(Robot.oi.joystick.getRawButton(1)){
//    		cameraDirection = !cameraDirection;
////    		if(cameraDirection){
////    			cameraBack.stopCapture();
////    			cameraFront.startCapture();
////    		}
////    		else{
////    			cameraFront.stopCapture();
////    			cameraBack.startCapture();
////    		}
//    	}
//    	if(cameraDirection){
//    		cameraFront.getImage(frame);
//    		System.out.println(frame.toString());
//    	}
//    	else{
//    		cameraBack.getImage(frame);
//    	}
//    	cameraFront.getImage(frame);
//    	server.setImage(frame);

//    	
//    	NIVision.IMAQdxGrab(currSession, frame, 1);
//    	CameraServer.getInstance().setImage(frame);
    	
    	
    }
	
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select between different autonomous modes
	 * using the dashboard. The sendable chooser code works with the Java SmartDashboard. If you prefer the LabVIEW
	 * Dashboard, remove all of the chooser code and uncomment the getString code to get the auto name from the text box
	 * below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the chooser code above (like the commented example)
	 * or additional comparisons to the switch structure below with additional strings & commands.
	 */
    public void autonomousInit() {
        autonomousCommand = (Command) chooser.getSelected();
        
		/* String autoSelected = SmartDashboard.getString("Auto Selector", "Default");
		switch(autoSelected) {
		case "My Auto":
			autonomousCommand = new MyAutoCommand();
			break;
		case "Default Auto":
		default:
			autonomousCommand = new ExampleCommand();
			break;
		} */
    	
    	// schedule the autonomous command (example)
        if (autonomousCommand != null) autonomousCommand.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
        
        
    }

    public void teleopInit() {
		// This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to 
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null) autonomousCommand.cancel();
        
        ServerThread = new Thread(new Server(serversocket));
		ServerThread.start();
        double dZeroAngle = RobotMap.ahrs.getAngle();
        
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        try {
			enabledPeriodic();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
//    	SmartDashboard.putData("PID Turn Right", new PIDTurnRight(90,1));
//    	SmartDashboard.putData("PID Turn Left", new PIDTurnRight(90,-1));
//		System.out.println(ahrs.getAngle());
        LiveWindow.run();
//        try {
//			enabledPeriodic();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//        System.out.println("y"+ahrs.getDisplacementY());
//        System.out.println("x"+ahrs.getDisplacementX());
//        System.out.println("z"+ahrs.getDisplacementZ());
        RobotMap.driveTrain.tankDrive(-1* oi.joystick.getRawAxis(1), -1 * oi.joystick.getRawAxis(5));
        System.out.println(ahrs.getYaw());
//        RobotMap.driveTrain.drive(-1* oi.joystick.getRawAxis(1), -1 * oi.joystick.getRawAxis(5));
    }
    public static double getTimerValue(){
    	return sessionTimer.get();
    }
    
    public static long getSessionIteration() {
		return sessionIteration;
	}
    public static void logAutonomous(){
    	
    }
}
