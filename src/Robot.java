import java.awt.*;
import java.awt.Graphics;

import java.util.ArrayList;

import java.io.*;
import javax.imageio.ImageIO;

import javax.swing.JOptionPane;

/**
 * Tigerclaw Corp. - Yuan Y. and Jaclyn W.
 * <p>
 * VK Enterprises - Ms. Krasteva
 * <p>
 * Time spent: 2 h 15 min
 * <p>
 * 
 * This class is a robot used in "Mission Glitch" of the game "CampX". It moves 
 * according to a list of commands that is sent to it from the main game panel.
 * 
 * <p>
 * <b>Class variables</b>
 * <p>
 * <b>img</b> Stores 4 views of the robot. 0 being the robot facing east, 1 south, 2 west and 3 north.
 * <p>
 * <b>dir</b> The direction that the robot is facing (0 for EAST, 1 for SOUTH, 2 for WEST, or 3 for NORTH).
 * <p>
 * <b>x</b> - The x coordinate of the robot on the grid map.
 * <p>
 * <b>y</b> The y coordinate of the robot on the grid map.
 * <p>
 * <b>unit</b> The side length of one unit on the grid map.
 * <p>
 * <b>cmds</b> The list of commands to be executed.
 * <p>
 * <b>cmdNum</b> The number of the command to be executed.
 * <p>
 * <b>repeatCounter</b> The number of times that a set of code should be repeated.
 * <p>
 * <b>repeatStart</b> The index of the first command to be repeated.
 * <p>
 * <b>repeatEnd</b> The index of the last command to be repeated.
 * <p>
 * 
 * MODIFICATION
 * <p>
 * Author    Date      Time        Modification
 * <p>
 * Yuan      Jun 2     30 min      Add endOfExecution and setCmds methods.
 * <p>
 * Yuan      Jun 3     5 min       Add getX and getY methods.
 * <p>
 * Yuan      Jun 4     1 h         Implement move method and fix comments.
 * 
 * 
 * @version 3 Jun 4, 2017
 * @author Yuan Yang
 */
public class Robot
{
  private Image[] img;
  private int dir;
  private int x;
  private int y;
  private int unit;
  private ArrayList <String> cmds;
  private int cmdNum;
  private int repeatCounter;
  private int repeatStart;
  private int repeatEnd;
  
  /** This constructor constructs a new object with initialized fields.*
    *
    * @param stageNum - The stage number (0 - 3).
    */ 
  public Robot (int stageNum)
  {
    img = new Image [4];
    try
    {
      img [0] = ImageIO.read (new File ("images/Glitch/Scout/ScoutEast.png"));
      img [1] = ImageIO.read (new File ("images/Glitch/Scout/ScoutSouth.png"));
      img [2] = ImageIO.read (new File ("images/Glitch/Scout/ScoutWest.png"));
      img [3] = ImageIO.read (new File ("images/Glitch/Scout/ScoutNorth.png"));
    }
    catch (IOException e)
    {
      JOptionPane.showMessageDialog (null, "Player image(s) not found.", "Error.", JOptionPane.ERROR_MESSAGE);
      System.exit(-1);
    }
    
    //set initial position and direction according to the stage number
    switch (stageNum)
    {
      case 0:
        dir = 2;
        x = 5;
        y = 2;
        unit = 100;
        break;
      case 1:
        dir = 2;
        x = 2;
        y = 0;
        unit = 100;
        break;
      case 2:
        dir = 1;
        x = 0;
        y = 0;
        unit = 100;
        break;
      case 3:
        dir = 2;
        x = 8;
        y = 8;
        unit = 50;
        break;
      default:
        break;
    }
    
    cmds = new ArrayList <String> ();
    cmdNum = 0;
  }
  
  /** This method takes a list of commands and sets them as commands to be exe-  
    * cuted. It also sets the repeat counter and its related variables.
    * 
    * @param cmds - The list of cmds sent to the robot from the command line text field.
    */
  public void setCmds (ArrayList <String> cmds)
  {
    //set default repeat variables
    repeatCounter = -1;
    repeatStart = -1;
    repeatEnd = -1;
    
    //set cmds
    for (int i= 0; i < cmds.size(); i++)
    {
      this.cmds.add (cmds.get(i));
      if (cmds.get(i).length() > 6 && cmds.get(i).substring (0, 6).equals ("repeat"))
      {
        repeatCounter = Integer.parseInt (cmds.get(i).substring (8, 9)) - 1;
        repeatStart = i + 1;
      }
      else
      {
        if (cmds.get(i).equals ("end"))
          repeatEnd = i - 1;
      }
    }
  }
  
  
  /** This method draws the image of the robot on the screen.
    * 
    * @param g - The Graphics object that helps draw on the screen.
    */
  public void draw (Graphics g)
  {
    g.drawImage(img [dir], 50 + x * unit, 50 + y * unit, unit, unit, null);
    
  }
  
  /** This method helps determine which aspect of the robot to change when one cmd
    * is executed.
    * <p>
    * <b>Local Variables</b>
    * <p>
    * <b>currentCmd</b> The command that is currently being executed.
    */
  public void move ()
  {
    String currentCmd;
    
    if (cmdNum < cmds.size())
    {
      if (repeatCounter != -1 && (cmdNum == repeatStart - 1 || cmdNum == repeatEnd + 1 && cmdNum != cmds.size () - 1)) //"repeat" and "end" don't count as one move
        cmdNum++;
      currentCmd = cmds.get(cmdNum);
      
      if (currentCmd.equals ("forward"))
      {
        switch (dir)
        {
          case 0:
            if (!GlitchPanel.isWall (x+1, y))
          {
            x++;
          }
            break;
          case 1:
            if (!GlitchPanel.isWall (x, y+1))
            y++;
            break;
          case 2:
            if (!GlitchPanel.isWall (x-1, y))
          {
            x--;
          }
            break;
          case 3:
            if (!GlitchPanel.isWall (x, y-1))
          {
            y--;
          }
            break;
          default:
            break;
        }
      }
      else if (currentCmd.equals ("left"))
      {
        if (dir == 0)
          dir = 3;
        else
          dir --;
      }
      else 
      {
        if (currentCmd.equals ("right"))
        {
          if (dir == 3)
            dir = 0;
          else
            dir++;
        }
      }
      
      //determine next command
      if (repeatCounter > 0 && cmdNum == repeatEnd) //jump back to start of repetition
      { 
        cmdNum = repeatStart;
        repeatCounter --;
      }
      else
      {
        cmdNum ++;
      }
    }
  }
  
  /** This method is called by the game panel class GlitchPanel to determine if 
    * execution of commands has finished.
    * 
    * @return Returns true if execution of commands has finished.
    */
  public boolean endOfExecution ()
  {
    return repeatCounter <= 0 && cmdNum == cmds.size();
  }
  
  
  /** This accessor method returns the x - position of the robot.
    * 
    * @return The x - position of the robot.
    */
  public int getX()
  {
    return x;
  }
  
  /** This accessor method returns the y - position of the robot.
    * 
    * @return The y - position of the robot.
    */
  public int getY()
  {
    return y;
  }
}