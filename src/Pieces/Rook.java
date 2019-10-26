package Pieces;

import Controller.Game;
import Data.Pair;

public class Rook extends Piece {

    public Rook(Pair position, int color) {
        super(position, color);
    }

    @Override
    public boolean isValidPath(Pair position) {
        if(this.position.x == position.x){
            if(position.y >= 0 && position.y < 8){
                return true;
            }
        }
        if(this.position.y == position.y){
            if(position.x >= 0 && position.x < 8){
                return true;
            }
        }
        else{
            return false;
        }
        return false;
    }

    @Override
    public void setValidMoves(Game game) {

    }

    @Override
    public String toString() {
        if(color == 0){
            return "wR";
        }else {
            return "bR";
        }
    }
}
