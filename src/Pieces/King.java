package Pieces;

import Data.Pair;

/**
 * @author Zain Ul-Abdin
 * @author Kirtan Patel
 */

/**
 * The class that represents the King piece.
 */

public class King extends Piece {

    /**
     * Variable to see if the King has moved.
     */
    public boolean moved = false;

    /**
     * The constructor of the King class.
     *
     * @param position Takes in the position of the King.
     * @param color Takes in the color of the King.
     */
    public King(Pair position, int color) {
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

        checkEndSpace(testGame, new Pair(position.x - 1, position.y));
        checkEndSpace(testGame, new Pair(position.x - 1, position.y - 1));
        checkEndSpace(testGame, new Pair(position.x - 1, position.y + 1));
        checkEndSpace(testGame, new Pair(position.x + 1, position.y + 1));
        checkEndSpace(testGame, new Pair(position.x + 1, position.y));
        checkEndSpace(testGame, new Pair(position.x + 1, position.y - 1));
        checkEndSpace(testGame, new Pair(position.x, position.y + 1));
        checkEndSpace(testGame, new Pair(position.x, position.y - 1));

    }

    /**
     * Overrides toString() method in the object class.
     * Return a String that represents the King class.
     *
     * @return String
     */
    @Override
    public String toString() {
        if(color == 0){
            return "wK";
        }else {
            return "bK";
        }
    }
}
