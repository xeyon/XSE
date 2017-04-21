/********************************************************************************
 * Copyright (c) 2017, Xu Siyang. All rights reserved.
 * This program and the accompanying materials are made available under the terms
 * of the Eclipse Public License v1.0 which accompanies this distribution, and is 
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Initial Contributors:
 * xusy@zhftc.com, Apr-18,2017 - Initial creation of EnterDB subsystem, immigrated 
 *                               from org.eclipse.rse.internal.subsystems.files.
 *                               ftp.connectors.FTPConnectorService
 ********************************************************************************/


package com.zhftc.xsm.internal.subsystems.enterdb.connectorservice;

import java.io.IOException;
import java.io.OutputStream;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.rse.core.model.IHost;
import org.eclipse.rse.core.model.ILabeledObject;
import org.eclipse.rse.core.model.IPropertySet;
import org.eclipse.rse.core.model.PropertyType;
import org.eclipse.rse.core.model.SystemSignonInformation;
import org.eclipse.rse.services.files.RemoteFileException;
import org.eclipse.rse.ui.subsystems.StandardConnectorService;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.MessageConsole;

import com.zhftc.xsm.internal.subsystems.enterdb.EnterDBSubsystemResources;

public class EnterDBConnectorService extends StandardConnectorService
{
	/* TODO: keep an FTPService handle here to maintain file transfer function
	 * protected FTPService _ftpService;
*/
	private OutputStream _loggingStream;
	private boolean _connected = false;
	/** Indicates the default string encoding on this platform */
	private static String _defaultEncoding = new java.io.InputStreamReader(new java.io.ByteArrayInputStream(new byte[0])).getEncoding();

	public EnterDBConnectorService(IHost host)
	{
		super(EnterDBSubsystemResources.RESID_DBM_CONNECTORSERVICE_NAME,
				EnterDBSubsystemResources.RESID_DBM_CONNECTORSERVICE_DESCRIPTION, 
				host, 0);
		
		SystemSignonInformation info = getSignonInformation();
		_loggingStream = getLoggingStream(info.getHostname(),getPort());

		//_ftpService = new FTPService();
		if (getHost().getSystemType().isWindows()) {
			// Configured against a Windows-specific system type
			//_ftpService.setIsCaseSensitive(false);
		}
		getPropertySet();
	}

	private IPropertySet getPropertySet()
	{
		IPropertySet propertySet = getPropertySet("DBManager Settings"); //$NON-NLS-1$

		if(propertySet==null)
		{
			propertySet = createPropertySet("DBManager Settings"); //$NON-NLS-1$
			propertySet.addProperty("port", "40198", PropertyType.getIntegerPropertyType());
			//Active - passive mode
			propertySet.addProperty("passive","true",PropertyType.getEnumPropertyType(new String[]{"true","false"})); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$

			// TODO: add datafile parser here? 
/*			String[] keys = FTPClientConfigFactory.getParserFactory().getKeySet();
			String[] keysArray = new String[keys.length+1];

			System.arraycopy(keys, 0, keysArray, 0, keys.length);

			keysArray[keysArray.length-1]="AUTO"; //$NON-NLS-1$

			Arrays.sort(keysArray);

			propertySet.addProperty("parser","AUTO",PropertyType.getEnumPropertyType(keysArray)); //$NON-NLS-1$ //$NON-NLS-2$
*/		}
		if (propertySet instanceof ILabeledObject) {
			String label = EnterDBSubsystemResources.RESID_DBM_SETTINGS_LABEL;
			((ILabeledObject)propertySet).setLabel(label);
		}
		return propertySet;
	}
	
	protected int getDBMPort() {
		int port = getPort();
		if (port<=0) {
			IPropertySet propertySet = getPropertySet();
			port = Integer.parseInt(propertySet.getPropertyValue("port"));
		}
		return port;
	}
	
	protected void internalConnect(IProgressMonitor monitor)  throws RemoteFileException, IOException
	{
		internalConnect();
		_connected = true;
		_loggingStream.write("Bon jour!\n internal connected...\n".getBytes());
	}

	private void internalConnect() throws RemoteFileException, IOException
	{
		IPropertySet propertySet = getPropertySet();
		SystemSignonInformation info = getSignonInformation();
		
/*		_ftpService.setHostName(info.getHostname());
		_ftpService.setUserId(info.getUserId());
		_ftpService.setPassword(info.getPassword());
		_ftpService.setPortNumber(getPort());
		_ftpService.setLoggingStream(getLoggingStream(info.getHostname(),getPort()));
		_ftpService.setPropertySet(propertySet);
		_ftpService.setFTPClientConfigFactory(FTPClientConfigFactory.getParserFactory());
		//TODO this code should be in IHost
		String encoding = getHost().getDefaultEncoding(false);
		if (encoding==null) encoding = getHost().getDefaultEncoding(true);
		//TODO Here, we set the FTP default encoding same as the local encoding.
		//Another alternative would be to set ISO-8859-1, which is the
		//default-default internal to FTP, or keep it "null".
		if (encoding==null) encoding = _defaultEncoding;
		//</code to be in IHost>
		_ftpService.setControlEncoding(encoding);

		_ftpService.connect();*/
	}

	/* (non-Javadoc)
	 * @see org.eclipse.rse.core.subsystems.AbstractConnectorService#getHomeDirectory()
	 */
/*	public String getHomeDirectory() {
/*		TODO: we should return the configuration root here
 * if (_ftpService!=null) {
			IHostFile f = _ftpService.getUserHome();
			if (f!=null) {
				return f.getAbsolutePath();
			}
		}
		//fallback while not yet connected
		return super.getHomeDirectory();
	}*/

	private OutputStream getLoggingStream(String hostName,int portNumber)
	{
		MessageConsole messageConsole=null;

		IConsole[] consoles = ConsolePlugin.getDefault().getConsoleManager().getConsoles();
		for (int i = 0; i < consoles.length; i++) {
			if(consoles[i].getName().equals("EnterDB log: "+hostName+":"+portNumber)) { //$NON-NLS-1$ //$NON-NLS-2$
				messageConsole = (MessageConsole)consoles[i];
				break;
			}
		}

		if(messageConsole==null){
			messageConsole = new MessageConsole("EnterDB log: "+hostName+":"+portNumber, null); //$NON-NLS-1$ //$NON-NLS-2$
			ConsolePlugin.getDefault().getConsoleManager().addConsoles(new IConsole[]{ messageConsole });
		}

		ConsolePlugin.getDefault().getConsoleManager().showConsoleView(messageConsole);

		return messageConsole.newOutputStream();
	}


	protected void internalDisconnect(IProgressMonitor monitor)
	{
		//_ftpService.disconnect();
		_connected = false;
		try {
			_loggingStream.write("\n internal disconnected.\nSalut!\n".getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean isConnected()
	{
		return _connected;
	}

	/**
	 * @return the _loggingStream
	 */
	public OutputStream getloggingStream() {
		return _loggingStream;
	}

}
