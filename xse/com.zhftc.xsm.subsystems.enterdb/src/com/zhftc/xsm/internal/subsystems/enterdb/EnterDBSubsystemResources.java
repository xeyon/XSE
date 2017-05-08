package com.zhftc.xsm.internal.subsystems.enterdb;

import org.eclipse.osgi.util.NLS;

public class EnterDBSubsystemResources extends NLS {
	private static final String BUNDLE_NAME = "com.zhftc.xsm.internal.subsystems.enterdb.EnterDBSubsystemResources"; //$NON-NLS-1$
	static {
		NLS.initializeMessages(BUNDLE_NAME, EnterDBSubsystemResources.class);
	}
	private EnterDBSubsystemResources() {
	}

	public static String	RESID_DBM_CONNECTORSERVICE_NAME;
	public static String	RESID_DBM_CONNECTORSERVICE_DESCRIPTION;
	public static String	RESID_DBM_SETTINGS_LABEL;
	public static String	RESID_FILTER_DBROOT;
	public static String	RESID_FILTER_CONFROOT;
	
	public static String	PROPERTY_ID_NAME;
	public static String	PROPERTY_ID_DESC;
	public static String	PROPERTY_PARENT_NAME;
	public static String	PROPERTY_PARENT_DESC;
	
	public static String	FILTER_DEFAULT_NAME;
	public static String	FILTER_TYPE_DB;
	public static String	FILTER_TYPE_CONF;
}
