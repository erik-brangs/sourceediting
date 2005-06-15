package org.eclipse.wst.xml.core.internal.catalog;

public interface OASISCatalogConstants
{
  public static final String namespaceName = "urn:oasis:names:tc:entity:xmlns:xml:catalog";
  /** Types of the catalog entries */
  /** The CATALOG element name. */
  public static final String TAG_CATALOG = "catalog";
  /** The GROUP catalog entry. */
  public static final String TAG_GROUP = "group";
  /** The PUBLIC catalog entry. */
  public static final String TAG_PUBLIC = "public";
  /** The SYSTEM catalog etnry. */
  public static final String TAG_SYSTEM = "system";
  /** The URI catalog entry. */
  public static final String TAG_URI = "uri";
  /** The REWRITE_SYSTEM catalog entry. */
  public static final String TAG_REWRITE_SYSTEM = "rewriteSystem";
  /** The REWRITE_URI catalog entry. */
  public static final String TAG_REWRITE_URI = "rewritePublic";
  /** The DELEGATE_PUBLIC catalog entry. */
  public static final String TAG_DELEGATE_PUBLIC = "delegatePublic";
  /** The DELEGATE_SYSTEM catalog entry. */
  public static final String TAG_DELEGATE_SYSTEM = "delegateSystem";
  /** The DELEGATE_URI catalog entry. */
  public static final String TAG_DELEGATE_URI = "delegateUri";
  /** The NEXT_CATALOG catalog entry. */
  public static final String TAG_NEXT_CATALOG = "nextCatalog";
  /** Attributes */
  /** Attribute id used in all catalog entries */
  public static final String ATTR_ID = "id";
  /** Attribute id base in all catalog entries */
  public static final String ATTR_BASE = "xml:base";
  /** Attribute id prefere in catalog entries: CATALOG, GROUP */
  public static final String ATTR_PREFERE = "prefere";
  /** Attribute used in catalog entries of type: PUBLIC */
  public static final String ATTR_PUBLIC_ID = "publicId";
  /**
   * Attribute used in catalog entries of type: SYSTEM
   */
  public static final String ATTR_SYSTEM_ID = "systemId";
  /**
   * Attribute used in catalog entries of type: URI
   */
  public static final String ATTR_NAME = "name";
  /**
   * Attribute used in catalog entries of type: PUBLIC, URI
   */
  public static final String ATTR_URI = "uri";
  /**
   * Attribute used in catalog entries of type: REWRITE_SYSTEM, DELEGATE_SYSTEM
   */
  public static final String ATTR_SYSTEM_ID_START_STRING = "systemIdStartString";
  /**
   * Attribute used in catalog entries of type: REWRITE_SYSTEM, REWRITE_URI
   */
  public static final String ATTR_REWRITE_PREFIX = "rewritePrefix";
  /**
   * Attribute used in catalog entries of type: DELEGATE_PUBLIC
   */
  public static final String ATTR_PUBLIC_ID_START_STRING = "publicIdStartString";
  /**
   * Attribute used in catalog entries of type: DELEGATE_PUBLIC,
   * DELEGATE_SYSTEM, DELEGATE_URI, NEXT_CATALOG
   */
  public static final String ATTR_CATALOG = "catalog";
  /**
   * Attribute used in catalog entries of type: REWRITE_URI, DELEGATE_URI
   */
  public static final String ATTR_URI_START_STRING = "uriStartString";
}
