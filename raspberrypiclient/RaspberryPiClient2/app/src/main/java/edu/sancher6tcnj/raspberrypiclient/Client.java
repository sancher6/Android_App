package edu.sancher6tcnj.raspberrypiclient;

import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.io.ObjectOutputStream;

/**
 * Created by peruv on 2/26/2019.
 */

public class Client extends Thread implements Parcelable{
    private String ipaddress;
    private int portnum;
    private Socket socket;
    private ObjectOutputStream out;

    public Client(String ipaddress, int portnum) {
        this.ipaddress = ipaddress;
        this.portnum = portnum;
    }

    @Override
    public void run() {
        super.run();
        connectToServer(ipaddress, portnum);
    }

    private void connectToServer(String ip, int port) {

        try {
            socket = new Socket(InetAddress.getByName(ip), port);
            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /*
    Used for Parcel Information
     */
    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel out, int flags)
    {
        out.writeString(ipaddress);
        out.writeInt(portnum);
    }

    private static Client readFromParcel(Parcel in) {
        String ip = in.readString();
        int port = in.readInt();
        return new Client(ip, port);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator()
    {
        public Client createFromParcel(Parcel in)
        {
            return readFromParcel(in);
        }

        public Client[] newArray(int size) {
            return new Client[size];
        }
    };
}
