package com.zhftc.xsm.internal.subsystems.enterdb.model;

import org.eclipse.rse.core.subsystems.ISubSystem;

public class DatabaseResource extends CommonResource {
	
	private DbTypeResource dbType;
	private String sumFile;
	
	/**
	 * Default constructor
	 */
	public DatabaseResource()
	{
		super();
	}
	/**
	 * Default constructor with initial parameters
	 */
	public DatabaseResource(String name, String id, String type)
	{
		super(name, id, type);
	}	
	/**
	 * Constructor for DatabaseResource when given a parent subsystem.
	 */
	public DatabaseResource(ISubSystem parentSubSystem)
	{
		super(parentSubSystem);
	}
	public DbTypeResource getDbType() {
		return dbType;
	}
	public void setDbType(DbTypeResource dbType) {
		this.dbType = dbType;
	}
	public String getSumFile() {
		return sumFile;
	}
	public void setSumFile(String sumFile) {
		this.sumFile = sumFile;
	}


}
