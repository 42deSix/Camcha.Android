#include <jni.h>
#include <opencv2/opencv.hpp>

using namespace cv;

extern "C" {
    JNIEXPORT void JNICALL

    Point minLoc,maxLoc;
    double minVal,maxVal;

    Java_com_softmilktea_camcha_MainActivity_ConvertRGBtoGray(
            JNIEnv *env, jobject /* this */,
            jlong matAddrInput, jlong matAddrResult) {

        Mat &matInput = *(Mat *)matAddrInput;
        Mat &matResult = *(Mat *)matAddrResult;

        cvtColor(matInput, matResult, COLOR_BGR2GRAY);



        GaussianBlur(matResult, matResult, Size(7,7), 1.5, 1.5);
        
        minMaxLoc(matResult, &minVal,&maxVal,&minLoc,&maxLoc,noArray());
        circle(matResult, maxLoc, 10, 1000);
        
    }
}
