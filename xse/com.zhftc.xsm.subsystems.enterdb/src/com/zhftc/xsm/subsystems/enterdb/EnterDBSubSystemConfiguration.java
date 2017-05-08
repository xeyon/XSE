/********************************************************************************
 * Copyright (c) 2017, Xu Siyang. All rights reserved.
 * This program and the accompanying materials are made available under the terms
 * of the Eclipse Public License v1.0 which accompanies this distribution, and is 
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Initial Contributors:
 * xusy@zhftc.com, Apr-18,2017 - Initial creation of EnterDB subsystem
 ********************************************************************************/

package com.zhftc.xsm.subsystems.enterdb;

import java.util.Vector;

import org.eclipse.rse.core.subsystems.IConnectorService;
import org.eclipse.rse.core.subsystems.ISubSystem;
import org.eclipse.rse.core.subsystems.SubSystemConfiguration;
import org.eclipse.rse.core.filters.ISystemFilterPool;
import org.eclipse.rse.core.filters.ISystemFilterPoolManager;
import org.eclipse.rse.core.filters.ISystemFilter;
import org.eclipse.rse.core.model.IHost;
import com.zhftc.xsm.internal.subsystems.enterdb.EnterDBSubsystemResources;
import com.zhftc.xsm.internal.subsystems.enterdb.connectorservice.EnterDBConnectorServiceManager;
import com.zhftc.xsm.internal.subsystems.enterdb.connectorservice.IEnterDBSubSystem;

/**
 * This is our subsystem factory, which creates instances of our subsystems,
 *  and supplies the subsystem and filter actions to their popup menus.
 */
public class EnterDBSubSystemConfiguration extends SubSystemConfiguration {

	/**
	 * Constructor for DeveloperSubSystemConfiguration.
	 */
	public EnterDBSubSystemConfiguration() {
		super();
	}

	/**
	 * Create an instance of our subsystem.
	 */
	public ISubSystem createSubSystemInternal(IHost conn) {
	   	return new EnterDBSubSystem(conn, getConnectorService(conn));
	}

	/* (non-Javadoc)
	 * @see org.eclipse.rse.core.subsystems.ISubSystemConfiguration#getConnectorService(org.eclipse.rse.core.model.IHost)
	 */
	public IConnectorService getConnectorService(IHost host) {
		return EnterDBConnectorServiceManager.getInstance()
			.getConnectorService(host, IEnterDBSubSystem.class);
	}
	
	protected ISystemFilterPool createDefaultFilterPool(ISystemFilterPoolManager mgr)
	{
		ISystemFilterPool defaultPool = null;
		try {
			defaultPool = mgr.createSystemFilterPool(getDefaultFilterPoolName(
					mgr.getName(), getId()), false); // true=>is deletable by user
			Vector<String> strings = new Vector<String>();
			strings.add("*");
			ISystemFilter filter = mgr.createSystemFilter(defaultPool, 
					EnterDBSubsystemResources.FILTER_DEFAULT_NAME, strings);
			filter.setType("CONF");
		} catch (Exception exc) {}
		return defaultPool;
	}
	
	/**
	 * Intercept of parent method so we can supply our own value shown in the property
	 *  sheet for the "type" property when a filter is selected within our subsystem.
	 *
	 * Requires this line in EnterDBResources.properties: property.type.teamfilter=Team filter
	 */
	public String getTranslatedFilterTypeProperty(ISystemFilter selectedFilter)
	{
	   	String type = selectedFilter.getType();
	   	if (type == null)
	   	  type = "CONF";
	   	if (type.equals("CONF"))
	   	  return EnterDBSubsystemResources.FILTER_TYPE_CONF;
	   	else
	   	  return EnterDBSubsystemResources.FILTER_TYPE_DB;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.rse.core.subsystems.SubSystemConfiguration#supportsUserId()
	 */
	public boolean supportsUserId() {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.rse.core.subsystems.SubSystemConfiguration#supportsServerLaunchProperties(org.eclipse.rse.core.model.IHost)
	 */
	public boolean supportsServerLaunchProperties(IHost host) {
		return false;
	}

}