package clients.kiosk;

import clients.kiosk.KioskModel;
import clients.kiosk.KioskView;

/**
 * The Customer Controller
 * @author M A Smith (c) June 2014
 */

public class KioskController
{
  private KioskModel model = null;
  private KioskView  view  = null;

  /**
   * Constructor
   * @param model The model 
   * @param view  The view from which the interaction came
   */
  public KioskController( KioskModel model, KioskView view )
  {
    this.view  = view;
    this.model = model;
  }

  /**
   * Check interaction from view
   * @param pn The product number to be checked
   */
  public void doCheck( String pn )
  {
    model.doCheck(pn);
  }
  
  public void doBuy( String pn )
  {
	  model.doBuy(pn);
  }
  
  public void doRemove()
  {
	  model.doRemove();
  }

  public void doConfirm() {
	  model.doConfirm();
  }
  /**
   * Clear interaction from view
   */
  public void doClear()
  {
    model.doClear();
  }

  
}
