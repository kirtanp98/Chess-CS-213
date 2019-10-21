package Controller;

import Pieces.*;

public class Game {
    public Piece[][] game = new Piece[8][8];// [x][y] x goes to the right and y goes down
    public int moveNumber = 0;

    public Game(){
        for(int i = 0; i < game.length; i++){
            for(int j = 0; j < game.length; j++){
                game[j][i] = new Piece(j, i, -1);
            }
        }


        for(int i = 0; i < game.length; i++){
            game[i][1] = new Pawn(i, 1, 1);
            game[i][6] = new Pawn(i, 6, 0);
        }

        game[0][0] = new Rook(0, 0, 1);
        game[7][0] = new Rook(7, 0, 1);

        game[1][0] = new Knight(1, 0, 1);
        game[6][0] = new Knight(1, 0, 1);

        game[2][0] = new Bishop(2, 0, 1);
        game[5][0] = new Bishop(5, 0, 1);

        game[3][0] = new Queen(3, 0, 1);

        game[4][0] = new King(4, 0, 1);

        //white pieces, for now this will be hardcoded, but a loop should be used as it will make it easier
        game[0][7] = new Rook(0, 7, 0);
        game[7][7] = new Rook(7, 7, 0);

        game[1][7] = new Knight(1, 7, 1);
        game[6][7] = new Knight(1, 7, 1);

        game[2][7] = new Bishop(2, 7, 1);
        game[5][7] = new Bishop(5, 7, 1);

        game[3][7] = new Queen(3, 7, 1);

        game[4][7] = new King(4, 7, 1);

    }

    public void move(int a, int b){

    }

    public boolean isCheck(){
        return false;
    }

    public String toString(){
        String board = "";
        int row = 8;

        for(int i = 0; i < game.length; i++){
            for(int j = 0; j < game.length; j++){
                board += game[j][i]+ " ";
            }
            board += row + "\n";
            row--;
        }

        board+= " a  b  c  d  e  f  g  h  ";
        return board;
    }

}
