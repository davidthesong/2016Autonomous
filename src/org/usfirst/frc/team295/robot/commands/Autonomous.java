package org.usfirst.frc.team295.robot.commands;

import org.usfirst.frc.team295.robot.Globals;
import org.usfirst.frc.team295.robot.RobotMap;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Autonomous extends CommandGroup{

	double dZeroTurn;
	double dZeroAngle;
	public Autonomous(){
//		dZeroAngle = RobotMap.ahrs.getAngle();
		RobotMap.ahrs.reset();
		dZeroAngle = RobotMap.ahrs.getAngle();
		SmartDashboard.putNumber("dzeroangle", dZeroAngle);
//		addSequential(new AutoDrive(1, .35,1));
//		addSequential(new PIDTurn(180));
//		addSequential(new AutoDrive(1,.35,1));
		addSequential(new AutoDrive(1.25, .6,1));
		dZeroTurn =dZeroAngle - RobotMap.ahrs.getAngle();
		System.out.println(dZeroTurn + " Dzeroturn");
//		SmartDashboard.putNumber("Dzero", value);
//		addSequential(new PIDTurn(dZeroTurn));
//		addSequential(new PIDTurn(90 + Globals.dError));
	}
}
