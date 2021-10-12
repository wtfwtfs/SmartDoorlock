import cv2
import numpy as np
from os import listdir
from os.path import isfile, join
from pyfcm import FCMNotification
import threading

bodycount = 0
facecount = 0

# 새로운 얼굴 감지 푸시 알림
def face_push():
    API_KEY = "AAAACSNzqZc:APA91bHLDdHrkjltFmt5I9W6-ewzPjK0V3xCZu9WCfVYTfZKXyiZ0nTztSA4E5q1ugQbbhCuVmLqht5pgRW5YZk3tPMYQXm_5iHP_LT4hSpHmk5PKugSnCU3ZgqJT88JGu04paunkVXn"
    push_service = FCMNotification(api_key=API_KEY)
    result = push_service.notify_single_device(
        registration_id='cQyt40PC3w4:APA91bGz36VvT89zqnM_0hy349rOxOMnMNlqdh_WPqOM7qAHmVM4gMXDZSfG3cEElzn4xz7HEXVP2y9H6dPq_h5p_LT2ZjEPepgYXPx80v1GuQpC4jiAWfWgioelDrbNgrnCNNhWDGgM',
        message_title='Face detector', message_body='새로운 얼굴이 감지되었습니다.')
    print
    result

# 낯선 사람의 움직임 감지 푸시 알림
def body_push():
    API_KEY = "AAAACSNzqZc:APA91bHLDdHrkjltFmt5I9W6-ewzPjK0V3xCZu9WCfVYTfZKXyiZ0nTztSA4E5q1ugQbbhCuVmLqht5pgRW5YZk3tPMYQXm_5iHP_LT4hSpHmk5PKugSnCU3ZgqJT88JGu04paunkVXn"
    push_service = FCMNotification(api_key=API_KEY)
    result = push_service.notify_single_device(
        registration_id='cQyt40PC3w4:APA91bGz36VvT89zqnM_0hy349rOxOMnMNlqdh_WPqOM7qAHmVM4gMXDZSfG3cEElzn4xz7HEXVP2y9H6dPq_h5p_LT2ZjEPepgYXPx80v1GuQpC4jiAWfWgioelDrbNgrnCNNhWDGgM',
        message_title='Body detector', message_body='수상한 사람이 감지되었습니다.')
    print
    result

def start_timer():
    global bodycount
    bodycount = 0
    global facecount
    facecount = 0
    # print("counter")
    timer = threading.Timer(10, start_timer)
    timer.start()


face_cascade = cv2.CascadeClassifier(
    'C:/Program Files (x86)/Python37-32/Lib/site-packages/cv2/data/haarcascade_frontalface_default.xml')  # 얼굴찾기 haar 파일
body_cascade = cv2.CascadeClassifier(
    'C:/Program Files (x86)/Python37-32/Lib/site-packages/cv2/data/haarcascade_upperbody.xml')
font = cv2.FONT_HERSHEY_SIMPLEX

data_path = 'C:/Users/ounog/test_image/face/'

# 폴더에 있는 파일 리스트 얻기
onlyfiles = [f for f in listdir(data_path) if isfile(join(data_path,f))]
Training_Data, Labels = [], []  # 데이터와 매칭될 라벨 변수


# 파일 개수 만큼 루프
for i, files in enumerate(onlyfiles):
    image_path = data_path + onlyfiles[i]                     # 이미지 불러오기
    images = cv2.imread(image_path, cv2.IMREAD_GRAYSCALE)     # Training_Data 리스트에 바이트 배열로 추가
    Training_Data.append(np.asarray(images, dtype=np.uint8))  # Labels 리스트엔 카운트 번호 추가
    Labels.append(i)

Labels = np.asarray(Labels, dtype=np.int32)                   # Labels를 32비트 정수로 변환
model = cv2.face.LBPHFaceRecognizer_create()                  # 모델생성
model.train(np.asarray(Training_Data), np.asarray(Labels))    # 학습시작
print("Model Training Complete!!!!!")


def face_detector(img, size = 0.5):
    gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)              # 흑백처리
    faces = face_cascade.detectMultiScale(gray,1.3,5)         # 얼굴찾기
    if faces is():
        return img,[]
    for(x,y,w,h) in faces:
        cv2.rectangle(img, (x,y),(x+w,y+h),(0,255,255),2)
        roi = img[y:y+h, x:x+w]
        roi = cv2.resize(roi, (200,200))
    return img,roi   # 검출된 좌표에 사각 박스 그리고(img), 검출된 부위를 잘라(roi) 전달



def body_detector(img, size = 1):
    gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
    body = body_cascade.detectMultiScale(gray, 1.05, 10)
    if body is():
        return img,[]
    else:
        global bodycount
        bodycount += 1
        print("bodycount : ")
        print(bodycount)
    for(x,y,w,h) in body:
        cv2.rectangle(img, (x,y),(x+w,y+h),(255,0,0),2)
        roi = img[y:y+h, x:x+w]
        roi = cv2.resize(roi, (400,500))
    return img,roi   #검출된 좌표에 사각 박스 그리고(img), 검출된 부위를 잘라(roi) 전달


try:
    cam = cv2.VideoCapture(0)
except:
    print("camera loading error")

count1 = 0
count2 = 0
start_timer()
while True:
    ret, frame = cam.read()

    if not ret:
        break

    body_image, body = body_detector(frame) # 바디 검출 시도
    image, face = face_detector(frame)  # 얼굴 검출 시도

    try:
        # 검출된 사진을 흑백으로 변환
        face = cv2.cvtColor(face, cv2.COLOR_BGR2GRAY)
        # body_image = cv2.cvtColor(body_image, cv2.COLOR_BGR2GRAY)
        # 위에서 학습한 모델로 예측시도
        result = model.predict(face)

        # result[1]은 신뢰도이고 0에 가까울수록 자신과 같다는 뜻이다.
        if result[1] < 500:
            confidence = int(100 * (1 - (result[1]) / 300))
            # 유사도 화면에 표시
            display_string = str(confidence) + '% Confidence it is user'
        cv2.putText(image, display_string, (100, 120), font, 1, (250, 120, 255), 2)

        # 80 보다 크면 동일 인물로 간주해 UnLocked!
        if confidence > 80:
            cv2.putText(image, "Owner", (250, 450), font, 1, (0, 255, 0), 2)

        else:
            # 80 이하면 타인.. Locked!!!
            count1 = count1 + 1
            cv2.putText(image, "Stranger", (250, 450), font, 1, (0, 0, 255), 2)
            facecount += 1
            print("facecount : ")
            print(facecount)
            if facecount == 5:
                #print("facecount = 5")
                cv2.imwrite("C:/Users/ounog/test_image/newface/frame%d.jpg" % count1, face)
                face_push()

        if bodycount == 3:
            count2 = count2 + 1
            cv2.imwrite("C:/Users/ounog/test_image/newbody/frame%d.jpg.jpg" % count2, body)
            body_push()

    except:
        # 얼굴 검출 안됨
        cv2.putText(image, "Face Not Found", (250, 450), font, 1, (255, 0, 0), 2)
        pass

    cv2.imshow("frame", frame)

    k = cv2.waitKey(30)

    if k == 113:
        break
cam.release()
cv2.destroyAllWindows()
