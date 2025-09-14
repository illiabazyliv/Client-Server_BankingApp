package gd.workshop.handler;

import gd.workshop.comands.Commands;
import gd.workshop.entities.UserContext;
import gd.workshop.roles.Role;

import java.util.HashMap;
import java.util.Map;

public class CommandRouter {
    private final Map<Commands, CommandHandler> commandHandlers = new HashMap<>();

    public void Register(Commands command , CommandHandler commandHandler) {
        commandHandlers.put(command, commandHandler);
    }
    public String Route(String input , UserContext userContext) {
        String[] parts = input.split(" ");
        String cmdName = parts[0].toUpperCase();
        String[] args = parts.length > 1 ? java.util.Arrays.copyOfRange(parts, 1, parts.length) : new String[0];

        try {
            Commands cmd = Commands.valueOf(cmdName);
            if(cmd.isAdminOnly() && userContext.getRole() != Role.ADMIN){
                return "You do not have permission to use this command";
            }
            CommandHandler handler = commandHandlers.get(cmd);
            if(handler == null){
                return "Command not found for command :" + cmd + "Type < HELP > for additional commands";
            }
            return handler.handleCommand(cmd,args ,userContext);
        }
        catch (IllegalArgumentException e){
            return "ERROR: : Unknown Command";
        }
    }
}
