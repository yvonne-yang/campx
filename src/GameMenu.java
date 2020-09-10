import java.awt.*;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javafx.scene.media.*;

import java.io.*;
import javax.imageio.ImageIO;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 * Tigerclaw Corp. - Yuan Y. and Jaclyn W.
 * <p>
 * VK Enterprises - Ms. Krasteva
 * <p>
 * Time spent: 1 h 45 mins
 * <p>
 * 
 * This program is a JPanel created to be a game menu. It allows the player to resume the game,
 * view the instructions again, go to the main menu and exit the game entirely.
 * 
 * <p>
 * <b>Class Variables</b>
 * <p>
 * <b>background</b> a private variable which creates an instance of the Image class which paints this component.
 * <p>
 * <b>resume</b> a private variable which creates an instance of the CustomButton class with an image of a resume button.
 * <p>
 * <b>instructions</b> a private variable which creates an instance of the CustomButton class with an image of an instructions button.
 * <p>
 * <b>mainMenu</b> a private variable which creates an instance of the CustomButton class with an image of a main menu button.
 * <p>
 * <b>exitGame</b> a private variable which creates an instance of the CustomButton class with an image of an exit game button.
 * <p>
 * <b>gamePanel</b> a private variable which stores the reference to the controlling GamePanel.
 * <p>
 * <b>instructionsPanel</b> a private variable which stores the reference to the InstructionsPanel in the controlling GamePanel.
 * <p>
 * <b>mPlayer</b> a private variable which stores the reference to the MediaPlayer in the controlling GamePanel.
 * <p>
 * 
 * MODIFICATION: Jaclyn Woon, Ver 5 June 4, 2017, Time spent: 20 mins
 * <p>
 * Jaclyn        delete testing code an replace System printing to JOptionPane dialogue
 * <p>
 * Jaclyn        edit JavaDoc
 * <p>
 * 
 * @version 5 June 4, 2017
 * @author Jaclyn Woon
 */
public class GameMenu extends JPanel implements ActionListener
{
  private Image background;
  private CustomButton resume;
  private CustomButton instructions;
  private CustomButton mainMenu;
  private CustomButton exitGame;
  private GamePanel gamePanel;
  private InstructionsPanel instructionsPanel;
  private MediaPlayer mPlayer;
  
  /**
   * This constructor creates a new GameMenu object with the necessary GamePanel attributes.
   * It sets all necessary constraints, initializes all variables and enables all buttons.
   * 
   * @param game - the GamePanel object that controls this GameMenu
   * @param instrucs - the InstructionsPanel associated with the controlling GamePanel
   * @param mp - the music associated with the controlling GamePanel
   */
  public GameMenu (GamePanel game, InstructionsPanel instrucs, MediaPlayer mp)
  {
    setBounds(0, 0, 1000, 800);
    //initializations
    try
    {
      background = ImageIO.read(new File("Images/GameMenu/background.jpg"));
    }
    catch (IOException e)
    {
      JOptionPane.showMessageDialog (null, "Game menu background image not found.", "Error.", JOptionPane.ERROR_MESSAGE);
      System.exit(-1);
    }
    gamePanel = game;
    instructionsPanel = instrucs;
    mPlayer = mp;
    resume = new CustomButton ("Images/GameMenu/resumeButton.jpg");
    instructions = new CustomButton ("Images/GameMenu/instructionsButton.jpg");
    mainMenu = new CustomButton ("Images/GameMenu/mainMenuButton.jpg");
    exitGame = new CustomButton ("Images/GameMenu/exitGameButton.jpg");
    
    //listeners
    resume.addActionListener (this);
    instructions.addActionListener (this);
    mainMenu.addActionListener (this);
    exitGame.addActionListener (this);
    
    setLayout(null);
    add(resume);
    resume.setBounds (300, 300, resume.getWidth(), resume.getHeight());
    add(instructions);
    instructions.setBounds (300, 400, instructions.getWidth(), instructions.getHeight());
    add(mainMenu);
    mainMenu.setBounds (300, 500, mainMenu.getWidth(), mainMenu.getHeight());
    add(exitGame);
    exitGame.setBounds (300, 600, exitGame.getWidth(), exitGame.getHeight());
    
    setVisible(true);
  }
  
  @Override
  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);
    
    g.drawImage(background, 0, 0, null);
  }
  
  @Override
  /**
   * The actionPerformed method checks for events and where they came from.
   * It performs the specific action for each origin.
   * The resume button moves gameplay back to the GamePanel.
   * The instructions button displays the instructions.
   * The main menu button moves gameplay back to the MainMenu.
   * The exit game button confirms if the user wants to exit the game.
   * If so, exits the entire game.
   * 
   * <p>
   * <b>Local Variables<\b>
   * <p>
   * <b>n<\b> int that stores the user's choice in a JOptionPane
   * <p>
   *
   * @param ae - ActionEvent object used to identify the event origin
   */ 
  public void actionPerformed(ActionEvent ae) {
    if (ae.getSource() == resume)
    {
      gamePanel.remove(this);
      gamePanel.unPause();
    }
    else if (ae.getSource() == instructions)
    {
      remove(resume);
      remove(instructions);
      remove(mainMenu);
      remove(exitGame);
      add(instructionsPanel);
      repaint();
    }
    else if (ae.getSource() == mainMenu)
    {
      gamePanel.remove(this);
      gamePanel.unPause();
      mPlayer.stop();
      CampXApp.frame.getContentPane().remove(gamePanel);
      CampXApp.toMainMenu(this);
    }
    else
    {
      if (ae.getSource() == exitGame)
      {
        int n = JOptionPane.showConfirmDialog (null, "Are you sure you want to exit the game?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (n == JOptionPane.YES_OPTION)
        {
          System.exit(0);
        }
      }
    }
  }
  
  /**
   * This method removes the instructions panel, enables the menu and repaints.
   */
  public void endInstructions()
  {
    add(resume);
    add(instructions);
    add(mainMenu);
    add(exitGame);
    remove(instructionsPanel);
    repaint();
  }
}