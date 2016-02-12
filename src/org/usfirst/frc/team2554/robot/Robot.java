package org.usfirst.frc.team2554.robot;


import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.*;
import edu.wpi.first.wpilibj.Talon;

public class Robot extends SampleRobot {
    RobotDrive myRobot;  // class that handles basic drive operations
    Joystick rightStick, controller; // set to ID 1 in DriverStation
    double right;
    SendableChooser autoChooser;
    DefaultAutonomous defaultAutonomous;
    Command autonomousCommand, rtAuto;
    Talon armLeft,armRight;
    public void initSmartBoard()
    {
    	autoChooser.addDefault("Under Low Bar", defaultAutonomous);
    	autoChooser.addObject("Rough Terrain", rtAuto);
        SmartDashboard.putData("Auto Command",autoChooser);
    }
     public Robot() {
        myRobot = new RobotDrive(0,1,2,3);
        armLeft = new Talon(4);
        armRight = new Talon(5);
        myRobot.setExpiration(0.1);
        rightStick = new Joystick(1);
        controller = new Joystick(2);
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
        	myRobot.arcadeDrive(rightStick, 2,rightStick,1);
        	armLeft.set(controller.getRawAxis(1)/5.0);
        	armRight.set(controller.getRawAxis(5)/5.0);
            Timer.delay(0.005);		// wait for a motor update time
        }
    }

}
