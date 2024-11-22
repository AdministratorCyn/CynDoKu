import java.util.*;
public class Sudoku {
    char[][] charGrid;
    char[][] candGrid;
    final char[][] one = {{' ', '┐', ' '}, {' ', '|', ' '}, {' ', '┴', ' '}};
    final char[][] two = {{'╭', '-', '╮'}, {' ', '/', ' '}, {'/', '_', '_'}};
    final char[][] three = {{'←', '─', '>'}, {' ', '<', ' '}, {'_', '‿', '>'}};
    final char[][] four = {{'|', ' ', '|'}, {'└', '─', '┤'}, {' ', ' ', '|'}};
    final char[][] five = {{'┌', '─', '─'}, {'└', '─', '┐'}, {'─', '─', '┘'}};
    final char[][] six = {{'┌', '─', '─'}, {'├', '─', '┐'}, {'└', '─', '┘'}};
    final char[][] seven = {{'─', '─', '>'}, {'─', '⌿', '─'}, {'╱', ' ', ' '}};
    final char[][] eight = {{'┌', '─', '┐'}, {'├', '─', '┤'}, {'└', '─', '┘'}};
    final char[][] nine = {{'┌', '─', '┐'}, {'└', '─', '┤'}, {'─', '─', '┘'}};
    public static int iterations = 0;
    public static int ns = 0;
    public static int hs = 0;
    public static int np = 0;
    public static int hp = 0;
    public static int lc1 = 0;
    public static int nt = 0;
    public static int ht = 0;
    public static int nq = 0;
    public static int nsAvg = 0;
    public static int hsAvg = 0;
    public static int npAvg = 0;
    public static int hpAvg = 0;
    public static int lc1Avg = 0;
    public static int ntAvg = 0;
    public static int htAvg = 0;
    public static int nqAvg = 0;
    //make givens a diff color maybe?
    public Sudoku(char[][] charGrid, char[][] candGrid) {
        this.charGrid = charGrid;
        this.candGrid = candGrid;
    }
    public Sudoku(char[][] charGrid) {
        this.charGrid = charGrid;
        char[][] chars = new char[9][81];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 81; j++) {
                chars[i][j] = Character.forDigit(((j % 9) + 1), 10);
            }
        }
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) { //four f loops, row col value, something wrong with cfd(i)
                if (charGrid[i][j] == '0') {//this is wrong idk how to fix
                    for (int k = 0; k < 9; k++) { //6 r4c7 i = 5 j = 3 k = 6
                        for (int l = 0; l < 9; l++) {
                            if (charGrid[i][l] == Character.forDigit((k + 1), 10)) {
                                chars[i][j * 9 + k] = '0';
                            }
                            else if (charGrid[l][j] == Character.forDigit((k + 1), 10)) {
                                chars[i][j * 9 + k] = '0';
                            }
                        }
                    }
                    for (int k = 0; k < 9; k++) {
                        for (int l = 0; l < 9; l++) {
                            if (charGrid[i / 3 * 3 + l / 3][j / 3 * 3 + l % 3] == Character.forDigit((k + 1), 10)) {
                                chars[i][j * 9 + k] = '0';
                            }
                        }
                    }
                }
                else {
                    for (int k = 0; k < 9; k++) {
                        chars[i][j * 9 + k] = '0';
                        if (charGrid[i][j] == Character.forDigit(k + 1, 10)) {
                            chars[i][j * 9 + k] = Character.forDigit(k + 1, 10);
                        }
                    }
                }
            }
        }
        this.candGrid = chars;
    }
    /*
    ╔ ═ ═ ═ ═ ═ ╤ ═ ═ ═ ═ ═ ╤ ═ ═ ═ ═ ═ ╦ ═ ═ ═ ═ ═ ╤ ═ ═ ═ ═ ═ ╤ ═ ═ ═ ═ ═ ╦ ═ ═ ═ ═ ═ ╤ ═ ═ ═ ═ ═ ╤ ═ ═ ═ ═ ═ ╦
    ║     ┐     | [0, 0, 0] |
    ║     |     | [0, 0, 0] |
    ║     ┴     | [0, 0, 0] |
    ╟ - - - - - +
    ║    ╭-╮    |
    ║     /     |
    ║    /__    |
    ╟ - - - - - +
    ║    ←─>    |
    ║     <     |
    ║    _‿>    |
    ╔ ═ ═ ═ ═ ═ ╤ ═ ═ ═ ═ ═ ╤ ═ ═ ═ ═ ═ ╦
    ║    | |    |
    ║    └─┤    |
    ║      |    |
    ╟ - - - - - +
    ║    ┌──    |
    ║    └─┐    |
    ║    ──┘    |
    ╟ - - - - - +
    ║    ┌──    |
    ║    ├─┐    |
    ║    └─┘    |
    ╔ ═ ═ ═ ═ ═ ╤ ═ ═ ═ ═ ═ ╤ ═ ═ ═ ═ ═ ╦
    ║    ──>    |
    ║    ─⌿─    |
    ║    ╱      |
    ╟ - - - - - +
    ║    ┌─┐    |
    ║    ├─┤    |
    ║    └─┘    |
    ╟ - - - - - +
    ║    ┌─┐    |
    ║    └─┤    |
    ║    ──┘    |
    -----------------------------
     */
    //make sure not to break from loop, do in order
    //maybe add \n?
    public String candidateSudoku() {
        String output = "";//try to remove spaces before and after []
        for (int i = 0; i < 37; i++) {
            if (i == 0) {
                for (int j = 0; j < 91; j++) {
                    if (j == 0) {
                        output = output.concat("╔");
                    }
                    else if (j == 30 || j == 60) {
                        output = output.concat("╦");
                    }
                    else if (j == 90) {
                        output = output.concat("╗\n");
                    }
                    else if (j % 10 == 0) {
                        output = output.concat("╤");
                    }
                    else if (j % 2 == 1) {
                        output = output.concat("═");
                    }
                    else {
                        output = output.concat(" ");
                    }
                }
            }
            else if (i == 12 || i == 24) {
                for (int j = 0; j < 91; j++) {
                    if (j == 0) {
                        output = output.concat("╠");
                    }
                    else if (j == 30 || j == 60) {
                        output = output.concat("╬");
                    }
                    else if (j == 90) {
                        output = output.concat("╣\n");
                    }
                    else if (j % 10 == 0) {
                        output = output.concat("╪");
                    }
                    else if (j % 2 == 1) {
                        output = output.concat("═");
                    }
                    else {
                        output = output.concat(" ");
                    }
                }
            }
            else if (i == 36) {
                for (int j = 0; j < 91; j++) {
                    if (j == 0) {
                        output = output.concat("╚");
                    }
                    else if (j == 30 || j == 60) {
                        output = output.concat("╩");
                    }
                    else if (j == 90) {
                        output = output.concat("╝\n");
                    }
                    else if (j % 10 == 0) {
                        output = output.concat("╧");
                    }
                    else if (j % 2 == 1) {
                        output = output.concat("═");
                    }
                    else {
                        output = output.concat(" ");
                    }
                }
            }
            else if (i % 4 == 0) {
                for (int j = 0; j < 91; j++) {
                    if (j == 0) {
                        output = output.concat("╟");
                    }
                    else if (j == 90) {
                        output = output.concat("╢\n");
                    }
                    else if (j % 30 == 0) {
                        output = output.concat("╫");
                    }
                    else if (j % 10 == 0) {
                        output = output.concat("+");
                    }
                    else if (j % 2 == 1) {
                        output = output.concat("-");
                    }
                    else {
                        output = output.concat(" ");
                    }
                }
            }
            else {
                for (int j = 0; j < 91; j++) {
                    //code cands first then figure out unicode s
                    if (j == 0) {
                        output = output.concat("║");
                    }
                    else if (j == 90) {
                        output = output.concat("║\n");
                    }
                    else if (j % 30 == 0) {
                        output = output.concat("║");
                    }
                    else if ((j % 10 == 4 || j % 10 == 5 || j % 10 == 6) && this.charGrid[i / 4][j / 10] == '1') {
                        output = output.concat(String.valueOf(one[(i % 4) - 1][j % 10 - 4]));
                    }
                    else if ((j % 10 == 4 || j % 10 == 5 || j % 10 == 6) && this.charGrid[i / 4][j / 10] == '2') {
                        output = output.concat(String.valueOf(two[(i % 4) - 1][j % 10 - 4]));
                    }
                    else if ((j % 10 == 4 || j % 10 == 5 || j % 10 == 6) && this.charGrid[i / 4][j / 10] == '3') {
                        output = output.concat(String.valueOf(three[(i % 4) - 1][j % 10 - 4]));
                    }
                    else if ((j % 10 == 4 || j % 10 == 5 || j % 10 == 6) && this.charGrid[i / 4][j / 10] == '4') {
                        output = output.concat(String.valueOf(four[(i % 4) - 1][j % 10 - 4]));
                    }
                    else if ((j % 10 == 4 || j % 10 == 5 || j % 10 == 6) && this.charGrid[i / 4][j / 10] == '5') {
                        output = output.concat(String.valueOf(five[(i % 4) - 1][j % 10 - 4]));
                    }
                    else if ((j % 10 == 4 || j % 10 == 5 || j % 10 == 6) && this.charGrid[i / 4][j / 10] == '6') {
                        output = output.concat(String.valueOf(six[(i % 4) - 1][j % 10 - 4]));
                    }
                    else if ((j % 10 == 4 || j % 10 == 5 || j % 10 == 6) && this.charGrid[i / 4][j / 10] == '7') {
                        output = output.concat(String.valueOf(seven[(i % 4) - 1][j % 10 - 4]));
                    }
                    else if ((j % 10 == 4 || j % 10 == 5 || j % 10 == 6) && this.charGrid[i / 4][j / 10] == '8') {
                        output = output.concat(String.valueOf(eight[(i % 4) - 1][j % 10 - 4]));
                    }
                    else if ((j % 10 == 4 || j % 10 == 5 || j % 10 == 6) && this.charGrid[i / 4][j / 10] == '9') {
                        output = output.concat(String.valueOf(nine[(i % 4) - 1][j % 10 - 4]));
                    }
                    else if (j % 10 == 0) {
                        output = output.concat("|");
                    }
          /*else if (j % 10 == 1) {
              output = output.concat("[");
          }
          else if (j % 10 == 9) {
              output = output.concat("]");
          }
          else if (j % 10 == 3 || j % 10 == 6) {
              output = output.concat(",");
          }*/
                    else if ((j % 10 == 2 || j % 10 == 5 || j % 10 == 8) && this.charGrid[i / 4][j / 10] == '0' && (this.candGrid[i / 4][((j % 10) / 3) + ((i - 1) % 4) * 3 + (j / 10) * 9]) != '0') { //i% statement, extra skipped col? 3 98 1 73 [0, 0+9+72
                        output = output.concat(String.valueOf(this.candGrid[i / 4][((j % 10) / 3) + ((i - 1) % 4) * 3 + (j / 10) * 9]));
                    }
                    else {
                        output = output.concat(" ");
                    }
                    //System.out.println((i / 4) + " " + (((j % 12 / 3) - 1 + ((i - 1) % 4) * 3 + (j / 12) * 9)));
                }
            }
        }
        return output;
    }
    public String fancyCandidateSudoku() {
        String output = "";//try to remove spaces before and after []
        for (int i = 0; i < 37; i++) {
            if (i == 0) {
                for (int j = 0; j < 91; j++) {
                    if (j == 0) {
                        output = output.concat("╔");
                    }
                    else if (j == 30 || j == 60) {
                        output = output.concat("╦");
                    }
                    else if (j == 90) {
                        output = output.concat("╗\n");
                    }
                    else if (j % 10 == 0) {
                        output = output.concat("╤");
                    }
                    else if (j % 2 == 1) {
                        output = output.concat("═");
                    }
                    else {
                        output = output.concat(" ");
                    }
                }
            }
            else if (i == 12 || i == 24) {
                for (int j = 0; j < 91; j++) {
                    if (j == 0) {
                        output = output.concat("╠");
                    }
                    else if (j == 30 || j == 60) {
                        output = output.concat("╬");
                    }
                    else if (j == 90) {
                        output = output.concat("╣\n");
                    }
                    else if (j % 10 == 0) {
                        output = output.concat("╪");
                    }
                    else if (j % 2 == 1) {
                        output = output.concat("═");
                    }
                    else {
                        output = output.concat(" ");
                    }
                }
            }
            else if (i == 36) {
                for (int j = 0; j < 91; j++) {
                    if (j == 0) {
                        output = output.concat("╚");
                    }
                    else if (j == 30 || j == 60) {
                        output = output.concat("╩");
                    }
                    else if (j == 90) {
                        output = output.concat("╝\n");
                    }
                    else if (j % 10 == 0) {
                        output = output.concat("╧");
                    }
                    else if (j % 2 == 1) {
                        output = output.concat("═");
                    }
                    else {
                        output = output.concat(" ");
                    }
                }
            }
            else if (i % 4 == 0) {
                for (int j = 0; j < 91; j++) {
                    if (j == 0) {
                        output = output.concat("╟");
                    }
                    else if (j == 90) {
                        output = output.concat("╢\n");
                    }
                    else if (j % 30 == 0) {
                        output = output.concat("╫");
                    }
                    else if (j % 10 == 0) {
                        output = output.concat("+");
                    }
                    else if (j % 2 == 1) {
                        output = output.concat("-");
                    }
                    else {
                        output = output.concat(" ");
                    }
                }
            }
            else {
                for (int j = 0; j < 91; j++) {
                    //code cands first then figure out unicode s
                    if (j == 0) {
                        output = output.concat("║");
                    }
                    else if (j == 90) {
                        output = output.concat("║\n");
                    }
                    else if (j % 30 == 0) {
                        output = output.concat("║");
                    }
                    else if ((j % 10 == 4 || j % 10 == 5 || j % 10 == 6) && this.charGrid[i / 4][j / 10] == '1') {
                        output = output.concat(String.valueOf(one[(i % 4) - 1][j % 10 - 4]));
                    }
                    else if ((j % 10 == 4 || j % 10 == 5 || j % 10 == 6) && this.charGrid[i / 4][j / 10] == '2') {
                        output = output.concat(String.valueOf(two[(i % 4) - 1][j % 10 - 4]));
                    }
                    else if ((j % 10 == 4 || j % 10 == 5 || j % 10 == 6) && this.charGrid[i / 4][j / 10] == '3') {
                        output = output.concat(String.valueOf(three[(i % 4) - 1][j % 10 - 4]));
                    }
                    else if ((j % 10 == 4 || j % 10 == 5 || j % 10 == 6) && this.charGrid[i / 4][j / 10] == '4') {
                        output = output.concat(String.valueOf(four[(i % 4) - 1][j % 10 - 4]));
                    }
                    else if ((j % 10 == 4 || j % 10 == 5 || j % 10 == 6) && this.charGrid[i / 4][j / 10] == '5') {
                        output = output.concat(String.valueOf(five[(i % 4) - 1][j % 10 - 4]));
                    }
                    else if ((j % 10 == 4 || j % 10 == 5 || j % 10 == 6) && this.charGrid[i / 4][j / 10] == '6') {
                        output = output.concat(String.valueOf(six[(i % 4) - 1][j % 10 - 4]));
                    }
                    else if ((j % 10 == 4 || j % 10 == 5 || j % 10 == 6) && this.charGrid[i / 4][j / 10] == '7') {
                        output = output.concat(String.valueOf(seven[(i % 4) - 1][j % 10 - 4]));
                    }
                    else if ((j % 10 == 4 || j % 10 == 5 || j % 10 == 6) && this.charGrid[i / 4][j / 10] == '8') {
                        output = output.concat(String.valueOf(eight[(i % 4) - 1][j % 10 - 4]));
                    }
                    else if ((j % 10 == 4 || j % 10 == 5 || j % 10 == 6) && this.charGrid[i / 4][j / 10] == '9') {
                        output = output.concat(String.valueOf(nine[(i % 4) - 1][j % 10 - 4]));
                    }
                    else if (j % 10 == 0) {
                        output = output.concat("|");
                    }
                    else if (j % 10 == 1) {
                        output = output.concat("[");
                    }
                    else if (j % 10 == 9) {
                        output = output.concat("]");
                    }
                    else if (j % 10 == 3 || j % 10 == 6) {
                        output = output.concat(",");
                    }
                    else if ((j % 10 == 2 || j % 10 == 5 || j % 10 == 8) && this.charGrid[i / 4][j / 10] == '0' && (this.candGrid[i / 4][((j % 10) / 3) + ((i - 1) % 4) * 3 + (j / 10) * 9]) != '0') { //i% statement, extra skipped col? 3 98 1 73 [0, 0+9+72
                        output = output.concat(String.valueOf(this.candGrid[i / 4][((j % 10) / 3) + ((i - 1) % 4) * 3 + (j / 10) * 9]));
                    }
                    else {
                        output = output.concat(" ");
                    }
                    //System.out.println((i / 4) + " " + (((j % 12 / 3) - 1 + ((i - 1) % 4) * 3 + (j / 12) * 9)));
                }
            }
        }
        return output;
    }
    public char[][] retrieveCandSudoku(String str) {
        char[][] output = new char[9][81];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 81; j++) {
                output[i][j] = '0';
            }
        }
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (this.charGrid[i][j] != '0') {
                    output[i][j * 9 + (Character.getNumericValue(this.charGrid[i][j])) - 1] = this.charGrid[i][j];
                }
            }
        }
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 81; j++) {
                if (Character.isDigit(str.charAt(92 * (i * 4 + (j % 9) / 3 + 1) + ((j / 9) * 10 + 2 + (j % 3) * 3)))) {
                    output[i][j] = str.charAt(92 * (i * 4 + (j % 9) / 3 + 1) + ((j / 9) * 10 + 2 + (j % 3) * 3));
                }
            }
        }
        return output;
    }
    public char[][] autoCand(char[][] candidates) {
        char[][] newCands = new char[9][81];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 81; j++) {
                newCands[i][j] = candidates[i][j];
            }
        }
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) { //four f loops, row col value, something wrong with cfd(i)
                if (this.charGrid[i][j] == '0') {//this is wrong idk how to fix
                    for (int k = 0; k < 9; k++) { //6 r4c7 i = 5 j = 3 k = 6
                        for (int l = 0; l < 9; l++) {
                            if (this.charGrid[i][l] == Character.forDigit((k + 1), 10)) {
                                newCands[i][j * 9 + k] = '0';
                            }
                            else if (this.charGrid[l][j] == Character.forDigit((k + 1), 10)) {
                                newCands[i][j * 9 + k] = '0';
                            }
                        }
                    }
                    for (int k = 0; k < 9; k++) {
                        for (int l = 0; l < 9; l++) {
                            if (this.charGrid[i / 3 * 3 + l / 3][j / 3 * 3 + l % 3] == Character.forDigit((k + 1), 10)) {
                                newCands[i][j * 9 + k] = '0';
                            }
                        }
                    }
                }
                else {
                    for (int k = 0; k < 9; k++) {
                        newCands[i][j * 9 + k] = '0';
                        if (this.charGrid[i][j] == Character.forDigit(k + 1, 10)) {
                            newCands[i][j * 9 + k] = Character.forDigit(k + 1, 10);
                        }
                    }
                }
            }
        }
        return newCands;
    }
    //prints fancy sudoku to ta1
    public String printSudoku() {
        //try string method concat
        char[][] output = new char[11][21];
        //for loop to input rows 3 + 7
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 21; j++) {
                if (i == 0) {
                    if (j % 2 == 1) {
                        output[3][j] = ' ';
                    } else if (j == 6 || j == 14) {
                        output[3][j] = '+';
                    } else {
                        output[3][j] = '-';
                    }
                } else {
                    if (j % 2 == 1) {
                        output[7][j] = ' ';
                    } else if (j == 6 || j == 14) {
                        output[7][j] = '+';
                    } else {
                        output[7][j] = '-';
                    }
                }
            }
        }
        int skipsRow = 0;
        int skipsCol = 0;
        for (int i = 0; i < 11; i++) {
            if (i == 3 || i == 7) {
                skipsRow++;
            } else {
                for (int j = 0; j < 21; j++) {
                    if ((j == 6 || j == 14)) {
                        skipsCol++;
                        output[i][j] = '|';
                    } else if (j % 2 == 1) {
                        skipsCol++;
                        output[i][j] = ' ';
                    } else {
                        output[i][j] = this.charGrid[i - skipsRow][j - skipsCol];
                    }
                }
                skipsCol = 0;
            }
        }
        String output1 = "";
        //remove this part by adding to bigger bit!!!
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 21; j++) {
                output1 = output1.concat(String.valueOf(output[i][j]));
            }
            output1 = output1.concat("\n"); //remember this
        }
        return output1;
    }
    public char[][] retrieveSudoku(String string) { //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!@#
        char[][] output = new char[9][9];
        int skippedRows = 0;
        int skippedCols = 0;
        for (int i = 0; i < 11; i++) {
            if (i == 4 || i == 8) {
                skippedRows++;
            }
            for (int j = 0; j < 21; j++) {
                if (j % 2 == 0 && (j != 6 && j != 14)) {
                    output[i - skippedRows][j - skippedCols] = string.charAt(i * 22 + j);
                }
                else {
                    skippedCols++;
                }
            }
            skippedCols = 0;
        }
        return output;
    }
    public char[] rowCol(String rxcx) {
        char[] output = new char[2];
        int used = 0;
        for (int i = 0; i < 4; i++) {
            if (rxcx.charAt(i) != 'r' && rxcx.charAt(i) != 'c') {
                output[used] = rxcx.charAt(i);
                used++;
            }
        }
        return output;
    }
    //to input a cell
    public void fillCell(String input, char val) {
        char[] rc = this.rowCol(input);
        if (this.charGrid[rc[0] - '1'][rc[1] - '1'] == '0') {
            this.charGrid[rc[0] - '1'][rc[1] - '1'] = val;
            this.printSudoku();
        }
    }
    public char[] getCellCandidates(String input) {
        char[] coords = this.rowCol(input);
        char[] candidates = new char[]{'1', '2', '3', '4', '5', '6', '7', '8', '9'};
        int[] box = new int[2];
        box[0] = (Character.getNumericValue(coords[0]) - 1) / 3 * 3;
        box[1] = (Character.getNumericValue(coords[1]) - 1) / 3 * 3;
        if (this.charGrid[Character.getNumericValue(coords[0]) - 1][Character.getNumericValue(coords[1]) - 1] == '0') {
            for (int i = 1; i < 10; i++) {
                for (int j = 0; j < this.charGrid.length; j++) {
                    if (this.charGrid[Character.getNumericValue(coords[0]) - 1][j] == Character.forDigit(i, 10)) {
                        candidates[i - 1] = '0';
                        break;
                    }
                }
                for (int j = 0; j < this.charGrid.length; j++) {
                    if (this.charGrid[j][Character.getNumericValue(coords[1]) - 1] == Character.forDigit(i, 10)) {
                        candidates[i - 1] = '0';
                        break;
                    }
                }
                for (int j = 0; j < 3; j++) {
                    for (int k = 0; k < 3; k++) {
                        if (this.charGrid[box[0] + j][box[1] + k] == Character.forDigit(i, 10)) {
                            candidates[i - 1] = '0';
                            break;
                        }
                    }
                }
            }
            return candidates;
        } else {
            char[] candidate = new char[]{'0', '0', '0', '0', '0', '0', '0', '0', '0'};
            candidate[Character.getNumericValue(this.charGrid[Character.getNumericValue(coords[0]) - 1][Character.getNumericValue(coords[1]) - 1]) - 1] = this.charGrid[Character.getNumericValue(coords[0]) - 1][Character.getNumericValue(coords[1]) - 1];
            return candidate;
        }
    }
    public String solvePath() {
        String output = "";
        char[][] test = new char[9][9];
        char[][] test1 = new char[9][9];
        char[][] testChar = new char[9][81];
        char[][] testChar1 = new char[9][81];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                test[i][j] = this.charGrid[i][j];
                test1[i][j] = this.charGrid[i][j];
                for (int k = 0; k < 9; k++) {
                    testChar[i][j * 9 + k] = this.getCellCandidates("r" + (i + 1) + "c" + (j + 1))[k];
                    testChar1[i][j * 9 + k] = this.getCellCandidates("r" + (i + 1) + "c" + (j + 1))[k];
                }
            }
        }
        Sudoku testSudoku = new Sudoku(test, testChar);
        Sudoku testSudoku1 = new Sudoku(test1, testChar1);
        boolean different = true;
        int x = 0;
        outerloop:
        while (different) {
            testSudoku.doNakedSingles();
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if (testSudoku.charGrid[i][j] != testSudoku1.charGrid[i][j]) {
                        output = output.concat("Naked Single: r" + (i + 1) + "c" + (j + 1) + "=" + testSudoku.charGrid[i][j] + "\n");
                        break outerloop;
                    } else if (i == 8 && j == 8) {
                        different = false;
                    }
                }
            }
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    testSudoku1.charGrid[i][j] = testSudoku.charGrid[i][j];
                }
            }
        }
        different = true;
        outerloop:
        while (different) {
            testSudoku.doHiddenSingles();
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if (testSudoku.charGrid[i][j] != testSudoku1.charGrid[i][j]) {
                        output = output.concat("Hidden Single: r" + (i + 1) + "c" + (j + 1) + "=" + testSudoku.charGrid[i][j] + "\n");
                        break outerloop;
                    } else if (i == 8 && j == 8) {
                        different = false;
                    }
                }
            }
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    testSudoku1.charGrid[i][j] = testSudoku.charGrid[i][j];
                }
            }
        }
        different = true;
        outerloop:
        while (different) {
            testSudoku.doNakedPairs();
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 81; j++) {
                    if (testSudoku.candGrid[i][j] != testSudoku1.candGrid[i][j]) { //hodoku syntax Naked pair: 1,3 in r5c37 => r5c4<>1, r5c46<>3
                        output = output.concat("Naked Pair: r" + (i + 1) + "c" + ((j / 9) + 1) + "<>" + ((j % 9) + 1) + "\n");
                        break outerloop;
                    } else if (i == 8 && j == 80) {
                        different = false;
                    }
                }
            }
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 81; j++) {
                    testSudoku1.candGrid[i][j] = testSudoku.candGrid[i][j];
                }
            }
        }
        return output;
    }
    public String hint(int timesUsed) {
        String hint;
        if (this.findNakedSingles()) {
            if (timesUsed < 1) {
                hint = "Technique Naked Single: ";
                char[][] testGrid = new char[9][9];
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        testGrid[i][j] = this.charGrid[i][j];
                    }
                }
                Sudoku test = new Sudoku(testGrid);
                test.doNakedSingles();
                outerloop:
                if (this.findNakedSingles()) {
                    for (int i = 0; i < 9; i++) {
                        for (int j = 0; j < 9; j++) {
                            if (this.charGrid[i][j] != test.charGrid[i][j]) {
                                hint = hint.concat(String.valueOf(test.charGrid[i][j]));
                                break outerloop;
                            }
                        }
                    }
                }
            } else {
                hint = "Technique Naked Single: ";
                char[][] testGrid = new char[9][9];
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        testGrid[i][j] = this.charGrid[i][j];
                    }
                }
                Sudoku test = new Sudoku(testGrid);
                test.doNakedSingles();
                int x;
                int y;
                outerloop:
                if (this.findNakedSingles()) {
                    for (int i = 0; i < 9; i++) {
                        for (int j = 0; j < 9; j++) {
                            if (this.charGrid[i][j] != test.charGrid[i][j]) {
                                x = j;
                                y = i;
                                hint = hint.concat("t" + String.valueOf(y / 3 + 1) + "s" + String.valueOf(x  / 3 + 1)) + " ";
                                break outerloop;
                            }
                        }
                    }
                }
            }
        } else if (this.findHiddenSingles()) {
            if (timesUsed < 1) {
                hint = "Technique Hidden Single: ";
                char[][] testGrid = new char[9][9];
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        testGrid[i][j] = this.charGrid[i][j];
                    }
                }
                Sudoku test = new Sudoku(testGrid);
                test.doHiddenSingles();
                outerloop:
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        if (this.charGrid[i][j] != test.charGrid[i][j]) {
                            hint = hint.concat(String.valueOf(test.charGrid[i][j]));
                            break outerloop;
                        }
                    }
                }
            } else {
                hint = "Technique Hidden Single: ";
                char[][] testGrid = new char[9][9];
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        testGrid[i][j] = this.charGrid[i][j];
                    }
                }
                Sudoku test = new Sudoku(testGrid);
                test.doHiddenSingles();
                int x;
                int y;
                outerloop:
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        if (this.charGrid[i][j] != test.charGrid[i][j]) {
                            x = j;
                            y = i;
                            hint = hint.concat("t" + String.valueOf(y / 3 + 1) + "s" + String.valueOf(x / 3 + 1)) + " ";
                            break outerloop;
                        }
                    }
                }
            }
        } else {
            hint = "Technique unknown :P";
        }
        return hint;
    }
    //makes cells with one possibility the possibility
    public void doNakedSingles() { //benchmark @ ~350 from 10
        ns++;
        int i = 0;
        int count = 0;
        outerloop:
        while (i < 729) {
            iterations++;
            nsAvg++;
            if (i % 9 == 0 && this.charGrid[i / 81][(i % 81) / 9] != '0') {
                i += 8;
            }
            else if (this.candGrid[i / 81][i % 81] != '0') {
                count++;
            }
            if (count > 1) {
                count = 0;
                i = i / 9 * 9 + 8;
            }
            else if (i % 9 == 8 && count == 1) {
                count = 0;
                for (int j = 0; j < 9; j++) {
                    if (this.candGrid[i / 81][(i % 81) / 9 * 9 + j] != '0') {
                        this.charGrid[i / 81][(i % 81) / 9] = Character.forDigit((j + 1), 10);
                        break outerloop;
                    }
                }
            }
            i++;
        }
    }
    //finds if there is a cell with only one candidate
    public boolean findNakedSingles() {
        boolean found = false;
        char[][] testGrid = new char[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                testGrid[i][j] = this.charGrid[i][j];
            }
        }
        Sudoku test = new Sudoku(testGrid);
        test.doNakedSingles();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (testGrid[i][j] != this.charGrid[i][j] && this.charGrid[i][j] == '0') {
                    found = true;
                    break;
                }
            }
        }
        return found;
    }
    //if only one cell in a region (row, col, box) can be a candidate, fill it in
    public void doHiddenSingles() { //benchmark at ~350 from 9
        hs++;
        int q = 0;
        int count = 0; //ordering is very VERY!!!!!!!!!!!!!!!!#^@^@#&#@ , find optimal check
        while (q < 729) {//try to multid, should ne faster
            iterations++;
            hsAvg++;
            //scuffed
            if (this.charGrid[q / 81][q % 9] == Character.forDigit((q / 9) + 1, 10)) {
                //System.out.println("Blacklist " + (q / 81) + " " + (q % 9) + " / " + (q % 81) / 9 + " " + q + " | " + hsAvg);
                q = (q / 9 * 9 + 8);
            } else if (count > 1) {
                //System.out.println("Skip! " + (q / 81) + " " + (q % 9) + " / " + (q % 81) / 9 + " " + q + " | " + hsAvg);
                q = (q / 9 * 9 + 8);
                count = 0;
            } else {
                if (this.charGrid[q / 81][q % 9] == '0' && this.candGrid[q / 81][(q % 9) * 9 + (q % 81) / 9] != '0') { //sop EVERYTHING
                    //System.out.println("count inc " + (q / 81) + " " + (q % 9) + " / " + (q % 81) / 9 + " " + q + " | " + hsAvg);
                    count++;
                } else {
                    //System.out.println("x " + (q / 81) + " " + (q % 9) + " / " + (q % 81) / 9 + " " + q + " | " + hsAvg);
                }
                if (q % 9 == 8 && count == 1) {
                    //System.out.println("Found! " + (q / 81) + " " + (q % 9) + " / " + (q % 81) / 9 + " " + q + " | " + hsAvg);
                    count = 0;
                    for (int i = 0; i < 9; i++) {
                        if (this.candGrid[q / 81][i * 9 + (q % 81) / 9] != '0') {
                            this.charGrid[q / 81][i] = Character.forDigit(((q % 81) / 9 + 1), 10);
                            return;
                        }
                    }
                }
            }
            q++;
        }
        q = 0;
        while (q < 729) {
            iterations++;
            hsAvg++;
            //scuffed
            if (this.charGrid[q % 9][q / 81] == Character.forDigit(((q % 81) / 9) + 1, 10)) {
                //System.out.println("Blacklist " + (q / 81) + " " + (q % 9) + " / " + (q % 81) / 9 + " " + q + " | " + hsAvg);
                q = (q / 9 * 9 + 8);
            } else if (count > 1) {
                //System.out.println("Skip! " + (q / 81) + " " + (q % 9) + " / " + (q % 81) / 9 + " " + q + " | " + hsAvg);
                q = (q / 9 * 9 + 8);
                count = 0;
            } else {
                if (this.charGrid[q % 9][q / 81] == '0' && this.candGrid[q % 9][(q % 81) / 9 + q / 81 * 9] != '0') { //sop EVERYTHING
                    //System.out.println("count inc " + (q / 81) + " " + (q % 9) + " / " + (q % 81) / 9 + " " + q + " | " + hsAvg);
                    count++;
                }
                if (q % 9 == 8 && count == 1) {
                    //System.out.println("Found! " + (q / 81) + " " + (q % 9) + " / " + (q % 81) / 9 + " " + q + " | " + hsAvg);
                    count = 0;
                    for (int i = 0; i < 9; i++) {
                        if (this.candGrid[i][(q % 81) / 9 + q / 81 * 9] != '0') {
                            this.charGrid[i][q / 81] = Character.forDigit(((q % 81) / 9 + 1), 10);
                            return;
                        }
                    }
                }
            }
            q++;
        }
        q = 0;
        while (q < 729) { //box
            iterations++;
            hsAvg++;
            //scuffed
            if (this.charGrid[(q / 243) * 3 + ((q % 9) / 3)][(q % 243) / 81 * 3 + (q % 3)] == Character.forDigit(((q % 81) / 9) + 1, 10)) {
                //System.out.println("Blacklist " + (q / 81) + " " + (q % 9) + " / " + (q % 81) / 9 + " " + q + " | " + hsAvg);
                q = (q / 9 * 9 + 8);
            } else if (count > 1) {
                //System.out.println("Skip! " + (q / 81) + " " + (q % 9) + " / " + (q % 81) / 9 + " " + q + " | " + hsAvg);
                q = (q / 9 * 9 + 8);
                count = 0;
            } else {
                if (this.charGrid[(q / 243) * 3 + ((q % 9) / 3)][(q % 243) / 81 * 3 + (q % 3)] == '0' && this.candGrid[(q / 243) * 3 + ((q % 9) / 3)][(q % 81) / 9 + (q % 243) / 81 * 27 + (q % 3) * 9] != '0') { //sop EVERYTHING
                    //System.out.println("count inc " + (q / 81) + " " + (q % 9) + " / " + (q % 81) / 9 + " " + q + " | " + hsAvg);
                    count++;
                }
                if (q % 9 == 8 && count == 1) {
                    //System.out.println("Found! " + (q / 81) + " " + (q % 9) + " / " + (q % 81) / 9 + " " + q + " | " + hsAvg);
                    count = 0;
                    for (int i = 0; i < 9; i++) {
                        if (this.candGrid[(q / 243) * 3 + (i / 3)][(q % 81) / 9 + (q % 243) / 81 * 27 + (i % 3) * 9] != '0') {
                            this.charGrid[(q / 243) * 3 + (i / 3)][(q % 243) / 81 * 3 + (i % 3)] = Character.forDigit(((q % 81) / 9 + 1), 10);
                            return;
                        }
                    }
                }
            }
            q++;
        }
    }
    public boolean findHiddenSingles() {
        char[][] test = new char[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                test[i][j] = this.charGrid[i][j];
            }
        }
        Sudoku testSudoku = new Sudoku(test);
        testSudoku.doHiddenSingles();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (testSudoku.charGrid[i][j] != this.charGrid[i][j]) {
                    return true;
                }
            }
        }
        return false;
    }
    public void doNakedPairs() { //bench @ 50 its,
        /*
         * finds two sets of two
         * removes all included candidates in region(s)
         *
         */
        np++;
 /*biv skips{ // -------------------- add vars before, write individually, also touch g2
     header (set vars){
         if (sc > 5) {
             q += 81;
         }
         else
         scB { take from notes || while
             route to set div
         }
     }
     skips {
         if sc = 5 and np (and no elim), second np of not np1 and sc{ (set div)








         }








         add each dir sc? 6 r1 + 2 c2 {








         }








         region ignore { (if sc > 7 in a region, skip region in bivF and g2 || while


         }


         ceB { if ca > 2 add to ceB


         }
                                 order them


         caB { try writing this omnidir || while
             for (int i = 0; i < 9; i ++) { //put at header
                 caB[0][0-8]; hor
                 caB[1][0-8]; vert
                 caB[2][0-8]; box
             }


         }


         dirBV { (if sc == 7 and bivalue, same bivalue where sc and bv1 isnt) use !sc instead of another 9


         }
     }
 }


    g1 (biv){


     cands[0] {
     }


     dirB {


     }


     cB {


     }
 }


 g2 {
     for u 9 {
         if (dirB[0] && not same) {
             while (scb) {
             }
             while (cb) {
             }
             if () {
             c++;
             }
         }
 if youve already checked a biv, can add to scB bc omnid
 try iterating in front of q % 81 / 9
 scb


 cb


 order
 1. biv 100%
 2. sam cands


 h {
 }
 v {
 }
 b {
 }
       if you find a non elim double bivalue and sc + 2 > 5 !!!REGIONAL!!!
         would be connected to g2 on fail exit
 }
 */
        //vars
        int q = 0;
        int count = 0;
        int count2 = 0;
        int count3 = 0;
        char[] cands = {'x', 'x'};
        boolean flag = false;
        boolean[] scB = {false, false, false, false, false, false, false, false, false}; // more eff to do hor like this, vert 9x1, box 9x9


        boolean[][][] caBD = {{{false, false, false, false, false, false, false, false, false}, {false, false, false, false, false, false, false, false, false}, {false, false, false, false, false, false, false, false, false}, {false, false, false, false, false, false, false, false, false}, {false, false, false, false, false, false, false, false, false}, {false, false, false, false, false, false, false, false, false}, {false, false, false, false, false, false, false, false, false}, {false, false, false, false, false, false, false, false, false}, {false, false, false, false, false, false, false, false, false}}
                , {{false, false, false, false, false, false, false, false, false}, {false, false, false, false, false, false, false, false, false}, {false, false, false, false, false, false, false, false, false}, {false, false, false, false, false, false, false, false, false}, {false, false, false, false, false, false, false, false, false}, {false, false, false, false, false, false, false, false, false}, {false, false, false, false, false, false, false, false, false}, {false, false, false, false, false, false, false, false, false}, {false, false, false, false, false, false, false, false, false}}
                , {{false, false, false, false, false, false, false, false, false}, {false, false, false, false, false, false, false, false, false}, {false, false, false, false, false, false, false, false, false}, {false, false, false, false, false, false, false, false, false}, {false, false, false, false, false, false, false, false, false}, {false, false, false, false, false, false, false, false, false}, {false, false, false, false, false, false, false, false, false}, {false, false, false, false, false, false, false, false, false}, {false, false, false, false, false, false, false, false, false}}};


        //dirb


        boolean[][] dirB = {{false, false, false, false, false, false, false, false, false}, {false, false, false, false, false, false, false, false, false}, {false, false, false, false, false, false, false, false, false}};
        boolean[][] dirS = {{false, false, false, false, false, false, false, false, false}, {false, false, false, false, false, false, false, false, false}, {false, false, false, false, false, false, false, false, false}};


        //dirB filling, routes to dirB skip
        //6 == directional imposs, 8 == full imposs
        //dir imposs should route to g2 stopping a dir being checked
        for (int i = 0; i < 9; i++) {//hor
            for (int j = 0; j < 9; j++) {
                if (this.charGrid[i][j] != '0') {
                    count++;
                    caBD[0][i][Character.getNumericValue(this.charGrid[i][j]) - 1] = true;
                    caBD[1][j][Character.getNumericValue(this.charGrid[i][j]) - 1] = true;
                    caBD[2][i / 3 * 3 + j / 3][Character.getNumericValue(this.charGrid[i][j]) - 1] = true;
                }
                if (count == 8) {
                    dirB[0][i] = true;
                    break;
                }
                if (count > 5) {
                    dirS[0][i] = true;
                }
            }
            count = 0;
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.println(Arrays.toString(caBD[i][j]));
            }
            System.out.println("---------------");
        }
        System.out.println(Arrays.toString(dirB[0]) + " / " + Arrays.toString(dirS[0]));
        for (int i = 0; i < 9; i++) {//vert
            for (int j = 0; j < 9; j++) {
                if (this.charGrid[j][i] != '0') {
                    count++;
                }
                if (count == 8) {
                    dirB[1][i] = true;
                    break;
                }
                if (count > 5) {
                    dirS[1][i] = true;
                }
            }
            count = 0;
        }
        System.out.println(Arrays.toString(dirB[1]) + " / " + Arrays.toString(dirS[1]));


        for (int i = 0; i < 9; i++) {//box
            for (int j = 0; j < 9; j++) {
                if (this.charGrid[i / 3 * 3 + j / 3][i % 3 * 3 + j % 3] != '0') { //issue is probably maybe here maybe
                    count++;
                }
                if (count == 8) {
                    dirB[2][i] = true;
                    break;
                }
                if (count > 5) {
                    dirS[2][i] = true;
                }
            }
            count = 0;
        }
        System.out.println(Arrays.toString(dirB[2]) + " / " + Arrays.toString(dirS[2]) + "--------");




        while (q < 729) {
            npAvg++;
            iterations++;
            //dirB bullshit




            while (dirB[2][(q / 243) * 3 + (q % 81) / 27]) { //dirB box
                System.out.println("box " + (q / 81) + " " + (q % 81));
                q += 27;
                if (!dirB[2][(q / 243) * 3 + (q % 81) / 27] || q % 81 == 0) {
                    q--;
                    break;
                }
            }




            while (dirB[0][q / 81]) { //dirB hor
                System.out.println("hor " + (q / 81) + " " + (q % 81));
                q += 81;
                if (!dirB[0][q / 81]) {
                    q--;
                    break;
                }
            }




            while (dirB[1][(q % 81) / 9]) { //dirB vert
                System.out.println("vert " + (q / 81) + " " + (q % 81));
                q += 9;
                if (!dirB[1][(q % 81) / 9] || q % 81 == 0) {
                    q--;
                    break;
                }
            }


            //bsetup-skip, figure % 81 = 0 first
            if (q % 81 == 0){
                count = 0;
                scB = new boolean[]{false, false, false, false, false, false, false, false, false};


                for (int i = 0; i < 9;i++) { //region skip hor
                    if (this.charGrid[q / 81][i] != '0') {
                        count++;
                        scB[i] = true;
                    }
                }


                System.out.println((q / 81) + " " + Arrays.toString(scB));
            }


            while (scB[(q % 81) / 9]) { //buggy with % 81 = 0,
                System.out.println("scB " + (q / 81) + " " + (q % 81)); //largest issue is losing its to q++ at end, fix FIRST
                q = (q / 9 * 9 + 9);
                if (!(scB[(q % 81) / 9]) || q % 81 == 0) {
                    q--;
                    break;
                }
            }
            while ((caBD[0][(q / 81)][q % 9]) || caBD[1][(q % 81) / 9][q % 9] || caBD[2][(q / 243) * 3 + (q % 81) / 27][q % 9]) {//candidate blacklist v2, ~25% faster than v1
                System.out.println("caB " + (q / 81) + " " + (q % 81));
                q++;
                if (q % 9 == 0 || (!caBD[0][(q / 81)][q % 9] && !caBD[1][(q % 81) / 9][q % 9] && !caBD[2][(q / 243) * 3 + (q % 81) / 27][q % 9])) {
                    q--;
                    break;
                }
            }
           /*while (caB[q % 9]) {
               q++;
               if (!caB[q % 9] || q % 9 == 0) {
                   q--;
                   break;
               }
           }*/
            //caB then region blacklist
            //body
            //find way to not do else, should save a few its
            //g1
            if (this.candGrid[q / 81][q % 81] != '0') {
                count++;
            }
            if (count > 2) {
                q = (q / 9 * 9 + 8);
            }
            if (q % 9 == 8 && count != 2) {
                count = 0;
            }
            else if (q % 9 == 8) {
                count = 0;
                System.out.println("bivvy " + (q / 81) + " " + (q % 81));
                cands[0] = 'x';
                cands[1] = 'x';
                for (int i = 0; i < 9; i++) { //candgrab
                    if (this.candGrid[q / 81][(q % 81) / 9 * 9 + i] != '0' && cands[0] == 'x') {
                        cands[0] = this.candGrid[q / 81][(q % 81) / 9 * 9 + i];
                    }
                    else if (this.candGrid[q / 81][(q % 81) / 9 * 9 + i] != '0') {
                        cands[1] = this.candGrid[q / 81][(q % 81) / 9 * 9 + i];
                    }
                }
                System.out.println(cands);
                for (int i = 0; i < 9; i++) {//geetu
                    //prob should split this
                    count = 0;
                    count2 = 0;
                    count3 = 0;
                    for (int j = 0; j < 9; j++) {
                        if (!dirS[0][q / 81]) { //hor g2
                            if (i != (q % 81) / 9 && this.charGrid[q / 81][i] == '0' && this.candGrid[q / 81][i * 9 + j] != '0') {
                                count++;
                            }
                            if (count > 2) {
                                //count = 0;
                                //break;
                            }
                            if (j == 8 && count != 2) {
                                count = 0;
                            }
                            else if (j == 8) {
                                count = 0;
                                if (this.candGrid[q / 81][i * 9 + Character.getNumericValue(cands[0]) - 1] != '0' && this.candGrid[q / 81][i * 9 + Character.getNumericValue(cands[1]) - 1] != '0') {
                                    System.out.println("double bivvy " + (q / 81) + " " + i);
                                    for (int k = 0; k < 9; k ++) {
                                        if (k != (q % 81) / 9 && k != i && (this.candGrid[q / 81][k * 9 + Character.getNumericValue(cands[0]) - 1] != '0' || this.candGrid[q / 81][k * 9 + Character.getNumericValue(cands[1]) - 1] != '0')) {
                                            this.candGrid[q / 81][k * 9 + Character.getNumericValue(cands[0]) - 1] = '0';
                                            this.candGrid[q / 81][k * 9 + Character.getNumericValue(cands[1]) - 1] = '0';
                                            flag = true;
                                        }
                                    }
                                    if (flag) {
                                        return;
                                    }
                                }
                            }
                        }
                        if (!dirS[1][q % 81 / 9]) { //vert
                            if (i != q / 81 && this.charGrid[i][q % 81 / 9] == '0' && this.candGrid[i][q % 81 / 9 * 9 + j] != '0') {
                                count2++;
                            }
                            if (count2 > 2) {
                                //count2 = 0;
                                //break;
                            }
                            if (j == 8 && count2 != 2) {
                                count2 = 0;
                                //System.out.println("!" + (i));
                            }
                            else if (j == 8) {
                                count2 = 0;
                                if (this.candGrid[i][q % 81 / 9 * 9 + Character.getNumericValue(cands[0]) - 1] != '0' && this.candGrid[i][q % 81 / 9 * 9 + Character.getNumericValue(cands[1]) - 1] != '0') {
                                    System.out.println("vert double bivvy " + (i) + " " + (q % 81));
                                    for (int k = 0; k < 9; k ++) {
                                        if (k != q / 81 && k != i && (this.candGrid[k][q % 81 / 9 * 9 + Character.getNumericValue(cands[0]) - 1] != '0' || this.candGrid[k][q % 81 / 9 * 9 + Character.getNumericValue(cands[1]) - 1] != '0')) {
                                            this.candGrid[k][q % 81 / 9 * 9 + Character.getNumericValue(cands[0]) - 1] = '0';
                                            this.candGrid[k][q % 81 / 9 * 9 + Character.getNumericValue(cands[1]) - 1] = '0';
                                            flag = true;
                                        }
                                    }
                                    if (flag) {
                                        return;
                                    }
                                }
                            }
                        }
                        //System.out.println("----" + (q / 243) * 3 + (q % 81) / 27 + dirS[2][(q / 243) * 3 + (q % 81) / 27]);
                        if (!dirS[2][(q / 243) * 3 + (q % 81) / 27]) { //box
                            if (i != ((q / 81) % 3 * 3 + (q % 27 / 9)) && this.charGrid[i / 3 + (q / 243) * 3][i % 3 + (q % 81) / 27 * 3] == '0' && this.candGrid[i / 3 + (q / 243) * 3][i % 3 * 9 + (q % 81) / 27 * 27 + j] != '0') {
                                count3++;
                            }
                            if (count3 > 2) { //issue is def in box dirs one way or other
                                //count3 = 0;
                                //break;
                            }
                            if (j == 8 && count3 != 2) {
                                System.out.println("!" + i);
                                count3 = 0;
                            }
                            else if (j == 8) {
                                count3 = 0;
                                if (this.candGrid[i / 3 + (q / 243) * 3][i % 3 * 9 + (q % 81) / 27 * 27 +  Character.getNumericValue(cands[0]) - 1] != '0' && this.candGrid[i / 3 + (q / 243) * 3][i % 3 * 9 + (q % 81) / 27 * 27 +  Character.getNumericValue(cands[1]) - 1] != '0') {
                                    System.out.println("box double bivvy " + i);
                                    for (int k = 0; k < 9; k ++) {
                                        if (k != (q / 81 % 3 * 3 + (q % 27 / 9)) && k != i && (this.candGrid[k / 3 + (q / 243) * 3][k % 3 * 9 + (q % 81) / 27 * 27 +  Character.getNumericValue(cands[0]) - 1] != '0' || this.candGrid[k / 3 + (q / 243) * 3][k % 3 * 9 + (q % 81) / 27 * 27 +  Character.getNumericValue(cands[1]) - 1] != '0')) {
                                            this.candGrid[k / 3 + (q / 243) * 3][k % 3 * 9 + (q % 81) / 27 * 27 +  Character.getNumericValue(cands[0]) - 1] = '0';
                                            this.candGrid[k / 3 + (q / 243) * 3][k % 3 * 9 + (q % 81) / 27 * 27 +  Character.getNumericValue(cands[1]) - 1] = '0';
                                            flag = true;
                                        }
                                    }
                                    if (flag) {
                                        return;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            //candgrab
            //g2
            //exitB
            System.out.println("c" + (q / 81) + " " + (q % 81));
            q++;
        }
    }


    public void doHiddenPairs(){ //weight hiddens above nakeds
        //write set/grab first
        int count = 0;
        int count2 = 0;
        char pos1 = ' ';
        char pos2 = ' ';
        char[] blacklist = new char[9];
        for (int i = 0; i < 9; i++) {
            //set/grab
            for (int j = 0; j < 9; j++) {
                blacklist[j] = '1';
            }
            for (int j = 0; j < 9; j++) {
                if (this.charGrid[i][j] != '0') {
                    blacklist[Character.getNumericValue(this.charGrid[i][j]) - 1] = '0';
                }
            }
            //System.out.println(blacklist);
            for (int j = 0; j < 81; j++) {
                pos1 = ' ';
                pos2 = ' ';
                if (blacklist[j / 9] != '0' && this.candGrid[i][j % 9 * 9 + j / 9] != '0') {
                    //System.out.println("Count inc @ " + i + " " + (j % 9 * 9 + j / 9) + " val " + (j / 9 + 1) + " to " + (count + 1));
                    count++;
                }
                if(j % 9 == 8 && count != 2) {
                    count = 0;
                }
                else if (j % 9 == 8) {//second
                    //System.out.println("biv");
                    count = 0;
                    for (int k = 0; k < 9; k++) {
                        if (this.candGrid[i][j / 9 + k * 9] != '0' && pos1 == ' ') {
                            pos1 = Character.forDigit(k + 1, 10);
                        }
                        else if (this.candGrid[i][j / 9 + k * 9] != '0') {
                            pos2 = Character.forDigit(k + 1, 10);
                        }
                        iterations++;
                        hpAvg++;
                    }
                    for (int k = 0; k < 81; k++) {//decrease its here idk how
                        if (this.candGrid[i][k % 9 * 9 + k / 9] == '0') {
                            count++;
                        }
                        if (blacklist[k / 9] != '0' && k / 9 != j / 9 && this.candGrid[i][k % 9 * 9 + k / 9] != '0' && (k % 9 == Character.getNumericValue(pos1) - 1 || k % 9 == Character.getNumericValue(pos2) - 1)) { //make it only check @ biv1
                            //System.out.println("Count2 inc @ " + i + " " + (k % 9 * 9 + k / 9) + " val " + (k / 9 + 1) + " to " + (count2 + 1));
                            //System.out.println(k + " " + pos1 + " " + pos2);
                            count2++;
                        }
                        if (k % 9 == 8 && (count2 != 2 || count != 7)) {
                            count2 = 0;
                            count = 0;
                        }
                        else if (k % 9 == 8) {
                            //System.out.println("double biv");
                            count = 0;
                            count2 = 0;
                            for (int l = 0; l < 9; l++) {
                                if (l != (k / 9) && l != (j / 9)) {
                                    this.candGrid[i][(Character.getNumericValue(pos1) - 1) * 9 + l] = '0';
                                    this.candGrid[i][(Character.getNumericValue(pos2) - 1) * 9 + l] = '0';
                                }
                            }
                        }
                        iterations++;
                        hpAvg++;
                    }
                    pos1 = ' ';
                    pos2 = ' ';
                }
                iterations++;
                hpAvg++;
            }
        }
        //vert
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                blacklist[j] = '1';
            }
            for (int j = 0; j < 9; j++) {
                if (this.charGrid[j][i] != '0') {
                    blacklist[Character.getNumericValue(this.charGrid[j][i]) - 1] = '0';
                }
            }
            for (int j = 0; j < 81; j++) {
                hpAvg++;
                pos1 = ' ';
                pos2 = ' ';
                if (blacklist[j / 9] != '0' && this.candGrid[j % 9][i * 9 + j / 9] != '0') {
                    //System.out.println("Count inc @ " + (j % 9) + " " + (i * 9 + j / 9) + " val " + (j / 9 + 1) + " to " + (count + 1));
                    count++;
                }
                if (j % 9 == 8 && count != 2) {
                    //System.out.println("reset");
                    count = 0;
                }
                else if (j % 9 == 8) {
                    //System.out.println("biv");
                    count = 0;
                    count2 = 0;
                    for (int k = 0; k < 9; k++) {
                        if (this.candGrid[k][i * 9 + j / 9] == Character.forDigit((j / 9) + 1, 10) && pos1 == ' ') {
                            pos1 = Character.forDigit(k + 1, 10);
                        }
                        else if (this.candGrid[k][i * 9 + j / 9] == Character.forDigit((j / 9) + 1, 10)) {
                            pos2 = Character.forDigit(k + 1, 10);
                        }
                        iterations++;
                        hpAvg++;
                    }
                    //System.out.println(pos1 + " " + pos2);
                    for (int k = 0; k < 81; k++) {
                        hpAvg++;
                        iterations++;
                        if (this.candGrid[k % 9][i * 9 + k / 9] == '0') {
                            //System.out.println("Count inc @ " + (k % 9) + " " + (i * 9 + k / 9) + " val " + (k / 9 + 1) + " to " + (count + 1));
                            count++;
                        }
                        if (blacklist[k / 9] != '0' && k / 9 != j / 9 && this.candGrid[k % 9][i * 9 + k / 9] != '0' && (k % 9 == Character.getNumericValue(pos1) - 1 || k % 9 == Character.getNumericValue(pos2) - 1)) {
                            //System.out.println("Count 2 inc @ " + (k % 9) + " " + (i * 9 + k / 9) + " val " + (k / 9 + 1) + " to " + (count + 1));
                            count2++;
                        }
                        if (k % 9 == 8 && (count != 7 || count2 != 2)) {//issue with both? idfk
                            count = 0;
                            count2 = 0;
                        } else if (k % 9 == 8) {
                            //System.out.println("double biv");
                            count = 0;
                            count2 = 0;
                            for (int l = 0; l < 9; l++) {
                                if (l != (k / 9) && l != (j / 9)) {
                                    this.candGrid[Character.getNumericValue(pos1) - 1][i * 9 + l] = '0';
                                    this.candGrid[Character.getNumericValue(pos2) - 1][i * 9 + l] = '0';
                                }
                            }
                        }
                    }
                    count = 0;
                    count2 = 0;
                    pos1 = ' ';
                    pos2 = ' ';
                }
            }
        }
        //box
        count = 0;
        for (int i = 0; i < 9; i++) {
            hpAvg++;
            iterations++;
            for (int j = 0; j < 9; j++) {
                blacklist[j] = '1';
            }
            for (int j = 0; j < 9; j++) {
                if (this.charGrid[i / 3 * 3 + j / 3][i % 3 * 3 + j % 3] != '0') {
                    blacklist[Character.getNumericValue(this.charGrid[i / 3 * 3 + j / 3][i % 3 * 3 + j % 3]) - 1] = '0';
                }
            }
            //System.out.println(Arrays.toString(blacklist) + " " + i);
            for (int j = 0; j < 81; j++) {
                hpAvg++;
                iterations++;
                if (blacklist[j / 9] != '0' && this.candGrid[i / 3 * 3 + (j % 9) / 3][i % 3 * 27 + j % 3 * 9 + j / 9] != '0') {
                    //System.out.println("Count inc @ " + (i / 3 * 3 + (j % 9) / 3) + " " + (i % 3 * 27 + j % 3 * 9 + j / 9) + " val " + (j / 9 + 1) + " to " + (count + 1));
                    count++;
                }
                if (j % 9 == 8 && count != 2) {
                    count = 0;
                    //System.out.println("reset");
                }
                else if (j % 9 == 8) {
                    count = 0;
                    pos1 = ' ';
                    pos2 = ' ';
                    //System.out.println("biv");
                    for (int k = 0; k < 9; k++) {
                        if (this.candGrid[i / 3 * 3 + (k % 9) / 3][i % 3 * 27 + k % 3 * 9 + j / 9] != '0' && pos1 == ' ') {//stored the value in poss
                            pos1 = Character.forDigit(k + 1, 10);
                        }
                        else if (this.candGrid[i / 3 * 3 + (k % 9) / 3][i % 3 * 27 + k % 3 * 9 + j / 9] != '0') {
                            pos2 = Character.forDigit(k + 1, 10);
                        }
                    }
                    //System.out.println("pos: " + pos1 + " " + pos2);
                    for (int k = 0; k < 81; k++) {
                        hpAvg++;
                        iterations++;
                        if (this.candGrid[i / 3 * 3 + (k % 9) / 3][i % 3 * 27 + k / 9 + k % 3 * 9] == '0') {
                            //System.out.println("Count inc @ " + (i / 3 * 3 + k / 27) + " " + (i % 3 * 27 + k / 9) + " val " + (k / 9 + 1) + " to " + (count + 1));
                            count++;
                        }//got here
                        if (blacklist[k / 9] != '0' && k / 9 != j / 9 && this.candGrid[i / 3 * 3 + (k % 9) / 3][i % 3 * 27 + k / 9 + k % 3 * 9] != '0' && (k % 9 == Character.getNumericValue(pos1) - 1 || k % 9 == Character.getNumericValue(pos2) - 1)) {
                            //System.out.println("Count 2 inc @ " + (k % 9) + " " + (i * 9 + k / 9) + " val " + (k / 9 + 1) + " to " + (count + 1));
                            count2++;
                        }
                        if (k % 9 == 8 && (count != 7 || count2 != 2)) {//issue with both? idfk
                            count = 0;
                            count2 = 0;
                        } else if (k % 9 == 8) {
                            //System.out.println("double biv " + pos1 + " " + pos2 + ": " + (j / 9) + ", " + (k / 9));
                            count = 0;
                            count2 = 0;
                            for (int l = 0; l < 9; l++) {
                                if (l != (k / 9) && l != (j / 9)) {
                                    //System.out.println("elim " + ((Character.getNumericValue(pos1) - 1) / 3 + i / 3 * 3) + " " + ((i % 3) * 27 + l + (Character.getNumericValue(pos1) - 1) % 3 * 9)); //in two diff boxes???
                                    //System.out.println("elim " + ((Character.getNumericValue(pos2) - 1) / 3 + i / 3 * 3) + " " + ((i % 3) * 27 + l + (Character.getNumericValue(pos2) - 1) % 3 * 9));
                                    this.candGrid[(Character.getNumericValue(pos1) - 1) / 3 + i / 3 * 3][(i % 3) * 27 + l + (Character.getNumericValue(pos1) - 1) % 3 * 9] = '0'; //this is just fucked
                                    this.candGrid[(Character.getNumericValue(pos2) - 1) / 3 + i / 3 * 3][(i % 3) * 27 + l + (Character.getNumericValue(pos2) - 1) % 3 * 9] = '0'; //should be 3 and 5
                                }
                            }
                        }
                    }
                }
            }
        }
        hp++;
    }
    public void doLockedCandidates() {
        lc1++;
        int count = 0;
        int row = 0;
        boolean found = false;
        boolean done = false;
        while (!done) {
            for (int i = 0; i < 9; i++) { //hor box
                for (int j = 0; j < 81; j++) {
                    iterations++;
                    lc1Avg++;
                    if (!found && this.charGrid[i / 3 * 3 + (j % 9) / 3][i % 3 * 3 + j % 3] == '0' && this.candGrid[i / 3 * 3 + (j % 9) / 3][i % 3 * 27 + (j % 3) * 9 + j / 9] != '0') {
                        count++;
                        row = (j % 9) / 3;
                        found = true;
                    }
                    if (j % 3 == 2) {
                        found = false;
                    }
                    if (j % 9 == 8 && count != 1) {
                        count = 0;
                        row = 0;
                    } else if (j % 9 == 8) {
                        //System.out.println("Found @ " + (i + 1) + " " + ((j / 9) + 1));
                        count = 0;
                        found = false;
                        for (int k = 0; k < 9; k++) {//r not stored in j % at else if
                            if (this.candGrid[i / 3 * 3 + row][k * 9 + j / 9] != '0' && k / 3 * 3 != i % 3 * 3) {
                                //System.out.println("Elim @ " + ((i / 3 * 3 + row) + 1) + " " + (((k * 9 + j / 9) / 9) + 1) + " " + (j / 9 + 1));
                                this.candGrid[i / 3 * 3 + row][k * 9 + j / 9] = '0';
                                done = true;
                            }
                            iterations++;
                            lc1Avg++;
                        }
                        row = 0;
                    }
                }
                for (int j = 0; j < 81; j++) {
                    iterations++;
                    lc1Avg++;
                    if (!found && this.charGrid[i / 3 * 3 + j % 3][i % 3 * 3 + (j % 9) / 3] == '0' && this.candGrid[i / 3 * 3 + j % 3][i % 3 * 27 + ((j % 9) / 3) * 9 + j / 9] != '0') {
                        count++;
                        row = (j % 9) / 3;
                        found = true;
                    }
                    if (j % 3 == 2) {
                        found = false;
                    }
                    if (j % 9 == 8 && count != 1) {
                        count = 0;
                        row = 0;
                    } else if (j % 9 == 8) {
                        //System.out.println("Found @ " + (i + 1) + " " + ((j / 9) + 1));
                        count = 0;
                        for (int k = 0; k < 9; k++) {//r not stored in j % at else if
                            if (this.candGrid[k][i % 3 * 27 + row * 9 + j / 9] != '0' && k / 3 * 3 != i / 3 * 3) {
                                //System.out.println("Elim @ " + ((k) + 1) + " " + (((i % 3 * 27 + row * 9 + j / 9) / 9) + 1) + " " + (j / 9 + 1));
                                this.candGrid[k][i % 3 * 27 + row * 9 + j / 9] = '0';
                                return;
                            }
                            iterations++;
                            lc1Avg++;
                        }
                        row = 0;
                    }
                    if (i == 8 && j == 80) {
                        done = true;
                    }
                }
            }
        }
    }
    public void doClaimCandidate() {
        lc1++;
        int count = 0;
        int row = 0;
        boolean found = false;
        boolean done = false;
        while (!done) {
            for (int i = 0; i < 9; i++) { //hor box
                for (int j = 0; j < 81; j++) {
                    iterations++;
                    lc1Avg++;
                    if (!found && this.charGrid[i / 3 * 3 + (j % 9) / 3][i % 3 * 3 + j % 3] == '0' && this.candGrid[i / 3 * 3 + (j % 9) / 3][i % 3 * 27 + (j % 3) * 9 + j / 9] != '0') {
                        count++;
                        row = (j % 9) / 3;
                        found = true;
                    }
                    if (j % 3 == 2) {
                        found = false;
                    }
                    if (j % 9 == 8 && count != 2) {
                        count = 0;
                        row = 0;
                    } else if (j % 9 == 8) {
                        //System.out.println("Found @ " + (i + 1) + " " + ((j / 9) + 1));
                        count = 0;
                        found = false;
                        for (int k = 0; k < 9; k++) {//r not stored in j % at else if
                            if (this.candGrid[i / 3 * 3 + row][k * 9 + j / 9] != '0' && k / 3 * 3 != i % 3 * 3) { //
                                //System.out.println("Elim @ " + ((i / 3 * 3 + row) + 1) + " " + (((k * 9 + j / 9) / 9) + 1) + " " + (j / 9 + 1));
                                this.candGrid[i / 3 * 3 + row][k * 9 + j / 9] = '0';
                                done = true;
                            }
                            iterations++;
                            lc1Avg++;
                        }
                        row = 0;
                    }
                }
                for (int j = 0; j < 81; j++) {
                    iterations++;
                    lc1Avg++;
                    if (!found && this.charGrid[i / 3 * 3 + j % 3][i % 3 * 3 + (j % 9) / 3] == '0' && this.candGrid[i / 3 * 3 + j % 3][i % 3 * 27 + ((j % 9) / 3) * 9 + j / 9] != '0') {
                        count++;
                        row = (j % 9) / 3;
                        found = true;
                    }
                    if (j % 3 == 2) {
                        found = false;
                    }
                    if (j % 9 == 8 && count != 2) {
                        count = 0;
                        row = 0;
                    } else if (j % 9 == 8) {
                        //System.out.println("Found @ " + (i + 1) + " " + ((j / 9) + 1));
                        count = 0;
                        for (int k = 0; k < 9; k++) {//r not stored in j % at else if
                            if (this.candGrid[k][i % 3 * 27 + row * 9 + j / 9] != '0' && k / 3 * 3 != i / 3 * 3) {
                                //System.out.println("Elim @ " + ((k) + 1) + " " + (((i % 3 * 27 + row * 9 + j / 9) / 9) + 1) + " " + (j / 9 + 1));
                                this.candGrid[k][i % 3 * 27 + row * 9 + j / 9] = '0';
                                return;
                            }
                            iterations++;
                            lc1Avg++;
                        }
                        row = 0;
                    }
                    if (i == 8 && j == 80) {
                        done = true;
                    }
                }
            }
        }
    }
    public void doNakedTriples(){
        nt++;
        int count = 0;
        int count2 = 0;
        int candcount = 0;
        boolean flag = false;
        boolean done = false;
        char[] candidates = {'x', 'x', 'x'};
        outerloop:
        while (!done) {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 81; j++) {
                    iterations++;
                    ntAvg++;
                    candcount = 0;
                    if (this.charGrid[i][j / 9] == '0' && this.candGrid[i][j] != '0') {
                        count++;
                    }
                    if (j % 9 == 8 && ((count != 2) && (count != 3))) {
                        count = 0;
                    } else if (j % 9 == 8) {
                        if (count == 3) {
                            flag = true;
                        }
                        count = 0;
                        //System.out.println("First " + (i + 1) + " " + ((j / 9) + 1));
                        for (int k = 0; k < 9; k++) {
                            if (this.candGrid[i][j / 9 * 9 + k] != '0') {
                                candidates[candcount] = this.candGrid[i][j / 9 * 9 + k];
                                candcount++;
                            }
                        }
                        //System.out.println(candidates);
                        for (int k = 0; k < 81; k++) {
                            iterations++;
                            ntAvg++;
                            if (k / 9 * 9 != j / 9 * 9 && this.charGrid[i][k / 9] == '0' && this.candGrid[i][k] != '0') {
                                count++;
                            }
                            if (this.charGrid[i][k / 9] == '0' && (this.candGrid[i][k] == candidates[0] || this.candGrid[i][k] == candidates[1] || this.candGrid[i][k] == candidates[2])) {
                                count2++;
                            }
                            if (k % 9 == 8) {
                                //System.out.println(count + " " + count2);
                            }
                            if (candcount == 3 && k % 9 == 8 && ((count != 2 && count != 3) || count2 != count)) {
                                count = 0;
                                count2 = 0;
                            } else if (candcount == 2 && k % 9 == 8 && ((count != 2 && count != 3) || (count2 != count - 1 && count2 != count))) {//if cands has 3 count2 MUST have 3
                                count = 0;
                                count2 = 0;
                            } else if (k % 9 == 8) {
                                count = 0;
                                count2 = 0;
                                //System.out.println("Second " + (i + 1) + " " + ((k / 9) + 1));
                                if (!flag) { //this is grabbing dupes with 12..7.38..6...87...7....9...3...187971..9.625.965.7143347...261982416537651732498
                                    for (int l = 0; l < 9; l++) {
                                        if ((this.candGrid[i][k / 9 * 9 + l] != '0' && (this.candGrid[i][k / 9 * 9 + l] != candidates[0] && this.candGrid[i][k / 9 * 9 + l] != candidates[1] && this.candGrid[i][k / 9 * 9 + l] != candidates[2]))) { // buggedvgffcswsderdftgtygyugyugyugfc
                                            //System.out.println("proc " + this.candGrid[i][k / 9 * 9 + l]);
                                            candidates[2] = this.candGrid[i][k / 9 * 9 + l];
                                            break;
                                        }
                                    }
                                }
                                //System.out.println(candidates);
                                for (int l = 0; l < 81; l++) {
                                    iterations++;
                                    ntAvg++;
                                    if (l / 9 * 9 != j / 9 * 9 && l / 9 * 9 != k / 9 * 9 && this.candGrid[i][l] != '0') {
                                        //System.out.println("Count 1 inc " + l + " to " + (count + 1));
                                        count++;
                                    }
                                    if (this.candGrid[i][l] == candidates[0] || this.candGrid[i][l] == candidates[1] || this.candGrid[i][l] == candidates[2]) { //not working??
                                        count2++;
                                    }
                                    if (l % 9 == 8) {
                                        //System.out.println("2: " + count + " " + count2 + " @ " + i + " " + l);
                                    }
                                    if (l % 9 == 8 && (((count != 2 && count != 3) || (count2 != 2 && count2 != 3)) || count != count2)) {//could just be c != c2, test later
                                        count = 0;
                                        count2 = 0;
                                    } else if (l % 9 == 8) {
                                        count = 0;
                                        count2 = 0;
                                        //System.out.println("Third!!!!!!!" + (i + 1) + " " + ((l / 9) + 1));
                                        for (int m = 0; m < 9; m++) {
                                            if (m != j / 9 && m != k / 9 && m != l / 9 && ((this.candGrid[i][m * 9 + Character.getNumericValue(candidates[0]) - 1] != '0' || this.candGrid[i][m * 9 + Character.getNumericValue(candidates[1]) - 1] != '0') || this.candGrid[i][m * 9 + Character.getNumericValue(candidates[2]) - 1] != '0')) {
                                                //System.out.println("Elim @ " + i + " " + (m));
                                                this.candGrid[i][m * 9 + Character.getNumericValue(candidates[0]) - 1] = '0';
                                                this.candGrid[i][m * 9 + Character.getNumericValue(candidates[1]) - 1] = '0';
                                                this.candGrid[i][m * 9 + Character.getNumericValue(candidates[2]) - 1] = '0';
                                            }
                                        }
                                    }
                                }
                                //here
                                if (!flag) {
                                    candidates[2] = 'x';
                                }
                            }
                        }
                        flag = false;
                    }
                    candcount = 0;
                    candidates[0] = 'x';
                    candidates[1] = 'x';
                    candidates[2] = 'x';
                }
                if (i == 8) {
                    done = true;
                }
            }
            for (int i = 0; i < 9; i++) {//vert
                for (int j = 0; j < 81; j++) {
                    iterations++;
                    ntAvg++;
                    candcount = 0;
                    if (this.candGrid[j / 9][i * 9 + j % 9] != '0') {
                        count++;
                    }
                    if (j % 9 == 8 && ((count != 2) && (count != 3))) {
                        count = 0;
                    } else if (j % 9 == 8) {
                        if (count == 3) {
                            flag = true;
                        }
                        count = 0;
                        //System.out.println("First " + (i + 1) + " " + ((j / 9) + 1));
                        for (int k = 0; k < 9; k++) {
                            if (this.candGrid[j / 9][i * 9 + k] != '0') {
                                candidates[candcount] = this.candGrid[j / 9][i * 9 + k];
                                candcount++;
                            }
                        }
                        //System.out.println(candidates);
                        for (int k = 0; k < 81; k++) {
                            iterations++;
                            ntAvg++;
                            if (k / 9 * 9 != j / 9 * 9 && this.candGrid[k / 9][i * 9 + k % 9] != '0') {
                                count++;
                            }
                            if (this.candGrid[k / 9][i * 9 + k % 9] == candidates[0] || this.candGrid[k / 9][i * 9 + k % 9] == candidates[1] || this.candGrid[k / 9][i * 9 + k % 9] == candidates[2]) {
                                count2++;
                            }
                            if (candcount == 3 && k % 9 == 8 && ((count != 2 && count != 3) || count2 != count)) {
                                count = 0;
                                count2 = 0;
                            } else if (candcount == 2 && k % 9 == 8 && ((count != 2 && count != 3) || (count2 != count - 1 && count2 != count))) {//if cands has 3 count2 MUST have 3
                                count = 0;
                                count2 = 0;
                            } else if (k % 9 == 8) {
                                count = 0;
                                count2 = 0;
                                //System.out.println("Second " + (i + 1) + " " + ((k / 9) + 1));
                                if (!flag) { //this is grabbing dupes with 12..7.38..6...87...7....9...3...187971..9.625.965.7143347...261982416537651732498
                                    for (int l = 0; l < 9; l++) {
                                        if ((this.candGrid[k / 9][i * 9 + l] != '0' && (this.candGrid[k / 9][i * 9 + l] != candidates[0] && this.candGrid[k / 9][i * 9 + l] != candidates[1] && this.candGrid[k / 9][i * 9 + l] != candidates[2]))) { // buggedvgffcswsderdftgtygyugyugyugfc
                                            //System.out.println("proc " + this.candGrid[i][k / 9 * 9 + l]);
                                            candidates[2] = this.candGrid[k / 9][i * 9 + l];
                                            break;
                                        }
                                    }
                                }
                                //System.out.println(candidates);
                                for (int l = 0; l < 81; l++) {
                                    iterations++;
                                    ntAvg++;
                                    if (l / 9 * 9 != j / 9 * 9 && l / 9 * 9 != k / 9 * 9 && this.candGrid[l / 9][i * 9 + l % 9] != '0') {
                                        //System.out.println("Count 1 inc " + l + " to " + (count + 1));
                                        count++;
                                    }
                                    if (this.candGrid[l / 9][i * 9 + l % 9] == candidates[0] || this.candGrid[l / 9][i * 9 + l % 9] == candidates[1] || this.candGrid[l / 9][i * 9 + l % 9] == candidates[2]) { //not working??
                                        count2++;
                                    }
                                    if (l % 9 == 8) {
                                        //System.out.println("2: " + count + " " + count2 + " @ " + i + " " + l);
                                    }
                                    if (l % 9 == 8 && (((count != 2 && count != 3) || (count2 != 2 && count2 != 3)) || count != count2)) {//could just be c != c2, test later
                                        count = 0;
                                        count2 = 0;
                                    } else if (l % 9 == 8) {
                                        count = 0;
                                        count2 = 0;
                                        //System.out.println("Third!!!!!!!" + (i + 1) + " " + ((l / 9) + 1));
                                        for (int m = 0; m < 9; m++) {
                                            if (m != j / 9 && m != k / 9 && m != l / 9 && ((this.candGrid[m][i * 9 + Character.getNumericValue(candidates[0]) - 1] != '0' || this.candGrid[m][i * 9 + Character.getNumericValue(candidates[1]) - 1] != '0') || this.candGrid[m][i * 9 + Character.getNumericValue(candidates[2]) - 1] != '0')) {
                                                //System.out.println("Elim @ " + i + " " + (m));
                                                this.candGrid[m][i * 9 + Character.getNumericValue(candidates[0]) - 1] = '0';
                                                this.candGrid[m][i * 9 + Character.getNumericValue(candidates[1]) - 1] = '0';
                                                this.candGrid[m][i * 9 + Character.getNumericValue(candidates[2]) - 1] = '0';
                                            }
                                        }
                                    }
                                }
                                //here
                                if (!flag) {
                                    candidates[2] = 'x';
                                }
                            }
                        }
                        flag = false;
                    }
                    candcount = 0;
                    candidates[0] = 'x';
                    candidates[1] = 'x';
                    candidates[2] = 'x';
                }
                if (i == 8) {
                    done = true;
                }
            }
            for (int i = 0; i < 9; i++) {//box
                for (int j = 0; j < 81; j++) {
                    iterations++;
                    ntAvg++;
                    flag = false;
                    if (this.candGrid[i / 3 * 3 + j / 27][i % 3 * 27 + j % 27] != '0') {
                        count++;
                    }
                    if (j % 9 == 8 && (count != 2 && count != 3)) {
                        count = 0;
                    }








                    else if (j % 9 == 8) {
                        //System.out.println("First: " + i + " " + (j / 9));
                        if (count == 3) {
                            flag = true;
                        }
                        count = 0;
                        //System.out.println("First " + (i + 1) + " " + ((j / 9) + 1));
                        for (int k = 0; k < 9; k++) {
                            if (this.candGrid[i / 3 * 3 + j / 27][i % 3 * 27 + (j % 27) / 9 * 9 + k] != '0') {
                                candidates[candcount] = this.candGrid[i / 3 * 3 + j / 27][i % 3 * 27 + (j % 27) / 9 * 9 + k];
                                candcount++;
                            }
                        }
                        //System.out.println(candcount + " " + i + " " + j);
                        //System.out.println(candidates);
                        for (int k = 0; k < 81; k++) {
                            iterations++;
                            ntAvg++;
                            if (k / 9 != j / 9 && this.candGrid[i / 3 * 3 + k / 27][i % 3 * 27 + k % 27] != '0') {
                                count++;
                            }
                            if (this.candGrid[i / 3 * 3 + k / 27][i % 3 * 27 + k % 27] == candidates[0] || this.candGrid[i / 3 * 3 + k / 27][i % 3 * 27 + k % 27] == candidates[1] || this.candGrid[i / 3 * 3 + k / 27][i % 3 * 27 + k % 27] == candidates[2]) {
                                count2++;
                            }
                            if (candcount == 3 && k % 9 == 8 && ((count != 2 && count != 3) || count2 != count)) {
                                count = 0;
                                count2 = 0;
                            } else if (candcount == 2 && k % 9 == 8 && ((count != 2 && count != 3) || (count2 != count - 1 && count2 != count))) {//if cands has 3 count2 MUST have 3
                                count = 0;
                                count2 = 0;
                            } else if (k % 9 == 8) {//                                                             code grab cands flag
                                //System.out.println("Second " + count + " " + count2 + " @ " + i + " " + (k / 9));
                                count = 0;
                                count2 = 0;
                                //System.out.println("Second " + (i + 1) + " " + ((k / 9) + 1));
                                if (!flag) {
                                    for (int l = 0; l < 9; l++) {
                                        if ((this.candGrid[i / 3 * 3 + k / 27][i % 3 * 27 + (k % 27) / 9 * 9 + l] != '0' && (this.candGrid[i / 3 * 3 + k / 27][i % 3 * 27 + (k % 27) / 9 * 9 + l] != candidates[0] && this.candGrid[i / 3 * 3 + k / 27][i % 3 * 27 + (k % 27) / 9 * 9 + l] != candidates[1] && this.candGrid[i / 3 * 3 + k / 27][i % 3 * 27 + (k % 27) / 9 * 9 + l] != candidates[2]))) { // buggedvgffcswsderdftgtygyugyugyugfc
                                            candidates[2] = this.candGrid[i / 3 * 3 + k / 27][i % 3 * 27 + (k % 27) / 9 * 9 + l];
                                            //System.out.println("proc " + Arrays.toString(candidates));
                                            break;
                                        }
                                    }
                                }
                                for (int l = 0; l < 81; l++) {
                                    iterations++;
                                    ntAvg++;
                                    if (l / 9 * 9 != j / 9 * 9 && l / 9 * 9 != k / 9 * 9 && this.candGrid[i / 3 * 3 + l / 27][i % 3 * 27 + l % 27] != '0') {
                                        //System.out.println("Count 1 inc " + l + " to " + (count + 1));
                                        count++;
                                    }
                                    if (this.candGrid[i / 3 * 3 + l / 27][i % 3 * 27 + l % 27] == candidates[0] || this.candGrid[i / 3 * 3 + l / 27][i % 3 * 27 + l % 27] == candidates[1] || this.candGrid[i / 3 * 3 + l / 27][i % 3 * 27 + l % 27] == candidates[2]) { //not working??
                                        count2++;
                                    }
                                    if (l % 9 == 8) {
                                        //System.out.println("2: " + count + " " + count2 + " @ " + i + " " + l);
                                    }
                                    if (l % 9 == 8 && (((count != 2 && count != 3) || (count2 != 2 && count2 != 3)) || count != count2)) {//could just be c != c2, test later
                                        count = 0;
                                        count2 = 0;
                                    } else if (l % 9 == 8) {
                                        count = 0;
                                        count2 = 0;
                                        //System.out.println("Third!!!!!!!" + (i + 1) + " " + ((l / 9) + 1));
                                        for (int m = 0; m < 9; m++) {
                                            if (m != j / 9 && m != k / 9 && m != l / 9 && ((this.candGrid[i / 3 * 3 + m / 3][i % 3 * 27 + (m % 3) * 9 + Character.getNumericValue(candidates[0]) - 1] != '0' || this.candGrid[i / 3 * 3 + m / 3][i % 3 * 27 + (m % 3) * 9 + Character.getNumericValue(candidates[1]) - 1] != '0') || this.candGrid[i / 3 * 3 + m / 3][i % 3 * 27 + (m % 3) * 9 + Character.getNumericValue(candidates[2]) - 1] != '0')) {
                                                System.out.println("Elim @ " + i + " " + (m));
                                                this.candGrid[i / 3 * 3 + m / 3][i % 3 * 27 + (m % 3) * 9 + Character.getNumericValue(candidates[0]) - 1] = '0';
                                                this.candGrid[i / 3 * 3 + m / 3][i % 3 * 27 + (m % 3) * 9 + Character.getNumericValue(candidates[1]) - 1] = '0';
                                                this.candGrid[i / 3 * 3 + m / 3][i % 3 * 27 + (m % 3) * 9 + Character.getNumericValue(candidates[2]) - 1] = '0';
                                            }
                                        }
                                    }
                                }
                                //here
                                if (!flag) {
                                    candidates[2] = 'x';
                                }
                            }
                        }
                    }
                    candcount = 0;
                    candidates[0] = 'x';
                    candidates[1] = 'x';
                    candidates[2] = 'x';
                }
            }
            done = true;
        }
    }
    public void doHiddenTriples() {//write with while
        ht++;
        //hp blacklist system for whilw only
        int[] pos = {-1, -1, -1};
        int count = 0;
        int count2 = 0;
        int posess = 0;
        int sc = 0;
        boolean flag = false;
        outerloop:
        for (int i = 0; i < 9; i++) {//hor
            for (int j = 0; j < 9; j++) {
                if (this.charGrid[i][j] != '0') {
                    sc++;
                }
            }
            if (sc < 3) {
                for (int j = 0; j < 81; j++) {
                    htAvg++;
                    iterations++;
                    if (this.charGrid[i][j % 9] == '0' && this.candGrid[i][j % 9 * 9 + j / 9] != '0') {
                        count++;
                    }
                    if (j % 9 == 8 && (count > 3 || 1 > count)) {
                        count = 0;
                    } else if (j % 9 == 8) {
                        System.out.println(i + " " + j + " ;" + count);
                        count = 0;
                        for (int k = 0; k < 9; k++) {
                            if (this.charGrid[i][k] == '0' && this.candGrid[i][j / 9 + k * 9] != '0') {
                                pos[posess] = k;
                                posess++;
                            }
                        }
                        System.out.println(Arrays.toString(pos) + ": " + (j / 9 + 1));
                        for (int k = 0; k < 81; k++) {
                            htAvg++;
                            iterations++;
                            if (k / 9 != j / 9 && this.charGrid[i][k % 9] == '0' && this.candGrid[i][k % 9 * 9 + k / 9] != '0') {
                                count++;
                            }
                            if ((k % 9 == pos[0] || k % 9 == pos[1] || k % 9 == pos[2]) && (this.candGrid[i][k % 9 * 9 + k / 9] != '0')) { //find a way to only use one
                                count2++;
                            }
                            if (k % 9 == 8 && ((count > 3 || 1 > count) || (posess == 3 && (count != count2)) || (posess == 2 && (count2 != count && count != count2 + 1)))) {// got rid of half bad cases, 3/2 non fonctionner :P
                                System.out.println("----- " + count + " " + count2); //9 in first should be 2, 2 for count???
                                count = 0;
                                count2 = 0;
                            } else if (k % 9 == 8) {
                                System.out.println("Second " + (k / 9 + 1) + " " + Arrays.toString(pos)); //issue is likely not resetting pos, va faire de la merde 7 2 1
                                count = 0;
                                count2 = 0;
                                for (int l = 0; l < (3 - posess); l++) {
                                    for (int m = 0; m < 9; m++) {
                                        if (m != pos[0] && m != pos[1] && this.candGrid[i][k / 9 + m * 9] != '0') {
                                            pos[posess + l] = m;
                                        }
                                    }
                                }
                                for (int l = 0; l < 81; l++) {
                                    htAvg++;
                                    iterations++;
                                    if (l / 9 != k / 9 && l / 9 != j / 9 && this.charGrid[i][l % 9] == '0' && this.candGrid[i][l % 9 * 9 + l / 9] != '0') {
                                        count++;
                                    }
                                    if (l / 9 != k / 9 && l / 9 != j / 9 && (l % 9 == pos[0] || l % 9 == pos[1] || l % 9 == pos[2]) && (this.candGrid[i][l % 9 * 9 + l / 9] != '0')) { //find a way to only use one
                                        count2++;
                                    }
                                    if (l % 9 == 8 && ((count > 3 || 1 > count) || count != count2)) {// got rid of half bad cases, 3/2 non fonctionner :P
                                        System.out.println("+++++ " + count + " " + count2); //9 in first should be 2, 2 for count???
                                        count = 0;
                                        count2 = 0;
                                    } else if (l % 9 == 8) {
                                        System.out.println("Last " + Arrays.toString(pos) + " " + (l / 9 + 1));
                                        for (int m = 0; m < 9; m++) {
                                            if (m != j / 9 && m != k / 9 && m != l / 9 && (this.candGrid[i][pos[0] * 9 + m] != '0' ||
                                                    this.candGrid[i][pos[1] * 9 + m] != '0' || this.candGrid[i][pos[2] * 9 + m] != '0')) {
                                                this.candGrid[i][pos[0] * 9 + m] = '0';
                                                this.candGrid[i][pos[1] * 9 + m] = '0';
                                                this.candGrid[i][pos[2] * 9 + m] = '0';
                                                flag = true;
                                            }
                                        }
                                        if (flag) {
                                            break outerloop;
                                        }
                                        count = 0;
                                        count2 = 0;
                                    }
                                }
                                for (int l = 0; l < 3 - posess; l++) {
                                    pos[2 - l] = -1;
                                }
                            }
                        }
                        posess = 0;
                        pos = new int[]{-1, -1, -1};
                    }
                }
            }
            sc = 0;
        }
    }
    public void doNakedQuadruples() { //use blacklist for already checked cells ------------------------------------------------------------------------------------------------------------------------------------------------------------
        nq++;
        int a = 0;
        int b = 0;
        int b2 = 0;
        int c = 0;
        int c2 = 0;
        int d = 0;
        int d2 = 0;
        int count = 0;
        char[] candidates = {'x', 'x', 'x', 'x'};
        for (int i = 0; i < 9; i++) {
            for (int ins = 0; ins < 9; ins++) {
                if (this.charGrid[i][ins] != '0') {
                    count++;
                }
            }
            if (count < 3) {
                outerouterloop:
                for (int j = 0; j < 81; j++) {
                    iterations++;
                    nqAvg++;
                    if (this.charGrid[i][j / 9] == '0' && this.candGrid[i][j] != '0') {
                        a++;
                    }
                    if (j % 9 == 8 && (a > 4 || 2 > a)) {
                        a = 0;
                    } else if (j % 9 == 8) {
                        //System.out.println(a + "value @ " + i + " " + (j / 9));
                        for (int k = 0; k < 9; k++) {
                            if (this.candGrid[i][j / 9 * 9 + k] != '0' && candidates[0] == 'x') {
                                candidates[0] = this.candGrid[i][j / 9 * 9 + k];
                            } else if (this.candGrid[i][j / 9 * 9 + k] != '0' && candidates[1] == 'x') {
                                candidates[1] = this.candGrid[i][j / 9 * 9 + k];
                            } else if (this.candGrid[i][j / 9 * 9 + k] != '0' && candidates[2] == 'x') {
                                candidates[2] = this.candGrid[i][j / 9 * 9 + k];
                            } else if (this.candGrid[i][j / 9 * 9 + k] != '0' && candidates[3] == 'x') {
                                candidates[3] = this.candGrid[i][j / 9 * 9 + k];
                            }
                            iterations++;
                            nqAvg++;
                        }
                        //System.out.println(candidates);
                        for (int k = 0; k < 81; k++) {
                            iterations++;
                            nqAvg++;
                            if (k / 9 != j / 9 && this.charGrid[i][k / 9] == '0' && this.candGrid[i][k] != '0') {
                                b++;
                            }
                            if (this.candGrid[i][k] == candidates[0] || this.candGrid[i][k] == candidates[1] || this.candGrid[i][k] == candidates[2] || this.candGrid[i][k] == candidates[3]) {
                                b2++;
                            }
                            if ((k % 9 == 8 && ((!(a != 4 && (b2 == (b + a) - 4) || (b2 == (b + a) - 3)) && !(a == 4 && b == b2)))) || (this.charGrid[i][k / 9] != '0' && k % 9 == 8)) { // true false false
                                //System.out.println("a " + a + " " + b + " " + b2 + " " + i + " " + k);
                                b = 0;
                                b2 = 0;
                            } else if (k % 9 == 8) {
                                //System.out.println("Found @ " + i + " " + (k / 9) + " " + b + " " + b2 + " " + a);
                                //put cands
                                for (int l = 0; l < ((b - b2)); l++) {
                                    for (int m = 0; m < 9; m++) {
                                        if (this.candGrid[i][k / 9 * 9 + m] != '0' && (this.candGrid[i][k / 9 * 9 + m] != candidates[0] && this.candGrid[i][k / 9 * 9 + m] != candidates[1] && this.candGrid[i][k / 9 * 9 + m] != candidates[2] && this.candGrid[i][k / 9 * 9 + m] != candidates[3])) {
                                            candidates[a + l] = this.candGrid[i][k / 9 * 9 + m];
                                        }
                                    }
                                }
                                //System.out.println("+ " + Arrays.toString(candidates));
                                //just use total cands
                                for (int l = 0; l < 81; l++) {
                                    if (l / 9 != j / 9 && l / 9 != k / 9 && this.charGrid[i][l / 9] == '0' && this.candGrid[i][l] != '0') {
                                        c++;
                                    }
                                    if (l / 9 != j / 9 && l / 9 != k / 9 && this.charGrid[i][l / 9] == '0' && (this.candGrid[i][l] == candidates[0] || this.candGrid[i][l] == candidates[1] || this.candGrid[i][l] == candidates[2] || this.candGrid[i][l] == candidates[3])) {
                                        c2++;
                                    }
                                    if (l % 9 == 8 && (this.charGrid[i][l / 9] != '0' || (a + (b - b2) + (c - c2) > 4) || c > 4 || l / 9 == j / 9 || l / 9 == k / 9)) { //plpolloklmlkm;';.l';.l';.l';;
                                        c = 0;
                                        c2 = 0;
                                    } else if (l % 9 == 8) {
                                        //System.out.println("Third @ " + i + " " + (l / 9) + " / " + c + " " + c2);
                                        for (int m = 0; m < ((c - c2)); m++) {
                                            for (int n = 0; n < 9; n++) {
                                                if (this.candGrid[i][l / 9 * 9 + n] != '0' && (this.candGrid[i][l / 9 * 9 + n] != candidates[0] && this.candGrid[i][l / 9 * 9 + n] != candidates[1] && this.candGrid[i][l / 9 * 9 + n] != candidates[2] && this.candGrid[i][l / 9 * 9 + n] != candidates[3])) {
                                                    candidates[a + (b - b2) + m] = this.candGrid[i][l / 9 * 9 + n];
                                                }
                                            }
                                        }
                                        //System.out.println("++ " + Arrays.toString(candidates));
                                        for (int m = a + (b - b2); m < 4; m++) {
                                            candidates[m] = 'x';
                                        }
                                        for (int m = 0; m < 81; m++) {
                                            iterations++;
                                            nqAvg++;
                                            if (m / 9 != l / 9 && m / 9 != j / 9 && m / 9 != k / 9 && this.charGrid[i][m / 9] == '0' && this.candGrid[i][m] != '0') {
                                                d++;
                                            }
                                            if (m / 9 != l / 9 && m / 9 != j / 9 && m / 9 != k / 9 && this.charGrid[i][m / 9] == '0' && (this.candGrid[i][m] == candidates[0] || this.candGrid[i][m] == candidates[1] || this.candGrid[i][m] == candidates[2] || this.candGrid[i][m] == candidates[3])) {
                                                d2++;
                                            }
                                            if (m % 9 == 8 && (this.charGrid[i][m / 9] != '0' || (a + (b - b2) + (c - c2) + (d - d2) > 4) || d > 4 || m / 9 == l / 9 || m / 9 == j / 9 || m / 9 == k / 9)) { //plpolloklmlkm;';.l';.l';.l';;
                                                d = 0;
                                                d2 = 0;
                                            } else if (m % 9 == 8) {
                                                //System.out.println("Last @ " + i + " " + (m / 9) + " / " + d + " " + d2);
                                                for (int n = 0; n < ((d - d2)); n++) {
                                                    for (int o = 0; o < 9; o++) {
                                                        if (this.candGrid[i][l / 9 * 9 + o] != '0' && (this.candGrid[i][m / 9 * 9 + o] != candidates[0] && this.candGrid[i][m / 9 * 9 + o] != candidates[1] && this.candGrid[i][m / 9 * 9 + o] != candidates[2] && this.candGrid[i][m / 9 * 9 + o] != candidates[3])) {
                                                            candidates[a + (b - b2) + (c - c2) + n] = this.candGrid[i][m / 9 * 9 + o];
                                                        }
                                                    }
                                                }
                                                for (int o = 0; o < 9; o++) {
                                                    if (o != j / 9 && o != k / 9 && o != l / 9 && o != m / 9) {//this.char
                                                        //System.out.println("nqhorElim @ " + i + " " + (o * 9) + " " + Arrays.toString(candidates));
                                                        this.candGrid[i][o * 9 + Character.getNumericValue(candidates[0]) - 1] = '0';
                                                        this.candGrid[i][o * 9 + Character.getNumericValue(candidates[1]) - 1] = '0';
                                                        this.candGrid[i][o * 9 + Character.getNumericValue(candidates[2]) - 1] = '0';
                                                        this.candGrid[i][o * 9 + Character.getNumericValue(candidates[3]) - 1] = '0';
                                                    }
                                                }
                                                //System.out.println("++ " + Arrays.toString(candidates));
                                                for (int n = a + (c - c2) + (b - b2); n < 4; n++) {
                                                    candidates[n] = 'x';
                                                }
                                                break outerouterloop;
                                            }
                                        }
                                        //System.out.println("-- " + Arrays.toString(candidates));
                                        c = 0;
                                        c2 = 0;
                                    }
                                }
                                for (int l = a; l < 4; l++) {
                                    candidates[l] = 'x';
                                }
                                //System.out.println("- " + Arrays.toString(candidates));
                                b = 0;
                                b2 = 0;
                            }
                        }
                        candidates = new char[]{'x', 'x', 'x', 'x'};
                        a = 0;
                    }
                }
                a = 0;
                b = 0;
                b2 = 0;
                c = 0;
                c2 = 0;
                d = 0;
                d2 = 0;
            }
            count = 0;
            candidates = new char[]{'x', 'x', 'x', 'x'};
        }
        for (int i = 0; i < 9; i++) { //vert
            for (int ins = 0; ins < 9; ins++) {
                if (this.charGrid[ins][i] != '0') {
                    count++;
                }
            }
            if (count < 3) {
                outerouterloop:
                for (int j = 0; j < 81; j++) {
                    iterations++;
                    nqAvg++;
                    if (this.charGrid[j / 9][i] == '0' && this.candGrid[j / 9][i * 9 + j % 9] != '0') {
                        a++;
                    }
                    if (j % 9 == 8 && (a > 4 || 2 > a)) {
                        a = 0;
                    } else if (j % 9 == 8) {
                        //System.out.println(a + "value @ " + i + " " + (j / 9));
                        for (int k = 0; k < 9; k++) {
                            if (this.candGrid[j / 9][i * 9 + k] != '0' && candidates[0] == 'x') {
                                candidates[0] = this.candGrid[j / 9][i * 9 + k];
                            } else if (this.candGrid[j / 9][i * 9 + k] != '0' && candidates[1] == 'x') {
                                candidates[1] = this.candGrid[j / 9][i * 9 + k];
                            } else if (this.candGrid[j / 9][i * 9 + k] != '0' && candidates[2] == 'x') {
                                candidates[2] = this.candGrid[j / 9][i * 9 + k];
                            } else if (this.candGrid[j / 9][i * 9 + k] != '0' && candidates[3] == 'x') {
                                candidates[3] = this.candGrid[j / 9][i * 9 + k];
                            }
                            iterations++;
                            nqAvg++;
                        }
                        //System.out.println("vert " + Arrays.toString(candidates) + " @ " + (j / 9) + " " + i);
                        for (int k = 0; k < 81; k++) {//2
                            iterations++;
                            nqAvg++;
                            if (k / 9 != j / 9 && this.charGrid[k / 9][i] == '0' && this.candGrid[k / 9][i * 9 + k % 9] != '0') {
                                b++;
                            }
                            if (this.candGrid[k / 9][i * 9 + k % 9] == candidates[0] || this.candGrid[k / 9][i * 9 + k % 9] == candidates[1] || this.candGrid[k / 9][i * 9 + k % 9] == candidates[2] || this.candGrid[k / 9][i * 9 + k % 9] == candidates[3]) {
                                b2++;
                            }
                            if ((k % 9 == 8 && ((!(a != 4 && (b2 == (b + a) - 4) || (b2 == (b + a) - 3)) && !(a == 4 && b == b2)))) || (this.charGrid[k / 9][i] != '0' && k % 9 == 8)) { // true false false
                                //System.out.println("a " + a + " " + b + " " + b2 + " " + (k / 9) + " " + i);
                                b = 0;
                                b2 = 0;
                            } else if (k % 9 == 8) {
                                //System.out.println("vert found @ " + (k / 9) + " " + i);
                                //put cands
                                for (int l = 0; l < ((b - b2)); l++) {
                                    for (int m = 0; m < 9; m++) {
                                        if (this.candGrid[k / 9][i * 9 + m] != '0' && (this.candGrid[k / 9][i * 9 + m] != candidates[0] && this.candGrid[k / 9][i * 9 + m] != candidates[1] && this.candGrid[k / 9][i * 9 + m] != candidates[2] && this.candGrid[k / 9][i * 9 + m] != candidates[3])) {
                                            candidates[a + l] = this.candGrid[k / 9][i * 9 + m];
                                        }
                                    }
                                }
                                //System.out.println("+ " + Arrays.toString(candidates));
                                //just use total cands
                                for (int l = 0; l < 81; l++) {
                                    if (l / 9 != j / 9 && l / 9 != k / 9 && this.charGrid[l / 9][i] == '0' && this.candGrid[l / 9][i * 9 + l % 9] != '0') {
                                        c++;
                                    }
                                    if (l / 9 != j / 9 && l / 9 != k / 9 && this.charGrid[l / 9][i] == '0' && (this.candGrid[l / 9][i * 9 + l % 9] == candidates[0] || this.candGrid[l / 9][i * 9 + l % 9] == candidates[1] || this.candGrid[l / 9][i * 9 + l % 9] == candidates[2] || this.candGrid[l / 9][i * 9 + l % 9] == candidates[3])) {
                                        c2++;
                                    }
                                    if (l % 9 == 8 && (this.charGrid[l / 9][i] != '0' || (a + (b - b2) + (c - c2) > 4) || c > 4 || l / 9 == j / 9 || l / 9 == k / 9)) { //plpolloklmlkm;';.l';.l';.l';;
                                        c = 0;
                                        c2 = 0;
                                    } else if (l % 9 == 8) {
                                        //System.out.println("Third @ " + i + " " + (l / 9) + " / " + c + " " + c2);
                                        for (int m = 0; m < ((c - c2)); m++) {
                                            for (int n = 0; n < 9; n++) {
                                                if (this.candGrid[l / 9][i * 9 + n] != '0' && (this.candGrid[l / 9][i * 9 + n] != candidates[0] && this.candGrid[l / 9][i * 9 + n] != candidates[1] && this.candGrid[l / 9][i * 9 + n] != candidates[2] && this.candGrid[l / 9][i * 9 + n] != candidates[3])) {
                                                    candidates[a + (b - b2) + m] = this.candGrid[l / 9][i * 9 + n];
                                                }
                                            }
                                        }
                                        //System.out.println("++ " + Arrays.toString(candidates));
                                        for (int m = a + (b - b2); m < 4; m++) {
                                            candidates[m] = 'x';
                                        }
                                        for (int m = 0; m < 81; m++) {
                                            iterations++;
                                            nqAvg++;
                                            if (m / 9 != l / 9 && m / 9 != j / 9 && m / 9 != k / 9 && this.charGrid[m / 9][i] == '0' && this.candGrid[m / 9][i * 9 + m % 9] != '0') {
                                                d++;
                                            }
                                            if (m / 9 != l / 9 && m / 9 != j / 9 && m / 9 != k / 9 && this.charGrid[m / 9][i] == '0' && (this.candGrid[m / 9][i * 9 + m % 9] == candidates[0] || this.candGrid[m / 9][i * 9 + m % 9] == candidates[1] || this.candGrid[m / 9][i * 9 + m % 9] == candidates[2] || this.candGrid[m / 9][i * 9 + m % 9] == candidates[3])) {
                                                d2++;
                                            }
                                            if (m % 9 == 8 && (this.charGrid[m / 9][i] != '0' || (a + (b - b2) + (c - c2) + (d - d2) > 4) || d > 4 || m / 9 == l / 9 || m / 9 == j / 9 || m / 9 == k / 9)) { //plpolloklmlkm;';.l';.l';.l';;
                                                d = 0;
                                                d2 = 0;
                                            } else if (m % 9 == 8) {
                                                //System.out.println("Last @ " + i + " " + (m / 9) + " / " + d + " " + d2);
                                                for (int n = 0; n < ((d - d2)); n++) {
                                                    for (int o = 0; o < 9; o++) {
                                                        if (this.candGrid[m / 9][i * 9 + o] != '0' && (this.candGrid[m / 9][i * 9 + o] != candidates[0] && this.candGrid[m / 9][i * 9 + o] != candidates[1] && this.candGrid[m / 9][i * 9 + o] != candidates[2] && this.candGrid[m / 9][i * 9 + o] != candidates[3])) {
                                                            candidates[a + (b - b2) + (c - c2) + n] = this.candGrid[m / 9][i * 9 + o];
                                                        }
                                                    }
                                                }
                                                for (int o = 0; o < 9; o++) {
                                                    if (o != j / 9 && o != k / 9 && o != l / 9 && o != m / 9) {//this.char
                                                        //System.out.println("nqvertElim @ " + o + " " + (i * 9) + " " + Arrays.toString(candidates));
                                                        this.candGrid[o][i * 9 + Character.getNumericValue(candidates[0]) - 1] = '0';
                                                        this.candGrid[o][i * 9 + Character.getNumericValue(candidates[1]) - 1] = '0';
                                                        this.candGrid[o][i * 9 + Character.getNumericValue(candidates[2]) - 1] = '0';
                                                        this.candGrid[o][i * 9 + Character.getNumericValue(candidates[3]) - 1] = '0';
                                                    }
                                                }
                                                //System.out.println("++ " + Arrays.toString(candidates));
                                                for (int n = a + (c - c2) + (b - b2); n < 4; n++) {
                                                    candidates[n] = 'x';
                                                }
                                                break outerouterloop;
                                            }
                                        }
                                        //System.out.println("-- " + Arrays.toString(candidates));
                                        c = 0;
                                        c2 = 0;
                                    }
                                }
                                for (int l = a; l < 4; l++) {
                                    candidates[l] = 'x';
                                }
                                //System.out.println("- " + Arrays.toString(candidates));
                                b = 0;
                                b2 = 0;
                            }
                        }
                        candidates = new char[]{'x', 'x', 'x', 'x'};
                        a = 0;
                    }
                }
                a = 0;
                b = 0;
                b2 = 0;
                c = 0;
                c2 = 0;
                d = 0;
                d2 = 0;
            }
            count = 0;
        }
    }
    public void doHiddenQuadruples() {
        //hq @ 5
       /*
       setup{ //we want less outside while than in                 //could x^3 sudoku be applied?, also keep in mind sashimi
           caBD bs {                                               //lower limit case handling
           yes, hq before static cabd
           }
           while {
               write {
                   scB 9x1 {
                   }
                   dirB / dir S{                                   // dir s is so ridiculously strong we might have to reorder
                       B == 8?, S == 1
                   }


               }
               need 5 new generalizations before writing (look at constraint satisfaction problem, likely just academic brute force, arc consistency, obviously avoid doubles but dont forget dirs
               //not counting
               a. improve forward checking






               1. heuristics
               8. Advanced Look-Ahead Strategies
What it means: A look-ahead strategy can be employed to predict where hidden quadruples are likely to appear based on partial clues. By analyzing the Sudoku grid before making any moves, the solver can look for patterns that suggest hidden quadruples are imminent.
Optimization Tip: Before making a placement, run a prediction phase to check if a hidden quadruple exists in the grid. This helps prevent recalculating after each guess, reducing redundant computation.
9. Constraint Propagation with Subgrid Analysis
What it means: Instead of focusing solely on individual rows, columns, or blocks, the solver can analyze overlapping subgrids (a set of cells that intersect in both row and column) to detect hidden quadruples across multiple constraints at once.
Optimization Tip: Use a multi-dimensional array to track constraints across rows, columns, and subgrids. This allows faster identification of hidden quadruples that span multiple areas of the grid.
13. Look-Ahead for Pairwise and Triplet Interactions
What it means: Sometimes hidden quadruples are part of a more complex set of pairwise or triplet interactions between cells. A solver can predict these interactions ahead of time and use them to reduce candidates for hidden quadruples.
Optimization Tip: Use a look-ahead algorithm that checks for pairwise or triplet relationships between digits in cells. This can help detect hidden quadruples as part of a broader pattern of interactions.




       }


           read {
            g1{ //written horizontally
               g2{ //flag through set, if double biv, ignore
                   g3{
                       g4{


                       }
                   }
               }
            }
            //exit blacklist
           }
       }
       */




    }
}
