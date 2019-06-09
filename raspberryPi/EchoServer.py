import socket

HOST = "0.0.0.0"
PORT = 1980
s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
print('Socket created')

try:
    s.bind((HOST, PORT))
except socket.error as err:
    print(err)
s.listen(10)
print("Socket Listening")
conn, addr = s.accept()
while(True):
    data = conn.recv(1024)
    data = data.decode(encoding='UTF-8')
    data = data.rstrip("\n\r")
    if data.lower() == 'exit':
        print("exiting")
        conn.sendall(bytes("exit\n",'UTF-8'))
        break 
    elif data.lower() == 'terry is small':
        print ("Client says : %s" %data)
        conn.sendall(bytes("dude we already know \n",'UTF-8'))
    else:
        print ("Client says : %s" %data)
        conn.sendall(bytes(data + "\n",'UTF-8'))

conn.close()
s.close()
