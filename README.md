# Camcha.Android
## Prototype
### 2017.10.01
#### changed features
 * 안드로이드에 OpenCV를 연결 (Connected Android project with OpenCV)
 * 카메라에 흑백필터 적용 (Attached gray-scale filter to camera)
#### development note
 * 본래 코틀린으로 개발하려고 했으나 OpenCV 라이브러리를 열어보니 Kotlin을 지원하는 라이브러리 파일이 없었다. 이걸 직접 만들 자신은 없어서 그냥 자바를 이용해 개발하기로 했다.
![alt text][opencv_no_kotlin2]
 * 탐지기를 카메라 앞에 두고 렌즈에 비춰보니 흑백필터가 있으니까 좀 더 반사된 빛이 극명하게 보이는 듯했다.
![alt text][detection_with_gray_scale]


[opencv_no_kotlin2]: https://github.com/42deSix/Images/raw/master/dunnotodono/opencv_no_kotlin2.png "opencv_no_kotlin2"

[detection_with_gray_scale]: https://github.com/42deSix/Images/raw/master/dunnotodono//detection_with_gray_scale.png "detection_with_gray_scale"