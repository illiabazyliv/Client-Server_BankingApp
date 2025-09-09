package gd.workshop.handler;

import gd.workshop.comands.Commands;
import gd.workshop.entities.UserContext;
import gd.workshop.roles.Role;

public class LoginHandler implements CommandHandler {
    @Override
    public String handleCommand(Commands cmd, String[] args, UserContext context) {
        if (args.length < 2) {
            return "Invalid username or password";
        }
        String username = args[0];
        String password = args[1];
        if (username.equals("admin") && password.equals("1234")) {
            context.Login(1 ,username, password, Role.ADMIN);
            return "You are logged in as admin";
        }
        if(username.equals("investor") && password.equals("10032")) {
            return "You are logged in as investor";
        }
        return "Invalid username or password";
    }
}
