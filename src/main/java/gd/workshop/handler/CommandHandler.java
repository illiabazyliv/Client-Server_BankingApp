package gd.workshop.handler;

import gd.workshop.entities.UserContext;

public interface CommandHandler {
    String handleCommand(String[] args , UserContext context);
}
