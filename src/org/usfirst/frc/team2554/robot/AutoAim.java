package org.usfirst.frc.team2554.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class AutoAim {
	static NetworkTable server = NetworkTable.getTable("SmartDashboard");
	static double xValue, yValue;
	static final double DEADZONE = 30;
	
	public static void run(RobotDrive myRobot, Victor shooterArm, RobotDrive shooter, Spark launcher, DigitalInput limitSwitch)
	{
		changeValues();
		if(xValue > DEADZONE)
				myRobot.arcadeDrive(0,-0.1);
		if(xValue < -DEADZONE)
				myRobot.arcadeDrive(0,0.1);
		if(yValue < DEADZONE && !limitSwitch.get())
				shooterArm.set(0.1);
		if(yValue > DEADZONE * 2 && !limitSwitch.get())
				shooterArm.set(-0.1);
		
		if(xValue < DEADZONE && xValue > -DEADZONE && yValue > DEADZONE && yValue < 2*DEADZONE)
		{
			shooter.arcadeDrive(0,0.6);
			launcher.set(0.4);
		}
	}
	private static void changeValues()
	{
		xValue = server.getNumber("magnitudeX", 0.0);
		yValue = server.getNumber("magnitudeY", 0.0);
			
	}
}
