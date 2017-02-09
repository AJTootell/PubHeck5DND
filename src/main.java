import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class main {
	
	private static int noOfQ = 6;
	
	public static void main(String[] args) {
		
		ArrayList<ArrayList<Answer>> allAnswers = createAnswers();
		ArrayList<Question> allQuestions = createQuestions(allAnswers);
		ArrayList<Room> map = createMap();
		
		placeQuestions(map);
		Canvas page = drawMap(map);

		BufferedImage myPicture;
		try {
			myPicture = ImageIO.read(new File("C:/Users/H080/workspace/Dungeons and dumbasses/src/donkey.png"));
			JLabel picLabel = new JLabel(new ImageIcon(myPicture));
			page.drawImage(myPicture, -20, 300);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Char player = createChar(map);
		intro(player,page);
		boolean cont = true;
		while(cont == true){
			step(page,player,map, allQuestions);
		}
	}
	//draw the blank rooms
	public static Canvas drawMap(ArrayList<Room> map){

		Canvas page = new Canvas("Dungeons and dumbasses", 505,800);
		page.setForegroundColor(Color.green);
		page.fillRectangle(0, 325, 700, 700);
		page.setForegroundColor(Color.blue);
		page.fillRectangle(0, 0, 700, 325);
		page.setVisible(true);
		page.setForegroundColor(Color.gray);
		for(Room here : map){
			page.fillRectangle(here.getX()*100 + 5, here.getY()*65 + 5, 95, 60);
		}
		
		return page;
	}
	
	//Generate the rooms and their position to each other
	public static ArrayList<Room> createMap(){
		ArrayList<Room> map = new ArrayList<Room>();
		for(int j =0 ; j < 5; j++){
			for(int i =0 ; i < 5; i++){
				map.add(new Room(i,j));
			}	
		}
		map.get(0).setRoom(2,map);
		map.get(1).setRoom(2,map);
		map.get(2).setRoom(2,map);
		//map.get(2).setRoom(-1,map);
		map.get(5).setRoom(2,map);
		map.get(6).setRoom(2,map);
		//map.get(7).setRoom(-1,map);
		map.get(9).setRoom(-1,map);
		map.get(11).setRoom(-1,map);
		map.get(11).setRoom(2,map);
		map.get(12).setRoom(2,map);
		//map.get(13).setRoom(-1,map);
		map.get(13).setRoom(2,map);
		//map.get(17).setRoom(-1,map);
		map.get(17).setRoom(2,map);
		map.get(22).setRoom(2,map);
		map.get(23).setRoom(2,map);
		
		return map;
	}
	
	public static void placeQuestions(ArrayList<Room> map){
		map.get(0).setDilemma(true);
		map.get(5).setDilemma(true);
		map.get(9).setDilemma(true);
		map.get(16).setDilemma(true);
		map.get(18).setDilemma(true);
		map.get(24).setDilemma(true);
		
	}
	
	public static Char createChar(ArrayList<Room> map){
		String input;
		Scanner user_input = new Scanner(System.in);
        System.out.print("Enter chracter name: ");
        input = user_input.next();
        Char player = new Char(input,map.get(2));
        return player;
	}
	
	//introduction to the gameplay
	public static void intro(Char player, Canvas page){
		//page.setForegroundColor(Color.black);
		//page.drawString("Your name is " + player.getName() + " and you have just scaled the 30 meter high trump-wall to america with your beloved partner and children, to escape a crazed donkey set on you by drug lords.", 5, 340);
		//page.setForegroundColor(Color.red);
		System.out.println("Your name is " + player.getName() + " and you have just scaled the 30 meter high Trump-wall to america with your beloved partner and children.");
	}
	
	public static void step(Canvas page, Char player, ArrayList<Room> map, ArrayList<Question> allQuestions){
		Room current = player.getPosition();
		page.fillRectangle(current.getX()*100 + 5, current.getY()*65 + 5, 95, 60);
		page.setForegroundColor(Color.green);
		page.fillCircle(current.getX()*100 + 55, current.getY()*65 + 35, 10);
		Scanner dirScan = new Scanner(System.in);
		ArrayList<String> dirs = new ArrayList<String>();
		if(current.getUp() != null){
			dirs.add("w");
			dirs.add("up");
		}
		if(current.getDown() != null){
			dirs.add("s");
			dirs.add("down");
		}
		if(current.getLeft() != null){
			dirs.add("a");
			dirs.add("left");
		}
		if(current.getRight() != null){
			dirs.add("d");
			dirs.add("right");
		}
		System.out.println("Choose a direction to move in " + dirs +": ");
		String direction = dirScan.next();
		if(!(dirs.contains(direction))){
			System.out.println("Invalid input");
		}
		else{
		switch(direction){
		case("w"):
		case("up"):
			player.setPosition(map.get(current.getId() -5));
			break;
		case("a"):
		case("left"):
			player.setPosition(map.get(current.getId() -1));
			break;
		case("s"):
		case("down"):
			player.setPosition(map.get(current.getId() +5));
			break;
		case("d"):
		case("right"):
			player.setPosition(map.get(current.getId() +1));
			break;
		default:
			System.out.println("Invalid input");
			break;
		}
		
		}
		
		if (player.getPosition().getDilemma() == true){
			player.getPosition().setDilemma(false);
			Question temp = chooseQuestion(allQuestions);
			temp.printquestion(player.getMoral(),player.getOrder());
			Scanner input_ans = new Scanner(System.in);
			int ans = input_ans.nextInt();
			processAnswer(temp.getAnswers().get(ans-1), player);
			
			if (map.get(2).getDown() == null){
				map.get(2).setRoom(-1,map);
			}	
			else if (map.get(7).getDown() == null){
				map.get(7).setRoom(-1,map);
			}	
			else if (map.get(13).getDown() == null){
				map.get(13).setRoom(-1,map);
			}	
			else if (map.get(17).getDown() == null){
				map.get(17).setRoom(-1,map);
			}
			else{
				finishGame(player);
			}

			
		}
		page.setForegroundColor(Color.red);
		page.fillCircle(current.getX()*100 + 55, current.getY()*65 + 35, 10);
	}
	//generate array based library for all the questions
	public static ArrayList<Question> createQuestions(ArrayList<ArrayList<Answer>> allAnswers){
		
		ArrayList<Question> allQuestions = new ArrayList<Question>();
		
		Question one = new Question("Just after climbing the wall, you are met with a guard who is having a heart attack, you feel you should help him, but in doing so, it could jeopardise your escape to America!", allAnswers.get(0));
		
		allQuestions.add(one);
		
		Question two = new Question("You need to escape from this room quickly as it is being filled with a poisonus gas. There is only one exit which can fit only one person at a time through, and the last person through might not make it out alive.", allAnswers.get(1));
		
		allQuestions.add(two);
		
		Question three = new Question("You see another family escaping from mexico, and they are holding another guard at gun-point, and you are almost certain that they are going to pull the trigger.", allAnswers.get(2));
		
		allQuestions.add(three);
		
		Question four = new Question("In this room of the wall, you find Trump donating several million dollars of money he stole to a childrens orphanage. Do you tell the authorities about the stolen money and leave the orphans hungry, or do you keep quiet about the money knowing that it was obtained illegaly.", allAnswers.get(3));
		
		allQuestions.add(four);
		
		Question five = new Question("You become lost, and have no idea of where to go", allAnswers.get(4));
		
		allQuestions.add(five);
		
		Question six = new Question("You find another family trying to get to America.", allAnswers.get(5));
		
		allQuestions.add(six);
		
		return allQuestions;
	}
	//generate array based library for all the answers

	public static ArrayList<ArrayList<Answer>> createAnswers(){
		ArrayList<ArrayList<Answer>> allAnswers = new ArrayList<ArrayList<Answer>>();
		ArrayList<Answer> one = new ArrayList<Answer>();
		
		Answer oneOne = new Answer("Give the guard a first aid kit, and wish them a good day.",2,1);
		one.add(oneOne);
		
		Answer oneTwo = new Answer("Give the guard a first aid kit, and tie him up.",1,-2);
		one.add(oneTwo);
		
		Answer oneThree = new Answer("Pick up a taser and shoot him, so he *probably* saved, but left unconcious.",2,-1);
		one.add(oneThree);
		
		Answer oneFour = new Answer("Give the guard CPR, and wish them a good day.",3,3);
		one.add(oneFour);
		
		Answer oneFive = new Answer("Give the guard CPR, then kick them off the wall.",-3,-3);
		one.add(oneFive);
		
		Answer oneSix = new Answer("Put the guard out of their missery.",-2,-2);
		one.add(oneSix);
		
		Answer oneSeven = new Answer("Just keep running!",-1,1);
		one.add(oneSeven);
		
		allAnswers.add(one);
		
		ArrayList<Answer> two = new ArrayList<Answer>();

		Answer twoOne = new Answer("Let your family crawl through first, then you follow afterwards, risking your death and the survival of your family.",3,3);
		two.add(twoOne);

		Answer twoTwo = new Answer("Throw your family through the escape like bouncers throw people out of pubs in cartoons, ensuring that everyone including you make it out alive.",+3,-2);
		two.add(twoTwo);

		Answer twoThree = new Answer("Run through the whole first, risking the death of one of your family.",-3,-3);
		two.add(twoThree);

		Answer twoFour = new Answer("Turn in to the hulk and break the wall to allow your family through, ensuring everyone including you make it out alive.",+3,-3);
		two.add(twoFour);

		allAnswers.add(two);
		
		ArrayList<Answer> three = new ArrayList<Answer>();

		Answer threeOne = new Answer("Taser the guard",1,-3);
		three.add(threeOne);

		Answer threeTwo = new Answer("Carefully negotiate with the family and guard so that no-one gets shot and the family is able to escape with you.",3,3);
		three.add(threeTwo);

		Answer threeThree = new Answer("Run for your lives.",-1,1);
		three.add(threeThree);

		Answer threeFour = new Answer("As you run past the guard, you give them a wedgie.",1,-3);
		three.add(threeFour);

		Answer threeFive = new Answer("Convince the family to pull the trigger.",-3,2);
		three.add(threeFive);
		
		allAnswers.add(three);
		
		ArrayList<Answer> four = new ArrayList<Answer>();

		Answer fourOne = new Answer("Keep quiet",2,-2);
		four.add(fourOne);

		Answer fourTwo = new Answer("Tell the Authorities",-3,2);
		four.add(fourTwo);

		allAnswers.add(four);
		
		ArrayList<Answer> five = new ArrayList<Answer>();

		Answer fiveOne = new Answer("Split up in to as many groups as you can.",-1,-2);
		five.add(fiveOne);

		Answer fiveTwo = new Answer("Keep together, saftey in numbers.",1,2);
		five.add(fiveTwo);

		Answer fiveThree = new Answer("Tell your family to stay still while you look for the exit, then come back for them when you have found it.",2,1);
		five.add(fiveThree);
		
		allAnswers.add(five);
		
		ArrayList<Answer> six = new ArrayList<Answer>();

		Answer sixOne = new Answer("Stick with them and help them through the wall",3,3);
		six.add(sixOne);

		Answer sixTwo = new Answer("Misdirect the other family so that they un-knowingly distract the guards and help you escape, leaving the other family trapped inside.",-3,-3);
		six.add(sixTwo);

		Answer sixThree = new Answer("Misdirect the other family down paths that you may think be the exit, using them as a search party.",-3,-2);
		six.add(sixThree);

		Answer sixFour = new Answer("Wish them good luck and move on by yourselves.",1,0);
		six.add(sixFour);

		Answer sixFive = new Answer("Give them some tools you found along the way (eg. crowbar) to help them escape.",1,1);
		six.add(sixFive);

		Answer sixSix = new Answer("You dont trust them and belive them to be guards in disguise, you hastily move onwards.",-1,2);
		six.add(sixSix);
		
		allAnswers.add(six);
		
		return	allAnswers;
	}

    public static void processAnswer(Answer answerselected, Char playerOne){
        playerOne.incrementMoral(answerselected.getMoral());
        playerOne.incrementOrder(answerselected.getOrder());
    }
    
    public static Question chooseQuestion(ArrayList<Question> allQuestions){
        int random = (int)(Math.random() * noOfQ);
        while (allQuestions.get(random).getQuestionUsed() == true) {
            random = (int)(Math.random() * noOfQ);
        }
        return allQuestions.get(random);
    }
    public static void finishGame(Char playerOne){
        System.out.println('\f');
        System.out.println("Hello ");
        System.out.print(playerOne.getName());
        System.out.print("!");
        if (playerOne.getOrder() == 0){
            System.out.println("You seem very conflicted in your decisions, and should probably see a psychiatrist.");
        } else {
        if (playerOne.getOrder() > 0) {
            System.out.println("Theres a good chance that you're lawfull ");
         }
        if (playerOne.getOrder() < 0) {
            System.out.println("Theres a good chance that you're chaotic ");
        }
        if (playerOne.getMoral() > 0) {
            System.out.print("Good! You are a Hero!");
        }
        if (playerOne.getMoral() < 0) {
            System.out.print("Evil! You are a Villan!");
        }
        }
        System.out.println("You have scored ");
        System.out.print(playerOne.getMoral()+12);
        System.out.print("/24 on the good-evil scale, and ");
        System.out.print(playerOne.getOrder()+12);
        System.out.print("/24 on the lawful-chaotic scale!");
    }
}
