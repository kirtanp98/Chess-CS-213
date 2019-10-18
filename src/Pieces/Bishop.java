package Pieces;

public class Bishop extends Piece {

    public Bishop(int xPos, int yPos, int color) {
        super(xPos, yPos, color);
    }

    @Override
    public boolean isValidPath(int xPos, int yPos) {
        int x = xPos - this.xPos;
        int y = yPos - this.yPos;

        x = x/Math.abs(x);
        y = y/Math.abs(y);

        int tempXPos = this.xPos;
        int tempYPos = this.yPos;

        while(tempXPos >= 0 && tempXPos < 8 && tempYPos >= 0 && tempYPos < 8){
            if(tempXPos == xPos && tempYPos == yPos){
                return true;
            }
            tempXPos+=x;
            tempYPos+=y;
        }

        return false;
    }
}
