import javax.xml.soap.Text;
import java.util.*;

public class gomoku {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String ANSI_BOLD = "\u001B[1m";
    public static int[] stoneHistory = new int[225];
    public static short currentStep = 0;
    public static int chessBoard[][] = new int[15][15];

    public static void main(String[] args) {
        boolean sPlayer = false;
        if (args.length > 0) {
            if (args[0] != "1")
                System.out.println(ANSI_CYAN + "A Gomoku Game by Nicholas@UIUC.\n\n" +
                        "USAGE: gomoku [OPTIONS] ...\n" +
                        "To activate single player mode, use argument 1.\n" +
                        "For bugs report please touch " + ANSI_YELLOW + ANSI_BOLD +
                        "chenkai3@illinois.edu" + ANSI_RESET + ANSI_CYAN +
                        ".\n======================================================\n\n" + ANSI_RESET);
            else sPlayer = true;
        }
        //chessBoard definition: 0 unset, 1 player1, 2 player2/cpuplayer
        for (int iBoard[] : chessBoard)
            for (int jBoard : iBoard) jBoard = 0;

        initBoard();
        for (currentStep = 0; currentStep <= 225; currentStep++) {
            if (currentStep % 2 == 0) { //Player 1
                cursorMoveTo(1, 31);
                System.out.print(ANSI_GREEN + "Player 1's turn" + ANSI_RESET);
                cursorMoveTo(2, 33);
                while (!proceedPlayerCmd(1, TextIO.getln())) notifyInvalidCmd();
                cursorMoveTo(1, 31);
                System.out.print("                                   ");
                cursorMoveTo(2, 33);
            } else {
                if (!sPlayer) { //Multi Player: Player 2
                    cursorMoveTo(1, 31);
                    System.out.print(ANSI_GREEN + "Player 2's turn" + ANSI_RESET);
                    cursorMoveTo(2, 33);
                    while (!proceedPlayerCmd(2, TextIO.getln())) notifyInvalidCmd();
                    cursorMoveTo(1, 31);
                    System.out.print("                                   ");
                    cursorMoveTo(2, 33);
                } else {//Single Player: CPU Player
                    proceedCPUCmd();
                }
            }
        }
    }

    public static void notifyInvalidCmd() {
        cursorMoveTo(1, 31);
        System.out.print(ANSI_RED + ANSI_BOLD + "Coordinate invalid, please retry." + ANSI_RESET);
        cursorMoveTo(1, 33);
        System.out.print(":                         ");
        cursorMoveTo(2, 33);
    }

    public static boolean proceedPlayerCmd(final int Player, final String Coordinate) {
        int x, y, tmp = coordinateParser(Coordinate);
        if (tmp == 0) {
            return false;
        }
        x = tmp % 100;
        y = tmp / 100;
        if (chessBoard[x][y] != 0) {
            return false;
        }
        chessBoard[x][y] = Player;
        putStoneOnBoard(Player, x, y);
        stoneHistory[currentStep] = tmp;
        return true;
    }

    public static boolean proceedCPUCmd() {
        return true;
    }


    public static void initBoard() {
        System.out.print("\033[H\033[2J");
        System.out.print("" +
                "1   2   3   4   5   6   7   8   9   a   b   c   d   e   f\n" +
                "┌───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┐A\n" +
                "│   │   │   │   │   │   │   │   │   │   │   │   │   │   │\n" +
                "├───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┤B\n" +
                "│   │   │   │   │   │   │   │   │   │   │   │   │   │   │\n" +
                "├───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┤C\n" +
                "│   │   │   │   │   │   │   │   │   │   │   │   │   │   │\n" +
                "├───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┤D\n" +
                "│   │   │   │   │   │   │   │   │   │   │   │   │   │   │\n" +
                "├───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┤E\n" +
                "│   │   │   │   │   │   │   │   │   │   │   │   │   │   │\n" +
                "├───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┤F\n" +
                "│   │   │   │   │   │   │   │   │   │   │   │   │   │   │\n" +
                "├───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┤G\n" +
                "│   │   │   │   │   │   │   │   │   │   │   │   │   │   │\n" +
                "├───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┤H\n" +
                "│   │   │   │   │   │   │   │   │   │   │   │   │   │   │\n" +
                "├───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┤I\n" +
                "│   │   │   │   │   │   │   │   │   │   │   │   │   │   │\n" +
                "├───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┤J\n" +
                "│   │   │   │   │   │   │   │   │   │   │   │   │   │   │\n" +
                "├───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┤K\n" +
                "│   │   │   │   │   │   │   │   │   │   │   │   │   │   │\n" +
                "├───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┤L\n" +
                "│   │   │   │   │   │   │   │   │   │   │   │   │   │   │\n" +
                "├───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┤M\n" +
                "│   │   │   │   │   │   │   │   │   │   │   │   │   │   │\n" +
                "├───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┤N\n" +
                "│   │   │   │   │   │   │   │   │   │   │   │   │   │   │\n" +
                "└───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┘O\n\n" +
                "Enter your decision coordinate in format like \"7J\"\n:");
    }

    public static boolean putStoneOnBoard(final int Player, final int x, final int y) {
        int termX, termY;
        termX = 4 * (x - 1);//0,4,8,12...
        termY = y * 2;//1,3,5,7...
        String strStone = "";
        switch (Player) {
            case 1:
                strStone = "● ";
                break;
            case 2:
                strStone = "○ ";
                break;
        }
        cursorMoveTo(termX, termY);
        System.out.print(strStone);
        cursorMoveTo(1, 33);
        System.out.print(":                  ");
        cursorMoveTo(2, 33);
        return true;
    }

    public static int coordinateParser(final String Coordinate) {
        if (Coordinate.trim().length() != 2) {
            return 0;
        }
        int rVal;
        int x = (int) Coordinate.toLowerCase().trim().charAt(0);
        if (x > 48 && x < 58) {
            rVal = x - 48;
        } else if (x > 96 && x < 103) {
            rVal = x - 87;
        } else {
            return 0;
        }
        int y = (int) Coordinate.toLowerCase().trim().charAt(1);
        if (y > 96 && y < 112) {
            rVal += (y - 96) * 100;
        } else {
            return 0;
        }
        return rVal;
    }

    public static void cursorMoveTo(final int x, final int y) {
        char escCode = 0x1B;
        System.out.print(String.format("%c[%d;%df", escCode, y, x));
    }
}
