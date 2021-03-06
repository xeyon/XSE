/********************************************************************************
 * Copyright (c) 2017, Xu Siyang. All rights reserved.
 * This program and the accompanying materials are made available under the terms
 * of the Eclipse Public License v1.0 which accompanies this distribution, and is 
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Initial Contributors:
 * xusy@zhftc.com - Initial creation of EnterDB subsystem, immigrate from 
 *                  com.zhftc.xsm.internal.subsystems.smb.connectorservice
 ********************************************************************************/

package com.zhftc.xsm.internal.subsystems.enterdb.connectorservice;


import org.eclipse.rse.core.model.IHost;
import org.eclipse.rse.core.subsystems.AbstractConnectorServiceManager;
import org.eclipse.rse.core.subsystems.IConnectorService;
import org.eclipse.rse.core.subsystems.ISubSystem;



/**
 * IConnectorService manager class.
 * There should be only one of these instantiated.
 * The job of this manager is to manage and return IConnectorService objects.
 * It ensures there is only ever one per unique SystemConnection,
 *  so that both the file and command subsystems can share the same system object.
 */
public class EnterDBConnectorServiceManager extends AbstractConnectorServiceManager
{
 	private static EnterDBConnectorServiceManager inst = null;
  
    /**
     * Private constructor to ensure not instantiated this way.
     * Use getDefault instead.
     
    private EnterDBConnectorServiceManager()
    {
    }*/
	/**
	 * Constructor
	 */
	public EnterDBConnectorServiceManager()
	{
		super();
	}

    /**
     * Return singleton instance of this class
     */
    public static EnterDBConnectorServiceManager getInstance()
    {
    	if (inst == null)
    	  inst = new EnterDBConnectorServiceManager();
    	return inst;
    }
    
    /**
     * Return true if the singleton has been created. 
     * This saves creating it at shutdown just to test for isConnected.
     */
    public static boolean isInstantiated()
    {
    	return (inst != null);
    }
    
    // -------------------------------------    
    // ABSTRACT METHODS FROM PARENT CLASS...
    // -------------------------------------
    
    /**
     * Return the actual IConnectorService object. We return an instance of UniversalSystem.
     */
    public IConnectorService createConnectorService(IHost host)
    {
    	return new EnterDBConnectorService(host);
    }    

    /**
     * For all subsystems in a particular SystemConnection, we need to know which
     *  ones are to share a single IConnectorService object. To do this, we need a key which
     *  is canonical for all subsystems in a given connection. This can be anything,
     *  but is typically a unique interface that all subsystems supported a shared
     *  IConnectorService object implement. 
     * <p>
     * Whatever is returned from here is used as the key into a hashtable to find the
     *  singleton IConnectorService object in getSystemObject.
     */
    public Class getSubSystemCommonInterface(ISubSystem subsystem)
    {
    	return IEnterDBSubSystem.class;
    }
	/**
	 * Given another subsystem, return true if that subsystem shares a single IConnectorService object
	 * with this one. You must override this to return true if you recognize that subsystem 
	 * as one of your own. You are guaranteed the other subsystem will be from the same 
	 * SystemConnection as this one.
	 * <p>
	 * You can't assume a SystemConnection will you only have subsystems of that you created,
	 * so you should only return true if it implements your interface or you know it is an
	 * instance of your subsystem class.
	 * <p>
	 * This should simply return (otherSubSystem instanceof interface) where interface is 
	 * the same one returned from getSubSystemCommonInterface
	 * 
	 * @return true if otherSubSystem instanceof IUniversalSubSystem
	 */
	public boolean sharesSystem(ISubSystem otherSubSystem)
	{
		return (otherSubSystem instanceof IEnterDBSubSystem);
	}
}