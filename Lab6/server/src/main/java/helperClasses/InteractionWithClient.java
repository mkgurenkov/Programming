package helperClasses;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
/**
 * @author Maksim Gurenkov
 * @version 1.0
 * Class, embodies the interaction with client
 */
public class InteractionWithClient {
    /** Field: socket address*/
    private static SocketAddress address;
    /** Field: datagram channel*/
    private static DatagramChannel channel;
    /**
     * Method which receive command from the client
     * @return serialised command
     */
    public static String receiveCommand() {
        byte arr[] = new byte[1000];
        int len = arr.length;
        int port = 6733;
        ByteBuffer buffer;
        try {
            address = new InetSocketAddress(port);
            channel = DatagramChannel.open();
            channel.bind(address);
            buffer = ByteBuffer.wrap(arr);
            address = channel.receive(buffer);
            String str = new String(arr, 0, len);
            return str.substring(0, str.lastIndexOf(">") + 1);
        } catch (SocketException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Method which sends message to the client
     * @param message - command output or another message
     */
    public static void sendMessage(String message) {
        ByteBuffer buffer;
        try {
            byte arr[] = message.getBytes();
            int len = arr.length;
            buffer = ByteBuffer.wrap(arr);
            channel.send(buffer, address);
        } catch (SocketException | UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    /** Method which closes datagram channel*/
    public static void closeChannel() {
        try {
            channel.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
