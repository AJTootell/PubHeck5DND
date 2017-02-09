import java.util.ArrayList;
import java.util.Iterator;

public class Question{
	//the dilema offered to the player
	private String text;
	//Array of answers for the set question
	private ArrayList<Answer> answers;

    //private Answer ans1, ans2, ans3;
    private boolean questionused = false;
    
	Question(String text, ArrayList<Answer> answers){
		this.text = text;
		this.answers = answers;
	}
 	public boolean getQuestionUsed(){
	        return questionused;
	    }
	    
    public void printquestion(int moral, int order){
        questionused = true;
        System.out.println(text);
        Iterator<Answer> possibleanswers = answers.iterator();
        boolean threeanswers = false;
        
        //selects the first 2 answers as sutible answers to provide
        Answer ans1 = possibleanswers.next();
        int ans1difference = calculateDifference(ans1, moral, order);
        Answer ans2 = possibleanswers.next();
        int ans2difference = calculateDifference(ans2, moral, order);
        Answer ans3 = new Answer("test",1,1);
        if (answers.size() > 3) {
            //choose a third answer
            ans3 = possibleanswers.next();
            int ans3difference = calculateDifference(ans3, moral, order);
            threeanswers = true;
        }
        while(possibleanswers.hasNext()) {
            Answer singleanswer = possibleanswers.next();
            //if singleanswer is better, then replace
            int singleAnswerDifference = calculateDifference(singleanswer, moral, order);
            if (singleAnswerDifference < ans1difference) {
                  ans1 = singleanswer;
                  answers.set(0, singleanswer);
            } else {
                if (singleAnswerDifference < ans2difference) {
                    ans2 = singleanswer;
                    answers.set(1, singleanswer);
                }
            }
        }
        System.out.println("Answer 1" + ans1);
        System.out.println("Answer 2" + ans2);
        if (threeanswers) {
            System.out.println("Answer 3" + ans3);
        }
    }
	    
    public int calculateDifference(Answer singleAnswer, int moral, int order){
        int moralDifference = singleAnswer.getMoral()*4 - moral;
        int orderDifference = singleAnswer.getOrder()*4 - order;
        
        moralDifference = (int) Math.pow(moralDifference*moralDifference,0.5);
        orderDifference = (int) Math.pow(orderDifference*orderDifference,0.5);
        
        return moralDifference + orderDifference;
    }
    public ArrayList<Answer> getAnswers(){
    	return answers;
    }
}

