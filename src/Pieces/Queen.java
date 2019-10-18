package Pieces;

public class Queen extends Piece {

    public Queen(int xPos, int yPos, int color) {
        super(xPos, yPos, color);
    }

    @Override
    public boolean isValidPath(int xPos, int yPos) {//Combined rook and bishop moves together
        if(this.xPos == xPos || this.yPos == yPos){
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

        }else{
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

        }

        return false;
    }
}
