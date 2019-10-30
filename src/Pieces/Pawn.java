package Pieces;

import Data.Pair;

public class Pawn extends Piece {

    public boolean twoMove = false;

    public Pawn(Pair position, int color) {
        super(position, color);
    }

    @Override
    public boolean isValidPath(Pair position) {
        return this.validMoves.contains(position);
    }

    @Override
    public void setValidMoves(Piece[][] testGame) {
        validMoves.clear();
        if(this.color == 0){
            checkEnd(testGame, new Pair(position.x, position.y - 1));
            if(position.y == 6 && testGame[position.x][5] == null){
                checkEnd(testGame, new Pair(position.x, position.y - 2));
            }
            if(position.x > 0 && position.y > 0) {
                if (testGame[position.x - 1][position.y - 1] != null && testGame[position.x - 1][position.y - 1].color != this.color) {
                    validMoves.add(new Pair(position.x - 1, position.y - 1));
                }
            }
            if(position.x < 7 && position.y > 0) {
                if (testGame[position.x + 1][position.y - 1] != null && testGame[position.x + 1][position.y - 1].color != this.color) {
                    validMoves.add(new Pair(position.x + 1, position.y - 1));
                }
            }
        } else {
            checkEnd(testGame, new Pair(position.x, position.y + 1));
            if(position.y == 1 && testGame[position.x][2] == null){
                checkEnd(testGame, new Pair(position.x, position.y + 2));
            }
            if(position.x > 0 && position.y < 7) {
                if (testGame[position.x - 1][position.y + 1] != null && testGame[position.x - 1][position.y + 1].color != this.color) {
                    validMoves.add(new Pair(position.x - 1, position.y + 1));
                }
            }
            if(position.x < 7 && position.y < 7) {
                if (testGame[position.x + 1][position.y + 1] != null && testGame[position.x + 1][position.y + 1].color != this.color) {
                    validMoves.add(new Pair(position.x + 1, position.y + 1));
                }
            }
        }
    }

    private void checkEnd(Piece[][] testGame, Pair newPos){
        if((newPos.x >= 0 && newPos.y >= 0 && newPos.x <= 7 && newPos.y <= 7) && (testGame[newPos.x][newPos.y] == null)){
            validMoves.add(newPos);
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
