package HSV4segmentos;

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

	public class HSV4segmentos {

		public static void main(String[] args) {
			
			IplImage imgOriginal, imgHSV;
			
			imgOriginal = cvLoadImage("face.jpg");
			imgHSV = cvCreateImage(imgOriginal.cvSize(),8,3);
			
			cvCvtColor(imgOriginal, imgHSV, CV_BGR2HSV);
			cvShowImage("Original", imgOriginal);
			cvShowImage("HSV", imgHSV);
			
			
			int i=0, j=0, quadrante=0, histograma[][] = new int[4][256];
			long soma[] = new long[4];
			
			for(i=0; i < imgHSV.height() ; i++){
                for(j=0; j < imgHSV.width() ; j++){
                	CvScalar v= cvGet2D(imgHSV, i, j);

                    if(i <= imgHSV.height()/2 && j <= imgHSV.width()/2){
                        quadrante=0;
                    }
                    if(i <= imgHSV.height()/2 && j > imgHSV.width()/2){
                        quadrante=1;
                    }
                    if(i > imgHSV.height()/2 && j > imgHSV.width()/2){
                        quadrante=2;
                    }
                    if(i > imgHSV.height()/2 && j <= imgHSV.width()/2){
                        quadrante=3;
                    }
                    //System.out.println("R=> "+(int)v.val(0)+" G=> "+ (int)v.val(1)+" B=> "+ (int)v.val(2));
                    histograma[quadrante][(int)v.val(0)]++;
                    histograma[quadrante][(int)v.val(1)]++;
                    histograma[quadrante][(int)v.val(2)]++;
                }
            }
			for(i=0; i<256; i++){
				soma[0] += histograma[0][i];
				soma[1] += histograma[1][i];
				soma[2] += histograma[2][i];
				soma[3] += histograma[3][i];
				//System.out.println("Primeiro Quadrante: "+ histograma[0][i]);
			}
			System.out.println("Quadrante 1: "+ soma[0]);
			System.out.println("Quadrante 2: "+ soma[1]);
			System.out.println("Quadrante 3: "+ soma[2]);
			System.out.println("Quadrante 4: "+ soma[3]);
			cvWaitKey();
		}

}
