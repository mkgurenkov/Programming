package application;

import commands.Command;
import externalConnections.ClientInteractor;

import java.net.DatagramSocket;
import java.util.Arrays;

public class ServerManager implements Runnable {
    private DatagramSocket ds;
    private CollectionManager collectionManager;
    public ServerManager(CollectionManager collectionManager, DatagramSocket ds) {
        this.collectionManager = collectionManager;
        this.ds = ds;
    }
    @Override
    public void run() {
        CommandExecutor executor = new CommandExecutor(collectionManager);
        ClientInteractor clientInteractor = new ClientInteractor(ds);
        byte[] serializedCommand = clientInteractor.receive();
        Command command = Command.deserialize(serializedCommand);
        command.setClientInteractor(clientInteractor);
        executor.execute(command);
        clientInteractor.sendString("endOfResponse");
        Main.threadCounter--;
    }
}
