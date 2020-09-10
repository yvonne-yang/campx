import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.util.Arrays;

/** 
 * Tigerclaw Corp. - Yuan Y. and Jaclyn W.
 * <p>
 * VK Enterprises - Ms Krasteva
 * <p>
 * Time spent: 1 h 45 min
 * <p>
 * 
 * This program displays any number of images in a folder as a cutscene.
 * The images are displyed one after another at 2 second-intervals.
 * 
 * <p>
 * <b>Class Variables</b>
 * <p>
 * <b>scenes</b> stores an array of Image objects to be displayed.
 * <p>
 * <b>numOfScenes</b> stores the total number of scenes.
 * <p>
 * <b>sceneNum</b> stores the number of the current scene being displayed.
 * <p>
 * <b>skipButton</b> creates an instance of the CustomButton class.
 * <p>
 * <b>cutNum</b> stores the cutscene number which determines program flow.
 * <p>
 * <b>sceneChanger</b> creates an instance of the Timer class which changes the scene.
 * <p>
 * 
 * MODIFICATION
 * <p>
 * Author    Date      Time        Modification
 * <p>
 * Yuan      Jun 4     10 min      Fix comments and uncomment testing code.
 * 
 * @version 4 June 3, 2017
 * @author Yuan Yang and Jaclyn Woon
 */
public class Cutscene extends JPanel implements ActionListener
{
  private Image [] scenes;
  private int numOfScenes;
  private int sceneNum;
  private CustomButton skipButton;
  private int cutNum;
  private Timer sceneChanger;
  
  /** This constructor constructs a new Cutscene JPanel. It initializes the 
   * fields, sets contraints and adds the skipButton.
   * 
   * @param folderPath - the path the folder containing the cutscene images
   * @param cutNum - the cutscene number
   */
  public Cutscene (String folderPath, int cutNum)
  {
    this.cutNum = cutNum;
    
    sceneNum = 0;
    
    File folder = new File (folderPath);
    File [] listFiles;
    if (folder.isDirectory()) { // make sure it exists and is a folder
      listFiles = folder.listFiles();
      Arrays.sort(listFiles);
      numOfScenes = listFiles.length;
      scenes = new Image [numOfScenes];
      try{
        for (int i = 0; i <listFiles.length; i++) //read into Image objects
          scenes [i] = ImageIO.read (listFiles [i]);
      }
      catch (IOException e)
      {
        JOptionPane.showMessageDialog (null, "Cutscene " + cutNum + " image not found.", "Error.", JOptionPane.ERROR_MESSAGE);
        System.exit(-1);
      }
    }
    else
    {
      JOptionPane.showMessageDialog (null, "Cutscene " + cutNum + " file not found.", "Error.", JOptionPane.ERROR_MESSAGE);
      System.exit(-1);
    }
    
    sceneChanger = new Timer (2000, this);
    
    skipButton = new CustomButton("Images/skipButton.jpg");
    skipButton.setBounds (800, 650, skipButton.getWidth(), skipButton.getHeight());
    skipButton.addActionListener(this);
    add(skipButton);
    
    setBounds (0, 0, 1000, 800);
    setVisible(true);
  }
  
  @Override
  public void paintComponent (Graphics g)
  {
    super.paintComponent(g);
    g.drawImage(scenes[sceneNum], 0, 0, null);  
  }
  
  /**
   * This method starts the timer.
   */
  public void start()
  {
    sceneNum = 0;
    sceneChanger.start();
  }
  
  @Override
  /**
   * This method changes scenes according to the timer and ends the cutscene when the skip button is clicked.
   */
    public void actionPerformed (ActionEvent ae)
  {
    if (ae.getSource() == sceneChanger)
    {
      sceneNum++;
      if (sceneNum == numOfScenes)
      {
        end();
      }
      else
      {
        repaint();
      }
    }
    else
    {
      if (ae.getSource () == skipButton)
        end();
    }
  }
  
  
  /**
   * This private method ends the cutscene and returns control to the driver.
   */
  private void end()
  {
    sceneChanger.stop();
    if (cutNum == -1)
    {
      CampXApp.endCutscene(this);
    }
    else
    {
      CampXApp.endCutscene(cutNum);
    }
  }
}