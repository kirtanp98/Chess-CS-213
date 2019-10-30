package Data;

/**
 * @author Zain Ul-Abdin
 * @author Kirtan Patel
 */

/**
 * The Pair class represents the x and y coordinates of a piece.
 */
public class Pair {
    public int x;
    public int y;

    /**
     * The Pair class constructor.
     *
     * @param x The x coordinate of the board.
     * @param y The y coordinate of the board.
     */
    public Pair(int x, int y){
        this.x = x;
        this.y = y;
    }

    /**
     * This method checks if the pair is valid.
     *
     * @return boolean
     */
    public boolean isValidPair(){
        return x >= 0 && x <= 7 && y >= 0 && y <= 7;
    }

    /**
     * Overrides toString() method in the object class.
     * Return a String that represents the Pair class.
     *
     * @return String
     */
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    /**
     *
     * @param other Checks if another object is equal to itself.
     * @return boolean
     */
    @Override
    public boolean equals(Object other) {
        if (other == null || !(other instanceof Pair)) {
            return false;
        }
        Pair p = (Pair) other;
        return (this.x == p.x && this.y == p.y);
    }
}