package Pieces;

import Data.Pair;

/**
 * @author Zain Ul-Abdin
 * @author Kirtan Patel
 */

/**
 * The class that represents the Knight piece.
 */

public class Knight extends Piece {

    /**
     * The constructor of the Knight class.
     *
     * @param position Takes in the position of the Knight.
     * @param color Takes in the color of the Knight.
     */
    public Knight(Pair position, int color) {
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
        checkEndSpace(testGame, new Pair(position.x - 1, position.y - 2));
        checkEndSpace(testGame, new Pair(position.x - 1, position.y + 2));
        checkEndSpace(testGame, new Pair(position.x + 1, position.y - 2));
        checkEndSpace(testGame, new Pair(position.x + 1, position.y + 2));
        checkEndSpace(testGame, new Pair(position.x - 2, position.y - 1));
        checkEndSpace(testGame, new Pair(position.x - 2, position.y + 1));
        checkEndSpace(testGame, new Pair(position.x + 2, position.y - 1));
        checkEndSpace(testGame, new Pair(position.x + 2, position.y + 1));
    }

    /**
     * Overrides toString() method in the object class.
     * Return a String that represents the Knight class.
     *
     * @return String
     */
    @Override
    public String toString() {
        if(color == 0){
            return "wN";
        }else {
            return "bN";
        }
    }
}
