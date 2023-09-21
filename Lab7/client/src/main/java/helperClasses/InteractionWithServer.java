package helperClasses;

import java.io.IOException;
import java.net.*;
/**
 * @author Maksim Gurenkov
 * @version 1.0
 * Class, embodies the interaction with server
 */
public class InteractionWithServer {
    private static DatagramPacket packetToSend;
    /** Field Datagram Socket*/
    private static DatagramSocket ds;
    /**
     * Method, which sends serialised command to the server
     * @param serialisedCommand
     */
    public static void sendCommand(String serialisedCommand) {
        try {
            byte arr[] = serialisedCommand.getBytes();
            int len = arr.length;
            ds = new DatagramSocket();
//            InetAddress host = InetAddress.getByName("helios.cs.ifmo.ru");
            InetAddress host = InetAddress.getLocalHost();
            int port = 6733;
            packetToSend = new DatagramPacket(arr, len, host, port);
            ds.send(packetToSend);
        } catch (SocketException | UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    /** Method, which receives answer from the server*/
    public static String receiveAnswer() {
        byte arr[] = new byte[1000];
        int len = arr.length;
        try {
            DatagramPacket packetToReceive = new DatagramPacket(arr, len);
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
            String str = new String(packetToReceive.getData(), 0, packetToReceive.getLength());
            return str;
        } catch (SocketException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
