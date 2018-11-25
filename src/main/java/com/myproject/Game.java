package com.myproject;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Game implements ActionListener {
    //pola w klasie powinny mieć modyfikator dostępu - domyślnie teraz jest protected, sugeruję private (poczytajcie o tym)
    //pola powinny mieć nazwy, które opisują je w taki sposób, że gdy spojrzę to wiem do czego służy dany obiekt czy pole
    JFrame frame = new JFrame("Memorki");

    JPanel field = new JPanel();
    JPanel menu = new JPanel();
    JPanel menu2 = new JPanel();
    JPanel menu3 = new JPanel();
    JPanel mini = new JPanel();

    JPanel start_screen = new JPanel();
    JPanel end_screen = new JPanel();

    //nie tworzy się obiektów w ten sposób, tworzenie obiektów powinno być po kontrolą, ujęte w metody
    //do usunięcia magic number 20 i magic string Start, Wyjście itp. Najlepiej jest wyciągnąć to do zmiennych statycznych
    //
    JButton btn[] = new JButton[20];
    JButton start = new JButton("Start");
    JButton over = new JButton("Wyjscie");
    JButton easy = new JButton("Easy");
    JButton hard = new JButton("Hard");
    JButton goBack = new JButton("Powrot do menu");

    Random randomGenerator = new Random();
    private boolean purgatory = false;
    //pole nie jest używane - will be removed in next commit
    JLabel winner;
    //pole nie jest używane - will be removed in next commit
    Boolean game_over = false;
    //domyślna wartość int to 0, więc inicjalizacja jest zbędna
    int level=0;


    String[] board;
    //pole nie jest używane - will be removed in next commit
    //formatowanie tekstu, kod powinien wyglądać tak: int[] boardQ = new int[20]; łatwiej się to czyta - will be removed in next commit
    int[] boardQ=new int[20];
    Boolean shown = true;
    int temp=30;
    int temp2=30;
    String a[]=new String[10];
    boolean eh=true;

    //magic string
    private JLabel label = new JLabel("Podaj liczbę kart od 1 do 10");
    private JTextField text = new JTextField(10);

// bez modyfikatora
    public Game(){
        //do wyciągnięcia do osobnej metody
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
    //private access
    public void setUpGame(int x,Boolean what){
        level=x;
        clearMain();

        //magic int
        board = new String[2*x];
        for(int i=0;i<(x*2);i++){
            //magic string
            btn[i] = new JButton("");
            btn[i].setBackground(new Color(220, 220, 220));
            btn[i].addActionListener(this);
            btn[i].setEnabled(true);
            field.add(btn[i]);

        }

        //można zrobić z tego enuma
        String[] c = {"auto","dom","chmura","pies","serce","diament","slonce","kot","drzewo","snieszka"};
        //do osobnej metody
        if(what) a=c;

        //magic int
        for(int i=0;i<x;i++){
            for(int z=0;z<2;z++){
                while(true){
                    //magic int
                    int y = randomGenerator.nextInt(x*2);
                    if(board[y]==null){ //metoda equals zamiast ==
                        btn[y].setText(a[i]);
                        board[y]=a[i];
                        break;
                    }
                }
            }


        }
        createBoard();

    }
    //private access
    public void hideField(int x){
        //magic int i string
        for(int i=0;i<(x*2);i++){
            btn[i].setText("");
        }
        shown=false;
    }
    //magic string and private access
    public void switchSpot(int i){//this will switch the current spot to either blank or what it should have
        //stringi porównujemy metodą equals() - !DONE_STRING.equals(board[i])
        if(board[i]!="done"){//when a match is correctly chosen, it will no longer switch when pressed
            if(btn[i].getText()==""){
                btn[i].setText(board[i]);

            } else {
                //magic string
                btn[i].setText("");

            }
        }
    }
    //metoda nie jest używana - will be removed in next commit
    public void showSpot(int i){
        btn[i].setText(board[i]);
    }
    //metoda nie jest używana - will be removed in next commit
    public void showField(int x, String a[]){
        for(int i=0;i<(x*2);i++){//magic int
            btn[i].setText(a[i]);
        }
        shown=true;
    }
    //metoda nie jest używana - will be removed in next commit
    void waitABit(){

        try{
            Thread.sleep(5);
        } catch(Exception e){

        }
    }
    //boolean zwracany przez tę metodę nie jest nigdzie wykorzystywany
    public boolean checkWin(){//checks if every spot is labeled as done
        for(int i=0;i<(level*2);i++){ //magic int
            if (board[i]!="done")return false; //magic string, metoda equals zamiast !=
        }
        winner();
        return true;
    }
    //private access
    public void winner(){

        start_screen.remove(field);
        start_screen.add(end_screen, BorderLayout.CENTER);
        end_screen.add(new TextField("Wygrales"), BorderLayout.NORTH); //magic string

        end_screen.add(goBack);
        goBack.setEnabled(true);
        goBack.addActionListener(this);




    }
    //private access
    public void goToMainScreen(){
        new Game();
    }
    //private access
    public void createBoard(){
        field.setLayout(new BorderLayout());
        start_screen.add(field, BorderLayout.CENTER);

        field.setLayout(new GridLayout(5,4,2,2)); //można wyrzucić to do zmiennych
        field.setBackground(Color.black);

    }
    //private access
    public void clearMain(){
        start_screen.remove(menu);
        start_screen.remove(menu2);
        start_screen.remove(menu3);

        start_screen.revalidate();
        start_screen.repaint();
    }
    //metoda na prawie 70 linii, do zmniejszenia/rozbicia na kilka metod
    public void actionPerformed(ActionEvent click){
        Object source = click.getSource();
        if(purgatory){
            switchSpot(temp2);
            switchSpot(temp);
            temp=(level*2); //magic int
            temp2=30; //magic int
            purgatory=false; // magic boolean
        }
        if(source==start){
            try{
                level = Integer.parseInt(text.getText());
            } catch (Exception e){
                level=1; //magic int
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

        for(int i =0;i<(level*2);i++){//magic int
            if(source==btn[i]){
                if(shown){
                    hideField(level);
                }else{
                    switchSpot(i);
                    if(temp>=(level*2)){ //magic int
                        temp=i;
                    } else {
                        if((board[temp]!=board[i])||(temp==i)){ //metoda equals zamiast !=
                            temp2=i;
                            purgatory=true;

                            //tutaj jest sporo wolnych linii, dbamy o wygląd i estetykę klasy - will be removed in next commit

                        } else {
                            board[i]="done";
                            board[temp]="done";
                            checkWin();
                            temp=(level*2);
                        }

                    }
                }

            }

//ta sama uwaga ze zbędnymi liniami - will be removed in next commit
        }


    }
    public static void main(String[] args) {
        new Game();
    }

}
