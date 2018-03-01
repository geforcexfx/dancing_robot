package finalAssignmentCam1;

import finalAssignmentCam1.camTest;
import lejos.hardware.Brick;
import lejos.hardware.BrickFinder;
import lejos.hardware.Button;
import lejos.hardware.LED;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.MotorPort;
import lejos.remote.ev3.RemoteEV3;
import lejos.remote.ev3.RemoteRequestEV3;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;
 
public class RemoteMotorStartG implements Behavior {
	RegulatedMotor remoteMotor1;
	RegulatedMotor remoteMotor2;
	camTest cam;
	public RemoteMotorStartG(RegulatedMotor motor1,RegulatedMotor motor2 , camTest ca){
		this.remoteMotor1 = motor1; this.remoteMotor2 = motor2; this.cam = ca;
	}
	
        
    public void run(){
    	remoteMotor1.setSpeed(120);
    	remoteMotor2.setSpeed(120);
    }

	@Override
	public boolean takeControl() {
		// TODO Auto-generated method stub
		return (Math.abs(camTest.offsetgoXAvg)) > 15;
	}

	@Override
	public void action() {
		if(camTest.offsetgoXAvg >= 15){
		remoteMotor1.rotate(10, true);
		remoteMotor2.rotate(10, true);
		}
		else if(camTest.offsetgoXAvg <= 15){
			remoteMotor1.rotate(-10, true);
			remoteMotor2.rotate(-10, true);
		}
	      Thread.yield();
          try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void suppress() {
		// TODO Auto-generated method stub
		
	} 
}
