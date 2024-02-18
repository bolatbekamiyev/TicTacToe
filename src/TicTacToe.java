//////////////////////////////////////////////////////////////////////
//                                                                  //
// Bolatbek Amiyev                                                  //
// CS622 TicTacToe project                                          //
// TicTacToe class                                                  //
// Blueprint for grid, noughts and crosses.                         //
//////////////////////////////////////////////////////////////////////

public class TicTacToe {

    public enum Pieces {X, O, EMPTY};    //Noughts, crosses and empty
    private Pieces[][] boardArray;       //Two-dimensional array for storing elements
    public static final Pieces player1 = Pieces.X;    //First player will be use X
    public static final Pieces player2 = Pieces.O;

    public TicTacToe() {
        createBoard();
    }   //Constructor
    public Pieces[][] getBoardArray() {
        return boardArray;
    }  //board getter

    //Creates 3x3 two-dimensional array for storing enum, and puts default value EMPTY
    public void createBoard() {
        boardArray = new Pieces[3][3];
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 3; column++) {
                boardArray[row][column] = Pieces.EMPTY;
            }
        }
    }

    //Changes given position in boards with given enum
    public void putPieceToPosition(int number, Pieces piece) {
        Pieces[][] board = getBoardArray();
        switch (number) {
            case 1:
                board[0][0] = piece;
                break;
            case 2:
                board[0][1] = piece;
                break;
            case 3:
                board[0][2] = piece;
                break;
            case 4:
                board[1][0] = piece;
                break;
            case 5:
                board[1][1] = piece;
                break;
            case 6:
                board[1][2] = piece;
                break;
            case 7:
                board[2][0] = piece;
                break;
            case 8:
                board[2][1] = piece;
                break;
            case 9:
                board[2][2] = piece;
                break;
            default:
                System.out.println("Can't find given position");
        }
    }

    //Creates grid for Tic Tac Toe game
    public void createGrid (){
        Pieces[][] board = getBoardArray(); //takes board array for creating grid
        String result = "";
        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board.length; column++) {
                if (column == 0 || column == 1) {     //if it is 0 or 1 column print slots with > |
                    if (board[row][column] == Pieces.EMPTY) { //if slot is EMPTY then print empty space for slot
                        result += "   " +  " | ";
                    }
                    else {
                        result += " " + board[row][column] + " " +  " | ";  //otherwise, if the slot is not EMPTY, print its value
                    }
                }
                else { //if column == 2 then print just slots without > |
                    if (row == 2 && column == 2) {  //if it is last slot on the grid then print just slot
                        if (board[row][column] == Pieces.EMPTY) { //if slot is EMPTY then print empty space for slot
                            result += "   \n";
                        }
                        else {
                            result += " " + board[row][column] + "\n";   //otherwise, if the slot is not EMPTY, print its value
                        }
                    }
                    else {   //if it is not last slot on the grid then print s lots and separator line
                        if (board[row][column] == Pieces.EMPTY){  //if slot is EMPTY then print empty space for slot
                            result += "   \n";
                            result += "---------------\n";
                        }
                        else {
                            result += " " + board[row][column] + "\n"; //otherwise, if the slot is not EMPTY, print its value
                            result += "---------------\n";
                        }
                    }
                }
            }
        }
        System.out.println(result);;
    }

    //method returns grid with values
    public String toString() {
        Pieces[][] board = getBoardArray();
        String result = " -------------------------\n";
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                result += " | " +  board[i][j];
            }
            result += " |\n";
            result += " -------------------------\n";
        }
        System.out.println("Welcome to the Tic Tac Toe game");
        return result;
    }
}