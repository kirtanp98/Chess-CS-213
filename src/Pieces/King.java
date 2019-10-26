package Pieces;

import Data.Pair;

public class King extends Piece {

    public King(Pair position, int color) {
        super(position, color);
    }

    @Override
    public boolean isValidPath(Pair position) {
        return this.validMoves.contains(position);
    }

    @Override
    public void setValidMoves(Piece[][] testGame) {

        checkEndSpace(testGame, new Pair(position.x - 1, position.y));
        checkEndSpace(testGame, new Pair(position.x - 1, position.y - 1));
        checkEndSpace(testGame, new Pair(position.x - 1, position.y + 1));
        checkEndSpace(testGame, new Pair(position.x + 1, position.y + 1));
        checkEndSpace(testGame, new Pair(position.x + 1, position.y));
        checkEndSpace(testGame, new Pair(position.x + 1, position.y - 1));
        checkEndSpace(testGame, new Pair(position.x, position.y + 1));
        checkEndSpace(testGame, new Pair(position.x, position.y - 1));

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
