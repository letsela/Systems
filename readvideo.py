from pydoc import text
import cv2
import numpy as np
import pytesseract
import matplotlib as plt
from PIL import Image  #python imaging library
import matplotlib.pyplot as plt
import matplotlib.image as mpimg
from datetime import datetime
import mysql.connector
conn = mysql.connector.connect(
    host="localhost",
    port="3306",
    user="root",
    password="",
    database="final_project"
)
cursor = conn.cursor(prepared=True) # defining funtion for database
date = datetime.now() # defining function for date

#numberPlateCascade = cv2.CascadeClassifier('haarcascade_russian_plate_number.xml')
pytesseract.pytesseract.tesseract_cmd = 'C:/Program Files/Tesseract-OCR/tesseract.exe'
plat_detector =  cv2.CascadeClassifier(cv2.data.haarcascades + "haarcascade_russian_plate_number.xml")
video = cv2.VideoCapture('videos/MVI_5.mp4')
count = 0
#correcting plate number
def correct_plate_number(text):
    test_str = text
    punc = '''!()-[]{};:“~¢'"\,<>./?@#$%^&*_~'''
    for ele in test_str:
        if ele in punc:
            test_str = test_str.replace(ele, "")
    
    join_str = "".join(test_str.split())
    cut_str = join_str[:7]

    return cut_str

def make_1080p():
    video.set(3, 1920)
    video.set(4, 1080)
    
if(video.isOpened()==False):
    print('Error Reading Video')
    
make_1080p()
# auto cupture image from video
print("Plate number detected.....")
def capture_image(cropped_image):
    cv2.imwrite("F:\SKILA\YEAR FOUR\Y4S2\Mojar Project2\Video images\IMAGES"+str(count)+".jpg",cropped_image)  #here we have defined where our image will be saved
    #cv2.rectangle(frame,(0,200),(640,400),(255,0,0),cv2.FILLED)
    cv2.putText(frame,"NUMBER PLATE SAVED",(15,256),cv2.FONT_HERSHEY_COMPLEX,2,(0,255,255),2) #AFTER PRESSING S THIS WILL DISPLAY scan saved ON THE SCREEN
    cv2.imshow("RESULT",resize_frame )  

    print("Plate number captured.....")

    image = mpimg.imread("F:\SKILA\YEAR FOUR\Y4S2\Mojar Project2\Video images\IMAGES"+str(count)+".jpg")
    imgray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
    (ret, thresh1)=cv2.threshold(imgray,190,255,cv2.THRESH_BINARY)

    cv2.imshow('Binary Threshold', thresh1)
    #read text from image
    text = pytesseract.image_to_string(thresh1)

    plate_number = correct_plate_number(text)
    
    #retriveng CAPTURED DATA INTO THE DATABASE
    # Parameterized query
    
    select_query="select * from road_crime where NumberPlate='"+plate_number+"'"
    cursor.execute(select_query)
    result = cursor.fetchall()

    temp = False
    for x in result:
        if plate_number in x:
            temp = True
    if temp:
        print("Match")
        update_query = "UPDATE road_crime SET Location='Kings Way', Status='Found' WHERE NumberPlate='"+plate_number+"' "
        cursor.execute(update_query)
        conn.commit() 
    else:
        print("Not match")
        
    print("Number plate: "+ text)

    print("The string after punctuation filter :", plate_number)


print("Waiting for capture...")

while True:
    ret,frame = video.read()    
    gray_video = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
    plate = plat_detector.detectMultiScale(gray_video,scaleFactor=1.2,minNeighbors=5,minSize=(25,25))
    
    img=cv2.resize(frame,None,fx=0.2,fy=0.2) #resise img if its large
    for (x,y,w,h) in plate:
        cv2.rectangle(frame, (x,y), (x+w,y+h), (255,0,0),2)
        frame[y:y+h,x:x+w] = cv2.blur(frame[y:y+h,x:x+w],ksize=(2,2))
        cv2.putText(frame,text='License Plate',org=(x-3,y-3),fontFace=cv2.FONT_HERSHEY_COMPLEX,color=(0,0,255),thickness=1,fontScale=0.6)
    
        
        # Cropping an image
        cropped_image = frame[y:y+h,x:x+w]
        # Display cropped image
        cv2.imshow("cropped", cropped_image)
        capture_image(cropped_image)
         
    resize_frame = cv2.resize(frame, (650, 500))
    cv2.imshow("result", resize_frame)
    

    cv2.waitKey(1)
    count = count + 1
            
    
video.release()
cv2.destroyAllWindows()            