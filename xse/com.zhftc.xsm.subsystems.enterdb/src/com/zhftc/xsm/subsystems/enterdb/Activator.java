package com.zhftc.xsm.subsystems.enterdb;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IAdapterManager;
import org.eclipse.core.runtime.Platform;
import org.eclipse.rse.services.clientserver.messages.SystemMessage;
import org.eclipse.rse.services.clientserver.messages.SystemMessageFile;
import org.eclipse.rse.ui.SystemBasePlugin;
import org.osgi.framework.BundleContext;

import com.zhftc.xsm.subsystems.enterdb.model.ConfigResource;
import com.zhftc.xsm.subsystems.enterdb.model.DatabaseResource;
import com.zhftc.xsm.subsystems.enterdb.model.EnterDBAdapterFactory;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends SystemBasePlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.zhftc.xsm.subsystems.enterdb"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;
	
	// ResourceBundle
	private ResourceBundle resourceBundle = null;
	
	// Message file
	private SystemMessageFile messageFile = null;

	/**
	 * The constructor
	 */
	public Activator() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		IAdapterManager manager = Platform.getAdapterManager();
		EnterDBAdapterFactory factory = new EnterDBAdapterFactory();
		manager.registerAdapters(factory, ConfigResource.class);
		manager.registerAdapters(factory, DatabaseResource.class);

	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	@Override
	protected void initializeImageRegistry() {
		String path = getIconPath();
		putImageInRegistry("ICON_ID_CONFIG", path + "systemfiles_obj.gif");
		putImageInRegistry("ICON_ID_DATABASE", path + "systemfileslive_obj.gif");	
		putImageInRegistry("ICON_ID_CONFIGFILTER", path + "teamFilter.gif");
		putImageInRegistry("ICON_ID_DATABASEFILTER", path + "developerFilter.gif");
	}

	/**
	 * Retrieves the string resource bundle associated with this plugin.
	 * @return the ResourceBundle or null if the bundle could not be loaded.
	 */
	public ResourceBundle getResourceBundle() {
		try {
			if (resourceBundle == null) {
				resourceBundle = ResourceBundle.getBundle("EnterDBResources.properties");
			}
		} catch (MissingResourceException e) {
			SystemBasePlugin.logError("Missing EnterDBResources.properties", e);
		}
		return resourceBundle;
	}

	/**
	 * Retrieves the SystemMessageFile associated with this plug-in.
	 * @return the SystemMessageFile or null if the message file 
	 * could not be loaded.
	 */
	public SystemMessageFile getMessageFile() {
		if (messageFile == null) {
			messageFile = loadMessageFile(this.getBundle(), "EnterDBMessages.xml"); //$NON-NLS-1$
		}
		return messageFile;
	}
	
	/**
	 * Retrieves the singleton workspace for this workbench.
	 * This is a convenience method, fully equivalent to 
	 * ResourcesPlugin.getWorkspace().
	 * @return the singleton workspace
	 */
	public static IWorkspace getWorkspace() {
		return ResourcesPlugin.getWorkspace();
	}
	
	/**
	 * Retrieve a string from the plugin's resource bundle.
	 * @param key the key to the string
	 * @return the retrieved string or the key if the string could not be
	 * found or the bundle could not be loaded.
	 */
	public static String getResourceString(String key) {
		String result = null;
		ResourceBundle bundle = Activator.getDefault().getResourceBundle();
		if (bundle != null) {
			try {
				result = bundle.getString(key);
			} catch (MissingResourceException e) {
				SystemBasePlugin.logError("Missing key in bundle", e);
			}
		}
		if (result == null) {
			result = key;
		}
		return result;
	}
	
	/**
	 * Retrieve the SystemMessageFile for this plugin.
	 * @return the SystemMessageFile or null if the message file
	 * could not be loaded.
	 */
	public static SystemMessageFile getPluginMessageFile() {
		return Activator.getDefault().getMessageFile();
	}
	
	/**
	 * Retrieve a SystemMessage from the message file for this 
	 * plug-in given its message id.
	 * @param messageId the message id to retrieve
	 * @return the retrieved SystemMessage or null if the message
	 * was not found in the message file or the message file
	 * could not be loaded.
	 */
	public static SystemMessage getPluginMessage(String messageId) {
		SystemMessage message = null;
		SystemMessageFile messageFile = getPluginMessageFile();
		if (messageFile != null) {
			message = messageFile.getMessage(messageId); 
		}
		return message;
	}
	
}
