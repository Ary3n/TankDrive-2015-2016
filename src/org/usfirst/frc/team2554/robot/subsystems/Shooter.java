package org.usfirst.frc.team2554.robot.subsystems;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Shooter extends Subsystem {
	Talon shootR, shootL, yAxis;
	Joystick controller;

	public Shooter(Joystick controller) {
		shootR = new Talon(4);
		shootL = new Talon(5);
		yAxis = new Talon(6);
		this.controller = controller;
	}

	public void initDefaultCommand() {
	}
	
	public void shoot() {
		
	}
}
