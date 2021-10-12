#include<SoftwareSerial.h>

SoftwareSerial BTSerial(2, 3);

int inputPin = 4; // 센서 시그널핀
int outputPin = 5; // 도어락 개폐 신호핀
int pirState = LOW; // PIR 초기상태
int val = 0; // Signal 입력값

void setup()
{
  pinMode (outputPin, OUTPUT);  // 블루투스 Output 설정 
  pinMode(inputPin, INPUT); // 인체감지센서 Input 설정
  Serial.begin(9600);
  BTSerial.begin(9600);
}
void loop()
{
  //<인체감지 Test>
   val = digitalRead(inputPin); // 센서값 읽기
    if (val == HIGH) { // 인체감지시     
        if (pirState == LOW) {
        // 시리얼모니터에 메시지 출력
        Serial.println("Motion detected!");
        pirState = HIGH;
        BTSerial.write(1);
        }
         
    } 
    else {
        if (pirState == HIGH){        
            // 시리얼모니터에 메시지 출력            
            Serial.println("Motion ended!");
            pirState = LOW;
            BTSerial.write(0);
        }
    }
  //<\인체감지 Test>
  
  char k=Serial.read(); 
  
  //<블루투스와 아두이노 통신? 코드>
  if(BTSerial.available())
    Serial.write(BTSerial.read());
  
  if(Serial.available())
    BTSerial.write(Serial.read());
   //<\블루투스와 아두이노 통신? 코드>
   
   if (k=='a'){             // a번 신호 인가
    digitalWrite(outputPin,HIGH);  
    delay(100);
    digitalWrite(outputPin,LOW);
  }
   else if (k=='b'){         // b번 신호 인가
    digitalWrite(outputPin,HIGH);
    delay(100);
    digitalWrite(outputPin,LOW);
  }  
}
