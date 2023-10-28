package application;

import externalConnections.InteractionWithDatabase;

import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Maksim Gurenkov
 * @version 1.0
 * Main class for starting a program
 */
public class Main {
    public static volatile int threadCounter = 0;
    /**
     * Main method for starting a program
     * @param args - argument for program - path to file with xml collection
     */
    public static void main(String[] args) {
        InteractionWithDatabase.connect();
        CollectionManager collectionManager = new CollectionManager();
        int serverPort = 6221;
        try {
            DatagramSocket ds = new DatagramSocket(serverPort);
            ExecutorService service = Executors.newFixedThreadPool(100);
            while (true) {
                if (threadCounter < 100) { // Проверяем, сколько активных потоков сейчас выполняется
                    service.execute(new ServerManager(collectionManager, ds));
                    threadCounter++;
                }
            }
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }
}