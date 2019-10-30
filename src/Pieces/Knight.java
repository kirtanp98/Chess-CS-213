package Pieces;

import Data.Pair;

public class Knight extends Piece {

    public Knight(Pair position, int color) {
        super(position, color);
    }

    @Override
    public boolean isValidPath(Pair position) {
        return this.validMoves.contains(position);
    }

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

    @Override
    public String toString() {
        if(color == 0){
            return "wN";
        }else {
            return "bN";
        }
    }
}
