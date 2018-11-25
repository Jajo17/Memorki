package com.myproject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Random;

public class Game implements ActionListener {

    private static final String STRING_DONE = "done";
    private static final String WELCOME_MESSAGE = "Podaj liczbę kart od 1 do 10";
    private static final String EMPTY_STRING = "";
    private static final String WIN_MESSAGE = "Wygrałeś!";
    private static final int NUMBER_OF_EQUAL_FIELDS = 2;

    private JFrame mainFrame = new JFrame("Memorki");

    private JPanel field = new JPanel();
    private JPanel menu = new JPanel();
    private JPanel menu2 = new JPanel();
    private JPanel menu3 = new JPanel();

    private JPanel start_screen = new JPanel();
    private JPanel end_screen = new JPanel();

    private JButton btn[] = new JButton[20];
    private JButton start = new JButton("Start");
    private JButton over = new JButton("Wyjscie");
    private JButton goBack = new JButton("Powrot do menu");

    private Random randomGenerator = new Random();
    private boolean purgatory = false;
    private int numberOfWordsOnBoard = 0;


    private String[] board;
    private Boolean shown = true;
    private int temp = 30;
    private int temp2 = 30;

    private JTextField textField = new JTextField(10);

    public Game() {
        mainFrame.setSize(500, 300);
        mainFrame.setLocation(500, 300);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        start_screen.setLayout(new BorderLayout());
        menu.setLayout(new FlowLayout(FlowLayout.CENTER));
        menu2.setLayout(new FlowLayout(FlowLayout.CENTER));
        menu3.setLayout(new FlowLayout(FlowLayout.CENTER));
        JPanel mini = new JPanel();
        mini.setLayout(new FlowLayout(FlowLayout.CENTER));

        start_screen.add(menu, BorderLayout.NORTH);
        start_screen.add(menu3, BorderLayout.CENTER);
        start_screen.add(menu2, BorderLayout.SOUTH);
        menu3.add(mini, BorderLayout.CENTER);
        JLabel label = new JLabel(WELCOME_MESSAGE);
        menu.add(label);
        menu.add(textField);
        start.addActionListener(this);
        start.setEnabled(true);
        menu2.add(start);
        over.addActionListener(this);
        over.setEnabled(true);
        menu2.add(over);

        mainFrame.add(start_screen, BorderLayout.CENTER);
        mainFrame.setVisible(true);
    }

    private void setUpGame(int numberOfWordsOnBoard) {
        clearMain();

        board = new String[NUMBER_OF_EQUAL_FIELDS * numberOfWordsOnBoard];
        for (int i = 0; i < (numberOfWordsOnBoard * NUMBER_OF_EQUAL_FIELDS); i++) {
            btn[i] = new JButton(EMPTY_STRING);
            btn[i].setBackground(new Color(220, 220, 220)); //white
            btn[i].addActionListener(this);
            btn[i].setEnabled(true);
            field.add(btn[i]);

        }

        List<String> boardFieldsList = BoardUtils.getBoardFields();
        for (int i = 0; i < numberOfWordsOnBoard; i++) {
            for (int j = 0; j < NUMBER_OF_EQUAL_FIELDS; j++) {
                while (true) {
                    int y = randomGenerator.nextInt(numberOfWordsOnBoard * NUMBER_OF_EQUAL_FIELDS);
                    if (board[y] == null) {
                        btn[y].setText(boardFieldsList.get(i));
                        board[y] = boardFieldsList.get(i);
                        break;
                    }
                }
            }
        }
        createBoard();
    }

    private void hideField(int x) {
        for (int i = 0; i < (x * NUMBER_OF_EQUAL_FIELDS); i++) {
            btn[i].setText(EMPTY_STRING);
        }
        shown = false;
    }

    private void switchSpot(int i) {
        if (!STRING_DONE.equals(board[i])) {
            if (EMPTY_STRING.equals(btn[i].getText())) {
                btn[i].setText(board[i]);
            } else {
                btn[i].setText(EMPTY_STRING);
            }
        }
    }

    private boolean checkWin() {
        for (int i = 0; i < (numberOfWordsOnBoard * NUMBER_OF_EQUAL_FIELDS); i++) {
            if (!STRING_DONE.equals(board[i]))
                return false;
        }
        return true;
    }

    private void winner() {
        start_screen.remove(field);
        start_screen.add(end_screen, BorderLayout.CENTER);
        end_screen.add(new TextField(WIN_MESSAGE), BorderLayout.NORTH);

        end_screen.add(goBack);
        goBack.setEnabled(true);
        goBack.addActionListener(this);
    }

    private void goToMainScreen() {
        new Game();
    }

    private void createBoard() {
        field.setLayout(new BorderLayout());
        start_screen.add(field, BorderLayout.CENTER);

        field.setLayout(new GridLayout(5, 4, 2, 2));
        field.setBackground(Color.black);
    }

    private void clearMain() {
        start_screen.remove(menu);
        start_screen.remove(menu2);
        start_screen.remove(menu3);

        start_screen.revalidate();
        start_screen.repaint();
    }

    public void actionPerformed(ActionEvent click) {
        Object source = click.getSource();

        if (purgatory) {
            switchSpot(temp2);
            switchSpot(temp);
            temp = (numberOfWordsOnBoard * NUMBER_OF_EQUAL_FIELDS);
            temp2 = 30;
            purgatory = false;
        }

        if (source == start) {
            try {
                numberOfWordsOnBoard = Integer.parseInt(textField.getText());
            } catch (Exception e) {
                numberOfWordsOnBoard = 1;
            }
            setUpGame(numberOfWordsOnBoard);
        }

        if (source == over) {
            System.exit(0);
        }

        if (source == goBack) {
            mainFrame.dispose();
            goToMainScreen();
        }

        for (int i = 0; i < (numberOfWordsOnBoard * NUMBER_OF_EQUAL_FIELDS); i++) {
            if (source == btn[i]) {
                if (shown) {
                    hideField(numberOfWordsOnBoard);
                } else {
                    switchSpot(i);
                    if (temp >= (numberOfWordsOnBoard * NUMBER_OF_EQUAL_FIELDS)) {
                        temp = i;
                    } else {
                        if ((!board[temp].equals(board[i])) || (temp == i)) {
                            temp2 = i;
                            purgatory = true;
                        } else {
                            board[i] = STRING_DONE;
                            board[temp] = STRING_DONE;
                            if (checkWin()) {
                                winner();
                            }
                            temp = (numberOfWordsOnBoard * NUMBER_OF_EQUAL_FIELDS);
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        new Game();
    }
}
