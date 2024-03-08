//////////////////////////////////////////////////////////////////////////////////////////////
//                                                                                          //
// Bolatbek Amiyev                                                                          //
// CS622 TicTacToe project                                                                  //
// Logic class based on TicTacToe class                                                     //
// All about game logic                                                                     //
// Win positions, logic of the player in the game, logic of the computer in the game,       //
// how to win, how to play with computer or friend. Computers battle and reset everything.  //
//////////////////////////////////////////////////////////////////////////////////////////////

import java.security.SecureRandom;   //for unpredictable random values
import java.util.*;                  //for everything

public class Logic {

    //stores all available positions
    private ArrayList<Integer> notTakenPositions = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));

    //stores all winning positions
    private static List<List> winPositions;

    //store first player's all move
    public final ArrayList<Integer> firstPlayerMove = new ArrayList<>();

    //store second player's all move
    public final ArrayList<Integer> secondPlayerMove = new ArrayList<>();

    private static final int counts = 10; //For loops

    SecureRandom random = new SecureRandom();  //for random values
    Scanner input = new Scanner(System.in);    //for user's keyboard values
    TicTacToe base = new TicTacToe();          //creates object of the class

    //constructor will create board
    public Logic() {
        base.createBoard();
    }

    //method returns all winning positions
    private static List<List> getWinPositions() {
        winPositions = new ArrayList<>();
        List firstRow = Arrays.asList(1, 2, 3);
        List secondRow = Arrays.asList(4, 5, 6);
        List thirdRow = Arrays.asList(7, 8, 9);
        List leftColumn = Arrays.asList(1, 4, 7);
        List centerColumn = Arrays.asList(2, 5, 8);
        List rightColumn = Arrays.asList(3, 6, 9);
        List firstCross = Arrays.asList(1, 5, 9);
        List secondCross = Arrays.asList(3, 5, 7);
        winPositions.add(firstRow);
        winPositions.add(secondRow);
        winPositions.add(thirdRow);
        winPositions.add(leftColumn);
        winPositions.add(centerColumn);
        winPositions.add(rightColumn);
        winPositions.add(firstCross);
        winPositions.add(secondCross);
        return winPositions;
    }

    //method checks user's input while playing and returns user input if it is correct
    private int getPositionValue (TicTacToe.Pieces piece) {
        while (true) {
            try {   //try to take int number between 1-9
                System.out.printf("Enter slot number to place %s in: ", piece);   //prints which player should give slot number
                int positionValue = input.nextInt();     //gets value from user's keyboard
                if (positionValue >= 1 && positionValue <= 9) { //int number should be between 1 <= positionValue <= 9
                    for (int notTakenPosition : notTakenPositions) {  //for loop iterates all not taken slot
                        if (positionValue == notTakenPosition) {   //if slot not taken then remove from array and return slot value
                            notTakenPositions.remove(Integer.valueOf(positionValue));    //remove from array
                            return positionValue; //returns correct int number for not taken slot
                        }
                    }//if slot taken just exit loop and throw exception
                    throw new ArithmeticException("This slot taken, please choose different slot");
                } else { //if int number not between 1-9 throw exception
                    throw new ArithmeticException("Please, enter int number 1-9!");
                }
            } catch (ArithmeticException e) { //catch exceptions and prints message
                System.out.println(e.getMessage());
                input.nextLine();
            } catch (InputMismatchException e) { //if user input not int number then throw exception
                System.out.println("Please, enter int type number 1-9!");
                input.nextLine();
            }

        }
    }

    //method implements the logic of the player. Takes player's array for storing move and piece (X or O)
    public void playerLogic(ArrayList<Integer> playersMove, TicTacToe.Pieces piece) {
        int positionValue = getPositionValue(piece);
        playersMove.add(positionValue);                   //add player's position to related array
        base.putPieceToPosition(positionValue, piece);    //add piece to given position on board
        base.createGrid();                                //creates updated grid with values
    }

    //method returns true if someone won, otherwise false
    public boolean isSomeoneWon(ArrayList<Integer> playerMoves) {
        List<List> winPositions = getWinPositions(); //takes all win positions
        for (List winPosition : winPositions) {     //go through each win combination
            if (playerMoves.containsAll(winPosition)) {  //if player's move array contains all win position then he won
                return true;
            }
        }
        return false;   //if no win positions return false
    }

    //computer algorithm for playing Tic Tac Toe. Takes own array, opponents array and computer's piece(X or O)
    public void computerLogic(ArrayList<Integer> firstPlayerMove, ArrayList<Integer> secondPlayerMove, TicTacToe.Pieces piece) {
        int randomMove;         //for random positions
        int defenseMove;        //for defense positions
        int winMove;            //for win positions
        if (notTakenPositions.size() == 9) {   //if computer goes first then put piece to random position
            randomMove = random.nextInt(9) + 1;    //generate random number from bound 0-8 not includes 9, but we need 1-9 that's why we add 1
            firstPlayerMove.add(randomMove);              //add computer's position to related array
            notTakenPositions.remove(Integer.valueOf(randomMove));    //remove position from available positions array
            base.putPieceToPosition(randomMove, piece);           //add piece to given position on board
        } else {
            for (int notTakenPosition : notTakenPositions) {   //go through each available position
                firstPlayerMove.add(notTakenPosition);         //add available position into computer's array
                if (isSomeoneWon(firstPlayerMove)) {           //if added position helps to win then this is winning position
                    winMove = notTakenPosition;
                    notTakenPositions.remove(Integer.valueOf(winMove));  //remove position from available positions array
                    base.putPieceToPosition(winMove, piece);             //add piece to given position on board
                    return;                                              //exit from method
                } else {
                    firstPlayerMove.removeLast();                        //otherwise remove from computer's array
                }
            }
            //checks if the available positions can be the last winning positions for the opponent, if so, the computer takes this position
            for (int notTakenPosition : notTakenPositions) {   //go through each available position
                secondPlayerMove.add(notTakenPosition);        //add available position into opponent's array
                if (isSomeoneWon(secondPlayerMove)) {          //if added position helps opponent to win, then this is losing position
                    defenseMove = notTakenPosition;            //losing position will be our defense position
                    secondPlayerMove.removeLast();             //remove from opponent's array
                    firstPlayerMove.add(notTakenPosition);     //add defense position into computer's array
                    notTakenPositions.remove(Integer.valueOf(defenseMove));  //remove position from available positions array
                    base.putPieceToPosition(defenseMove, piece);             //add piece to given position on board
                    return;                                                  //exit from method
                } else {
                    secondPlayerMove.removeLast();                           //otherwise remove from opponent's array
                }
            }
            //if there is no winning or losing position, then place the piece in a random place
            //generates a random number to use as an index of an array of available positions
            //gets available position by random index number
            randomMove = notTakenPositions.get(random.nextInt(notTakenPositions.size()));
            firstPlayerMove.add(randomMove);            //add available position into computer's array
            notTakenPositions.remove(Integer.valueOf(randomMove));     //remove position from available positions array
            base.putPieceToPosition(randomMove, piece);                //add piece to given position on board
        }
    }

    //method implements game between two computers
    public void computersBattle() {
        for (int i = 1; i < counts; i ++) {  //there are a maximum of 9 moves that can be made in the game, so the number of iterations is 9
            if (i % 2 != 0) {            //on odd iterations, X will be place on the board
                System.out.println("The first computer move!");
                computerLogic(firstPlayerMove, secondPlayerMove, TicTacToe.player1);  //calls computer algorithm
                try {                               //delays for 1.5s the queue of the next computer in the console, because data output is fast for the human eye
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                base.createGrid();              //creates updated grid
                if (isSomeoneWon(firstPlayerMove)) {  //every iteration will check if someone won or not
                    System.out.println("The first computer won!");
                    break;
                }
            }
            else {    //on even iterations O will be place on the board
                System.out.println("The second computer move!");
                computerLogic(secondPlayerMove, firstPlayerMove, TicTacToe.player2); //calls computer algorithm
                try {                           //delays for 1.5s the queue of the next computer in the console, because data output is fast for the human eye
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                base.createGrid();              //creates updated grid
                if (isSomeoneWon(secondPlayerMove)) {  //every iteration will check if someone won or not
                    System.out.println("The second computer won!");
                    break;
                }
            }
        } //if no one won then it prints Draw message
        if (!(isSomeoneWon(firstPlayerMove) || isSomeoneWon(secondPlayerMove))) {
            System.out.println("Draw!");
        }
        resetEverything(); //clean up after yourself
    }

    //method do everything for playing game between friends
    public void playWithFriend() {
        base.createGrid();    //creates grid for game
        for (int i = 1; i < counts; i ++) {  //there are a maximum of 9 moves that can be made in the game, so the number of iterations is 9
            if (i % 2 != 0) {     //on odd iterations, X will be place on the board
                playerLogic(firstPlayerMove, TicTacToe.player1);  //calls player algorithm
                if (isSomeoneWon(firstPlayerMove)) {            //every iteration will check if someone won or not
                    System.out.println("The first player won!");
                    break;
                }
            }
            else {    //on even iterations O will be place on the board
                playerLogic(secondPlayerMove, TicTacToe.player2); //calls player algorithm
                if (isSomeoneWon(secondPlayerMove)) {           //every iteration will check if someone won or not
                    System.out.println("The second player won!");
                    break;
                }
            }
        } //if no one won then it prints Draw message
        if (!(isSomeoneWon(firstPlayerMove) || isSomeoneWon(secondPlayerMove))) {
            System.out.println("Draw!");
        }
        resetEverything(); //clean up after yourself
    }

    //method implements game against computer
    public void playWithComputer() {
        String userAnswer;   //for user answer
        System.out.print("Will you go first ? y/n: ");
        userAnswer = input.next().toLowerCase();
        if (userAnswer.equals("y")) {  //if yes user goes first
            base.createGrid();    //creates grid for game
            for (int i = 1; i < counts; i ++) {  //there are a maximum of 9 moves that can be made in the game, so the number of iterations is 9
                if (i % 2 != 0) {     //on odd iterations, X will be place on the board
                    playerLogic(firstPlayerMove, TicTacToe.player1);  //calls player's algorithm
                    if (isSomeoneWon(firstPlayerMove)) {  //every iteration will check if someone won or not
                        System.out.println("The user won!");
                        break;
                    }
                }
                else {    //on even iterations O will be place on the board
                    computerLogic(secondPlayerMove, firstPlayerMove, TicTacToe.player2);     //calls computer's algorithm
                    base.createGrid();              //creates updated grid
                    if (isSomeoneWon(secondPlayerMove)) { //every iteration will check if computer won or not
                        System.out.println("The computer won!");
                        break;
                    }
                }
            } //if no one won then it prints Draw message
            if (!(isSomeoneWon(firstPlayerMove) || isSomeoneWon(secondPlayerMove))) {
                System.out.println("Draw!");
            }
            resetEverything(); //clean up after yourself
        }
        else if (userAnswer.equals("n")) {    //if no then computer goes first
            for (int i = 1; i < counts; i ++) {  //there are a maximum of 9 moves that can be made in the game, so the number of iterations is 9
                if (i % 2 != 0) {
                    computerLogic(firstPlayerMove, secondPlayerMove, TicTacToe.player1);  //calls computer algorithm
                    base.createGrid(); //creates updated grid with values
                    if (isSomeoneWon(firstPlayerMove)) {        //every iteration will check if computer won or not
                        System.out.println("The computer won!");
                        break;
                    }
                }
                else {
                    playerLogic(secondPlayerMove, TicTacToe.player2);       //calls player's algorithm
                    if (isSomeoneWon(secondPlayerMove)) {  //every iteration will check if someone won or not
                        System.out.println("The user won!");
                        break;
                    }
                }
            } //if no one won then it prints Draw message
            if (!(isSomeoneWon(firstPlayerMove) || isSomeoneWon(secondPlayerMove))) {
                System.out.println("Draw!");
            }
            resetEverything(); //clean up after yourself
        }
        else { //if user's input not y or n
            System.out.println("Can't understand what you mean!");
        }
    }

    //method sets everything to the start point
    public void resetEverything() {
        base.createBoard();  //create new board
        notTakenPositions = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
        firstPlayerMove.clear(); //clean after the game
        secondPlayerMove.clear(); //clean after the game
    }
}