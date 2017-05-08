/********************************************************************************
 * Copyright (c) 2017, Xu Siyang. All rights reserved.
 * This program and the accompanying materials are made available under the terms
 * of the Eclipse Public License v1.0 which accompanies this distribution, and is 
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Initial Contributors:
 * xusy@zhftc.com, Apr-18,2017 - Initial creation of EnterDB subsystem
 * xusy@zhftc.com, May-4, 2017 - resolve filter string by element's ID
 ********************************************************************************/
package com.zhftc.xsm.subsystems.enterdb;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Vector;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.rse.core.subsystems.IConnectorService;
import org.eclipse.rse.core.subsystems.SubSystem;
import org.eclipse.rse.core.model.IHost;
import org.eclipse.rse.services.clientserver.NamePatternMatcher;

import com.zhftc.xsm.internal.subsystems.enterdb.connectorservice.EnterDBConnectorService;
import com.zhftc.xsm.internal.subsystems.enterdb.model.CommonResource;

/**
 * This is our subsystem, which manages the remote connection and resources for
 *  a particular system connection object.
 */
public class EnterDBSubSystem extends SubSystem
{
	//private CommonResource dbRoot;       // root element of db types
	//private CommonResource folderRoot;   // root of folder(configurations)
	private Hashtable<String, CommonResource> dbMap = new Hashtable<String, CommonResource>();
	private OutputStream _loggingStream;

	private String dataFile = "D:\\DBManager\\Work\\datafile.txt";	// "datafile.txt"
	
	/**
	 * @param host
	 * @param connectorService
	 */
	public EnterDBSubSystem(IHost host, IConnectorService connectorService) {
		super(host, connectorService);
		_loggingStream = ((EnterDBConnectorService)connectorService).getloggingStream();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.rse.core.subsystems.SubSystem#initializeSubSystem(org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void initializeSubSystem(IProgressMonitor monitor) {
		loggerPrintln("initializing SubSystem...");
		parseDatafile(dataFile);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.rse.core.subsystems.ISubSystem#uninitializeSubSystem(org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void uninitializeSubSystem(IProgressMonitor monitor) {
		loggerPrintln("SubSystem uninitialized.");
	}

	/**
	 * For drag and drop, and clipboard support of remote objects.
	 *   
	 * Return the remote object within the subsystem that corresponds to
	 * the specified unique ID.  Because each subsystem maintains it's own
	 * objects, it's the responsibility of the subsystem to determine
	 * how an ID (or key) for a given object maps to the real object.
	 * By default this returns null. 
	 */
	public Object getObjectWithAbsoluteName(String key)
	{
		loggerPrintln("__enter getObjectWithAbsoluteName: "+key);
		return dbMap.get(key);
	}
	/**
	 * When a filter is expanded, this is called for each filter string in the filter.
	 * Using the criteria of the filter string, it must return objects representing remote resources.
	 * For us, this will be an array of CommonResource objects filtered by ID.
	 * 
	 * @param monitor - the progress monitor in effect while this operation performs
	 * @param filterString - one of the filter strings from the expanded filter.
	 */
	protected Object[] internalResolveFilterString(String filterString, IProgressMonitor monitor)
         throws java.lang.reflect.InvocationTargetException,
                java.lang.InterruptedException                
	{
			loggerPrintln(".internalResolveFilterString: "+filterString);
			
			if (filterString.equals("*")) {
				CommonResource[] roots = new CommonResource[2];
				roots[0] = dbMap.get("-1");
				roots[1] = dbMap.get("200000");
				return roots;
			}
			else {
				// Now, subset master list, based on filter string...
				NamePatternMatcher subsetter = new NamePatternMatcher(
						filterString);
				Vector<CommonResource> v = new Vector<CommonResource>();
				for (Iterator<String> itr = dbMap.keySet().iterator(); itr
						.hasNext();) {
					String key = (String) itr.next();
					if (subsetter.matches(key))
						v.addElement(dbMap.get(key));
				}
				CommonResource[] filteredRes = new CommonResource[v.size()];
				for (int idx = 0; idx < v.size(); idx++)
					filteredRes[idx] = (CommonResource) v.elementAt(idx);
				return filteredRes;
			}
	}

	/**
	 * When a remote resource is expanded, this is called to return the children of the resource, if
	 * the resource's adapter states the resource object is expandable.
	 * For us, it is a Team resource that was expanded, and an array of Developer resources will be returned.
	 * 
	 * @param monitor - the progress monitor in effect while this operation performs
	 * @param parent - the parent resource object being expanded
	 * @param filterString - typically defaults to "*". In future additional user-specific quick-filters may be supported.
	 */
	protected Object[] internalResolveFilterString(Object parent, String filterString, IProgressMonitor monitor)
         throws java.lang.reflect.InvocationTargetException,
                java.lang.InterruptedException
	{
		// typically we ignore the filter string as it is always "*" 
		//  until support is added for "quick filters" the user can specify/select
		//  at the time they expand a remote resource.
		loggerPrintln(".internalResolveFilterString: "+parent+filterString);
		CommonResource team = (CommonResource)parent;
		return team.getChildren();
	}


	// ------------------	
	// Our own methods...
	// ------------------

	public void loggerPrintln(String str) {
		if(_loggingStream != null) {
			try {
				_loggingStream.write((str+"\n").getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	private void parseDatafile(String fileName) {
		int stage = 0;   // stage = 0(idle) 1(processing <DBLIST>) 2(processing <FOLDERLIST>)
		int idx = 0;
		String line;
		String[] field;
		CommonResource element;
		LinkedList<CommonResource> tempRes = new LinkedList<CommonResource>();
		File dataFile = new File(fileName);
		
		loggerPrintln(".parsing datafile: "+fileName);
		
		if(dataFile.exists()) {
			BufferedReader reader = null;
			try {
				reader = new BufferedReader(new FileReader(dataFile));
				while ((line=reader.readLine()) != null) {
					loggerPrintln("..Stage "+ stage +": "+line);
					switch (stage) {
					case 0:
						if (line.equalsIgnoreCase("<DBLIST>")) {
							tempRes.clear();
							stage = 1;
						}
						break;
					case 1:
						if (line.equalsIgnoreCase("<FOLDERLIST>")) {
							tempRes.clear();
							stage = 2;
						}
						else {
							field = line.split(",");
							idx = Integer.parseInt(field[0]);
							element = new CommonResource(field[1],field[2],field[3]);
							element.setSubSystem(this);
							tempRes.add(idx, element);
							dbMap.put(field[2], element);
						
							if (idx > 0) { // This is not the root element.
								element.setParent(tempRes.get(idx-1));
								tempRes.get(idx-1).addChild(element);
							}
							/*if (idx > 0) {
								element = new CommonResource(field[1],field[2],field[3]);
								element.setParent(tempRes.get(idx-1));
								element.setSubSystem(this);
								tempRes.get(idx-1).addChild(element);
								tempRes.add(idx, element);
							}
							else {
								// this is the root element (idx = 0)
								dbRoot = new CommonResource(field[1],field[2],field[3]);
								dbRoot.setSubSystem(this);
								tempRes.add(0, dbRoot);
							}*/
						}
						break;
					case 2:
						field = line.split(",");
						idx = Integer.parseInt(field[0]);
						element = new CommonResource(field[1],field[2],field[3]);
						element.setSubSystem(this);
						tempRes.add(idx, element);
						dbMap.put(field[2], element);
					
						if (idx > 0) { // This is not the root element.
							element.setParent(tempRes.get(idx-1));
							tempRes.get(idx-1).addChild(element);
						}

/*						field = line.split(",");
						idx = Integer.parseInt(field[0]);
						if (idx > 0) {
							element = new CommonResource(field[1],field[2],field[3]);
							element.setParent(tempRes.get(idx-1));
							element.setSubSystem(this);
							tempRes.get(idx-1).addChild(element);
							tempRes.add(idx, element);
						}
						else {
							// this is the root element (idx = 0)
							folderRoot = new CommonResource(field[1],field[2],field[3]);
							folderRoot.setSubSystem(this);
							tempRes.add(0, folderRoot);
						}*/
						break;
					}
				}
			} catch (Exception e){
	            System.out.println(e);
	        }
			finally {
	            try{
	                reader.close();

	            }catch(Exception e){
	                System.out.println(e);
	            }
	        }
		}

	}

	/**
	 * @return the dataFile
	 */
	public String getDataFile() {
		return dataFile;
	}

	/**
	 * @param dataFile the dataFile to set
	 */
	public void setDataFile(String dataFile) {
		this.dataFile = dataFile;
	}

}