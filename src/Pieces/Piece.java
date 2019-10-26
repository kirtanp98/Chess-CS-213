package Pieces;

import Controller.Game;
import Data.Pair;

import java.util.ArrayList;

public abstract class Piece {
    Pair position;
    int color; // 0 = white, 1 = black
    ArrayList<Pair> validMoves = new ArrayList<>();

    public Piece(Pair position, int color){
        this.position = position;
        this.color = color;
    }

    public abstract boolean isValidPath(Pair position);

    public abstract void setValidMoves(Game game);

    public void updatePosition(Pair position){
        this.position = position;
    }

    //TODO Implement getType()
//    Class<?> getType(){
//        return this.getType();
//    }
    @Override
    public abstract String toString();
}
