package Controller;

import Data.Pair;
import Pieces.*;

import java.util.ArrayList;
import java.util.Iterator;

public class Game {
    public Piece[][] game = new Piece[8][8];// [x][y] x goes to the right and y goes down
    public int moveNumber = 0;
    public boolean gameFinished = false;
    private boolean drawCondition = false;
    private boolean promotionBoolean = false;

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

        if (moves.length < 1 || moves.length > 3){
            System.out.println("Not correct format");
            return;
        }

        if (moves.length == 1){
            if(moves[0].equals("resign")){
                moveNumber++;
                if(moveNumber % 2 == 0){
                    System.out.println("White Wins");
                    gameFinished = true;
                } else {
                    System.out.println("Black Wins");
                    gameFinished = true;
                }
                return;
            }

            if(moves[0].equals("draw")){
                if(drawCondition){
                    gameFinished = true;
                    return;
                }else {
                    System.out.println("Draw has not been asked");
                }
            }
        }

        if (drawCondition) {
            drawCondition = false;
        }

        int startX = letterToInt(moves[0].charAt(0));
        int startY = Math.abs(8 -Character.getNumericValue(moves[0].charAt(1)));
        int endX =  letterToInt(moves[1].charAt(0));
        int endY = Math.abs(8 -Character.getNumericValue(moves[1].charAt(1)));

        if(startX > 7 || startX < 0 || startY > 7 || startY < 0 || endX > 7 || endX < 0 || endY > 7 || endY < 0){
            System.out.println("Input out of bound");
            return;
        }

        Pair startPos = new Pair(startX, startY);
        Pair endPos = new Pair(endX, endY);

        if (moves.length > 2){
            if(moves[2].equals("draw?")){
                drawCondition = true;
            }
            else if (moves[2].length() == 1){
                promotion(startPos, endPos, moves[2]);
                System.out.println(this);
                return;
            }
        }

        move(startPos, endPos);
        System.out.println(this);
    }

    public void move(Pair startPos, Pair endPos){
        //Create a new test Board to check for invalid moves so that you don't mess with the real board while checking
        Piece[][] testGame = dupeBoard(game);
        if(game[startPos.x][startPos.y] == null){ //Checks for player trying to move null space
            System.out.println("No Piece at " + startPos);
        } else if(moveNumber % 2 != game[startPos.x][startPos.y].color){ //Checks for player trying to move opponents piece
            System.out.println("Cannot move opponent's Piece at " + startPos);
        } else {
            boolean castle = castle(startPos, endPos);
            boolean enPassent = pawnCapture(startPos, endPos);

            if(game[startPos.x][startPos.y].validMoves.contains(endPos)){ //Piece at atartX,startY can move to endX, endY

                if(game[startPos.x][startPos.y] instanceof King){
                    King temp = (King)testGame[startPos.x][startPos.y];
                    temp.moved = true;
                    testGame[startPos.x][startPos.y] = temp;
                    setValidPieceMoves(testGame);
                }

                if(game[startPos.x][startPos.y] instanceof Rook){
                    Rook temp = (Rook)testGame[startPos.x][startPos.y];
                    temp.moved = true;
                    testGame[startPos.x][startPos.y] = temp;
                    setValidPieceMoves(testGame);
                }

                if(castle){
                    testGame[startPos.x][startPos.y].updatePosition(endPos);
                    testGame[endPos.x][endPos.y] = testGame[startPos.x][startPos.y];
                    testGame[startPos.x][startPos.y] = null;

                    testGame[7][startPos.y].updatePosition(new Pair(endPos.x-1,endPos.y));
                    testGame[endPos.x-1][endPos.y] = testGame[7][startPos.y];

                    testGame[7][startPos.y] = null;
                    setValidPieceMoves(testGame);
                } else if(enPassent && testGame[endPos.x][endPos.y] == null){
                    if(testGame[startPos.x][startPos.y].color == 0) {
                        testGame[endPos.x][endPos.y + 1] = null;
                    }else {
                        testGame[endPos.x][endPos.y - 1] = null;
                    }
                    testGame[startPos.x][startPos.y].updatePosition(endPos);
                    testGame[endPos.x][endPos.y] = testGame[startPos.x][startPos.y];
                    testGame[startPos.x][startPos.y] = null;
                    setValidPieceMoves(testGame);
                }else {
                    testGame[startPos.x][startPos.y].updatePosition(endPos);
                    testGame[endPos.x][endPos.y] = testGame[startPos.x][startPos.y];
                    testGame[startPos.x][startPos.y] = null;
                    setValidPieceMoves(testGame);
                }

                pawnTwoMoveReset(testGame);

                if((testGame[endPos.x][endPos.y] instanceof Pawn)){
                    int moveNumber = Math.abs(endPos.y - startPos.y);
                    if (moveNumber == 2){
                        Pawn temp = (Pawn)testGame[endPos.x][endPos.y];
                        temp.twoMove = true;
                        testGame[endPos.x][endPos.y] = temp;
                        setValidPieceMoves(testGame);
                    } else {
                        Pawn temp = (Pawn)testGame[endPos.x][endPos.y];
                        temp.twoMove = false;
                        testGame[endPos.x][endPos.y] = temp;
                        setValidPieceMoves(testGame);
                    }
                }
                //default promotion
                if(!promotionBoolean && (testGame[endPos.x][endPos.y] instanceof Pawn) && (((testGame[endPos.x][endPos.y].color == 0) && (endPos.y == 0)) || (testGame[endPos.x][endPos.y].color == 1) && (endPos.y == 7))){ //Promotion
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
                    if(isStalemate()) {
                        System.out.println("In Stalemate");
                        gameFinished = true;
                    }
                    System.out.println("Move Successful");
                    moveNumber++;
                }
            } else {
                System.out.println("Invalid move from " + startPos + " to " + endPos);
            }
        }
    }

    public void promotion(Pair startPos, Pair endPos, String promotion){
        promotionBoolean = true;
        move(startPos, endPos);
        promotionBoolean = false;
        Piece current = game[endPos.x][endPos.y];
        Piece promotionReplacement = null;

        if(!(game[endPos.x][endPos.y] instanceof Pawn)){
            System.out.println("Can not promote");
            return;
        }

        if(promotion.equals("R")){
            promotionReplacement = new Rook(endPos, current.color);
        }
        else if(promotion.equals("N")){
            promotionReplacement = new Knight(endPos, current.color);
        }
        else if(promotion.equals("B")){
            promotionReplacement = new Bishop(endPos, current.color);
        }
        else if(promotion.equals("Q")){
            promotionReplacement = new Queen(endPos, current.color);
        }
        else {
            System.out.println("Can not promote");
        }
        Piece[][] testGame = dupeBoard(game);

        testGame[endPos.x][endPos.y] = promotionReplacement;
        setValidPieceMoves(testGame);
        game = testGame;
        if(isOpponentCheck(testGame)) {

            if (isCheckmate()) {
                if (moveNumber % 2 == 0) {
                    System.out.println("White Wins");
                    gameFinished = true;
                } else {
                    System.out.println("Black Wins");
                    gameFinished = true;
                }
            }
            System.out.println("Check");
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
                    newP.twoMove = p.twoMove;
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
                    newP.moved = p.moved;
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
                    newP.moved = p.moved;
                    newP.validMoves = new ArrayList<>(p.validMoves);
                    newGame[i][j] = newP;
                }
            }
        }
        return newGame;
    }

    public boolean isStalemate(){
        Piece[][] testGame = dupeBoard(game);
        if(isCheck(testGame)){
            return false;
        }

        for(int i = 0; i < game.length; i++) {
            for (int j = 0; j < game[i].length; j++) {
                if (game[i][j] != null) {
                    if (moveNumber % 2 != game[i][j].color) {
                        Pair[] valid = new Pair[game[i][j].validMoves.size()];
                        game[i][j].validMoves.toArray(valid);

                        for(Pair pair : valid){
                            testGame[i][j].updatePosition(pair);
                            testGame[pair.x][pair.y] = testGame[i][j];
                            testGame[i][j] = null;
                            setValidPieceMoves(testGame);

                            if (!isOpponentCheck(testGame)) {
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

    public boolean castle(Pair king, Pair endPos){
        Piece[][] testGame = dupeBoard(game);

        if(king.y != endPos.y){
            return false;
        }

        if((testGame[king.x][king.y] instanceof King) && (testGame[7][king.y] instanceof Rook)){
            King kingTemp = (King) testGame[king.x][king.y];
            Rook rookTemp = (Rook) testGame[7][king.y];
            if(kingTemp.moved || rookTemp.moved){
                return false;
            }

            if(king.x != 4 && !(king.y == 0|| king.y == 7)){
                return false;
            }

            if(king.x != 7 && !(king.y == 0|| king.y == 7)){
                return false;
            }

            if(endPos.x != 6){
                return false;
            }

            if(!(testGame[king.x+1][king.y] == null && testGame[king.x+2][king.y] == null)){
                return false;
            }
        } else {
            return false;
        }
        game[king.x][king.y].validMoves.add(new Pair(endPos.x, endPos.y));
        game[7][king.y].validMoves.add(new Pair(endPos.x-1, endPos.y));
        return true;
    }

    public boolean pawnCapture(Pair start, Pair end) {
        Piece[][] testGame = dupeBoard(game);
        if(testGame[start.x][start.y] == null){
            return false;
        }
        if(!(testGame[start.x][start.y] instanceof Pawn)){
            return false;
        }

        if(testGame[start.x][start.y].color == 0){
            if(end.y == start.y-1 && (end.x == start.x+1 || end.x == start.x-1)){
                if(testGame[end.x][end.y]!= null){
                    game[start.x][start.y].validMoves.add(end);
                    return true;
                }else if(testGame[end.x][end.y] == null && (testGame[end.x][end.y+1] instanceof Pawn) && testGame[end.x][end.y+1].color == 1) {
                    Pawn temp = (Pawn) testGame[end.x][end.y+1];
                    if(temp.twoMove){
                        game[start.x][start.y].validMoves.add(end);
                        return true;
                    }
                } else {
                    return false;
                }
            }

        }else{
            if(end.y == start.y+1 && (end.x == start.x+1 || end.x == start.x-1)){
                if(testGame[end.x][end.y]!= null){
                    game[start.x][start.y].validMoves.add(end);
                    return true;
                }else if(testGame[end.x][end.y] == null && (testGame[end.x][end.y-1] instanceof Pawn) && testGame[end.x][end.y-1].color == 0) {
                    Pawn temp = (Pawn) testGame[end.x][end.y-1];
                    if(temp.twoMove){
                        game[start.x][start.y].validMoves.add(end);
                        return true;
                    }
                } else {
                    return false;
                }
            }
        }

        return false;
    }

    public void pawnTwoMoveReset(Piece[][] testGame){
        for(int i = 0; i < testGame.length; i++){
            if(testGame[i][2] instanceof Pawn){
                Pawn temp = (Pawn) testGame[i][2];
                temp.twoMove = false;
                testGame[i][2] = temp;
            }
            if(testGame[i][5] instanceof Pawn){
                Pawn temp = (Pawn) testGame[i][5];
                temp.twoMove = false;
                testGame[i][5] = temp;
            }
        }
        setValidPieceMoves(testGame);
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
