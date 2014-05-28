/**
 * @author dingdj
 * Date:2014-3-18上午11:14:14
 *
 */
package com.ddj.launcher2.core;

/**
 * 桌面的UI部件
 * @author dingdj
 * Date:2014-3-18上午11:14:14
 *
 */
public class UIComponentManager {
	
	private static UIComponentManager instance;
	
	private UIComponentManager(){
		
	}
	
	public static UIComponentManager getInstance(){
		if(instance == null){
			instance = new UIComponentManager();
		}
		return instance;
	}

	private IWorkspace workspace;
	
	private IDragLayer dragLayer;
	
	private IDragController dragController;

	public IWorkspace getWorkspace() {
		return workspace;
	}

	public void setWorkspace(IWorkspace workspace) {
		this.workspace = workspace;
	}

	public IDragLayer getDragLayer() {
		return dragLayer;
	}

	public void setDragLayer(IDragLayer dragLayer) {
		this.dragLayer = dragLayer;
	}

	public IDragController getDragController() {
		return dragController;
	}

	public void setDragController(IDragController dragController) {
		this.dragController = dragController;
	}
	
	
	
	

}
