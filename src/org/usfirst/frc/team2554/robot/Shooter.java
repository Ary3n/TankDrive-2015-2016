package org.usfirst.frc.team2554.robot;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.*;
public class Shooter extends Command {
	private Talon t1, t2;
	final double speed = 0.5; //From -1 to 1
	final double timerDelay = 3;
    public Shooter(int port1, int port2){
    	t1 = new Talon(port1);
    	t2 = new Talon(port2);
    }
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	t1.set(speed);
    	t2.set(-speed);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return(timeSinceInitialized()>timerDelay);
    }

    // Called once after isFinished returns true
    protected void end() {
    	t1.set(0);
    	t2.set(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	t1.set(0);
    	t2.set(0);
    }
}