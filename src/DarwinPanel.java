import java.awt.*;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

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
 * Time spent: 15h 20 mins
 * <p>
 * 
 * This program creates level 3 of the CampX game in a panel which can be added to the main window.
 * This is the last level of the CampX game.
 * In this level, the player has successfully inflitrated a suspicious lab and has to collect all
 * the information available. They travel around the lab and interact with doors, papers, cages,
 * tanks, shelf, cabinets and keys. After the player finds all the information, the have to classify
 * lists of animals biologically. 
 * 
 * <p>
 * <b>Class Variables</b>
 * <p>
 * <b>menuButton</b> This creates an instance of the CustomButton class with a picture of a menu symbol.
 * <p>
 * <b>map</b> This creates an instance of the Image class which loads a map of the secret lab.
 * <p>
 * <b>key</b> This creates an instance of the Image class which loads a picture of a key.
 * <p>
 * <b>book</b> This creates an instance of the Image class which loads a picture of a notebook.
 * <p>
 * <b>mapX</b> This stores the x-coordinate of where the map should be drawn.
 * <p>
 * <b>mapY</b> This stores the y-coordinate of where the map should be drawn.
 * <p>
 * <b>moveX</b> This stores whether the map should move left or right.
 * <p>
 * <b>moveY</b> This stores whether the map should move up or down.
 * <p>
 * <b>spyRow</b> This stores the row of the player's location on the map.
 * <p>
 * <b>spyCol</b> This stores the column of the player's location on the map.
 * <p>
 * <b>solids</b> This 2D int array creates a matrix of the solid objects on the map.
 * <p>
 * <b>doors</b> This 2D int array creates a matrix of the doors on the map.
 * <p>
 * <b>papers</b> This 2D int array creates a matrix of the papers on the map.
 * <p>
 * <b>cages</b> This 2D int array creates a matrix of the cages on the map.
 * <p>
 * <b>spy</b> This creates an instance of the Player class.
 * <p>
 * <b>foundPapers</b> This boolean array stores whether the player has found each piece of information.
 * <p>
 * <b>foundAnimals</b> This boolean array stores whether the player has found each animal.
 * <p>
 * <b>foundKeys</b> This boolean array stores whether the player has found each key.
 * <p>
 * <b>inPart2</b> This boolean stores whether the player is in part 2 of the mission.
 * <p>
 * <b>questionNum</b> This stores question number the player is on.
 * <p>
 * <b>ANS_BUTTON_FILES</b> This final String array stores the file paths of images of all the answer options.
 * <p>
 * <b>optionA</b> This creates an instance of the CustomButton class.
 * <p>
 * <b>optionB</b> This creates an instance of the CustomButton class.
 * <p>
 * <b>optionC</b> This creates an instance of the CustomButton class.
 * <p>
 * 
 * MODIFICATION: Jaclyn Woon, Ver 4 June 4, 2017, Time spent: 4 h
 * <p>
 * Author        Modificiation
 * <p>
 * Jaclyn        edit instructions and game graphics
 * <p>
 * Jaclyn        implement part 2 (classifications)
 * <p>
 * Jaclyn        JavaDoc
 * <p>
 * 
 * @version 4 June 4, 2017
 * @author Jaclyn Woon
 */
public class DarwinPanel extends GamePanel implements ActionListener, KeyListener
{
  private CustomButton menuButton;
  private Image map;
  private Image key;
  private Image book;
  
  private int mapX;
  private int mapY;
  private int moveX;
  private int moveY;
  private int spyRow;
  private int spyCol;
  private int[][] solids = new int[31][35];
  private int[][] doors = new int[31][35];
  private int[][] papers = new int[31][35];
  private int[][] cages = new int[31][35];
  private Player spy;
  private boolean[] foundPapers = new boolean[26];
  private boolean[] foundAnimals = new boolean[18];
  private boolean[] foundKeys = new boolean[5];
  
  private boolean inPart2 = false;
  private int questionNum;
  private final String[][] ANS_BUTTON_FILES = new String[][]{{"Images/Darwin/Buttons/optionA(1).jpg", "Images/Darwin/Buttons/optionB(1).jpg", "Images/Darwin/Buttons/optionC(1).jpg"}, {"Images/Darwin/Buttons/optionA(2).jpg", "Images/Darwin/Buttons/optionB(2).jpg", "Images/Darwin/Buttons/optionC(2).jpg"}, {"Images/Darwin/Buttons/optionA(3).jpg", "Images/Darwin/Buttons/optionB(3).jpg", "Images/Darwin/Buttons/optionC(3).jpg"}, {"Images/Darwin/Buttons/optionA(4).jpg", "Images/Darwin/Buttons/optionB(4).jpg", "Images/Darwin/Buttons/optionC(4).jpg"}}; //4 questions with 3 answers each
  private CustomButton optionA, optionB, optionC;  
  
  /**
   * This constructor creates a new DarwinPanel object.
   * It sets all necessary constraints, initializes all variables and enables all buttons.
   */
  public DarwinPanel()
  {
    super(1560000);  //25 minutes
    setBounds (0, 0, 1000, 800);
    //initialization, load all images
    try
    {
      background = ImageIO.read(new File("images/Darwin/blackBack.png"));
      map = ImageIO.read(new File("images/Darwin/Map.png"));
      key = ImageIO.read(new File("images/Darwin/key.png"));
      book = ImageIO.read(new File("images/Darwin/notebook.jpg"));
    }
    catch (IOException e)
    {
      JOptionPane.showMessageDialog (null, "Darwin image(s) not found.", "Error.", JOptionPane.ERROR_MESSAGE);
      System.exit(-1);
    }
    initializeSolids();
    initializeDoors();
    initializePapers();
    initializeCages();
    mPlayer = new MediaPlayer (new Media(new File("Music/darwinTheme.mp3").toURI().toString()));
    instructionsPanel = new InstructionsPanel ("Images/Darwin/Instructions/", 2, this);
    menuButton = new CustomButton ("Images/GameMenu/gameMenuButton.jpg");
    gameMenu = new GameMenu(this, instructionsPanel, mPlayer);
    
    spy = new Player();
    mapX = -540;
    mapY = -1470;
    moveY = 0;
    moveX = 0;
    spyRow = 29;
    spyCol = 16;
    
    questionNum = 0;
    optionA = new CustomButton(ANS_BUTTON_FILES[0][0]);
    optionB = new CustomButton(ANS_BUTTON_FILES[0][1]);
    optionC = new CustomButton(ANS_BUTTON_FILES[0][2]);
    
    menuButton.setBounds (900, 10, menuButton.getWidth(), menuButton.getHeight());
    optionA.setBounds (650, 200, optionA.getWidth(), optionA.getHeight());
    optionB.setBounds (650, 350, optionB.getWidth(), optionB.getHeight());
    optionC.setBounds (650, 500, optionC.getWidth(), optionC.getHeight());
    
    //listeners
    menuButton.addActionListener (this);
    optionA.addActionListener(this);
    optionB.addActionListener(this);
    optionC.addActionListener(this);
    addKeyListener(this); //the panel
    
    frameUpdater = new Timer (150, this); //no need a very fast updater
  }
  
  @Override
  /*
   * This method draws the correct background and text in the correct stages.
   **/
  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);
    
    if (!inPart2)
    {
      g.drawImage(background, 0, 0, null);
      g.drawImage(map, mapX, mapY, null);
      
      //draw keys
      if (!foundKeys[0])
        g.drawImage(key, mapX + 128, mapY + 384, null);
      if (!foundKeys[1])
        g.drawImage(key, mapX + 192, mapY + 1088, null);
      if (!foundKeys[2])
        g.drawImage(key, mapX + 64, mapY + 1856, null);
      if (!foundKeys[3])
        g.drawImage(key, mapX + 2112, mapY + 64, null);
      if (!foundKeys[4])
        g.drawImage(key, mapX + 832, mapY + 832, null);
      
      
      spy.draw(g);
    }
    else
    {
      g.drawImage(book, 0, 0, null);
      
      //questions
      g.setFont (new Font(g.getFont().getFontName(), 0, 40));
      g.drawString("You found these", 50, 150);
      g.drawString("animals in the same", 50, 200);
      g.drawString("experiment room.", 50, 250);
      g.setColor (Color.BLUE);
      if (questionNum == 0)
      {
        g.drawString("Tiger", 100, 350);
        g.drawString("Lion", 100, 400);
        g.drawString("Puma", 100, 450);
      }
      else if (questionNum == 1)
      {
        g.drawString("Poodle", 100, 350);
        g.drawString("Husky", 100, 400);
        g.drawString("Chihuahua", 100, 450);
      }
      else if (questionNum == 2)
      {
        g.drawString("Mouse", 100, 350);
        g.drawString("Rat", 100, 400);
        g.drawString("Beaver", 100, 450);
      }
      else
      {
        if (questionNum == 3)
        {
          g.drawString("Orca", 100, 350);
          g.drawString("Human", 100, 400);
          g.drawString("Dolphin", 100, 450);
        }
      }
      g.setColor (Color.BLACK);
      g.drawString("They are all biologically", 50, 550);
      g.drawString("classified as...", 50, 600);
    }
  }
  
  @Override
  /**
   * The actionPerformed method checks for events and where they came from.
   * It performs the specific action for each origin.
   * The frame updater updates the map and repaints the screen.
   * The menu button pauses the game and brings the user to the game menu screen.
   * Option A goes to the next question if the answer is correct. If not, tells
   * the user they were wrong and decreases their score.
   * Option B goes to the next question if the answer is correct. If not, tells
   * the user they were wrong and decreases their score.
   * Option C completes the game if the answer is correct. If not, tells the user
   * they were wrong and decreases their score.
   *
   * @param ae ActionEvent object used to identify the event origin
   */ 
  public void actionPerformed(ActionEvent ae) {
    if (ae.getSource() == frameUpdater)
    {
      mapUpdate();
      repaint();
    }
    if (ae.getSource() == menuButton)
    {
      pause();
      add(gameMenu);
      repaint();
    }
    else if (ae.getSource() == optionA)
    {
      if (questionNum == 1)  //correct answer
      {
        nextQuestion();
      }
      else  //incorrect answer
      {
        JOptionPane.showMessageDialog (null, "Incorrect answer.", "Incorrect.", JOptionPane.PLAIN_MESSAGE);
        startTime -= 1000;
      }
    }
    else if (ae.getSource() == optionB)
    {
      if (questionNum == 0 || questionNum == 2)  //correct answer
      {
        nextQuestion();
      }
      else  //incorrect answer
      {
        JOptionPane.showMessageDialog (null, "Incorrect answer.", "Incorrect.", JOptionPane.PLAIN_MESSAGE);
        startTime -= 1000;
      }
    }
    else
    {
      if (ae.getSource() == optionC)
      {
        if (questionNum == 3)  //correct answer
        {
          complete();
        }
        else  //incorrect answer
        {
          JOptionPane.showMessageDialog (null, "Incorrect answer.", "Incorrect.", JOptionPane.PLAIN_MESSAGE);
          startTime -= 1000;
        }
      }
    }
  }
  
  @Override
  /**
   * The keyPressed method checks for pressed keys and where they came from.
   * It performs the specific action for each origin.
   * The right arrow key turns moves the sprite right.
   * The left arrow key turns moves the sprite left.
   * The up arrow key turns moves the sprite up.
   * The down arrow key turns moves the sprite down.
   *
   * @param ae KeyEvent object used to identify the event origin
   */ 
  public void keyPressed (KeyEvent e)
  {
    if (e.getKeyCode() == KeyEvent.VK_RIGHT)
    {
      spy.changeDir("right");
      moveX = -1;
    }
    else if (e.getKeyCode() == KeyEvent.VK_LEFT)
    {
      spy.changeDir("left");
      moveX = 1;
    }
    else if (e.getKeyCode() == KeyEvent.VK_UP)
    {
      spy.changeDir("up");
      moveY = 1;
    }
    else
    {
      if (e.getKeyCode() == KeyEvent.VK_DOWN)
      {
        spy.changeDir("down");
        moveY = -1;
      }
    }
  }
  
  @Override
  /**
   * The keyReleased method checks for released keys and where they came from.
   * It performs the specific action for each origin.
   * The space bar checks for events.
   * The right arrow key stops moving the sprite right.
   * The left arrow key stops moving the sprite left.
   * The up arrow key stops moving the sprite up.
   * The down arrow key stops moving the sprite down.
   *
   * @param ae KeyEvent object used to identify the event origin
   */ 
  public void keyReleased (KeyEvent e)
  {
    if (e.getKeyCode() == KeyEvent.VK_SPACE)
    {
      checkEvents();
    }
    else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
    {
      moveX = 0;
    }
    else if (e.getKeyCode() == KeyEvent.VK_LEFT)
    {
      moveX = 0;
    }
    else if (e.getKeyCode() == KeyEvent.VK_UP)
    {
      moveY = 0;
    }
    else
    {
      if (e.getKeyCode() == KeyEvent.VK_DOWN)
      {
        moveY = 0;
      }
    }
  }
  
  @Override
  public void keyTyped (KeyEvent e)
  {
  }
  
  /**
   * This method updates the map by moving the map coordinates. This is done instead of moving the player.
   * Only moves if there isn't a door, or solid object in the player's way.
   */ 
  private void mapUpdate()
  {
    if (solids[spyRow - moveY][spyCol - moveX] != 1 && doors[spyRow - moveY][spyCol - moveX] != 1 && papers[spyRow - moveY][spyCol - moveX] != 1 && cages[spyRow - moveY][spyCol - moveX] != 1)
    {
      spyRow -= moveY;
      spyCol -= moveX;
      mapX += moveX * 64;
      mapY += moveY * 64;
    }
  }
  
  /**
   * Goes to the next question by increasing the question number and changing the answer options.
   */
  private void nextQuestion()
  {
    questionNum++;
    optionA.changeImg(ANS_BUTTON_FILES[questionNum][0]);
    optionB.changeImg(ANS_BUTTON_FILES[questionNum][1]);
    optionC.changeImg(ANS_BUTTON_FILES[questionNum][2]);
    repaint();
  }
  
  /**
   * Reinitializes all relevant variables for a new game,
   * resets instructionsPanel's control panel, shows the
   * instructions and plays the music.
   */
  public void startNewGame()
  {
    pause();
    mapX = -540;
    mapY = -1470;
    moveY = 0;
    moveX = 0;
    spyRow = 29;
    spyCol = 16;
    spy.dir = 3;
    inPart2 = false;
    questionNum = 0;
    optionA.changeImg(ANS_BUTTON_FILES[questionNum][0]);
    optionB.changeImg(ANS_BUTTON_FILES[questionNum][1]);
    optionC.changeImg(ANS_BUTTON_FILES[questionNum][2]);
    for (int i = 0; i < foundPapers.length; i++)
    {
      foundPapers[i] = false;
    }
    for (int i = 0; i < foundAnimals.length; i++)
    {
      foundAnimals[i] = false;
    }
    for (int i = 0; i < foundKeys.length; i++)
    {
      foundKeys[i] = false;
    }
    instructionsPanel.resetControlPanel(this);
    showInstructions();
    mPlayer.play();
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
    if (inPart2)  //part 1 doesn't have any components to remove
    {
      remove(optionA);
      remove(optionB);
      remove(optionC);
    }
    remove(menuButton);
    frameUpdater.stop();
    pauseTime = System.currentTimeMillis();
  }
  
  @Override
  public void unPause()
  {
    if (inPart2)  //part 1 doesn't have any components to add
    {
      add(optionA);
      add(optionB);
      add(optionC);
    }
    add(menuButton);
    frameUpdater.start();
    totalPauseTime += System.currentTimeMillis() - pauseTime;
    repaint();
  }
  
  /**
   * Initializes the solids Rectangle array.
   */
  private void initializeSolids()
  {
    //borders
    for (int i = 0; i < solids[0].length; i++)
    {
      solids[0][i] = 1;
      solids[30][i] = 1;
    }
    for (int i = 1; i < solids.length-1; i++)
    {
      solids[i][0] = 1;
      solids[i][34] = 1;
    }
    
    //four in a row
    for (int i = 0; i < 4; i++)
    {
      solids[5][1 + i] = 1;
      solids[8][4 + i] = 1;
      solids[8][9 + i] = 1;
      solids[5][27 + i] = 1;
      solids[3][30 + i] = 1;
      solids[26][2 + i] = 1;
      solids[21][8 + i] = 1;
      solids[12][7 + i] = 1;
      solids[16][1 + i] = 1;
      solids[22][1 + i] = 1;
      solids[5][13 + i] = 1;
      solids[6][13 + i] = 1;
      solids[7][13 + i] = 1;
      solids[21][30 + i] = 1;
      solids[22][30 + i] = 1;
      solids[23][30 + i] = 1;
    }
    
    //six in a row
    for (int i = 0; i < 6; i++)
    {
      solids[4][12 + i] = 1;
      solids[12][12 + i] = 1;
      solids[14][12 + i] = 1;
      solids[17][14 + i] = 1;
      solids[25][14 + i] = 1;
      solids[13][22 + i] = 1;
      solids[21][1 + i] = 1;
      solids[17][22 + i] = 1;
      solids[17][28 + i] = 1;
      solids[20][22 + i] = 1;
      solids[20][28 + i] = 1;
      solids[9][12 + i] = 1;
      solids[9][18 + i] = 1;
      solids[12][1 + i] = 1;
      solids[19][4 + i] = 1;
    }
    
    //seven down in a row
    for (int i = 0; i < 7; i++)
    {
      solids[13 + i][10] = 1;
      solids[18 + i][14] = 1;
      solids[18 + i][19] = 1;
      solids[6 + i][27] = 1;
      solids[3 + i][24] = 1;
      solids[21 + i][22] = 1;
      solids[22 + i][11] = 1;
    }
    
    //three down in a row
    for (int i = 0; i < 3; i++)
    {
      solids[5 + i][12] = 1;
      solids[17 + i][1] = 1;
      solids[17 + i][2] = 1;
      solids[27 + i][5] = 1;
      solids[14 + i][22] = 1;
      solids[21 + i][23] = 1;
      solids[7 + i][33] = 1;
      solids[4 + i][21] = 1;
    }
    
    //two down in a row
    for (int i = 0; i < 2; i++)
    {
      solids[6 + i][4] = 1;
      solids[1 + i][24] = 1;
      solids[17 + i][4] = 1;
      solids[28 + i][22] = 1;
      solids[1 + i][18] = 1;
      solids[3 + i][18] = 1;
      solids[1 + i][27] = 1;
      solids[3 + i][27] = 1;
    }
    
    solids[4][30] = 1;
    solids[13][12] = 1;
    solids[28][1] = 1;
    solids[28][2] = 1;
    solids[28][3] = 1;
    solids[29][11] = 1;
    solids[10][29] = 1;
    solids[8][28] = 1;
    solids[8][29] = 1;
    solids[8][30] = 1;
    solids[1][6] = 1;
    solids[1][7] = 1;
    solids[1][8] = 1;
    solids[10][33] = 1;
  }
  
  /**
   * Initializes the doors Rectangle array.
   */
  private void initializeDoors()
  {
    doors[8][8] = 1;
    doors[9][21] = 1;
    doors[3][27] = 1;
    doors[11][27] = 1;
    doors[13][15] = 1;
    doors[13][17] = 1;
    doors[17][17] = 1;
    doors[19][9] = 1;
    doors[21][7] = 1;
    doors[26][1] = 1;
    doors[29][3] = 1;
    doors[25][22] = 1;
    doors[20][27] = 1;
    doors[3][32] = 1;
  }
  
  /**
   * Initializes the papers Rectangle array.
   */
  private void initializePapers()
  {
    //4 by 1 table
    for (int i = 0; i < 2; i++)
    {
      for (int j = 0; j < 4; j++)
      {
        papers[5 + i][6 + j] = 1;
        papers[5 + i][17 + j] = 1;
        papers[25 + i][7 + j] = 1;
        papers[24 + i][25 + j] = 1;
      }
    }
    
    //2 down
    for (int i = 0; i < 2; i++)
    {
      papers[15 + i][9] = 1;
      papers[5 + i][11] = 1;
      papers[12 + i][28] = 1;
      papers[12 + i][33] = 1;
      papers[14 + i][33] = 1;
    }
    
    papers[8][13] = 1;
    papers[1][28] = 1;
    papers[1][29] = 1;
    papers[4][33] = 1;
    papers[6][28] = 1;
    papers[13][6] = 1;
    papers[13][7] = 1;
    papers[13][8] = 1;
    papers[13][9] = 1;
    papers[22][9] = 1;
    papers[22][10] = 1;
    papers[24][18] = 1;
    papers[26][31] = 1;
    papers[26][32] = 1;
    papers[26][33] = 1;
  }
  
  /**
   * Initializes the cages Rectangle array.
   */
  private void initializeCages()
  {
    //3 by 2
    for (int i = 0; i < 2; i++)
    {
      for (int j = 0; j < 3; j++)
      {
        cages[1 + i][19 + j] = 1;
        cages[17 + i][5 + j] = 1;
        cages[19 + i][15 + j] = 1;
        cages[22 + i][16 + j] = 1;
        cages[15 + i][28 + j] = 1;
        cages[28 + i][8 + j] = 1;
        cages[28 + i][27 + j] = 1;
      }
    }
    
    //4 by 3
    for (int i = 0; i < 3; i++)
    {
      for (int j = 0; j < 4; j++)
      {
        cages[1 + i][1 + j] = 1;
        cages[1 + i][10 + j] = 1;
        cages[1 + i][14 + j] = 1;
        cages[13 + i][1 + j] = 1;
        cages[14 + i][23 + j] = 1;
        cages[27 + i][23 + j] = 1;
      }
    }
    
    //2 by 1
    for (int i = 0; i < 2; i++)
    {
      cages[4][1 + i] = 1;
      cages[4][3 + i] = 1;
      cages[1][22 + i] = 1;
      cages[16][32 + i] = 1;
      cages[25][2 + i] = 1;
      cages[25][4 + i] = 1;
      cages[29][30 + i] = 1;
      cages[29][32 + i] = 1;
    }
  }
  
  /**
   * Checks for various events, such as a door, papers or a cage, in the location in front of the player.
   * 
   * If a door is found, moves the player through if it is open or unlocked. Otherwise, tells the player the door is
   * locked.
   * If papers are found, checks if the papers have already been found. If so, tells the player so. If not, makes it
   * found and tells the user they have found new information.
   * If a cage is found, checks if the cage has already been found. If so, tells the player so. If not, makes it found
   * and tells the user they have found a new animal.
   * 
   * Then checks if all the information has been retrieved. If so, moves on to part 2.
   */
  private void checkEvents()
  {
    if (foundDoor() != -1) //found a door
    {
      if (foundDoor() == 7 || (foundDoor() != 8 && foundKeys[foundDoor()])) //no need key or has found the key
      {
        switch (spy.dir)
        {
          case 0: mapX -= 128;
                  spyCol += 2;
                  break;
          case 1: mapY -= 128;
                  spyRow += 2;
                  break;
          case 2: mapX += 128;
                  spyCol -= 2;
                  break;
          case 3: mapY += 128;
                  spyRow -= 2;
                  break;
        }
        repaint();
      }
      else
      {
        JOptionPane.showMessageDialog (null, "The door is locked.", "Locked", JOptionPane.INFORMATION_MESSAGE);
      }
    }
    else if (foundPapers() != -1) // found papers
    {
      if (foundPapers[foundPapers()])
      {
        JOptionPane.showMessageDialog (null, "You have already retrieved this information.", "Already Retrieved", JOptionPane.INFORMATION_MESSAGE);
      }
      else
      {
        foundPapers[foundPapers()] = true;
        JOptionPane.showMessageDialog (null, "You have found new information!", "Infomation Retrieved!", JOptionPane.INFORMATION_MESSAGE);
      }
    }
    else if (foundCage() != -1) //found a cage
    {
      if (foundAnimals[foundCage()])
      {
        JOptionPane.showMessageDialog (null, "You have already discovered this animal.", "Already Found", JOptionPane.INFORMATION_MESSAGE);
      }
      else
      {
        foundAnimals[foundCage()] = true;
        JOptionPane.showMessageDialog (null, "You have discovered a new animal!", "Animal Discoverd!", JOptionPane.INFORMATION_MESSAGE);
      }
    }
    else if (spyRow == 6 && spyCol == 2 && !foundKeys[0]) //on top of key 0
    {
      foundKeys[0] = true;
      JOptionPane.showMessageDialog (null, "You found a key!", "Key!", JOptionPane.INFORMATION_MESSAGE);
    }
    else if (spyRow == 17 && spyCol == 3 && !foundKeys[1]) //on top of key 1
    {
      foundKeys[1] = true;
      JOptionPane.showMessageDialog (null, "You found a key!", "Key!", JOptionPane.INFORMATION_MESSAGE);
    }
    else if (spyRow == 29 && spyCol == 1 && !foundKeys[2]) //on top of key 2
    {
      foundKeys[2] = true;
      JOptionPane.showMessageDialog (null, "You found a key!", "Key!", JOptionPane.INFORMATION_MESSAGE);
    }
    else if (spyRow == 1 && spyCol == 33 && !foundKeys[3]) //on top of key 3
    {
      foundKeys[3] = true;
      JOptionPane.showMessageDialog (null, "You found a key!", "Key!", JOptionPane.INFORMATION_MESSAGE);
    }
    else
    {
      if (spyRow == 13 && spyCol == 13 && !foundKeys[4]) //on top of key 4
      {
        foundKeys[4] = true;
        JOptionPane.showMessageDialog (null, "You found a key!", "Key!", JOptionPane.INFORMATION_MESSAGE);
      }
    }
    if (foundAllInfo())
    {
      pause();
      inPart2 = true;
      unPause();
      frameUpdater.stop();  //no longer needed
      repaint();
    }
  }
  
  /**
   * Checks if the location in front of the player is a door and if so, which door it is.
   * 
   * @return the door number of the door found. -1 if door not found. 7 is door doesn't require a key.
   */
  private int foundDoor()
  {
    if (spy.dir == 0) //right
    {
      if (doors[spyRow][spyCol + 1] == 1)
      {
        return findDoorNum(spyRow, spyCol + 1);
      }
    }
    else if (spy.dir == 1) //down
    {
      if (doors[spyRow + 1][spyCol] == 1)
      {
        return findDoorNum(spyRow + 1, spyCol);
      }
    }
    else if (spy.dir == 2) //left
    {
      if (doors[spyRow][spyCol - 1] == 1)
      {
        return findDoorNum(spyRow, spyCol - 1);
      }
    }
    else
    {
      if (spy.dir == 3) //up
      {
        if (doors[spyRow - 1][spyCol] == 1)
        {
          return findDoorNum(spyRow - 1, spyCol);
        }
      }
    }
    return -1;
  }
  
  /**
   * Finds the number of the door at a location.
   * 
   * @param int row of the location
   * @param int column of he location
   * @return an int door number, -1 if not found
   */
  private int findDoorNum(int row, int col)
  {
    if ((row == 8 && col == 8) || (row == 9 && col == 21) || (row == 19 && col == 9) || (row == 20 && col == 27) || (row == 25 && col == 22) || (row == 26 && col == 1) || (row == 29 && col == 3) || (row == 21 && col == 7))
      return 7; //unlocked doors
    if (row == 3 && col == 32)
      return 8; //always locked door
    if (row == 11 && col == 27)
      return 0;
    if (row == 3 && col == 27)
      return 1;
    if (row == 13 && col == 17)
      return 2;
    if (row == 13 && col == 15)
      return 3;
    if (row == 17 && col == 17)
      return 4;
    return -1;
  }
  
  /**
   * Checks if the location in front of the player has papers and if so, which papers it is.
   * 
   * @return the papers number of the papers found. -1 if papers not found.
   */
  private int foundPapers()
  {
    if (spy.dir == 0) //right
    {
      if (papers[spyRow][spyCol + 1] == 1)
      {
        return findPapersNum(spyRow, spyCol + 1);
      }
    }
    else if (spy.dir == 1) //down
    {
      if (papers[spyRow + 1][spyCol] == 1)
      {
        return findPapersNum(spyRow + 1, spyCol);
      }
    }
    else if (spy.dir == 2) //left
    {
      if (papers[spyRow][spyCol - 1] == 1)
      {
        return findPapersNum(spyRow, spyCol - 1);
      }
    }
    else
    {
      if (spy.dir == 3) //up
      {
        if (papers[spyRow - 1][spyCol] == 1)
        {
          return findPapersNum(spyRow - 1, spyCol);
        }
      }
    }
    return -1;
  }
  
  /**
   * Finds the number of the papers at a location.
   * 
   * @param int row of the location
   * @param int column of he location
   * @return an int papers number, -1 if not found
   */
  private int findPapersNum(int row, int col)
  {
    if (row >= 5 && row <= 6 && col >= 6 && col <= 9)
      return 0;
    if (row == 5 && col == 11)
      return 1;
    if (row == 6 && col == 11)
      return 2;
    if (row == 8 && col == 13)
      return 3;
    if (row >= 5 && row <= 6 && col >= 17 && col <= 20)
      return 4;
    if (row == 1 && col == 28)
      return 5;
    if (row == 1 && col == 29)
      return 6;
    if (row == 4 && col == 33)
      return 7;
    if (row == 6 && col == 28)
      return 8;
    if ((row == 12 || row == 13) && col == 28)
      return 9;
    if (row == 12  && col == 33)
      return 10;
    if ((row == 13 || row == 14) && col == 33)
      return 11;
    if (row == 15  && col == 33)
      return 12;
    if (row == 13  && col == 6)
      return 13;
    if (row == 13  && col == 7)
      return 14;
    if (row == 13  && col == 8)
      return 15;
    if (row == 13  && col == 9)
      return 16;
    if ((row == 15 || row == 16) && col == 9)
      return 17;
    if (row == 22  && col == 9)
      return 18;
    if (row == 22  && col == 10)
      return 19;
    if (row >= 25 && row <= 26 && col >= 7 && col <= 10)
      return 20;
    if (row == 24  && col == 18)
      return 21;
    if (row == 26  && col == 31)
      return 22;
    if (row == 26  && col == 32)
      return 23;
    if (row == 26  && col == 33)
      return 24;
    if (row >= 24 && row <= 25 && col >= 25 && col <= 28)
      return 25;
    return -1;
  }
  
  /**
   * Checks if the location in front of the player is a cage and if so, which cage it is.
   * 
   * @return the cage number of the cage found. -1 if cage not found.
   */
  private int foundCage()
  {
    if (spy.dir == 0) //right
    {
      if (cages[spyRow][spyCol + 1] == 1)
      {
        return findCageNum(spyRow, spyCol + 1);
      }
    }
    else if (spy.dir == 1) //down
    {
      if (cages[spyRow + 1][spyCol] == 1)
      {
        return findCageNum(spyRow + 1, spyCol);
      }
    }
    else if (spy.dir == 2) //left
    {
      if (cages[spyRow][spyCol - 1] == 1)
      {
        return findCageNum(spyRow, spyCol - 1);
      }
    }
    else
    {
      if (spy.dir == 3) //up
      {
        if (cages[spyRow - 1][spyCol] == 1)
        {
          return findCageNum(spyRow - 1, spyCol);
        }
      }
    }
    return -1;
  }
  
  /**
   * Finds the number of the cage at a location.
   * 
   * @param int row of the location
   * @param int column of he location
   * @return an int cage number, -1 if not found
   */
  private int findCageNum(int row, int col)
  {
    if (row >= 1 && row <= 4 && col >= 1 && col <= 4)
      return 0;
    if (row >= 1 && row <= 3 && col >= 10 && col <= 17)
      return 1;
    if (row >= 1 && row <= 2 && col >= 19 && col <= 21)
      return 2;
    if (row == 1 && col >= 22 && col <= 23)
      return 3;
    if (row >= 13 && row <= 15 && col >= 1 && col <= 4)
      return 4;
    if (row >= 17 && row <= 18 && col >= 5 && col <= 7)
      return 5;
    if (row >= 14 && row <= 16 && col >= 23 && col <= 26)
      return 6;
    if (row >= 15 && row <= 16 && col >= 28 && col <= 30)
      return 7;
    if (row == 16 && col >= 32 && col <= 33)
      return 8;
    if (row >= 19 && row <= 20 && col >= 15 && col <= 17)
      return 9;
    if (row >= 22 && row <= 23 && col >= 16 && col <= 18)
      return 10;
    if (row == 25 && col >= 2 && col <= 3)
      return 11;
    if (row == 25 && col >= 4 && col <= 5)
      return 12;
    if (row >= 28 && row <= 29 && col >= 8 && col <= 10)
      return 13;
    if (row >= 27 && row <= 29 && col >= 23 && col <= 26)
      return 14;
    if (row >= 28 && row <= 29 && col >= 27 && col <= 29)
      return 15;
    if (row == 29 && col >= 30 && col <= 31)
      return 16;
    if (row == 29 && col >= 32 && col <= 33)
      return 17;
    return -1;
  }
  
  /**
   * Checks if all papers and animals have been found.
   * 
   * @return true is all information has been found. Otherwise, false.
   */
  private boolean foundAllInfo()
  {
    for (int i = 0; i < foundPapers.length; i++)
    {
      if (!foundPapers[i])
      {
        return false;
      }
    }
    for (int i = 0; i < foundAnimals.length; i++)
    {
      if (!foundAnimals[i])
      {
        return false;
      }
    }
    return true;
  }
  
  /**
   * Stops the frame updater and music, then passes the player's score and program flow back to the driver class.
   */
  private void complete()
  {
    frameUpdater.stop();
    mPlayer.stop();
    CampXApp.darwinComplete(getTimeLeft());
  }
  
  @Override
  public void fail()
  {
    mPlayer.stop();
    Cutscene failScene = new Cutscene ("Images/Darwin/Fail", -1);
    pause();
    CampXApp.frame.getContentPane().remove(this);
    CampXApp.frame.add(failScene);
    CampXApp.frame.repaint();
    failScene.start();
  }
}