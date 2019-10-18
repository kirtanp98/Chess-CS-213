package Pieces;

public class Pawn extends Piece {
    boolean hasMoved = false;

    public Pawn(int xPos, int yPos, int color) {
        super(xPos, yPos, color);
    }

    @Override
    public boolean isValidPath(int xPos, int yPos) {
        if(hasMoved){
            if(color == 0){
                if(xPos!= this.xPos || yPos != this.yPos-1){
                    return false;
                }
            }else{
                if(xPos!= this.xPos || yPos != this.yPos+1){
                    return false;
                }
            }
        }else{
            if(color == 0){
                if(xPos!= this.xPos || yPos < this.yPos-2){
                    return false;
                }
            }else{
                if(xPos!= this.xPos || yPos > this.yPos+2){
                    return false;
                }
            }
        }
        return true;
    }
}
