package org.usfirst.frc.team295.robot.subsystems;


import org.usfirst.frc.team295.robot.Robot;
import org.usfirst.frc.team295.robot.RobotMap;
import org.usfirst.frc.team295.robot.commands.PIDTurnRight;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Drivetrain extends PIDSubsystem{
	
	RobotDrive drive = RobotMap.driveTrain;
	AHRS ahrs = RobotMap.ahrs;
	public static double direction;
	public Drivetrain(){
		super("Drivetrain",.025, .0015, .03);
		setAbsoluteTolerance(3);
		getPIDController().setInputRange(0,360);
		getPIDController().setOutputRange(-.5, .5);
		getPIDController().setContinuous(true);
		LiveWindow.addActuator("Drive Base", "PID Controller", getPIDController());
		
	}
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
//		setDefaultCommand(new Logger());
	}
	public void drive(double magnitude, double curve){
		 drive.drive(magnitude, curve);
	}
	@Override
	protected double returnPIDInput() {
		// TODO Auto-generated method stub
		return ahrs.getAngle();
	}
	@Override
	protected void usePIDOutput(double output) {
		// TODO Auto-generated method stub
//		System.out.println(direction);
		if(output<0){
			drive.drive(-output, -1);
		}
		else if(output>0){
			drive.drive(output, 1);
		}
		
		
	}
	public double getYaw(){
		return ahrs.getYaw();
	}
	
}
