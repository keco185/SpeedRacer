package edu.wpi.first.wpilibj.templates;


import com.sun.squawk.util.MathUtils;
import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
//Hello World!
public class Robot extends IterativeRobot {
    Command OpenClaw;
    RobotDrive chassis;
    Joystick leftStick;
    Joystick rightStick;
    Victor PWM3;
    Victor PWM4;
    AnalogChannel A1;
    AnalogChannel ir;
    AnalogChannel ir2;
    AnalogChannel pot;
    DigitalInput cs;
    boolean attack = true;
    public void robotInit() {
        chassis = new RobotDrive(1, 2);
        leftStick = new Joystick(1);
        rightStick = new Joystick(2);
        PWM3 = new Victor(3);
        PWM4 = new Victor(4);
        A1 = new AnalogChannel(1);
        ir = new AnalogChannel(2);
        ir2 = new AnalogChannel(3);
        cs = new DigitalInput(1);
        pot = new AnalogChannel(4);
    }

    public void autonomousPeriodic() {
        chassis.setSafetyEnabled(false);
        new ExampleSimpleCommand().run();
    }

    public void testPeriodic() {
        
    }
    
    public void teleopPeriodic() {

        chassis.setSafetyEnabled(true);
        while (isOperatorControl() && isEnabled()) {
            PWM3.set(rightStick.getY());
            PWM4.set(rightStick.getX());
            if (A1.getValue() > 40) {
                PWM3.set(-0.4);
                if (rightStick.getX() < -0.4) {
                    PWM3.set(rightStick.getX());
                }
            } else if(A1.getValue() < 13.5){
                PWM3.set(0.4);
                if (rightStick.getX() > 0.4) {
                    PWM3.set(rightStick.getX());
                }
            }
            double A12 = (A1.getValue()-25);
            double A13 = (A12/30);
            double rotate;
            PWM3.set(PWM3.get()-A13);
            //System.out.println(A13 + " " + A1.getValue());
            //System.out.println(ir.getValue());
            int leftVal = ir2.getValue();
            int rightVal = ir.getValue();
            
            System.out.println(pot.getValue());
            //System.out.println("Inches: " + (MathUtils.pow(ir.getValue(), -1.2224)*6627.6));
            double left = (MathUtils.pow(leftVal, -1.22)*6627);
            double right = (MathUtils.pow(rightVal, -1.22)*6627);
            //System.out.println("left: " + left + " right: " + right);
            double avg = (left + right) / 2;
            double min = 36;
            //System.out.println("lraw: " + ir2.getValue() + " rraw: " + ir.getValue() + " l: " + left + " r: " + right + " " + cs.get());
            //System.out.println("avg val: " + ir.getAverageValue() + " avg vol: " + ir.getAverageVoltage() + " vol: " + ir.getVoltage());
            if (cs.get() && leftStick.getTrigger() && attack) {
                if (left - 1 > right) {
                    System.out.println("Ball is too far to the right");
                    
                    if (left - 12 > right) {
                        rotate = 0.65;
                    } else {
                        rotate = 0.02*(left - right) + 0.5;
                    }
                    min = right;
                    //chassis.arcadeDrive(0, 0.7);
                } else if (left + 1 < right) {
                    System.out.println("Ball is too far to the left");
                    //chassis.arcadeDrive(0, -0.7);
                    if (left + 12 < right) {
                        rotate = -0.65;
                    } else {
                        rotate = (-(0.02*(right - left) + 0.5));
                    }
                    min = left;
                } else {
                    //chassis.arcadeDrive(leftStick);
                    rotate = 0;
                }
                if (left < 48 || right < 48) {
                    chassis.arcadeDrive(-(0.0055*min+0.3786), rotate);
                } else {
                    System.out.println(left + ", " + right);
                    chassis.arcadeDrive(leftStick);
                }
            } else {
                chassis.arcadeDrive(leftStick);
            }
            if (!leftStick.getTrigger()) {
                attack = true;
            }
            if (!cs.get()) {
                attack = false;
            }
            Timer.delay(0.01);
      }
    }
    
}
