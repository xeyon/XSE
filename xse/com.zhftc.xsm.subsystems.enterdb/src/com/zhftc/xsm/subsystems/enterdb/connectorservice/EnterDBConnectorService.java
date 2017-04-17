package com.zhftc.xsm.subsystems.enterdb.connectorservice;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.rse.core.model.IHost;
import org.eclipse.rse.core.subsystems.AbstractConnectorService;

import com.zhftc.xsm.subsystems.enterdb.Activator;

public class EnterDBConnectorService extends AbstractConnectorService {
	
	private boolean connected = false;

	public EnterDBConnectorService(IHost host,
			int port) {
		super(
				Activator.getResourceString("connectorservice.devr.name"),
				Activator.getResourceString("connectorservice.devr.desc"),
				host,
				port
			);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void acquireCredentials(boolean arg0)
			throws OperationCanceledException {
		// TODO Auto-generated method stub

	}

	@Override
	public void clearCredentials() {
		// TODO Auto-generated method stub

	}

	@Override
	public void clearPassword(boolean arg0, boolean arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getUserId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasPassword(boolean arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean inheritsCredentials() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isConnected() {
		return connected;
	}

	@Override
	public boolean isSuppressed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removePassword() {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeUserId() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean requiresPassword() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean requiresUserId() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void savePassword() {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveUserId() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setPassword(String arg0, String arg1, boolean arg2, boolean arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setSuppressed(boolean arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setUserId(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean sharesCredentials() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean supportsPassword() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean supportsUserId() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.rse.core.subsystems.AbstractConnectorService#internalConnect(org.eclipse.core.runtime.IProgressMonitor)
	 */
	protected void internalConnect(IProgressMonitor monitor) throws Exception
	{
		// TODO pretend. Normally, we'd connect to our remote server-side code here
		connected=true;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.rse.core.subsystems.AbstractConnectorService#internalDisconnect(org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void internalDisconnect(IProgressMonitor monitor) throws Exception
	{
		// TODO pretend. Normally, we'd disconnect from our remote server-side code here
		connected=false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.rse.core.subsystems.IConnectorService#supportsRemoteServerLaunching()
	 */
	public boolean supportsRemoteServerLaunching()
	{
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.rse.core.subsystems.IConnectorService#supportsServerLaunchProperties()
	 */
	public boolean supportsServerLaunchProperties()
	{
		return false;
	}

}
