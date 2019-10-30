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
public class Chess {

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

        while(!newGame.gameFinished) {
            String input = scanner.nextLine();
            newGame.moveStringConverter(input);
        }

    }

}
