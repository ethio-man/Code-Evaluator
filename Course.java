public class Course {
    private String name;
    private String id;
User[] student=new Student[10];
    int Scount=0;
    public Course(String name, String id) {
        this.name = name;
        this.id = id;
    }
    public String getId() {
        return id;
    }
    public String getName() {
        return this.name;
    }
    public void addStudent(User s) {
        student[Scount]=s;
    }
}
