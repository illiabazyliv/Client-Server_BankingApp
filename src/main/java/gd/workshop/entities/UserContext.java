package gd.workshop.entities;

import gd.workshop.roles.Role;

public class UserContext {
    private int UserId;
    private String login;
    private String password;
    private Role role;
    private boolean loggedIn;
    public UserContext() {
        this.role= Role.USER;
        this.loggedIn=false;
    }
    public void Login(int UserId, String login, String password, Role role) {
        this.UserId=UserId;
        this.login=login;
        this.password=password;
        this.role=role;
        this.loggedIn=true;
    }
    public void logout(){
    this.loggedIn=false;
    this.UserId = 0;
    this.login = null;
    this.role= Role.USER;
    }
    public boolean isLoggedIn() {
        return loggedIn;
    }
    public Role getRole(){
        return role;
    }
    public boolean isAdmin(){
        return role.equals(Role.ADMIN);
    }
    public boolean isUser(){
        return role.equals(Role.USER);
    }
    public boolean isInvestor(){
        return role.equals(Role.INVESTOR);
    }
    public int getUserId() {
        return UserId;
    }
}
