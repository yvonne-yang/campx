import javax.swing.*;
import java.awt.*;

import javafx.scene.media.*;

import java.io.*;
import javax.imageio.ImageIO;

/** 
 * Tigerclaw Corp. - Yuan Y. and Jaclyn W.
 * <p>
 * VK Enterprises - Ms Krasteva
 * <p>
 * Time spent: 30 min
 * <p>
 * 
 * This program displays a splashscreen in a JPanel.
 * It displays the Tigerclaw Corp. company logo.
 * 
 * <p>
 * <b>Class Variables</b>
 * <p>
 * <b>logo</b> The picture of the splash screen.
 * <p>
 * <b>prustenPlayer</b> The MediaPlayer object that plays the prusten sound effect.
 * <p>
 * 
 * MODIFICATION
 * <p>
 * Author    Date      Time        Modification
 * <p>
 * Yuan      Jun 4     10 min      Fix comments and add prusten SE.
 * 
 * @version 3 Jun 4, 2017
 * @author Yuan Yang
 */
public class SplashScreen extends JPanel
{
  private Image logo;
  private MediaPlayer prustenPlayer;
  
  /**
   * This constructor constructs a new SplashScreen JPanel. It initializes the 
   * fields.
   */
  public SplashScreen ()
  {
    prustenPlayer = new MediaPlayer (new Media(new File("Music/prusten.mp3").toURI().toString()));
    try
    {
      logo = ImageIO.read(new File("images/Splashscreen/splashscreen.jpg"));
    } 
    catch (IOException e) 
    {
      JOptionPane.showMessageDialog (null, "Splashscreen image not found.", "Error.", JOptionPane.ERROR_MESSAGE);
      System.exit(-1);
    }
    setBounds (0, 0, 1000, 800);
  }
  
  @Override
  public void paintComponent (Graphics g)
  {
    super.paintComponent(g);
    g.drawImage (logo, 0, 0, null);
  }
  
  /** This method plays the prusten sound effect.
    */
  public void playSE ()
  {
    prustenPlayer.play();
  }
}