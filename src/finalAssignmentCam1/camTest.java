package finalAssignmentCam1;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import lejos.hardware.BrickFinder;
import lejos.hardware.Button;
import lejos.hardware.device.NXTCam;
import lejos.hardware.ev3.EV3;
import lejos.hardware.lcd.TextLCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.geometry.Rectangle2D;

public class camTest extends Thread {
	public static int offsetRed = 1;
	public static int offsetGreen = 1;
	public static int offsetRedy;
	public static int offsetGreeny = 1;
	final static int INTERVAL = 500; // milliseconds
	public static byte[] buf = new byte[1];
	public static int offsetGreenAvg = 1;
	public static int offsetGreenAvgY = 1;
	public static int offsetRedAvg = 1;
	public static int offsetRedAvgY = 1;
	public static int offsetgoX = 1;
	public static int offsetgoXAvg = 1;

	public camTest() {
		this.setDaemon(true);
		this.start();
	}

	public void run() {
		NXTCam camera = new NXTCam(SensorPort.S2);//Instantiate camera object that use SensorPort S2
		camera.enableTracking(true);
		boolean has_G = false, has_R = false, has_C = false;
		int numObjects = 0;

		camera.sendCommand('A'); // sort objects by size
		camera.sendCommand('E'); // start tracking

		final EV3 ev3 = (EV3) BrickFinder.getLocal();
		TextLCD lcd = ev3.getTextLCD();
		//Create queues to make color filter
		Queue<Integer> offsetGr = new LinkedList<Integer>();
		for (int i = 0; i < 3; i++) {
			offsetGr.add(1);
		}
		Queue<Integer> offsetGrY = new LinkedList<Integer>();
		for (int i = 0; i < 3; i++) {
			offsetGrY.add(1);
		}
		Queue<Integer> offsetR = new LinkedList<Integer>();
		for (int i = 0; i < 3; i++) {
			offsetR.add(1);
		}
		Queue<Integer> offsetRY = new LinkedList<Integer>();
		for (int i = 0; i < 3; i++) {
			offsetRY.add(1);
		}
		Queue<Integer> offsetX = new LinkedList<Integer>();
		for (int i = 0; i < 3; i++) {
			offsetX.add(1);
		}
	

		while (true) {
			numObjects = camera.getNumberOfObjects();
			lcd.clear();
			lcd.drawString("Objects: " + numObjects, 0, 1);
			
			int bestIdG = 0;
			int bestIdR = 0;
			int bestIdGy = 0;
			int bestIdRy = 0;
			int bestIdX = 0;
			int bestDistXR = 999999;
			int bestDistXG = 999999;
			int bestDistYR = 999999;
			int bestDistYG = 999999;
			int bestDistX = 999999;
			double X;
			double Y;
			double Z;
			double A;
			double B;
			double C;
			// I set the offset at the center of the camera frame(width is 166/2
			// = 88 then if the camera see the color based on its color number
			// from the camera
			// if new distance is smaller than best distance so it will save the new distance.
			for (int id = 0; id < numObjects; id++) {
				Rectangle2D r = camera.getRectangle(id);
				int dist = (int) Math.abs(88 - r.getX() + r.getWidth() / 2);
				int colorNumber = camera.getObjectColor(id);
				//colorNumber 0 means green
				if (colorNumber == 0) {
					if (dist < bestDistXG) {
						bestIdG = id;   //which index number 
						bestDistXG = dist;// this is the best distance
						has_G = true;//there is green ball in the scene
					}
				} else if (colorNumber == 1) {
					if (dist < bestDistXR) {
						bestIdR = id;
						bestDistXR = dist;
						has_R = true;
					}
				} else if (colorNumber == 2) {
					if (dist < bestDistX) {
						bestIdX = id;
						bestDistX = dist;
						has_C = true;
					}
				}
			}

			for (int id = 0; id < numObjects; id++) {
				Rectangle2D ry = camera.getRectangle(id);
				int disty = (int) Math.abs(77 - ry.getY() + ry.getHeight() / 2);
				int colorNumber = camera.getObjectColor(id);
				if (colorNumber == 0 && has_G) {
					if (disty < bestDistYG) {
						bestIdGy = id;
						bestDistYG = disty;
					}
				} else if (colorNumber == 1 && has_R) {
					if (disty < bestDistYR) {
						bestIdRy = id;
						bestDistYR = disty;
					}

				}
			}
			//calculate the center of each rectangle
			Rectangle2D best = camera.getRectangle(bestIdR);
			offsetRed = (int) (best.getX() - 88);
			Rectangle2D best1 = camera.getRectangle(bestIdG);
			offsetGreen = (int) (best1.getX() - 88);
			Rectangle2D best2 = camera.getRectangle(bestIdRy);
			offsetRedy = (int) (best2.getY() - 77);
			Rectangle2D best3 = camera.getRectangle(bestIdGy);
			offsetGreeny = (int) (best3.getY() - 77);
			Rectangle2D best4 = camera.getRectangle(bestIdX);
			offsetgoX = (int) (best4.getX() - 88);
			// add offsetGreen in queue only if it can see the green color
			if (has_G) {
				has_G = false;
				offsetGr.add(offsetGreen);
				offsetGr.remove();
				Iterator<Integer> it = offsetGr.iterator();
				Z = 0;
				while (it.hasNext()) {
					Z += it.next();
				}
				Z /= offsetGr.size();
				offsetGreenAvg = (int) Z;
				// add offsetGreeny in queue
				offsetGrY.add(offsetGreeny);
				offsetGrY.remove();
				Iterator<Integer> itY = offsetGrY.iterator();
				A = 0;
				while (itY.hasNext()) {
					A += itY.next();
				}
				A /= offsetGrY.size();
				offsetGreenAvgY = (int) A;
			}

			// add offsetRed in queue
			if (has_R) {
				has_R = false;
				offsetR.add(offsetRed);
				offsetR.remove();

				Iterator<Integer> itR = offsetR.iterator();
				B = 0;
				while (itR.hasNext()) {
					B += itR.next();
				}
				B /= offsetR.size();
				offsetRedAvg = (int) B;
				// add offsetRedy in queue
				offsetRY.add(offsetRedy);
				offsetRY.remove();
				Iterator<Integer> itRY = offsetRY.iterator();
				C = 0;
				while (itRY.hasNext()) {
					C += itRY.next();
				}
				C /= offsetR.size();
				offsetRedAvgY = (int) C;
			}
			if (has_C) {
				has_C = false;
				offsetX.add(offsetgoX);
				offsetX.remove();

				Iterator<Integer> itC = offsetX.iterator();
				Y = 0;
				while (itC.hasNext()) {
					Y += itC.next();
				}
				Y /= offsetX.size();
				offsetgoXAvg = (int) Y;
				// add offsetRedy in queue

			}
			lcd.drawString("offsetred: " + offsetRedAvg, 0, 4);
			lcd.drawString("offsetgreen: " + offsetGreenAvg, 0, 5);
			lcd.drawString("offsetGo: " + offsetgoXAvg, 0, 6);

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
