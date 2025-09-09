package gd.workshop.handler;

import gd.workshop.comands.Commands;
import gd.workshop.entities.UserContext;

public interface CommandHandler {
    String handleCommand(Commands cmd, String[] args , UserContext context);
}
