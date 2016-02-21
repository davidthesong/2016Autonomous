
package org.usfirst.frc.team295.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import org.usfirst.frc.team295.robot.commands.AutoTurn;
import org.usfirst.frc.team295.robot.commands.PIDTurnRight;
import org.usfirst.frc.team295.robot.subsystems.Drivetrain;
import org.usfirst.frc.team295.robot.subsystems.ExampleSubsystem;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
    AHRS ahrs;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    static{
    	logger  = Logger.getInstance();
    }
    
    public void robotInit() {
		RobotMap.init();
		drivetrain  = new Drivetrain();
    	oi = new OI();
		ahrs = RobotMap.ahrs;
		sessionTimer = new Timer();
//        chooser = new SendableChooser();
//        chooser.addDefault("Default Auto", new AutoTurn());
////        chooser.addObject("My Auto", new MyAutoCommand());
//        SmartDashboard.putData("Auto mode", chooser);
    }
	
	/**
     * This function is called once each time the robot enters Disabled mode.
     * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
     */
    public void disabledInit(){
    	logger.endLog();
    	
    	sessionTimer.reset();
		sessionIteration = 0;
    }
    public void enabledPeriodic(){
    	sessionIteration++;
    	sessionTimer.start();
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
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        enabledPeriodic();
        
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
//    	SmartDashboard.putData("PID Turn Right", new PIDTurnRight(90,1));
//    	SmartDashboard.putData("PID Turn Left", new PIDTurnRight(90,-1));
//		System.out.println(ahrs.getAngle());
        LiveWindow.run();
        enabledPeriodic();
        RobotMap.driveTrain.tankDrive(-1* oi.joystick.getRawAxis(1), -1 * oi.joystick.getRawAxis(5));
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
