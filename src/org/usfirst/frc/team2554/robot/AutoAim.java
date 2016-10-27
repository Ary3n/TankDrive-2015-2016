package org.usfirst.frc.team2554.robot;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class AutoAim {
	NetworkTable server = NetworkTable.getTable("SmartDashboard");
	double xValue, yValue;
	final double DEADZONE = 30;
	
	public void run(RobotDrive myRobot, Victor shooterArm, RobotDrive shooter)
	{
		changeValues();
		if(xValue > DEADZONE)
				myRobot.arcadeDrive(0,-0.1);
		if(xValue < -DEADZONE)
				myRobot.arcadeDrive(0,0.1);
		if(yValue < DEADZONE)
				shooterArm.set(0.1);
		if(yValue > DEADZONE * 2)
				shooterArm.set(-0.1);
		
		if(xValue < DEADZONE && xValue > -DEADZONE && yValue > DEADZONE && yValue < 2*DEADZONE)
			shooter.arcadeDrive(0,0.6);
	}
	private void changeValues()
	{
		xValue = server.getNumber("magnitudeX", 0.0);
		yValue = server.getNumber("magnitudeY", 0.0);
			
	}
}
