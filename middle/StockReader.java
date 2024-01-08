package middle;

import catalogue.Product;

import javax.swing.*;

/**
  * Interface for read access to the stock list.
  * @author  Mike Smith University of Brighton
  * @version 2.0
  */

public interface StockReader
{

 /**
   * Checks if the product exits in the stock list
   * @param pNum Product nymber
   * @return true if exists otherwise false
   * @throws StockException if issue
   */
  boolean exists(String pNum) throws StockException;
         
  /**
   * Returns details about the product in the stock list
   * @param pNum Product nymber
   * @return StockNumber, Description, Price, Quantity
   * @throws StockException if issue
   */
  
  Product getDetails(String pNum) throws StockException;

  /**
   * Returns details about the product in the stock list
   * @param pName Product name
   * @return StockNumber, Description, Price, Quantity
   * @throws StockException if issue
   */
  
  Product getDetails2(String pName) throws StockException;
  
  /**
   * Checks if the product exits in the stock list
   * @param pName Product name
   * @return true if exists otherwise false
   * @throws StockException if issue
   */
  
  boolean exists2(String pName) throws StockException;
  
  /**
   * Returns an image of the product in the stock list
   * @param pNum Product name
   * @return Image
   * @throws StockException if issue
   */
  
  ImageIcon getImage(String pNum) throws StockException;

/**
 * Returns an image of the product in the stock list
 * @param pName Product name
 * @return Image
 * @throws StockException if issue
 */

ImageIcon getImage2(String pName) throws StockException;
}


