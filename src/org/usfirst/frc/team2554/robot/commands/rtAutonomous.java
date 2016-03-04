package org.usfirst.frc.team2554.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Timer;
import org.usfirst.frc.team2554.robot.Robot;

/**
 *
 */
public class rtAutonomous extends Command {
	RobotDrive myRobot;
    public rtAutonomous(RobotDrive drive) {
    	myRobot = drive;
        // Use requires() here to declare subsystem dependencies
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	myRobot.tankDrive(0.5,0.5);
    	Timer.delay(2.0);
    	myRobot.tankDrive(0,0);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return timeSinceInitialized() > 15;
    }

    // Called once after isFinished returns true
    protected void end() {
    	myRobot.tankDrive(0,0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	myRobot.tankDrive(0,0);
    }
}
