
public class Char {
	private String name;
	//postive for good negative for evil
	private int moral;
	//positive for lawful negative for chaotic
	private int order;
	//room the charecter is currently stood in
	private Room position;
	
	Char(String name, Room start){
		this.name = name;
		moral = 0;
		order = 0;
		position = start;
	}
	String getName(){
		return name;
	}
	int getMoral(){
		return moral;
	}
	int getOrder(){
		return order;
	}
	void setMoral(int moral){
		this.moral = moral;
	}
	void setOrder(int order){
		this.order = order;
	}
	//change moral value by set amount
	void incrementMoral(int moralChange){
		moral += moralChange;
	}
	//change order value by set amount
	void incrementOrder(int orderChange){
		order += orderChange;
	}
	
	Room getPosition(){
		return position;
	}
	
	void setPosition(Room position){
		this.position = position;
	}
}
