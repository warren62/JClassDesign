/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.gui;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Group;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
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
import jc.data.Item;
import jc.data.JClass;
import jc.file.FileManager;
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

    Group g;

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
    Button parentBtn;

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
    TableColumn varFinalColumn = new TableColumn("Final");
    TableColumn varAbstractColumn = new TableColumn("Abstract");

    TableColumn methNameColumn = new TableColumn("Name");
    TableColumn methTypeColumn = new TableColumn("Type");
    TableColumn methStaticColumn = new TableColumn("Static");
    TableColumn methFinalColumn = new TableColumn("Final");
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
        parentBtn = new Button("Edit Parents");
        parentBtn.setMaxWidth(Double.MAX_VALUE);

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
        photoBtn = gui.initChildButton(fileToolBar, PHOTO_ICON.toString(), PHOTO_TOOLTIP.toString(), false);
        photoBtn.setMaxWidth(Double.MAX_VALUE);
        codeBtn = gui.initChildButton(fileToolBar, CODE_ICON.toString(), CODE_TOOLTIP.toString(), false);
        codeBtn.setMaxWidth(Double.MAX_VALUE);

//        newBtn = gui.initChildButton(editToolBar, NEW_ICON.toString(), SELECT_TOOLTIP.toString(), false);
        selectBtn = gui.initChildButton(editToolBar, SELECT_ICON.toString(), SELECT_TOOLTIP.toString(), false);
        selectBtn.setMaxWidth(Double.MAX_VALUE);
        resizeBtn = gui.initChildButton(editToolBar, RESIZE_ICON.toString(), RESIZE_TOOLTIP.toString(), false);
        resizeBtn.setMaxWidth(Double.MAX_VALUE);
        addClassBtn = gui.initChildButton(editToolBar, ADD_CLASS_ICON.toString(), ADD_CLASS_TOOLTIP.toString(), false);
        addClassBtn.setMaxWidth(Double.MAX_VALUE);
        addInterfaceBtn = gui.initChildButton(editToolBar, ADD_INTERFACE_ICON.toString(), ADD_INTERFACE_TOOLTIP.toString(), false);
        addInterfaceBtn.setMaxWidth(Double.MAX_VALUE);
        removeBtn = gui.initChildButton(editToolBar, REMOVE_ICON.toString(), REMOVE_TOOLTIP.toString(), false);
        removeBtn.setMaxWidth(Double.MAX_VALUE);
        undoBtn = gui.initChildButton(editToolBar, UNDO_ICON.toString(), UNDO_TOOLTIP.toString(), false);
        undoBtn.setMaxWidth(Double.MAX_VALUE);
        redoBtn = gui.initChildButton(editToolBar, REDO_ICON.toString(), REDO_TOOLTIP.toString(), false);
        redoBtn.setMaxWidth(Double.MAX_VALUE);

        zoomInBtn = gui.initChildButton(viewToolBar, ZOOM_IN_ICON.toString(), ZOOM_IN_TOOLTIP.toString(), false);
        zoomInBtn.setMaxWidth(Double.MAX_VALUE);
        zoomOutBtn = gui.initChildButton(viewToolBar, ZOOM_OUT_ICON.toString(), ZOOM_OUT_TOOLTIP.toString(), false);
        zoomOutBtn.setMaxWidth(Double.MAX_VALUE);

        gridCheckBox = new CheckBox("Grid");
        gridSnapCheckBox = new CheckBox("Snap");
        gridSnapCheckBox.setDisable(true);

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
        parentHBox.getChildren().addAll(parentLbl, parentBtn);
        parentHBox.setAlignment(Pos.CENTER);
        varHBox.getChildren().addAll(variableLbl, plusVarBtn, minusVarBtn);
        varHBox.setAlignment(Pos.CENTER);
        methodHBox.getChildren().addAll(methodLbl, plusMethodBtn, minusMethodBtn);
        methodHBox.setAlignment(Pos.CENTER);

        varNameColumn.setCellValueFactory(new PropertyValueFactory<String, String>("name"));
        varTypeColumn.setCellValueFactory(new PropertyValueFactory<String, String>("type"));
        varStaticColumn.setCellValueFactory(new PropertyValueFactory<Boolean, String>("s"));
        varAccessColumn.setCellValueFactory(new PropertyValueFactory<String, String>("access"));
        varFinalColumn.setCellValueFactory(new PropertyValueFactory<Boolean, String>("f"));
        varAbstractColumn.setCellValueFactory(new PropertyValueFactory<Boolean, String>("a"));

        variableTable = new TableView();
        variableTable.getColumns().add(varNameColumn);
//        varNameColumn.setCellValueFactory(new PropertyValueFactory("nameProp"));
        variableTable.getColumns().add(varTypeColumn);
        variableTable.getColumns().add(varStaticColumn);
        variableTable.getColumns().add(varAccessColumn);
        variableTable.getColumns().add(varFinalColumn);
        variableTable.getColumns().add(varAbstractColumn);

        methNameColumn.setCellValueFactory(new PropertyValueFactory<String, String>("name"));
        methTypeColumn.setCellValueFactory(new PropertyValueFactory<String, String>("type"));
        methStaticColumn.setCellValueFactory(new PropertyValueFactory<Boolean, String>("s"));
        methAccessColumn.setCellValueFactory(new PropertyValueFactory<String, String>("access"));
        methFinalColumn.setCellValueFactory(new PropertyValueFactory<Boolean, String>("f"));
        methAbstractColumn.setCellValueFactory(new PropertyValueFactory<Boolean, String>("a"));

        methodTable = new TableView();
        methodTable.getColumns().add(methNameColumn);
        methodTable.getColumns().add(methTypeColumn);
        methodTable.getColumns().add(methStaticColumn);
        methodTable.getColumns().add(methAbstractColumn);
        methodTable.getColumns().add(methFinalColumn);
        methodTable.getColumns().add(methAccessColumn);
//        methodTable.getColumns().add(methArg1Column);
//        methodTable.getColumns().add(methArg2Column);

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

//        g = new Group();
//        designRenderer.getChildren().add(g);
        designRendererScroll = new ScrollPane();
        designRendererScroll.setVvalue(.5);
        designRendererScroll.setHvalue(.5);
//        designRendererScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
//        designRendererScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        designRendererScroll.setContent(designRenderer);

        splitPane = new SplitPane();
        splitPane.getItems().addAll(designRendererScroll, componentToolBar);
        splitPane.setDividerPositions(.75);

//	debugText = new Text();
//	designRenderer.getChildren().add(debugText);
//	debugText.setX(100);
//	debugText.setY(100);
//        variableTable.setItems(designRenderer.getChildren());
//        methodTable.setItems(designRenderer.getChildren());
        DataManager data = (DataManager) app.getDataComponent();
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
            System.out.println("add class handler" + editToolBarController.toString());
            editToolBarController.handleAddClass();

        });

        addInterfaceBtn.setOnAction(e -> {
            System.out.println("add class handler" + editToolBarController.toString());
            editToolBarController.handleAddInterface();

        });
        selectBtn.setOnAction(e -> {
            System.out.println("select class handler");
            editToolBarController.handleSelect();
        });

        removeBtn.setOnAction(e -> {
            editToolBarController.handleRemove();
        });

        zoomOutBtn.setOnAction(e -> {
            editToolBarController.handleZoomOut();
        });

        zoomInBtn.setOnAction(e -> {
            editToolBarController.handleZoomIn();
        });

        resizeBtn.setOnAction(e -> {
            editToolBarController.handleResize();
        });

        undoBtn.setOnAction(e -> {
            editToolBarController.handleUndo();
        });

        codeBtn.setOnAction(e -> {
            FileManager fileManager = (FileManager) app.getFileComponent();

            FileChooser fileChooser = new FileChooser();

            File file = fileChooser.showSaveDialog(app.getGUI().getWindow());

            try {
                DataManager data = (DataManager) app.getDataComponent();
                fileManager.exportData(data, file.getAbsolutePath(), app);
            } catch (IOException ex) {
                Logger.getLogger(Workspace.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        photoBtn.setOnAction(e -> {
            editToolBarController.handleSnapshot();
        });

        plusVarBtn.setOnAction(e -> {
            VariableDialog vd = new VariableDialog(gui.getWindow());
            vd.showDialog();
            DataManager data = (DataManager) app.getDataComponent();
            vd.addData(data.getSelectedItem(), variableTable);
        });

        minusVarBtn.setOnAction(e -> {
            DataManager data = (DataManager) app.getDataComponent();
            if (data.getSelectedItem() instanceof JClass) {
                JClass j = (JClass) data.getSelectedItem();
                if (j.getVariables().size() > 0) {
                    j.getVariables().remove(j.getVariables().size() - 1);
                }
            }
        });

        plusMethodBtn.setOnAction(e -> {
            MethodDialog md = new MethodDialog(gui.getWindow(), app);
            md.showDialog();
//            System.out.println(dataManager.getSelectedItem() == null);
            DataManager data = (DataManager) app.getDataComponent();
            md.addData(data.getSelectedItem(), methodTable);
            md.generate(data.getSelectedItem());
        });

        parentBtn.setOnAction(e -> {
            ParentDialog pd = new ParentDialog(gui.getWindow(), app);
            pd.populateDialog();
//            pd.clearLines();
            pd.showDialog();

            DataManager data = (DataManager) app.getDataComponent();
            pd.addData(data.getSelectedItem());
        });

        classNameField.textProperty().addListener((a, e, o) -> {
            editToolBarController.handleClassName(o);
            System.out.println(packageField.getText());

        });

//        parentComboBox.setOnAction(e -> {
//            editToolBarController.handleAddParent();
//        });
        packageField.textProperty().addListener((a, e, o) -> {
            editToolBarController.handlePackageName(o);
            System.out.println(packageField.getText());

        });

        gridCheckBox.setOnAction(e -> {
            if (gridCheckBox.isSelected()) {
                designRenderer.getStyleClass().add("root");
                gridSnapCheckBox.setDisable(false);
            } else {
                designRenderer.getStyleClass().clear();
                gridSnapCheckBox.setDisable(true);
            }

        });

        gridSnapCheckBox.setOnAction(e -> {
            DataManager data = (DataManager) app.getDataComponent();
            data.snap();
        });

        redoBtn.setOnAction(e -> {
            DataManager data = (DataManager) app.getDataComponent();
            data.redo();

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
            designRendererController.processCanvasMouseExited((int) e.getX(), (int) e.getY());
        });
//        designRenderer.setOnMouseMoved(e -> {
//            designRendererController.processCanvasMouseMoved((int)e.getX(), (int)e.getY());
//        });
    }

    public ComboBox getParentComboBox() {
        return parentComboBox;
    }

    public Group getGroup() {
        return g;
    }

    public TableView getMethodTable() {
        return methodTable;
    }

    public TableView getVariableTable() {
        return variableTable;
    }

    public boolean isSnap() {
        boolean b;
        if (gridSnapCheckBox.isSelected()) {
            b = true;
        } else {
            b = false;
        }
        return b;
    }

    public ScrollPane getDesignRendererScroll() {
        return designRendererScroll;
    }

//    public void addLines() {
//        designRenderer.getStyleClass().add("root");
//    }
//    public void clearLines() {
//        designRenderer.getStyleClass().clear();
//    }
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

    public void setDesignRenderer(Pane designRenderer) {
        this.designRenderer = designRenderer;
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
