package main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CircleButton extends JButton implements Runnable
{
    /** Colour of main square */
    protected Color squareColor;
    /** Width of border in pixels */
    protected int squareBorderWidth;
    /** Colour of border */
    protected Color squareBorderColor;

    /** Colour of main circle */
    protected Color circleColor;
    /** Width of border in pixels */
    protected int circleBorderWidth;
    /** Colour of border */
    protected Color circleBorderColor;

    protected boolean Alternative = false;
    protected BufferedImage tile_1 = null;
    protected BufferedImage tile_2 = null;

    protected int width;


    /** ------------ */
    /** Constructors */
    /** ------------ */

    /**
     * Constructor initialises the object - for a colour and a different coloured border
     * @param width Width of the square - preferred and min size
     * @param height Height of the square - preferred and min size
     * @param squareColor Colour of the main part of the square
     * @param squareBorderWidth Width of the square border, in pixels
     * @param squareBorderColor Colour of the square border
     * @param circleColor Colour of the main part of the circle
     * @param circleBorderWidth Width of the circle border, in pixels
     * @param circleBorderColor Colour of the circle border
     */
    public CircleButton(int width, int height,
                        Color squareColor, int squareBorderWidth, Color squareBorderColor,
                        Color circleColor, int circleBorderWidth, Color circleBorderColor)
    {
        this.squareBorderWidth = squareBorderWidth;
        this.squareColor = squareColor;
        this.squareBorderColor = squareBorderColor;

        this.circleColor = circleColor;
        this.circleBorderWidth = circleBorderWidth;
        this.circleBorderColor = circleBorderColor;

        setMinimumSize( new Dimension(width, height) );
        setPreferredSize( new Dimension(width, height) );
        setMaximumSize( new Dimension(width, height) );
    }

    /**
     * Constructor initialises the object - for a colour and a different coloured border
     * No circle border
    */
    public CircleButton(int width, int height,
                        Color squareColor, int squareBorderWidth, Color squareBorderColor,
                        Color circleColor)
    {
        // Call the other constructor with some default values
        this(width, height,
                squareColor, squareBorderWidth, squareBorderColor,
                circleColor, 0, null);
    }

    /**
     * Constructor initialises the object - for a colour and a different coloured border
     * No square border
     */
    public CircleButton(int width, int height,
                        Color squareColor,
                        Color circleColor, int circleBorderWidth, Color circleBorderColor)
    {
        // Call the other constructor with some default values
        this(width, height,
                squareColor, 0, null,
                circleColor, circleBorderWidth, circleBorderColor);
    }

    /**
     * Constructor initialises the object - for a colour and a different coloured border
     * No circle or square border
    */

    public CircleButton(int width, int height,
                        Color squareColor,
                        Color circleColor)
    {
        // Call the other constructor with some default values
        this(width, height,
                squareColor, 0, null,
                circleColor, 0, null);
    }

    public CircleButton(int width, int height,int squareBorderWidth,int circleBorderWidth,
                        BufferedImage tile_1,BufferedImage tile_2,boolean Alternative, Color squareColor,
                        Color circleColor)
    {

        this.squareBorderWidth = squareBorderWidth;
        this.circleBorderWidth = circleBorderWidth;

        this.Alternative = Alternative;

        this.width = width;

        this.tile_1 = tile_1;
        this.tile_2 = tile_2;

        this.circleColor = circleColor;
        this.squareColor = squareColor;

        setMinimumSize( new Dimension(width, height) );
        setPreferredSize( new Dimension(width, height) );
        setMaximumSize( new Dimension(width, height) );

    }

    /** ------- */
    /** Drawing */
    /** ------- */

    /**
     * This is called by the system and your code needs to draw the component
     * @param g The graphics object that the systems gives you to draw to
     */
    protected void paintComponent(Graphics g)
    {

        /** Square */
        if ( squareBorderColor != null )
        {
            g.setColor(new Color(44,72,72));
            g.fillRect(squareBorderWidth, squareBorderWidth,getWidth() - squareBorderWidth*2, getHeight() - squareBorderWidth*2);
        }

        if ( squareColor != null )
        {
            g.setColor(squareColor);
            g.fillRect(10,10,10,10);
        } else {
            if(Alternative == true){
                //g.setColor(new Color(49,135,130));
                //g.fillRect(squareBorderWidth, squareBorderWidth,getWidth() - squareBorderWidth*2, getHeight() - squareBorderWidth*2);
                g.drawImage(tile_2, 0, 0, width*3, width*3, null);
            } else{
                //g.setColor(new Color(35,98,105));
                //g.fillRect(squareBorderWidth, squareBorderWidth,getWidth() - squareBorderWidth*2, getHeight() - squareBorderWidth*2);
                g.drawImage(tile_1, 0, 0, width*3, width*3, null);
            }
        }

        Graphics2D g2d = (Graphics2D)g;
        g2d.setRenderingHint (RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        /** CircleButton */
        if (circleBorderColor != null)
        {


        }
        if (circleColor != null)
        {

            Point2D center = new Point2D.Float(getWidth()/2,getWidth()/2);

//            Color[] colors = {circleColor, Color.WHITE,circleColor};
//            float[] dist = {0.0f, 0.2f, 0.6f};
//            RadialGradientPaint p = new RadialGradientPaint(center, 25, dist, colors);

            int btnmax = getWidth();
            int btnedge = getWidth();

            g2d.setPaint(circleColor);
            g2d.fillOval(btnmax/2 - btnmax,btnmax/2 - btnmax,btnedge,btnedge); //TL
            g2d.fillOval(btnmax/2,btnmax/2 - btnmax,btnedge,btnedge); //TR
            g2d.fillOval(btnmax/2 - btnmax,btnmax/2,btnedge,btnedge); //BL
            g2d.fillOval(btnmax/2,btnmax/2,btnedge,btnedge); //BR
            g2d.fillOval(btnmax/2,btnmax/2,btnedge,btnedge); //BR


            //g2d.fillOval(circleBorderWidth, circleBorderWidth,getWidth() - circleBorderWidth*2, getHeight() - circleBorderWidth*2);
        }

    }

    /** Ask the even thread to redraw this button now */
    private void redrawSelf()
    {
        EventQueue.invokeLater(this);
    }

    /** Implemented run() in this object for passing to invokeLater() above */
    public void run()
    {
        repaint();
    }

    /** ------- */
    /** Setters */
    /** ------- */

    /** Set the colour of the square AND ask it to redraw now */
    public void setSquareColor(Color newColor)
    {
        squareColor = newColor;
        redrawSelf();
    }

    /** Set the border colour of the square AND ask it to redraw now */
    public void setSquareBorderColor(Color newColor)
    {
        squareBorderColor = newColor;
        redrawSelf();
    }

    /** Set the border width of the square AND ask it to redraw now */
    public void setSquareBorderWidth(int newWidth)
    {
        squareBorderWidth = newWidth;
        redrawSelf();
    }

    /** Set the colour of the circle AND ask it to redraw now */
    public void setCircleColor(Color newColor)
    {
        circleColor = newColor;
        redrawSelf();
    }

    /** Set the border colour of the circle AND ask it to redraw now */
    public void setCircleBorderColor(Color newColor)
    {
        circleBorderColor = newColor;
        redrawSelf();
    }

    /** Set the border width of the circle AND ask it to redraw now */
    public void setCircleBorderWidth(int newWidth)
    {
        circleBorderWidth = newWidth;
        redrawSelf();
    }

    /** Added to get rid of warning, for serialisation */
    private static final long serialVersionUID = 9041257494324082543L;
}