package com.zhftc.xsm.subsystems.enterdb.model;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.rse.ui.view.AbstractSystemRemoteAdapterFactory;
import org.eclipse.rse.ui.view.ISystemViewElementAdapter;
import org.eclipse.ui.views.properties.IPropertySource;

public class EnterDBAdapterFactory extends AbstractSystemRemoteAdapterFactory
		implements IAdapterFactory {
	
	private ConfigResourceAdapter configAdapter = new ConfigResourceAdapter();
	private DatabaseResourceAdapter databaseAdapter = new DatabaseResourceAdapter();
	private CommonResourceAdapter commonAdapter = new CommonResourceAdapter();
	
	/**
	 * Constructor for DeveloperAdapterFactory.
	 */
	public EnterDBAdapterFactory()
	{
		super();
	}

	/**
	 * @see org.eclipse.core.runtime.IAdapterFactory#getAdapter(Object, Class)
	 */
	public Object getAdapter(Object adaptableObject, Class adapterType)
	{
		ISystemViewElementAdapter adapter = null;
		if (adaptableObject instanceof ConfigResource)
		  adapter = configAdapter;
		else if (adaptableObject instanceof DatabaseResource)
		  adapter = databaseAdapter;
		else if (adaptableObject instanceof CommonResource)
			  adapter = commonAdapter;
		
		// these lines are very important! 
		if ((adapter != null) && (adapterType == IPropertySource.class))
		  adapter.setPropertySourceInput(adaptableObject);
		return adapter;
	}

}
