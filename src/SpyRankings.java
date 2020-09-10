import javax.swing.*;
import java.awt.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.event.*;
import java.util.ArrayList;   

/** 
 * Tigerclaw Corp. - Yuan Y. and Jaclyn W.
 * <p>
 * VK Enterprises - Ms Krasteva
 * <p>
 * Time spent: 6 h
 * <p>
 * 
 * This program creates a ranking of the top ten "spies" (players) on a JPanel which can be added to the main game JFrame.
 * 
 * <p>
 * <b>Class Variables</b>
 * <p>
 * <b>FILENAME</b> The absolute file name to the .txt file that contains information about rankings.
 * <p>
 * <b>DIRECTORY</b> The absolute file directory to the FILENAME file that contains information about rankings.
 * <p>
 * <b>file</b> The file used to check if the directory and file exists, and to read and write.
 * <p>
 * <b>NUMBER_OF_TOP_SPIES</b> The total number of top spies that are displayed.
 * <p>
 * <b>spyNames</b> A list of top spy names.
 * <p>
 * <b>spyContribPoints</b> A list of the contribution points of top spies.
 * <p>
 * <b>in</b> The object used to read from the file.
 * <p>
 * <b>backToMenuButton</b> The button that goes back to the main menu.
 * <p>
 * <b>resetButton</b> The button that resets the rankings.
 * <p>
 * <b>titleImage</b> The background image of the spy rankings screen.
 * <p>
 * <b>menu</b> A main menu object.
 * <p>
 * 
 * MODIFICATION
 * <p>
 * Author    Date      Time        Modification
 * <p>
 * Yuan      Jun 2     5 min       Add errortrap for code name.
 * <p>
 * Jaclyn    Jun 3     10 min      Save file to computer.
 * <p>
 * Jaclyn    Jun 4     10 min      Fix comments and add reset button
 * 
 * @version 4 Jun 4, 2017
 * @author Yuan Yang and Jaclyn Woon
 */
public class SpyRankings extends JPanel implements ActionListener
{
  private final String FILENAME = "/savedRankings.txt";
  private final String DIRECTORY = "C:/CampX";
  private File file = new File (DIRECTORY);
  private final int NUMBER_OF_TOP_SPIES = 10;
  private ArrayList<String> spyNames;   
  private ArrayList<Integer> spyContribPoints;   
  private BufferedReader in;
  private CustomButton backToMenuButton;  
  private CustomButton resetButton;  
  private Image titleImage;
  private MainMenu menu;
  
  
  /** 
   * This constructor constructs a new spy rankings JPanel. It initializes the 
   * fields.
   * <p>   
   * <b>Local Variables</b>   
   * <b>numOfLines</b> Stores the total number of non-empty lines in the file.
   * <p>
   * 
   * @param m - A MainMenu object.   
   */
  public SpyRankings (MainMenu m)
  {
    if (m != null)
      menu = m;
    setBounds (0, 0, 1000, 800);
    int numOfLines;
    
    spyNames = new ArrayList <String> ();
    spyContribPoints = new ArrayList <Integer> ();
    
    if (!file.exists())
    {
      createFile();
    }
    file = new File (DIRECTORY + FILENAME);
    
    try
    {
      titleImage = ImageIO.read(new FileInputStream("Images/SpyRankings/background.jpg"));
    } 
    catch (IOException e) 
    {
      JOptionPane.showMessageDialog (null, "SpyRankings background image not found.", "Error.", JOptionPane.ERROR_MESSAGE);
      System.exit(-1);
    }
    
    numOfLines = countNumOfLines();
    storeFileIntoLists(numOfLines);
    
    backToMenuButton = new CustomButton ("Images/SpyRankings/backToMenuButton.jpg");
    add(backToMenuButton);
    backToMenuButton.setBounds (700, 680, backToMenuButton.getWidth(), backToMenuButton.getHeight());    
    backToMenuButton.addActionListener(this);
    
    resetButton = new CustomButton ("Images/SpyRankings/resetButton.jpg");
    add(resetButton);
    resetButton.setBounds (550, 680, resetButton.getWidth(), resetButton.getHeight());    
    resetButton.addActionListener(this);
    
    repaint();    
  }
  
  @Override
  public void paintComponent (Graphics g)
  {
    super.paintComponent(g);
    
    g.drawImage (titleImage, 0, 0, null);
    
    g.setColor (Color.white);    
    g.setFont (new Font ("Georgia", 0, 25));    
    for (int i = 0; i < NUMBER_OF_TOP_SPIES; i++)    
    {    
      g.drawString ((i+1) + ".", 360, 150 + i * 50);    
      if (i < spyNames.size() && i < spyContribPoints.size())    
      {    
        g.drawString (spyNames.get (i), 400, 150 + i * 50);    
        g.drawString (spyContribPoints.get (i).toString(), 800, 150 + i * 50);    
      }    
    }    
  }
  
  /**
   * This method goes back to the main menu when the "Back To Main Menu" button   
   * is clicked. If the reset button is clicked, it erases all rankings.
   * 
   * @param ae - The ActionEvent that was performed.   
   */
  public void actionPerformed (ActionEvent ae)
  {
    if (ae.getSource() == backToMenuButton)
    {
      if (menu == null)
      {
        CampXApp.toMainMenu();
      }
      else
      {
        menu.setEnabled(true);
        menu.remove(this);
      }
    }
    else
    {
      if (ae.getSource() == resetButton)
      {
        spyNames.clear();
        spyContribPoints.clear();
        storeListsIntoFile();
        repaint();
      }
    }
  }
  
  /** 
   * This method is called by the driver class when a game is won. It checks the   
   * current score with the ones on the ranking list to see if it should be        
   * placed on the list. If so, a dialog window pops up to ask for the player's    
   * name. The new list is then recorded into the saved file.   
   * <p>
   * <b>Local Variables</b>   
   * <p>
   * <b>name</b> Stores the player's codename, "anonymous spy" by default.    
   * 
   * @param currentScore - The score of the current player who just won the game.   
   */   
  public void newEntry (int currentScore)   
  { 
    String name;    
    
    name = askForName();    
    if (name == null || name == "")    
      name = "anonymous spy";    
    
    //Check if the player made it into the top ten    
    if (spyNames.size () == 0 && spyContribPoints.size() == 0) //first winner    
    {    
      spyNames.add (name);    
      spyContribPoints.add (currentScore);    
    }    
    else    
    {    
      for (int i = 0; i < spyContribPoints.size(); i++)     
      {    
        if (currentScore > spyContribPoints.get(i))    
        {    
          spyNames.add (i, name);    
          spyContribPoints.add (i, currentScore);    
          if (spyNames.size() > NUMBER_OF_TOP_SPIES && spyContribPoints.size() > NUMBER_OF_TOP_SPIES)    
          {    
            spyNames.remove (spyNames.size() - 1);    
            spyContribPoints.remove (spyContribPoints.size() - 1);    
          }    
          break;    
        }    
        else    
        {    
          if (i == spyContribPoints.size() - 1 &&     
              spyNames.size() < NUMBER_OF_TOP_SPIES && spyContribPoints.size() < NUMBER_OF_TOP_SPIES)    
          {    
            spyNames.add (name);    
            spyContribPoints.add (currentScore);    
            break;    
          }    
        }    
      }    
    }    
    storeListsIntoFile();    
    repaint();    
  }
  
  /**
   * Creates the needed directory and file on the system.
   */
  private void createFile()
  {
    file = new File(DIRECTORY);
    file.mkdir();   //creates the file directory
    file = new File (DIRECTORY + FILENAME);
    storeListsIntoFile();  //creates the file
  }
  
  /**
   * This method helps count the number of non-empty lines in the saved file.
   * <p>   
   * <b>Local Variables<\b>   
   * <p>
   * <b>line</b> Temporarily stores the contents of a line in the file.   
   * <p>
   * <b>n</b> Counter for the total number of lines.   
   * 
   * @return The total number of non-empty lines in the saved file.
   */
  private int countNumOfLines ()   
  {
    String line = "";   
    int n = 0;   
    try   
    {   
      in = new BufferedReader (new FileReader (file));
      line = in.readLine();   
      while (line != null)   
      {   
        n ++;   
        line = in.readLine();   
      }   
      in.close();  
      return n;  
    }   
    catch (IOException e)   
    {   
      createFile();
      return countNumOfLines();
    }     
  }
  
  /**
   * This method helps read the saved file and store the information into the 
   * ArrayLists in this class.
   * 
   * @param numOfLines - The total number of non-empty lines in the file.
   */
  private void storeFileIntoLists (int numOfLines)
  {
    try   
    {   
      in = new BufferedReader (new FileReader (file));
      for (int i = 0; i < numOfLines; i += 2)   
      {   
        spyNames.add (in.readLine());   
        spyContribPoints.add (Integer.parseInt (in.readLine()));  
      }   
      in.close();
    }   
    catch (IOException e)   
    {   
      createFile();
      storeFileIntoLists(numOfLines);
    }   
  }
  
  /**
   * This method helps store the rankings info into the file.   
   * <p>   
   * <b>Local Variables<\b>   
   * out - The object used to write info into a file.   
   */
  private void storeListsIntoFile ()
  {
    PrintWriter out;   
    try   
    {   
      out = new PrintWriter (new FileWriter (DIRECTORY + FILENAME));   
      for (int i = 0; i < spyNames.size(); i++)   
      {   
        out.println (spyNames.get(i));   
        out.println (spyContribPoints.get (i));   
      }   
      out.close();   
    }   
    catch (IOException e)   
    {   
      createFile();
    }   
  }
  
  /**
   * This method helps ask for a name in a pop-up JOptionPane window.    
   * <p>    
   * <b>Local Variables</b>
   * <p>
   * <b>name</b> Stores the user input in the JOptionPane.    
   * 
   * @return Returns a String that is the inputted code name.
   */    
  private String askForName ()    
  { 
    String name;    
    name = JOptionPane.showInputDialog (this, "Please enter your code name: ", "Code name", JOptionPane.PLAIN_MESSAGE);    
    while (name.length() >= 25)
    {
      name = JOptionPane.showInputDialog (this, "The code name can not be more than 25 characters.\nPlease enter again: ", "Code name", JOptionPane.ERROR_MESSAGE);    
    }
    return name;    
  }
}