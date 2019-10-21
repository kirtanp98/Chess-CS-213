package Pieces;

public class Rook extends Piece {

    public Rook(int xPos, int yPos, int color) {
        super(xPos, yPos, color);
    }

    @Override
    public boolean isValidPath(int xPos, int yPos) {
        if(this.xPos == xPos){
            if(yPos >= 0 && yPos < 8){
                return true;
            }
        }
        if(this.yPos == yPos){
            if(xPos >= 0 && xPos < 8){
                return true;
            }
        }
        else{
            return false;
        }
        return false;
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
