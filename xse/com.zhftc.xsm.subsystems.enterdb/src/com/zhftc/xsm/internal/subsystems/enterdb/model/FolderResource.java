package com.zhftc.xsm.internal.subsystems.enterdb.model;

import org.eclipse.rse.core.subsystems.ISubSystem;

public class FolderResource extends CommonResource {
	
	/**
	 * Default constructor
	 */
	public FolderResource()
	{
		super();
	}
	/**
	 * Default constructor with initial parameters
	 */
	public FolderResource(String name, String id, String type)
	{
		super(name, id, type);
	}	
	/**
	 * Constructor for DbTypeResource when given a parent subsystem.
	 */
	public FolderResource(ISubSystem parentSubSystem)
	{
		super(parentSubSystem);
	}

	
}
