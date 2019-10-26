package Data;

public class Pair {
    public int x;
    public int y;

    public Pair(int x, int y){
        this.x = x;
        this.y = y;
    }

    public boolean isValidPair(){
        return x >= 0 && x <= 7 && y >= 0 && y <= 7;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}