import java.awt.*;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;

import java.io.*;
import javax.imageio.ImageIO;
import javafx.scene.media.*;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.Timer;
import javax.swing.JOptionPane;

/**
 * Tigerclaw Corp. - Yuan Y. and Jaclyn W.
 * <p>
 * VK Enterprises - Ms. Krasteva
 * <p>
 * Time spent: 9 h 25 min
 * <p>
 * 
 * This class is the "Mission Glitch" level of the CampX game on a JPanel which can be added to the main window.
 * 
 * <p>
 * <b>Class Variables</b>
 * <p>
 * <b>background</b> The background image.
 * <p>
 * <b>maps</b> An array of map images for each stage of the mission.
 * <p>
 * <b>intMap</b> An int representation of the map for the current stage.
 * <p>
 * <b>stage</b> The stage number. (0 - 3)
 * <p>
 * <b>tries</b> The number of tries that the player gets each stage (3).
 * <p>
 * <b>scouter</b> The moving image of the robot.
 * <p>
 * <b>guards</b> A list of guards.
 * <p>
 * <b>cmdLnBox</b> The JTextArea object that displays the commands.
 * <p>
 * <b>maxCmdNum</b> The maximum number of commands needed for one stage.
 * <p>
 * <b>cmdLnText</b> A list of strings, each being one line of command in the command line text area.
 * <p>
 * <b>menuButton</b> A CustomButton object that goes to the game menu.
 * <p>
 * <b>goButton</b> A CustomButton object that starts the animation.
 * <p>
 * <b>leftButton</b> A CustomButton object that would make the robot turn left.
 * <p>
 * <b>rightButton</b> A CustomButton object that would make the robot turn right.
 * <p>
 * <b>forwardButton</b> A CustomButton object that would make the robot move 1 unit forward.
 * <p>
 * <b>repeatButton</b> A CustomButton object that repeats executing the set of commands until "end".
 * <p>
 * <b>endButton</b> A CustomButton object that indicates the end of the commands that should be executed repeatedly.
 * <p>
 * <b>deleteButton</b> A CustomButton object that deletes the last command entered.
 * <p>
 * <b>processingCmds</b> True if commands are being processed and the animation is displayed.
 * <p>
 * MODIFICATIONS
 * <p>
 * Author    Date      Time          Modification
 * <p>
 * Yuan      Jun 1     1 h 15 min    Fix nextStage, implement paintComponent, add and implement lostOrWon.
 * <p>
 * Yuan      Jun 2     3 h           Add resetGuardAndRobot, fix isWall, implement run method and test.
 * <p>
 * Yuan      Jun 3     2 h           Delete lostOrWon method, fix and implement actionPerformed, fix paintComponent, implement complete.
 * <p>
 * Yuan      Jun 4     1 h           Fix instructions, fix complete and fail, fix actoinPerformed, fix music, fix Javadoc.
 * 
 * @version 4 Jun 4, 2017
 * @author Yuan Yang
 */

public class GlitchPanel extends GamePanel implements ActionListener
{
  private Image background;
  private Image[] maps;
  private static int [][] intMap;
  private int stage = 0;
  private int tries = 0;
  private Robot scouter;
  private ArrayList<Guard> guards = new ArrayList<Guard>();
  private JTextArea cmdLnBox;
  private int maxCmdNum;
  private ArrayList <String> cmdLnText;
  private CustomButton menuButton;
  private CustomButton goButton;
  private CustomButton leftButton;
  private CustomButton rightButton;
  private CustomButton forwardButton;
  private CustomButton deleteButton;
  private CustomButton repeatButton;
  private CustomButton endButton;
  private boolean processingCmds;
  
  /** This constructor constructs a new GlitchPanel object and initializes all fields.
    */
  public GlitchPanel()
  {
    super (1200000); //20 minutes given
    setBounds(0, 0, 1000, 800);
    setLayout (null); //absolute layouts
    setFocusable(true); //automatically "selects" panel - no need to click on screen first
    
    //music
    mPlayer = new MediaPlayer (new Media(new File("Music/glitchTheme.mp3").toURI().toString()));
    
    //instructions panel and gameMenu
    instructionsPanel = new InstructionsPanel ("Images/Glitch/Instructions/", 2, this);
    gameMenu = new GameMenu(this, instructionsPanel, mPlayer);
    
    //load all images
    try
    {
      background = ImageIO.read (new File ("Images/Glitch/background.jpg"));
      maps = new Image [4];
      for (int i = 0; i < maps.length; i++)
      {
        maps [i] = ImageIO.read (new File ("Images/Glitch/StageMaps/stage" + (i + 1) + ".jpg"));
      }
    }
    catch (IOException e)
    {
      JOptionPane.showMessageDialog (null, "Glitch background image(s) not found.", "Error.", JOptionPane.ERROR_MESSAGE);
      System.exit(-1);
    }
    
    //initialize text area and buttons
    cmdLnBox = new JTextArea (20, maxCmdNum);
    menuButton = new CustomButton ("Images/GameMenu/gameMenuButton.jpg");
    goButton = new CustomButton ("Images/Glitch/goButton.jpg");
    leftButton = new CustomButton ("Images/Glitch/leftButton.jpg");
    rightButton = new CustomButton ("Images/Glitch/rightButton.jpg");
    forwardButton = new CustomButton ("Images/Glitch/forwardButton.jpg");
    repeatButton = new CustomButton ("Images/Glitch/repeatButton.jpg");
    endButton = new CustomButton ("Images/Glitch/endButton.jpg");
    deleteButton = new CustomButton ("Images/Glitch/deleteButton.jpg");
    
    //set text area properties
    cmdLnBox.setFont (new Font ("Courier", 1, 20));
    cmdLnBox.setBackground (Color.BLACK);
    cmdLnBox.setForeground (Color.GREEN);
    cmdLnBox.setCaretColor (Color.GREEN);
    cmdLnBox.setEditable (false);
    
    //set text area and button locations
    cmdLnBox.setBounds (700, 100, 280, 380);
    menuButton.setBounds (900, 10, menuButton.getWidth(), menuButton.getHeight());
    leftButton.setBounds (680, 500, leftButton.getWidth(), leftButton.getHeight());
    rightButton.setBounds (920, 500, rightButton.getWidth(), rightButton.getHeight());
    forwardButton.setBounds (760, 520, forwardButton.getWidth(), forwardButton.getHeight());
    repeatButton.setBounds (700, 600, repeatButton.getWidth(), repeatButton.getHeight());
    endButton.setBounds (870, 600, endButton.getWidth(), endButton.getHeight());
    goButton.setBounds (690, 680, goButton.getWidth(), goButton.getHeight());
    deleteButton.setBounds (920, 680, deleteButton.getWidth(), deleteButton.getHeight());
    
    //listeners
    menuButton.addActionListener (this);
    goButton.addActionListener (this);
    leftButton.addActionListener (this);
    rightButton.addActionListener (this);
    forwardButton.addActionListener (this);
    deleteButton.addActionListener (this);
    repeatButton.addActionListener (this);
    endButton.addActionListener (this);
  }
  
  @Override
  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);
    
    //variables for drawing the target
    int targetX = 0, targetY = 0;
    Image target = null;
    try{
      target = ImageIO.read (new File ("Images/Glitch/destination.jpg"));
    }
    catch (IOException e){}
    
    //draw background and map
    g.drawImage (background, 0, 0, null);
    g.drawImage (maps [stage], 50, 50, null);
    
    //draw max number of commands
    g.setFont (new Font ("Georgia", 0, 35));
    g.setColor (Color.red);
    g.drawString (maxCmdNum + "", 700, 70);
    g.setFont (new Font ("Georgia", 0, 15));
    g.drawString ("commands needed ", 710, 95);
    
    //draw tries
    g.setFont (new Font ("Georgia", 0, 35));
    g.setColor (Color.yellow);
    g.drawString (tries + "", 850, 70);
    g.setFont (new Font ("Georgia", 0, 15));
    if (tries != 1)
    {
      g.drawString ("tries left", 850, 95);
    }
    else
    {
      g.drawString ("try left", 850, 95);
    }
    
    //display commands
    String allCmd = "";
    for (int i = 0; i < cmdLnText.size(); i++)
      allCmd = allCmd + "\n  >" + cmdLnText.get (i);
    cmdLnBox.setText (allCmd);
    
    //draw target
    switch (stage)
    {
      case 0:
        targetX = 2;
        targetY = 5;
        break;
      case 1:
        targetX = 3;
        targetY = 5;
        break;
      case 2:
        targetX = 5;
        targetY = 5;
        break;
      case 3:
        targetX = 8;
        targetY = 11;
        break;
      default:
        break;
    }
    if (stage != 3)
    {
      g.drawImage (target, 50 + 100 * targetX, 50 + 100 * targetY, null);
    }
    else
    {
      g.drawImage (target, 50 + 50 * targetX, 50 + 50 * targetY, 50, 50, null);
    }
    
    //draw guards and robot
    for (Guard gd : guards)
    {
      gd.draw (g);
    }
    scouter.draw (g);
  }
  
  @Override
  public void actionPerformed(ActionEvent ae) {
    if (ae.getSource() == menuButton)
    {
      pause();
      add(gameMenu);
      repaint();
    }
    else if (ae.getSource() == forwardButton)
    {
      if (cmdLnText.size() == maxCmdNum)
      {
        JOptionPane.showMessageDialog (this, "You don't need any more commands!", "Error.", JOptionPane.ERROR_MESSAGE);
      }
      else
      {
        cmdLnText.add (new String ("forward"));
        repaint();
      }
    }
    else if (ae.getSource() == leftButton)
    {
      if (cmdLnText.size() == maxCmdNum)
      {
        JOptionPane.showMessageDialog (this, "You don't need any more commands!", "Error.", JOptionPane.ERROR_MESSAGE);
      }
      else
      {
        cmdLnText.add (new String ("left"));
        repaint();
      }
    }
    else if (ae.getSource() == rightButton)
    {
      if (cmdLnText.size() == maxCmdNum)
      {
        JOptionPane.showMessageDialog (this, "You don't need any more commands!", "Error.", JOptionPane.ERROR_MESSAGE);
      }
      else
      {
        cmdLnText.add (new String ("right"));
        repaint();
      }
    }
    else if (ae.getSource() == repeatButton)
    {
      if (cmdLnText.size() == maxCmdNum)
      {
        JOptionPane.showMessageDialog (this, "You don't need any more commands!", "Error.", JOptionPane.ERROR_MESSAGE);
      }
      else
      {
        int numOfTimes = -1;
        while (true)
          try{
          numOfTimes = Integer.parseInt (JOptionPane.showInputDialog (this,"Repeat how many times?","Repeat (?)", JOptionPane.PLAIN_MESSAGE));
          if (numOfTimes <= 0 || numOfTimes >= 10)
          {
            JOptionPane.showMessageDialog (this, "Please enter a positive integer smaller than 10.", "Error.", JOptionPane.ERROR_MESSAGE);
          }
          else
          {
            break;
          }
        }
        catch (NumberFormatException e)
        {
          JOptionPane.showMessageDialog (this, "Please enter a positive integer smaller than 10.", "Error.", JOptionPane.ERROR_MESSAGE);
        }
        cmdLnText.add (new String ("repeat (" + numOfTimes + ")"));
        repaint();
      }
    }
    else if (ae.getSource() == endButton)
    {
      if (cmdLnText.size() == maxCmdNum)
      {
        JOptionPane.showMessageDialog (this, "You don't need any more commands!", "Error.", JOptionPane.ERROR_MESSAGE);
      }
      else
      {
        cmdLnText.add (new String ("end"));
        repaint();
      }
    }
    else if (ae.getSource() == deleteButton)
    {
      if (cmdLnText.size() == 0)
      {
        JOptionPane.showMessageDialog (this, "There's nothing to delete!", "Error.", JOptionPane.ERROR_MESSAGE);
      }
      else
      {
        cmdLnText.remove (cmdLnText.size() - 1);
        repaint();
      }
    }
    else if (ae.getSource() == goButton)
    {
      run();
    }
    else if (ae.getSource() == frameUpdater)
    {
      if (intMap [scouter.getY()][scouter.getX()] == 2) //won
      {
        goButton.setEnabled (true);
        frameUpdater.stop();
        if (stage < 3)
        {
          nextStage();
          repaint();
          if (stage == 2)
            JOptionPane.showMessageDialog (this, "Beware! Guards will now start moving!", "Information", JOptionPane.PLAIN_MESSAGE);
        }
        else
          complete();
        return;
      }
      else
      {
        for (Guard gd : guards) //lost: caught by guard!
        {
          if (scouter.getX() == gd.getX() && scouter.getY() == gd.getY() ||
              scouter.getX() == gd.getFlashlightX() && scouter.getY() == gd.getFlashlightY())
          {
            JOptionPane.showMessageDialog (this, "You're caught by a guard!", "Game over.", JOptionPane.PLAIN_MESSAGE);
            fail();
            return;
          }
        }
        //disable go button
        goButton.setEnabled (false);
        
        if (!scouter.endOfExecution())
        {
          processingCmds = true;
          scouter.move();
          for (int i = 0; i < guards.size(); i++)
          {
            guards.get (i).move();
          }
          repaint();
        }
        else //end of execution
        {
          frameUpdater.stop();
          processingCmds = false;
          goButton.setEnabled (true);
          resetGuardAndRobot (stage);
          repaint();
          if (tries == 0) //lost: no tries left
          {
            JOptionPane.showMessageDialog (this, "No more tries left!", "Game over.", JOptionPane.PLAIN_MESSAGE);
            fail();
            return;
          }
        }
      }
    }
  }
  
  @Override
  public void startNewGame()
  {
    pause();
    //play music
    mPlayer.play();
    //initialize frame updater and processingCmds
    frameUpdater = new Timer (1000, this); //no need for a very fast updater
    processingCmds = false;
    //enable go button
    goButton.setEnabled(true);
    //initialize stage-related variables
    stage = -1;
    cmdLnText = new ArrayList <String>();
    guards = new ArrayList <Guard>();
    nextStage();
    //show instructions
    instructionsPanel.resetControlPanel(this);
    showInstructions();
    repaint();
  }
  
  @Override
  public void endInstructions()
  {
    remove(instructionsPanel);
    if (instructionsPanel.menu == null)
    {
      instructionsPanel.changeControlPanel(gameMenu);
    }
    unPause();
    totalPauseTime = 0;
    startTime = System.currentTimeMillis();
    repaint();
  }
  
  @Override
  public void pause()
  {
    //remove all JComponents
    remove (menuButton);
    remove (cmdLnBox);
    remove (goButton);
    remove (deleteButton);
    remove (forwardButton);
    remove (leftButton);
    remove (rightButton);
    remove (repeatButton);
    remove (endButton);
    
    //stop frame updater
    if (processingCmds)
      frameUpdater.stop();
    
    //record start of pausing
    pauseTime = System.currentTimeMillis();
    
    repaint();
  }
  
  @Override
  public void unPause()
  {
    //add all JComponents
    add (cmdLnBox);
    add (menuButton);
    add (goButton);
    add (leftButton);
    add (rightButton);
    add (forwardButton);
    add (repeatButton);
    add (endButton);
    add (deleteButton);
    
    //start frame updater if paused when in the process of execution
    if (processingCmds)
      frameUpdater.start();
    
    //add to totalPauseTime
    totalPauseTime = System.currentTimeMillis() - pauseTime;
    
    repaint();
  }
  
  /** This method gives control back to the driver when the game is won.
    */
  public void complete ()
  {
    int score;
    
    //stop music player
    mPlayer.stop();
    
    //store score
    score = getTimeLeft();
    CampXApp.glitchComplete(score);
  }
  
  @Override
  public void fail()
  {
    //stop music player
    mPlayer.stop();
    //stop frame updater
    frameUpdater.stop();
    //mission failed screen
    Cutscene failScene = new Cutscene ("Images/Glitch/Fail", -1);
    pause();
    CampXApp.frame.getContentPane().remove(this);
    CampXApp.frame.add(failScene);
    CampXApp.frame.repaint();
    failScene.start();
    
  }
  
  /** This method checks if a certain position on the map is illegal for moving 
    * objects to be placed, that is, the position is a wall or is the border of 
    * the map.
    * 
    * @param x - The x position of the unit to be checked.
    * @param y - The y position of the unit to be checked.
    * @return Returns true if the specified location is a wall/border of the map.
    */
  public static boolean isWall (int x, int y)
  {
    if (x < 0 || x >= intMap [0].length 
          || y < 0 || y >= intMap.length
          || intMap [y][x] == 1)
    {
      return true;
    }
    return false;
  }
  
  /** This method helps initialize/reset variables for each stage.
    * <p>
    * <b>Local Variables</b>
    * <p>
    * <b>tempMap</b> Stores a temporary int representaion of the stage 1 map for
    * easier initialization and better readability.
    * <p>
    * <b>tempMap2</b> Stores a temporary int representaion of the stage 2 map for
    * easier initialization and better readability.
    * <p>
    * <b>tempMap3</b> Stores a temporary int representaion of the stage 3 map for
    * easier initialization and better readability.
    * <p>
    * <b>tempMap4</b> Stores a temporary int representaion of the stage 4 map for
    * easier initialization and better readability.
    */
  private void nextStage ()
  {
    stage++;
    tries = 3; //reset tries
    cmdLnText.clear(); //reset cmds
    resetGuardAndRobot(stage); //reset guard and robot
    //determine number of commands needed, int representation of map, and guards
    switch (stage)
    {
      case 0:
        maxCmdNum = 7;
        int [][] tempMap = {
          {1, 1, 0, 1, 1, 1},
          {1, 1, 1, 1, 1, 1},
          {1, 0, 0, 0, 0, 0},
          {1, 1, 0, 1, 1, 1},
          {1, 1, 0, 1, 0, 0},
          {0, 1, 2, 1, 0, 0}
        };
        intMap = tempMap;
        break;
      case 1:
        maxCmdNum = 13;
        int [][] tempMap2 = {
          {1, 1, 0, 1, 1, 0},
          {1, 1, 0, 1, 1, 0},
          {0, 0, 0, 0, 0, 1},
          {1, 1, 1, 1, 0, 1},
          {0, 0, 0, 0, 0, 1},
          {1, 1, 1, 2, 1, 1}
        };
        intMap = tempMap2;
        break;
      case 2:
        maxCmdNum = 6;
        int [][] tempMap3 = {
          {0, 1, 1, 1, 1, 1},
          {0, 0, 0, 0, 0, 1},
          {1, 0, 0, 1, 1, 1},
          {0, 0, 0, 0, 0, 1},
          {1, 1, 1, 0, 0, 1},
          {1, 1, 1, 1, 0, 2}
        };
        intMap = tempMap3;
        break;
      case 3:
        maxCmdNum = 10;
        int [][] tempMap4 = {
          {1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1},
          {1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1},
          {1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1},
          {1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1},
          {1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1},
          {1, 1, 0, 1, 1, 0, 0, 0, 0, 1, 1, 1},
          {1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1},
          {1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1},
          {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
          {1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1},
          {1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1},
          {1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1},
        };
        intMap = tempMap4;
        break;
      default:
        break;
    }
  }
  
  /** This method helps reset the positions of the robot and the guards.
    * 
    * @param stageNum - The stage number. (0 - 3)
    */
  private void resetGuardAndRobot (int stageNum)
  { 
    //reset robot
    scouter = new Robot (stageNum);
    
    //reset guards
    guards.clear();
    switch (stageNum)
    {
      case 0:
        //not-moving
        guards.add (new Guard (2, 0, 3));
        guards.add (new Guard (4, 4, 1));
        guards.add (new Guard (0, 5, 2));
        break;
      case 1:
        //not-moving
        guards.add (new Guard (5, 1, 3));
        guards.add (new Guard (0, 2, 0));
        guards.add (new Guard (0, 4, 0));
        break;
      case 2:
        //moving
        guards.add (new Guard (2, 1, 0, 0, 4, 100));
        guards.add (new Guard (0, 3, 0, 0, 4, 100));
        break;
      case 3:
        //moving
        guards.add (new Guard (11, 8, 2, 0, 11, 50));
        guards.add (new Guard (8, 10, 3, 0, 11, 50));
        break;
      default:
        break;
    }
    
  }
  
  /** This method helps run the commands given to the robot.
    * <p>
    * <b>Local Variables</b>
    * <p>
    * <b>numOfRepeats</b> The total number of the command "repeat".
    * <p>
    * <b>indOfRepeat</b> The position of the "repeat" command.
    */
  private void run()
  {
    int numOfRepeats = 0, indOfRepeat = -1;
    
    tries--;
    
    //errortrap commands
    if (cmdLnText.size() <= 0)
    {
      JOptionPane.showMessageDialog (this, "Press a command button to write code.", "Error.", JOptionPane.ERROR_MESSAGE);  
    }
    else
    {
      for (int i = 0; i < cmdLnText.size(); i++) //count total number of "repeat"s
      {
        if (cmdLnText.get(i).length() >= 6 && cmdLnText.get(i).substring (0, 6).equals ("repeat"))
        {
          numOfRepeats++;
          indOfRepeat = i;
        }
        if (numOfRepeats > 1)
          break;
      }
      if (numOfRepeats > 1) //invalid: more than one repeat
      {
        JOptionPane.showMessageDialog (this, "You should need no more than one \"repeat\" command.", "Error.", JOptionPane.ERROR_MESSAGE);
      }
      else
      {
        if (numOfRepeats > 0 && !cmdLnText.contains ("end") || indOfRepeat > cmdLnText.indexOf ("end"))
        {
          JOptionPane.showMessageDialog (this, "A \"repeat\" command must be followed by an \"end\".", "Error.", JOptionPane.ERROR_MESSAGE);
        }
        else
        {
          if (numOfRepeats <= 0 && cmdLnText.contains ("end"))
          {
            JOptionPane.showMessageDialog (this, "An \"end\" only works with a \"repeat\".", "Error.", JOptionPane.ERROR_MESSAGE);
          }
          else
          {
            if (indOfRepeat == cmdLnText.indexOf ("end") - 1)
            {
              JOptionPane.showMessageDialog (this, "Try putting something between the \"repeat\" and the \"end\".", "Error.", JOptionPane.ERROR_MESSAGE);
            }
            else
            {
              scouter.setCmds (cmdLnText);
              frameUpdater.start();
            }
          }
        }
      }
    }
  }
}