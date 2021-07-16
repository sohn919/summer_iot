### 실시간 스트리밍

+ 세대별 택배 도난방지 시스템을 위해서는 카메라를 통한 실시간 스트리밍은 꼭 필요한 요소입니다.


### 터미널 창에 사용된 명령어

+ 설치된 패키지를 업데이트 시켜주기 위한 명령어입니다.

      sudo apt-get update
      sudo apt-get upgrade
#
+ mjpg 폴더를 만들고 mjpg 폴더로 체인지 시켜줍니다.

      sudo mkdir mjpg
      cd mjpg
#
+ mjpg streamer를 다운 받아줍니다. (github에 올라가 있는 실시간 스트리밍 오픈 소스입니다.)

      sudo git clone https://github.com/jacksonliam/mjpg-streamer.git
#          
+ 폴더를 이동 시켜줍니다.

      cd mjpg-streamer/mjpg-streamer-experimental
#            
+ 필요한 패키지들을 설치해줍니다.

      sudo apt-get install cmake
      sudo apt-get install python-imaging
      sudo apt-get install libjpeg-dev
#      
+ 파일을 컴파일 시켜줍니다.

      make CMAKE_BUILD_TYPE=Debug
#
+ mjpg-streamer를 설치 시켜줍니다.

      sudo make install
#
+ mjpg.sh 파일을 nano 편집기로 열어 줍니다.

      cd
      sudo nano mjpg.sh
#
+ 아래 내용을 입력하여 저장 후 나옵니다.

      export STREAMER_PATH=$HOME/mjpg/mjpg-streamer/mjpg-streamer-experimental
      export LD_LIBRARY_PATH=$STREAMER_PATH
      $STREAMER_PATH/mjpg_streamer -i "input_raspicam.so" -o "output_http.so -p 8091 -w $STREAMER_PATH/www"
#
+ 스크립트를 실행 시켜줍니다.

      sh mjpg.sh
#

+ 실행 결과
![image](https://user-images.githubusercontent.com/84082544/125980146-aa422c2b-7247-4d21-9894-f510b3896b53.png)
