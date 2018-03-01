package finalAssignmentCam1;

import lejos.robotics.RegulatedMotor;
import lejos.robotics.subsumption.Behavior;

public class MotorTurnRY extends Thread {
	RegulatedMotor rightMotorY;
	camTest cam;
	public MotorTurnRY (RegulatedMotor rmy,  camTest ca)
	{ this.rightMotorY = rmy; this.cam = ca;}
	
	public void run(){
		rightMotorY.setAcceleration(180);
		rightMotorY.setSpeed(120);
		while(true){
			
			
			rightMotorY.rotateTo(((int)Math.abs(camTest.offsetRedAvgY)/10)*10);
			
			if(Math.abs(camTest.offsetRedAvgY) <= 10 || Math.abs(camTest.offsetRedAvgY) >= 80){
				rightMotorY.stop();
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
