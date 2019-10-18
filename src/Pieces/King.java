package Pieces;

public class King extends Piece {

    public King(int xPos, int yPos, int color) {
        super(xPos, yPos, color);
    }

    @Override
    public boolean isValidPath(int xPos, int yPos){
        if(xPos > this.xPos + 1 ||xPos < this.xPos - 1){
            return false;
        }
        if(yPos > this.yPos + 1 || yPos < this.yPos - 1){
            return false;
        }

        return true;
    }
}
