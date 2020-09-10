import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.io.*;
import javax.imageio.ImageIO;


import javax.swing.JOptionPane;

/**
 * Tigerclaw Corp. - Yuan Y. and Jaclyn W.
 * <p>
 * VK Enterprises - Ms Krasteva
 * <p>
 * Time spent: 2 h 20 mins
 * <p>
 * 
 * This program is a JPanel created to be a main menu. It allows the player to start the game,
 * view the spy ranking, view the credits and exit the game.
 * 
 * <p>
 * <b>Class Variables</b>
 * <p>
 * <b>startButton</b> a private variable which creates an instance of the CustomButton class.
 * <p>
 * <b>spyRankingsButton</b> a private variable which creates an instance of the CustomButton class.
 * <p>
 * <b>creditsButton</b> a private variable which creates an instance of the CustomButton class.
 * <p>
 * <b>exitButton</b> a private variable which creates an instance of the CustomButton class.
 * <p>
 * <b>background</b> a private variable which creates an instance of the Image class which paints this component.
 * <p>
 * 
 * MODIFICATION    Jaclyn Woon, Ver 4 June 4, 2017, Time spent: 15 mins
 * <p>
 * Jaclyn        delete testing code an replace System printing to JOptionPane dialogue
 * <p>
 * Jaclyn        edit JavaDoc
 * <p>
 * 
 * @version 4 June 4, 2017
 * @author Yuan Yang and Jaclyn Woon
 */
public class MainMenu extends JPanel implements MouseListener
{
  private CustomButton startButton;
  private CustomButton spyRankingsButton;
  private CustomButton creditsButton;
  private CustomButton exitButton;
  private Image background;
  
  /** This constructor constructs a new main menu JPanel. It initializes the 
   * fields, sets all constraint and enables all buttons.
   */
  public MainMenu ()
  {
    setBounds (0, 0, 1000, 800);
    //initialiation
    try
    {
      background = ImageIO.read(new File("Images/MainMenu/background.jpg"));
    }
    catch (IOException e)
    {
      JOptionPane.showMessageDialog (null, "Main menu background image not found.", "Error.", JOptionPane.ERROR_MESSAGE);
      System.exit(-1);
    }
    startButton = new CustomButton ("Images/MainMenu/startGameButton_mouseExited.jpg");
    spyRankingsButton = new CustomButton ("Images/MainMenu/spyRankingsButton_mouseExited.jpg");
    creditsButton = new CustomButton ("Images/MainMenu/creditsButton_mouseExited.jpg");
    exitButton = new CustomButton ("Images/MainMenu/exitGameButton_mouseExited.jpg");
    //ActionListeners
    startButton.addMouseListener (this);
    spyRankingsButton.addMouseListener (this);
    creditsButton.addMouseListener (this);
    exitButton.addMouseListener (this);
    //add
    startButton.setBounds(322, 260, startButton.getWidth(), startButton.getHeight());
    spyRankingsButton.setBounds(322, 375, spyRankingsButton.getWidth(), spyRankingsButton.getHeight());
    creditsButton.setBounds(325, 491, creditsButton.getWidth(), creditsButton.getHeight());
    exitButton.setBounds(327, 600, exitButton.getWidth(), exitButton.getHeight());
    add(startButton);
    add(spyRankingsButton);
    add(creditsButton);
    add(exitButton);
  }
  
  @Override
  public void paintComponent (Graphics g)
  {
    super.paintComponent(g);
    g.drawImage (background, 0, 0, null);
  }
  
  @Override
  /** 
   * This method goes to different screens when their corresponding buttons are 
   * clicked.
   * 
   * @param me - the MouseEvent object used to identify event origin.
   */
  public void mouseClicked (MouseEvent me)
  {
    if (me.getSource() == startButton)
    {
      CampXApp.startGame();
    }
    else if (me.getSource() == spyRankingsButton)
    {
      disableAll();
      SpyRankings sr = new SpyRankings(this);
      add(sr);
      repaint();
    }
    else if (me.getSource() == creditsButton)
    {
      disableAll();
      Credits c = new Credits(this);
      c.start();
      add(c);
      repaint();
    }
    else
    {
      if (me.getSource() == exitButton)
      {
        System.exit(0);
      }
    }
    mouseExited(me);
  }
  
  @Override
  /**
   * This method changes the image of the corresponding buttons.
   * 
   * @param me - the MouseEvent object used to identify event origin.
   */
  public void mouseEntered(MouseEvent me)
  {
    if (me.getSource() == startButton)
    {
      startButton.changeImg("Images/MainMenu/startGameButton_mouseEntered.jpg");
      repaint();
    }
    else if (me.getSource() == spyRankingsButton)
    {
      spyRankingsButton.changeImg("Images/MainMenu/spyRankingsButton_mouseEntered.jpg");
      repaint();
    }
    else if (me.getSource() == creditsButton)
    {
      creditsButton.changeImg("Images/MainMenu/creditsButton_mouseEntered.jpg");
      repaint();
    }
    else
    {
      if (me.getSource() == exitButton)
      {
        exitButton.changeImg("Images/MainMenu/exitGameButton_mouseEntered.jpg");
        repaint();
      }
    }
  }
  
  @Override
  /**
   * This method changes the image of the corresponding buttons.
   * 
   * @param me - the MouseEvent object used to identify event origin.
   */
  public void mouseExited(MouseEvent me)
  {
    if (me.getSource() == startButton)
    {
      startButton.changeImg("Images/MainMenu/startGameButton_mouseExited.jpg");
      repaint();
    }
    else if (me.getSource() == spyRankingsButton)
    {
      spyRankingsButton.changeImg("Images/MainMenu/spyRankingsButton_mouseExited.jpg");
      repaint();
    }
    else if (me.getSource() == creditsButton)
    {
      creditsButton.changeImg("Images/MainMenu/creditsButton_mouseExited.jpg");
      repaint();
    }
    else
    {
      if (me.getSource() == exitButton)
      {
        exitButton.changeImg("Images/MainMenu/exitGameButton_mouseExited.jpg");
        repaint();
      }
    }
  }
  
  @Override
  public void mousePressed(MouseEvent me)
  {
  }
  
  @Override
  public void mouseReleased(MouseEvent me)
  {
  }
  
  /**
   * This method disables all the buttons.
   */
  private void disableAll ()
  {
    remove(startButton);
    remove(spyRankingsButton);
    remove(creditsButton);
    remove(exitButton);
  }
  
  /**
   * This method enables all the buttons and repaints the screen.
   */
  public void enable()
  {
    add(startButton);
    add(spyRankingsButton);
    add(creditsButton);
    add(exitButton);
    repaint();
  }
}