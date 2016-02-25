package org.usfirst.frc.team2554.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;
/**
 *
 */
public class Portcullis extends Command {
	RobotBase robotBase;
	RobotDrive myRobot;
	Victor armBar;
    public Portcullis(RobotDrive driveSystem) {
    	 myRobot = driveSystem;
    	 armBar= new Victor(6);
        // Use requires() here to declare subsystem dependencies
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	myRobot.drive(0.2,0);
    	Timer.delay(1.0);
    	myRobot.drive(0,0);
    	armBar.set(.3);
    	Timer.delay(1.0);
    	myRobot.drive(0.2,0);
    	Timer.delay(1.0);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return(timeSinceInitialized()>3);
    }

    // Called once after isFinished returns true
    protected void end() {
    	myRobot.drive(0,0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
