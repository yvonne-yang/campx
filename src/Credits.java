import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.imageio.ImageIO;

/**
 * Tigerclaw Corp. - Yuan Y. and Jaclyn W.
 * <p>
 * VK Enterprises - Ms Krasteva
 * <p>
 * Time spent: 1 h 50 min
 * <p>
 * This class is a scrolling animation of the credits for the game "CampX".
 * <p>
 * <b>Class Variables</b>
 * <p>
 * <b>menu</b> A MainMenu object.
 * <p>
 * <b>img</b> The credits image.
 * <p>
 * <b>backToMenuButoon</b> The CustomButton object that goes back to the main menu.
 * <p>
 * <b>scroller</b> Tbe Timer object that updates the screen and makes smooth animation.
 * <p>
 * <b>yCoord</b> The y-coordinate of the top-left of the image on the screen.
 * <p>
 * MODIFICATION
 * <p>
 * Author    Date      Time        Modification
 * <p>
 * Yuan      Jun 4     10 min      Fix comments and fix scrolling animation.
 * 
 * @version 3 Jun 4, 2017
 * @author Yuan Yang and Jaclyn Woon
 */
public class Credits extends JPanel implements ActionListener
{
  private MainMenu menu;
  private Image img;
  private CustomButton backToMenuButton;
  private Timer scroller;
  private int yCoord;
  
  /**
   * This constructor constructs a new credits JPanel.
   */
  public Credits()
  {
    setBounds (0, 0, 1000, 800);
    setVisible(true);
    scroller = new Timer (10, this);
    yCoord = 0;
    
    try 
    {
      img = ImageIO.read(new File("Images/Credits/credits.jpg"));
    }
    catch (IOException ex)
    {
      JOptionPane.showMessageDialog (null, "Credits image not found.", "Error.", JOptionPane.ERROR_MESSAGE);
      System.exit(-1);
    }
  }
  
  /**
   * This constructor constructs a new credits JPanel.
   * 
   * @param m - the MainMenu object controling this Credits object
   */
  public Credits (MainMenu m)
  {
    this();
    menu = m;
    
    backToMenuButton = new CustomButton ("Images/Credits/backToMenuButton.jpg");
    add(backToMenuButton);
    backToMenuButton.setBounds (700, 680, backToMenuButton.getWidth(), backToMenuButton.getHeight());
    backToMenuButton.addActionListener(this);
  }
  
  @Override
  public void paintComponent (Graphics g)
  {
    super.paintComponent(g);
    
    g.drawImage(img, 0, yCoord, null);
  }
  
  /**
   * Starts the scrolling animation.
   */
  public void start()
  {
    scroller.start();
    yCoord = 0;
  }
  
  @Override
  /**
   * The actionPerformed method checks for events and where they came from.
   * It performs the specific action for each origin.
   * It panes the image up every if the source is the scroller Timer. If the image has been scrolled all the way,
   * it removes itself and continues program flow elsewhere.
   * It removes itself and continues program flow elsewhere if the source is the backToMenuButton
   * 
   * @param ae ActionEvent object used to identify the event origin
   */
  public void actionPerformed (ActionEvent ae)
  {
    if (ae.getSource() == scroller)
    { 
      yCoord -= 1;
      if (yCoord <= -1300)
      {
        scroller.stop();
        if (backToMenuButton == null)
        {
          CampXApp.frame.getContentPane().remove(this);
          CampXApp.toRankings();
        }
        else
        {
          menu.enable();
          menu.remove(this);
        }
      }
      else
      {
        repaint();
      }
    }
    if (backToMenuButton != null && ae.getSource() == backToMenuButton)
    {
      menu.enable();
      menu.remove(this);
    }
  }
}