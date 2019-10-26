package Pieces;

import Controller.Game;
import Data.Pair;

public class Pawn extends Piece {
    boolean hasMoved = false;

    public Pawn(Pair position, int color) {
        super(position, color);
    }

    @Override
    public boolean isValidPath(Pair position) {
        if(hasMoved){
            if(color == 0){
                if(position.x!= this.position.x || position.y != this.position.y-1){
                    return false;
                }
            }else{
                if(position.x!= this.position.x || position.y != this.position.y+1){
                    return false;
                }
            }
        }else{
            if(color == 0){
                if(position.x!= this.position.x || position.y < this.position.y-2){
                    return false;
                }
            }else{
                if(position.x!= this.position.x || position.y > this.position.y+2){
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void setValidMoves(Game game) {

    }

    @Override
    public String toString() {
        if(color == 0){
            return "wp";
        }else {
            return "bp";
        }
    }
}
