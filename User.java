public abstract class User {
    protected String name;
    protected String username;
    protected String password;
    String result;
    public User(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }
    public String getUsername() { return username; }
    public String getName() { return this.name; }
    public boolean checkPassword(String pwd) { return this.password.equals(pwd); }
public boolean checkUsername(String usr){return this.username.equals(usr);}
    public abstract String getRole();
}
