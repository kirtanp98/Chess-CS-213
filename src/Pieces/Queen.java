package Pieces;

import Data.Pair;

public class Queen extends Piece {

    public Queen(Pair position, int color) {
        super(position, color);
    }

    @Override
    public boolean isValidPath(Pair position) {
        return this.validMoves.contains(position);
    }

    @Override
    public void setValidMoves(Piece[][] testGame) {

        validMoves.clear();

        int x = this.position.x;
        int y = this.position.y;
        while(x > 0 && y > 0){
            x--;
            y--;
            Pair newPos = new Pair(x, y);
            if(testGame[x][y] == null){ // Moving to null space
                validMoves.add(newPos);
            } else if(this.color != testGame[x][y].color){ //(White capture black or Black capture white)
                validMoves.add(newPos);
                break;
            } else { //Trying to capture your own piece
                break;
            }
        }
        x = this.position.x;
        y = this.position.y;
        while(x < 7 && y < 7){
            x++;
            y++;
            Pair newPos = new Pair(x, y);
            if(testGame[x][y] == null){
                validMoves.add(newPos);
            } else if(this.color != testGame[x][y].color){ //(White capture black or Black capture white)
                validMoves.add(newPos);
                break;
            } else {
                break;
            }
        }
        x = this.position.x;
        y = this.position.y;
        while(x > 0 && y < 7){
            x--;
            y++;
            Pair newPos = new Pair(x, y);
            if(testGame[x][y] == null){
                validMoves.add(newPos);
            } else if(this.color != testGame[x][y].color){ //(White capture black or Black capture white)
                validMoves.add(newPos);
                break;
            } else {
                break;
            }
        }
        x = this.position.x;
        y = this.position.y;
        while(x < 7 && y > 0){
            x++;
            y--;
            Pair newPos = new Pair(x, y);
            if(testGame[x][y] == null){
                validMoves.add(newPos);
            } else if(this.color != testGame[x][y].color){ //(White capture black or Black capture white)
                validMoves.add(newPos);
                break;
            } else {
                break;
            }
        }
        x = this.position.x;
        y = this.position.y;
        while(x > 0){
            x--;
            Pair newPos = new Pair(x, y);
            if(testGame[x][y] == null){ // Moving to null space
                validMoves.add(newPos);
            } else if(this.color != testGame[x][y].color){ //(White capture black or Black capture white)
                validMoves.add(newPos);
                break;
            } else { //Trying to capture your own piece
                break;
            }
        }
        x = this.position.x;
        y = this.position.y;
        while(x < 7){
            x++;
            Pair newPos = new Pair(x, y);
            if(testGame[x][y] == null){
                validMoves.add(newPos);
            } else if(this.color != testGame[x][y].color){ //(White capture black or Black capture white)
                validMoves.add(newPos);
                break;
            } else {
                break;
            }
        }
        x = this.position.x;
        y = this.position.y;
        while(y < 7){
            y++;
            Pair newPos = new Pair(x, y);
            if(testGame[x][y] == null){
                validMoves.add(newPos);
            } else if(this.color != testGame[x][y].color){ //(White capture black or Black capture white)
                validMoves.add(newPos);
                break;
            } else {
                break;
            }
        }
        x = this.position.x;
        y = this.position.y;
        while(y > 0){
            y--;
            Pair newPos = new Pair(x, y);
            if(testGame[x][y] == null){
                validMoves.add(newPos);
            } else if(this.color != testGame[x][y].color){ //(White capture black or Black capture white)
                validMoves.add(newPos);
                break;
            } else {
                break;
            }
        }
        x = this.position.x;
        y = this.position.y;
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
