package Controller;

import Data.Pair;
import Pieces.*;

import java.util.ArrayList;
import java.util.Iterator;

public class Game {
    public Piece[][] game = new Piece[8][8];// [x][y] x goes to the right and y goes down
    public int moveNumber = 0;
    public boolean gameFinished = false;

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
        game[6][0] = new Knight(new Pair(6, 0), 1);

        game[2][0] = new Bishop(new Pair(2, 0), 1);
        game[5][0] = new Bishop(new Pair(5, 0), 1);

        game[3][0] = new Queen(new Pair(3, 0), 1);

        game[4][0] = new King(new Pair(4, 0), 1);

        //white pieces, for now this will be hardcoded, but a loop should be used as it will make it easier
        game[0][7] = new Rook(new Pair(0, 7), 0);
        game[7][7] = new Rook(new Pair(7, 7), 0);

        game[1][7] = new Knight(new Pair(1, 7), 0);
        game[6][7] = new Knight(new Pair(6, 7), 0);

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

    public void moveStringConverter(String move){ //promotion is not taken in to consideration yet
        String moves[] = move.split(" ");

        int startX = letterToInt(moves[0].charAt(0));
        int startY = Math.abs(8 -Character.getNumericValue(moves[0].charAt(1)));
        int endX =  letterToInt(moves[1].charAt(0));
        int endY = Math.abs(8 -Character.getNumericValue(moves[1].charAt(1)));

        Pair startPos = new Pair(startX, startY);
        Pair endPos = new Pair(endX, endY);

        move(startPos, endPos);
        System.out.println(this);
        //return position;
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

                if((testGame[endPos.x][endPos.y] instanceof Pawn) && (((testGame[endPos.x][endPos.y].color == 0) && (endPos.y == 0)) || (testGame[endPos.x][endPos.y].color == 1) && (endPos.y == 7))){ //Promotion
                    testGame[endPos.x][endPos.y] = new Queen(new Pair(endPos.x, endPos.y), testGame[endPos.x][endPos.y].color);
                    setValidPieceMoves(testGame);
                }

                if(isCheck(testGame)){ //Putting yourself in check
                    System.out.println("Putting yourself in check if you move from " + startPos + " to " + endPos);
                } else if(isOpponentCheck(testGame)) { //Putting opponent in check
                    game = testGame;
                    if(isCheckmate()){
                        if(moveNumber % 2 == 0){
                            System.out.println("White Wins");
                            gameFinished = true;
                        } else {
                            System.out.println("Black Wins");
                            gameFinished = true;
                        }
                    }
                    System.out.println("Move Successful");
                    moveNumber++;
                    System.out.println("Check");
                } else {
                    game = testGame;
                    System.out.println("Move Successful");
                    moveNumber++;
                }
            } else {
                System.out.println("Invalid move from " + startPos + " to " + endPos);
            }
        }
    }

    public void setValidPieceMoves(Piece[][] testGame){
        for (Piece[] pieces : testGame) {
            for (Piece piece : pieces) {
                if(piece != null){
                    piece.setValidMoves(testGame);
                }
            }
        }
    }

    public Piece[][] dupeBoard(Piece[][] testGame){
        Piece[][] newGame = new Piece[8][8];
        for(int i = 0; i < testGame.length; i++) {
            for (int j = 0; j < testGame[i].length; j++) {
                if(testGame[i][j] == null){
                    newGame[i][j] = null;
                } else if(testGame[i][j] instanceof Pawn){
                    Pawn p = (Pawn) testGame[i][j];
                    Pawn newP = new Pawn(new Pair(p.position.x, p.position.y), p.color);
                    newP.validMoves = new ArrayList<>(p.validMoves);
                    newGame[i][j] = newP;
                } else if(testGame[i][j] instanceof Knight){
                    Knight p = (Knight) testGame[i][j];
                    Knight newP = new Knight(new Pair(p.position.x, p.position.y), p.color);
                    newP.validMoves = new ArrayList<>(p.validMoves);
                    newGame[i][j] = newP;
                } else if(testGame[i][j] instanceof Rook){
                    Rook p = (Rook) testGame[i][j];
                    Rook newP = new Rook(new Pair(p.position.x, p.position.y), p.color);
                    newP.validMoves = new ArrayList<>(p.validMoves);
                    newGame[i][j] = newP;
                } else if(testGame[i][j] instanceof Bishop){
                    Bishop p = (Bishop) testGame[i][j];
                    Bishop newP = new Bishop(new Pair(p.position.x, p.position.y), p.color);
                    newP.validMoves = new ArrayList<>(p.validMoves);
                    newGame[i][j] = newP;
                } else if(testGame[i][j] instanceof Queen){
                    Queen p = (Queen) testGame[i][j];
                    Queen newP = new Queen(new Pair(p.position.x, p.position.y), p.color);
                    newP.validMoves = new ArrayList<>(p.validMoves);
                    newGame[i][j] = newP;
                } else if(testGame[i][j] instanceof King){
                    King p = (King) testGame[i][j];
                    King newP = new King(new Pair(p.position.x, p.position.y), p.color);
                    newP.validMoves = new ArrayList<>(p.validMoves);
                    newGame[i][j] = newP;
                }
            }
        }
        return newGame;
    }

    public boolean isCheckmate(){
        Piece[][] testGame = dupeBoard(game);
        for(int i = 0; i < game.length; i++) {
            for (int j = 0; j < game[i].length; j++) {
                if(game[i][j] != null) {
                    if (moveNumber % 2 != game[i][j].color) { //Finds all opponent's pieces
                        Pair[] valid = new Pair[game[i][j].validMoves.size()];
                        game[i][j].validMoves.toArray(valid);

                        for(Pair pair : valid){

                            testGame[i][j].updatePosition(pair);
                            testGame[pair.x][pair.y] = testGame[i][j];
                            testGame[i][j] = null;
                            setValidPieceMoves(testGame);

                            if (!(isOpponentCheck(testGame))) {
                                return false;
                            }
                            testGame = dupeBoard(game);
                        }
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
                if(piece != null){
                    if (piece.validMoves.contains(allyKingPos)){
                        return true;
                    }
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
                if(piece != null){
                    if (piece.validMoves.contains(opponentKingPos)){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public int letterToInt(char c){
        int number = -1;

        switch (c){
            case 'a':
                number = 0;
                break;
            case 'b':
                number = 1;
                break;
            case 'c':
                number = 2;
                break;
            case 'd':
                number = 3;
                break;
            case 'e':
                number = 4;
                break;
            case 'f':
                number = 5;
                break;
            case 'g':
                number = 6;
                break;
            case 'h':
                number = 7;
                break;
            default:
                break;
        }

        return number;
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
