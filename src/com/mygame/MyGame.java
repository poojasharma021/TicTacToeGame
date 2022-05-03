package com.mygame;

import javax.swing.*;
import javax.swing.text.html.MinimalHTMLWriter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class MyGame extends JFrame implements ActionListener {


    JLabel heading, clockLabel;
    Font font = new Font("", Font.BOLD,40);
    JPanel mainPanel;

    JButton []btns = new JButton[9];

    // game instance variable
    int gameChances[] = {2,2,2,2,2,2,2,2,2};
    int activePlayer =0;
    int wps[][]= {
            {0, 1, 2},
            {3, 4, 5},
            {6, 7, 8},
            {0, 3, 6},
            {1,4,7},
            {2,5,8},
            {0,4,8},
            {2,4,6}


    };
    int winner;
    boolean gameOver = false;
    MyGame()
    {
        //System.out.println("Creating instance of game");
        setTitle("Tic Tac Toe Game");
        setSize(850,850);
        ImageIcon icon  = new ImageIcon("src/img/title.png");
        setIconImage(icon.getImage());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        createGUI();

        setVisible(true);
    }

    private void createGUI()
    {   this.getContentPane().setBackground(Color.BLACK);
        this.setLayout(new BorderLayout());

        //north heading

        heading = new JLabel("TIC TAC TOE");
        heading.setFont(font);
        heading.setHorizontalAlignment(SwingConstants.CENTER);
        heading.setForeground(Color.WHITE);


        this.add(heading,BorderLayout.NORTH);

        // creating object of JLabel for clock

        clockLabel = new JLabel("Clock");

        clockLabel.setFont(font);
        clockLabel.setHorizontalAlignment(SwingConstants.CENTER);
        clockLabel.setForeground(Color.WHITE);
        this.add(clockLabel,BorderLayout.SOUTH);

        Thread t = new Thread()
        {
            @Override
            public void run() {
                super.run();
                try {
                    while (true) {


                        String datetime = new Date().toLocaleString();

                        clockLabel.setText(datetime);
                        Thread.sleep(1000);
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        };
        t.start();

        ////Panel

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3,3));

        for(int i=1; i<=9;i++)
        {
            JButton btn = new JButton();
            //btn.setIcon(new ImageIcon("src/img/0.png"));
            btn.setBackground(Color.WHITE);

            btn.setFont(font);
            mainPanel.add(btn);
            btns[i-1] = btn;
            btn.addActionListener(this);
            btn.setName(String.valueOf(i-1));
        }

        this.add(mainPanel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        JButton currentbtn = (JButton) e.getSource();
        String namestr = currentbtn.getName();

        int name = Integer.parseInt(namestr.trim());

        if(gameOver)
        {
            JOptionPane.showMessageDialog(this,"Game Over");
            return;
        }

        if(gameChances[name]==2) {
            if (activePlayer == 1) {
                currentbtn.setIcon(new ImageIcon("src/img/x.png"));
                gameChances[name] = activePlayer;

                activePlayer = 0;
            } else {
                currentbtn.setIcon(new ImageIcon("src/img/0.png"));
                gameChances[name] = activePlayer;

                activePlayer = 1;
            }

            //winner finding

            for( int[]temp : wps)
            {
                if ((gameChances[temp[0]]==gameChances[temp[1]]) && (gameChances[temp[1]]==gameChances[temp[2]]) && gameChances[temp[2]]!=2) {
                    winner = gameChances[temp[0]]+1;
                    gameOver = true;
                    JOptionPane.showMessageDialog(null, "Player " + winner + " has won the game");
                    int i = JOptionPane.showConfirmDialog(this, "DO YOU WANT TO PLAY MORE?");
                    if (i == 0) {
                        this.setVisible(false);
                        new MyGame();
                    } else if (i == 1) {
                        System.exit(3476);
                    }
                    else {

                    }
                    System.out.println(i);
                    break;
                }
            }

            int count =0;

            for(int x:gameChances)
            {
                if(x==2) {
                    count++;
                    break;
                }

            }

            if(count == 0 && gameOver == false)
            {   JOptionPane.showMessageDialog(this,"GAME DRAW");
                int i = JOptionPane.showConfirmDialog(this, "DO YOU WANT TO PLAY MORE?");
                if (i == 0) {
                    this.setVisible(false);
                    new MyGame();
                } else if (i == 1) {
                    System.exit(3476);
                }
                else {

                }

                gameOver = true;
            }

        }else
            {
                JOptionPane.showMessageDialog(this, "Position already Occupied");
            }

        //System.out.println(namestr);
    }
}

