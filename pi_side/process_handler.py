# PI SPECIFIC
from threading import Condition, Thread
import time,random

# FOR PHONE COMM
import socket

# FOR ARDUINO COMM
import serial 
import RPi.GPIO as GPIO 

IP = '192.168.4.1'
PORT = 22
PORT2 = 69 # YOUDOSUIOAGKDSLAJG CHANGE THIS ALKSDJGIASODJGA 
MAX_COMM = 5

# FOR ARDUINO COMMANDS
ser=serial.Serial("/dev/ttyACM0",9600)

# CONDITION VARIABLES  
given_instr = Condition() 
given_state = Condition() 

threads = []

dataObj = bytes(8)

# SEND CAR STATE TO PHONE 
def msg_tophone(state): 
    print("Message is being sent\n")
    #SEND ITTTT
    try: 
        t = socket.socket() 
        print("Socket successfully created")
    except socket.error as err: 
	    print ("socket creation failed with error %s" %(err)) 
    #BINDS SOCKET 
    t.bind((IP,PORT2))
    print("Socket successfully Binded") 
    t.send(state.encode('utf-8'))

# SEND INSTRUCTIONS TO CAR 
def msg_tocar(data): 
    print("Message is being sent\n")
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

# LISTENS FOR PHONE INSTRUCTIONS 
def listen_tophone(): 
    # SOCKET LISTEN MODE 
    s.listen(MAX_COMM)  
    print("Listening for Instructions\n")
    conn, addr = s.accept() 
    while(True): 
        dataObj = conn.recv(4096)
        if(not dataObj): 
            break

        elif:
            data = (dataObj[3:4]) + (dataObj[4:5])
            msg_tocar(data)
    #while instruction is not given, listen


# listens for state from the car 
def listen_tocar(): 
    print("Listening for Car State\n")
    #state update given 
    given_state.acquire() 

def create_socket():
    try: 
        s = socket.socket() 
        print("Socket successfully created")
    except socket.error as err: 
	    print ("socket creation failed with error %s" %(err)) 
    
    #BINDS SOCKET 
    s.bind((IP,PORT))
    print("Socket successfully Binded") 

    #accept
    conn, addr = s.accept()
    print("Connected") 


#CAR INSTRUCTIONSSSSSSSSSSSSSSSSSSSSS
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

#main function to call threads 
if __name__ == '__main__':
    # INITIALIZE CONDITION VARIABLES 

    # START THREADS 