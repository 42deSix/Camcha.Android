# Camcha.Android
## Prototype
### 2017.10.01
#### OpenCV Start
 * �ȵ���̵忡 OpenCV�� ���� (Connected Android project with OpenCV)
 * ī�޶� ������� ���� (Attached gray-scale filter to camera)
#### Development Note
 * ���� ��Ʋ������ �����Ϸ��� ������ OpenCV ���̺귯���� ����� Kotlin�� �����ϴ� ���̺귯�� ������ ������. �̰� ���� ���� �ڽ��� ��� �׳� �ڹٸ� �̿��� �����ϱ�� �ߴ�.
![alt text][opencv_no_kotlin2]
 * Ž���⸦ ī�޶� �տ� �ΰ� ��� ���纸�� ������Ͱ� �����ϱ� �� �� �ݻ�� ���� �ظ��ϰ� ���̴� ���ߴ�.
![alt text][detection_with_gray_scale]

<img src="https://github.com/42deSix/Images/raw/master/dunnotodono/opencv_no_kotlin2.png" width="550px"/>

<img src="https://github.com/42deSix/Images/raw/master/dunnotodono//detection_with_gray_scale.png" height="350px"/>


### 2017.10.16
#### Snapshot
* ������ ���� ��� (Save Snapshot Feature)
	* �������� ������ �ȵ���̵� ��Ʈ ���丮 �������� Camcha ���丮�� �ִ��� ã�´�. (When you touch snapshot button, it check if there is Camcha directory under Android root directory.)
	* Camcha ���丮�� ������ �ش� ��ο� �������� �����Ѵ�. (If there is Camcha directory, generate a snapshot there.)
	* Camcha ���丮�� ������ Camcha ���丮�� ���� �� �� ������ �������� �����Ѵ�. (If there is no Camcha directory, create the directory first, and then save a snapshot there.)
* ������ ���� ��� (View Snapshot Feature)
	* Ž�� �信�� ������ ���� ��ư�� ������ �ִ� ������ ���� ��ư�� ���� ����� ���������� ������ �� �ִ�. (You can see saved snapshots by touching a view-snapshot-button on the right side of save-snapshot-button in detection view.)
<img src="https://github.com/42deSix/Images/raw/master/camcha/view_snapshots1.png" width="350em"/>
#### Main Fragment & Menu
 * �޴��� ��ư�� ���� �����׸�Ʈ�� ��ü�Ѵ�.
	 * MainActivity�� RootFragment�� ����ϰ� RootFragment�� root_view�� id�� layout�� MainFragment, ReportFragment, SettingFragment ������ ��ü�Ѵ�.
<img src="https://github.com/42deSix/Images/raw/master/camcha/menu1.png" width="350em"/>
<img src="https://github.com/42deSix/Images/raw/master/camcha/main1.png" width="350em"/>
#### Location Permission
 * Ž�� ���� ��  ����ó�� ����ڷκ��� gps �������� �޴´�.
<img src="https://github.com/42deSix/Images/raw/master/camcha/gps_plz.png" width="350em"/>

### 2017.10.23
#### OpenCV - Find Brightest Spot
* ���� ���� ������ ã�� OpenCV�ڵ带 �ȵ���̵忡 ž���Ͽ���.
<img src="https://github.com/42deSix/Images/raw/master/camcha/opencv_brightest_spot.png" width="350em"/>
#### Report
* �޴��� Ž����Ƽ��Ƽ ������ �Ű��ư�� ����� �Ű������� ����� �� �ְԲ� �ߴ�.
* �Ű����� ���� �� �Ű��� ��ȣ�� �����ϰԲ� �ߴ�.
<img src="https://github.com/42deSix/Images/raw/master/camcha/report_how1.png" width="350em"/>
<img src="https://github.com/42deSix/Images/raw/master/camcha/report_where1.png" width="350em"/>

#### Main Screen Renewal
* ����ȭ�鿡 �۱͸� �߰��غ��Ҵ�.
<img src="https://github.com/42deSix/Images/raw/master/camcha/main2.png" width="350em"/>
#### Map
* Ž���� ���� ������ �񵿱������� ����/�浵 ���� �����͸� json�������� ������ �����ϰԲ� �Ͽ���.
* ���̹������� ���� �����κ��� Ž�������͸� ��� �ܾ� ��Ŀ�� ǥ���ϵ��� �Ͽ���.
<img src="https://github.com/42deSix/Images/raw/master/camcha/map1.png" width="350em"/>



