package org.usfirst.frc.team2554.robot;


import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.*;
import edu.wpi.first.wpilibj.Victor;

import org.usfirst.frc.team2554.robot.commands.*;
import org.usfirst.frc.team2554.robot.subsystems.*;
import edu.wpi.first.wpilibj.buttons.*;

;public class Robot extends SampleRobot {
    RobotDrive myRobot;  // class that handles basic drive operations
    RobotDrive shooter;

    // Two drivers necessary!
    Joystick rightStick, controller; // set to ID 1 in DriverStation
    JoystickButton launchButton;
    //double right;
    SendableChooser autoChooser;
    Command autonomousCommand;
    Victor armBar,armShooter;

    public void initSmartBoard()
    {
    	autoChooser.addDefault("Under Low Bar", new DefaultAutonomous(myRobot));
    	autoChooser.addObject("Rough Terrain", new rtAutonomous());
        SmartDashboard.putData("Auto Command",autoChooser);
    }
    
    public Robot() {
        myRobot = new RobotDrive(1,0,3,2); //FrontLeft, BackLeft, FrontRight, BackRight
        shooter = new RobotDrive(4,5);
        armBar = new Victor(6);
        armShooter = new Victor(7);
        myRobot.setExpiration(0.1);
        rightStick = new Joystick(0);
        controller = new Joystick(1);
        launchButton = new JoystickButton(controller, 7);
        autoChooser = new SendableChooser();
        initSmartBoard();
    }
    public void autonomous()
    {
    	autonomousCommand = (Command) autoChooser.getSelected();
    	autonomousCommand.start();
    	Timer.delay(0.05);
    }
    /**
     * The motors using arcade steering
     */
    public void operatorControl() {
        myRobot.setSafetyEnabled(true);
        while (isOperatorControl() && isEnabled()) 
        {
        	myRobot.arcadeDrive(rightStick, 1,rightStick,2);
        	//Set speed of each arm based on y-axis of each joystick on controller
        		//1 is L Y Axis
        		//5 is R Y Axis
	        armBar.set(controller.getRawAxis(1)/5.0);
	        armShooter.set(controller.getRawAxis(5)/5.0);
	        //Shooter System NOTE: Not a subsystem. 
	        //3 is Right Trigger
	        shooter.arcadeDrive(0,controller.getRawAxis(3));
	        //Collector; 2 is left trigger
	        shooter.arcadeDrive(0,-controller.getRawAxis(2));
	        launchButton.whenPressed(new Launch());
            Timer.delay(0.005);		// wait for a motor update time
        }
    }

}
