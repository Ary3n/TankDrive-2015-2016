package org.usfirst.frc.team2554.robot;

import edu.wpi.first.wpilibj.image.*;
import edu.wpi.first.wpilibj.vision.*;
import org.usfirst.frc.team2554.robot.Robot;
import java.lang.Math;

public class Camera {
	//Limits for the HSL Threshold
	final static int hueLow = 0;
	final static int hueHigh = 0;
	final static int saturationLow = 0;
	final static int saturationHigh = 0;
	final static int luminenceLow = 0;
	final static int luminenceHigh = 0;
	//Values for Analysis
	static double particleArea, boundArea, boundHeight, boundWidth;
	//Best report
	static int bestReportNum;
	//How close the Values are to desired ones. Goes up linearly. Best close to 0.
	static double areaComp, lengthComp, bestSumComp;
	static AxisCamera axisCam;
	static HSLImage originalImage;
	static BinaryImage maskedImage, erodedImage;
	//Number of Erosions
	final static  int erosions = 3;
	static ParticleAnalysisReport originalreport[];
	//The ideal shot point
	static int xCoord, yCoord, acceptableRegion, yAdjustment;
	public Camera()
	{
	}
	public static void doStuff() throws NIVisionException
	{
		bestSumComp = 99999999999999.0;
		yAdjustment = 40;
		xCoord = 320;
		yCoord = 240 + yAdjustment; 
		axisCam = new AxisCamera("10.25.54.11");
		axisCam.getImage(originalImage);
		//Masks Image so only stuff over a certain threshold shows up.
		maskedImage = originalImage.thresholdHSL(hueLow, hueHigh, saturationLow, saturationHigh, luminenceLow, luminenceHigh);
		//Erodes the image so the smaller objects disappear. Decreases size of the array of Particle Analysis Reports
		erodedImage = maskedImage.removeSmallObjects(false, erosions);
		//Gets reports for all of the calculated particles
		originalreport = erodedImage.getOrderedParticleAnalysisReports();
		//Runs through to generate the best report
		for(int i = 0; i <originalreport.length;i ++)
		{
			particleArea = originalreport[i].particleArea;
			boundHeight = originalreport[i].boundingRectHeight;
			boundWidth = originalreport[i].boundingRectWidth;
			boundArea = boundHeight * boundWidth;
			areaComp = Math.abs(1/3.0-particleArea/boundArea);
			lengthComp = Math.abs(1.6-boundWidth/boundHeight);
			//Finds best report/lowest SumComp
			if((areaComp+lengthComp) < bestSumComp)
				bestReportNum = i;
		}
	}
}
