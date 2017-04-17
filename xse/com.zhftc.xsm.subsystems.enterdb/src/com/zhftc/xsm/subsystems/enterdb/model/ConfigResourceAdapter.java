package com.zhftc.xsm.subsystems.enterdb.model;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.rse.ui.SystemMenuManager;
import org.eclipse.rse.ui.view.AbstractSystemViewAdapter;
import org.eclipse.rse.ui.view.ISystemRemoteElementAdapter;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.views.properties.IPropertyDescriptor;

import com.zhftc.xsm.subsystems.enterdb.Activator;
import com.zhftc.xsm.subsystems.enterdb.EnterDBSubSystem;

public class ConfigResourceAdapter extends AbstractSystemViewAdapter implements
		ISystemRemoteElementAdapter {
	
	/**
	 * Constructor.
	 */
	public ConfigResourceAdapter() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.rse.ui.view.AbstractSystemViewAdapter#addActions(org.eclipse.rse.ui.SystemMenuManager,
	 * org.eclipse.jface.viewers.IStructuredSelection, org.eclipse.swt.widgets.Shell, java.lang.String)
	 */
	public void addActions(SystemMenuManager menu,
			IStructuredSelection selection, Shell parent, String menuGroup)
	{
	}

	/* (non-Javadoc)
	 * @see org.eclipse.rse.ui.view.AbstractSystemViewAdapter#getImageDescriptor(java.lang.Object)
	 */
	public ImageDescriptor getImageDescriptor(Object element)
	{
		return Activator.getDefault().getImageDescriptor("ICON_ID_CONFIG");
	}

	/* (non-Javadoc)
	 * @see org.eclipse.rse.ui.view.AbstractSystemViewAdapter#getText(java.lang.Object)
	 */
	public String getText(Object element)
	{
		return ((ConfigResource)element).getName();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.rse.ui.view.AbstractSystemViewAdapter#getAbsoluteName(java.lang.Object)
	 */
	public String getAbsoluteName(Object object)
	{
		ConfigResource team = (ConfigResource)object;
		return "Config_"+team.getName();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.rse.ui.view.AbstractSystemViewAdapter#getType(java.lang.Object)
	 */
	public String getType(Object element)
	{
		return Activator.getResourceString("property.team_resource.type");
	}

	/* (non-Javadoc)
	 * @see org.eclipse.rse.ui.view.AbstractSystemViewAdapter#getParent(java.lang.Object)
	 */
	public Object getParent(Object element)
	{
		return null; // not really used, which is good because it is ambiguous
	}
	
	/**
	 * @see org.eclipse.rse.ui.view.AbstractSystemViewAdapter#internalGetPropertyDescriptors()
	 */
	protected IPropertyDescriptor[] internalGetPropertyDescriptors()
	{
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.rse.ui.view.AbstractSystemViewAdapter#internalGetPropertyValue(java.lang.Object)
	 */
	protected Object internalGetPropertyValue(Object key)
	{
		return null;
	}

	/**
	 * Intercept of parent method to indicate these objects can be renamed using the RSE-supplied
	 *  rename action.
	 */
	public boolean canRename(Object element)
	{
		return true;
	}
	
	// --------------------------------------
	// ISystemRemoteElementAdapter methods...
	// --------------------------------------

	/* (non-Javadoc)
	 * @see org.eclipse.rse.ui.view.AbstractSystemViewAdapter#doRename(org.eclipse.swt.widgets.Shell, java.lang.Object, java.lang.String, org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public boolean doRename(Shell shell, Object element, String newName,
			IProgressMonitor monitor) throws Exception {
		((ConfigResource)element).setName(newName);
		return true;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.rse.ui.view.ISystemRemoteElementAdapter#getAbsoluteParentName(java.lang.Object)
	 */
	public String getAbsoluteParentName(Object element)
	{
		return "root"; // not really applicable as we have no unique hierarchy
	}

	/* (non-Javadoc)
	 * @see org.eclipse.rse.ui.view.ISystemRemoteElementAdapter#getSubSystemConfigurationId(java.lang.Object)
	 */
	public String getSubSystemConfigurationId(Object element)
	{
		return "com.zhftc.xsm.subsystems.enterdb"; // as declared in extension in plugin.xml
	}

	/* (non-Javadoc)
	 * @see org.eclipse.rse.ui.view.ISystemRemoteElementAdapter#getRemoteTypeCategory(java.lang.Object)
	 */
	public String getRemoteTypeCategory(Object element)
	{
		return "dbm"; // Course grained. Same for all our remote resources.
	}

	/* (non-Javadoc)
	 * @see org.eclipse.rse.ui.view.ISystemRemoteElementAdapter#getRemoteType(java.lang.Object)
	 */
	public String getRemoteType(Object element)
	{
		return "configuration"; // Fine grained. Unique to this resource type.
	}

	/* (non-Javadoc)
	 * @see org.eclipse.rse.ui.view.ISystemRemoteElementAdapter#getRemoteSubType(java.lang.Object)
	 */
	public String getRemoteSubType(Object element)
	{
		return null; // Very fine grained. We don't use it.
	}

	/* (non-Javadoc)
	 * @see org.eclipse.rse.ui.view.ISystemRemoteElementAdapter#refreshRemoteObject(java.lang.Object, java.lang.Object)
	 */
	public boolean refreshRemoteObject(Object oldElement, Object newElement)
	{
		ConfigResource oldTeam = (ConfigResource)oldElement;
		ConfigResource newTeam = (ConfigResource)newElement;
		newTeam.setName(oldTeam.getName());
		return false; // If developer objects held references to their team names, we'd have to return true
	}

	@Override
	public Object getRemoteParent(Object arg0, IProgressMonitor arg1)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getRemoteParentNamesInUse(Object element, IProgressMonitor arg1)
			throws Exception {
		EnterDBSubSystem ourSS = (EnterDBSubSystem)getSubSystem(element);
		CommonResource[] allTeams = ourSS.getAllDBTypes();
		String[] allNames = new String[allTeams.length];
		for (int idx = 0; idx < allTeams.length; idx++)
		  allNames[idx] = allTeams[idx].getName();
		return allNames; // Return list of all team names 	
	}

	@Override
	public Object[] getChildren(IAdaptable config, IProgressMonitor arg1) {
		return ((ConfigResource)config).getSubItems();	
	}

	@Override
	public boolean hasChildren(IAdaptable arg0) {
		// TODO Auto-generated method stub
		return true;
	}

}
