package main;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main extends JFrame { // Just the one class

    public static void drawgame() throws IOException
    {

        /** Create main window frames */
        final JFrame player_1 = new JFrame();          // Create a new frame object – top level window
        final JFrame player_2 = new JFrame();          // Create a new frame object – top level window

        /** set main window layouts  */
        player_1.setLayout(new BorderLayout(1,1));  // Setting border layout for main window
        player_2.setLayout(new BorderLayout(1,1));  // Setting border layout for main window

        /** Create game board panel */
        JPanel gameboard_1 = new JPanel();            // defining the tile board
        JPanel details_1 = new JPanel();
        JPanel controls_1 = new JPanel();

        JPanel gameboard_2 = new JPanel();            // defining the tile board
        JPanel details_2 = new JPanel();
        JPanel controls_2 = new JPanel();

        /** Set gameboard layout */
        gameboard_1.setLayout(new GridLayout(8,8));
        details_1.setLayout(new GridLayout(1,1));
        controls_1.setLayout(new GridLayout(1,1));


        gameboard_2.setLayout(new GridLayout(8,8));
        details_2.setLayout(new GridLayout(1,1));
        controls_2.setLayout(new GridLayout(1,1));

        /** Fetch tile textures */
        BufferedImage tile_1 = null;
        BufferedImage tile_2 = null;

        try {
            tile_1 = ImageIO.read(new File("res/tile_1.jpg"));
            tile_2 = ImageIO.read(new File("res/tile_2.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        /** set position of gameboard within main panel */
        player_1.add(gameboard_1, BorderLayout.CENTER);    // attaching the tile board to the main board
        player_1.add(details_1,BorderLayout.NORTH);
        player_1.add(controls_1,BorderLayout.SOUTH);

        player_2.add(gameboard_2, BorderLayout.CENTER);    // attaching the tile board to the main board
        player_2.add(details_2,BorderLayout.NORTH);
        player_2.add(controls_2,BorderLayout.SOUTH);

        /** Create buttons */
        JButton Restart_Game_1 = new JButton("Restart");
        JButton Restart_Game_2 = new JButton("Restart");

        JButton AI_1 = new JButton("AI Move");
        JButton AI_2 = new JButton("AI Move");


        AI_1.setPreferredSize(new Dimension(200,10));
        AI_2.setPreferredSize(new Dimension(200,10));

        controls_1.add(Restart_Game_1);
        controls_2.add(Restart_Game_2);

        controls_1.add(AI_1);
        controls_2.add(AI_2);

        AI_1.setBorderPainted(true);
        AI_1.setFocusPainted(false);
        AI_1.setContentAreaFilled(false);

        AI_2.setBorderPainted(true);
        AI_2.setFocusPainted(false);
        AI_2.setContentAreaFilled(false);

        Restart_Game_1.setBorderPainted(true);
        Restart_Game_1.setFocusPainted(false);
        Restart_Game_1.setContentAreaFilled(false);

        Restart_Game_2.setBorderPainted(true);
        Restart_Game_2.setFocusPainted(false);
        Restart_Game_2.setContentAreaFilled(false);


        /** Create labels */
        JLabel current_player_1 = new JLabel("");
        JLabel score_1 = new JLabel("Player 1 : 0  Player 2 : 0");

        JLabel current_player_2 = new JLabel("Player One");
        JLabel score_2 = new JLabel("Player 1 : 0  Player 2 : 0");

        /** Add labels to main panel */
        details_1.add(current_player_1);
        details_1.add(score_1);

        details_2.add(current_player_2);
        details_2.add(score_2);

        /** Label set font type face and size */
        current_player_1.setFont(new Font("Ubuntu", Font.BOLD, 18));
        current_player_1.setHorizontalAlignment( SwingConstants.CENTER );
        score_1.setFont(new Font("Ubuntu", Font.BOLD, 18));
        score_1.setHorizontalAlignment( SwingConstants.CENTER );


        current_player_2.setFont(new Font("Ubuntu", Font.BOLD, 18));
        current_player_2.setHorizontalAlignment( SwingConstants.CENTER );
        score_2.setFont(new Font("Ubuntu", Font.BOLD, 18));
        score_2.setHorizontalAlignment( SwingConstants.CENTER );

        /** Declaring the 2d array of buttons */
        ReversiTile button_p1[][] = new ReversiTile[8][8];   // Create
        ReversiTile button_p2[][] = new ReversiTile[8][8];   // Create

        /** Instancing the Reversi Object */
        Reversi ReversiStart = new Reversi();

        /** a boolean to craete the checker board pattern */
        boolean Altenative = true;

        for (int row = 0; row < 8; row++){
            Altenative ^= true;
            for(int column = 0; column < 8; column++){
                Altenative ^= true;

                button_p1[row][column] = new ReversiTile(row, column, ReversiStart,tile_1,tile_2,Altenative,1);
                button_p2[7 - row][7 - column] = new ReversiTile(7 - row,7 - column, ReversiStart,tile_1,tile_2,Altenative,2);

                gameboard_1.add(button_p1[row][column]);
                gameboard_2.add(button_p2[7 - row][7 - column]);
            }
        }


        /** Setting window properties and start position */
        player_1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close window exits
        player_1.setTitle("Reversi - Player 1"); // Set a caption/title bar content for the frame
        player_1.setLocation(30,30);

        player_1.pack();
        player_1.setVisible(true); // Display it – until you do it will not appear

        /** Setting window properties and start position */
        player_2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close window exits
        player_2.setTitle("Reversi - Player 2"); // Set a caption/title bar content for the frame
        player_2.setLocation((player_1.getBounds().width + 60) ,30);

        player_2.pack();
        player_2.setVisible(true); // Display it – until you do it will not appear

        ReversiStart.setupreversi(button_p1,button_p2,current_player_1,score_1,current_player_2,score_2,Restart_Game_1,Restart_Game_2,AI_1,AI_2);

        //ReversiStart.ai_test();
        ReversiStart.gameloop();

    }


    public static void main (String[] args) throws IOException
    {
        drawgame();
    }


}