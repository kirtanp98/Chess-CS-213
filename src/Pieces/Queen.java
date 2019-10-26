package Pieces;

import Controller.Game;
import Data.Pair;

public class Queen extends Piece {

    public Queen(Pair position, int color) {
        super(position, color);
    }

    @Override
    public boolean isValidPath(Pair position) {//Combined rook and bishop moves together
        if(this.position.x == position.x || this.position.y == position.y){
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

        }else{
            int x = position.x - this.position.x;
            int y = position.y - this.position.y;

            x = x/Math.abs(x);
            y = y/Math.abs(y);

            int tempXPos = this.position.x;
            int tempYPos = this.position.y;

            while(tempXPos >= 0 && tempXPos < 8 && tempYPos >= 0 && tempYPos < 8){
                if(tempXPos == position.x && tempYPos == position.x){
                    return true;
                }
                tempXPos+=x;
                tempYPos+=y;
            }

        }

        return false;
    }

    @Override
    public void setValidMoves(Game game) {

    }

    @Override
    public String toString() {
        if(color == 0){
            return "wQ";
        }else {
            return "bQ";
        }
    }
}
