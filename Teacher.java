import java.util.ArrayList;
public class Teacher extends User {
    public Teacher(String name, String username, String password) {
        super(name, username, password);
    }
    @Override
    public String getRole() {
        return "Teacher";
    }
}
