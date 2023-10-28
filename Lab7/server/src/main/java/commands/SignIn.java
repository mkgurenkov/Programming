package commands;

import externalConnections.InteractionWithDatabase;

import java.net.Socket;

public class SignIn extends Command {
    public SignIn() {
        this.setType("sign_in");
    }

    public void execute() {
        if (InteractionWithDatabase.checkLoginDetails(getUsername(), getArgument())) {
            getClientInteractor().sendString("signed_in");
            getClientInteractor().sendString("You have successfully logged in, to log out enter \"sign_out\"");
        } else {
            getClientInteractor().sendString("Invalid username or password");
        }
    }
}
