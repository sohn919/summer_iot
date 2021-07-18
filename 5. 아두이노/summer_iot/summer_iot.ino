#include "DHT.h" // DHT 라이브러리 호출
 
#define DHTPIN 6     // 온습도 센서가 4번에 연결
 
#define DHTTYPE DHT11   // DHT11 온습도 센서 사용
 
DHT dht(DHTPIN, DHTTYPE); // DHT 설정 (4,DHT11)
 
void setup()
{
 Serial.begin(9600); // 통신속도 9600으로 통신 시작
 Serial.println("DHT11 test!"); // 문자 출력
 
}
 
void loop() 
{
delay(2000);
 
 int h = dht.readHumidity(); // 습도값을 h에 저장
 int t = dht.readTemperature(); // 온도값을 t에 저장
 Serial.print("Humidity: "); // 문자열 출력
 Serial.print(h); // 습도값 출력
 Serial.print("% ");
 Serial.print("Temperature: ");
 Serial.print(t); // 온도값 출력
 Serial.println("C");
 }

int trig = 2;
int echo = 3;
float duration;
float distance;

void setup() 
{
  Serial.begin(9600);
  pinMode(trig,OUTPUT);
  pinMode(echo,INPUT);
}

void loop() 
{
  digitalWrite(trig,HIGH);
  delay(10);
  digitalWrite(trig,LOW);
  duration = pulseIn(echo,HIGH);     //pulseIn함수의 단위는 ms(마이크로 세컨드)
  distance = ((34000*duration)/1000000)/2;
  Serial.print(distance);
  Serial.println("cm");
  delay(100);
}
