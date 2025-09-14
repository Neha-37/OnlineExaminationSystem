
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import javax.swing.Timer;

public class OnlineExamination {
    private User user;
    private Exam exam;
    private int timeRemaining;
    private Timer timer;

    public static void main(String[] args) {
        OnlineExamination system = new OnlineExamination();
        system.setupExam();
        system.startLogin();
    }

    private void setupExam() {
        exam = new Exam(600); // 10 minutes (600 sec)

        exam.addQuestions(new Question("Which component of Java is responsible for running the compiled Java bytecode?",
                new String[]{"JDK", "JVM", "JRE", "JIT"}, 1));
        exam.addQuestions(new Question("Which of these is not a feature of Java?",
                new String[]{"Object-oriented", "Platform-independent", "Compiled", "Interpreted language"}, 2));
        exam.addQuestions(new Question("What is the purpose of the PATH environment variable in Java?",
                new String[]{"To locate Java libraries", "To store Java bytecode", "To locate the Java compiler", "To optimize Java code"}, 2));
        exam.addQuestions(new Question("Which feature of Java makes it possible to run a Java program on different platforms?",
                new String[]{"Object-Oriented", "Platform-Independent", "Syntax", "Memory Management"}, 1));
        exam.addQuestions(new Question("How should class names be written in Java?",
                new String[]{"camelCase", "snake_case", "PascalCase", "kebab-case"}, 2));
        exam.addQuestions(new Question("Which data type would be best for storing a person's age in Java?",
                new String[]{"float", "double", "int", "long"}, 2));
        exam.addQuestions(new Question("What is the default value of a boolean variable in Java?",
                new String[]{"true", "false", "0", "null"}, 1));
        exam.addQuestions(new Question("Which keyword is used to exit a loop prematurely in Java?",
                new String[]{"break", "continue", "exit", "stop"}, 0));
        exam.addQuestions(new Question("Which access modifier makes a member visible only within its own class?",
                new String[]{"public", "private", "protected", "default"}, 1));
        exam.addQuestions(new Question("Which of the following is used for compile-time polymorphism?",
                new String[]{"Method Overriding", "Method Overloading", "Inheritance", "Abstraction"}, 1));
    }

    private void startLogin() {
        JFrame loginFrame = new JFrame("Login");
        JTextField usernameField = new JTextField(20);
        JPasswordField passwordField = new JPasswordField(20);
        JButton loginButton = new JButton("Login");

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                user = new User(usernameField.getText(), String.valueOf(passwordField.getPassword()));
                loginFrame.dispose();
                startExam();
            }
        });

        loginFrame.setLayout(new FlowLayout());
        loginFrame.add(new JLabel("Username:"));
        loginFrame.add(usernameField);
        loginFrame.add(new JLabel("Password:"));
        loginFrame.add(passwordField);
        loginFrame.add(loginButton);
        loginFrame.setSize(300, 200);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setVisible(true);
    }

    private void startExam() {
        JFrame examFrame = new JFrame("Exam");
        JPanel examPanel = new JPanel();
        examPanel.setLayout(new BoxLayout(examPanel, BoxLayout.Y_AXIS));

        JLabel totalLabel = new JLabel("Total Questions: " + exam.getQuestions().size());
        examPanel.add(totalLabel);

        JLabel timerLabel = new JLabel("Time Remaining: " + exam.getTimeLimit() + " seconds");
        examPanel.add(timerLabel);

        List<ButtonGroup> answerGroups = new ArrayList<>();

        for (Question question : exam.getQuestions()) {
            JLabel questionLabel = new JLabel(question.getQuestionText());
            examPanel.add(questionLabel);

            ButtonGroup group = new ButtonGroup();
            for (int i = 0; i < question.getOptions().length; i++) {
                JRadioButton optionButton = new JRadioButton(question.getOptions()[i]);
                group.add(optionButton);
                examPanel.add(optionButton);
            }
            answerGroups.add(group);
        }

        JButton submitButton = new JButton("Submit");
        examPanel.add(submitButton);

        JScrollPane scrollPane = new JScrollPane(examPanel);
        examFrame.add(scrollPane);

        examFrame.setSize(600, 500);
        examFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        examFrame.setVisible(true);

        startTimer(examFrame, timerLabel);

        // Submit button functionality
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                List<Integer> selectedAnswers = new ArrayList<>();

                for (ButtonGroup group : answerGroups) {
                    int selectedIndex = -1;
                    int j = 0;
                    for (Enumeration<AbstractButton> buttons = group.getElements(); buttons.hasMoreElements(); j++) {
                        AbstractButton button = buttons.nextElement();
                        if (button.isSelected()) {
                            selectedIndex = j;
                            break;
                        }
                    }
                    selectedAnswers.add(selectedIndex);
                }

                int score = exam.calculateScore(selectedAnswers);

                if (timer != null) {
                    timer.stop();
                }
                examFrame.dispose();

                // Step 1: Show score with OK button
                JOptionPane.showMessageDialog(null,
                        "Your score: " + score + "/" + exam.getQuestions().size(),
                        "Exam Result",
                        JOptionPane.INFORMATION_MESSAGE);

                // Step 2: Show "Test Completed" with Logout button
                int logoutChoice = JOptionPane.showOptionDialog(null,
                        "Your test is completed.\nYou may logout.",
                        "Test Completed",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        null,
                        new String[]{"Logout"},
                        "Logout");

                if (logoutChoice == 0) {
                    // Step 3: Show "Logout Successful" with OK button
                    JOptionPane.showMessageDialog(null,
                            "You have logged out successfully.",
                            "Logout",
                            JOptionPane.INFORMATION_MESSAGE);

                    // Step 4: Exit application
                    System.exit(0);
                }
            }
        });
    }

    private void startTimer(JFrame examFrame, JLabel timerLabel) {
        if (timer != null) {
            timer.stop();
        }
        timeRemaining = exam.getTimeLimit();

        timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                timeRemaining--;
                timerLabel.setText("Time Remaining: " + timeRemaining + " seconds");

                if (timeRemaining <= 0) {
                    timer.stop();
                    JOptionPane.showMessageDialog(examFrame, "Time is up!");
                    examFrame.dispose();
                }
            }
        });
        timer.start();
    }
}
