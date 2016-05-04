/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.controller;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import jc.data.JClass;
import jc.data.DataManager;
import jc.data.Item;
import jc.data.JClassDesignerState;
import jc.gui.Workspace;
import saf.AppTemplate;

/**
 *
 * @author Steve
 */
public class EditToolbarController {
    AppTemplate app;
    Item selectedItem;    
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
        System.out.println(dataManager.getState());
        System.out.println("handle select set state after");
	
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
        System.out.println("handle add class");

	// ENABLE/DISABLE THE PROPER BUTTONS
	Workspace workspace = (Workspace)app.getWorkspaceComponent();
	workspace.reloadWorkspace();
    }
    
     public void handleAddInterface() {
        // CHANGE THE CURSOR
	Scene scene = app.getGUI().getPrimaryScene();
	scene.setCursor(Cursor.CROSSHAIR);
	
	// CHANGE THE STATE
	dataManager.setState(JClassDesignerState.STARTING_INTERFACE);
        System.out.println("handle add class");

	// ENABLE/DISABLE THE PROPER BUTTONS
	Workspace workspace = (Workspace)app.getWorkspaceComponent();
	workspace.reloadWorkspace();
    }
    
    public void handleRemove() {
        dataManager.removeItem(dataManager.getSelectedItem());
    }
    
    public void handleResize() {
        dataManager.resize(dataManager.getSelectedItem());
    }
    
    public void handleSnapshot() {
        
        Workspace workspace = (Workspace)app.getWorkspaceComponent();
        Pane pane = workspace.getDesignRenderer();
        WritableImage image = pane.snapshot(new SnapshotParameters(), null);

        File fileWork = new File("./snapshots/");
        if (!fileWork.exists()) {
            fileWork.mkdir();
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(fileWork);
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PNG Files (.*png)", ".*png");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showSaveDialog(app.getGUI().getWindow());
        String path = file.getPath() + ".png";
        System.out.println(path);
        file = new File(path);
//       File file = new File("pane.png");

        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
        } catch (IOException ex) {
            Logger.getLogger(EditToolbarController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public void handleUndo() {
        dataManager.undo();
    }
    
    public void handleRedo() {
        
    }
    
    public void handleZoomOut() {
        dataManager.zoomOut();
    }
    
    public void handleZoomIn() {
        dataManager.zoomIn();
    }
    
    public void handleClassName(String n) {
        
        
        dataManager.getSelectedItem().setName(n);
        dataManager.getSelectedItem().updateNameLabel();
//        dataManager.updateParentNames();
        
        System.out.println(dataManager.getSelectedItem().getName());
        System.out.println(dataManager.getSelectedItem());
    }
    
    public void handlePackageName(String n) {
        dataManager.getSelectedItem().setPackage(n);
        System.out.println(dataManager.getSelectedItem().getPackageName());
    }
    
    public void handleAddParent() {
        dataManager.linkLines();
    }
    
}
