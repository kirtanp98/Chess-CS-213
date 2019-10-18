package Pieces;

public class Knight extends Piece {

    public Knight(int xPos, int yPos, int color) {
        super(xPos, yPos, color);
    }

    @Override
    public boolean isValidPath(int xPos, int yPos){

        if(this.xPos-1 == xPos && this.yPos == yPos-3){
            return true;
        }
        if(this.xPos-3 == xPos && this.yPos == yPos-1){
            return true;
        }

        if(this.xPos-1 == xPos && this.yPos == yPos+3){
            return true;
        }
        if(this.xPos-3 == xPos && this.yPos == yPos+1){
            return true;
        }

        if(this.xPos+1 == xPos && this.yPos == yPos+3){
            return true;
        }
        if(this.xPos+3 == xPos && this.yPos == yPos+1){
            return true;
        }

        if(this.xPos+1 == xPos && this.yPos == yPos-3){
            return true;
        }
        if(this.xPos-3 == xPos && this.yPos == yPos-1){
            return true;
        }

        return false;
    }
}
