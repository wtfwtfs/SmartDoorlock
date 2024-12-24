graduation final project 참고

# Smart Bluetooth Doorlock with Webcam

## 목차

[1. Smart Doorlock 소개](#1-PT-소개)<br/>
[2. 개요](#2-개요)<br/>
[3. 기술 스택 및 구조](#3-기술-스택-및-구조)<br/>
[4. 주기능 소개](#4-주기능-소개)<br/>

<br />
<br />

## 1. Smart Doorlock 소개

- 개발기간 : 2018.09.03 - 2019.05.31
- 개발인원 : 권예슬, 최연정, 윤영은
- 주제 : 범죄 예방을 위해 도어락 조작과 웹캠 영상 감시 및 침입자 알림을 연동한 모바일 앱 서비스 
- 역할<br />
    - 권예슬 : 도어락 조작, 포트포워딩
    - 윤영은 : UI/UX, 포트포워딩, 얼굴 인식 및 러닝, 푸시 알림
    - 최연정 : 도어락 조작, 푸시 알림

<br />
<br />

## 2. 개요

1인 가구도 안심하고 외출하세요! Smart Doorlock이 지켜볼게요.🧐

> 
> Smart Doorlock은 범죄 예방을 위한 **도어락 조작 어플**로, 문앞의 **실시간 웹캠 영상 확인**과 얼굴 인식을 통한 **침입자 알림 서비스**를 제공하는 모바일 앱 서비스입니다.
> 
> 도어락의 키패드가 아닌 어플의 **키패드**로 도어락 개폐를 조작하고 언제 어디서든 **웹캠의 실시간 영상**을 확인할 수 있습니다. 또, 미리 **러닝**된 집주인의 얼굴과 다른 얼굴 감지시 **푸시 알림**을 받을 수 있습니다.

<br />

### P;T 기능
![주기능](https://user-images.githubusercontent.com/62532878/136691979-11589c46-1f98-4837-8e52-9c44019ff88b.PNG)

<br />
<br />

## 3. 기술 스택 및 구조
### 사용 기술
<img src="https://i.imgur.com/pHhgOVJ.png" width="600"/>
<br />

### 아키텍처
<img src="https://i.imgur.com/ByGx4d2.png" width="600"/>
<br />

### 도어락 통신구조
<img src="https://i.imgur.com/upIPp1y.png" width="600"/>

<br />
<br />

## 4. 주기능 소개

### 주기능 1) 도어락 연결

![](https://i.imgur.com/j2ztjvD.png)


>블루투스를 통해 도어락과 앱을 연결합니다.

ㅤ

### 주기능 2) 도어락 잠금 해제
![](https://i.imgur.com/LunsLbv.png)


>블루투스 연결이 완료되면, 키패드에 비밀번호를 입력해 도어락의 잠금을 해제할 수 있습니다.
>

<br />

### 주기능 3) 웹캠 영상 확인

![](https://i.imgur.com/5qj6wrx.png)


>키패드 페이지 하단의 'CAM' 버튼을 통해 **언제 어디서든** 문 앞의 실시간 웹캠 영상을 확인할 수 있습니다.

<br />

### 주기능 4) 얼굴 인식 및 푸시 알림
<img src="https://i.imgur.com/VJ9jlCd.png" width="700"/>

>웹캠 영상에 얼굴이 인식되면 **집주인인지 아닌지**를 판별하여 푸시 알림을 받게 됩니다.




<br />
<br />



**Fin.**
