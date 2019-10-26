package Controller;

import Data.Pair;
import Pieces.*;

public class Game {
    public Piece[][] game = new Piece[8][8];// [x][y] x goes to the right and y goes down
    public int moveNumber = 0;

    public Game(){

        for(int i = 0; i < game.length; i++) {
            for (int j = 0; j < game[i].length; j++) {
                game[i][j] = null;
            }
        }

        for(int i = 0; i < game.length; i++){
            game[i][1] = new Pawn(new Pair(i, 1), 1);
            game[i][6] = new Pawn(new Pair(i, 6), 0);
        }

        game[0][0] = new Rook(new Pair(0, 0), 1);
        game[7][0] = new Rook(new Pair(7, 0), 1);

        game[1][0] = new Knight(new Pair(1, 0), 1);
        game[6][0] = new Knight(new Pair(1, 0), 1);

        game[2][0] = new Bishop(new Pair(2, 0), 1);
        game[5][0] = new Bishop(new Pair(5, 0), 1);

        game[3][0] = new Queen(new Pair(3, 0), 1);

        game[4][0] = new King(new Pair(4, 0), 1);

        //white pieces, for now this will be hardcoded, but a loop should be used as it will make it easier
        game[0][7] = new Rook(new Pair(0, 7), 0);
        game[7][7] = new Rook(new Pair(7, 7), 0);

        game[1][7] = new Knight(new Pair(1, 7), 0);
        game[6][7] = new Knight(new Pair(1, 7), 0);

        game[2][7] = new Bishop(new Pair(2, 7), 0);
        game[5][7] = new Bishop(new Pair(5, 7), 0);

        game[3][7] = new Queen(new Pair(3, 7), 0);

        game[4][7] = new King(new Pair(4, 7), 0);

        for(int i = 0; i < game.length; i++) {
            for (int j = 0; j < game[i].length; j++) {
                if(game[i][j] != null){
                    game[i][j].setValidMoves(this);
                }
            }
        }

    }

    public void move(Pair startPos, Pair endPos){

    }

    public boolean isCheck(){
        return false;
    }

    public String toString(){
        String board = "";
        int row = 8;
        for(int i = 0; i < game.length; i++){
            for(int j = 0; j < game[i].length; j++){
                if(game[j][i] == null){
                    if(i % 2 == 1 && j % 2 == 0 || i % 2 == 0 && j % 2 == 1 ){
                        board += "## ";
                    } else {
                        board += "   ";
                    }
                } else {
                    board += game[j][i] + " ";
                }
            }
            board += row + "\n";
            row--;
        }
        board+= " a  b  c  d  e  f  g  h  ";
        return board;
    }

}
