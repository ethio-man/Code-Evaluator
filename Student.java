
public class Student extends User {
    public Student(String name, String username, String password) {

        super(name, username, password);
    }

    @Override
    public String getRole() { return "Student"; }

}
