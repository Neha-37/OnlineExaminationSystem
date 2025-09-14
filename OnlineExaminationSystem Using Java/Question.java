public class Question {
    private String questionText;
    private String[] options;
    private int answerIndex;

    public Question(String questionText, String[] options, int answerIndex){
        this.questionText=questionText;
        this.options=options;
        this.answerIndex=answerIndex;
    }
    public String getQuestionText(){
        return questionText;
    }
    public String[] getOptions(){
        return options;
    }
    public boolean checkAnswer(int selectedOption){
        return selectedOption == answerIndex;
    }
}
