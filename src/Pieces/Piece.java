package Pieces;

public class Piece {
    int xPos;
    int yPos;
    int color;

    public Piece(int xPos, int yPos, int color){
        this.xPos = xPos;
        this.yPos = yPos;
        this.color = color;
    }

    public boolean isValidPath(int xPos, int yPos){
        return false;
    }

    public void updatePosition(int xPos, int yPos){
        this.xPos = xPos;
        this.yPos = yPos;
    }

    //TODO Implement getType()
//    Class<?> getType(){
//        return this.getType();
//    }
    @Override
    public String toString() {
        String result = "";
        if(xPos % 2 == 1 && yPos % 2 == 0 || xPos % 2 == 0 && yPos % 2 == 1 ){
            return "##";
        }else{
            return "  ";
        }
    }
}
