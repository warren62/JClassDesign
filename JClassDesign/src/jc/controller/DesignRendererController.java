/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.controller;

import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.shape.Shape;
import jc.data.DataManager;
import jc.data.Draggable;
import jc.data.Class;
import jc.data.Item;
import jc.data.JClassDesignerState;
import static jc.data.JClassDesignerState.DRAGGING_NOTHING;
import jc.gui.Workspace;
import saf.AppTemplate;
import static jc.data.JClassDesignerState.SELECTING_CLASS;
import static jc.data.JClassDesignerState.DRAGGING_CLASS;
import static jc.data.JClassDesignerState.SIZING_CLASS;

/**
 *
 * @author Steve
 */
public class DesignRendererController {
    
    AppTemplate app;
    
    public DesignRendererController(AppTemplate initApp) {
	app = initApp;
    }
    
    
    public void processCanvasMouseExited(int x, int y) {
	DataManager dataManager = (DataManager)app.getDataComponent();
	if (dataManager.isInState(JClassDesignerState.DRAGGING_CLASS)) {
	    
	}
//	else if (dataManager.isInState(JClassDesignerState.SIZING_SHAPE)) {
//	    
//	}
    }
     public void processCanvasMousePress(int x, int y) {
	DataManager dataManager = (DataManager)app.getDataComponent();
        System.out.println("processCanvas pressed");
        System.out.println("process mouse press before");
        System.out.println("The State is" + dataManager.getState());
	if (dataManager.isInState(SELECTING_CLASS)) {
	    // SELECT THE TOP SHAPE
            System.out.println("selectTopItem before");
//	    Class item = dataManager.selectTopItem(x, y);
            Item item = dataManager.getSelectedItem();
            System.out.println("selectTopItem after");
	    Scene scene = app.getGUI().getPrimaryScene();

	    // AND START DRAGGING IT
	    if (item != null) {
//		scene.setCursor(Cursor.MOVE);
//		dataManager.setState(JClassDesignerState.DRAGGING_CLASS);
//		app.getGUI().updateToolbarControls(false);
	    }
	    else {
		scene.setCursor(Cursor.DEFAULT);
		dataManager.setState(DRAGGING_NOTHING);
		app.getWorkspaceComponent().reloadWorkspace();
	    }
	}
	else if (dataManager.isInState(JClassDesignerState.STARTING_CLASS)) {
            System.out.println("processmouse");
	    dataManager.startNewClass(x, y);
            System.out.println("processmouse after");
	}
        
        else if (dataManager.isInState(JClassDesignerState.STARTING_INTERFACE)) {
            System.out.println("processmouse");
	    dataManager.startNewInterface(x, y);
            System.out.println("processmouse after");
	}
	
	Workspace workspace = (Workspace)app.getWorkspaceComponent();
	workspace.reloadWorkspace();
    }
     
     public void processCanvasMouseDragged(int x, int y) {
	DataManager dataManager = (DataManager)app.getDataComponent();
//	if (dataManager.isInState(SIZING_ITEM)) {
//	    Draggable newDraggableShape = (Draggable)dataManager.getNewShape();
//	    newDraggableShape.size(x, y);
//	}
	if (dataManager.isInState(DRAGGING_CLASS)) {
	    Item selectedDraggableItem = (Item)dataManager.getSelectedItem();
//            selectedDraggableItem.setOnMouseDragged(e -> {
//                selectedDraggableItem.relocate(e.getSceneX(), e.getSceneY());
//            });
//	    selectedDraggableItem.drag(x, y);
	    app.getGUI().updateToolbarControls(false);
	}
    }
    
    public void processCanvasMouseRelease(int x, int y) {
	DataManager dataManager = (DataManager)app.getDataComponent();
	if (dataManager.isInState(SIZING_CLASS)) {
	    dataManager.selectSizedItem();
	    app.getGUI().updateToolbarControls(false);
	}
	if (dataManager.isInState(JClassDesignerState.DRAGGING_CLASS)) {
	    dataManager.setState(SELECTING_CLASS);
	    Scene scene = app.getGUI().getPrimaryScene();
	    scene.setCursor(Cursor.DEFAULT);
	    app.getGUI().updateToolbarControls(false);
	}
	else if (dataManager.isInState(JClassDesignerState.DRAGGING_NOTHING)) {
	    dataManager.setState(SELECTING_CLASS);
	}
    }
    
}
