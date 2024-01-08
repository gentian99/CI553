package clients.kiosk;

import catalogue.Basket;
import catalogue.Product;
import debug.DEBUG;
import middle.*;
import javax.swing.*;
import java.util.Observable;

/**
 * Implements the Model of the customer client
 * @author  Mike Smith University of Brighton
 * @version 1.0
 */
public class KioskModel extends Observable
{
	
  private Product     theProduct = null;          // Current product
  private Basket      theBasket  = null;          // Bought items
  private Basket      theBasket2 = null;
  
  private String      pn = "";                    // Product being processed

  private StockReadWriter theStock    = null;
  private OrderProcessing theOrder     = null;
  private ImageIcon       thePic       = null;

  /*
   * Construct the model of the Customer
   * @param mf The factory to create the connection objects
   */
  public KioskModel(MiddleFactory mf)
  {
    try                                          // 
    {  
      theStock = mf.makeStockReadWriter();
      theOrder = mf.makeOrderProcessing();        // Process order
    } catch ( Exception e )
    {
      DEBUG.error("CustomerModel.constructor\n" +
                  "Database not created?\n%s\n", e.getMessage() );
    }
    theBasket = makeBasket();                    // Initial Basket
    //theBasket2 = makeBasket();
  }
  
  /**
   * return the Basket of products
   * @return the basket of products
   */
  public Basket getBasket()
  {
    return theBasket;
  }
  
  public Basket getBasket2()
  {
	  return theBasket2;
  }

  /**
   * Check if the product is in Stock
   * @param productName The product name
   */
  
  public void doCheck(String productName )
  {
    theBasket.clear();                          // Clear s. list
    String theAction = "";
    pn  = productName;                   // Product no.
    int    amount  = 1;                         //  & quantity
    try
    {
      if ( theStock.exists2( pn ))              // Stock Exists?
      {                                         // T
        Product pr = theStock.getDetails2( pn ); //  Product
        if ( pr.getQuantity() >= amount )       //  In stock?
        { 
          theAction =                           //   Display 
            String.format( "%s : %7.2f (%2d) ", //
              pr.getDescription(),              //    description
              pr.getPrice(),                    //    price
              pr.getQuantity() );               //    quantity
          pr.setQuantity( amount );             //   Require 1    
          theBasket.add( pr );                  //   Add to basket
          //thePic = theStock.getImage( pn );     //    product
        } else {                                //  F
          theAction =                           //   Inform
            pr.getDescription() +               //    product not
            " not in stock" ;                   //    in stock
        }
      } else {                                  // F
        theAction =                             //  Inform Unknown
          "Unknown product name " + pn;       //  product number
      }
    } catch( StockException e )
    {
      DEBUG.error("CustomerClient.doCheck()\n%s",
      e.getMessage() );
    }
    setChanged(); notifyObservers(theAction);
  }
  
  //Buy product using product name as parameter
  
  public void doBuy( String productName ) 
  {	  
	    String theAction = "";
	    pn  = productName;                    // Product no.
	    int    amount  = 1;                         //  & quantity
	    try
	    {
	    	
	      if ( theStock.exists2( pn ) )              // Stock Exists?
	      {                                         // T
	    	  Product pr = theStock.getDetails2( pn ); //  Product
	    	  boolean stockBought = theStock.buyStock(pr.getProductNum(), amount);
	    	  if ( pr.getQuantity() >= amount )       //  In stock?
	        { 
	          /**theAction =                           //   Display 
	            String.format( "%s : %7.2f (%2d) ", //
	              pr.getDescription(),              //    description
	              pr.getPrice(),                    //    price
	              pr.getQuantity() );               //    quantity*/
	          pr.setQuantity( amount );//   Require 1    
	          makeBasketIfReq();
	          theBasket2.add( pr );                  //   Add to basket
	        } else {                                //  F
	          theAction =                           //   Inform
	            pr.getDescription() +               //    product not
	            " not in stock" ;                   //    in stock
	        }
	      } else {                                  // F
	        theAction =                             //  Inform Unknown
	          "Unknown product name " + pn;       //  product number
	      }
	    } catch( StockException e )
	    {
	      DEBUG.error("CustomerClient.doCheck()\n%s",
	      e.getMessage() );
	    }
	    
	    setChanged(); notifyObservers(theAction);
  }
  
  
  /**
   * Removes most recent product from basket
   */
 public void doRemove() {
	  String theAction;
	  int amount = 1;
	  
	  try {
		  //boolean stockReturned = theStock.removeProduct.(theProduct.getProductNum(), theProduct.getQuantity());
		  Product removedProduct = theBasket2.remove(theBasket2.size() - 1);
		  boolean stockReturned = theStock.addProduct(removedProduct.getProductNum(), amount);
		  if(theBasket2.size() >= 1 && stockReturned) {
			  theAction = "Removed " + removedProduct.getDescription() + " from your basket.";
			
		  } else {
			  theAction = "Basket is empty";
		  }
	  } catch (StockException e) {
		  DEBUG.error("CashierModel.doRemove\n%s", e.getMessage());
		  theAction = e.getMessage();
	  }
	  //theState = State.process;
	  setChanged(); 
	  notifyObservers(theAction);
  }
  
  /**public void doRemove() {
	    String theAction;
	    int amount = 1;
	    
	    try {
	        if (!theBasket2.isEmpty()) {
	            Product removedProduct = theBasket2.remove(theBasket2.size() - 1);
	            boolean stockReturned = theStock.addProduct(removedProduct.getProductNum(), amount);
	            if (theBasket2.size() >= 1 && stockReturned) {
	                theAction = "Removed " + removedProduct.getDescription() + " from your basket.";
	            } else {
	                theAction = "Basket is empty";
	            }
	        } else {
	            theAction = "Basket is empty";
	        }
	    } catch (StockException e) {
	        DEBUG.error("CashierModel.doRemove\n%s", e.getMessage());
	        theAction = e.getMessage();
	    }
	    //theState = State.process;
	    setChanged(); 
	    notifyObservers(theAction);
	}**/



  
  public void doConfirm()
  {
    String theAction = "";
    int    amount  = 1;                       //  & quantity
    try
    {
      if ( theBasket2 != null &&
           theBasket2.size() >= 1 )            // items > 1
      {                                       // T
        theOrder.newOrder( theBasket2 );       //  Process order
        theBasket2 = null;                     //  reset
      }                                       //
      theAction = "Next customer";            // New Customer
      //theState = State.process;               // All Done
      theBasket2 = null;
    } catch( OrderException e )
    {
      DEBUG.error( "%s\n%s", 
            "CashierModel.doCancel", e.getMessage() );
      theAction = e.getMessage();
    }
    theBasket2 = null;
    setChanged(); notifyObservers(theAction); // Notify
  }

  /**
   * Clear the products from the basket
   */
  public void doClear()
  {
    String theAction = "";
    theBasket.clear();                        // Clear s. list
    theAction = "Enter Product Number";       // Set display
    thePic = null;                            // No picture
    setChanged(); notifyObservers(theAction);
  }
  
  /**
   * Return a picture of the product
   * @return An instance of an ImageIcon
   */ 
  public ImageIcon getPicture()
  {
    return thePic;
  }
  
  /**
   * ask for update of view callled at start
   */
  private void askForUpdate()
  {
    setChanged(); notifyObservers("START only"); // Notify
  }

  /**
   * Make a new Basket
   * @return an instance of a new Basket
   */
  protected Basket makeBasket()
  {
    return new Basket();
  }
  
  private void makeBasketIfReq()
  {
    if ( theBasket2 == null )
    {
      try
      {
        int uon   = theOrder.uniqueNumber();     // Unique order num.
        theBasket2 = makeBasket();                //  basket list
        theBasket2.setOrderNum( uon );            // Add an order number
      } catch ( OrderException e )
      {
        DEBUG.error( "Comms failure\n" +
                     "CashierModel.makeBasket()\n%s", e.getMessage() );
      }
    }
  }
  
}

  
