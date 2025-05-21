import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.String;
public class Main extends JFrame {
    CardLayout cardLayout = new CardLayout();
    JPanel mainPanel = new JPanel(cardLayout);
    Register register=new Register();
    private Course currentCourse;
    Assignment assign;
    public Main() {
        setTitle("Coding Exercise Checker");
        setSize(600, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        mainPanel.add(welcomePanel(), "welcome");
        mainPanel.add(registerPanel(), "register");
        mainPanel.add(loginPanel(), "login");
        mainPanel.add(teacherDashboard(), "teacher_dashboard");
        mainPanel.add(coursePanel(), "create_course");
        mainPanel.add(manageAssignmentPanel(), "manage_assignment");
        mainPanel.add(checkAssignmentsPanel(), "check_assignments");
        mainPanel.add(studentDashboardPanel(), "student_dashboard");
        add(mainPanel);
        cardLayout.show(mainPanel, "welcome");
        setVisible(true);
    }
    private JPanel welcomePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        JLabel title = new JLabel("<html><center><h1>Coding exercise<br/>checker and Evaluator</h1></center></html>", SwingConstants.CENTER);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton registerBtn = new JButton("REGISTER");
        JButton loginBtn = new JButton("LOGIN");
        registerBtn.setMaximumSize(new Dimension(150, 40));
        loginBtn.setMaximumSize(new Dimension(150, 40));
        registerBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        registerBtn.addActionListener(e -> cardLayout.show(mainPanel, "register"));
        loginBtn.addActionListener(e -> cardLayout.show(mainPanel, "login"));
        panel.add(Box.createVerticalGlue());
        panel.add(title);
        panel.add(Box.createRigidArea(new Dimension(0, 30)));
        panel.add(registerBtn);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(loginBtn);
        panel.add(Box.createVerticalGlue());
        return panel;
    }

    private JPanel registerPanel() {
        JPanel p = new JPanel(new GridBagLayout());
        p.setBackground(new Color(224, 255, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        String[] labels = {"Name:", "Username:", "Password:", "Role:"};
        JTextField name = new JTextField(15), user = new JTextField(15);
        JPasswordField pass = new JPasswordField(15);
        JRadioButton teacher = new JRadioButton("Teacher"), student = new JRadioButton("Student");
        ButtonGroup group = new ButtonGroup(); group.add(teacher); group.add(student);
        JButton submit = new JButton("Submit"), back = new JButton("Back");
        back.addActionListener(e -> cardLayout.show(mainPanel, "welcome"));
        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String names = name.getText().trim();
                String username = user.getText().trim();
                String password = new String(pass.getPassword());
                if (teacher.isSelected()) {
                   User x= register.register(names,username,password,true);
                    System.out.println(x.getName());
                } else if (student.isSelected()) {
                    User y=register.register(names,username,password,false);
                    System.out.println(y.getName());
                } else {
                    JOptionPane.showMessageDialog(null,"Role not selected!");
                }
            }
        });
        gbc.gridx = 0; gbc.gridy = 0; p.add(new JLabel(labels[0]), gbc);
        gbc.gridx = 1; p.add(name, gbc);
        gbc.gridx = 0; gbc.gridy = 1; p.add(new JLabel(labels[1]), gbc);
        gbc.gridx = 1; p.add(user, gbc);
        gbc.gridx = 0; gbc.gridy = 2; p.add(new JLabel(labels[2]), gbc);
        gbc.gridx = 1; p.add(pass, gbc);
        gbc.gridx = 0; gbc.gridy = 3; p.add(new JLabel(labels[3]), gbc);
        gbc.gridx = 1;
        JPanel rolePanel = new JPanel(); rolePanel.add(teacher); rolePanel.add(student);
        p.add(rolePanel, gbc);
        gbc.gridy = 4; p.add(submit, gbc);
        gbc.gridy = 5; p.add(back, gbc);
        return p;
    }

    private JPanel loginPanel() {
        JPanel p = new JPanel(new GridBagLayout());
        p.setBackground(new Color(200, 255, 200));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        JTextField user = new JTextField(15);
        JPasswordField pass = new JPasswordField(15);
        JRadioButton teacher = new JRadioButton("Teacher"), student = new JRadioButton("Student");
        ButtonGroup group = new ButtonGroup(); group.add(teacher); group.add(student);
        JButton loginBtn = new JButton("LOGIN"), back = new JButton("Back");
        back.addActionListener(e -> cardLayout.show(mainPanel, "welcome"));

        //loginBtn.addActionListener(e -> cardLayout.show(mainPanel, teacher.isSelected() ? "teacher_dashboard" : "student_dashboard"));
        loginBtn.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                String userName = user.getText().trim();
                String password = new String(pass.getPassword());
                if (teacher.isSelected()) {
                    User[] t= register.getTeachers();
                    boolean l=false;
                    for(int i=0;i<t.length;i++){
                        if(t[i] != null && t[i].checkPassword(password) && t[i].getUsername().equals(userName))
                            l=true;
                    }
                    if(l)
                        cardLayout.show(mainPanel , "teacher_dashboard");
                    else
                        JOptionPane.showMessageDialog(null, "Wrong username and password", "Login Failed", JOptionPane.WARNING_MESSAGE);
                } else if (student.isSelected()) {
                    User[] s= register.getStudents();
                    boolean l=false;
                    for(int i=0;i<s.length;i++){
                        if(s[i] != null && s[i].checkPassword(password) && s[i].getUsername().equals(userName))
                            l=true;
                    }
                    if(l) {
                        if (l) {
                            mainPanel.add(studentDashboardPanel(), "student_dashboard");
                            cardLayout.show(mainPanel, "student_dashboard");
                        }

                    }
                    else
                        JOptionPane.showMessageDialog(null, "Wrong username and password", "Login Failed", JOptionPane.WARNING_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null,"Role not selected!");
                }
            }
        });
        gbc.gridx = 0; gbc.gridy = 0; p.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1; p.add(user, gbc);
        gbc.gridx = 0; gbc.gridy = 1; p.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1; p.add(pass, gbc);
        gbc.gridx = 0; gbc.gridy = 2; p.add(new JLabel("Role:"), gbc);
        gbc.gridx = 1;
        JPanel rolePanel = new JPanel(); rolePanel.add(teacher); rolePanel.add(student);
        p.add(rolePanel, gbc);
        gbc.gridy = 3; p.add(loginBtn, gbc);
        gbc.gridy = 4; p.add(back, gbc);
        return p;
    }
    private JPanel teacherDashboard() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(new Color(240, 240, 255));
        JLabel title = new JLabel("Teacher Dashboard", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton createCourse = new JButton("Create Course"), manageAssignments = new JButton("Manage Assignments"), checkAssignments = new JButton("Check Assignments");
        createCourse.setAlignmentX(Component.CENTER_ALIGNMENT);
        manageAssignments.setAlignmentX(Component.CENTER_ALIGNMENT);
        checkAssignments.setAlignmentX(Component.CENTER_ALIGNMENT);
        createCourse.addActionListener(e -> cardLayout.show(mainPanel, "create_course"));
        manageAssignments.addActionListener(e -> cardLayout.show(mainPanel, "manage_assignment"));
        checkAssignments.addActionListener(e ->{
            mainPanel.add(checkAssignmentsPanel(), "check_assignments"); // correct key
            cardLayout.show(mainPanel, "check_assignments");
        });
        p.add(Box.createRigidArea(new Dimension(0, 30))); p.add(title); p.add(Box.createRigidArea(new Dimension(0, 20)));
        p.add(createCourse); p.add(Box.createRigidArea(new Dimension(0, 15)));
        p.add(manageAssignments); p.add(Box.createRigidArea(new Dimension(0, 15))); p.add(checkAssignments);
        p.add(bottomPanel());
        return p;
    }

    private JPanel coursePanel() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(new Color(230, 245, 250));
        JLabel title = new JLabel("Create Course");
        title.setFont(new Font("SansSerif", Font.BOLD, 18));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        JTextField courseName = new JTextField(15), courseId = new JTextField(15);
        JButton create=new JButton("Create");
        JButton addStudent = new JButton("Add Student");
        JPanel formPanel = new JPanel(new GridLayout(4,2,10,10));
        formPanel.setMaximumSize(new Dimension(300,100));
        formPanel.setOpaque(false);
        formPanel.add(new JLabel("Course Name:")); formPanel.add(courseName);
        formPanel.add(new JLabel("Course ID:")); formPanel.add(courseId);
        formPanel.add(new JLabel(""));
        formPanel.add(create);
        formPanel.add(addStudent);
        create.addActionListener(e -> {
            String courseNameText = courseName.getText().trim();
            String courseIdText = courseId.getText().trim();
            if (!courseNameText.isEmpty() && !courseIdText.isEmpty()) {
                currentCourse = new Course(courseNameText, courseIdText);
                register.createCourse(currentCourse);
                JOptionPane.showMessageDialog(null, "Course created successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "Course name and ID cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        addStudent.addActionListener(e -> {
            if (currentCourse == null) {
                JOptionPane.showMessageDialog(null, "Please create a course first.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String username = JOptionPane.showInputDialog(null, "Enter Student's Username:");
            if (username != null && !username.trim().isEmpty()) {
                User[] students = register.getStudents();
                for (User student : students) {
                    if (student != null && student.checkUsername(username.trim())) {
                        currentCourse.addStudent(student);
                        JOptionPane.showMessageDialog(null, username + " added to course.");
                        return;
                    }
                }
                JOptionPane.showMessageDialog(null, "Student not found!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        p.add(Box.createVerticalStrut(20));
        p.add(title);
        p.add(Box.createVerticalStrut(20));
        p.add(formPanel); p.add(bottomPanel());
        return p;
    }

    private JPanel manageAssignmentPanel() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(new Color(255, 250, 240));
        JLabel title = new JLabel("Manage Assignment");
        title.setFont(new Font("SansSerif", Font.BOLD, 18));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        JTextField atitle = new JTextField(20), input = new JTextField(20), output = new JTextField(20), deadline = new JTextField(20);
        JTextArea desc = new JTextArea(3,20), instr = new JTextArea(3,20);
        JButton create = new JButton("CREATE");
        p.add(Box.createVerticalStrut(15)); p.add(title); p.add(Box.createVerticalStrut(15));
        p.add(labeled("Assignment Title:", atitle));
        p.add(labeled("Description:", new JScrollPane(desc)));
        p.add(labeled("Instructions:", new JScrollPane(instr)));
        p.add(labeled("Test Case Input:", input));
        p.add(labeled("Test Case Output:", output));
        p.add(labeled("Deadline:", deadline));
        p.add(Box.createVerticalStrut(10)); create.setAlignmentX(Component.CENTER_ALIGNMENT); p.add(create);
        create.addActionListener(e -> {
            String titl=atitle.getText();
            String descr=desc.getText();
            String instact=instr.getText();
            String inp=input.getText();
            String outp=output.getText();
            String dd=deadline.getText();
            if(!titl.isEmpty() && !descr.isEmpty() && !instact.isEmpty() && !inp.isEmpty() && !outp.isEmpty() && !dd.isEmpty()) {
                 assign = new Assignment(titl, descr, instact, inp, outp, dd);
                register.createAssignment(assign);
                JOptionPane.showMessageDialog(p, "Assignment created successfully!");
            }
            else
                JOptionPane.showMessageDialog(null, "Fill all required fields.", "Error", JOptionPane.ERROR_MESSAGE);
        });
        p.add(bottomPanel());
        return p;
    }

    private JPanel checkAssignmentsPanel() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(new Color(245, 255, 245));
        JLabel title = new JLabel("Check Assignments", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 18));
        p.add(title, BorderLayout.NORTH);
        String[] cols = {"Student Name", "Deadline Status", "Plagiarism","code status", "Special Consideration"};
        User[] info=register.getStudents();
        Object[][] data =new Object[info.length][5];
        for(int i=0;i<info.length;i++) {
            if(info[i] !=null) {
                data[i][0] = info[i].getName();
                data[i][1] = "Early";
                data[i][2] = "No";
                data[i][3]=info[i].result;
                data[i][4] = info[i].result == "Compilation Error:" ? "Yes" : "No";
            }
        }
        JTable table = new JTable(new DefaultTableModel(data, cols));
        table.setFillsViewportHeight(true);
        p.add(new JScrollPane(table), BorderLayout.CENTER);
        p.add(bottomPanel(), BorderLayout.SOUTH);
        return p;
    }

    private JPanel studentDashboardPanel() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(new Color(240, 245, 255));
        JLabel title = new JLabel("Student Dashboard");
        title.setFont(new Font("SansSerif", Font.BOLD, 18));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));
        JTextArea leftArea = new JTextArea(10, 20);
        leftArea.setLineWrap(true);
        leftArea.setWrapStyleWord(true);
        leftArea.setEditable(false);
        JScrollPane leftScroll = new JScrollPane(leftArea);
        container.add(leftScroll);
        container.add(Box.createRigidArea(new Dimension(20, 0)));
        JTextArea rightArea = new JTextArea(10, 20);
        rightArea.setLineWrap(true);
        rightArea.setWrapStyleWord(true);
        rightArea.setEditable(false);
        JScrollPane rightScroll = new JScrollPane(rightArea);
        container.add(rightScroll);
             p.add(container);
        Assignment[] a = register.getAssignments();
       if (a[0] != null) {
            leftArea.setText(a[0].title+"\nInstructions:\n" + a[0].instruction);
            rightArea.setText("Description:\n" + a[0].description);
        } else {
            leftArea.setText("Instructions\n(No assignment available yet)");
            rightArea.setText("Description:\n(No assignment available yet)");
        }
        JTextField user = new JTextField(20);
        JTextArea code = new JTextArea(5, 20);
        JButton submit = new JButton("Submit"), assess = new JButton("Assessment");
        p.add(Box.createVerticalStrut(20)); p.add(title); p.add(Box.createVerticalStrut(20));
        p.add(labeled("Student UserName:", user)); p.add(labeled("Code:", new JScrollPane(code)));
        p.add(Box.createVerticalStrut(10));
        JPanel btnPanel = new JPanel(); btnPanel.setOpaque(false); btnPanel.add(submit); btnPanel.add(assess);
        p.add(btnPanel);
        submit.addActionListener(e -> {
            String usr=user.getText();
            String cod=code.getText();
            if(!usr.isEmpty() && !cod.isEmpty()) {
                Submission sub=new Submission(usr,cod);
                String resul=sub.evaluateUserCode(cod,a[0].input,a[0].output);

                User[] stud=register.getStudents();
                for(int i=0;i<stud.length;i++){
                    if(stud[i].getUsername().equals(usr))
                        stud[i].result=resul;
                }
register.recieceAssignment(sub);
                JOptionPane.showMessageDialog(p, "Code submitted for review.");
            }
            else
                JOptionPane.showMessageDialog(null, "Fill all required fields.", "Error", JOptionPane.ERROR_MESSAGE);
        });
        assess.addActionListener(e -> JOptionPane.showMessageDialog(p, "Assessment Result:\nPassed: Yes\nPlagiarism: No\nDeadline: Early"));
        p.add(bottomPanel());
        return p;
    }

    private JPanel labeled(String label, Component field) {
        JPanel p = new JPanel(new BorderLayout(5, 5));
        p.setMaximumSize(new Dimension(400, 60));
        p.setOpaque(false);
        p.add(new JLabel(label), BorderLayout.WEST);
        p.add(field, BorderLayout.CENTER);
        return p;
    }

    private JPanel bottomPanel() {
        JPanel b = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        b.setOpaque(false);
        JButton main = new JButton("Main");
        main.addActionListener(e -> cardLayout.show(mainPanel, "welcome"));
        b.add(main); return b;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}