import java.awt.*;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.*;
import javax.imageio.ImageIO;

import javax.swing.JPanel;
import javax.swing.JOptionPane;

/**
 * Tigerclaw Corp. - Yuan Y. and Jaclyn W.
 * <p>
 * VK Enterprises - Ms. Krasteva
 * <p>
 * Time spent: 1 h 15 mins
 * <p>
 * 
 * This program is a JPanel which displays several images loaded from a file as pages.
 * The images have instructions on them. It also has a button that allows the user to
 * continue to the next page or to the game if all the pages have been displayed.
 * 
 * <p>
 * <b>Class Variables</b>
 * <p>
 * <b>continueButton</b> a private variable which creates an instance of the CustomButton class.
 * <p>
 * <b>gamePanel</b> stores the reference to the GamePanel class that creates this.
 * <p>
 * <b>menu</b> stores the reference to the GameMenu class that controls this.
 * <p>
 * <b>numOfPages</b> a private variable which stores an integer of the number of images in the folder.
 * <p>
 * <b>pages</b> a private variable which creates an array of instances of the Image class.
 * <p>
 * <b>pageNum</b> a private variable which stores an integer of the current page number being displayed.
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
 * @author Jaclyn Woon and Yuan Yang
 */
public class InstructionsPanel extends JPanel implements ActionListener
{
  private CustomButton continueButton;
  private GamePanel gamePanel;
  public GameMenu menu;
  private int numOfPages;
  private Image[] pages;
  private int pageNum;
  
  /**
   * This constructor is used to create a new instance of this class.
   * It sets the layout to null for absolute positioning and makes the panel actomatically selected.
   * It also sets the bounds and initializes all variables.
   * It adds an actionListener to continueButton, sets its bounds and adds it the the Panel.
   * 
   * @param folderName String used to identify the folder to read images from
   * @param num int that determines the number od images in the folder
   * @param game GamePanel which creates and controls this class
   */
  public InstructionsPanel (String folderName, int num, GamePanel game)
  {
    setBounds(0, 0, 1000, 800);
    //initializations
    gamePanel = game;
    numOfPages = num;
    pages = new Image[numOfPages];
    loadImages(folderName);
    pageNum = 0;
    continueButton = new CustomButton ("Images/continueButton.jpg");
    
    //listeners
    continueButton.addActionListener (this);
    
    setLayout(null);
    continueButton.setBounds (700, 650, continueButton.getWidth(), continueButton.getHeight());
    add(continueButton);
    
    setVisible(true);
  }
  
  @Override
  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);
    g.drawImage (pages[pageNum], 0, 0, null);
  }
  
  @Override
  /**
   * The actionPerformed method checks for events and where they came from.
   * It performs the specific action for each origin.
   * It ends the instructions if all pages have been displayed and the user clicks continue button.
   * Otherwise, the next page is displayed.
   * 
   * @param ae ActionEvent object used to identify the event origin
   */
  public void actionPerformed(ActionEvent ae) {
    if (ae.getSource() == continueButton)
    {
      if (pageNum + 1 >= numOfPages)
      {
        pageNum = 0;
        if (gamePanel != null)
        {
          gamePanel.endInstructions();
        }
        else
        {
          menu.endInstructions();
        }
      }
      else
      {
        pageNum++;
        repaint();
      }
    }
  }
  
  /**
   * This method changes the control panel.
   * It makes menu equal the reference to the new control panel and sets gamePanel to null.
   * 
   * @param gm GameMenu object reference used to identify the control panel
   */
  public void changeControlPanel(GameMenu gm)
  {
    menu = gm;
    gamePanel = null;
  }
  
  /**
   * This method resets the control panel.
   * It makes menu equal null and and sets gamePanel to the reference to the game panel controlling it.
   * 
   * @param gp GamePanel object reference used to identify the control panel
   */
  public void resetControlPanel (GamePanel gp)
  {
    menu = null;
    gamePanel = gp;
  }
  
  /**
   * This method initilizes the pages Image array by loading from the folderName.
   * 
   * @param folderName String used to identify the folder to read from
   */
  private void loadImages(String folderName)
  {
    try
    {
      for (int i = 0; i < numOfPages; i++)
      {
        pages[i] = ImageIO.read(new File(folderName+ "page" + (i+1) + ".jpg"));
      }
    }
    catch (IOException e)
    {
      JOptionPane.showMessageDialog (null, "Instructions image(s) not found.", "Error.", JOptionPane.ERROR_MESSAGE);
      System.exit(-1);
    }
  }
}