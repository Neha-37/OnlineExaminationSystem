import java.util.ArrayList;
import java.util.List;

public class Exam {
    private List<Question> questions;
    private int timeLimit;

    public Exam(int timeLimit){
        this.questions = new ArrayList<>();
        this.timeLimit = timeLimit;
    }
    public void addQuestions(Question question){
        questions.add(question);
    }
    public List<Question> getQuestions(){
        return questions;
    }
    public int getTimeLimit(){
        return timeLimit;
    }
    public int calculateScore(List<Integer> answers){
        int score=0;
        for (int i = 0; i < questions.size(); i++) {
            if (questions.get(i).checkAnswer(answers.get(i))){
                score++;
            }
        }
        return score;
    }
}
