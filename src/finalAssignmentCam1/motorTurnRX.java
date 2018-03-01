package finalAssignmentCam1;

import finalAssignmentCam1.camTest;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.subsumption.Behavior;

public class motorTurnRX extends Thread  {
	
	RegulatedMotor rightMotor ;
	camTest cam;
	
	
	public motorTurnRX (RegulatedMotor rm, camTest ca)
	{ this.rightMotor = rm;  this.cam = ca;}
	public void run() {
		rightMotor.setAcceleration(180);
		rightMotor.setSpeed(120);
		while(true){
			
			
			
			rightMotor.rotateTo(((int)camTest.offsetRedAvg/10)*10);
			if (Math.abs(camTest.offsetRedAvg) <= 10 || Math.abs(camTest.offsetRedAvg) >= 80){
				rightMotor.stop();
			}
			Thread.yield();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			} 
		}
	

}