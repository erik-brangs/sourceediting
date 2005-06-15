/*
* Copyright (c) 2002 IBM Corporation and others.
* All rights reserved.   This program and the accompanying materials
* are made available under the terms of the Common Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/cpl-v10.html
* 
* Contributors:
*   IBM - Initial API and implementation
*   Jens Lukowski/Innoopract - initial renaming/restructuring
* 
*/
package org.eclipse.wst.xml.core.internal.catalog;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.Platform;
import org.eclipse.wst.xml.core.internal.Logger;
import org.eclipse.wst.xml.core.internal.XMLCorePlugin;
import org.eclipse.wst.xml.core.internal.catalog.provisional.ICatalog;
import org.eclipse.wst.xml.core.internal.catalog.provisional.ICatalogElement;
import org.eclipse.wst.xml.core.internal.catalog.provisional.ICatalogEntry;
import org.eclipse.wst.xml.core.internal.catalog.provisional.ICatalogEvent;
import org.eclipse.wst.xml.core.internal.catalog.provisional.ICatalogListener;
import org.eclipse.wst.xml.core.internal.catalog.provisional.INextCatalog;
public class Catalog implements ICatalog
{

	class CatalogLS
	{
		public void load()
		{
		}

		public void save()
		{
			try
			{
				new CatalogWriter().write(Catalog.this, location);
			} catch (Exception e)
			{
				Logger.logException(e);
			}
		}
	}

	class DefaultCatalogLS extends CatalogLS
	{
		public void load()
		{
			NextCatalog userCatalogReference = new NextCatalog();
			userCatalogReference.setId(XMLCorePlugin.USER_CATALOG_ID);
			userCatalogReference.setCatalogLocation(USER_CATALOG_FILE);
			addCatalogElement(userCatalogReference);

			NextCatalog systemCatalogReference = new NextCatalog();
			systemCatalogReference.setId(XMLCorePlugin.SYSTEM_CATALOG_ID);
			systemCatalogReference.setCatalogLocation(SYSTEM_CATALOG_FILE);
			addCatalogElement(systemCatalogReference);

			/*
			 * Here we save the file in order to 'reflect' the catalog that
			 * we've created from plugin extensions to disk. The 'default'
			 * catalog is only ever written to disk and never read from disk.
			 */
			save();
		}
	}

	class InternalResolver
	{
		protected Map publicMap = new HashMap();

		protected Map systemMap = new HashMap();

		protected Map uriMap = new HashMap();

		InternalResolver()
		{
			for (Iterator i = catalogElements.iterator(); i.hasNext();)
			{
				ICatalogElement catalogElement = (ICatalogElement) i.next();
				if (catalogElement.getType() == ICatalogElement.TYPE_ENTRY)
				{
					ICatalogEntry entry = (ICatalogEntry) catalogElement;
					Map map = getEntryMap(entry.getEntryType());
					map.put(entry.getKey(), entry);
				}
			}
		}

		private Map getEntryMap(int entryType)
		{
			Map map = systemMap;
			switch (entryType)
			{
			case ICatalogEntry.ENTRY_TYPE_PUBLIC:
				map = publicMap;
				break;
			case ICatalogEntry.ENTRY_TYPE_URI:
				map = uriMap;
				break;
			default:
				break;
			}
			return map;
		}

		protected String getMappedURI(Map map, String key)
		{
			CatalogEntry entry = (CatalogEntry) map.get(key);
			if(entry == null) return null;
			String uri = entry.getURI();
			try
			{
				URL entryURL = new URL(entry.getAbsolutePath(uri));
				return Platform.resolve(entryURL).toString();
			} catch (IOException e)
			{
				return null;
			}
		}

		public String resolvePublic(String publicId, String systemId)
				throws MalformedURLException, IOException
		{
			String result = getMappedURI(publicMap, publicId);
			if (result == null)
			{
				result = getMappedURI(systemMap, systemId);
			}
			if (result == null)
			{
				result = resolveSubordinateCatalogs(
						ICatalogEntry.ENTRY_TYPE_PUBLIC, publicId, systemId);
			}
			return result;
		}

		public String resolveSystem(String systemId)
				throws MalformedURLException, IOException
		{
			String result = getMappedURI(systemMap, systemId);
			if (result == null)
			{
				result = resolveSubordinateCatalogs(
						ICatalogEntry.ENTRY_TYPE_SYSTEM, null, systemId);
			}
			return result;
		}

		public String resolveURI(String uri) throws MalformedURLException,
				IOException
		{
			String result = getMappedURI(uriMap, uri);
			if (result == null)
			{
				result = resolveSubordinateCatalogs(
						ICatalogEntry.ENTRY_TYPE_URI, null, uri);
			}
			return result;
		}
	}

	class SystemCatalogLS extends CatalogLS
	{
		public void load()
		{
			new CatalogContributorRegistryReader(Catalog.this).readRegistry();

			/*
			 * Here we save the file in order to 'reflect' the catalog that
			 * we've created from plugin extensions to disk. 
			 * The 'system' catalog is only ever written to disk and never read from disk.
			 */
			save();
		}
	}

	class UserCatalogLS extends CatalogLS
	{
		public void load()
		{
			InputStream inputStream = null;
			try
			{
				URL url = new URL(location);
				inputStream = url.openStream();
				boolean oldNotificationEnabled = isNotificationEnabled();
				setNotificationEnabled(false);
				clear();
				try
				{
					CatalogReader.read(Catalog.this, inputStream);
				} finally
				{
					setNotificationEnabled(oldNotificationEnabled);
				}
				notifyChanged();
			} catch (Exception e)
			{
			} finally
			{
				if (inputStream != null)
				{
					try
					{
						inputStream.close();
					} catch (Exception e)
					{
					}
				}
			}
		}
	}

	public static final String DEFAULT_CATALOG_FILE = "default_catalog.xml";

	public static final String SYSTEM_CATALOG_FILE = "system_catalog.xml";

	public static final String USER_CATALOG_FILE = "user_catalog.xml";

	protected String base;

	protected List catalogElements = new ArrayList();

	protected CatalogLS catalogLS;

	protected String id;

	protected InternalResolver internalResolver;

	protected boolean isNotificationEnabled;

	protected List listenerList = new ArrayList();

	protected String location;

	protected CatalogSet resourceSet;

	public Catalog(CatalogSet catalogResourceSet, String id, String location)
	{
		this.resourceSet = catalogResourceSet;
		this.id = id;
		this.location = location;

		if (XMLCorePlugin.DEFAULT_CATALOG_ID.equals(id))
		{
			catalogLS = new DefaultCatalogLS();
		} else if (XMLCorePlugin.SYSTEM_CATALOG_ID.equals(id))
		{
			catalogLS = new SystemCatalogLS();
		} else
		{
			catalogLS = new UserCatalogLS();
		}
	}

	public void addCatalogElement(ICatalogElement element)
	{
		catalogElements.add(element);
		element.setOwnerCatalog(this);
		internalResolver = null;
		notifyAddElement(element);
	}

	public void addEntriesFromCatalog(ICatalog catalog)
	{
		try
		{
			setNotificationEnabled(false);
			if (catalog != null)
			{
				ICatalogElement[] entries = ((Catalog)catalog).getCatalogElements();
				for (int i = 0; i < entries.length; i++)
				{
					CatalogElement clone = (CatalogElement)((CatalogElement)entries[i]).clone();
					addCatalogElement(clone);
				}
			} else
			{
				Logger.log(Logger.ERROR, "argument was null in Catalog.addEntriesFromCatalog");
			}
		} finally
		{
			setNotificationEnabled(true);
		}
		internalResolver = null;
		notifyChanged();
	}

	public void addListener(ICatalogListener listener)
	{
		listenerList.add(listener);
	}

	public void clear()
	{
		catalogElements.clear();
		internalResolver = null;
		notifyChanged();
	}

	public ICatalogElement createCatalogElement(int type)
	{
		switch (type)
		{
		case ICatalogElement.TYPE_ENTRY:
			return new CatalogEntry();
		case ICatalogElement.TYPE_NEXT_CATALOG:
			return new NextCatalog();
		case ICatalogEntry.ENTRY_TYPE_PUBLIC:
			return new CatalogEntry(ICatalogEntry.ENTRY_TYPE_PUBLIC);
		case ICatalogEntry.ENTRY_TYPE_SYSTEM:
			return new CatalogEntry(ICatalogEntry.ENTRY_TYPE_SYSTEM);
		case ICatalogEntry.ENTRY_TYPE_URI:
			return new CatalogEntry(ICatalogEntry.ENTRY_TYPE_URI);
		default:
			return new CatalogElement(type);
		}
	}

	public String getBase()
	{
		return base;
	}

	private List getCatalogElements(int type)
	{
		List result = new ArrayList();
		ICatalogElement[] elements = (ICatalogElement[]) catalogElements
				.toArray(new ICatalogElement[catalogElements.size()]);
		for (int i = 0; i < elements.length; i++)
		{
			ICatalogElement element = elements[i];
			if (element.getType() == type)
			{
				result.add(element);
			}
		}
		return result;
	}

	public ICatalogEntry[] getCatalogEntries()
	{
		List result = getCatalogElements(ICatalogElement.TYPE_ENTRY);
		return (ICatalogEntry[]) result
				.toArray(new ICatalogEntry[result.size()]);
	}

	protected CatalogSet getCatalogSet()
	{
		return resourceSet;
	}

	public String getId()
	{
		return id;
	}

	public String getLocation()
	{
		return location;
	}

	public INextCatalog[] getNextCatalogs()
	{
		List result = getCatalogElements(ICatalogElement.TYPE_NEXT_CATALOG);
		return (INextCatalog[]) result.toArray(new INextCatalog[result.size()]);
	}

	protected InternalResolver getOrCreateInternalResolver()
	{
		if (internalResolver == null)
		{
			internalResolver = new InternalResolver();
		}
		return internalResolver;
	}

	protected boolean isNotificationEnabled()
	{
		return isNotificationEnabled;
	}

	public void load() throws IOException
	{
		catalogLS.load();
	}

	protected void notifyAddElement(ICatalogElement entry)
	{
		if (isNotificationEnabled)
		{
			ICatalogEvent event = new CatalogEvent(this, entry,
					ICatalogEvent.ELEMENT_ADDED);
			notifyListeners(event);
		}
	}

	protected void notifyChanged()
	{
		ICatalogEvent event = new CatalogEvent(this, null,
				ICatalogEvent.CHANGED);
		notifyListeners(event);
	}

	protected void notifyListeners(ICatalogEvent event)
	{
		List list = new ArrayList();
		list.addAll(listenerList);
		for (Iterator i = list.iterator(); i.hasNext();)
		{
			ICatalogListener listener = (ICatalogListener) i.next();
			listener.catalogChanged(event);
		}
	}

	protected void notifyRemoveElement(ICatalogElement element)
	{
		if (isNotificationEnabled)
		{
			ICatalogEvent event = new CatalogEvent(this, element,
					ICatalogEvent.ELEMENT_REMOVED);
			notifyListeners(event);
		}
	}

	public void removeCatalogElement(ICatalogElement element)
	{
		catalogElements.remove(element);
		internalResolver = null;
		notifyRemoveElement(element);
		
	}

	public void removeListener(ICatalogListener listener)
	{
		listenerList.remove(listener);
	}

	public String resolvePublic(String publicId, String systemId)
			throws MalformedURLException, IOException
	{
		return getOrCreateInternalResolver().resolvePublic(publicId, systemId);
	}

	protected String resolveSubordinateCatalogs(int entryType, String publicId,
			String systemId) throws MalformedURLException, IOException
	{
		String result = null;
		INextCatalog[] nextCatalogs = getNextCatalogs();
		for (int i = 0; i < nextCatalogs.length; i++)
		{
			INextCatalog nextCatalog = nextCatalogs[i];
			ICatalog catalog = nextCatalog.getReferencedCatalog();
			if (catalog != null)
			{
				switch (entryType)
				{
				case ICatalogEntry.ENTRY_TYPE_PUBLIC:
					result = catalog.resolvePublic(publicId, systemId);
					break;
				case ICatalogEntry.ENTRY_TYPE_SYSTEM:
					result = catalog.resolveSystem(systemId);
					break;
				case ICatalogEntry.ENTRY_TYPE_URI:
					result = catalog.resolveURI(systemId);
					break;
				default:
					break;
				}
				if (result != null)
				{
					return result;
				}
			}
		}
		return null;
	}

	public String resolveSystem(String systemId) throws MalformedURLException,
			IOException
	{
		return getOrCreateInternalResolver().resolveSystem(systemId);
	}

	public String resolveURI(String uri) throws MalformedURLException,
			IOException
	{
		return getOrCreateInternalResolver().resolveURI(uri);
	}

	public void save() throws IOException
	{
		catalogLS.save();
	}

	public void setBase(String base)
	{
		this.base = base;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public void setLocation(String location)
	{
		this.location = location;
	}

	protected void setNotificationEnabled(boolean b)
	{
		isNotificationEnabled = b;
	}

	public ICatalogElement[] getCatalogElements()
	{
		return (ICatalogElement[]) catalogElements.toArray(new ICatalogElement[catalogElements.size()]);
	}
	

	

}
