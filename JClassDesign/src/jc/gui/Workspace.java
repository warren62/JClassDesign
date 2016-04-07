/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.gui;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import saf.AppTemplate;
import saf.components.AppWorkspaceComponent;
import saf.ui.AppGUI;

/**
 *
 * @author Steve
 */
public class Workspace extends AppWorkspaceComponent{
    
    // HERE'S THE APP
    AppTemplate app;

    // IT KNOWS THE GUI IT IS PLACED INSIDE
    AppGUI gui;
    
    HBox topToolBar;
    HBox fileToolBar;
    HBox editToolBar;
    HBox viewToolBar;
    
    HBox componentToolBar;
    
    Button saveBtn;
    Button saveAsBtn;
    Button newBtn;
    Button loadBtn;
    Button exitBtn;
    
    Pane designRenderer;
    ScrollPane designeRendererScroll;
    ScrollPane componentToolBarScroll;
    GridPane grid;
    
    
    public Workspace(AppTemplate initApp) {
        
        // KEEP THIS FOR LATER
	app = initApp;

	// KEEP THE GUI FOR LATER
	gui = app.getGUI();
        
        buildGui();
        
    }
    
    private void buildGui() {
       
        fileToolBar = new HBox();
        editToolBar = new HBox();
        viewToolBar = new HBox();
        
        saveAsBtn = gui.getSaveButton();
        newBtn = gui.getNewButton();
        exitBtn = gui.getExitButton();
        loadBtn = gui.getLoadButton();
        
        
        fileToolBar.getChildren().addAll(newBtn, saveAsBtn, loadBtn, exitBtn);
        
        topToolBar = new HBox();
        topToolBar.getChildren().addAll(fileToolBar, editToolBar, viewToolBar);
        
        gui.setTopToolbar(topToolBar);
        
        workspace  = new BorderPane();
//       ((BorderPane)workspace).setTop(topToolBar);
       ((BorderPane)workspace).setRight(componentToolBarScroll);
       ((BorderPane)workspace).setLeft(designeRendererScroll);
    } 

    
    @Override
    public void reloadWorkspace() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void initStyle() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
