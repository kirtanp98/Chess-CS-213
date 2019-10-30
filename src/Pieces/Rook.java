package Pieces;

import Data.Pair;

/**
 * @author Zain Ul-Abdin
 * @author Kirtan Patel
 */

/**
 * The class that represents the Rook piece.
 */

public class Rook extends Piece {

    /**
     * Variable to see if the Rook has moved.
     */
    public boolean moved = false;

    /**
     * The constructor of the Knight class.
     *
     * @param position Takes in the position of the Knight.
     * @param color Takes in the color of the Knight.
     */
    public Rook(Pair position, int color) {
        super(position, color);
    }

    /**
     * Checks if the position is in the validMoves arraylist.
     *
     * @param position Takes in a Pair object to check if can move to that position.
     * @return boolean
     */
    @Override
    public boolean isValidPath(Pair position) {
        return this.validMoves.contains(position);
    }

    /**
     * Adds valid moves the validMoves array according to the board that gets inputted.
     *
     * @param testGame Takes in the board.
     */
    @Override
    public void setValidMoves(Piece[][] testGame) {

        validMoves.clear();

        int x = this.position.x;
        int y = this.position.y;
        while(x > 0){
            x--;
            Pair newPos = new Pair(x, y);
            if(testGame[x][y] == null){ // Moving to null space
                validMoves.add(newPos);
            } else if(this.color != testGame[x][y].color){ //(White capture black or Black capture white)
                validMoves.add(newPos);
                break;
            } else { //Trying to capture your own piece
                break;
            }
        }
        x = this.position.x;
        y = this.position.y;
        while(x < 7){
            x++;
            Pair newPos = new Pair(x, y);
            if(testGame[x][y] == null){
                validMoves.add(newPos);
            } else if(this.color != testGame[x][y].color){ //(White capture black or Black capture white)
                validMoves.add(newPos);
                break;
            } else {
                break;
            }
        }
        x = this.position.x;
        y = this.position.y;
        while(y < 7){
            y++;
            Pair newPos = new Pair(x, y);
            if(testGame[x][y] == null){
                validMoves.add(newPos);
            } else if(this.color != testGame[x][y].color){ //(White capture black or Black capture white)
                validMoves.add(newPos);
                break;
            } else {
                break;
            }
        }
        x = this.position.x;
        y = this.position.y;
        while(y > 0){
            y--;
            Pair newPos = new Pair(x, y);
            if(testGame[x][y] == null){
                validMoves.add(newPos);
            } else if(this.color != testGame[x][y].color){ //(White capture black or Black capture white)
                validMoves.add(newPos);
                break;
            } else {
                break;
            }
        }
        x = this.position.x;
        y = this.position.y;
    }

    /**
     * Overrides toString() method in the object class.
     * Return a String that represents the Rook class.
     *
     * @return String
     */
    @Override
    public String toString() {
        if(color == 0){
            return "wR";
        }else {
            return "bR";
        }
    }
}
