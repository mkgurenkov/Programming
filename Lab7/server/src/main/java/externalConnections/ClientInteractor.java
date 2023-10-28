package externalConnections;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Maksim Gurenkov
 * @version 1.0
 * Class, embodies the interaction with client
 */
public class ClientInteractor {
    /** Field: socket address*/
    private DatagramSocket  ds;
    private DatagramPacket dp;
    /**
     * Method which receive command from the client
     * @return serialised command
     */
    public ClientInteractor(DatagramSocket ds) {
        this.ds = ds;
    }
    public synchronized byte[] receive() {
       byte arr[] = new byte[5000];
       int len = arr.length;
       try {
           dp = new DatagramPacket(arr, len);
           ds.receive(dp);
           return arr;
       } catch (IOException e) {
           throw new RuntimeException(e);
       }
   }
    public String receiveString() {
        byte[] arr = receive();
        String str = new String(arr, 0, arr.length);
        return str.substring(0, arr.length);
    }
    public void send(byte[] arr) {
        InetAddress host = dp.getAddress();
        int port = dp.getPort();
        dp = new DatagramPacket(arr, arr.length, host, port);
        try {
            ds.send(dp);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void sendString(String message) {
        byte[] arr = message.getBytes();
        send(arr);
    }
    /** Method which closes datagram channel*/
}
