package com.example.socket_in;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class LoopingSocketClient {
    public static void main(String args[]) throws Exception {
        Socket socket1;
        int portNumber = 22;
        String ip = "";

        socket1 = new Socket(InetAddress.getLocalHost(), portNumber);

        ObjectInputStream ois = new ObjectInputStream(socket1.getInputStream());

        ObjectOutputStream oos = new ObjectOutputStream(socket1.getOutputStream());

        String str;
        str = "initialize";
        oos.writeObject(str);

        while ((str = (String) ois.readObject()) != null) {
            System.out.println(str);
            oos.writeObject("bye");

            if (str.equals("bye bye"))
                break;
        }

        ois.close();
        oos.close();
        socket1.close();
    }
}
