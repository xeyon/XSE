package com.zhftc.xsm.subsystems.enterdb.model;

import org.eclipse.rse.core.subsystems.ISubSystem;

public class DbTypeResource extends CommonResource {
	
	/**
	 * Default constructor
	 */
	public DbTypeResource()
	{
		super();
	}
	/**
	 * Default constructor with initial parameters
	 */
	public DbTypeResource(String name, String id, String type)
	{
		super(name, id, type);
	}	

	/**
	 * Constructor for DbTypeResource when given a parent subsystem.
	 */
	public DbTypeResource(ISubSystem parentSubSystem)
	{
		super(parentSubSystem);
	}

	
}
