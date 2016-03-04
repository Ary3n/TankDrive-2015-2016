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
    CameraServer server, serverTwo;
    static double distance;
    final double DEADZONE = 0.18;
    
    public void initSmartBoard()
    {
    	autoChooser.addDefault("Under Low Bar", new DefaultAutonomous(myRobot));
    	autoChooser.addObject("Rough Terrain", new rtAutonomous());
        SmartDashboard.putData("Auto Command",autoChooser);  
        SmartDashboard.putNumber("Distance", distance);
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
        
        /*serverTwo = CameraServer.getInstance();
        serverTwo.setQuality(50); 
        serverTwo.startAutomaticCapture("cam1");*/
        initSmartBoard();
    }
    public void autonomous()
    {
    	autonomousCommand = (Command) autoChooser.getSelected();
    	autonomousCommand.start();
    	//Timer.delay(0.05);
    }
    /**
     * The motors using arcade steering
     */
    public void operatorControl() {
        myRobot.setSafetyEnabled(false);
        while (isOperatorControl() && isEnabled()) 
        {
        	double magnitude = -rightStick.getRawAxis(3) + 1;
        	myRobot.arcadeDrive( magnitude * -rightStick.getY(), magnitude * -rightStick.getZ() );
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
	        double ryAxisMag = controller.getRawAxis(5);
	        if( ryAxisMag <= DEADZONE && ryAxisMag >= -DEADZONE ) {
	        	armShooter.set(-0.2);
	        }
	        else if ( ryAxisMag > DEADZONE ) { //DOWN
	        	armShooter.set(-ryAxisMag/5.0 );
	        }
	        else { //UP
	        	armShooter.set(ryAxisMag/3.0);
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
            Timer.delay(0.001);		// wait for a motor update time
        }
   }
}

/**
*	CONTROLLER BINDS:
*	Left Y axis: armBar moves up/down
*	Right Y Axis: armShooter moves up/down
*	Left Trigger: Reverse Rev motors (suck in)
*	Right Trigger: Rev motors
*	Select: 
*/
