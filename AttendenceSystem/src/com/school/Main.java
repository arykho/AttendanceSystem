package com.school;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.objdetect.Objdetect;
import org.opencv.videoio.VideoCapture;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.opencv.core.Core;

public class Main
{
	final static int GRID_WIDTH = 4;
	final static int GRID_HEIGHT = 4;
	final static int GRID_SIZE = GRID_WIDTH*GRID_HEIGHT;

	public static String basePath = System.getProperty("user.dir");
	public static String classifierPath1 = basePath+"/resources/classifiers/haarcascade_frontalface_alt.xml";
	public static String trainingDataFile = basePath+"/resources/faces/training.txt";
	public static String nameMapDataFile = basePath+"/resources/faces/namemap.txt";

	static JLabel[] jlabels = new JLabel[GRID_SIZE];
	static JTextField[] textLabels = new JTextField[GRID_SIZE];
	static Mat[] faceImages = new Mat[GRID_SIZE];
	static volatile boolean snapped = false;
	static JButton snapButton = new JButton("Snap");
	static JButton saveButton = new JButton("Save");
	
	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		Mat frame = new Mat();
	    VideoCapture camera = new VideoCapture(0);
	    
	    JFrame jframe = new JFrame("Title");
	    jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    jframe.setPreferredSize(new Dimension(1000, 600));
	    JLabel vidpanel = new JLabel();
	    vidpanel.setPreferredSize(new Dimension(600, 300));
	    
	    Container basePane = jframe.getContentPane();
        basePane.add(vidpanel, BorderLayout.LINE_START);
         
        JPanel iconPanel = new JPanel();
        iconPanel.setLayout(new GridLayout(GRID_WIDTH, GRID_HEIGHT));
        snapButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				snapped = true;
			}
		});
        saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for(int i = 1; i < GRID_SIZE; ++i) {
					if (faceImages[i] != null && textLabels[i] != null) {
						String imageName = textLabels[i].getText();
						if (imageName != null && imageName.trim().length() > 0) {
							imageName = imageName.trim().toLowerCase();
							try {
								saveImageFile(imageName, faceImages[i]);
							} catch (Exception err) {
								err.printStackTrace();
							}
						}
					}
				}
				try {
					createTrainingList();
				} catch (Exception err) {
					err.printStackTrace();
				}
			}
		});
        
        JPanel cmdPanel = new JPanel();
        cmdPanel.setLayout(new BorderLayout());
        cmdPanel.add(snapButton, BorderLayout.NORTH);
        cmdPanel.add(saveButton, BorderLayout.SOUTH);
    	iconPanel.add(cmdPanel);
    	
        for(int i = 1; i < GRID_SIZE; ++i) {
        	JLabel l = new JLabel();
        	l.setPreferredSize(new Dimension(92, 112));
        	jlabels[i] = l;
            JPanel imagePanel = new JPanel();
            imagePanel.setLayout(new BorderLayout());
            imagePanel.add(l, BorderLayout.NORTH);
            textLabels[i] = new JTextField();
            imagePanel.add(textLabels[i], BorderLayout.SOUTH);
        	iconPanel.add(imagePanel);
        }
        
        basePane.add(iconPanel, BorderLayout.LINE_END);
         
        jframe.setVisible(true);
	    jframe.pack();

	    try {
	    while (true) {
	        if (camera.read(frame)) {

	        	if (snapped) {
	        		detectFace(frame, true);
	        		snapped = false;
	        	} else {
	        		detectFace(frame, false);
	        	}
				Image scaledImage = Mat2BufferedImage(frame).getScaledInstance(vidpanel.getWidth(),
	            		-1, Image.SCALE_FAST);
	            vidpanel.setIcon(new ImageIcon(scaledImage));
	            vidpanel.repaint();
	    	    //jframe.pack();

	        }
	    }
	    } catch(Exception e) {
	    	e.printStackTrace();
	    	};
	}
	
	public static void detectFace(Mat frame, boolean isSnapped) throws IOException
	{
		MatOfRect faces = new MatOfRect();
		Mat grayFrame = new Mat();
		int absoluteFaceSize=0;
		CascadeClassifier faceCascade=new CascadeClassifier();
		
		faceCascade.load(classifierPath1);
		Imgproc.cvtColor(frame, grayFrame, Imgproc.COLOR_BGR2GRAY);
		Imgproc.equalizeHist(grayFrame, grayFrame);
		
			int height = grayFrame.rows();
			if (Math.round(height * 0.2f) > 0)
			{
				absoluteFaceSize = Math.round(height * 0.01f);
			}
				
		faceCascade.detectMultiScale(grayFrame, faces, 1.1, 2, 0 | Objdetect.CASCADE_SCALE_IMAGE,
				new Size(absoluteFaceSize, absoluteFaceSize), new Size(height,height));
				
		Rect[] facesArray = faces.toArray();
		System.out.println("Number of faces detected = "+facesArray.length);
		for (int i = 0; i < facesArray.length; i++) {
			Imgproc.rectangle(frame, facesArray[i].tl(), facesArray[i].br(), new Scalar(0, 255, 0), 2);
			if (!isSnapped) {
				continue;
			}
			Rect rect = facesArray[i];
			Rect newRect = new Rect();
			if (rect.width*112/92 > rect.height) {
				newRect.width = rect.width;
				newRect.x = rect.x;
				newRect.height = rect.width * 112 / 92;
				newRect.y = rect.y - (newRect.height - rect.height)/2;
				if (newRect.y < 0) {
					newRect.y = 0;
				}
			} else {
				newRect.height = rect.height;
				newRect.y = rect.y;
				newRect.width = rect.height * 92 / 112;
				newRect.x = rect.x - (newRect.width - rect.width)/2;
				if (newRect.x < 0) {
					newRect.x = 0;
				}
			}
			Mat cropped = new Mat(grayFrame, newRect);
			Size sz = new Size(92,112);
			//Mat resized = new Mat();
			Imgproc.resize(cropped, cropped, sz);
			Image scaledImage = Mat2BufferedImage(cropped).getScaledInstance(jlabels[i+1].getWidth(),
            		-1, Image.SCALE_FAST);
			jlabels[i+1].setIcon(new ImageIcon(scaledImage));
			faceImages[i+1] = cropped;
		}
			
	}

	
    public static BufferedImage Mat2BufferedImage(Mat m){

     int type = BufferedImage.TYPE_BYTE_GRAY;
     if ( m.channels() > 1 ) {
         type = BufferedImage.TYPE_3BYTE_BGR;
     }
     int bufferSize = m.channels()*m.cols()*m.rows();
     byte [] b = new byte[bufferSize];
     m.get(0,0,b);
     BufferedImage image = new BufferedImage(m.cols(),m.rows(), type);
     final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
     System.arraycopy(b, 0, targetPixels, 0, b.length);  
     return image;
    }
    
	private static void saveImageFile(String imageName, Mat mat) {
		File dataDir = new File(basePath + "/resources/faces");
		if (!dataDir.exists()){
			dataDir.mkdir();
		}
		
		File nameDir = new File(dataDir, imageName);
		if (!nameDir.exists()) {
			nameDir.mkdir();
		}
		
		int seq = 1;
		File imgFile = null;
		while (seq < 100) {
			imgFile = new File(nameDir, "" + seq);
			if (imgFile.exists()) {
				seq++;
				continue;
			}
			break;
		}
		if (imgFile != null) {
			Imgcodecs.imwrite(imgFile.getAbsolutePath() + ".pgm", mat);
		}
		
	}
	
	private static void createTrainingList() throws Exception {
		List<String> faceNames = new ArrayList<String>();
		List<List<String>> faceFiles = new ArrayList<List<String>>();
		File dataDir = new File(basePath + "/resources/faces");
	    for (File faceDir : dataDir.listFiles()) {
	        if (!faceDir.isDirectory()) {
	        	continue;
	        }
    		faceNames.add(faceDir.getName());
    		List<String> imgFiles = new ArrayList<String>();
		    for (File imgFile : faceDir.listFiles()) {
		    	if (imgFile.getAbsolutePath().endsWith(".pgm")) {
		    		imgFiles.add(imgFile.getAbsolutePath());
		    	}
		    }
		    faceFiles.add(imgFiles);
	    }
	    
		File nameMapFile = new File(nameMapDataFile);
	    BufferedWriter nameMapWriter = new BufferedWriter(new FileWriter(nameMapFile));

	    File trainingFile = new File(trainingDataFile);
	    BufferedWriter imgPathWriter = new BufferedWriter(new FileWriter(trainingFile));

	    for(int i = 0; i < faceNames.size(); i++) {
	    	String faceName = faceNames.get(i);
	    	nameMapWriter.append(faceName + ";" + (i+1) + "\n");
	    	List<String> imgFiles = faceFiles.get(i);
	    	for(int f = 0; f < imgFiles.size(); f++) {
	    		String resourceDir = basePath + "/resources";
	    		imgPathWriter.append(imgFiles.get(i).substring(resourceDir.length()) + ";" + (i+1) + "\n");
	    	}
	    }
	    nameMapWriter.close();
	    imgPathWriter.close();

	}

}
