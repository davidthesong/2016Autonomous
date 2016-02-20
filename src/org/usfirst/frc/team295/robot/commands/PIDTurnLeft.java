package org.usfirst.frc.team295.robot.commands;

import org.usfirst.frc.team295.robot.Globals;
import org.usfirst.frc.team295.robot.Robot;
import org.usfirst.frc.team295.robot.RobotMap;
import org.usfirst.frc.team295.robot.subsystems.Drivetrain;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class PIDTurnLeft extends Command{
	double dstartTime;
	double dpointAngle;
	double dendAngle;
	double dturnAmount;
	double dAngle;
	double dEndDiff;
	AHRS ahrs;
	boolean done = false;
	/* Feature to make front the zero*/
	
	public PIDTurnLeft(double amount){
		requires(Robot.drivetrain);
		dturnAmount = amount;
	}
	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		done = false;
		Drivetrain.direction = -1;
		Robot.drivetrain.enable();
		ahrs = RobotMap.ahrs;
		dpointAngle = ahrs.getAngle();
		dendAngle = ahrs.getAngle() - dturnAmount;
		dstartTime = Timer.getFPGATimestamp();
    		if(dendAngle < 0){
    			dendAngle = 360 + dendAngle; 
    		}
	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub
		dAngle = ahrs.getAngle();
		
		if(Timer.getFPGATimestamp()>dstartTime){
			dstartTime += 0.0020;
			if(dpointAngle > dendAngle){
				dpointAngle +=-.5;
			}
			else if(dpointAngle < (dendAngle - dturnAmount))
			{
				dpointAngle +=-.5;
				if(dpointAngle < 0){
					dpointAngle = 360 - dpointAngle;
				}
			}
			else if(dpointAngle < dendAngle){
				System.out.println("point : " + dpointAngle + " end: "+ dendAngle);
				done = true;
			}
		}
		Robot.drivetrain.setSetpoint(dpointAngle);
		
		System.out.println(
				"Done ? : " + done +
				" Error : " + Globals.dError + " " + 
				"Speed : " +Robot.drivetrain.getPIDController().get()
				+ " DiffAngle : " + (dpointAngle - dAngle) + " DiffTotal : " + (dendAngle - dAngle) + " Current Angle : " + dAngle + " End Angle: " 
				+ dendAngle + " dpointAngle : " + dpointAngle);
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		if(done){
			Globals.dError = dendAngle-ahrs.getAngle();
			return true;
		}
		return false;
//		return Robot.drivetrain.onTarget();
//		return Robot.drivetrain.onTarget();
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub
		Robot.drivetrain.disable();
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		
	}

}
