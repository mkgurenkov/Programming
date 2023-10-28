package commands;

import externalConnections.InteractionWithDatabase;

public class SignUp extends Command {
    public SignUp() {
        this.setType("sign_up");
    }
    public void execute() {
        if (InteractionWithDatabase.checkUsernameAvailability(getUsername())) {
            InteractionWithDatabase.addUser(getUsername(), getArgument());
            getClientInteractor().sendString("Signing up completed successfully");
        } else {
            getClientInteractor().sendString("Username already taken, try again");
        }
    }
}
