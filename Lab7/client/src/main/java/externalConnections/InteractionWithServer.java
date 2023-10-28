package externalConnections;

import java.io.IOException;
import java.net.*;
/**
 * @author Maksim Gurenkov
 * @version 1.0
 * Class, embodies the interaction with server
 */
public class InteractionWithServer {
    private static DatagramPacket packetToReceive;
    private static DatagramPacket packetToSend;
    /** Field Datagram Socket*/
    private static DatagramSocket ds;
    public static void send(byte[] bytes) {
        try {
            int len = bytes.length;
            ds = new DatagramSocket();
            InetAddress host = InetAddress.getLocalHost();
            int port = 6221;
            packetToSend = new DatagramPacket(bytes, len, host, port);
            ds.send(packetToSend);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static byte[] receive() {
        byte arr[] = new byte[5000];
        int len = arr.length;
        try {
            packetToReceive = new DatagramPacket(arr, len);
            ds.setSoTimeout(1000);
            boolean delivered = false;
            int it = 0;
            while (!delivered) {
                it ++;
                try {
                    ds.receive(packetToReceive);
                    delivered = true;
                } catch (SocketTimeoutException e) {
                    if (it == 1) {
                        System.out.println("The server is not responding, please try again later");
                    }
                    ds.send(packetToSend);
                }
            }
            return packetToReceive.getData();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    /** Method, which receives answer from the server*/
    public static String receiveString() {
        byte[] strInBytes = receive();
        String str = new String(strInBytes, 0, packetToReceive.getLength());
        return str;
    }
}
