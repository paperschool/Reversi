package main;

/**
 * Created by Overlord Main on 02/05/2016.
 */
public class AI {

    public boolean Check_End_Game(ReversiTile tiles[][], String Curcolour){

        int temp;

        for(int row = 0; row < 8 ; row++){
            for(int column = 0; column < 8 ; column++) {
                temp = checkmove(Curcolour,tiles,row,column,false);
                if(temp > 0){
                    return true;
                }
            }
        }

        System.out.println("No More Values Found");

        return false;
    }

    public boolean Start_Search_Viable(String Curcolour, ReversiTile tiles[][]){

        int tempttiles;
        int besttiles = 0;
        int bestrow = -1;
        int bestcolumn = -1;

        for(int row = 0; row < 8 ; row++){
            for(int column = 0; column < 8 ; column++) {

                tempttiles = checkmove(Curcolour,tiles,row,column,false);
                //System.out.println(" x: " + row + " y: " + column + " Color: " + Curcolour + " TileCount: " +  tempttiles);
                if(tempttiles > besttiles){
                    besttiles = tempttiles;
                    bestrow = row;
                    bestcolumn = column;
                }
            }
        }

        if(besttiles > 0){
            checkmove(Curcolour,tiles,bestrow,bestcolumn,true);
            return true;
        }
        return false;
    }

    public int checkmove(String Curcolor, ReversiTile Board[][],int currentX, int currentY, boolean performmove) {

        /** This array contains the diagonal and cardinal directions in vector form and are applied to the 2d tile array*/
        int[] directions = {-1, -1, -1, 0, -1, 1, 0, -1, 0, 1, 1, -1, 1, 0, 1, 1};

        /** empcount checks if the tiles surrounding the selected tile are empty
         *  samecount checks if all the surrounding tiles are the same colour
         *  movemade checks if a valid move was made, allowing the players turn to end
         * */
        int empcount = 0;
        int samecount = 0;
        boolean movemade = false;

        int nettiles = 0;

        /** condition to stop users selecting already placed tiles as new tile chains */
        if (!(Board[currentX][currentY].currentstate().equals("empty"))) {
            return 0;
        }

        for (int i = 0; i < 16; i = i + 2){
            /** error checking to ensure the bounds of the board arent touched.  */
            if (currentY + directions[i + 1] < 0 || currentY + directions[i + 1] > 7 || currentX + directions[i] < 0 || currentX + directions[i] > 7) {
                continue;
            } else if (!(Board[directions[i] + currentX][directions[i + 1] + currentY].currentstate().equals("empty"))) {
                if (Board[directions[i] + currentX][directions[i + 1] + currentY].currentstate().equals(Curcolor)) {
                    if (samecount >= 7) {
                        return 0;
                    }
                    samecount++;
                }
                else {
                    nettiles += checkdirection(directions[i], directions[i + 1], Curcolor, Board,currentX,currentY,performmove);
                    if(nettiles > 0){
                        movemade = true;
                    }
                }
            }
            else {
                if (empcount >= 7) {
                    return 0;
                }
                empcount++;
            }
        }

        if (movemade) {
            return nettiles;
        }
        return 0;
    }

    public int checkdirection(int dirx, int diry, String Curcolor, ReversiTile[][] Board,int currentX,int currentY,boolean performmove)
    {

        /** Setting the start modifiers*/
        int nettiles = 0;
        int xstart = dirx * 2;
        int ystart = diry * 2;
        int Curcolorint = 0;

        if (Curcolor.equals("black")) {
            Curcolorint = 1;
        }
        else if (Curcolor.equals("white")) {
            Curcolorint = 2;
        }

        /** a boolean to break out of the main loop */
        boolean chkdir = true;

        while (chkdir) {
            if (currentY + ystart < 0 || currentY + ystart > 7 || currentX + xstart < 0 || currentX + xstart > 7) {
                return 0;
            }
            else if (Board[xstart + currentX][ystart + currentY].currentstate().equals("empty")) {
                return 0;
            }
            else if (!(Board[xstart + currentX][ystart + currentY].currentstate().equals(Curcolor))) {
                xstart += dirx;
                ystart += diry;
            }
            /** this condititon when a start and end chain is found, will change the end tiles to the player color and
             * backtrack through the tiles in that vector changing each tiles color */
            else {
                if(performmove){Board[currentX][currentY].setstate(Curcolorint);}
                do {
                    nettiles++;
                    xstart -= dirx;
                    ystart -= diry;
                    if(performmove){ Board[currentX + xstart][currentY + ystart].setstate(Curcolorint); }
                } while (currentX + xstart != currentX || currentY + ystart != currentY);
                return nettiles;
            }
        }
        return 0;
        }


}

