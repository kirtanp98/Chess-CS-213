package Pieces;

import Controller.Game;
import Data.Pair;

import java.util.ArrayList;

/**
 * @author Zain Ul-Abdin
 * @author Kirtan Patel
 */

/**
 * The abstract class that all the board pieces are based on.
 */

public abstract class Piece {
    /**
     * The position of the piece.
     */
    public Pair position;

    /**
     * The color of the piece.
     */
    public int color; // 0 = white, 1 = black

    /**
     * The arraylist that contains all the valid moves of the piece.
     */
    public ArrayList<Pair> validMoves = new ArrayList<>();

    /**
     * The constructor of the Piece class.
     *
     * @param position Takes in the position of the Piece.
     * @param color Takes in the color of the Piece.
     */
    public Piece(Pair position, int color){
        this.position = position;
        this.color = color;
    }

    /**
     * Checks if the position is in the validMoves arraylist.
     * Classes that extend this class must implement this method.
     *
     * @param position Takes in a Pair object to check if can move to that position.
     * @return boolean
     */
    public abstract boolean isValidPath(Pair position);

    /**
     * Adds valid moves the validMoves array according to the board that gets inputted.
     * Classes that extend this class must implement this method.
     *
     * @param testGame Takes in the board.
     */
    public abstract void setValidMoves(Piece[][] testGame); //Does not consider self-induced checks this in handled in Game class

    /**
     * Updates the position of the piece.
     *
     * @param position Takes in a Pair object.
     */
    public void updatePosition(Pair position){
        this.position = position;
    }

    /**
     * Checks if the piece is at the end of the board
     *
     * @param testGame Takes in the board
     * @param newPos Takes in a Pair object
     */
    public void checkEndSpace(Piece[][] testGame, Pair newPos){
        if((newPos.x >= 0 && newPos.y >= 0 && newPos.x <= 7 && newPos.y <= 7) && (testGame[newPos.x][newPos.y] == null || testGame[newPos.x][newPos.y].color != this.color)){
            validMoves.add(newPos);
        }
    }

    @Override
    public abstract String toString();
}
