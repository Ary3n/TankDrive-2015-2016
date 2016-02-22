package org.usfirst.frc.team2554.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Victor;
/**
 *
 */
public class AutoAim extends Command {
	NetworkTable table;
	double xCenter, yCenter, goodZone, totalCenterX, totalCenterY;
	boolean isXCentered, isYCentered;
	RobotDrive driveSystem;
	Victor shooterArm;
    public AutoAim(RobotDrive drive, Victor shooter) {
        // Use requires() here to declare subsystem dependencies
    	xCenter = 0.0;
    	yCenter = 0.0;
    	totalCenterX = 320; //adjust
    	totalCenterY = 240; //adjust
    	driveSystem = drive;
    	shooterArm = shooter;
    	isXCentered = false;
    	isYCentered = false;
    	goodZone = 5; //Change Value
    	table = NetworkTable.getTable("GRIP/myContoursReport");
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	NetworkTable.setClientMode();
    	NetworkTable.setTeam(2554);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(table.containsKey("centerX"))
    		xCenter = table.getDouble("centerX");
    	else
    		xCenter = 640; //Default Value for X
    	if(table.containsKey("centerY"))
    		yCenter = table.getDouble("centerY");
    	else
    		yCenter = 360; //Default Value for Y
    	if(totalCenterX - xCenter > goodZone)
    		driveSystem.arcadeDrive(0, 0.25);
    	else
    	{
    		driveSystem.arcadeDrive(0,0);
    		isXCentered = true;
    	}
    	if(totalCenterY - yCenter > goodZone)
    		shooterArm.set(0.2);
    	else
    	{
    		shooterArm.set(0);
    		isYCentered = true;
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return isXCentered && isYCentered;
    }

    // Called once after isFinished returns true
    protected void end() {
    	shooterArm.set(0);
    	driveSystem.arcadeDrive(0,0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	shooterArm.set(0);
    	driveSystem.arcadeDrive(0,0);
    }
}
