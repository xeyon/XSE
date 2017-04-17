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

public class CommonResourceAdapter extends AbstractSystemViewAdapter implements
		ISystemRemoteElementAdapter {
	
	/**
	 * Constructor.
	 */
	public CommonResourceAdapter() {
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
		return ((CommonResource)element).getName();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.rse.ui.view.AbstractSystemViewAdapter#getAbsoluteName(java.lang.Object)
	 */
	public String getAbsoluteName(Object object)
	{
		CommonResource element = (CommonResource)object;
		return element.getId()+element.getName()+element.getType();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.rse.ui.view.AbstractSystemViewAdapter#getType(java.lang.Object)
	 */
	public String getType(Object element)
	{
		return ((CommonResource)element).getType();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.rse.ui.view.AbstractSystemViewAdapter#getParent(java.lang.Object)
	 */
	public Object getParent(Object element)
	{
		return ((CommonResource)element).getParent();
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
		return ((CommonResource)element).getParent().getName();
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
		return "common"; // Fine grained. Unique to this resource type.
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
		// TODO: more fields to be updated?
		CommonResource oldE = (CommonResource)oldElement;
		CommonResource newE = (CommonResource)newElement;
		newE.setName(oldE.getName());
		return true;
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
		CommonResource[] adjacents = ((CommonResource)element).getParent().getSubItems();
		String[] allNames = new String[adjacents.length];
		for (int idx = 0; idx < adjacents.length; idx++)
			  allNames[idx] = adjacents[idx].getName();
		return allNames; // Return list of all team names 	
	}

	@Override
	public Object[] getChildren(IAdaptable element, IProgressMonitor arg1) {
		return ((CommonResource)element).getSubItems();	
	}

	@Override
	public boolean hasChildren(IAdaptable arg0) {
		return ((CommonResource)arg0).getSubItems().length > 0;
	}

}
