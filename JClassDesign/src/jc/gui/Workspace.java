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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
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
import jc.controller.DesignRendererController;
import jc.controller.EditToolbarController;
import jc.data.DataManager;
import saf.AppTemplate;
import saf.components.AppWorkspaceComponent;
import static saf.settings.AppPropertyType.NEW_ICON;
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
    HBox varHBox;
    HBox methodHBox;

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
    
    TableColumn varNameColumn = new TableColumn("Name");
    TableColumn varTypeColumn = new TableColumn("Type");
    TableColumn varStaticColumn = new TableColumn("Static");
    TableColumn varAccessColumn = new TableColumn("Access");

    TableColumn methNameColumn = new TableColumn("Name");
    TableColumn methReturnColumn = new TableColumn("Return");
    TableColumn methStaticColumn = new TableColumn("Static");
    TableColumn methAbstractColumn = new TableColumn("Abstract");
    TableColumn methAccessColumn = new TableColumn("Access");
    TableColumn methArg1Column = new TableColumn("Arg1");
    TableColumn methArg2Column = new TableColumn("Arg2");
    
    Pane designRenderer;
    ScrollPane designRendererScroll;
    SplitPane splitPane;
    GridPane grid;

    DesignRendererController designRendererController;
    EditToolbarController editToolBarController;
    DataManager dataManager;
    
    Text debugText;

    public Workspace(AppTemplate initApp) {

        // KEEP THIS FOR LATER
        app = initApp;

        // KEEP THE GUI FOR LATER
        gui = app.getGUI();

        buildGui();
        buildHandlers();

    }

    private void buildGui() {

        fileToolBar = new HBox();
        editToolBar = new HBox();
        viewToolBar = new HBox();

        classHBox = new HBox();
        packageHBox = new HBox();
        parentHBox = new HBox();
        varHBox = new HBox();
        methodHBox = new HBox();

        gridControls = new VBox();

        componentToolBar = new VBox();

        
        plusVarBtn = new Button("+");
        plusVarBtn.setMaxWidth(Double.MAX_VALUE);
        minusVarBtn = new Button("-");
        minusVarBtn.setMaxWidth(Double.MAX_VALUE);
        plusMethodBtn = new Button("+");
        plusMethodBtn.setMaxWidth(Double.MAX_VALUE);
        minusMethodBtn = new Button("-");
        minusMethodBtn.setMaxWidth(Double.MAX_VALUE);
        
        
        newBtn = gui.getNewButton();
        newBtn.setMaxWidth(Double.MAX_VALUE);
        exitBtn = gui.getExitButton();
        exitBtn.setMaxWidth(Double.MAX_VALUE);
        saveAsBtn = gui.getSaveAsButton();
        saveAsBtn.setMaxWidth(Double.MAX_VALUE);
        loadBtn = gui.getLoadButton();
        loadBtn.setMaxWidth(Double.MAX_VALUE);
        
        fileToolBar.getChildren().addAll(newBtn, loadBtn, saveAsBtn);
        
        saveBtn = gui.initChildButton(fileToolBar, SAVE_ICON.toString(), SAVE_TOOLTIP.toString(), true);
        saveBtn.setMaxWidth(Double.MAX_VALUE);
        fileToolBar.getChildren().add(exitBtn);
        photoBtn = gui.initChildButton(fileToolBar, PHOTO_ICON.toString(), PHOTO_TOOLTIP.toString(), true);
        photoBtn.setMaxWidth(Double.MAX_VALUE);
        codeBtn = gui.initChildButton(fileToolBar, CODE_ICON.toString(), CODE_TOOLTIP.toString(), true);
        codeBtn.setMaxWidth(Double.MAX_VALUE);
        
        
        

//        newBtn = gui.initChildButton(editToolBar, NEW_ICON.toString(), SELECT_TOOLTIP.toString(), false);
        selectBtn = gui.initChildButton(editToolBar, SELECT_ICON.toString(), SELECT_TOOLTIP.toString(), false);
        selectBtn.setMaxWidth(Double.MAX_VALUE);
        resizeBtn = gui.initChildButton(editToolBar, RESIZE_ICON.toString(), RESIZE_TOOLTIP.toString(), true);
        resizeBtn.setMaxWidth(Double.MAX_VALUE);
        addClassBtn = gui.initChildButton(editToolBar, ADD_CLASS_ICON.toString(), ADD_CLASS_TOOLTIP.toString(), false);
        addClassBtn.setMaxWidth(Double.MAX_VALUE);
        addInterfaceBtn = gui.initChildButton(editToolBar, ADD_INTERFACE_ICON.toString(), ADD_INTERFACE_TOOLTIP.toString(), false);
        addInterfaceBtn.setMaxWidth(Double.MAX_VALUE);
        removeBtn = gui.initChildButton(editToolBar, REMOVE_ICON.toString(), REMOVE_TOOLTIP.toString(), true);
        removeBtn.setMaxWidth(Double.MAX_VALUE);
        undoBtn = gui.initChildButton(editToolBar, UNDO_ICON.toString(), UNDO_TOOLTIP.toString(), true);
        undoBtn.setMaxWidth(Double.MAX_VALUE);
        redoBtn = gui.initChildButton(editToolBar, REDO_ICON.toString(), REDO_TOOLTIP.toString(), true);
        redoBtn.setMaxWidth(Double.MAX_VALUE);

        zoomInBtn = gui.initChildButton(viewToolBar, ZOOM_IN_ICON.toString(), ZOOM_IN_TOOLTIP.toString(), true);
        zoomInBtn.setMaxWidth(Double.MAX_VALUE);
        zoomOutBtn = gui.initChildButton(viewToolBar, ZOOM_OUT_ICON.toString(), ZOOM_OUT_TOOLTIP.toString(), true);
        zoomOutBtn.setMaxWidth(Double.MAX_VALUE);

        gridCheckBox = new CheckBox("Grid");
        gridSnapCheckBox = new CheckBox("Snap");

        gridControls.getChildren().addAll(gridCheckBox, gridSnapCheckBox);

        viewToolBar.getChildren().add(gridControls);

        

        topToolBar = new HBox();
        topToolBar.getChildren().addAll(fileToolBar, editToolBar, viewToolBar);

        gui.setTopToolbar(topToolBar);

        classNameLbl = new Label("Class Name: ");
        packageLbl = new Label("Package: ");
        parentLbl = new Label("Parent: ");
        variableLbl = new Label("Variables: ");
        methodLbl = new Label("Methods: ");

        classNameField = new TextField();
        packageField = new TextField();

        parentComboBox = new ComboBox();

        classHBox.getChildren().addAll(classNameLbl, classNameField);
        classHBox.setAlignment(Pos.CENTER);
        packageHBox.getChildren().addAll(packageLbl, packageField);
        packageHBox.setAlignment(Pos.CENTER);
        parentHBox.getChildren().addAll(parentLbl, parentComboBox);
        parentHBox.setAlignment(Pos.CENTER);
        varHBox.getChildren().addAll(variableLbl, plusVarBtn, minusVarBtn);
        varHBox.setAlignment(Pos.CENTER);
        methodHBox.getChildren().addAll(methodLbl, plusMethodBtn, minusMethodBtn);
        methodHBox.setAlignment(Pos.CENTER);

        variableTable = new TableView();
        variableTable.getColumns().add(varNameColumn);
        variableTable.getColumns().add(varTypeColumn);
        variableTable.getColumns().add(varStaticColumn);
        variableTable.getColumns().add(varAccessColumn);
        
        methodTable = new TableView();
        methodTable.getColumns().add(methNameColumn);
        methodTable.getColumns().add(methReturnColumn);
        methodTable.getColumns().add(methStaticColumn);
        methodTable.getColumns().add(methAbstractColumn);
        methodTable.getColumns().add(methAccessColumn);
        methodTable.getColumns().add(methArg1Column);
        methodTable.getColumns().add(methArg2Column);
        
        
        ScrollPane varTableScroll = new ScrollPane();
        varTableScroll.setContent(variableTable);
        ScrollPane methTableScroll = new ScrollPane();
        methTableScroll.setContent(methodTable);

        componentToolBar.setAlignment(Pos.CENTER);
        componentToolBar.getChildren().addAll(classHBox, packageHBox, parentHBox, varHBox, varTableScroll, methodHBox, methTableScroll);

        grid = new GridPane();
        designRenderer = new Pane();
        designRenderer.setStyle("-fx-background: black;");
        designRenderer.setMinSize(100, 100);
        designRenderer.setPrefSize(2500, 2500);

        designRendererScroll = new ScrollPane();
        
//        designRendererScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
//        designRendererScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        designRendererScroll.setContent(designRenderer);

        splitPane = new SplitPane();
        splitPane.getItems().addAll(designRendererScroll, componentToolBar);
        splitPane.setDividerPositions(.75);
        
	debugText = new Text();
	designRenderer.getChildren().add(debugText);
	debugText.setX(100);
	debugText.setY(100);
        
        DataManager data = (DataManager)app.getDataComponent();
//	data.addClass(redoBtn);

        workspace = new BorderPane();
//       ((BorderPane)workspace).setTop(topToolBar);
        ((BorderPane) workspace).setCenter(splitPane);

    }

    public void buildHandlers() {

//        newBtn.setOnAction(e -> {
//            designRenderer.getChildren().clear();
//        });
        
        editToolBarController = new EditToolbarController(app);
        addClassBtn.setOnAction(e -> {
            System.out.println("add class handler" +  editToolBarController.toString());
            editToolBarController.handleAddClass();
            
        });
        
        addInterfaceBtn.setOnAction(e -> {
            System.out.println("add class handler" +  editToolBarController.toString());
            editToolBarController.handleAddInterface();
            
        });
        selectBtn.setOnAction(e -> {
            System.out.println("select class handler");
            editToolBarController.handleSelect();
        });
        
        classNameField.textProperty().addListener((a,e,o) -> {
            editToolBarController.handleClassName(o);
        });
        
        packageField.textProperty().addListener((a,e,o) -> {
            editToolBarController.handlePackageName(o);
        });

        designRendererController = new DesignRendererController(app);
        designRenderer.setOnMousePressed(e -> {
            System.out.println("mouse pressed handler");
            designRendererController.processCanvasMousePress((int) e.getX(), (int) e.getY());
            System.out.println("mouse pressed handler after");
            
        });
        designRenderer.setOnMouseReleased(e -> {
            designRendererController.processCanvasMouseRelease((int) e.getX(), (int) e.getY());
        });
//        designRenderer.setOnMouseDragged(e -> {
//            designRendererController.processCanvasMouseDragged((int) e.getX(), (int) e.getY());
//        });
        designRenderer.setOnMouseExited(e -> {
            designRendererController.processCanvasMouseExited((int)e.getX(), (int)e.getY());
        });
//        designRenderer.setOnMouseMoved(e -> {
//            designRendererController.processCanvasMouseMoved((int)e.getX(), (int)e.getY());
//        });
    }

    @Override
    public void reloadWorkspace() {
//        designRenderer.getChildren().clear();
        System.out.println("The # of objects is:  " + designRenderer.getChildren().size());
//        if (dataManager.getItems() != null) {
//            ObservableList<Node> items = dataManager.getItems();
//            for (Node node : items) {
//                designRenderer.getChildren().add(node);
//            }
//        }
    }

    @Override
    public void initStyle() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

        componentToolBar.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
        topToolBar.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
        viewToolBar.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
        fileToolBar.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
        editToolBar.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
    }
    
    public Pane getDesignRenderer() {
        return designRenderer;
    }
    
    public AppTemplate getApp() {
        return app;
    }
    
    public void setClassNameText(String s) {
        classNameField.setText(s);
    }

    public void setPackageNameText(String s) {
        packageField.setText(s);
    }
}
