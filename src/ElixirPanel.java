import java.awt.*;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javafx.scene.media.*;

import java.io.*;
import javax.imageio.ImageIO;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 * Tigerclaw Corp. - Yuan Y. and Jaclyn W.
 * <p>
 * VK Enterprises - Ms. Krasteva
 * <p>
 * Time spent: 10 h 25 mins
 * <p>
 * 
 * This program creates level 1 of the CampX game in a panel which can be added to the main window.
 * In this level, the player mixes different concentrations of antidotes to get a certain concentration
 * and save fellow agents who have important intel. The player then has to find the correct dosage based
 * on several formulae. After they successfully save the agents and receive the intel, the player has to
 * piece it together to get an address to the location of the next mission.
 * 
 * <p>
 * <b>Class Variables</b>
 * <p>
 * <b>add20Button</b> This creates an instance of the CustomButton class with a picture with the word "Add" on it.
 * <p>
 * <b>add80Button</b> This creates an instance of the CustomButton class with a picture with the word "Add" on it.
 * <p>
 * <b>emptyButton</b> This creates an instance of the CustomButton class with a picture with the word "Empty" on it.
 * <p>
 * <b>menuButton</b> This creates an instance of the CustomButton class with a picture of a menu symbol.
 * <p>
 * <b>add20Field</b> This creates an instance of the JTextField class.
 * <p>
 * <b>add80Field</b> This creates an instance of the JTextField class.
 * <p>
 * <b>background</b> This creates an instance of the Image class and loads a background image from a file.
 * <p>
 * <b>subBackground</b> This creates an instance of the Image class and loads a subBackground image from a file.
 * <p>
 * <b>ADDRESS</b> This is a final String which stores the answer in the final stage.
 * <p>
 * <b>stage</b> This monitors the current stage level.
 * <p>
 * <b>NEEDED_PERCENTAGES</b> This is a final int[] that determines the target percentage for each stage.
 * <p>
 * <b>currentPer</b> This int stores the current percentage of the antidote in the large beaker.
 * <p>
 * <b>amount20</b> This int stores the current amount of 20% antidote in the large beaker.
 * <p>
 * <b>amount80</b> This int stores the current amount of 80% antidote in the large beaker.
 * <p>
 * <b>timeLeft</b> This is used to convert the remaining time into a String and drawn on the panel.
 * <p>
 * <b>inSubStage</b> This boolean stores whether the player in is the substage.
 * <p>
 * <b>confirmButton</b> This creates an instance of the CustomButton class with a picture with the word "CONFIRM" on it.
 * <p>
 * <b>answerField</b> This creates an instance of the JTextField class.
 * <p>
 * <b>inFinalStage</b> This boolean stores whether the player in is the final stage.
 * <p>
 * <b>DOSAGES</b> This is a final int[] that determines the dosage needed for each stage.
 * <p>
 * 
 * MODIFICATION: Jaclyn Woon, Ver 5 June 4, 2017, Time spent: 15 mins
 * <p>
 * Jaclyn        delete testing code an replace System printing to JOptionPane dialogue
 * <p>
 * Jaclyn        edit JavaDoc
 * <p>
 * 
 * @version 5 June 4, 2017
 * @author Jaclyn Woon
 */
public class ElixirPanel extends GamePanel implements ActionListener
{
  private CustomButton add20Button;
  private CustomButton add80Button;
  private CustomButton emptyButton;
  private CustomButton menuButton;
  private JTextField add20Field;
  private JTextField add80Field;
  private Image background;
  private Image subBackground;
  
  private int stage = 0;
  private final int[] NEEDED_PERCENTAGES = new int[] {40, 35, 38, 60};
  private int currentPer = 0;
  private int amount20 = 0;
  private int amount80 = 0;
  private String timeLeft = "15:00";
  
  
  private Boolean inSubStage = false;
  private CustomButton confirmButton;
  private JTextField answerField;
  private Boolean inFinalStage = false;
  private final String ADDRESS = "Area 5 4465 Stirling St. #65";
  private final int[] DOSAGES = new int[] {50, 64, 81, 64};
  
  /*
   * This constructor creates a new ElixirPanel object.
   * It sets all necessary constraints, initializes all variables and enables all buttons.
   **/
  public ElixirPanel()
  {
    super(900000);  //15 minutes
    setBounds (0, 0, 1000, 800);
    //initialization, load all images
    try
    {
      background = ImageIO.read(new File("Images/Elixir/background.jpg"));
      subBackground = ImageIO.read(new File("Images/Elixir/subBackground.jpg"));
    }
    catch (IOException e)
    {
      JOptionPane.showMessageDialog (null, "Elixir image(s) not found.", "Error.", JOptionPane.ERROR_MESSAGE);
      System.exit(-1);
    }
    
    mPlayer = new MediaPlayer (new Media(new File("Music/elixirTheme.mp3").toURI().toString()));
    instructionsPanel = new InstructionsPanel ("Images/Elixir/Instructions/", 2, this);
    gameMenu = new GameMenu(this, instructionsPanel, mPlayer);
    add20Button = new CustomButton ("Images/Elixir/addButton.jpg");
    add80Button = new CustomButton ("Images/Elixir/addButton.jpg");
    emptyButton = new CustomButton ("Images/Elixir/emptyButton.jpg");
    menuButton = new CustomButton ("Images/GameMenu/gameMenuButton.jpg");
    add20Field = new JTextField();
    add80Field = new JTextField();
    
    confirmButton = new CustomButton ("Images/Elixir/confirmButton.jpg");
    answerField = new JTextField();
    
    add20Button.setBounds (687, 690, add20Button.getWidth(), add20Button.getHeight());
    add80Button.setBounds (238, 690, add80Button.getWidth(), add80Button.getHeight());
    emptyButton.setBounds (440, 690, emptyButton.getWidth(), emptyButton.getHeight());
    menuButton.setBounds (900, 10, menuButton.getWidth(), menuButton.getHeight());
    add20Field.setBounds (805, 695, 170, 50);
    add20Field.setFont (new Font ("Courier", Font.BOLD,20));
    add20Field.setBackground(Color.BLACK);
    add20Field.setForeground(Color.GREEN);
    add20Field.setCaretColor(Color.GREEN);
    add80Field.setBounds (25, 695, 170, 50);
    add80Field.setFont (new Font ("Courier", Font.BOLD,20));
    add80Field.setBackground(Color.BLACK);
    add80Field.setForeground(Color.GREEN);
    add80Field.setCaretColor(Color.GREEN);
    
    confirmButton.setBounds (600, 620, confirmButton.getWidth(), confirmButton.getHeight());
    answerField.setBounds (150, 600, 400, 100);
    answerField.setFont (new Font ("Courier", Font.BOLD,20));
    answerField.setBackground(Color.BLACK);
    answerField.setForeground(Color.YELLOW);
    answerField.setCaretColor(Color.YELLOW);
    answerField.setText("<input here>");
    
    //listeners
    add20Button.addActionListener (this);
    add80Button.addActionListener (this);
    emptyButton.addActionListener (this);
    menuButton.addActionListener (this);
    confirmButton.addActionListener (this);
    
    frameUpdater = new Timer (50, this); //no need a very fast updater
  }
  
  @Override
  /*
   * This method draws the correct background and text in the correct stages.
   **/
  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);
    
    if (!inSubStage)
    {
      g.drawImage(background, 0, 0, null);
      
      g.setFont (new Font(g.getFont().getFontName(), 0, 20));
      g.drawString(NEEDED_PERCENTAGES[stage] + "%", 490, 226);
      g.setFont (new Font(g.getFont().getFontName(), 0, 40));
      g.setColor (Color.GREEN);
      g.drawString (currentPer + "%", 485, 623);
      g.setColor (Color.BLACK);
    }
    else
    {
      g.drawImage(subBackground, 0, 0, null);
      g.setFont (new Font(g.getFont().getFontName(), 0, 40));
      g.setColor (Color.WHITE);
      if (!inFinalStage)
      {
        if (stage ==0)
        {
          g.drawString("200 units of the posion was injected into Agent 1's body.", 10, 200);
          g.drawString("We need a quarter of that amount.", 10, 250);
        }
        else if (stage == 1)
        {
          g.drawString("35 units of the posion was injected into Agent 2's body.", 10, 200);
          g.drawString("We need 6 units less than double that amount.", 10, 250);
        }
        else if (stage == 2)
        {
          g.drawString("81 units of the posion was injected into Agent 3's body.", 10, 200);
          g.drawString("We need that amount subtracted from 90,", 10, 250);
          g.drawString("then multiplied by itself.", 10, 300);
        }
        else
        {
          g.drawString("4 units of the posion was injected into Agent 4's body.", 10, 200);
          g.drawString("We need that amount to the power of 3.", 10, 250);
        }
        g.setColor (Color.CYAN);
        g.drawString("Input the amount of anitdote we need.", 10, 450);
        g.drawString("Do not include units, only the integer amount.", 10, 500);
      }
      else
      {
        //draw clues and question
        g.drawString("You have saved all the agents and received the intel.", 20, 200);
        g.drawString("Clue #1: Stirling St.", 20, 250);
        g.drawString("Clue #2: 4465", 20, 300);
        g.drawString("Clue #3: #65", 20, 350);
        g.drawString("Clue #4: Area 5", 20, 400);
        g.setColor (Color.CYAN);
        g.drawString("Enter the correct address.", 20, 500);
        g.drawString("Hint: No extra characters other than spaces are used.", 20, 550);
      }
      g.setColor (Color.YELLOW);
    }
    g.drawString (timeLeft, 20, 60);
    
    //draw beaker at different liquid levels -> if (amount20 <= 0)...
  }
  
  @Override
  /**
   * The actionPerformed method checks for events and where they came from.
   * It performs the specific action for each origin.
   * The frame updater updates the timeLeft variable and repaints the screen.
   * The menu button pauses the game and brings the user to the game menu screen.
   * The add20 button adds the desired amount (if valid) of 20% antidote to the large beaker.
   * The add80 button adds the desired amount (if valid) of 80% antidote to the large beaker.
   * The empty button empties the large beaker of all 20% and 80% antdote.
   * The confirm button checks the stage. If in the substage, checks if the dosage is correct
   * and if so goes to the subCutscene. If not, the player has failed. If in the final stage,
   * checks if the address is correct and if so, the game is completed. If not, the player
   * tries again.
   *
   * @param ae ActionEvent object used to identify the event origin
   */ 
  public void actionPerformed(ActionEvent ae) {
    if (ae.getSource() == frameUpdater)
    {
      timeLeft = getStrTimeLeft();
      repaint();
    }
    if (ae.getSource() == menuButton)
    {
      pause();
      add(gameMenu);
      repaint();
    }
    else if (ae.getSource() == add20Button)
    {
      try
      {
        amount20 += Integer.parseInt(add20Field.getText());
      }
      catch (NumberFormatException e)
      {
        JOptionPane.showMessageDialog (null, "Please enter a positive integer smaller than 2^32.", "Error.", JOptionPane.ERROR_MESSAGE);
        add20Field.setText("");
      }
      calculatePercentage();
      repaint();
      if (currentPer == NEEDED_PERCENTAGES[stage])
      {
        subStage1();
      }
    }
    else if (ae.getSource() == add80Button)
    {
      try
      {
        amount80 += Integer.parseInt(add80Field.getText());
      }
      catch (NumberFormatException e)
      {
        JOptionPane.showMessageDialog (null, "Please enter a positive integer smaller than 2^32.", "Error.", JOptionPane.ERROR_MESSAGE);
        add80Field.setText("");
      }
      calculatePercentage();
      repaint();
      if (currentPer == NEEDED_PERCENTAGES[stage])
      {
        subStage1();
      }
    }
    else if (ae.getSource() == emptyButton)
    {
      currentPer = 0;
      amount20 = 0;
      amount80 = 0;
      repaint();
    }
    else
    {
      if (ae.getSource() == confirmButton)
      {
        if (!inFinalStage)
        {
          int dosage = 0;
          try
          {
            dosage = Integer.parseInt(answerField.getText());
            if (dosage == DOSAGES[stage])
            {
              pause();
              CampXApp.elixirSubCutscene();
            }
            else
            {
              fail();
            }
          }
          catch (NumberFormatException e)
          {
            JOptionPane.showMessageDialog (null, "Please enter a positive integer smaller than 2^32.", "Error.", JOptionPane.ERROR_MESSAGE);
          }
        }
        else
        {
          if (answerField.getText().equals(ADDRESS))
          {
            complete();
          }
          else
          {
            JOptionPane.showMessageDialog (null, "You scouted the area but realised it wasn't the right address.", "", JOptionPane.INFORMATION_MESSAGE);
          }
        }
        answerField.setText("");
      }
    }
  }
  
  /*
   * Reinitializes all relevant variables for a new game,
   * resets instructionsPanel's control panel, shows the
   * instructions and plays the music.
   **/
  public void startNewGame()
  {
    stage = 0;
    pause();
    inSubStage = false;
    inFinalStage = false;
    currentPer = 0;
    amount20 = 0;
    amount80 = 0;
    add20Field.setText("");
    add80Field.setText("");
    instructionsPanel.resetControlPanel(this);
    showInstructions();
    mPlayer.play();
  }
  
  /*
   * Calculates the time left in seconds and converts it to a String in the form of MM:SS.
   * 
   * <b>Local Variables</b>
   * <p>
   * <b>secondsLeft</b> stores the time left in seconds
   * <p>
   * 
   * @return the time left in the form MM:SS
   **/
  private String getStrTimeLeft()
  {
    int secondsLeft = getTimeLeft()/1000;
    if (secondsLeft <= 0)
    {
      fail();
    }
    
    String mins = "" + secondsLeft / 60;
    if (mins.length() <= 1)
    {
      mins = "0" + mins;
    }
    
    String seconds = "" + secondsLeft % 60;
    if (seconds.length() <= 1)
    {
      seconds = "0" + seconds;
    }
    
    return mins + ":" + seconds;
  }
  
  /*
   * Calculaes the percentage in the large beaker using the amounts of 20% and 80% anitidotes in it.
   **/
  private void calculatePercentage()
  {
    currentPer = (int)((amount20 * 20.0 + amount80 * 80.0)/(amount20 + amount80));
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
    frameUpdater.start();
    startTime = System.currentTimeMillis();
    repaint();
  }
  
  @Override
  public void pause()
  {
    if (!inSubStage)
    {
      remove(menuButton);
      remove(add20Button);
      remove(add80Button);
      remove(emptyButton);
      remove(add20Field);
      remove(add80Field);
    }
    else
    {
      remove(menuButton);
      remove(confirmButton);
      remove(answerField);
    }
    frameUpdater.stop();
    pauseTime = System.currentTimeMillis();
  }
  
  @Override
  public void unPause()
  {
    if (!inSubStage)
    {
      add(menuButton);
      add(add20Button);
      add(add80Button);
      add(emptyButton);
      add(add20Field);
      add(add80Field);
    }
    else
    {
      add(menuButton);
      add(confirmButton);
      add(answerField);
    }
    frameUpdater.start();
    totalPauseTime += System.currentTimeMillis() - pauseTime;
    repaint();
  }
  
  /*
   * Shifts the game status to being in the substage.
   **/
  private void subStage1()
  {
    pause();
    inSubStage = true;
    answerField.setText("<input here>");
    unPause();
    
    repaint();
  }
  
  /*
   * Called at the completion of the substage.
   * Shifts game status to either in the final stage or the next stage.
   **/
  public void subStage2()
  {
    if (stage >= 3)
    {
      inFinalStage = true;
      answerField.setText("<input here>");
    }
    else
    {
      stage++;
      inSubStage = false;
    }
    unPause();
    repaint();
  }
  
  /*
   * Stops the frame updater and music, then passes the player's score and program flow back to the driver class.
   **/
  private void complete()
  {
    frameUpdater.stop();
    mPlayer.stop();
    CampXApp.elixirComplete(getTimeLeft());
  }
  
  @Override
  public void fail()
  {
    mPlayer.stop();
    Cutscene failScene = new Cutscene ("Images/Elixir/Fail", -1);
    pause();
    CampXApp.frame.getContentPane().remove(this);
    CampXApp.frame.add(failScene);
    CampXApp.frame.repaint();
    failScene.start();
  }
}