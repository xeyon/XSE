package com.zhftc.xsm.subsystems.enterdb;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Vector;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.rse.core.subsystems.IConnectorService;
import org.eclipse.rse.core.subsystems.SubSystem;
import org.eclipse.rse.core.model.IHost;

import com.zhftc.xsm.subsystems.enterdb.model.CommonResource;

/**
 * This is our subsystem, which manages the remote connection and resources for
 *  a particular system connection object.
 */
public class EnterDBSubSystem extends SubSystem
{
	private CommonResource dbRoot;       // root element of db types
	private CommonResource folderRoot;   // root of folder(configurations)

	private String dataFile = "D:\\DBManager\\Work\\datafile.txt";              // "datafile.txt"
	
	/**
	 * @param host
	 * @param connectorService
	 */
	public EnterDBSubSystem(IHost host, IConnectorService connectorService) {
		super(host, connectorService);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.rse.core.subsystems.SubSystem#initializeSubSystem(org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void initializeSubSystem(IProgressMonitor monitor) {
		//parseDatafile(dataFile);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.rse.core.subsystems.ISubSystem#uninitializeSubSystem(org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void uninitializeSubSystem(IProgressMonitor monitor) {
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
		//  Functional opposite of getAbsoluteName(Object) in our resource adapters
		if (key.startsWith("Team_")) //$NON-NLS-1$
		{
			String teamName = key.substring(5);
			CommonResource[] allTeams = getAllDBTypes();
			for (int idx=0; idx < allTeams.length; idx++)
			   if (allTeams[idx].getName().equals(teamName))
			     return allTeams[idx];
		}
		else if (key.startsWith("Devr_")) //$NON-NLS-1$
		{
			String devrId = key.substring(5);
/*			DatabaseResource[] devrs = getAllDatabases();
			for (int idx=0; idx < devrs.length; idx++)
			  if (devrs[idx].getId().equals(devrId))
			    return devrs[idx];            	
*/		}
		return null; 
	}

	// ------------------	
	// Our own methods...
	// ------------------

	/**
	 * Get all the DBTypes from DBLIST. 
	 */
	public CommonResource[] getAllDBTypes()
	{
		if (dbRoot == null) {
			parseDatafile(dataFile);
		}
		return dbRoot.getSubItems();
	}

	/**
	 * Get all the folders from FOLDERLIST. 
	 */
	public CommonResource[] getAllFolders()
	{
		if (folderRoot == null) {
		    parseDatafile(dataFile);
		}
		return folderRoot.getSubItems();
	}

	private void parseDatafile(String fileName) {
		int stage = 0;   // stage = 0(idle) 1(processing <DBLIST>) 2(processing <FOLDERLIST>)
		int idx = 0;
		String line;
		String[] field;
		Vector<CommonResource> tempRes = new Vector<CommonResource>();
		File dataFile = new File(fileName);
		if(dataFile.exists()) {
			BufferedReader reader = null;
			try {
				reader = new BufferedReader(new FileReader(dataFile));
				while ((line=reader.readLine()) != null) {
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
							if (idx > 0) {
								CommonResource element = new CommonResource(field[1],field[2],field[3]);
								element.setParent(tempRes.elementAt(idx-1));
								tempRes.elementAt(idx-1).addChild(element);
								tempRes.set(idx, element);
							}
							else {
								// this the root element (idx = 0)
								dbRoot = new CommonResource(field[1],field[2],field[3]);
								dbRoot.setParent(null);
								tempRes.set(0, dbRoot);
							}
						}
						break;
					case 2:
						field = line.split(",");
						idx = Integer.parseInt(field[0]);
						if (idx > 0) {
							CommonResource element = new CommonResource(field[1],field[2],field[3]);
							element.setParent(tempRes.elementAt(idx-1));
							tempRes.elementAt(idx-1).addChild(element);
							tempRes.set(idx, element);
						}
						else {
							// this the root element (idx = 0)
							folderRoot = new CommonResource(field[1],field[2],field[3]);
							folderRoot.setParent(null);
							tempRes.set(0, folderRoot);
						}
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