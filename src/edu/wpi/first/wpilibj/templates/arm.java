/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 * @author Team484
 */
public class arm extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        OpenClaw OC = new OpenClaw();
        OC.execute();
    }
}