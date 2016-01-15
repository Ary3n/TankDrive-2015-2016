package org.usfirst.frc.team2554.robot;


import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;


public class Robot extends SampleRobot {
    RobotDrive myRobot;  // class that handles basic drive operations
    Joystick leftStick;  // set to ID 1 in DriverStation
    Joystick rightStick; // set to ID 2 in DriverStation
    double left,right;
    public Robot() {
        myRobot = new RobotDrive(0, 1,2,3);
        myRobot.setExpiration(0.1);
        leftStick = new Joystick(0);
        rightStick = new Joystick(1);
    }

    
    /**
     * Runs the motors with tank steering.
     */
    public void operatorControl() {
        myRobot.setSafetyEnabled(true);
        while (isOperatorControl() && isEnabled()) {
        	
        	if(rightStick.getY()>0 && rightStick.getTrigger()==false)
        		right = rightStick.getMagnitude();
        	if(rightStick.getY()<0 && rightStick.getTrigger()==false)
        		right = -rightStick.getMagnitude();
        	left = -right;
        	if(rightStick.getX()>0 && rightStick.getTrigger())
        		left=right;
        	if(rightStick.getX()<0 && rightStick.getTrigger())
        	{
        		right = -right;
        		left=right;
        	}
        	myRobot.tankDrive(left, right);
            Timer.delay(0.005);		// wait for a motor update time
        }
    }

}