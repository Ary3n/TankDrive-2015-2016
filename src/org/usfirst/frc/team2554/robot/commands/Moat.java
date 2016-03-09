package org.usfirst.frc.team2554.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Timer;
/**
 *
 *
 */
public class Moat extends Command {
	RobotDrive myRobot;
    public Moat(RobotDrive drive) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	myRobot = drive;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	myRobot.drive(0.75,0);
    	Timer.delay(2.0);
    	myRobot.drive(0, 0);
    }
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return timeSinceInitialized() > 15;
    }

    // Called once after isFinished returns true
    protected void end() {
    	myRobot.drive(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	myRobot.drive(0, 0);
    }
}
