public class Register {
private User[] teachers=new Teacher[10];
private User[] students=new Student[10];
private Course[] courses=new Course[10];
private Assignment[] assignments=new Assignment[10];
private Submission[] submissions=new Submission[10];
int Tcount=0,Scount=0,Ccount=0,Acount=0,Bcount=0;
public User[] getTeachers(){
    return teachers;
}
public User[] getStudents(){
    return students;
}
public Course[] getCourse(){
    return courses;
}
public Assignment[] getAssignments(){
    return assignments;
}
public Submission[] getSubmissions(){
    return submissions;
}
    public User register(String name, String username, String password,Boolean tech){
        if (tech) {
            Teacher newTeacher=new Teacher(name,username,password);
            teachers[Tcount]=newTeacher;
            Tcount++;
            return teachers[Tcount-1];
        }
        else{
            Student newStudent=new Student(name,username,password);
            students[Scount]=newStudent;
            Scount++;
            return students[Scount-1];
        }
    }
    public void createCourse(Course c){
    courses[Ccount]=c;
    Ccount++;
    }
    public void createAssignment(Assignment a){
    assignments[Acount]=a;
    Acount++;
    }
    public void recieceAssignment(Submission s){
    submissions[Bcount]=s;
    Bcount++;
    }
}
