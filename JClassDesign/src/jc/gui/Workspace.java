/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.gui;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import static jc.PropertyType.ADD_CLASS_ICON;
import static jc.PropertyType.ADD_CLASS_TOOLTIP;
import static jc.PropertyType.ADD_INTERFACE_ICON;
import static jc.PropertyType.ADD_INTERFACE_TOOLTIP;
import static jc.PropertyType.CODE_ICON;
import static jc.PropertyType.CODE_TOOLTIP;
import static jc.PropertyType.PHOTO_ICON;
import static jc.PropertyType.PHOTO_TOOLTIP;
import static jc.PropertyType.REDO_ICON;
import static jc.PropertyType.REDO_TOOLTIP;
import static jc.PropertyType.REMOVE_ICON;
import static jc.PropertyType.REMOVE_TOOLTIP;
import static jc.PropertyType.RESIZE_ICON;
import static jc.PropertyType.RESIZE_TOOLTIP;
import static jc.PropertyType.SELECT_ICON;
import static jc.PropertyType.SELECT_TOOLTIP;
import static jc.PropertyType.UNDO_ICON;
import static jc.PropertyType.UNDO_TOOLTIP;
import static jc.PropertyType.ZOOM_IN_ICON;
import static jc.PropertyType.ZOOM_IN_TOOLTIP;
import static jc.PropertyType.ZOOM_OUT_ICON;
import static jc.PropertyType.ZOOM_OUT_TOOLTIP;
import saf.AppTemplate;
import saf.components.AppWorkspaceComponent;
import static saf.settings.AppPropertyType.SAVE_AS_ICON;
import static saf.settings.AppPropertyType.SAVE_ICON;
import static saf.settings.AppPropertyType.SAVE_TOOLTIP;
import saf.ui.AppGUI;

/**
 *
 * @author Steve
 */
public class Workspace extends AppWorkspaceComponent {
    
    static final String CLASS_EDIT_TOOLBAR = "edit_toolbar";
    static final String CLASS_EDIT_TOOLBAR_ROW = "edit_toolbar_row";

    // HERE'S THE APP
    AppTemplate app;

    // IT KNOWS THE GUI IT IS PLACED INSIDE
    AppGUI gui;

    HBox topToolBar;
    HBox fileToolBar;
    HBox editToolBar;
    HBox viewToolBar;

    VBox componentToolBar;
    
    HBox classHBox;
    HBox packageHBox;
    HBox parentHBox;

    Button saveBtn;
    Button saveAsBtn;
    Button newBtn;
    Button loadBtn;
    Button exitBtn;
    Button selectBtn;
    Button resizeBtn;
    Button addClassBtn;
    Button addInterfaceBtn;
    Button removeBtn;
    Button undoBtn;
    Button redoBtn;
    Button zoomInBtn;
    Button zoomOutBtn;
    Button photoBtn;
    Button codeBtn;
    Button plusVarBtn;
    Button minusVarBtn;
    Button plusMethodBtn;
    Button minusMethodBtn;

    Label classNameLbl;
    Label packageLbl;
    Label parentLbl;
    Label variableLbl;
    Label methodLbl;
    
    TextField classNameField;
    TextField packageField;
    ComboBox parentComboBox;

    VBox gridControls;

    CheckBox gridCheckBox;
    CheckBox gridSnapCheckBox;

    TableView variableTable;
    TableView methodTable;

    Pane designRenderer;
    ScrollPane designRendererScroll;
    SplitPane splitPane;
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
        
        classHBox = new HBox();
        packageHBox = new HBox();
        parentHBox = new HBox();
        
        gridControls = new VBox();

        componentToolBar = new VBox();
        
        saveAsBtn = gui.getSaveAsButton();
        newBtn = gui.getNewButton();
        exitBtn = gui.getExitButton();
        loadBtn = gui.getLoadButton();
        saveBtn = gui.initChildButton(fileToolBar, SAVE_ICON.toString(), SAVE_TOOLTIP.toString(), true);
        photoBtn = gui.initChildButton(fileToolBar, PHOTO_ICON.toString(), PHOTO_TOOLTIP.toString(), true);
        codeBtn = gui.initChildButton(fileToolBar, CODE_ICON.toString(), CODE_TOOLTIP.toString(), true);

        selectBtn = gui.initChildButton(editToolBar, SELECT_ICON.toString(), SELECT_TOOLTIP.toString(), true);
        resizeBtn = gui.initChildButton(editToolBar, RESIZE_ICON.toString(), RESIZE_TOOLTIP.toString(), true);
        addClassBtn = gui.initChildButton(editToolBar, ADD_CLASS_ICON.toString(), ADD_CLASS_TOOLTIP.toString(), true);
        addInterfaceBtn = gui.initChildButton(editToolBar, ADD_INTERFACE_ICON.toString(), ADD_INTERFACE_TOOLTIP.toString(), true);
        removeBtn = gui.initChildButton(editToolBar, REMOVE_ICON.toString(), REMOVE_TOOLTIP.toString(), true);
        undoBtn = gui.initChildButton(editToolBar, UNDO_ICON.toString(), UNDO_TOOLTIP.toString(), true);
        redoBtn = gui.initChildButton(editToolBar, REDO_ICON.toString(), REDO_TOOLTIP.toString(), true);
        
        zoomInBtn = gui.initChildButton(viewToolBar, ZOOM_IN_ICON.toString(), ZOOM_IN_TOOLTIP.toString(), true);
        zoomOutBtn = gui.initChildButton(viewToolBar, ZOOM_OUT_ICON.toString(), ZOOM_OUT_TOOLTIP.toString(), true);
        
        gridCheckBox = new CheckBox("Grid");
        gridSnapCheckBox = new CheckBox("Snap");
        
        gridControls.getChildren().addAll(gridCheckBox, gridSnapCheckBox);
        
        viewToolBar.getChildren().add(gridControls);

        fileToolBar.getChildren().addAll(newBtn, loadBtn, saveAsBtn, exitBtn);

        topToolBar = new HBox();
        topToolBar.getChildren().addAll(fileToolBar, editToolBar, viewToolBar);

        gui.setTopToolbar(topToolBar);
        
        classNameLbl = new Label("Class Name: ");
        packageLbl = new Label("Package: ");
        parentLbl = new Label("Parent: ");
        
        classNameField = new TextField();
        packageField = new TextField();
        
        parentComboBox = new ComboBox();
        
        classHBox.getChildren().addAll(classNameLbl, classNameField);
        packageHBox.getChildren().addAll(packageLbl, packageField);
        classHBox.getChildren().addAll(parentLbl, parentComboBox);
        
        variableTable = new TableView();
        methodTable = new TableView();
        
        componentToolBar.setAlignment(Pos.CENTER);
        componentToolBar.getChildren().addAll(classHBox, packageHBox, parentHBox, variableTable, methodTable);
        
        grid = new GridPane();
        
        designRendererScroll = new ScrollPane();
        designRendererScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        designRendererScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        designRendererScroll.setContent(grid);
        
        splitPane = new SplitPane();
        splitPane.getItems().addAll(designRendererScroll, componentToolBar);
        splitPane.setDividerPositions(.75);

        workspace = new BorderPane();
//       ((BorderPane)workspace).setTop(topToolBar);
        ((BorderPane) workspace).setCenter(splitPane);
        
    }

    @Override
    public void reloadWorkspace() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void initStyle() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

        topToolBar.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
    }

}
