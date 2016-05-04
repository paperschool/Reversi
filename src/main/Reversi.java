package main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by overlord on 01/05/16.
 */
public class Reversi
{

    /**
     * Declare current x and y positions
     */
    public int currentY;
    public int currentX;
    public int boardnum;

    /**
     * count number of moves taken place
     */
    public int moves;

    /**
     * Declare current player
     */
    public int player;

    /**
     * Current button state
     */
    public String state;

    public  boolean endgame = true;

    /**
     * Redeclare player label
     */
    private JLabel current_player_1;
    private JLabel current_player_2;

    private JLabel score_1;
    private JLabel score_2;

    /**
     * Declare array of tiles
     */
    private ReversiTile tiles_1[][];
    private ReversiTile tiles_2[][];

    private JButton Restart_Game_1;
    private JButton Restart_Game_2;

    private JButton AI_Move_1;
    private JButton AI_Move_2;

    final AI ai = new AI();

    /** function ran to get the current x and y values for rest of object */
    public void setCurrentCoods(int x, int y, int board)
    {
        boardnum = board;
        currentX = x;
        currentY = y;

        moves++;

        state = tiles_1[x][y].currentstate();
        System.out.println("SetCurrentCordinates: " + moves + ": " + "current x: " + currentX + " y: " + currentY + " State: " + state + " Board: " + boardnum);
    }

        /**
         * Sets the initial tiles to black or white depending on the state set.
         *
         * @param tiles_1         - This is the 2d array containing all the tiles and the data within them
         * @param current_player_1  - This current player label refers to the label declared in main and is used to show which
         *                       player currently in play
         * @param score_1           - This label is used to store the current player.
         */
    public void setupreversi(final ReversiTile tiles_1[][], final ReversiTile tiles_2[][], JLabel current_player_1, JLabel score_1, JLabel current_player_2, JLabel score_2,JButton Restart_Game_1, JButton Restart_Game_2, JButton AI_1, JButton AI_2)
    {
        this.Restart_Game_1 = Restart_Game_1;
        this.Restart_Game_2 = Restart_Game_2;

        this.AI_Move_1 = AI_1;
        this.AI_Move_2 = AI_2;

        this.current_player_1 = current_player_1;
        this.current_player_2 = current_player_2;

        this.score_1 = score_1;
        this.score_2 = score_2;

        this.tiles_1 = tiles_1;
        this.tiles_2 = tiles_2;


        Restart_Game_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startgame();
            }
        });

        Restart_Game_2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startgame();
            }
        });


        AI_Move_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (player == 1) {
                    if(ai.Start_Search_Viable("black", tiles_1)) {
                        changeplayer();
                    } else {
                        Print("No Possible Moves");
                    }
                }

            }
        });

        AI_Move_2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (player == 2) {
                    if(ai.Start_Search_Viable("white", tiles_2)) {
                        changeplayer();
                    } else {
                        Print("No Possible Moves");
                    }
                }
            }
        });

        startgame();

    }

    public void ai_test(){

        wait(100);

        while(true){


            if (endgame == false) {
                current_player_1.setText("Thank you for playing!");
                if (BlackCount() > WhiteCount()) {
                    score_1.setText("Gameover - Winner : Player 1");
                    score_2.setText("Gameover - Winner : Player 1");

                } else if(BlackCount() < WhiteCount()){
                    score_1.setText("Gameover - Winner : Player 2");
                    score_2.setText("Gameover - Winner : Player 2");
                } else {
                    score_1.setText("Gameover - Draw");
                    score_2.setText("Gameover - Draw");
                }
            }

            if(ai.Start_Search_Viable(playertostring(player), boardreturn(player))){
                Print("Move Made.");
                changeplayer();
                wait(50);
            } else {
                break;
            }
        }

    }

    public void startgame(){

        /**
         *  1 = black
         *  2 = white
         */
        player = 1;

        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                tiles_1[row][column].setstate(0);
            }
        }

        current_player_1.setText("Player One - Starts");
        score_1.setText("Black : " + 2 + " White : " + 2);

        current_player_2.setText("Player One - Starts");
        score_2.setText("Black : " + 2 + " White : " + 2);

        tiles_1[3][3].setstate(1);
        tiles_1[4][3].setstate(2);
        tiles_1[3][4].setstate(2);
        tiles_1[4][4].setstate(1);

        tiles_2 = syncboardstate(tiles_1, tiles_2);

    }

    public void changeplayer(){

        score_1.setText("Black : " + BlackCount() + " White : " + WhiteCount());
        score_2.setText("Black : " + BlackCount() + " White : " + WhiteCount());

        if(player == 1){
            current_player_1.setText("Player Two - White");
            current_player_2.setText("Player Two - White");

            tiles_2 = syncboardstate(tiles_1,tiles_2);
            player = 2;
            endgame = ai.Check_End_Game(tiles_1, "white");
        } else {
            current_player_1.setText("Player One - Black");
            current_player_2.setText("Player One - Black");

            tiles_1 = syncboardstate(tiles_2,tiles_1);
            player = 1;
            endgame = ai.Check_End_Game(tiles_1, "black");
        }
    }

    /**
     * The main loop iterates indefinitely until a winner is found, each step of the loop is checked for a button click
     * and by extension a player change.
     *

     *
     */
    public void gameloop()
    {

        /** Setting this two to -1, allows the loop to know when a button is pressed or not */
        currentY = -1;
        currentX = -1;

        /** boolean used to terminate loop when game is over */
        boolean running = true;

        do {

            score_1.setText(" Black : " + BlackCount() + " White : " + WhiteCount());
            score_2.setText(" Black : " + BlackCount() + " White : " + WhiteCount());

            /** gameover block, ran when tile count exceeds board number */
            if (endgame == false) {
                current_player_1.setText("Thank you for playing!");
                if (BlackCount() > WhiteCount()) {
                    score_1.setText("Gameover - Winner : Player 1");
                } else if(BlackCount() < WhiteCount()){
                    score_1.setText("Gameover - Winner : Player 2");
                } else {
                    score_1.setText("Gameover - Draw");
                }
                running = false;
            }


            /** Two variables that detect when a tile is clicked */
            if (currentX != -1 || currentY != -1) {
                /** running the check logical move funtion */
                if (player != boardnum){
                    if(player == 1){
                        endgame = ai.Check_End_Game(tiles_1, "white");
                        current_player_1.setText("Player One - Black.");
                        current_player_2.setText("Player Two - Not Your Turn.");
                    } else {
                        endgame = ai.Check_End_Game(tiles_2, "black");
                        current_player_1.setText("Player One - Not Your Turn.");
                        current_player_2.setText("Player Two - White.");
                    }
                } else if ((ai.checkmove(playertostring(player),boardreturn(player),currentX,currentY,true)) == 0) {
                    Print("Invalid Move Made:");
                }
                /**  if move was logical, the player is swapped to other player*/
                else{
                    changeplayer();

                }
            }

            /** button press test variables reset */
            currentY = -1;
            currentX = -1;

            wait(1);

        } while (running);

    }

    public synchronized ReversiTile[][] syncboardstate(ReversiTile Board_One[][],ReversiTile Board_Two[][]){

        int state = 0;

        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {

                if(Board_One[row][column].currentstate().equals("empty")){
                    state = 0;
                } else if(Board_One[row][column].currentstate().equals("black")){
                    state = 1;
                } else if(Board_One[row][column].currentstate().equals("white")){
                    state = 2;
                }
                Board_Two[row][column].setstate(state);
            }
        }
        return Board_Two;
    }


    /**
     * a wait helper function to pause the program when needed.
     */
    public void wait(int ms)
    {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * a print helper function for printing debug info
     */
    public void Print(String output)
    {
        System.out.println(output);
    }

    /**
     * a black tile counter function
     */
    public int BlackCount()
    {

        int blackcount = 0;

        for (int row = 0; row < 8; row++) {
            for (int j = 0; j < 8; j++) {
                if (tiles_1[row][j].currentstate() == "black") {
                    blackcount++;
                }
            }
        }

        return blackcount;
    }

    /**
     * a white tile counter function
     */
    public int WhiteCount()
    {

        int whitecount = 0;

        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                if (tiles_1[row][column].currentstate() == "white") {
                    whitecount++;
                }
            }
        }

        return whitecount;
    }

    /**
     * an occupied tile counter function
     */
    public int TileCount()
    {

        int tilecount = 0;

        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                if (!(tiles_1[row][column].currentstate() == "empty")) {
                      tilecount++;
                }
            }
        }

        if (tilecount >= 64) {
            tilecount = -1;
        }

        return tilecount;
    }

    public String playertostring(int player){

        if(player == 1){
            return "black";
        } else {
            return "white";
        }

    }

    public ReversiTile[][] boardreturn(int player){
        if(player == 1){
            return tiles_1;
        } else {
            return tiles_2;
        }

    }


    public void Debug(boolean debugon, int debugcode, int verbosity){

    }

}
