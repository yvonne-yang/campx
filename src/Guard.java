import java.awt.*;
import java.awt.Graphics;

import java.io.*;
import javax.imageio.ImageIO;

import javax.swing.JOptionPane;

/**
 * Tigerclaw Corp. - Yuan Y. and Jaclyn W.
 * <p>
 * VK Enterprises - Ms. Krasteva
 * <p>
 * Time spent: 2 h 5 min
 * 
 * This class is a guard with a flashlight used in "Mission Glitch" of the game 
 * "CampX". It may move horizontally or vertically to patrol a column/row on the
 * map.
 * 
 * <p>
 * <b>Class variables</b>
 * <p>
 * <b>img</b> Stores 4 views of the guard. 0 being the guard facing east, 1 south, 2 west and 3 north. 
 * <p>
 * <b>flashlight</b> Stores 4 views of the flashlight. 0 being the flashlight shining east, 1 south, 2 west and 3 north
 * <p>
 * <b>dir</b> The direction that the guard is facing ("EAST", "SOUTH", "WEST", or "SOUTH").
 * <p>
 * <b>x</b> The x coordinate of the guard on the grid map. This would be the fixed coordinate if the guard initially faces north or south.
 * <p>
 * <b>y</b> The y coordinate of the guard on the grid map. This would be the fixed coordinate if the guard initially faces east or west.
 * <p>
 * <b>moves</b> Whether the guard will move on the map or not.
 * <p>
 * <b>fixedCoord</b> The fixed coordinate in the guard's route.
 * <p>
 * <b>start</b> A coordinate of the unit where the guard's route starts (inclusive). 
 * <p>
 * <b>end</b> A coordinate of the unit where the guard's route ends (inclusive). 
 * <p>
 * <b>unit</b> The side length of one unit on the grid map.
 * <p>
 * 
 * MODIFICATION
 * <p>
 * Author   Date    Time      Modification
 * <p>
 * Yuan     Jun 3   15 min    Add getX, getY, getFlashlightX and getFlashlightY methods.
 * <p>
 * Yuan     Jun 4   15 min    Fix comments.
 * 
 * @version 2 Jun 4, 2017
 * @author Yuan Yang
 */

public class Guard
{
  private Image [] img;
  private Image [] flashlight;
  private int dir;
  private int x;
  private int y;
  private boolean moves;
  private char fixedCoord;
  private int start;
  private int end;
  private int unit;
  
  /** This constructor constructs a default guard object that does not move. It  
    * initializes the image variables and sets the location to (0, 0), the direc-
    * tion to east.
    */
  public Guard ()
  {
    img = new Image [4];
    flashlight = new Image [4];
    try
    {
      img [0] = ImageIO.read (new File ("images/GuardAndFlashlight/guard_east.jpg"));
      img [1] = ImageIO.read (new File ("images/GuardAndFlashlight/guard_south.jpg"));
      img [2] = ImageIO.read (new File ("images/GuardAndFlashlight/guard_west.jpg"));
      img [3] = ImageIO.read (new File ("images/GuardAndFlashlight/guard_north.jpg"));
      flashlight [0] = ImageIO.read (new File ("images/GuardAndFlashlight/flashlight_east.jpg"));
      flashlight [1] = ImageIO.read (new File ("images/GuardAndFlashlight/flashlight_south.jpg"));
      flashlight [2] = ImageIO.read (new File ("images/GuardAndFlashlight/flashlight_west.jpg"));
      flashlight [3] = ImageIO.read (new File ("images/GuardAndFlashlight/flashlight_north.jpg"));
    }
    catch (IOException e)
    {
      JOptionPane.showMessageDialog (null, "Glitch background image(s) not found.", "Error.", JOptionPane.ERROR_MESSAGE);
      System.exit(-1);
    }
    
    moves = false;
    fixedCoord = ' ';
    start = -1;
    end = -1;
    
    dir = 0;
    x = 0;
    y = 0;
    
    unit = 100;
  }
  
  /** This constructor constructs a non-moving guard object with a specified     
    * location and initial direction.
    * 
    * @param x - Specified initial x position.
    * @param y - Specified initial y position.
    * @param dir - Initial direction that the guard is facing.
    */
  public Guard (int x, int y, int dir)
  {
    this();
    this.x = x;
    this.y = y;
    this.dir = dir;
  }
  
  /** This constructor constructs a moving guard object with a location on the map
    * and direction that it is facing, as well as the start and end of its route.
    * It also sets the side length of one unit on the map.
    * 
    * @param x - Specified initial x position.
    * @param y - Specified initial y position.
    * @param dir - Initial direction that the guard is facing.
    * @param start - Position of the start of the route.
    * @param end - Position of the start of the route.
    * @param unit - The side length of one unit on the map.
    */
  public Guard (int x, int y, int dir, int start, int end, int unit)
  {
    this();
    this.x = x;
    this.y = y;
    this.dir = dir;
    
    moves = true;
    if (dir == 0 || dir == 2)
      fixedCoord = 'y';
    else
      fixedCoord = 'x';
    this.start = start;
    this.end = end;
    
    this.unit = unit;
  }
  
  /** This method draws the image of the guard on the screen.
    * 
    * @param g - The Graphics object that helps draw graphics on the screen.
    */
  public void draw (Graphics g)
  {
    //draw image of the guard
    g.drawImage(img [dir], 50 + x * unit, 50 + y * unit, unit, unit, null);
    
    //determine if flashlight is shown and draw it
    if (dir == 0 && !GlitchPanel.isWall (x + 1, y))
    {
      g.drawImage (flashlight [dir], 50 + (x + 1) * unit, 50 + y * unit, unit, unit, null);
    }
    else if (dir == 1 && !GlitchPanel.isWall (x, y + 1))
    {
      g.drawImage (flashlight [dir], 50 + x * unit, 50 + (y + 1) * unit, unit, unit, null);
    }
    else if (dir == 2 && !GlitchPanel.isWall (x - 1, y))
    {
      g.drawImage (flashlight [dir], 50 + (x - 1) * unit, 50 + y * unit, unit, unit, null);
    }
    else
    {
      if (dir == 3 && !GlitchPanel.isWall (x, y - 1))
      {
        g.drawImage (flashlight [dir], 50 + x * unit, 50 + (y - 1)* unit, unit, unit, null);
      }
    }
  }
  
  /** This method helps move the guard by one unit. 
    */
  public void move ()
  {
    if (moves)
    {
      //check if it is at the start or end of the route and is not facing the right direction
      if (fixedCoord == 'y' && (x == start && dir != 0|| x == end && dir != 2) ||
          fixedCoord == 'x' && (y == start && dir != 1|| y == end && dir != 3)
         )
      {
        if (dir == 3) //change direction (clockwise)
        {
          dir = 0;
        }
        else
        { 
          dir++;
        }
      }
      else
      {  
        //move 1 coordinate by 1
        switch (dir)
        {
          case 0:
            x++;
            break;
          case 1:
            y++;
            break;
          case 2:
            x--;
            break;
          case 3:
            y--;
            break;
          default:
            break;
        }
      }
    }
  }
  
  /** This accessor method returns the x - position of the guard.
    * 
    * @return The x - position of the guard.
    */
  public int getX()
  {
    return x;
  }
  
  /** This accessor method returns the y - position of the guard.
    * 
    * @return The y - position of the guard.
    */
  public int getY()
  {
    return y;
  }
  
  
  /** This accessor method returns the x - position of the flashlight.
    * 
    * @return The x - position of the flashlight.
    */
  public int getFlashlightX()
  {
    if (dir == 0 && !GlitchPanel.isWall (x + 1, y))
    {
      return x + 1;
    }
    else if (dir == 1 && !GlitchPanel.isWall (x, y + 1) || dir == 3 && !GlitchPanel.isWall (x, y - 1))
    {
      return x;
    }
    else if (dir == 2 && !GlitchPanel.isWall (x - 1, y))
    {
      return x - 1;
    }
    else
    {
      return - 1; //not shown
    }
  }
  
  /** This accessor method returns the y - position of the flashlight.
    * 
    * @return The y - position of the guard.
    */
  public int getFlashlightY()
  {
    if (dir == 0 && !GlitchPanel.isWall (x + 1, y) || dir == 2 && !GlitchPanel.isWall (x - 1, y))
    {
      return y;
    }
    else if (dir == 1 && !GlitchPanel.isWall (x, y + 1))
    {
      return y + 1;
    }
    else if (dir == 3 && !GlitchPanel.isWall (x, y - 1))
    {
      return y - 1;
    }
    else
    {
      return - 1; //not shown
    }
  }
}