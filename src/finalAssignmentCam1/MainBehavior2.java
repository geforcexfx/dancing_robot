package finalAssignmentCam1;

import java.io.IOException;

import lejos.hardware.BrickFinder;
import lejos.hardware.Button;
import lejos.hardware.motor.EV3LargeRegulatedMotor;

import lejos.hardware.port.MotorPort;
import lejos.remote.ev3.RemoteRequestEV3;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class MainBehavior2 {

	
	public static void main(String[] args) {
		//create an array of 2 devices with the same name as 2 ev3, first index is host, second index is client 
	String[] names = {"EV3", "cuong"};
	RemoteRequestEV3 remoteReq;
	RegulatedMotor remoteMotor1=null, remoteMotor2=null;
	try {
		//try to connect to client ev3 with remoteRequestEV3 class
		//after that declare 2 motor in client based on Regulated motor class
		remoteReq = new RemoteRequestEV3(BrickFinder.find("cuong")[0].getIPAddress());
		remoteMotor1 = remoteReq.createRegulatedMotor("A", 'L');
		remoteMotor2 = remoteReq.createRegulatedMotor("B", 'L');
	} catch (IOException ex) {
		ex.printStackTrace();
	}
   
        RegulatedMotor rightMotor = new EV3LargeRegulatedMotor(MotorPort.A);
    	RegulatedMotor rightMotorY = new EV3LargeRegulatedMotor(MotorPort.C);
    	RegulatedMotor leftMotorY = new EV3LargeRegulatedMotor(MotorPort.D);
    	RegulatedMotor leftMotor = new EV3LargeRegulatedMotor(MotorPort.B);
    	
    	remoteMotor1.setAcceleration(120);
    	remoteMotor2.setAcceleration(120);
    	
    	remoteMotor1.rotate(120,true);
    	remoteMotor2.rotate(120,true);
       
    	camTest cam = new camTest();
    	
    	MotorTurnGX LeftMx = new MotorTurnGX(leftMotor, cam);
    	MotorTurnGY LeftMy = new MotorTurnGY(leftMotorY, cam);
    	motorTurnRX RightMx = new motorTurnRX(rightMotor, cam);
    	MotorTurnRY RightMy = new MotorTurnRY(rightMotorY, cam);
        
        Behavior b1 = new RemoteMotorStartG(remoteMotor1, remoteMotor2, cam);

        Behavior b2 = new RemoteMotorStop(remoteMotor1, remoteMotor2, cam);
       
        
        Behavior[] behav ={b1,b2};
        Arbitrator arby = new Arbitrator(behav);
        
        while(!Button.ESCAPE.isDown()){
			LeftMx.start();
			LeftMy.start();
			RightMx.start();
			RightMy.start();
			
			arby.go();
		}
        System.exit(1);

	}
}

	
