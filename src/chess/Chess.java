package chess;

import Controller.Game;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

/**
 * @author Zain Ul-Abdin
 * @author Kirtan Patel
 */

/**
 * The chess class handles the inputs and creation of the game.
 * When the main method is called, a new chess game is created. The game will keep running until the game ends.
 */

public class Chess {
    /**
     * The main method where the chess game starts
     * @param args String args
     */

    public static void main(String[] args) {
        Game newGame = new Game();

//Using file to run moves
/*        List<String> allLines = Files.readAllLines(Paths.get("File.txt"));
        for (String line : allLines) {
            newGame.moveStringConverter(line);
            if(newGame.gameFinished){
                break;
            }
        }
*/
        Scanner scanner = new Scanner(System.in);
        System.out.println(newGame);
        System.out.println();

        while(!newGame.gameFinished) {
            if(newGame.moveNumber % 2 == 0) {
                System.out.print("White's Move: ");
            }else{
                System.out.print("Black's Move: ");
            }
            String input = scanner.nextLine();
            System.out.println();
            newGame.moveStringConverter(input);
        }

        scanner.close();
    }

}
