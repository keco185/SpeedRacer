/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

/**
 *
 * @author Team484
 */
public abstract class SimpleCommand implements CommandBase{
    
    public void run(){
        init();
        method();
        exit();
    }
    public abstract void init();
    
    public abstract void method();
    
    public abstract void exit();
}
