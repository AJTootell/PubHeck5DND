
public class Answer {
	//text fro the answers shown
	private String text;
	//change in moral when picked
	private int moralValue;
	//change in order when picked
	private int orderValue;
	
	Answer(String text, int moralValue, int orderValue){
		this.text = text;
		this.moralValue = moralValue;
		this.orderValue = orderValue;
	}	
	public String toString(){
		return text;
	}
	public int getMoral(){
	    return moralValue;
	}
	public int getOrder(){
	    return orderValue;
	}
}
