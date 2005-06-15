/*
 */
package org.eclipse.wst.xml.core.internal.catalog;

import java.util.HashMap;
import java.util.Map;



public class CatalogSet
{
  protected Map uriResourceMap = new HashMap();
  protected Map catalogPersistenceLocations = new HashMap();
  //protected boolean isPluginEnvironment = false;

  public CatalogSet()
  {
    super();
  }

  public Catalog lookupOrCreateCatalog(String id, String uri)
  {
    Catalog catalog = getCatalog(id, uri);
    if (catalog == null)
    {  
      catalog = new Catalog(this, id, uri);
      try
      {
        catalog.load();
      }
      catch (Exception e) 
      {
        // TODO: handle exception
      }
      uriResourceMap.put(uri, catalog);
    }  
    return catalog;
  }

  private Catalog getCatalog(String id, String uri)
  {
    return (Catalog) uriResourceMap.get(uri);  
  }
  
  public void putCatalogPersistenceLocation(String logicalURI, String actualURI)
  {
    catalogPersistenceLocations.put(logicalURI, actualURI);
  }
  
  public String getCatalogPersistenceLocation(String id)
  {
    return (String)catalogPersistenceLocations.get(id); 
  }
/*
  public boolean isPluginEnvironment()
  {
    return isPluginEnvironment;
  }

  public void setPluginEnvironment(boolean isPluginEnvironment)
  {
    this.isPluginEnvironment = isPluginEnvironment;
  }*/
}
