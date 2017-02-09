import java.util.ArrayList;

public class Room {
	//rooms surrounding this room
	private Room up;
	private Room down;
	private Room left;
	private Room right;
	//location within the array
	private int x;
	private int y;
	//question fund in this room
	private boolean dilemma;
	//id in the arraylist map
	private int id;
	
	Room(int x, int y){
		this.x = x;
		this.y = y;
		id = y*5 + x;	

		dilemma = false;
		up= null;
		down = null;
		left = null;
		right = null;
	}
	
	//set which rooms are in each direction 1,2,-1,-2 up right down left.
	void setRoom(int direction, ArrayList<Room> map){
		switch(direction){
		case(1):
			Room upRoom = map.get(id -5);
			up = upRoom;
			if(upRoom.getDown() == null){
				upRoom.setRoom(-direction, map);
			}
			break;
		case(2):
			Room rightRoom = map.get(id +1);
			right = rightRoom;
			if(rightRoom.getLeft() == null){
				rightRoom.setRoom(-direction, map);
			}
			break;
		case(-1):
			Room downRoom = map.get(id +5);
			down = downRoom;
			if(downRoom.getUp() == null){
				downRoom.setRoom(-direction, map);
			}
			break;
		case(-2):
			Room leftRoom = map.get(id -1);
			left = leftRoom;
			if(leftRoom.getRight() == null){
				leftRoom.setRoom(-direction, map);
			}
			break;
		default:
			break;
		}
	}
	void setDilemma(boolean dilemma){
		this.dilemma = dilemma;
	}
	boolean getDilemma(){
		return dilemma;
	}
	
	Room getUp(){
		return up;
	}
	Room getDown(){
		return down;
	}
	Room getLeft(){
		return left;
	}
	Room getRight(){
		return right;
	}
	
	int getX(){
		return x;
	}
	
	int getY(){
		return y;
	}
	
	int getId(){
		return id;
	}
	
}
