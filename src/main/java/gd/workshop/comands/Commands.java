package gd.workshop.comands;


import gd.workshop.roles.Role;

public enum Commands {

    HELP(false, Role.USER),
    LOGIN(false, Role.USER),
    LOGOUT(false, Role.USER),
    TRANSFER(false, Role.USER),
    BALANCE(false, Role.USER),
    RECEIPTS(false, Role.USER),
    TRANSACTIONS(false, Role.USER),

    // Investor commands
    INVEST(false, Role.INVESTOR),
    INVESTMENT(false, Role.INVESTOR),

    // Admin commands
    ADD_USER(true, Role.ADMIN),
    BLOCK_USER(true, Role.ADMIN),
    GET_USER(true, Role.ADMIN);

    private final boolean adminOnly;
    private final Role role;

    Commands(boolean adminOnly, Role role) {
        this.adminOnly = adminOnly;
        this.role = role;
    }


    public boolean isAdminOnly() { return adminOnly; }
    public Role getRole() { return role; }

    public static Commands from(String name) {
        return Commands.valueOf(name.toUpperCase());
    }

}
