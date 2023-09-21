package application;

import commands.Command;
import commands.HelpCommand;
import data.City;
import helperClasses.*;
import java.util.Scanner;
/**
 * @author Maksim Gurenkov
 * @version 1.0
 * Main class for starting a program
 */
public class Main {
    /**
     * Main method for starting a program
     * @param args - argument of the command line
     */
    public static void main(String[] args) {
        System.out.println("Press ctrl+D or type \"exit\" to exit program");
        boolean interactiveMode = true;
        Scanner scanner = new Scanner(System.in);
        while (interactiveMode && scanner.hasNextLine()) {
            String request = scanner.nextLine();
            request = DeleteSpaces.delete(request);
            RequestValidator validator = new RequestValidator(request);
            if (validator.requestIsValid()) {
                Command command = new Command();
                boolean initializingCompletedCorrectly = initializeCommandFields(command, request);
                if (!initializingCompletedCorrectly) {
                    System.out.println("The entry of the element was completed incorrectly, the command was not executed");
                    continue;
                }
                if (command.getType().equals("help")) { //команда help выполняется на клиенте
                    HelpCommand helpCommand = new HelpCommand();
                    helpCommand.execute();
                } else if (command.getType().equals("exit")) {
                    break;
                }
                String serializedCommand = CommandMarshaller.toXml(command); //сериализуем объект команды в xml строку
                InteractionWithServer.sendCommand(serializedCommand);
                String answer = InteractionWithServer.receiveAnswer();
                while (!answer.equals("endOfResponse")) { //метка endOfResponse означает, что сервер закончил отвечать клиенту
                    System.out.println(answer);
                    answer = InteractionWithServer.receiveAnswer();
                }
            }
        }
    }
    /**
     * Method, which initializes fields of the Command object (type, argument, element)
     * @param command - object of the Command class
     * @param request - console input
     */
    private static boolean initializeCommandFields(Command command, String request) {
        String[] splittedRequest = request.split(" ");
        command.setType(splittedRequest[0]);
        if (splittedRequest.length > 1) {
            command.setArgument(splittedRequest[1]);
        }
        if (Command.requiresElement(splittedRequest[0])) {
            ElementReceiver elementReceiver = new ElementReceiver();
            City element = elementReceiver.receive();
            if (elementReceiver.getExit() == 0) {
                command.setElement(element);
            } else {
                return false;
            }
        }
        return true;
    }
}