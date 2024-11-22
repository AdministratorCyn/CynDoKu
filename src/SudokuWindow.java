import java.awt.*;
import java.util.Arrays;
import java.util.regex.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.*;
import java.io.FileWriter;
//group panels
public class SudokuWindow extends JFrame implements ActionListener, DocumentListener {
    Font font1 = new Font("JetBrains Mono", Font.PLAIN, 26);
    JTextArea ta1, ta2, ta3, ta4;
    JTextField tf1, tf2, tf3;
    JButton b1, b2, b3, b4;
    JCheckBox jcb1;
    JPanel p1, p2, p3, p4, p5;
    TitledBorder tb1, tb2, tb3, tb4;
    Border br1, br2, br3, br4, br5, br6, br7;
    JTabbedPane jt1;
    JScrollPane jsp1;
    Sudoku grid;
    int hints = 0;
    public SudokuWindow(){
        //frame
        JFrame sudokuWindow = new JFrame("GO FORMAT YOUR COMMENTS INTO A .TXT FILE");
        sudokuWindow.getContentPane().setBackground(Color.BLACK);
        sudokuWindow.setDefaultCloseOperation(EXIT_ON_CLOSE);
        //sudoku
        ta1 = new JTextArea(11, 21);
        ta1.setBounds(800, 100, 475, 575);
        ta1.setFont(new Font("JetBrains Mono", Font.PLAIN, 36));
        ta1.setBackground(Color.BLACK);
        ta1.setForeground(Color.gray);
        ta1.setEditable(true);
        ta1.setDocument(new JTextFieldLimit(242));
        ta1.getDocument().addDocumentListener(this);
        //borders
        br1 = BorderFactory.createLineBorder(Color.gray);
        br2 = BorderFactory.createLineBorder(Color.gray);
        br4 = BorderFactory.createLineBorder(Color.gray);
        br5 = BorderFactory.createLineBorder(Color.gray);
        br6 = BorderFactory.createLineBorder(Color.gray);
        br7 = BorderFactory.createLineBorder(Color.gray);
        tb1 = BorderFactory.createTitledBorder(br1, "Sudoku");
        tb2 = BorderFactory.createTitledBorder(br2, "Input Sudoku");
        tb3 = BorderFactory.createTitledBorder(br3, "Candidates");
        tb4 = BorderFactory.createTitledBorder(br4, "Console");
        tb1.setTitleFont(font1);
        tb1.setTitleColor(Color.gray);
        tb2.setTitleFont(font1);
        tb2.setTitleColor(Color.gray);
        tb3.setTitleFont(font1);
        tb3.setTitleColor(Color.gray);
        tb4.setTitleFont(font1);
        tb4.setTitleColor(Color.gray);
        //panel 1- input
        tf1 = new JTextField("");
        tf1.setBounds(1360, 90, 310, 30);
        tf1.setFont(new Font("JetBrains Mono", Font.PLAIN, 21));
        tf1.setBackground(Color.BLACK);
        tf1.setForeground(Color.gray);
        ta4 = new JTextArea();
        ta4.setBounds(800, 800, 1060, 220);
        ta4.setBackground(Color.BLACK);
        ta4.setForeground(Color.gray);
        ta4.setFont(font1);
        ta4.setBorder(tb4);
        b3 = new JButton("Hint");
        b3.setBounds(820, 700, 150, 50);
        b3.setBackground(Color.BLACK);
        b3.setForeground(Color.gray);
        b3.setFont(font1);
        b3.addActionListener(this);
        b4 = new JButton("Solve");
        b4.setBounds(1105, 700, 150, 50);
        b4.setBackground(Color.BLACK);
        b4.setForeground(Color.gray);
        b4.setFont(font1);
        b4.addActionListener(this);
        p1 = new JPanel();
        p1.setBounds(1340, 40, 350, 150);
        p1.setBackground(Color.BLACK);
        b1 = new JButton("Done");
        b1.setBounds(1360, 130, 310, 40);
        b1.setFont(new Font("JetBrains Mono", Font.PLAIN, 20));
        b1.setBackground(Color.BLACK);
        b1.setForeground(Color.gray);
        b1.addActionListener(this);
        //panel 2- candidate display
        ta2 = new JTextArea();
        ta2.setBounds(20, 50, 740, 700);
        ta2.setBackground(Color.BLACK);
        ta2.setForeground(Color.gray);
        ta2.setFont(new Font("JetBrains Mono", Font.PLAIN, 13));
        ta2.setBorder(tb3);
        ta2.setEditable(true);
        //ta2.setDocument(new JTextFieldLimit(3404));
        ta2.getDocument().addDocumentListener(this);
        //panel 5, generate (no function rn)
        p5 = new JPanel();
        //tabbed pane
        jt1 = new JTabbedPane();
        jt1.setBounds(1340, 210, 500, 465);
        jt1.setBackground(Color.BLACK);
        jt1.setForeground(Color.gray);
        jt1.setFont(font1);
        p4 = new JPanel();
        p4.setBackground(Color.BLACK);
        p4.setForeground(Color.gray);
        jcb1 = new JCheckBox("Fancy candidate grid");
        jcb1.setBackground(Color.BLACK);
        jcb1.setForeground(Color.gray);
        jcb1.addActionListener(this);
        p4.add(jcb1);
        jt1.addTab("Preferences", p4);
        jt1.setMnemonicAt(0, KeyEvent.VK_P);
        p3 = new JPanel();
        p3.setBackground(Color.BLACK);
        p3.setForeground(Color.gray);
        jt1.addTab("Technique Summary", p3);
        jt1.setMnemonicAt(1, KeyEvent.VK_T);
        p2 = new JPanel();
        p2.setLayout(new FlowLayout());
        p2.setBackground(Color.BLACK);
        p2.setForeground(Color.gray);
        jt1.addTab("Solve Path", p2);
        jt1.setMnemonicAt(2, KeyEvent.VK_S);
        ta3 = new JTextArea(16, 40);
        p2.add(ta3);
        ta3.setEditable(false);
        ta3.setBounds(1150, 240, 690, 430);
        ta3.setBackground(Color.BLACK);
        ta3.setForeground(Color.gray);
        ta3.setFont(new Font("JetBrains Mono", Font.PLAIN, 19));
        jsp1 = new JScrollPane(ta3, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jsp1.setBackground(Color.BLACK);
        jsp1.setForeground(Color.gray);
        p2.add(jsp1);
        //a
        ta1.setBorder(tb1);
        tf1.setBorder(br5);
        p1.setBorder(tb2);
        sudokuWindow.setSize(1920, 1080);
        p1.add(tf1);
        p1.add(b1);
        sudokuWindow.add(p1);
        sudokuWindow.add(tf1);
        sudokuWindow.add(b1);
        sudokuWindow.add(b3);
        sudokuWindow.add(b4);
        sudokuWindow.add(ta1);
        sudokuWindow.add(ta2);
        sudokuWindow.add(ta4);
        sudokuWindow.add(jt1);
        sudokuWindow.setLayout(null);
        sudokuWindow.setVisible(true);
        p1.setVisible(true);
    }
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b1) {
            Sudoku.iterations = 0;
            Sudoku.ns = 0;
            Sudoku.hs = 0;
            Sudoku.np = 0;
            Sudoku.hp = 0;
            Sudoku.lc1 = 0;
            Sudoku.nt = 0;
            Sudoku.ht = 0;
            Sudoku.nq = 0;
            Sudoku.nsAvg = 0;
            Sudoku.hsAvg = 0;
            Sudoku.npAvg = 0;
            Sudoku.hpAvg = 0;
            Sudoku.lc1Avg = 0;
            Sudoku.ntAvg = 0;
            Sudoku.htAvg = 0;
            Sudoku.nqAvg = 0;
            String input = tf1.getText();
            ta1.setText("");
            ta2.setText("");
            char[][] sudokuGrid = new char[9][9];
            char[][] candidateGrid = new char[9][81];
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 81; j++) {
                    candidateGrid[i][j] = Character.forDigit(((j + 1) % 9), 10);
                }
            }
            if (input.length() != 81) {
                JOptionPane.showMessageDialog(null, "Invalid sudoku");
            }
            else {
                for (int i = 0; i < input.length() / 9; i++) {
                    for (int j = 0; j < input.length() / 9; j++) {
                        char c = input.charAt(i * 9 + j);
                        if (c == '.' || c == ' ') {
                            sudokuGrid[i][j] = '0';
                        } else {
                            sudokuGrid[i][j] = c;
                        }
                    }
                }
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        for (int k = 0; k < 9; k++) {
                            if (sudokuGrid[i][j] == Character.forDigit(k, 10) || sudokuGrid[j][i] == Character.forDigit(k, 10)) {
                                candidateGrid[i][j * 9 + k] = '0';
                            }
                        }
                        for (int k = 0; k < 3; k++) {
                            for (int l = 0; l < 3; l++) {
                                if (sudokuGrid[i / 3 * 3 + k][j / 3 * 3 + l] == Character.forDigit(k, 10)) {
                                    candidateGrid[i][j * 9 + k * 3 + l] = '0';
                                }
                            }
                        }
                    }
                }
                grid = new Sudoku(sudokuGrid, candidateGrid);
                ta1.setText(grid.printSudoku());
                ta2.setText(grid.candidateSudoku());
                hints = 0;
                //ta3.setText(grid.solvePath());
            }
        }
        else if (e.getSource() == b3) {
            String hint;
            hint = grid.hint(hints);
            hints++;
            String console = ta4.getText(); //remove old lines
            console = console.concat(hint + "\n");
            Pattern pattern = Pattern.compile("(\n)");
            Matcher matcher = pattern.matcher(ta4.getText());
            int i = 0;
            while (matcher.find()) i++;
            if (i >= 5) {
                int spot = ta4.getText().indexOf("\n");
                console = console.substring(spot + 1);
            }
            ta4.setText(console);
        }
        else if (e.getSource() == b4) {
            boolean done = false;
            int steps = 0;
            while (!done && steps < 100) { //yeah go optim nq
                grid.doNakedSingles();
                if (grid.candidateSudoku().equals(ta2.getText())) {
                    ta1.setText(grid.printSudoku());
                    ta2.setText(grid.candidateSudoku());
                    grid.doHiddenSingles();
                    if (grid.candidateSudoku().equals(ta2.getText())) {
                        ta1.setText(grid.printSudoku());
                        ta2.setText(grid.candidateSudoku());
                        grid.doNakedPairs();
                        if (grid.candidateSudoku().equals(ta2.getText())) {
                            ta2.setText(grid.candidateSudoku());
                            grid.doHiddenPairs();
                            if (grid.candidateSudoku().equals(ta2.getText())) {
                                ta2.setText(grid.candidateSudoku());
                                grid.doLockedCandidates();
                                if (grid.candidateSudoku().equals(ta2.getText())) {
                                    ta2.setText(grid.candidateSudoku());
                                    grid.doNakedTriples();
                                    if (grid.candidateSudoku().equals(ta2.getText())) {
                                        ta2.setText(grid.candidateSudoku());
                                        grid.doHiddenTriples();
                                        //ta2.setText(grid.candidateSudoku());
                                        if (grid.candidateSudoku().equals(ta2.getText())) {
                                            ta2.setText(grid.candidateSudoku());
                                            grid.doNakedQuadruples();
                                            ta2.setText(grid.candidateSudoku());
                                        }
                                        else {
                                            ta2.setText(grid.candidateSudoku());
                                        }
                                    }
                                    else {
                                        ta2.setText(grid.candidateSudoku());
                                    }
                                }
                                else {
                                    ta2.setText(grid.candidateSudoku());
                                }
                            }
                            else {
                                ta2.setText(grid.candidateSudoku());
                            }
                        }
                        else {
                            ta2.setText(grid.candidateSudoku());
                        }
                    }
                    else {
                        ta1.setText(grid.printSudoku());
                        ta2.setText(grid.candidateSudoku());
                    }
                }
                else {
                    ta1.setText(grid.printSudoku());
                    ta2.setText(grid.candidateSudoku());
                }
                outerloop:
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        if (grid.charGrid[i][j] == '0') {
                            done = false;
                            break outerloop;
                        }
                        else if (i == 8 && j == 8) {
                            done = true;
                        }
                    }
                }
                steps++;
                if (steps >= 100) {
                    String console = ta4.getText(); //remove old lines
                    console = console.concat("SOLVER FAILSAFE" + "\n");
                    Pattern pattern = Pattern.compile("(\n)");
                    Matcher matcher = pattern.matcher(ta4.getText());
                    int i = 0;
                    while (matcher.find()) i++;
                    if (i >= 5) {
                        int spot = ta4.getText().indexOf("\n");
                        console = console.substring(spot + 1);
                    }
                    ta4.setText(console);
                }
            }
            System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.println("Total iterations: " + Sudoku.iterations);
            if(Sudoku.ns > 0) {
                System.out.println("Naked Single: called " + Sudoku.ns + " times, " + Sudoku.nsAvg + " total iterations, " + (Sudoku.nsAvg / Sudoku.ns) + " average iterations");
            }
            if(Sudoku.hs > 0) {
                System.out.println("Hidden Single: called " + Sudoku.hs + " times, " + Sudoku.hsAvg + " total iterations, " + (Sudoku.hsAvg / Sudoku.hs) + " average iterations");
            }
            if(Sudoku.np > 0) {
                System.out.println("Naked Pair: called " + Sudoku.np + " times, " + Sudoku.npAvg + " total iterations, " + (Sudoku.npAvg / Sudoku.np) + " average iterations");
            }
            if(Sudoku.hp > 0) {
                System.out.println("Hidden Pair: called " + Sudoku.hp + " times, " + Sudoku.hpAvg + " total iterations, " + (Sudoku.hpAvg / Sudoku.hp) + " average iterations");
            }
            if(Sudoku.lc1 > 0) {
                System.out.println("Locked Candidate (Type 1): called " + Sudoku.lc1 + " times, " + Sudoku.lc1Avg + " total iterations, " + (Sudoku.lc1Avg / Sudoku.lc1) + " average iterations");
            }
            if(Sudoku.nt > 0) {
                System.out.println("Naked Triple: called " + Sudoku.nt + " times, " + Sudoku.ntAvg + " total iterations, " + (Sudoku.ntAvg / Sudoku.nt) + " average iterations");
            }
            if(Sudoku.ht > 0) {
                System.out.println("Hidden Triple: called " + Sudoku.ht + " times, " + Sudoku.htAvg + " total iterations, " + (Sudoku.htAvg / Sudoku.ht) + " average iterations");
            }
            if(Sudoku.nq > 0) {
                System.out.println("Naked Quadruple: called " + Sudoku.nq + " times, " + Sudoku.nqAvg + " total iterations, " + (Sudoku.nqAvg / Sudoku.nq) + " average iterations");
            }
            //ta1.setText(grid.printSudoku());
            ta2.setText(grid.candidateSudoku());
            ta1.setText(grid.printSudoku());
        }
    }
    @Override
    public void insertUpdate(DocumentEvent e) {
        if (ta1.getText().length() == 242 && e.getDocument() == ta1.getDocument() && ta2.getText().isEmpty()) {
            grid = new Sudoku(grid.retrieveSudoku(ta1.getText()));
            hints = 0;
            ta2.setText(grid.candidateSudoku());
        }
        else if (ta1.getText().length() == 242 && e.getDocument() == ta1.getDocument()) {
            grid.candGrid = grid.autoCand(grid.retrieveCandSudoku(ta2.getText()));
            grid.charGrid = grid.retrieveSudoku(ta1.getText());
            hints = 0;
            ta2.setText(grid.candidateSudoku());
            //ta1.setText(grid.printSudoku());
        }
        if (e.getDocument() == ta2.getDocument()) {
            grid.candGrid = grid.retrieveCandSudoku(ta2.getText());
            grid.charGrid = grid.retrieveSudoku(ta1.getText());
            //ta2.setText(grid.candidateSudoku());
            //ta1.setText(grid.printSudoku());
        }
    }
















    @Override
    public void removeUpdate(DocumentEvent e) {
















    }
















    @Override
    public void changedUpdate(DocumentEvent e) {
















    }
}
