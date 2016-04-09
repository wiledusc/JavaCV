package circleDetection;

import com.googlecode.javacpp.Loader;
import com.googlecode.javacv.*;
import com.googlecode.javacv.cpp.*;
import com.googlecode.javacv.cpp.opencv_core.CvPoint;
import com.googlecode.javacv.cpp.opencv_core.CvScalar;
import com.googlecode.javacv.cpp.opencv_core.CvSeq;
import com.googlecode.javacv.cpp.opencv_core.IplImage;
import com.googlecode.javacv.cpp.opencv_highgui.CvCapture;
import com.googlecode.javacv.cpp.opencv_imgproc.CvMoments;

import static com.googlecode.javacv.cpp.opencv_core.*;
import static com.googlecode.javacv.cpp.opencv_imgproc.*;
import static com.googlecode.javacv.cpp.opencv_calib3d.*;
import static com.googlecode.javacv.cpp.opencv_objdetect.*;
import static com.googlecode.javacv.cpp.opencv_highgui.*;

public class CircleDetection{
	 public static void main(String[] args){
	  IplImage src = cvLoadImage("circulos.png");
	  IplImage gray = cvCreateImage(cvGetSize(src), 8, 1);
	   
	  cvCvtColor(src, gray, CV_BGR2GRAY);  
	  cvSmooth(gray, gray, CV_GAUSSIAN, 3);
	   
	  CvMemStorage mem = CvMemStorage.create();
	   
	  CvSeq circles = cvHoughCircles( 
	    gray, //Input image
	    mem, //Memory Storage
	    CV_HOUGH_GRADIENT, //Detection method
	    1, //Inverse ratio
	    100, //Minimum distance between the centers of the detected circles
	    100, //Higher threshold for canny edge detector
	    100, //Threshold at the center detection stage
	    15, //min radius
	    500 //max radius
	    );
	   
	  for(int i = 0; i < circles.total(); i++){
	      CvPoint3D32f circle = new CvPoint3D32f(cvGetSeqElem(circles, i));
	      CvPoint center = cvPointFrom32f(new CvPoint2D32f(circle.x(), circle.y()));
	      int radius = Math.round(circle.z());      
	      cvCircle(src, center, radius, CvScalar.RED, 6, CV_AA, 0);    
	     }
	   
	  cvShowImage("Result",src);  
	  cvWaitKey(0);
	   
	 }
	}
