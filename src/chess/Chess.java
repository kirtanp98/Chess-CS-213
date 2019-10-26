package chess;

import Controller.Game;
import java.util.Scanner;

/**
 * @author Zain Ul-Abdin
 * @author Kirtan Patel
 */
public class Chess {

    public static void main(String[] args) {
        Game newGame = new Game();

        Scanner scanner = new Scanner(System.in);
        System.out.println(newGame);

        while(!newGame.gameFinished) {
            String input = scanner.nextLine();
            newGame.moveStringConverter(input);
        }

    }

}
