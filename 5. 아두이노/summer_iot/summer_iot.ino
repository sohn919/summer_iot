//온습도
#include "DHT.h" // DHT 라이브러리 호출
#define DHTPIN 6
#define DHTTYPE DHT11   // DHT11 온습도 센서 사용
DHT dht(DHTPIN, DHTTYPE);

//초음파 센서
int trig = 2;
int echo = 3;
float duration;
float distance;

//블루투스
#include <SoftwareSerial.h>
SoftwareSerial BTSerial(2, 3);
int blue3 = 0;
int blue4 = 0;
 
void setup()
{
 Serial.begin(9600); // 통신속도 9600으로 통신 시작
 BTSerial.begin(9600);
 pinMode(trig,OUTPUT);
 pinMode(echo,INPUT);
}
 
void loop() 
{
 delay(2000);
 int count = 0;
 int h = dht.readHumidity(); // 습도값을 h에 저장
 int t = dht.readTemperature(); // 온도값을 t에 저장
// Serial.print("Humidity: "); // 문자열 출력
// Serial.print(h); // 습도값 출력
// Serial.print("% ");
// Serial.print("Temperature: ");
// Serial.print(t); // 온도값 출력
// Serial.println("C");
 BTSerial.print(t);
 BTSerial.print(",");
 BTSerial.print(h);

//초음파 센서
 digitalWrite(trig,HIGH);
 delay(10);
 digitalWrite(trig,LOW);
 duration = pulseIn(echo,HIGH);     //pulseIn함수의 단위는 ms(마이크로 세컨드)
 distance = ((34000*duration)/1000000)/2;
 Serial.print(distance);
 Serial.println("cm");
 delay(100);
 
 BTSerial.print(",");

//물품이 왔다는 걸 알리기 위한 코드
 if(distance != 200) {
  count++;
 } else {
  count = 0;
 }
 if(count == 5) {
  blue3 = 2;
  BTSerial.print(blue3);
 } else {
  BTSerial.print(blue3);
 }

 BTSerial.print(",");

 if(Serial.available() > 0) {
  blue4 = 1;
  BTSerial.print(blue4);
 } else {
  BTSerial.print(blue4);
 }
}
