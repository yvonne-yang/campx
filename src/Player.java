import java.awt.*;
import java.awt.Graphics;

import java.io.*;
import javax.imageio.ImageIO;

import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 * Tigerclaw Corp. - Yuan Y. and Jaclyn W.
 * <p>
 * VK Enterprises - Ms. Krasteva
 * <p>
 * Time spent: 1 h 20 mins
 * <p>
 *
 * This program creates a player sprite which changes directions.
 * <p>
 * <b>Class Variables</b>
 * <p>
 * <b>playerImages</b> Creates an array of the Image class which loads images of the sprite facing different directions.
 * <p>
 * <b>dir</b> Stores the direction the sprite is facing.
 * <p>
 * <b>x</b> Stores the x-coordinate of the sprite.
 * <p>
 * <b>y</b> Stores the y-coordinate of the sprite.
 * <p>
 * 
 * MODIFICATION    Jaclyn Woon, Ver 4 June 4, 2017, Time spent: 30 mins
 * <p>
 * Jaclyn        delete useless methods, edit access levels
 * <p>
 * Jaclyn        delete testing code an replace System printing to JOptionPane dialogue
 * <p>
 * Jaclyn        JavaDoc
 * <p>
 * 
 * @version 3 June 4, 2017
 * @author Jaclyn Woon
 */
public class Player
{
  private Image[] playerImages;
  public int dir;
  private int x, y;
  
  /**
   * This constructor creates a new Player object.
   * It loads all images, sets the dirction to facing north and
   * sets the coordinates.
   */
  public Player ()
  {
    playerImages = new Image[4];
    try
    {
      playerImages[0] = ImageIO.read(new File("Images/Darwin/right.png"));
      playerImages[1] = ImageIO.read(new File("Images/Darwin/front.png"));
      playerImages[2] = ImageIO.read(new File("Images/Darwin/left.png"));
      playerImages[3] = ImageIO.read(new File("Images/Darwin/back.png"));
    }
    catch (IOException e)
    {
      JOptionPane.showMessageDialog (null, "Player image(s) not found.", "Error.", JOptionPane.ERROR_MESSAGE);
      System.exit(-1);
    }
    
    dir = 3;
    x = 484;
    y = 384;
  }
  
  /**
   * This method draws the player on the passed Graphics object, at coordinate (x, y).
   * 
   * @param g - the Graphics object to be drawn on.
   */
  public void draw (Graphics g)
  {
    g.drawImage(playerImages[dir], x, y, null);
  }
  
  /**
   * This method changes the sprite's direction.
   * 
   * @param dirStr - the desired dir as a String. Must be one of right", "left", "up" or "down".
   */
  public void changeDir(String dirStr)
  {
    if (dirStr.equals("right"))
    {
      dir = 0;
    }
    else if (dirStr.equals("down"))
    {
      dir = 1;
    }
    else if (dirStr.equals("left"))
    {
      dir = 2;
    }
    else
    {
      if (dirStr.equals("up"))
      {
        dir = 3;
      }
    }
  }
}