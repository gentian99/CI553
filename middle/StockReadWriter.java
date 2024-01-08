package middle;

import catalogue.Product;

/**
  * Interface for read/write access to the stock list.
  * @author  Mike Smith University of Brighton
  * @version 2.0
  */
 
public interface StockReadWriter extends StockReader
{
 /**
   * Customer buys stock,
   * stock level is thus decremented by amount bought.
   * @param pNum Product number
   * @param amount Quantity of product
   * @return StockNumber, Description, Price, Quantity
   * @throws middle.StockException if issue
   */
  boolean buyStock(String pNum, int amount) throws StockException;

/**
  * Customer removes most recent product,
  * stock level is thus incremented by amount bought.
  * @param pNum Product number
  * @param amount Quantity of product
  * @return StockNumber, Description, Price, Quantity
  * @throws middle.StockException if issue
*/
  boolean addProduct(String productNum, int amount) throws StockException;
  
  /**
   * Adds stock (Restocks) to store.
   * @param pNum Product number
   * @param amount Quantity of product
   * @throws middle.StockException if issue
   */
  void addStock(String pNum, int amount) throws StockException;
  
  /**
   * Modifies Stock details for a given product number.
   * Information modified: Description, Price
   * @param detail Replace with this version of product
   * @throws middle.StockException if issue
   */
  void modifyStock(Product detail) throws StockException;

  void modifyStock2(Product detail) throws StockException;
  
  /**
   * Customer buys stock,
   * stock level is thus decremented by amount bought.
   * @param pName Product name
   * @param amount Quantity of product
   * @return StockNumber, Description, Price, Quantity
   * @throws middle.StockException if issue
   */
  boolean buyStock2(String pName, int amount) throws StockException;

/**
 * Customer removes most recent product,
 * stock level is thus incremented by amount bought.
 * @param pName Product name
 * @param amount Quantity of product
 * @return StockNumber, Description, Price, Quantity
 * @throws middle.StockException if issue
*/
 boolean addProduct2(String pName, int amount) throws StockException;
 
 /**
  * Adds stock (Restocks) to store.
  * @param pName Product name
  * @param amount Quantity of product
  * @throws middle.StockException if issue
  */
 void addStock2(String pName, int amount) throws StockException;
 
 
}
