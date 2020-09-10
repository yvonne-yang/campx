import java.awt.*;
import java.awt.Graphics;

import javafx.scene.media.*;

import java.io.*;
import javax.imageio.ImageIO;

import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.JOptionPane;

/**
 * Tigerclaw Corp. - Yuan Y. and Jaclyn W.
 * <p>
 * VK Enterprises - Ms. Krasteva
 * <p>
 * Time spent: 1 h 30 mins
 * <p>
 * 
 * This program is a JPanel created to be a game. It has a game background image, a frame updater,
 * a game menu, start time, pause time and totl paused time. It can display the instructions,
 * start a new game, pause, resume and end the game.
 * 
 * <p>
 * <b>Class Variables</b>
 * <p>
 * <b>frameUpdater</b> creates an instance of the Timer class.
 * <p>
 * <b>instructionsPanel</b> creates an instance of the InstructionsPanel class.
 * <p>
 * <b>background</b> creates an instance of the Image class.
 * <p>
 * <b>startTime</b> stores a long of the time when the game is started in milliseconds.
 * <p>
 * <b>pauseTime</b> stores a long of the time when the game is paused in milliseconds.
 * <p>
 * <b>totalPauseTime</b> stores a long of the total time the game has been paused for in milliseconds.
 * <p>
 * <b>gameMenu</b> creates an instance of the GameMenu class.
 * <p>
 * <b>mPlayer</b> This creates an instance of the MediaPlayer class.
 * <p>
 * <b>TIME_GIVEN</b> This is a final int that determines how much time the player is allocated. Stored in milliseconds.
 * <p>
 * 
 * MODIFICATION: Jaclyn Woon, Ver 4 June 4, 2017, Time spent: 20 mins
 * <p>
 * Jaclyn        delete testing code an replace System printing to JOptionPane dialogue
 * <p>
 * Jaclyn        edit JavaDoc
 * <p>
 * 
 * @version 4 June 4, 2017
 * @author Jaclyn Woon
 */
public abstract class GamePanel extends JPanel
{
  Timer frameUpdater;
  InstructionsPanel instructionsPanel;
  Image background;
  long startTime;
  long pauseTime;
  long totalPauseTime;
  GameMenu gameMenu;
  MediaPlayer mPlayer;
  final int TIME_GIVEN;
  
  /**
   * This constructor is used to create a new instance of this class.
   * It sets the layout to null for absolute positioning and makes the panel actomatically selected.
   * It initializes TIME_GIVEN to the int parameter, and initializes pauseTime and totalPauseTime to 0.
   * 
   * @param tGiven  - int value of the time in milliseconds given to the player to play the game
   */
  public GamePanel(int tGiven)
  {
    setLayout (null); //absolute layouts
    setFocusable(true); //automatically "selects" panel - no need to click on the screen first
    TIME_GIVEN = tGiven;
    pauseTime = 0;
    totalPauseTime = 0;
  }
  
  /**
   * This method shows the instrucstions panel.
   */
  public void showInstructions()
  {
    add(instructionsPanel);
  }
  
  /**
   * Calculates the time left in seconds.
   * 
   * <p>
   * <b>Local Variables</b>
   * <p>
   * <b>milliLeft</b> stores the calculated time left(in milliseconds) from the current time, start time and total pause time
   * <p>
   * 
   * @return the integer time left in milliseconds
   */
  public int getTimeLeft()
  {
    int milliLeft = TIME_GIVEN - (int)(System.currentTimeMillis() - startTime) + (int)totalPauseTime;
    return milliLeft;
  }
  
  /**
   * This method reinitializes all relevant variables to start a new game.
   * It then shows the instructions.
   */
  public abstract void startNewGame();
  
  /**
   * This method is used in the InstructionPanel class. It closes the instructions,
   * adds all game components, starts the frame updater and stores the start time.
   */
  public abstract void endInstructions();
  
  /**
   * This method pauses the game by removing all game components and stopping the frame updater.
   * It also reinitializes the pauseTime to the current time in milliseconds.
   */
  public abstract void pause();
  
  /**
   * This method resumes the game by added all game components and starting the frame updater.
   * It adds the time spent in a paused state to the totalPausedTime.
   */
  public abstract void unPause();
  
  /**
   * This method shows the game failed screen and brings the user back to the main menu.
   */
  public abstract void fail();
}