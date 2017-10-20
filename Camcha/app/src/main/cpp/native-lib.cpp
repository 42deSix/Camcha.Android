#include <jni.h>
#include <opencv2/core/core.hpp>
#include <opencv2/imgproc/imgproc.hpp>
#include <iostream>

using namespace cv;

extern "C" {
    JNIEXPORT void JNICALL

    Java_com_softmilktea_camcha_MainActivity_ConvertRGBtoGray(
            JNIEnv *env, jobject /* this */,
            jlong matAddrInput, jlong matAddrResult) {

        Point minLoc,maxLoc;
        double minVal,maxVal;

        Mat &matInput = *(Mat *)matAddrInput;
        Mat &matResult = *(Mat *)matAddrResult;
        matResult = matInput;
//        Mat &matResult = *(Mat *)matAddrInput;
        cvtColor(matInput, matInput, COLOR_BGR2GRAY);



//        GaussianBlur(matInput, matResult, Size(7,7), 1.5, 1.5);
        
        minMaxLoc(matInput, &minVal,&maxVal,&minLoc,&maxLoc);
        circle(matResult, maxLoc, 50, 1000, 5);

    }
}
