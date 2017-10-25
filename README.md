# Camcha.Android
## Prototype
### 2017.10.01
#### OpenCV Start
 * 안드로이드에 OpenCV를 연결 (Connected Android project with OpenCV)
 * 카메라에 흑백필터 적용 (Attached gray-scale filter to camera)
#### Development Note
 * 본래 코틀린으로 개발하려고 했으나 OpenCV 라이브러리를 열어보니 Kotlin을 지원하는 라이브러리 파일이 없었다. 이걸 직접 만들 자신은 없어서 그냥 자바를 이용해 개발하기로 했다.
![alt text][opencv_no_kotlin2]
 * 탐지기를 카메라 앞에 두고 렌즈에 비춰보니 흑백필터가 있으니까 좀 더 반사된 빛이 극명하게 보이는 듯했다.
![alt text][detection_with_gray_scale]

<img src="https://github.com/42deSix/Images/raw/master/dunnotodono/opencv_no_kotlin2.png" width="550px"/>

<img src="https://github.com/42deSix/Images/raw/master/dunnotodono//detection_with_gray_scale.png" height="350px"/>


### 2017.10.16
#### Snapshot
* 스냅샷 저장 기능 (Save Snapshot Feature)
	* 스냅샷을 찍으면 안드로이드 루트 디렉토리 하위에서 Camcha 디렉토리가 있는지 찾는다. (When you touch snapshot button, it check if there is Camcha directory under Android root directory.)
	* Camcha 디렉토리가 있으면 해당 경로에 스냅샷을 생성한다. (If there is Camcha directory, generate a snapshot there.)
	* Camcha 디렉토리가 없으면 Camcha 디렉토리를 생성 후 그 하위에 스냅샷을 생성한다. (If there is no Camcha directory, create the directory first, and then save a snapshot there.)
* 스냅샷 보기 기능 (View Snapshot Feature)
	* 탐지 뷰에서 스냅샷 저장 버튼의 우측에 있는 스냅샷 보기 버튼을 통해 저장된 스냅샷들을 열람할 수 있다. (You can see saved snapshots by touching a view-snapshot-button on the right side of save-snapshot-button in detection view.)
<img src="https://github.com/42deSix/Images/raw/master/camcha/view_snapshots1.png" width="350em"/>
#### Main Fragment & Menu
 * 메뉴의 버튼을 통해 프래그먼트를 교체한다.
	 * MainActivity에 RootFragment를 등록하고 RootFragment의 root_view란 id의 layout을 MainFragment, ReportFragment, SettingFragment 등으로 교체한다.
<img src="https://github.com/42deSix/Images/raw/master/camcha/menu1.png" width="350em"/>
<img src="https://github.com/42deSix/Images/raw/master/camcha/main1.png" width="350em"/>
#### Location Permission
 * 탐지 시작 전  다음처럼 사용자로부터 gps 사용권한을 받는다.
<img src="https://github.com/42deSix/Images/raw/master/camcha/gps_plz.png" width="350em"/>

### 2017.10.23
#### OpenCV - Find Brightest Spot
* 가장 밝은 지점을 찾는 OpenCV코드를 안드로이드에 탑재하였다.
<img src="https://github.com/42deSix/Images/raw/master/camcha/opencv_brightest_spot.png" width="350em"/>
#### Report
* 메뉴와 탐지액티비티 각각에 신고버튼을 만들어 신고기능으로 연결될 수 있게끔 했다.
* 신고방법을 선택 후 신고할 번호를 선택하게끔 했다.
<img src="https://github.com/42deSix/Images/raw/master/camcha/report_how1.png" width="350em"/>
<img src="https://github.com/42deSix/Images/raw/master/camcha/report_where1.png" width="350em"/>

#### Main Screen Renewal
* 메인화면에 글귀를 추가해보았다.
<img src="https://github.com/42deSix/Images/raw/master/camcha/main2.png" width="350em"/>
#### Map
* 탐지가 끝날 때마다 비동기적으로 위도/경도 등의 데이터를 json포맷으로 서버에 전송하게끔 하였다.
* 네이버지도를 띄우고 서버로부터 탐지데이터를 모두 긁어 마커로 표시하도록 하였다.
<img src="https://github.com/42deSix/Images/raw/master/camcha/map1.png" width="350em"/>



