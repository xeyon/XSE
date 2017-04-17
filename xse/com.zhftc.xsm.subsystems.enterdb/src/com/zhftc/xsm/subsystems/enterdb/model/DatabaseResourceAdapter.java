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
import org.eclipse.ui.views.properties.PropertyDescriptor;

import com.zhftc.xsm.subsystems.enterdb.Activator;
import com.zhftc.xsm.subsystems.enterdb.EnterDBSubSystem;

public class DatabaseResourceAdapter extends AbstractSystemViewAdapter implements
		ISystemRemoteElementAdapter {
	
	/**
	 * Constructor.
	 */
	public DatabaseResourceAdapter() {
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
		return Activator.getDefault().getImageDescriptor("ICON_ID_DATABASE");
	}

	/* (non-Javadoc)
	 * @see org.eclipse.rse.ui.view.AbstractSystemViewAdapter#getText(java.lang.Object)
	 */
	public String getText(Object element)
	{
		return ((DatabaseResource)element).getName();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.rse.ui.view.AbstractSystemViewAdapter#getAbsoluteName(java.lang.Object)
	 */
	public String getAbsoluteName(Object object)
	{
		DatabaseResource team = (DatabaseResource)object;
		return "db_"+team.getName();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.rse.ui.view.AbstractSystemViewAdapter#getType(java.lang.Object)
	 */
	public String getType(Object element)
	{
		return Activator.getResourceString("property.devr_resource.type");
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
		// the following array should be made static to it isn't created every time		
		PropertyDescriptor[] ourPDs = new PropertyDescriptor[2];
		ourPDs[0] = new PropertyDescriptor("devr_id", Activator.getResourceString("property.devr_id.name"));
		ourPDs[0].setDescription(Activator.getResourceString("property.devr_id.desc"));
		ourPDs[1] = new PropertyDescriptor("devr_dept", Activator.getResourceString("property.devr_dept.name"));
		ourPDs[1].setDescription(Activator.getResourceString("property.devr_dept.desc"));
		return ourPDs;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.rse.ui.view.AbstractSystemViewAdapter#internalGetPropertyValue(java.lang.Object)
	 */
	protected Object internalGetPropertyValue(Object key)
	{
		// propertySourceInput holds the currently selected object
		DatabaseResource devr = (DatabaseResource)propertySourceInput;
		if (key.equals("devr_id"))
			return devr.getId();
		else if (key.equals("devr_dept"))
		  return devr.getDbType();
		return null;
	}

	// --------------------------------------
	// ISystemRemoteElementAdapter methods...
	// --------------------------------------

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
		return "database"; // Fine grained. Unique to this resource type.
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
		DatabaseResource oldDevr= (DatabaseResource)oldElement;
		DatabaseResource newDevr = (DatabaseResource)newElement;
		newDevr.setName(oldDevr.getName());
		return false;
	}

	@Override
	public Object getRemoteParent(Object element, IProgressMonitor monitor)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getRemoteParentNamesInUse(Object element,
			IProgressMonitor monitor) throws Exception {
		EnterDBSubSystem ourSS = (EnterDBSubSystem)getSubSystem(element);
		CommonResource[] allTeams = ourSS.getAllDBTypes();
		String[] allNames = new String[allTeams.length];
		for (int idx = 0; idx < allTeams.length; idx++)
		  allNames[idx] = allTeams[idx].getName();
		return allNames; // Return list of all team names 	
	}

	@Override
	public boolean hasChildren(IAdaptable element) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object[] getChildren(IAdaptable element, IProgressMonitor monitor) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
