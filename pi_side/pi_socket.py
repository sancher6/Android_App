import socket # for socket
import sys 
import pickle

#Imports to communicate with Arduino
import serial
import RPi.GPIO as GPIO
import time

#Commands for Arduino
ser=serial.Serial("/dev/ttyACM0",9600)  #change ACM number as found from ls /dev/tty/ACM*

def do_startcar():
     print ("Starting Car")
     ser.write('g'.encode('utf-8'))

def do_stopcar():
     print ("Stoping Car")
     ser.write('s'.encode('utf-8'))

def do_goleft():
     print ("Turning Left")
     ser.write('l'.encode('utf-8'))
                          
def do_goright():
     print ("Turning Right")
     ser.write('r'.encode('utf-8'))

#ip = '172.16.93.182'
ip = '192.168.4.1'
try: 
	s = socket.socket() 
	print ("Socket successfully created")
except socket.error as err: 
	print ("socket creation failed with error %s" %(err)) 

# default port for socket 
port = 22

# connecting to the server
pp = (ip,port)
s.bind(pp)

print ("The socket has successfully binded" )

#listen mode
s.listen(5) 
print ("socket is listening")

dataObj = bytes(8)

#accept 
conn,addr = s.accept() 
print ('connected')
while True:
    dataObj = conn.recv(4096)
    decodeData = dataObj#.decode(encoding='utf-8', errors='strict')
    if (not decodeData): break
    data = ''
    if(len(decodeData) > 4):
       data = (decodeData[3:4]) + (decodeData[4:5])
    msg = ""
    if (data == "11"):
        do_goright()
        msg = "Turning right"
    elif (data == "21"):
        do_goleft()
        msg = "Turning left"
    elif (data == "31"):
        do_startcar()
        msg = "Starting"
    elif (data == "30"):
        do_stopcar()
        msg = "Stopping"
    else:
        msg = "Nothing"


    #conn.sendall(msg)
    
conn.close()
do_stopcar()
