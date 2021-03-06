package org.usfirst.frc.team295.robot.commands;

import org.usfirst.frc.team295.robot.Robot;
import org.usfirst.frc.team295.robot.RobotMap;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoDrive extends Command{
	double dTime;
	double dSpeed;
	double Kp = .03;
	double dDirection;
	boolean done;
	static double startTime;
	AHRS ahrs;
	double dFront;
	double dDiff;
	public AutoDrive(double time, double speed, double direction){
		dTime = time;
		dSpeed = speed;
		dDirection = direction;
		requires(Robot.drivetrain);
		
	}
	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		startTime = Timer.getFPGATimestamp();
		ahrs = RobotMap.ahrs;
		dFront = ahrs.getYaw();
	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub
		SmartDashboard.putBoolean("Doing it", true);
		SmartDashboard.putNumber("dSpeed", dSpeed);
		dDiff = dFront - ahrs.getYaw();
		
		if(dDirection > 0){
			
			Robot.drivetrain.drive(dSpeed, (dDiff)*Kp);
		}
		else if(dDirection < 0){
			Robot.drivetrain.drive(-dSpeed, -(dDiff)*Kp);	
		}
		
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub3
		if(Timer.getFPGATimestamp() > startTime + dTime){
			return true;
		}
		else{
			return false;
		}
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub
		SmartDashboard.putBoolean("Doing it", false);
		Robot.drivetrain.drive(0, 0);
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		end();
	}

}
