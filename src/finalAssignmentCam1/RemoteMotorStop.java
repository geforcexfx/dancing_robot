package finalAssignmentCam1;

import lejos.robotics.RegulatedMotor;
import lejos.robotics.subsumption.Behavior;

public class RemoteMotorStop implements Behavior{
	RegulatedMotor remoteMotor1;
	RegulatedMotor remoteMotor2;
	
	camTest cam;
	public RemoteMotorStop(RegulatedMotor motor1,RegulatedMotor motor2 , camTest ca){
		this.remoteMotor1 = motor1; this.remoteMotor2 = motor2; this.cam = ca;
	}
	@Override
	public boolean takeControl() {
		// TODO Auto-generated method stub
		return (Math.abs(camTest.offsetgoXAvg)) < 15 ;
	}

	@Override
	public void action() {
		remoteMotor2.stop();
		remoteMotor1.stop();
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
