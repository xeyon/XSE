package com.zhftc.xsm.internal.subsystems.enterdb.model;

import org.eclipse.rse.core.subsystems.ISubSystem;

public class ConfigResource extends CommonResource {
	
	private String dirNbr;
	
	/**
	 * Default constructor
	 */
	public ConfigResource()
	{
		super();
	}
	/**
	 * Default constructor with initial parameters
	 */
	public ConfigResource(String name, String id, String type)
	{
		super(name, id, type);
	}	
	/**
	 * Constructor for DatabaseResource when given a parent subsystem.
	 */
	public ConfigResource(ISubSystem parentSubSystem)
	{
		super(parentSubSystem);
	}

	/**
	 * @return the dirNbr
	 */
	public String getDirNbr() {
		return dirNbr;
	}
	/**
	 * @param dirNbr the dirNbr to set
	 */
	public void setDirNbr(String dirNbr) {
		this.dirNbr = dirNbr;
	}

}
