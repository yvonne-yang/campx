import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import java.io.*;
import javax.imageio.ImageIO;

import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 * Tigerclaw Corp. - Yuan Y. and Jaclyn W.
 * <p>
 * VK Enterprises - Ms. Krasteva
 * <p>
 * Time spent: 1 h 45 mins
 * <p>
 * This program is a JButton which allows its looks to be cuztomized completely by importing a picture.
 * The picure can also be changed easily.
 * 
 * <p>
 * <b>Class Variables</b>
 * <p>
 * <b>dimensions</b> creates an instance of the Dimension class which stores the dimensions of the button
 * <p>
 * <b>img</b> creates an instance of the BufferedImage class which stores the image of the button.
 * <p>
 * 
 * MODIFICATION: Jaclyn Woon, Ver 5 June 4, 2017, Time spent: 15 mins
 * <p>
 * Author        Modificiation
 * <p>
 * Jaclyn        delete testing code an replace System printing to JOptionPane dialogue
 * <p>
 * Jaclyn        edit JavaDoc
 * <p>
 * 
 * @version 5 June 4, 2017
 * @author Jaclyn Woon
 */
public class CustomButton extends JButton
{
  private Dimension dimensions;
  private BufferedImage img;
  
  /**
   * This constructor creates a new CustomButton object with an image
   * loaded from the filePath parameter and dimensions of that image.
   * 
   * @param filePath - the String path to the desired image
   */
  public CustomButton (String filePath)
  {
    try 
    {
      img = ImageIO.read(new File(filePath));
    }
    catch (IOException ex)
    {
      JOptionPane.showMessageDialog (null, "Button image not found.", "Error.", JOptionPane.ERROR_MESSAGE);
      System.exit(-1);
    }
    dimensions = new Dimension (img.getWidth(), img.getHeight());
    
    setPreferredSize (dimensions);
    setMaximumSize (dimensions);
    setMinimumSize (dimensions);
  }
  
  @Override
  public void paint (Graphics g)
  {
    g.drawImage(img, 0, 0, null);
  }
  
  /**
   * This method provides easier access to the width of the image.
   * 
   * @return the width of the button.
   */
  public int getWidth()
  {
    return dimensions.width;
  }
  
  /**
   * This method provides easier access to the height of the image.
   * 
   * @return the height of the button.
   */
  public int getHeight()
  {
    return dimensions.height;
  }
  
  /**
   * This method changes the button img.
   * 
   * @param filename - the filename of the new img.
   */
  public void changeImg(String filename)
  {
    try 
    {
      img = ImageIO.read(new File(filename));
    }
    catch (IOException ex)
    {
      JOptionPane.showMessageDialog (null, "Button image not found.", "Error.", JOptionPane.ERROR_MESSAGE);
      System.exit(-1);
    }
    dimensions = new Dimension (img.getWidth(), img.getHeight());
  }
  
  @Override
  public Dimension getPreferredSize()
  {
    return dimensions;
  }
  
  @Override
  public Dimension getMaximumSize()
  {
    return dimensions;
  }
  
  @Override
  public Dimension getMinimumSize()
  {
    return dimensions;
  }
}