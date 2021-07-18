### 움직임 감지

+ 움직임을 감지하여 움직임이 감지되면 캡처를 합니다.

### 소스 코드

+ 움직임을 감지할 영상을 지정해 줍니다. (실시간으로 스트림 하는 영상을 사용하였습니다.)

      cap = cv2.VideoCapture("http://0.0.0.0:8090/?action=stream")
#

+ 움직임이 감지되면 지정된 경로에 폴더를 만들어 주고 움직이 감지된 시간을 체크합니다.

      if diff_cnt > max_diff:
            try:
                  if not os.path.exists('/var/lib/tomcat9/webapps/ROOT/recources/%d' % count2):
                  os.mkdir('/var/lib/tomcat9/webapps/ROOT/recources/%d' % count2)
            except OSError:
                  continue
          
            start = time.time()
#

+ 움직임이 감지되면 지정된 경로에 캡처한 파일을 저장시켜줍니다.

      cv2.imwrite("/var/lib/tomcat9/webapps/ROOT/recources/%d/motion%d.jpg" % (count2, count), draw)
      count += 1
#

+ 움직임이 감지되지 않는다면 움직임이 감지되었던 시간과 비교하여 움직임이 감지되지 않았던 시간이 5초가 된다면 다음 폴더를 만들어 주기 위해 count2를 +1 시켜주고 1초 대기합니다.

      else:
           if(int(time.time() - start) == 5):
               count2 += 1
               count = 0
               time.sleep(1)
#

### 움직임 감지 결과

+ opencv를 통해 초록색 박스로 움직임이 감지된 것을 확인 할 수 있습니다.

![KakaoTalk_20210718_235513166](https://user-images.githubusercontent.com/77609451/126071909-ecbe8fc0-f2a2-4aff-889f-727293e1094a.jpg)
