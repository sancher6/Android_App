import socket # for socket 
import sys 

ip = '172.16.93.182'
try: 
	s = socket.socket() 
	print "Socket successfully created"
except socket.error as err: 
	print "socket creation failed with error %s" %(err) 

# default port for socket 
port = 22

# connecting to the server 
s.bind((ip,port))

print "The socket has successfully binded" 

#listen mode
s.listen(5) 
print "socket is listening"


#accept 
conn,addr = s.accept() 
print 'connected'
while True: 
    data = conn.recv(1024)
    if not data: break
    print data
    conn.sendall(data)
    
conn.close() 
