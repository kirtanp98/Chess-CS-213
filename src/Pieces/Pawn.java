package Pieces;

import Data.Pair;

public class Pawn extends Piece {

    public Pawn(Pair position, int color) {
        super(position, color);
    }

    @Override
    public boolean isValidPath(Pair position) {
        return this.validMoves.contains(position);
    }

    @Override
    public void setValidMoves(Piece[][] testGame) {
        if(this.color == 0){
            checkEndSpace(testGame, new Pair(position.x, position.y - 1));
            if(position.y == 6){
                checkEndSpace(testGame, new Pair(position.x, position.y - 2));
            }
        } else {
            checkEndSpace(testGame, new Pair(position.x, position.y + 1));
            if(position.y == 1){
                checkEndSpace(testGame, new Pair(position.x, position.y + 2));
            }
        }
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
