package org.usfirst.frc.team295.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team295.robot.commands.AutoDrive;
import org.usfirst.frc.team295.robot.commands.AutoTurn;
import org.usfirst.frc.team295.robot.commands.Autonomous;
import org.usfirst.frc.team295.robot.commands.PIDTurnLeft;
import org.usfirst.frc.team295.robot.commands.PIDTurnRight;
import org.usfirst.frc.team295.robot.commands.Reset;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	public Joystick joystick;
	Button buttonTurn;
	Button buttonDrive;
	Button buttonReset;
    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
    // Joystick stick = new Joystick(port);
    // Button button = new JoystickButton(stick, buttonNumber);
    
    // There are a few additional built in buttons you can use. Additionally,
    // by subclassing Button you can create custom triggers and bind those to
    // commands the same as any other Button.
    
    //// TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:
    
    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenPressed(new ExampleCommand());
    
    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    // button.whileHeld(new ExampleCommand());
    
    // Start the command when the button is released  and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());
	public OI(){
		joystick = new Joystick(0);
		buttonTurn = new JoystickButton(joystick,1);
		buttonDrive  = new JoystickButton(joystick, 2);
		buttonReset = new JoystickButton(joystick,3);
//		buttonTurn.whenPressed(new PIDTurn(90 + Globals.dError));
		buttonTurn.whenActive(new PIDTurnLeft(90));
//		buttonDrive.whenPressed(new AutoDrive(1,.25,1));
		buttonDrive.whenActive(new PIDTurnRight(90));
		buttonReset.whenPressed(new Reset());
		
		SmartDashboard.putData("Autonomous", new Autonomous());
		SmartDashboard.putData("Drive", new AutoDrive(1,.3,1));
	}
}

