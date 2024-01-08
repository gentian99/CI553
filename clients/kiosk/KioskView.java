package clients.kiosk;

import catalogue.Basket;
import catalogue.BetterBasket;
import clients.Picture;
import middle.MiddleFactory;
import middle.OrderProcessing;
import middle.StockReader;
import middle.StockReadWriter;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Implements the Customer view.
 * @author  Mike Smith University of Brighton
 * @version 1.0
 */

public class KioskView implements Observer
{
  class Name                              // Names of buttons
  {
    public static final String SEARCH  = "Search";
    public static final String CLEAR  = "Clear";
  }

  public static final String ADD = "Add";
  public static final String REMOVE = "Remove";
  public static final String CONFIRM = "Confirm";
  
  private static final int H = 600;       // Height of window pixels
  private static final int W = 400;       // Width  of window pixels

  private final JLabel      theAction  = new JLabel();
  private final JTextField  theInput   = new JTextField();
  private final JTextArea   theOutput  = new JTextArea();
  private final JScrollPane theSP      = new JScrollPane();
  private final JTextArea   theOutput2 = new JTextArea();
  private final JScrollPane theSP2     = new JScrollPane();
  private final JButton     theBtCheck = new JButton( Name.SEARCH );
  private final JButton     theBtAdd = new JButton( ADD );
  private final JButton     theBtRemove = new JButton( REMOVE );
  private final JButton     theBtConfirm = new JButton( CONFIRM );
  private final JButton     theBtClear = new JButton( Name.CLEAR );
  
  private Picture thePicture = new Picture(80,80);
  private StockReadWriter theStock   = null;
  private OrderProcessing theOrder     = null;
  private KioskController cont= null;

  /**
   * Construct the view
   * @param rpc   Window in which to construct
   * @param mf    Factor to deliver order and stock objects
   * @param x     x-cordinate of position of window on screen 
   * @param y     y-cordinate of position of window on screen  
   */
  
  public KioskView( RootPaneContainer rpc, MiddleFactory mf, int x, int y )
  {
    try                                             // 
    {      
      theStock  = mf.makeStockReadWriter();             // Database Access
      theOrder = mf.makeOrderProcessing();        // Process order
    } catch ( Exception e )
    {
      System.out.println("Exception: " + e.getMessage() );
    }
    Container cp         = rpc.getContentPane();    // Content Pane
    Container rootWindow = (Container) rpc;         // Root Window
    cp.setLayout(null);                             // No layout manager
    rootWindow.setSize( W, H );                     // Size of Window
    rootWindow.setLocation( x, y );

    Font f = new Font("Monospaced",Font.PLAIN,12);  // Font f is

    theBtCheck.setBounds( 290, 49, 80, 40 );    // Check button
    theBtCheck.addActionListener(                   // Call back code
      e -> cont.doCheck( theInput.getText() ) );
    cp.add( theBtCheck );                      //  Add to canvas
    
    theBtAdd.setBounds(290, 180, 80, 40);
    theBtAdd.addActionListener(                     // Call back code
    e -> cont.doBuy (theInput.getText() ) );
    cp.add( theBtAdd );   

    theBtRemove.setBounds( 290, 350, 80, 40 );       // Buy button
    theBtRemove.addActionListener(                  // Call back code
      e -> cont.doRemove() );                      
    cp.add( theBtRemove );   
    
    theBtClear.setBounds( 290, 220, 80, 40 );    // Clear button
    theBtClear.addActionListener(                   // Call back code
      e -> cont.doClear() );
    cp.add( theBtClear );                         //  Add to canvas 
    
    theBtConfirm.setBounds( 290, 390, 80, 40 );   // Clear Button
    theBtConfirm.addActionListener(                  // Call back code
      e -> cont.doConfirm() );
    cp.add( theBtConfirm );  
    
    theAction.setBounds( 20, 25 , 270, 20 );       // Message area
    theAction.setText( "" );                        //  Blank
    cp.add( theAction );                            //  Add to canvas

    theInput.setBounds( 20, 50, 270, 40 );         // Product no area
    theInput.setText("");                           // Blank
    cp.add( theInput );                             //  Add to canvas
    
    theSP.setBounds( 20, 100, 270, 160 );          // Scrolling pane
    theOutput.setText( "" );                        //  Blank
    theOutput.setFont( f );                         //  Uses font  
    cp.add( theSP );                                //  Add to canvas
    theSP.getViewport().add( theOutput );           //  In TextArea
    
    theSP2.setBounds( 20, 270, 270, 160 );          // Scrolling pane
    theOutput2.setText( "" );                        //  Blank
    theOutput2.setFont( f );                         //  Uses font  
    cp.add( theSP2 );                                //  Add to canvas
    theSP2.getViewport().add( theOutput2 );           //  In TextArea

    thePicture.setBounds( 292, 100, 80, 80 );   // Picture area
    cp.add( thePicture );                           //  Add to canvas
    thePicture.clear();
    
    rootWindow.setVisible( true );                  // Make visible);
    theInput.requestFocus();                        // Focus is here
  
    cp.setBackground(Color.cyan);                   //sets background color
  }

   /**
   * The controller object, used so that an interaction can be passed to the controller
   * @param c   The controller
   */

  public void setController( KioskController c )
  {
    cont = c;
  }

  /**
   * Update the view
   * @param modelC   The observed model
   * @param arg      Specific args 
   */
   
  public void update( Observable modelC, Object arg )
  {
    KioskModel model  = (KioskModel) modelC;
    String        message = (String) arg;
    theAction.setText( message );
    ImageIcon image = model.getPicture();  // Image of product
    if ( image == null )
    {
      thePicture.clear();                  // Clear picture
    } else {
      thePicture.set( image );             // Display picture
    }
    Basket basket = model.getBasket2();
    if ( basket == null )
      theOutput2.setText( "Customers order" );
    else
      theOutput2.setText( basket.getDetails() );
    theOutput.setText( model.getBasket().getDetails() );
    theInput.requestFocus();    // Focus is here
  }
}
