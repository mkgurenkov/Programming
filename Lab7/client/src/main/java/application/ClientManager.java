package application;

import commands.Command;
import commands.HelpCommand;
import data.City;
import externalConnections.InteractionWithServer;
import otherUtils.PasswordHasher;
import otherUtils.DeleteSpaces;
import otherUtils.ElementReceiver;
import validators.CommandInputValidator;

import java.util.Scanner;

public class ClientManager {
    private String username;
    private boolean authorized = false;
    private Scanner scanner = new Scanner(System.in);
    public void interactiveMode() {
        System.out.println("Press ctrl+D or type \"exit\" to exit program");
        while (true) {
            //TODO отправить серверу инфорацию о том что клиент авторизован или нет
            if (!authorized) {
                System.out.println("Enter: sign_in <username> <password> / sign_up <username> <password>");
            }
            if (!scanner.hasNextLine()) {
                break;
            }
            String input = scan();
            if (input.equals("exit")) {
                break;
            }
            if (input.equals("help")) { //команда help выполняется на клиенте
                HelpCommand helpCommand = new HelpCommand();
                helpCommand.execute();
            }
            if (input.equals("sign_out") && authorized) {
                authorized = false;
                username = null;
                System.out.println("You are logged out");
                continue;
            }
            CommandInputValidator validator = new CommandInputValidator(input);
            if (validator.commandIsValid()) {
                String[] splittedInput = input.split(" ");
                if (!splittedInput[0].equals("sign_in") && !splittedInput[0].equals("sign_up") && !authorized) {
                    System.out.println("Please sign in before executing commands");
                    continue;
                }
                if ((splittedInput[0].equals("sign_in") | splittedInput[0].equals("sign_up")) && authorized) {
                    System.out.println("You are already logged in");
                    continue;
                }
                Command command = new Command();
                if (!initializeCommandFields(command, input)) {
                    System.out.println("The entry of the element was completed incorrectly, the command was not executed");
                    continue;
                }
                InteractionWithServer.send(Command.serialize(command));
                String answer = InteractionWithServer.receiveString();
                while (!answer.equals("endOfResponse")) { //метка endOfResponse означает, что сервер закончил отвечать клиенту
                    if (answer.equals("signed_in")) {
                        authorized = true;
                        username = command.getUsername();
                    } else {
                        System.out.println(answer);
                    }
                    answer = InteractionWithServer.receiveString();
                }
            } else {
                validator.printException();
            }
        }
    }
    private boolean initializeCommandFields(Command command, String request) {
        String[] splittedRequest = request.split(" ");
        command.setType(splittedRequest[0]);
        command.setUsername(username);
        if (splittedRequest.length > 1) {
            if (command.getType().equals("sign_in") | command.getType().equals("sign_up")) {
                PasswordHasher hasher = new PasswordHasher();
                command.setArgument(hasher.digest(splittedRequest[2].getBytes()));
                command.setUsername(splittedRequest[1]);
            } else {command.setArgument(splittedRequest[1]);}
            if (Command.argumentIsEnum(command.getType())) {
                command.setArgument(command.getArgument().toUpperCase());
            }
        }
        if (Command.requiresElement(command.getType())) {
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
    private String scan() {
        return DeleteSpaces.delete(scanner.nextLine());
    }
}
