import time,random
from threading import Condition, Thread 

condition = Condition()

def consumer(items): 
    print("Consumer Thread started")
    time.sleep(items) 
    condition.acquire()
    
