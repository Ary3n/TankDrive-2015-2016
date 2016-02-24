package org.usfirst.frc.team2554.robot;


import org.usfirst.frc.team2554.robot.commands.AutoAim;
import org.usfirst.frc.team2554.robot.commands.DefaultAutonomous;
import org.usfirst.frc.team2554.robot.commands.rtAutonomous;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.AnalogInput;
public class Robot extends SampleRobot {
    RobotDrive myRobot;  // class that handles basic drive operations
    RobotDrive shooter;
    
    // Two drivers necessary!
    
    /*
     	PORT 0 MUST BE JOYSTICK OR THINGS WON'T WORK
    	PORT 1 MUST BE CONTROLLER OR THINGS WON'T WORK
    	CONTROLLER MUST BE SET TO XINPUT
    	CONTROLLER BINDS @ EOF
     */
    AnalogInput distanceSensor;
    Joystick rightStick, controller; // set CONTROLLER to ID 1 in DriverStation
    JoystickButton launchButton, autoAimButton;
    SendableChooser autoChooser;
    Command autonomousCommand;
    Victor armBar,armShooter;
    Spark launcher,roller;
    Relay extension;
    CameraServer server;
    double distance;
    final double DEADZONE = 0.10;
    
    public void initSmartBoard()
    {
    	autoChooser.addDefault("Under Low Bar", new DefaultAutonomous(myRobot));
    	autoChooser.addObject("Rough Terrain", new rtAutonomous());
        SmartDashboard.putData("Auto Command",autoChooser);  
        SmartDashboard.putDouble("Distance", distance);
        }
    
    public Robot() {
        myRobot = new RobotDrive(3,1); //FrontLeft, BackLeft, FrontRight, BackRight 
        shooter = new RobotDrive(4,5);
        armBar = new Victor(6);
        armShooter = new Victor(7);
        launcher = new Spark(8);
        roller = new Spark(2);
        extension = new Relay(0);
        myRobot.setExpiration(0.1);
        rightStick = new Joystick(0);
        controller = new Joystick(1);
        launchButton = new JoystickButton(controller, 6);
        autoAimButton = new JoystickButton(controller,5);
        distanceSensor = new AnalogInput(1);
        autoChooser = new SendableChooser();
        distance = 0;

        server = CameraServer.getInstance();
        server.setQuality(50);
        server.startAutomaticCapture("cam0");
        initSmartBoard();
    }
    public void autonomous()
    {
    	autonomousCommand = (Command) autoChooser.getSelected();
    	autonomousCommand.start();
    	Timer.delay(0.05);
    }
    /**
     * The motors using arcade steering
     */
    public void operatorControl() {
        myRobot.setSafetyEnabled(true);
        while (isOperatorControl() && isEnabled()) 
        {
        	myRobot.arcadeDrive( -rightStick.getY(), -rightStick.getZ() );			
        	//myRobot.arcadeDrive(rightStick, 1,rightStick,2);
        	//Set speed of each arm based on y-axis of each joystick on controller
        		//1 is L Y Axis
        		//5 is R Y Axis
	        if(controller.getRawAxis(1) <= DEADZONE && controller.getRawAxis(1) >= -DEADZONE) {
	        	armBar.set(-0.08);
	        }
	        else {
	        	armBar.set(controller.getRawAxis(1)/3.0);
	        }
	        	
	        
	        // R Y axis DEADZONE set at instance create
	        if( controller.getRawAxis(5) <= DEADZONE && controller.getRawAxis(5) >= -DEADZONE ) {
	        	armShooter.set(-0.15);
	        }
	        else {
	        	armShooter.set(controller.getRawAxis(5)/2.0);
	        }
	        
	        //Shooter System NOTE: Not a subsystem. THIS WORKS!
	        //3 is Right Trigger
	        if( controller.getRawAxis(3) > 0.1 )
	        	shooter.arcadeDrive(0,-controller.getRawAxis(3)); 					
	        //Collector; 2 is left trigger
	        if( controller.getRawAxis(2) > 0.1 ) {
	        	shooter.arcadeDrive(0,controller.getRawAxis(2));
	        	roller.set(0.4);
	        } else {
	        	roller.set(0.0);
	        }
	        
	        if( controller.getRawAxis(2) > 0.1 && controller.getRawAxis(3) > 0.1 ) {
	        	shooter.arcadeDrive(0,0);
	        	roller.set(0);
	        }
	        
	        // 6 is left bumper?
	        if (controller.getRawButton(6)) {
	        	launcher.set(-1);
	        } else {
	        	launcher.set(1);
	        }
	        autoAimButton.whileHeld(new AutoAim(myRobot,armShooter));
	        
	        // 1 is A
            if( controller.getRawButton(1) ) {
            	extension.set(Value.kReverse);
            } else if( controller.getRawButton(2) ) {
            	extension.set(Value.kForward);
            } else {
            	extension.set(Value.kOff);
            }
	        distance = distanceSensor.getValue();
            Timer.delay(0.005);		// wait for a motor update time
        }
   }
}

/**
*	CONTROLLER BINDS:
*	Left Y axis: armBar moves up/down
*	Right Y Axis: armShooter moves up/down
*	Left Trigger: Reverse Rev motors (suck in) ‎( ͡° ͜ʖ ͡°)
*	Right Trigger: Rev motors
*	Select: 
*/
/*
 Warning  44003  FRC:  No robot code is currently running.  Driver Station 
 Java HotSpot(TM) Embedded Client VM warning: INFO: os::commit_memory(0xb2ea3000, 22814720, 0) failed; error='Cannot allocate memory' (errno=12) 
 # There is insufficient memory for the Java Runtime Environment to continue. 
 # An error report file with more information is saved as: 
 âž” Launching Â«'/usr/local/frc/JRE/bin/java' '-jar' '/home/lvuser/FRCUserProgram.jar'Â» 
 NT: server: client CONNECTED: 10.25.54.158 port 55973 
 Camera not yet ready, awaiting image 
 Default disabled() method running, consider providing your own 
 # 
 # /tmp/hs_err_pid2729.log 
 */