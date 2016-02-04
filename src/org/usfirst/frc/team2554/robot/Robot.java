package org.usfirst.frc.team2554.robot;


import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.*;


public class Robot extends SampleRobot {
    RobotDrive myRobot;  // class that handles basic drive operations
    Joystick rightStick; // set to ID 1 in DriverStation
    double right;
    SendableChooser autoChooser;
    DefaultAutonomous defaultAutonomous;
    Command autonomousCommand, rtAuto;
    public void initSmartBoard()
    {
    	autoChooser.addDefault("Under Low Bar", defaultAutonomous);
    	autoChooser.addObject("Rough Terrain", rtAuto);
        SmartDashboard.putData("Auto Command",autoChooser);
    }
     public Robot() {
        myRobot = new RobotDrive(0, 1,2,3);
        myRobot.setExpiration(0.1);
        rightStick = new Joystick(1);
        autoChooser = new SendableChooser();
        defaultAutonomous = new DefaultAutonomous(myRobot);
        rtAuto = new rtAutonomous();
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
        	/*
        	Might not need:
        	if(rightStick.getY()>0)
        		right = -rightStick.getMagnitude();
        	if(rightStick.getY()<0)
        		right = rightStick.getMagnitude();*/
        	myRobot.arcadeDrive(rightStick, 2,rightStick,1);
            Timer.delay(0.005);		// wait for a motor update time
        }
    }

}
