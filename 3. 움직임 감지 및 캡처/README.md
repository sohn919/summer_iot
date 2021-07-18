### 움직임 감지

+ 움직임을 감지하여 움직임이 감지되면 캡처를 합니다.

### 소스 코드

+ 움직임을 감지할 영상을 지정해 줍니다. (실시간으로 스트림 하는 영상을 사용하였습니다.)

      cap = cv2.VideoCapture("http://0.0.0.0:8090/?action=stream")
#

+ 움직임을 감지할 영상을 지정해 줍니다. (실시간으로 스트림 하는 영상을 사용하였습니다.)

      try:
          if not os.path.exists('/var/lib/tomcat9/webapps/ROOT/recources/%d' % count2):
              os.mkdir('/var/lib/tomcat9/webapps/ROOT/recources/%d' % count2)
          except OSError:
              continue
#

### 움직임 감지 결과

+ opencv를 통해 초록색 박스로 움직임이 감지된 것을 확인 할 수 있습니다.

![KakaoTalk_20210718_235513166](https://user-images.githubusercontent.com/77609451/126071909-ecbe8fc0-f2a2-4aff-889f-727293e1094a.jpg)
