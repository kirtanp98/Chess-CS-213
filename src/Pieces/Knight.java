package Pieces;

import Controller.Game;
import Data.Pair;

public class Knight extends Piece {

    public Knight(Pair position, int color) {
        super(position, color);
    }

    @Override
    public boolean isValidPath(Pair position){

        if(this.position.x-1 == position.x && this.position.y == position.y-3){
            return true;
        }
        if(this.position.x-3 == position.x && this.position.y == position.y-1){
            return true;
        }

        if(this.position.x-1 == position.x && this.position.y == position.y+3){
            return true;
        }
        if(this.position.x-3 == position.x && this.position.y == position.y+1){
            return true;
        }

        if(this.position.x+1 == position.x && this.position.y == position.y+3){
            return true;
        }
        if(this.position.x+3 == position.x && this.position.y == position.y+1){
            return true;
        }

        if(this.position.x+1 == position.x && this.position.y == position.y-3){
            return true;
        }
        if(this.position.x-3 == position.x && this.position.y == position.y-1){
            return true;
        }

        return false;
    }

    @Override
    public void setValidMoves(Game game) {

    }

    @Override
    public String toString() {
        if(color == 0){
            return "wN";
        }else {
            return "bN";
        }
    }
}
