package Pieces;

import Data.Pair;

public class Rook extends Piece {

    public Rook(Pair position, int color) {
        super(position, color);
    }

    @Override
    public boolean isValidPath(Pair position) {
        return this.validMoves.contains(position);
    }

    @Override
    public void setValidMoves(Piece[][] testGame) {

        int x = this.position.x;
        int y = this.position.y;
        while(x > 0){
            x--;
            Pair newPos = new Pair(x, y);
            if(testGame[x][y] == null){ // Moving to null space
                validMoves.add(newPos);
            } else if(this.color != testGame[x][y].color){ //(White capture black or Black capture white)
                validMoves.add(newPos);
                x = this.position.x;
                y = this.position.y;
                break;
            } else { //Trying to capture your own piece
                x = this.position.x;
                y = this.position.y;
                break;
            }
        }
        while(x < 7){
            x++;
            Pair newPos = new Pair(x, y);
            if(testGame[x][y] == null){
                validMoves.add(newPos);
            } else if(this.color != testGame[x][y].color){ //(White capture black or Black capture white)
                validMoves.add(newPos);
                x = this.position.x;
                y = this.position.y;
                break;
            } else {
                x = this.position.x;
                y = this.position.y;
                break;
            }
        }
        while(y < 7){
            y++;
            Pair newPos = new Pair(x, y);
            if(testGame[x][y] == null){
                validMoves.add(newPos);
            } else if(this.color != testGame[x][y].color){ //(White capture black or Black capture white)
                validMoves.add(newPos);
                x = this.position.x;
                y = this.position.y;
                break;
            } else {
                x = this.position.x;
                y = this.position.y;
                break;
            }
        }
        while(y > 0){
            y--;
            Pair newPos = new Pair(x, y);
            if(testGame[x][y] == null){
                validMoves.add(newPos);
            } else if(this.color != testGame[x][y].color){ //(White capture black or Black capture white)
                validMoves.add(newPos);
                x = this.position.x;
                y = this.position.y;
                break;
            } else {
                x = this.position.x;
                y = this.position.y;
                break;
            }
        }
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
