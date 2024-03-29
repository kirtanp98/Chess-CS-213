package Controller;

import Data.Pair;
import Pieces.*;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author Zain Ul-Abdin
 * @author Kirtan Patel
 */

/**
 * The main game class, where all the board is set up and the game logic gets applied.
 */
public class Game {
    /**
     * The game variable is the 8 by 8 board where Piece objects occupies it.
     */
    public Piece[][] game = new Piece[8][8];// [x][y] x goes to the right and y goes down

    /**
     * The number of moves that passed in the game.
     */
    public int moveNumber = 0;

    /**
     * Boolean that checks if the game has finished.
     */
    public boolean gameFinished = false;

    /**
     * Boolean that checks if the game is in a draw.
     */
    private boolean drawCondition = false;

    /**
     * Boolean that checks if a piece is in for a promotion.
     */
    private boolean promotionBoolean = false;

    /**
     * Constructor of the Game class, where the board gets set up with the starting pieces and their position.
     */
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

    /**
     * Method that converts the input string to the correct format that the game accepts.
     * After converting the string, the method inputs the move in to the moves method.
     *
     * @param move Accepts the input string.
     */
    public void moveStringConverter(String move){
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
        if(!gameFinished) {
            System.out.println(this);
        }
        System.out.println();
    }

    /**
     * The method where most of the game logic is located.
     * Checks if the move a piece is possible and moves it.
     *
     * @param startPos The starting position.
     * @param endPos The ending position.
     */
    public void move(Pair startPos, Pair endPos){
        //Create a new test Board to check for invalid moves so that you don't mess with the real board while checking
        Piece[][] testGame = dupeBoard(game);
        if(game[startPos.x][startPos.y] == null){ //Checks for player trying to move null space
            System.out.println("No Piece at " + startPos);
        } else if(moveNumber % 2 != game[startPos.x][startPos.y].color){ //Checks for player trying to move opponents piece
            System.out.println("Cannot move opponent's Piece at " + startPos);
        } else {
            boolean enPassent = pawnCapture(startPos, endPos);

            if(game[startPos.x][startPos.y].validMoves.contains(endPos)){ //Piece at atartX,startY can move to endX, endY

                if(enPassent && testGame[endPos.x][endPos.y] == null){
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
                    if(startPos.equals(new Pair(4, 7)) && endPos.equals(new Pair(2, 7))){ //White left castle
                        testGame[startPos.x][startPos.y].updatePosition(endPos);
                        testGame[0][7].updatePosition(new Pair(3, 7));
                        testGame[endPos.x][endPos.y] = testGame[startPos.x][startPos.y];
                        testGame[3][7] = testGame[0][7];
                        testGame[startPos.x][startPos.y] = null;
                        testGame[0][7] = null;
                        setValidPieceMoves(testGame);
                    } else if(startPos.equals(new Pair(4, 7)) && endPos.equals(new Pair(6, 7))) { //White right castle
                        testGame[startPos.x][startPos.y].updatePosition(endPos);
                        testGame[7][7].updatePosition(new Pair(5, 7));
                        testGame[endPos.x][endPos.y] = testGame[startPos.x][startPos.y];
                        testGame[5][7] = testGame[7][7];
                        testGame[startPos.x][startPos.y] = null;
                        testGame[7][7] = null;
                        setValidPieceMoves(testGame);
                    } else if(startPos.equals(new Pair(4, 0)) && endPos.equals(new Pair(2, 0))){ //Black left castle
                        testGame[startPos.x][startPos.y].updatePosition(endPos);
                        testGame[0][0].updatePosition(new Pair(3, 0));
                        testGame[endPos.x][endPos.y] = testGame[startPos.x][startPos.y];
                        testGame[3][0] = testGame[0][0];
                        testGame[startPos.x][startPos.y] = null;
                        testGame[0][0] = null;
                        setValidPieceMoves(testGame);
                    } else if(startPos.equals(new Pair(4, 0)) && endPos.equals(new Pair(6, 0))){ //Black right castle
                        testGame[startPos.x][startPos.y].updatePosition(endPos);
                        testGame[7][0].updatePosition(new Pair(5, 0));
                        testGame[endPos.x][endPos.y] = testGame[startPos.x][startPos.y];
                        testGame[5][0] = testGame[7][0];
                        testGame[startPos.x][startPos.y] = null;
                        testGame[7][0] = null;
                        setValidPieceMoves(testGame);
                    } else {
                        testGame[startPos.x][startPos.y].updatePosition(endPos);
                        testGame[endPos.x][endPos.y] = testGame[startPos.x][startPos.y];
                        testGame[startPos.x][startPos.y] = null;
                        setValidPieceMoves(testGame);
                    }
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
                            return;
                        } else {
                            System.out.println("Black Wins");
                            gameFinished = true;
                            return;
                        }
                    }
                    //System.out.println("Move Successful");
                    moveNumber++;
                    System.out.println("Check");
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
                } else {
                    game = testGame;
                    if(isStalemate()) {
                        System.out.println("In Stalemate");
                        gameFinished = true;
                        return;
                    }
                    //System.out.println("Move Successful");
                    moveNumber++;
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
                }
            } else {
                System.out.println("Invalid move from " + startPos + " to " + endPos);
                System.out.println();
            }
        }
    }

    /**
     * Method that handles the promotion of a pawn.
     *
     * @param startPos The starting position.
     * @param endPos The ending position.
     * @param promotion The string containing the piece that the user wants to promote to.
     */
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
                    return;
                } else {
                    System.out.println("Black Wins");
                    gameFinished = true;
                    return;
                }
            }
            System.out.println("Check");
        }


    }

    /**
     * After pieces move, their validMoves arraylist need to updated.
     * This method updates the validMoves arraylist of all pieces if called.
     *
     * @param testGame The game board.
     */
    public void setValidPieceMoves(Piece[][] testGame){
        for (Piece[] pieces : testGame) {
            for (Piece piece : pieces) {
                if(piece != null){
                    piece.setValidMoves(testGame);
                }
            }
        }
        if(game[4][7] != null && game[4][7] instanceof King){ //White left Castle
            King k = (King) game[4][7];
            if(!(k.moved)){
                if(game[0][7] instanceof Rook && game[1][7] == null && game[2][7] == null && game[3][7] == null && (!attacked(new Pair(1,7))) && (!attacked(new Pair(2,7))) && (!attacked(new Pair(3,7)))){
                    Rook r = (Rook) game[0][7];
                    if(!(r.moved)){
                        k.validMoves.add(new Pair(2, 7));
                        //r.validMoves.add(new Pair(3,7)); Shouldn't be in Rook?
                    }
                }
                if(game[7][7] instanceof Rook && game[5][7] == null && game[6][7] == null && (!attacked(new Pair(5,7))) && (!attacked(new Pair(6,7)))){ //White Right Castle
                    Rook r1 = (Rook) game[7][7];
                    if(!(r1.moved)){
                        k.validMoves.add(new Pair(6, 7));
                        //r1.validMoves.add(new Pair(5,7)); Shouldn't be in Rook?
                    }
                }
            }
        }
        if(game[4][0] != null && game[4][0] instanceof King){ //Black left Castle
            King k = (King) game[4][0];
            if(!(k.moved)){
                if(game[0][0] instanceof Rook && game[1][0] == null && game[2][0] == null && game[3][0] == null && (!attacked(new Pair(1,0))) && (!attacked(new Pair(2,0))) && (!attacked(new Pair(3,0)))){
                    Rook r = (Rook) game[0][0];
                    if(!(r.moved)){
                        k.validMoves.add(new Pair(2, 0));
                        r.validMoves.add(new Pair(3,0));
                    }
                }
                if(game[7][0] instanceof Rook && game[5][0] == null && game[6][0] == null && (!attacked(new Pair(5,0))) && (!attacked(new Pair(6,0)))){ //Black Right Castle
                    Rook r1 = (Rook) game[7][0];
                    if(!(r1.moved)){
                        k.validMoves.add(new Pair(6, 0));
                        r1.validMoves.add(new Pair(5,0));
                    }
                }
            }
        }
    }

    /**
     * This method duplicates the gameboard.
     *
     * @param testGame The game board.
     * @return Piece[][] The duplicated game board.
     */
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
        setValidPieceMoves(newGame);
        return newGame;
    }

    /**
     * This method checks if there is a stalemate and returns a boolean.
     *
     * @return boolean
     */
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

    /**
     * This method checks if there is a checkmate and returns a boolean.
     *
     * @return boolean
     */
    public boolean isCheckmate(){
        Piece[][] testGame = dupeBoard(game);
        for(int i = 0; i < game.length; i++) {
            for (int j = 0; j < game[i].length; j++) {
                if(game[i][j] != null) {
                    if (moveNumber % 2 != game[i][j].color) { //Finds all opponent's pieces
                        Pair[] valid = new Pair[game[i][j].validMoves.size()];
                        game[i][j].validMoves.toArray(valid);

                        for(Pair pair : valid){
                            if(game[i][j] instanceof King){ //Can't get out of check using castling
                                if(i == 4 && j == 7){
                                    if(!(pair.equals(new Pair(2, 7)) || pair.equals(new Pair(6, 7)))){
                                        testGame[i][j].updatePosition(pair);
                                        testGame[pair.x][pair.y] = testGame[i][j];
                                        testGame[i][j] = null;
                                        setValidPieceMoves(testGame);
                                    }
                                } else if (i == 4 && j == 0) {
                                    if (!(pair.equals(new Pair(2, 0)) || pair.equals(new Pair(6, 0)))) {
                                        testGame[i][j].updatePosition(pair);
                                        testGame[pair.x][pair.y] = testGame[i][j];
                                        testGame[i][j] = null;
                                        setValidPieceMoves(testGame);
                                    }
                                } else {
                                    testGame[i][j].updatePosition(pair);
                                    testGame[pair.x][pair.y] = testGame[i][j];
                                    testGame[i][j] = null;
                                    setValidPieceMoves(testGame);
                                }
                            } else {
                                testGame[i][j].updatePosition(pair);
                                testGame[pair.x][pair.y] = testGame[i][j];
                                testGame[i][j] = null;
                                setValidPieceMoves(testGame);
                            }


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

    /**
     * This methods check if the king is under attack or will be in check.
     * This is a helper method to help with castling.
     *
     * @param p Takes in a pair object.
     * @return boolean.
     */
    public boolean attacked(Pair p){
        for(int i = 0; i < game.length; i++) {
            for (int j = 0; j < game[i].length; j++) {
                if(game[i][j] != null) {
                    if (moveNumber % 2 != game[i][j].color) { //Finds all opponent's pieces
                        Pair[] valid = new Pair[game[i][j].validMoves.size()];
                        game[i][j].validMoves.toArray(valid);
                        for(Pair pair : valid){
                            if (pair.equals(p)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * This method checks if there is a check happening with the current player.
     *
     * @param testGame Takes in the board.
     * @return boolean
     */
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

    /**
     * This method checks if there is a check happening with the opponent.
     *
     * @param testGame Takes in the board.
     * @return boolean
     */
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

    /**
     * This method checks if an en passant can happen.
     *
     * @param start Starting position, takes in the pair object.
     * @param end End position, takes in the pair object.
     * @return boolean
     */
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
                 if(testGame[end.x][end.y] == null && (testGame[end.x][end.y+1] instanceof Pawn) && testGame[end.x][end.y+1].color == 1) {
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
                if(testGame[end.x][end.y] == null && (testGame[end.x][end.y-1] instanceof Pawn) && testGame[end.x][end.y-1].color == 0) {
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

    /**
     * This method resets the Pawn's twoMove variable to false, which is used to figure out if en passant is possible.
     *
     * @param testGame Takes in the board.
     */
    public void pawnTwoMoveReset(Piece[][] testGame){
        for(int i = 0; i < testGame.length; i++){
            if(testGame[i][3] instanceof Pawn){
                Pawn temp = (Pawn) testGame[i][3];
                temp.twoMove = false;
                testGame[i][3] = temp;
            }
            if(testGame[i][4] instanceof Pawn){
                Pawn temp = (Pawn) testGame[i][4];
                temp.twoMove = false;
                testGame[i][4] = temp;
            }
        }
        setValidPieceMoves(testGame);
    }

    /**
     * This method converts characters into an integer.
     * This helps with knowing where to move the piece in the 2d array board.
     *
     * @param c Character
     * @return Integer
     */
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

    /**
     * Overrides toString() method in the object class.
     * Return a String that represents the game board.
     *
     * @return String
     */
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
