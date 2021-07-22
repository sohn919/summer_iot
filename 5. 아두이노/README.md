### 소스 코드

+ 온 습도 센서를 사용하기 위한 라이브러리를 추가시키고 센서의 핀을 지정시켜줍니다.

      #include "DHT.h"
      #define DHTPIN 6
      #define DHTTYPE DHT11
      DHT dht(DHTPIN, DHTTYPE);
#
 
+ 초음파 센서를 사용하기 위해 핀을 지정 시켜주고 거리 측정을 위한 변수를 선언합니다.

      int trig = 4;
      int echo = 5;
      float duration;
      float distance;
 
#

+ 블루투스 모듈을 사용하기 위한 라이브러리를 추가하고 핀을 지정해 줍니다. 

      #include <SoftwareSerial.h>
      SoftwareSerial BTSerial(2, 3);
      int blue3 = 0;
      int blue4 = 0;
      
#

+ 라즈베리, 블루투스 시리얼 통신을 위해 통신 속도 9600으로 지정해 줍니다. 

      Serial.begin(9600);
      BTSerial.begin(9600);
      pinMode(trig,OUTPUT);
      pinMode(echo,INPUT);
#
 
+ 초음파 센서를 사용하기 위해 핀을 지정 시켜주고 거리 측정을 위한 변수를 선언합니다.

      int trig = 4;
      int echo = 5;
      float duration;
      float distance;
 
#

+ 습도와 온도를 각각의 변수에 넣어줍니다.

      int h = dht.readHumidity();
      int t = dht.readTemperature();
      
#

+ 블루투스에 보낼 값을 넣어줍니다. ( ','를 중간에 넣는 이유는 앱에서 ','별로 나누어 값을 저장하기 위해서 입니다.)

      BTSerial.print(t);
      BTSerial.print(",");
      BTSerial.print(h);
      BTSerial.print(",");
#

+ 거리가 12 밑으로 떨어지면 카운트를 올리고 이것이 총 5번 체크가 되면 택배가 온 것으로 인지합니다.

      if(distance < 12) {
        count++;
      } else {
        count = 0;
      }
      if(count == 5) {
        blue3 = 2;
        BTSerial.print(blue3);
      }
 
#

+ 라즈베리와 아두이노의 시리얼 통신 부분입니다. 라즈베리 움직임 감지 코드에서 움직임이 감지되면 값을 보내며 이를 Serial.available()를 통해 값을 인지하여 블루투스로 값을 보냅니다.

      if(Serial.available() > 0) {
        blue4 = 1;
        BTSerial.print(blue4);
        Serial.read();
      }else if(Serial.available() < 0){
        blue4 = 0;
        BTSerial.print(blue4);
      }
