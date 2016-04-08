/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.controller;

import javafx.scene.Cursor;
import javafx.scene.Scene;
import jc.data.DataManager;
import jc.data.JClassDesignerState;
import jc.gui.Workspace;
import saf.AppTemplate;

/**
 *
 * @author Steve
 */
public class EditToolbarController {
    AppTemplate app;
        
    DataManager dataManager;
    
    public EditToolbarController(AppTemplate initApp) {
        app = initApp;
	dataManager = (DataManager)app.getDataComponent();
    }
    
    public void handleSelect() {
        // CHANGE THE CURSOR
	Scene scene = app.getGUI().getPrimaryScene();
	scene.setCursor(Cursor.DEFAULT);
	
	// CHANGE THE STATE
	dataManager.setState(JClassDesignerState.SELECTING_CLASS);	
	
	// ENABLE/DISABLE THE PROPER BUTTONS
	Workspace workspace = (Workspace)app.getWorkspaceComponent();
	workspace.reloadWorkspace();
    }
    
    public void handleAddClass() {
        // CHANGE THE CURSOR
	Scene scene = app.getGUI().getPrimaryScene();
	scene.setCursor(Cursor.CROSSHAIR);
	
	// CHANGE THE STATE
	dataManager.setState(JClassDesignerState.STARTING_CLASS);

	// ENABLE/DISABLE THE PROPER BUTTONS
	Workspace workspace = (Workspace)app.getWorkspaceComponent();
	workspace.reloadWorkspace();
    }
    
    public void handleAddInterface() {
        
    }
    
    public void handleRemove() {
        
    }
    
    public void handleResize() {
        
    }
    
    public void handleUndo() {
        
    }
    
    public void handleRedo() {
        
    }
}
