package com.zhftc.xsm.subsystems.enterdb.model;

import java.util.Vector;

import org.eclipse.rse.core.subsystems.AbstractResource;
import org.eclipse.rse.core.subsystems.ISubSystem;

public class CommonResource extends AbstractResource {
	protected String id;
	protected String name;
	protected String type;
	protected CommonResource parent;
	protected Vector<CommonResource> subItems = null;

	/**
	 * Default constructor
	 */
	public CommonResource()
	{
		super();
	}
	/**
	 * Default constructor with initial parameters
	 */
	public CommonResource(String name, String id, String type)
	{
		super();
		this.name = name;
		this.id = id;
		this.type = type;
	}	
	/**
	 * Constructor for CommonResource when given a parent subsystem.
	 */
	public CommonResource(ISubSystem parentSubSystem)
	{
		super(parentSubSystem);
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the subItems
	 */
	public CommonResource[] getSubItems() {
		return (CommonResource[]) subItems.toArray();
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	public void addChild(CommonResource element) {
		if (subItems == null)
			subItems = new Vector<CommonResource>();
		subItems.addElement(element);		
	}
	public CommonResource getParent() {
		return parent;
	}
	/**
	 * @param parent the parent to set
	 */
	public void setParent(CommonResource parent) {
		this.parent = parent;
	}
	

}
