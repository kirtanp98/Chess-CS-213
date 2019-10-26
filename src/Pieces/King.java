package Pieces;

import Controller.Game;
import Data.Pair;

public class King extends Piece {

    public King(Pair position, int color) {
        super(position, color);
    }

    @Override
    public boolean isValidPath(Pair position){
        if(position.x > this.position.x + 1 ||position.x < this.position.x - 1){
            return false;
        }
        if(position.y > this.position.y + 1 || position.y < this.position.y - 1){
            return false;
        }

        return true;
    }

    @Override
    public void setValidMoves(Game game) {

    }

    @Override
    public String toString() {
        if(color == 0){
            return "wK";
        }else {
            return "bK";
        }
    }
}
