package com.school;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import java.nio.IntBuffer;

import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.MatVector;
import org.bytedeco.opencv.opencv_face.EigenFaceRecognizer;
import org.bytedeco.opencv.opencv_face.FisherFaceRecognizer;
import org.bytedeco.opencv.opencv_face.LBPHFaceRecognizer;
import org.bytedeco.opencv.global.opencv_imgcodecs;

import static org.bytedeco.opencv.global.opencv_core.*;

public class FaceRecognition {
	
	String trainingDataFile;
	String nameMapDataFile;
	EigenFaceRecognizer efr;
	LBPHFaceRecognizer lfr;
	FisherFaceRecognizer ffr;
	Map<Integer, String> nameMap = new HashMap<Integer, String>();
	
	int count = 1;

	
	public FaceRecognition(String trainingDataFile, String nameMapDataFile) {

		this.trainingDataFile = trainingDataFile;
		this.nameMapDataFile = nameMapDataFile;
	}
	
	public void train() {
		ArrayList<Mat> images = new ArrayList<>();
		ArrayList<Integer> labels = new ArrayList<>();
		readTrainingData(trainingDataFile, images, labels);
		
		MatVector matImages = new MatVector(images.size());
		Mat matLabels = new Mat(images.size(), 1, CV_32SC1);
        IntBuffer labelsBuf = matLabels.createBuffer();
		
        for(int i = 0; i < images.size(); ++i) {
        	matImages.put(i, images.get(i));
        	labelsBuf.put(i, labels.get(i));
        }
        
		efr = EigenFaceRecognizer.create();
		lfr = LBPHFaceRecognizer.create();
		ffr = FisherFaceRecognizer.create();
		
		System.out.println("Starting training on " + images.size() + " data points ...");
		
		efr.train(matImages, matLabels);
		lfr.train(matImages, matLabels);
		ffr.train(matImages, matLabels);

		System.out.println("Starting completed...");
		readNameMapData();
	}
	
	public String predict(Mat mat) {
		int[] outLabel=new int[1];
		double[] outConf=new double[1];
		//System.out.print("found: ");
		
		efr.predict(mat, outLabel, outConf);
		String efrName = nameMap.get(outLabel[0]);
		//System.out.print(" E=" + efrName);

		ffr.predict(mat, outLabel, outConf);
		String ffrName = nameMap.get(outLabel[0]);
		//System.out.print(" F=" + efrName);

		lfr.predict(mat, outLabel, outConf);
		String lfrName = nameMap.get(outLabel[0]);
		//System.out.print(" L=" + efrName);
		
		if (efrName != null && efrName.contentEquals(ffrName) && efrName.equals(lfrName)) {
			System.out.println("Found: " + efrName + "    " + count++);
		}

		return nameMap.get(outLabel[0]);
	}

	private void readTrainingData(String trainingDataFile, ArrayList<Mat> images, ArrayList<Integer> labels)  {
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(trainingDataFile));
		
		String line;
		while((line=br.readLine())!=null){
			String[] tokens = line.split("\\;");
			String imgPath = Main.dataDir + tokens[0];
			Mat readImage = opencv_imgcodecs.imread(imgPath, 0);
			images.add(readImage);
			labels.add(Integer.parseInt(tokens[1]));
		}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private void readNameMapData()  {
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(nameMapDataFile));
		
			String line;
			while((line=br.readLine())!=null){
				String[] tokens=line.split("\\;");
				if (tokens.length == 2) {
					Integer id = new Integer(tokens[1]);
					nameMap.put(id, tokens[0]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
