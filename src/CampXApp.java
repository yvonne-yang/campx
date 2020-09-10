import java.io.*;

import javax.swing.JFrame;

import javafx.scene.media.*;
import javafx.embed.swing.JFXPanel;

/**
 * Tigerclaw Corp. - Yuan Y. and Jaclyn W.
 * <p>
 * VK Enterprises - Ms. Krasteva
 * <p>
 * Time spent: 4 h 25 mins
 * <p>
 * This is the driver class that contains all static methods that control       
 * program flow. It initializes a 1000*800 frame and all game screens as panels.
 * A screen is displayed when its panel is added to the frame after the previous
 * panel is removed. Each static method in this class performs one of these     
 * screen changes.
 * <p>
 * The game starts with a splash screen that lasts for 2 seconds. The user will 
 * then see a main menu where they could choose to start a new game, see the 
 * "spy rankings", see the credits, or exit the game. The "spy rankings" screen 
 * will display ten players who scored the highest. If the player chooses to    
 * start playing, a cutscene that introduces the story will be shown. A cutscene
 * and an instructions screen will always precede a mission/level. When the     
 * player fails a mission, they will see the "mission failed" screen of that    
 * mission. There are 3 mission in total: "Elixir", "Glitch", and "Darwin". The 
 * "mission completed" screen will only be displayed after the player has won   
 * all 3 games. The program will then display the credits and the spy rankings 
 * screen. The user can go back to the main menu from the spy rankings screen.  
 * They could also pause the game by clicking on the game menu icon in any      
 * mission, and see the instructions again or go to main menu.
 * <p>
 * <b>Class Variables</b>
 * <p>
 * <b>frame</b> creates a static instance of the JFrame class.
 * <p>
 * <b>splashScreenPanel</b> creates an instance of the splashScreenPanel class.
 * <p>
 * <b>mainMenuPanel</b> creates a static instance of the MainMenu class.
 * <p>
 * <b>elixirPanel</b> creates a static instance of the ElixirPanel class.
 * <p>
 * <b>glitchPanel</b> creates a static instance of the GlitchPanel class.
 * <p>
 * <b>darwinPanel</b> creates a static instance of the DarwinPanel class.
 * <p>
 * <b>cutscenes</b> creates a static array of instances of the Cutscene class.
 * <p>
 * <b>spyRankingsPanel</b> creates a static instance of the SpyRankings class.
 * <p>
 * <b>creditsPanel</b> creates a static instance of the Credits class.
 * <p>
 * <b>mPlayer</b> creates a static instance of the MediaPlayer class.
 * <p>
 * <b>score</b> stores a static int of the player's culmulative score.
 * <p>
 * 
 * MODIFICATION: Jaclyn Woon, Ver 4 June 4, 2017, Time spent: 15 mins
 * <p>
 * Author        Modificiation
 * <p>
 * Jaclyn        delete testing code
 * <p>
 * Jaclyn        edit JavaDoc
 * <p>
 * 
 * @version 4 Jun 4, 2017
 * @author Jaclyn Woon and Yuan Yang
 */
public class CampXApp
{
  public static JFrame frame;
  private SplashScreen splashScreenPanel;
  private static MainMenu mainMenuPanel;
  private static ElixirPanel elixirPanel;
  private static GlitchPanel glitchPanel;
  private static DarwinPanel darwinPanel;
  private static Cutscene[] cutscenes;
  private static SpyRankings spyRankingsPanel;
  private static Credits creditsPanel;
  private static MediaPlayer mPlayer;
  private static int score;
  
  /**
   * This constructor creates the frame and initializes all panels that are used
   * in the game, including the three mission panels, main menu, splash screen, 
   * cutscenes, spy rankings, and credits. It also initializes the music player.
   * It then sets the properties of the frame and displays the splash screen and
   * the main menu
   * <p>
   * <b>Local Variables</b>
   * <p>
   * <b>fxPanel</b> A JFXPanel object that contains the toolkit for music.
   */
  public CampXApp()
  {
    JFXPanel fxPanel = new JFXPanel();
    
    mPlayer = new MediaPlayer (new Media(new File("Music/mainTheme.mp3").toURI().toString()));
    elixirPanel = new ElixirPanel();
    glitchPanel = new GlitchPanel();
    darwinPanel = new DarwinPanel();
    mainMenuPanel = new MainMenu();
    splashScreenPanel = new SplashScreen();
    cutscenes = new Cutscene[6];
    cutscenes[0] = new Cutscene ("images/cutscene0", 0);
    cutscenes[1] = new Cutscene ("images/cutscene1", 1);
    cutscenes[2] = new Cutscene ("images/cutscene2", 2);
    cutscenes[3] = new Cutscene ("images/cutscene3", 3);
    cutscenes[4] = new Cutscene ("images/completed", 4);
    cutscenes[5] = new Cutscene ("images/cutscene1.1", 11);
    
    frame = new JFrame("CampX App");
    frame.setSize (1000, 800);
    frame.setResizable (false);
    frame.setLocationRelativeTo (null);
    frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
    frame.add (splashScreenPanel);
    splashScreenPanel.playSE();
    frame.setVisible (true);
    try
    {
      Thread.sleep(2000);
    }
    catch (Exception e){}
    frame.getContentPane().remove(splashScreenPanel);
    mPlayer.play();
    frame.add(mainMenuPanel);
    frame.repaint();
  }
  
  /**
   * This method starts the actual game by reinitializing variables, removing
   * the main menu panel and displaying the first cutscene.
   */
  public static void startGame()
  {
    score = 0;
    frame.getContentPane().remove(mainMenuPanel);
    frame.add(cutscenes[0]);
    cutscenes[0].start();
    frame.repaint();
  }
  
  /**
   * This method determines which screen to show after a cutscene has ended. For
   * example, cutscene 0 leads to cutscene 1 and cutscene 1, 2, 3 will each go  
   * to one of the mission screens. Cutscene 4 goes to credits whereas cutscene 
   * 5 is contained in mission "Elixir".
   * 
   * @param cutNum - the cutscnene number
   */
  public static void endCutscene(int cutNum)
  {
    switch (cutNum)
    {
      case 0: frame.getContentPane().remove(cutscenes[0]);
              frame.add(cutscenes[1]);
              cutscenes[1].start();
              frame.repaint();
              break;
      case 1: frame.getContentPane().remove(cutscenes[1]);
              elixir();
              break;
      case 2: frame.getContentPane().remove(cutscenes[2]);
              glitch();
              break;
      case 3: frame.getContentPane().remove(cutscenes[3]);
              darwin();
              break;
      case 4: frame.getContentPane().remove(cutscenes[4]);
              creditsPanel = new Credits();
              spyRankingsPanel = new SpyRankings(null);
              frame.add(creditsPanel);
              creditsPanel.start();
              frame.repaint();
              break;
      case 11: frame.getContentPane().remove(cutscenes[5]);
               frame.add(elixirPanel);
               elixirPanel.subStage2();
               frame.repaint();
      default: break;
    }
  }
  
  /**
   * This method goes back to the main menu after a 'mission failed' screen is shown.
   * 
   * @param cs - The mission failed cutscnene
   */
  public static void endCutscene(Cutscene cs)
  {
    frame.getContentPane().remove(cs);
    mPlayer.play();
    frame.add(mainMenuPanel);
    frame.repaint();
  }
  
  /**
   * This method displays the spy rankings screen and adds the player's score to the
   * rankings list.
   */
  public static void toRankings()
  {
    frame.add(spyRankingsPanel);
    frame.repaint();
    spyRankingsPanel.newEntry(score);
    frame.repaint();
  }
  
  /**
   * This method is called when the player wishes to go back to the main menu   
   * from the game menu screen. 
   * 
   * @param gm - The GameMenu object.
   */
  public static void toMainMenu(GameMenu gm)
  {
    frame.getContentPane().remove(gm);
    mPlayer.play();
    frame.add(mainMenuPanel);
    frame.repaint();
  }
  
  /**
   * This method is called when the player wishes to go back to the main menu 
   * from the spy rankings screen.
   */
  public static void toMainMenu()
  {
    frame.getContentPane().remove(spyRankingsPanel);
    frame.add(mainMenuPanel);
    frame.repaint();
  } 
  
  /**
   * This method is called when one of the 3 mission panels goes back to the    
   * main menu.
   * 
   * @param gp - One of the 3 mission panels which is a GamePanel.
   */
  public static void toMainMenu (GamePanel gp)
  {
    frame.getContentPane().remove(gp);
    mPlayer.play();
    frame.add(mainMenuPanel);
    frame.repaint();
  }
  
  /**
   * This method starts the mission "Elixir" game by adding its panel to the 
   * frame.
   */
  private static void elixir()
  {
    mPlayer.stop();
    frame.add(elixirPanel);
    elixirPanel.startNewGame();
    frame.repaint();
  }
  
  /**
   * This method displays the sub-cutscene of mission "Elixir".
   */
  public static void elixirSubCutscene()
  {
    frame.getContentPane().remove(elixirPanel);
    frame.add(cutscenes[5]);
    cutscenes[5].start();
    frame.repaint();
  }
  
  /**
   * This method is called when the player passes mission "Elixir" successfully.
   * It will remove the game panel and display the cutscene to the next mission.
   * 
   * @param eScore - Player's score in Elixir.
   */
  public static void elixirComplete(int eScore)
  {
    score += eScore;
    frame.getContentPane().remove(elixirPanel);
    mPlayer.play();
    frame.add(cutscenes[2]);
    cutscenes[2].start();
    frame.repaint();
  }
  
  /**
   * This method starts the mission "Glitch" game by adding its panel to the 
   * frame.
   */
  private static void glitch()
  {
    mPlayer.stop();
    frame.add(glitchPanel);
    glitchPanel.startNewGame();
    frame.repaint();
  }
  
  /**
   * This method is called when the player passes mission "Glitch" successfully.
   * It will remove the game panel and display the cutscene to the next mission.
   * 
   * @param gScore - Player's score in Glitch.
   */
  public static void glitchComplete(int gScore)
  {
    score += gScore;
    frame.getContentPane().remove(glitchPanel);
    frame.add(cutscenes[3]);
    cutscenes[3].start();
    frame.repaint();
  }
  
  
  /**
   * This method starts the mission "Darwin" game by adding its panel to the 
   * frame.
   */
  private static void darwin()
  {
    mPlayer.stop();
    frame.add(darwinPanel);
    darwinPanel.startNewGame();
    frame.repaint();
  }
  
  
  /**
   * This method is called when the player passes mission "Darwin" successfully.
   * It will remove the game panel and display the mission completed screen.
   * 
   * @param dScore - Player's score in Darwin.
   */
  public static void darwinComplete(int dScore)
  {
    score += dScore;
    frame.getContentPane().remove(darwinPanel);
    frame.add(cutscenes[4]);
    mPlayer.play();
    cutscenes[4].start();
    frame.repaint();
  }
  
  /**
   * The main method which creates an instance of this driver class and starts the game.
   * 
   * @param args - An array of String arguments.
   */
  public static void main (String[] args)
  {
    new CampXApp();
  }
}