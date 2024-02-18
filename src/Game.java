//////////////////////////////////////////////////////////////////////
//                                                                  //
// Bolatbek Amiyev                                                  //
// CS622 TicTacToe project                                          //
// Game class based on Logic class                                  //
// All about start the game                                         //
//////////////////////////////////////////////////////////////////////


import java.util.Scanner;   //for user's keyboard values

public class Game {

    public static Logic gameLogic = new Logic();  //creates object of the class
    Scanner input = new Scanner(System.in);       //for user's keyboard values


    //method runs program
    // if user's answer y(yes) then start game. If user's answer n(no) then quit game.
    // if user's answer s(see) then start computer's battle
    public void runProgram() {
        String userAnswer;
        System.out.println("Welcome to TicTacToe");
        while (true) {                      //while user answer y/n/s
            System.out.print("Do you wanna play (y/n) or see battle between computers (s) ? y/n/s: ");
            userAnswer = input.next().toLowerCase();
            if (userAnswer.equals("y")) {
                System.out.print("With Friend or with Computer (f/c) or quit (q) ? f/c/q: ");
                userAnswer = input.next().toLowerCase();
                if (userAnswer.equals("f")) {
                    System.out.println("First player will be X");
                    gameLogic.playWithFriend();
                } else if (userAnswer.equals("c")) {
                    gameLogic.playWithComputer();
                } else if (userAnswer.equals("q")) {
                    System.out.println("Thank you for your time, bye!");
                    break;
                } else {
                    System.out.println("Can't understand what you mean!");
                }

            } else if (userAnswer.equals("n")) {
                System.out.println("Thank you for your time, bye!");
                break;
            } else if (userAnswer.equals("s")) {
                System.out.println("Enjoy the battle!");
                gameLogic.computersBattle();
            } else {
                System.out.println("Can't understand what you mean!");
            }
        }
    }
}