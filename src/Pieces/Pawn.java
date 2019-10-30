package Pieces;

import Data.Pair;

/**
 * @author Zain Ul-Abdin
 * @author Kirtan Patel
 */

/**
 * The class that represents the Pawn piece.
 */

public class Pawn extends Piece {

    /**
     *  Variable that checks if the Pawn has moved two spaces.
     */
    public boolean twoMove = false;

    /**
     * The constructor of the Pawn class.
     *
     * @param position Takes in the position of the Pawn.
     * @param color Takes in the color of the Pawn.
     */
    public Pawn(Pair position, int color) {
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
        if(this.color == 0){
            checkEnd(testGame, new Pair(position.x, position.y - 1));
            if(position.y == 6 && testGame[position.x][5] == null){
                checkEnd(testGame, new Pair(position.x, position.y - 2));
            }
            if(position.x > 0 && position.y > 0) {
                if (testGame[position.x - 1][position.y - 1] != null && testGame[position.x - 1][position.y - 1].color != this.color) {
                    validMoves.add(new Pair(position.x - 1, position.y - 1));
                }
            }
            if(position.x < 7 && position.y > 0) {
                if (testGame[position.x + 1][position.y - 1] != null && testGame[position.x + 1][position.y - 1].color != this.color) {
                    validMoves.add(new Pair(position.x + 1, position.y - 1));
                }
            }
        } else {
            checkEnd(testGame, new Pair(position.x, position.y + 1));
            if(position.y == 1 && testGame[position.x][2] == null){
                checkEnd(testGame, new Pair(position.x, position.y + 2));
            }
            if(position.x > 0 && position.y < 7) {
                if (testGame[position.x - 1][position.y + 1] != null && testGame[position.x - 1][position.y + 1].color != this.color) {
                    validMoves.add(new Pair(position.x - 1, position.y + 1));
                }
            }
            if(position.x < 7 && position.y < 7) {
                if (testGame[position.x + 1][position.y + 1] != null && testGame[position.x + 1][position.y + 1].color != this.color) {
                    validMoves.add(new Pair(position.x + 1, position.y + 1));
                }
            }
        }
    }

    /**
     * Checks if the Pawn has reached the end and updates the validMoves arraylist.
     *
     * @param testGame Takes in the board.
     * @param newPos Takes in the new position of the Pawn
     */
    private void checkEnd(Piece[][] testGame, Pair newPos){
        if((newPos.x >= 0 && newPos.y >= 0 && newPos.x <= 7 && newPos.y <= 7) && (testGame[newPos.x][newPos.y] == null)){
            validMoves.add(newPos);
        }
    }

    /**
     * Overrides toString() method in the object class.
     * Return a String that represents the Pawn class.
     *
     * @return String
     */
    @Override
    public String toString() {
        if(color == 0){
            return "wp";
        }else {
            return "bp";
        }
    }
}
