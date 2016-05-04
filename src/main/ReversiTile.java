package main;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

/**
 * Created by overlord on 08/03/16.
 */
public class ReversiTile extends CircleButton{

    protected volatile int state;

    public final int xValue;
    public final int yValue;
    public final int boardNum;

    private final Reversi Reversi;

    public ReversiTile(int x, int y, Reversi mainGame, BufferedImage tile_1, BufferedImage tile_2, boolean Alternative, int boardNum){

        //super(75, 75,null, 4, null,new Color(130,82,1), 10, null,tile_1,tile_2,Alternative);

        super(75, 75,4,10,tile_1,tile_2,Alternative,null,null);

        this.xValue =  x;
        this.yValue =  y;
        this.boardNum = boardNum;

        this.Reversi = mainGame;

        this.addActionListener(new ReversiTilePressed());
    }

    public synchronized String currentstate()
    {
        switch (state)
        {
            case 0:
                return "empty";
            case 1:
                return "black";
            case 2:
                return "white";
            default:
                return "";
        }
    }


    public synchronized void setstate(int currentstate){

        switch (currentstate)
        {
            case 0:
                setstateEmpty();
                state = 0;
                break;
            case 1:
                setstateBlack();
                state = 1;
                break;
            case 2:
                setstateWhite();
                state = 2;
                break;
            case 3:
                setstateopposite();
                state = 3;
                break;
            default:
                System.out.println("Error: " + currentstate);
        }

    }

    /**
     * When called by the switch statement this a function will change the color of the button
     * **/
    public synchronized void setstateopposite(){
        if(currentstate() == "black"){
            setCircleColor(new Color(51, 51, 51));
            setCircleBorderWidth(5);
            setCircleBorderColor(new Color(238, 238, 238));
        } else {
            setCircleColor(new Color(51, 51, 51));
            setCircleBorderWidth(5);
            setCircleBorderColor(new Color(68, 68, 68));
        }
    }

    public void setstateEmpty(){
        setinitialstate();
    }

    public void setstateWhite(){
        setCircleColor(new Color(255,252,232));
        repaint();
    }

    public void setstateBlack(){
        setCircleBorderWidth(10);
        setCircleColor(new Color(251,159,137));
    }

    public synchronized void setinitialstate(){
        setCircleColor(null);
        setCircleBorderWidth(10);
        setCircleBorderColor(null);

    }

    class ReversiTilePressed implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            Reversi.setCurrentCoods(xValue,yValue,boardNum);
        }
    }

}
