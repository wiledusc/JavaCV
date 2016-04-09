package faceTrackingVideo;

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

public class FaceTrackingVideo{

	public static final String XML_FILE = 
			"haarcascade_frontalface_default.xml";

	public static void main(String[] args){

		IplImage img_src = null, img_detected = null;
		img_detected = cvCreateImage(cvSize(640,480),8,1);
		
		CvCapture capture1 = cvCreateCameraCapture(CV_CAP_ANY);
		int i=1;
		while(i==1)
		{
			img_src = cvQueryFrame(capture1);
			if(img_src == null) break;
			img_detected = detect(img_src);
			cvShowImage("color",img_detected);
			
			char c = (char)cvWaitKey(15);
			if(c == 'q') break; 
		}
		cvReleaseCapture(capture1);

	}	

	public static IplImage detect(IplImage src){

		CvHaarClassifierCascade cascade = new 
				CvHaarClassifierCascade(cvLoad(XML_FILE));
		CvMemStorage storage = CvMemStorage.create();
		CvSeq sign = cvHaarDetectObjects(
				src,
				cascade,
				storage,
				1.5,
				3,
				CV_HAAR_DO_CANNY_PRUNING);

		cvClearMemStorage(storage);

		int total_Faces = sign.total();		

		for(int i = 0; i < total_Faces; i++){
			CvRect r = new CvRect(cvGetSeqElem(sign, i));
			cvRectangle (
					src,
					cvPoint(r.x(), r.y()),
					cvPoint(r.width() + r.x(), r.height() + r.y()),
					CvScalar.GREEN,
					1,
					CV_AA,
					0);

		}

		return src;
	}			
}