package finalAssignmentCam1;

import finalAssignmentCam1.camTest;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.subsumption.Behavior;

public class MotorTurnGY extends Thread {
	RegulatedMotor leftMotorY ;
	camTest cam;
	public MotorTurnGY(RegulatedMotor lmy, camTest ca){this.cam = ca; this.leftMotorY = lmy;}
	
	public void run() {
		leftMotorY.setAcceleration(120);
		leftMotorY.setSpeed(120);
		while(true){
			
			
			leftMotorY.rotateTo(-((int)camTest.offsetGreenAvgY/10)*10);
			
			if(Math.abs(camTest.offsetGreenAvgY) <= 10 || Math.abs(camTest.offsetGreenAvgY) >= 80){
				leftMotorY.stop();
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


