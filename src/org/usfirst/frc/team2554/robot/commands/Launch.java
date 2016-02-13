package org.usfirst.frc.team2554.robot.commands;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.*;

public class Launch extends Command {
	private Spark launcher;
	final double speed = 0.5; // From -1 to 1
	final double timerDelay = 3;

	public Launch() {
	}

	protected void initialize() {
		launcher = new Spark(8);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		launcher.set(speed);
		Timer.delay(1);
		launcher.set(-speed);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return (timeSinceInitialized() > timerDelay);
	}

	// Called once after isFinished returns true
	protected void end() {
		launcher.set(0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		launcher.set(0);
	}
}