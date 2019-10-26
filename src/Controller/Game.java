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

        for (Piece[] pieces : game) {
            for (Piece piece : pieces) {
                if (piece != null) {
                    piece.setValidMoves(this.game);
                }
            }
        }

    }

    public void move(Pair startPos, Pair endPos){
        //Create a new test Board to check for invalid moves so that you don't mess with the real board while checking
        Piece[][] testGame = dupeBoard(game);
        if(game[startPos.x][startPos.y] == null){ //Checks for player trying to move null space
            System.out.println("No Piece at " + startPos);
        } else if(moveNumber % 2 != game[startPos.x][startPos.y].color){ //Checks for player trying to move opponents piece
            System.out.println("Cannot move opponent's Piece at " + startPos);
        } else {
            if(game[startPos.x][startPos.y].validMoves.contains(endPos)){ //Piece at atartX,startY can move to endX, endY

                testGame[startPos.x][startPos.y].updatePosition(endPos);
                testGame[endPos.x][endPos.y] = testGame[startPos.x][startPos.y];
                testGame[startPos.x][startPos.y] = null;
                setValidPieceMoves(testGame);

                if(isCheck(testGame)){ //Putting yourself in check
                    System.out.println("Putting yourself in check if you move from " + startPos + " to " + endPos);
                } else if(isOpponentCheck(testGame)) { //Putting opponent in check
                    game = testGame;
                    if(isCheckmate()){
                        if(moveNumber % 2 == 0){
                            System.out.println("White Wins");
                        } else {
                            System.out.println("Black Wins");
                        }
                    }
                    System.out.println("Move Successful");
                    System.out.println("Check");
                } else {
                    game = testGame;
                    System.out.println("Move Successful");
                }
            } else {
                System.out.println("Invalid move from " + startPos + " to " + endPos);
            }
        }
    }

    public void setValidPieceMoves(Piece[][] testGame){
        for (Piece[] pieces : testGame) {
            for (Piece piece : pieces) {
                piece.setValidMoves(testGame);
            }
        }
    }

    public Piece[][] dupeBoard(Piece[][] testGame){
        Piece[][] newGame = new Piece[8][8];
        for(int i = 0; i < testGame.length; i++) {
            for (int j = 0; j < testGame[i].length; j++) {
                newGame[i][j] = testGame[i][j];
            }
        }
        return newGame;
    }

    public boolean isCheckmate(){
        Piece[][] testGame = dupeBoard(game);
        for(int i = 0; i < game.length; i++) {
            for (int j = 0; j < game[i].length; j++) {
                if(moveNumber % 2 != game[i][j].color) { //Finds all opponent's pieces
                    for (Pair pair : game[i][j].validMoves) {

                        testGame[i][j].updatePosition(pair);
                        testGame[pair.x][pair.y] = testGame[i][j];
                        testGame[i][j] = null;
                        setValidPieceMoves(testGame);

                        if(!(isOpponentCheck(testGame))){
                            return false;
                        }
                        testGame = dupeBoard(game);
                    }
                }
            }
        }
        return true;
    }

    public boolean isCheck(Piece[][] testGame){
        Pair allyKingPos = null;
        for(int i = 0; i < testGame.length; i++) {
            for (int j = 0; j < testGame[i].length; j++) {
                if(testGame[i][j] instanceof King && moveNumber % 2 == testGame[i][j].color){//Finds your King
                    allyKingPos = new Pair(i, j);
                    break;
                }
            }
        }
        for (Piece[] pieces : testGame) {
            for (Piece piece : pieces) {
                if (piece.validMoves.contains(allyKingPos)){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isOpponentCheck(Piece[][] testGame){
        Pair opponentKingPos = null;
        for(int i = 0; i < testGame.length; i++) {
            for (int j = 0; j < testGame[i].length; j++) {
                if((testGame[i][j] instanceof King) && (moveNumber % 2 != testGame[i][j].color)){//Finds opponents King
                    opponentKingPos = new Pair(i, j);
                    break;
                }
            }
        }
        for (Piece[] pieces : testGame) {
            for (Piece piece : pieces) {
                if (piece.validMoves.contains(opponentKingPos)){
                    return true;
                }
            }
        }
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
