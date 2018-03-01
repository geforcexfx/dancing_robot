package finalAssignmentCam1;

import finalAssignmentCam1.camTest;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.subsumption.Behavior;

public class MotorTurnGX extends Thread {
	RegulatedMotor leftMotor ;
	camTest cam;
	
	
	public MotorTurnGX (RegulatedMotor lm, camTest ca)
	{ this.leftMotor = lm;  this.cam = ca;}
	public void run(){
	leftMotor.setSpeed(180);
	leftMotor.setAcceleration(180);
	
	while (true) {
			
			
			leftMotor.rotateTo(((int)Math.abs(camTest.offsetGreenAvg)/10)*10);
			if (Math.abs(camTest.offsetGreenAvg) <= 10 || Math.abs(camTest.offsetGreenAvg) >= 80){
				leftMotor.stop();
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

