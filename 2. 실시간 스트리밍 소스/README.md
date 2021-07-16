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
