package com.myproject;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Game implements ActionListener {
    JFrame frame = new JFrame("Memorki");

    JPanel field = new JPanel();
    JPanel menu = new JPanel();
    JPanel menu2 = new JPanel();
    JPanel menu3 = new JPanel();
    JPanel mini = new JPanel();

    JPanel start_screen = new JPanel();
    JPanel end_screen = new JPanel();

    JButton btn[] = new JButton[20];
    JButton start = new JButton("Start");
    JButton over = new JButton("Wyjscie");
    JButton easy = new JButton("Easy");
    JButton hard = new JButton("Hard");
    JButton goBack = new JButton("Powrot do menu");

    Random randomGenerator = new Random();
    private boolean purgatory = false;
    JLabel winner;
    Boolean game_over = false;
    int level=0;


    String[] board;
    int[] boardQ=new int[20];
    Boolean shown = true;
    int temp=30;
    int temp2=30;
    String a[]=new String[10];
    boolean eh=true;

    private JLabel label = new JLabel("Podaj liczbę kart od 1 do 10");
    private JTextField text = new JTextField(10);


    public Game(){
        frame.setSize(500,300);
        frame.setLocation(500,300);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        start_screen.setLayout(new BorderLayout());
        menu.setLayout(new FlowLayout(FlowLayout.CENTER));
        menu2.setLayout(new FlowLayout(FlowLayout.CENTER));
        menu3.setLayout(new FlowLayout(FlowLayout.CENTER));
        mini.setLayout(new FlowLayout(FlowLayout.CENTER));

        start_screen.add(menu, BorderLayout.NORTH);
        start_screen.add(menu3, BorderLayout.CENTER);
        start_screen.add(menu2, BorderLayout.SOUTH);
        menu3.add(mini, BorderLayout.CENTER);
        menu.add(label);
        menu.add(text);

        start.addActionListener(this);
        start.setEnabled(true);
        menu2.add(start);
        over.addActionListener(this);
        over.setEnabled(true);
        menu2.add(over);

        frame.add(start_screen, BorderLayout.CENTER);
        frame.setVisible(true);
    }
    public void setUpGame(int x,Boolean what){
        level=x;
        clearMain();

        board = new String[2*x];
        for(int i=0;i<(x*2);i++){
            btn[i] = new JButton("");
            btn[i].setBackground(new Color(220, 220, 220));
            btn[i].addActionListener(this);
            btn[i].setEnabled(true);
            field.add(btn[i]);

        }

        String[] c = {"auto","dom","chmura","pies","serce","diament","slonce","kot","drzewo","snieszka"};
        if(what) a=c;


        for(int i=0;i<x;i++){
            for(int z=0;z<2;z++){
                while(true){
                    int y = randomGenerator.nextInt(x*2);
                    if(board[y]==null){
                        btn[y].setText(a[i]);
                        board[y]=a[i];
                        break;
                    }
                }
            }


        }
        createBoard();

    }
    public void hideField(int x){
        for(int i=0;i<(x*2);i++){
            btn[i].setText("");
        }
        shown=false;
    }
    public void switchSpot(int i){//this will switch the current spot to either blank or what it should have
        if(board[i]!="done"){//when a match is correctly chosen, it will no longer switch when pressed
            if(btn[i].getText()==""){
                btn[i].setText(board[i]);

            } else {
                btn[i].setText("");

            }
        }
    }
    public void showSpot(int i){
        btn[i].setText(board[i]);
    }
    public void showField(int x, String a[]){
        for(int i=0;i<(x*2);i++){
            btn[i].setText(a[i]);
        }
        shown=true;
    }
    void waitABit(){

        try{
            Thread.sleep(5);
        } catch(Exception e){

        }
    }
    public boolean checkWin(){//checks if every spot is labeled as done
        for(int i=0;i<(level*2);i++){
            if (board[i]!="done")return false;
        }
        winner();
        return true;
    }
    public void winner(){

        start_screen.remove(field);
        start_screen.add(end_screen, BorderLayout.CENTER);
        end_screen.add(new JLabel("Wygrales"), BorderLayout.NORTH);

        end_screen.add(goBack);
        goBack.setEnabled(true);
        goBack.addActionListener(this);




    }
    public void goToMainScreen(){
        new Game();
    }
    public void createBoard(){
        field.setLayout(new BorderLayout());
        start_screen.add(field, BorderLayout.CENTER);

        field.setLayout(new GridLayout(5,4,2,2));
        field.setBackground(Color.black);

    }
    public void clearMain(){
        start_screen.remove(menu);
        start_screen.remove(menu2);
        start_screen.remove(menu3);

        start_screen.revalidate();
        start_screen.repaint();
    }
    public void actionPerformed(ActionEvent click){
        Object source = click.getSource();
        if(purgatory){
            switchSpot(temp2);
            switchSpot(temp);
            temp=(level*2);
            temp2=30;
            purgatory=false;
        }
        if(source==start){
            try{
                level = Integer.parseInt(text.getText());
            } catch (Exception e){
                level=1;
            }

            setUpGame(level, eh);
        }
        if(source==over){
            System.exit(0);
        }

        if(source==goBack){
            frame.dispose();
            goToMainScreen();
        }
        if(source==easy){
            eh=true;
            easy.setForeground(Color.BLUE);
            hard.setForeground(Color.BLACK);
        } else if(source==hard){
            eh=false;
            hard.setForeground(Color.BLUE);
            easy.setForeground(Color.BLACK);
        }

        for(int i =0;i<(level*2);i++){
            if(source==btn[i]){
                if(shown){
                    hideField(level);
                }else{
                    switchSpot(i);
                    if(temp>=(level*2)){
                        temp=i;
                    } else {
                        if((board[temp]!=board[i])||(temp==i)){
                            temp2=i;
                            purgatory=true;


                        } else {
                            board[i]="done";
                            board[temp]="done";
                            checkWin();
                            temp=(level*2);
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
