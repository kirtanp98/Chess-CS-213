package Pieces;

import Controller.Game;
import Data.Pair;

public class Bishop extends Piece {

    public Bishop(Pair position, int color) {
        super(position, color);
    }

    @Override
    public boolean isValidPath(Pair position) {
        return this.validMoves.contains(position);
    }

    @Override
    public void setValidMoves(Game currgame) {
        int x = this.position.x;
        int y = this.position.y;
        while(x > 0 && y > 0){
            x--;
            y--;
            Pair newPos = new Pair(x, y);
            if(currgame.game[x][y] == null){
                validMoves.add(newPos);
            } else if(this.color == 0 && currgame.game[x][y].color == 1 || this.color == 1 && currgame.game[x][y].color == 0){ //(White capture black or Black capture white)
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
        while(x < 7 && y < 7){
            x++;
            y++;
            Pair newPos = new Pair(x, y);
            if(currgame.game[x][y] == null){
                validMoves.add(newPos);
            } else if(this.color == 0 && currgame.game[x][y].color == 1 || this.color == 1 && currgame.game[x][y].color == 0){ //(White capture black or Black capture white)
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
        while(x > 0 && y < 7){
            x--;
            y++;
            Pair newPos = new Pair(x, y);
            if(currgame.game[x][y] == null){
                validMoves.add(newPos);
            } else if(this.color == 0 && currgame.game[x][y].color == 1 || this.color == 1 && currgame.game[x][y].color == 0){ //(White capture black or Black capture white)
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
        while(x < 7 && y > 0){
            x++;
            y--;
            Pair newPos = new Pair(x, y);
            if(currgame.game[x][y] == null){
                validMoves.add(newPos);
            } else if(this.color == 0 && currgame.game[x][y].color == 1 || this.color == 1 && currgame.game[x][y].color == 0){ //(White capture black or Black capture white)
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
            return "wB";
        }else {
            return "bB";
        }
    }
}
