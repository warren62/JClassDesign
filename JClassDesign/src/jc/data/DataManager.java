/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.data;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.transform.Scale;
import static jc.data.JClassDesignerState.SELECTING_CLASS;
import jc.file.FileManager;
import jc.gui.Workspace;
import saf.AppTemplate;
import saf.components.AppDataComponent;

/**
 *
 * @author Steve
 */
public class DataManager implements AppDataComponent {

    private double zoomAmount = .1;
    // THIS IS A SHARED REFERENCE TO THE APPLICATION
    AppTemplate app;

    JClassDesignerState state;

    ArrayList<Node> items;

    Item newItem;

    Item selectedItem;

    Group g = new Group();

    Scale scaleTransform;

    JClassDesignerMemento memento = new JClassDesignerMemento();

    // USE THIS WHEN THE SHAPE IS SELECTED
    Effect highlightedEffect;

    double orgSceneX, orgSceneY;
    double orgTranslateX, orgTranslateY;

    public DataManager(AppTemplate initApp) throws Exception {

        app = initApp;

        DropShadow dropShadowEffect = new DropShadow();
        dropShadowEffect.setOffsetX(0.0f);
        dropShadowEffect.setOffsetY(0.0f);
        dropShadowEffect.setSpread(1.0);
        dropShadowEffect.setColor(Color.YELLOW);
        dropShadowEffect.setBlurType(BlurType.GAUSSIAN);
        dropShadowEffect.setRadius(15);
        highlightedEffect = dropShadowEffect;

        items = new ArrayList<>();

    }

    public DataManager() {
        items = new ArrayList<Node>();
    }

    public AppTemplate getApp() {
        return app;
    }

    public JClassDesignerState getState() {
        return state;
    }

    public void setState(JClassDesignerState initState) {
        state = initState;
    }

    public Item getNewClass() {
        return newItem;
    }

    public Item getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(Item initSelectedClass) {
        selectedItem = initSelectedClass;
    }

    public boolean isInState(JClassDesignerState testState) {
        return state == testState;
    }

    public void unhighlightItem(Item item) {
        selectedItem.setEffect(null);
    }

    public void highlightItem(Item item) {
        item.setEffect(highlightedEffect);
    }

    public void startNewClass(int x, int y) {
        System.out.println("start new item");
        JClass newItem1 = new JClass();
        newItem1.start(x, y);
        newItem = newItem1;
        initNewItem();
        Workspace workspace = (Workspace) app.getWorkspaceComponent();
        System.out.println("*******memento add workspace in start new class for memento: ");
        System.out.println("*******memento add workspace in start new class : " + workspace == null);
        System.out.println("*******memento add workspace in start new class for app : " + app == null);
//        memento.add(new WorkspaceData(workspace));
        memento.add(workspace.getDesignRenderer().getChildren());

//        memento.add(deepCopyDataManager());
//        memento = new JClassDesignerMemento(app.getGUI().getAppPane());
        System.out.println("start item after");
    }

    public void startNewInterface(int x, int y) {
        System.out.println("start new item");
        Interface newItem1 = new Interface();
        newItem1.start(x, y);
        newItem = newItem1;
        initNewItem();
        Workspace workspace = (Workspace) app.getWorkspaceComponent();
//       memento.add(new WorkspaceData(workspace));
        memento.add(workspace.getDesignRenderer().getChildren());

//       memento.add(deepCopyDataManager());
//        memento = new JClassDesignerMemento(app.getGUI().getAppPane());
        System.out.println("start item after");
    }

    public void initNewItem() {
        // DESELECT THE SELECTED SHAPE IF THERE IS ONE
        if (selectedItem != null) {
            unhighlightItem(selectedItem);
            selectedItem = null;
        }

        // USE THE CURRENT SETTINGS FOR THIS NEW SHAPE
        Workspace workspace = (Workspace) app.getWorkspaceComponent();
        newItem.initHandler(this, workspace);

        // ADD THE SHAPE TO THE CANVAS
        System.out.println(items.toString() + "//" + items.size());
        items.add(newItem);
        System.out.println(newItem.toString() + "//" + items.size());

//        workspace.getGroup().getChildren().add(newItem);
//        g.getChildren().add(newItem);
//        HBox hb = new HBox();
//        hb.getChildren().addAll(new CheckBox(), new Label(newItem.getName()));
        workspace.getParentComboBox().getItems().add(newItem.getName());
        workspace.getDesignRenderer().getChildren().add(newItem);

//        memento.add(workspace.getDesignRenderer().getChildren());
//        Workspace workspace = (Workspace) app.getWorkspaceComponent();
//        memento = new JClassDesignerMemento(workspace);
//        memento.add(workspace);
//        workspace.getWorkspace().getChildren().add(newItem);
        System.out.println("initnew item debug" + workspace.getWorkspace().getChildren().size());

        // GO INTO SHAPE SIZING MODE
    }

    public Item selectTopItem(int x, int y) {
        System.out.println("select top item before");
//	Class item = getTopItem(x, y);
        Item item = getSelectedItem();
        if (item == selectedItem) {
            return item;
        }

        if (selectedItem != null) {
            unhighlightItem(selectedItem);
        }
        if (item != null) {
            highlightItem(item);
            Workspace workspace = (Workspace) app.getWorkspaceComponent();
//	    workspace.loadSelectedShapeSettings(item);
        }
        selectedItem = item;
//        setSelectedClass(item);
        if (item != null) {
            ((Draggable) item).start(x, y);
        }
        return item;
    }

    public Item getTopItem(int x, int y) {
        for (int i = items.size() - 1; i >= 0; i--) {
            Item item = (Item) items.get(i);
            if (item.contains(x, y)) {
                return item;
            }
        }
        return null;
    }

    public void selectSizedItem() {
        if (selectedItem != null) {
            unhighlightItem(selectedItem);
        }
        selectedItem = newItem;
        highlightItem(selectedItem);
        newItem = null;
    }

    public void addClass(JClass itemToAdd) {
        items.add(itemToAdd);
        Workspace workspace = (Workspace) app.getWorkspaceComponent();
//        memento.add(workspace.getDesignRenderer().getChildren());

    }

//    public void setItems(ObservableList<Node> initItems, Pane pane) {
//	items = initItems;
//    }
    public void addInterface(Interface itemToAdd) {
        items.add(itemToAdd);
        Workspace workspace = (Workspace) app.getWorkspaceComponent();
//        memento.add(workspace.getDesignRenderer().getChildren());
    }

    public void removeItem(Item itemToRemove) {
        Workspace workspace = (Workspace) app.getWorkspaceComponent();

        for (Line l : itemToRemove.getParentLines()) {
            workspace.getDesignRenderer().getChildren().remove(l);
        }

        for (Line l : itemToRemove.getChildLines()) {
            workspace.getDesignRenderer().getChildren().remove(l);
        }

        for (Shape l : itemToRemove.getChildShapes()) {
            workspace.getDesignRenderer().getChildren().remove(l);
        }

        for (Shape l : itemToRemove.getParentShapes()) {
            workspace.getDesignRenderer().getChildren().remove(l);
        }

        itemToRemove.getChildLines().clear();
        itemToRemove.getParentLines().clear();
        items.remove(itemToRemove);

        workspace.getDesignRenderer().getChildren().remove(itemToRemove);
//        Workspace workspace = (Workspace) app.getWorkspaceComponent();
//        memento = new JClassDesignerMemento(workspace);
//        memento.add(new WorkspaceData(workspace));
        memento.add(workspace.getDesignRenderer().getChildren());

//        memento.add(deepCopyDataManager());
    }

    public void addToWorkspace(Item i) {

//        g.getChildren().add(i);
        Workspace workspace = (Workspace) app.getWorkspaceComponent();
        System.out.println("*********Item name test add to workspace******" + i.getName());
        i.updateNameLabel();
        workspace.getDesignRenderer().getChildren().add(i);
        System.out.println("*********Item name test add to workspace after******" + i.getName());

        workspace.getParentComboBox().getItems().add(i.getName());
        i.initHandler(this, workspace);
//        Workspace workspace = (Workspace) app.getWorkspaceComponent();
//        memento = new JClassDesignerMemento(workspace);
//        memento.add(new WorkspaceData(workspace));
//        memento.add(workspace.getDesignRenderer().getChildren());

//        memento.add(deepCopyDataManager());
//        ((Workspace)app.getWorkspaceComponent()).getGroup().getChildren().add(i);
    }

    public void addLineToWorkspace(BoundLine l) {

        Workspace workspace = (Workspace) app.getWorkspaceComponent();

        workspace.getDesignRenderer().getChildren().add(l);
//        l.initHandler();
        System.out.println("*******add node to workspace*******" + l.toString());

//        workspace.getDesignRenderer().getChildren().add(n);
    }

    public ArrayList getItems() {
        return items;
    }

    @Override
    public void reset() {

        setState(SELECTING_CLASS);
        newItem = null;
        selectedItem = null;

        items.clear();
        ((Workspace) app.getWorkspaceComponent()).getDesignRenderer().getChildren().clear();

    }

    public void zoomIn() {
        Workspace workspace = (Workspace) app.getWorkspaceComponent();
        Pane p = ((Workspace) app.getWorkspaceComponent()).getDesignRenderer();
//         p.setPrefHeight(p.getPrefHeight() + 50);
//         p.setPrefWidth(p.getPrefWidth() + 50);
        p.setScaleX(p.getScaleX() + zoomAmount);
        p.setScaleY(p.getScaleY() + zoomAmount);
        ((Workspace) app.getWorkspaceComponent()).getDesignRendererScroll().setContent(p);
//         p.setLayoutX(0);
//         p.setLayoutY(0);
//        p.setPrefWidth(zoomAmount + p.getPrefWidth());
//        
//        p.setPrefHeight(zoomAmount + p.getPrefHeight());
//        for(Node i : p.getChildren() ) {
//            Item item = (Item) i;
//            
//            item.setPrefHeight(item.getPrefHeight() + zoomAmount);
//            item.setPrefWidth(item.getPrefWidth() + zoomAmount);
//            item.setLayoutX(item.getLayoutX() + zoomAmount/2);
//            item.setLayoutY(item.getLayoutY() + zoomAmount/2);
//        }
//       
    }

    public void zoomOut() {
        Pane p = ((Workspace) app.getWorkspaceComponent()).getDesignRenderer();
        p.setScaleX(p.getScaleX() - zoomAmount);
        p.setScaleY(p.getScaleY() - zoomAmount);
        ((Workspace) app.getWorkspaceComponent()).getDesignRendererScroll().setContent(p);

    }

//    public void linkLines() {
//        ComboBox cb = ((Workspace) app.getWorkspaceComponent()).getParentComboBox();
//        Pane p = ((Workspace) app.getWorkspaceComponent()).getDesignRenderer();
//        if (this.getSelectedItem() instanceof JClass) {
////            if (this.getSelectedItem().getName().equals(cb.getValue())) {
//            for (Node n : p.getChildren()) {
//                Item i = (Item) n;
//                if (i.getName().equals(cb.getValue())) {
//                    JClass jC = (JClass) this.getSelectedItem();
//                    jC.addParent(i);
//                    buildLine(i);
//                    buildArrow(i.layoutXProperty(), i.layoutYProperty(), i);
//
//                }
//            }
//        }
//        Workspace workspace = (Workspace) app.getWorkspaceComponent();
////        memento = new JClassDesignerMemento(workspace);
////        memento.add(new WorkspaceData(workspace));
//        memento.add(workspace.getDesignRenderer().getChildren());
//
////        memento.add(deepCopyDataManager());
//    }
    public void buildLine(Item i) {
        Line line = new BoundLine(i.layoutXProperty(), i.layoutYProperty(),
                this.getSelectedItem().layoutXProperty(), this.getSelectedItem().layoutYProperty());
        i.addParentLine(line);
        this.getSelectedItem().addChildLine(line);
//        line.setOnMousePressed(e -> {
//            System.out.println("****Click line works******");
//            Circle c = new Circle(5);
//
////            double midpointX = line.startXProperty().add(line.endXProperty()).divide(2);
////            c.centerXProperty().bind(line.startXProperty().add(line.endXProperty()).divide(2));
////            c.centerYProperty().bind(line.startYProperty().add(line.endYProperty()).divide(2));
//            c.setCenterX((line.getStartX() + line.getEndX()) / 2);
//            c.setCenterY((line.getStartY() + line.getEndY()) / 2);
//            ((Workspace) app.getWorkspaceComponent()).getDesignRenderer().getChildren().remove(line);
//            Line l = new BoundLine(i.layoutXProperty(), i.layoutYProperty(), c.centerXProperty(), c.centerYProperty());
//            Line l2 = new BoundLine(c.centerXProperty(), c.centerYProperty(), this.getSelectedItem().layoutXProperty(), this.getSelectedItem().layoutYProperty());
//            System.out.println("******** line 1 *****" + l.toString());
//            System.out.println("******** line 2 *****" + l2.toString());
//
//            ((Workspace) app.getWorkspaceComponent()).getDesignRenderer().getChildren().addAll(c, l, l2);
//
//            c.setOnMousePressed(t -> {
//                orgSceneX = t.getSceneX();
//                orgSceneY = t.getSceneY();
//                orgTranslateX = ((Circle) (t.getSource())).getCenterX();
//                orgTranslateY = ((Circle) (t.getSource())).getCenterY();
//
//            });
//
//            c.setOnMouseDragged(t -> {
//                double offsetX = t.getSceneX() - orgSceneX;
//                double offsetY = t.getSceneY() - orgSceneY;
////                double newTranslateX = orgTranslateX + offsetX;
////                double newTranslateY = orgTranslateY + offsetY;
//                double newTranslateX = orgTranslateX + offsetX;
//                double newTranslateY = orgTranslateY + offsetY;
//
////                ((Workspace) app.getWorkspaceComponent()).getDesignRenderer().getChildren().removeAll(l, l2);
//                Line l3 = new BoundLine(i.layoutXProperty(), i.layoutYProperty(), c.centerXProperty(), c.centerYProperty());
//                Line l4 = new BoundLine(c.centerXProperty(), c.centerYProperty(), this.getSelectedItem().layoutXProperty(), this.getSelectedItem().layoutYProperty());
//
////                ((Circle) (t.getSource())).setLayoutX(newTranslateX);
////                ((Circle) (t.getSource())).setLayoutY(newTranslateY);
//                ((Circle) (t.getSource())).setCenterX(newTranslateX);
//                ((Circle) (t.getSource())).setCenterY(newTranslateY);
//
//                System.out.println("t get scene x " + t.getSceneX());
//                System.out.println("t get scene y " + t.getSceneY());
//                System.out.println("orig scene x " + orgSceneX);
//                System.out.println("orig scene y " + orgSceneY);
//                System.out.println("orig Translate x " + orgTranslateX);
//                System.out.println("orig Translate y " + orgTranslateY);
//
//                System.out.println("******** circle 1 translate x*****" + ((Circle) (t.getSource())).getTranslateX());
//                System.out.println("******** circle 1 translate y *****" + ((Circle) (t.getSource())).getTranslateY());
//
////                c.centerXProperty().bind(line.startXProperty().add(line.endXProperty()).divide(2));
////                c.centerYProperty().bind(line.startYProperty().add(line.endYProperty()).divide(2));
////                l.endXProperty().bind(c.translateXProperty());
////                l.endYProperty().bind(c.translateYProperty());
////                l2.startXProperty().bind(c.translateXProperty());
////                l2.startYProperty().bind(c.translateYProperty());
//
//                System.out.println("******** line 1 *****" + l.toString());
//                System.out.println("******** line 2 *****" + l2.toString());
//                System.out.println("******** circle 1 *****" + c.toString());
//
////                ((Workspace) app.getWorkspaceComponent()).getDesignRenderer().getChildren().addAll(l3, l4);
//            });
//
//            int clickCount = e.getClickCount();
//            if (clickCount == 2) {
//                System.out.println("****Double Click line works******");
//            }
//        });
//        DoubleProperty sX = new SimpleDoubleProperty(startX);
//        DoubleProperty sY = new SimpleDoubleProperty(startY);
//
//        line.startXProperty().bind(sX);
//        line.startYProperty().bind(sY);
//        line.setStartX(startX);
//        line.setStartY(startY);
        ((Workspace) app.getWorkspaceComponent()).getDesignRenderer().getChildren().add(line);
        memento.add(((Workspace) app.getWorkspaceComponent()).getDesignRenderer().getChildren());
    }

    public void buildArrow(Item p, Item i) {
//        Circle point = new Circle(.5);
//        point.centerXProperty().bind(x);
//        point.centerYProperty().bind(y);
//        DoubleProperty topBottomX = new SimpleDoubleProperty( + 50);
//        DoubleProperty topBottomY = new SimpleDoubleProperty(point.centerYProperty().get() + 50);
//        DoubleProperty bottomX = new SimpleDoubleProperty(point.centerXProperty().get() - 50);
//        DoubleProperty bottomY = new SimpleDoubleProperty(point.centerYProperty().get() - 50);

        Polygon arrow = new Polygon();
        arrow.getPoints().addAll(new Double[]{
            0.0, 10.0,
            -10.0, -10.0,
            10.0, -10.0});
        arrow.setRotate(270);
//        pl.getPoints().add(point.getCenterY())
        arrow.layoutXProperty().bind(p.layoutXProperty());
        arrow.layoutYProperty().bind(p.layoutYProperty());

        p.addParentShape(arrow);
        i.addChildShape(arrow);

//        Line top = new BoundLine(point.centerXProperty(), point.centerYProperty(), topBottomX, topBottomY);
//        Line bottom = new BoundLine(point.centerXProperty(), point.centerYProperty(), bottomX, bottomY);
//        Line top = new Line();
//
////         DoubleProperty topCenterX = new SimpleDoubleProperty();
////         DoubleProperty topCenterY = new SimpleDoubleProperty(point.centerYProperty().get());
//        top.startXProperty().bind(x);
//        top.startYProperty().bind(y);
//        DoubleProperty topBottomX = new SimpleDoubleProperty(top.startXProperty().get() + 50);
//        DoubleProperty topBottomY = new SimpleDoubleProperty(top.startYProperty().get() + 50);
//        top.endXProperty().bind(top.startXProperty().subtract(15));
//        top.endYProperty().bind(top.startYProperty().subtract(15));
//
//        Line bottom = new Line();
//        bottom.startXProperty().bind(x);
//        bottom.startYProperty().bind(y);
////         DoubleProperty bottomX = new SimpleDoubleProperty(top.startXProperty().get() - 50);
////         DoubleProperty bottomY = new SimpleDoubleProperty(top.startYProperty().get() - 50);
//        bottom.endXProperty().bind(bottom.startXProperty().add(15));
//        bottom.endYProperty().bind(bottom.startYProperty().add(15));
//        ((Workspace) app.getWorkspaceComponent()).getDesignRenderer().getChildren().addAll(bottom, top);
        ((Workspace) app.getWorkspaceComponent()).getDesignRenderer().getChildren().addAll(arrow);
    }

    public void buildFeatheredArrow(DoubleProperty x, DoubleProperty y, Item i) {
        Polyline pl = new Polyline();
        pl.getPoints().addAll(new Double[]{
            0.0, 10.0,
            -10.0, -10.0,
            10.0, -10.0});
//        pl.setRotate(90);

        pl.layoutXProperty().bind(x);
        pl.layoutYProperty().bind(y);

        ((Workspace) app.getWorkspaceComponent()).getDesignRenderer().getChildren().addAll(pl);

    }

    public void buildDiamond(DoubleProperty x, DoubleProperty y, Item i) {
//        double[] xh = {-.5, 0, .5, 0, 0, -.5, 0, .5};
//        double[] yh = {0, -.5, 0, .5};

        Polygon p = new Polygon();
        p.getPoints().addAll(new Double[]{
            0.0, 10.0,
            -10.0, -10.0,
            0.0, -30.0,
            10.0, -10.0,});

        p.setRotate(90);

        p.layoutXProperty().bind(x);
        p.layoutYProperty().bind(y);
        ((Workspace) app.getWorkspaceComponent()).getDesignRenderer().getChildren().addAll(p);

    }

//    public void updateParentNames() {
//        Workspace workspace = (Workspace) app.getWorkspaceComponent();
//        workspace.getParentComboBox().getItems().clear();
//        for (Node n : workspace.getDesignRenderer().getChildren()) {
//            Item i = (Item) n;
//            workspace.getParentComboBox().getItems().add(i.getName());
//        }
////        Workspace workspace = (Workspace) app.getWorkspaceComponent();
////        memento = new JClassDesignerMemento(workspace);
////        memento.add(new WorkspaceData(workspace));
//        memento.add(workspace.getDesignRenderer().getChildren());
//
////        memento.add(deepCopyDataManager());
//    }
    public void resize(Item i) {
        Circle c1 = new Circle(10);
        c1.centerXProperty().bind(i.layoutXProperty());
        c1.centerYProperty().bind(i.layoutYProperty());
        c1.setOnMouseDragged(e -> {
            i.setPrefHeight(e.getSceneY());
            i.setPrefWidth(e.getSceneX());
        });

        ((Workspace) app.getWorkspaceComponent()).getDesignRenderer().getChildren().add(c1);

        c1.setOnMouseDragReleased(e -> {
            ((Workspace) app.getWorkspaceComponent()).getDesignRenderer().getChildren().remove(c1);
        });
        Workspace workspace = (Workspace) app.getWorkspaceComponent();
//        memento = new JClassDesignerMemento(workspace);
//        memento.add(new WorkspaceData(workspace));
        memento.add(workspace.getDesignRenderer().getChildren());
//        memento.add(deepCopyDataManager());

    }

    public void undo() {

//        app.getWorkspaceComponent().setWorkspace(memento.getSavedState().getWorkspace());
        Workspace workspace = (Workspace) app.getWorkspaceComponent();
        workspace.getDesignRenderer().getChildren().clear();
//        WorkspaceData workspaceMemento = memento.getSavedState();
//        System.out.println("*******undo method test workspacememento : " + memento.getSavedState().getDesignRenderer().getChildren().size());
//        workspace.setDesignRenderer(workspaceMemento.getDesignRenderer());
//        System.out.println("*******Memento get children in undo method : " + workspaceMemento.getDesignRenderer().getChildren().size());
//        for(Node n : workspaceMemento.getDesignRenderer().getChildren()) {
//            Item i = (Item) n;
//            addToWorkspace(i);
//        }
//        System.out.println("********** Workspace set in undo : ");
        ArrayList<Node> list = memento.getSavedState();
        System.out.println("*******Memento get children in undo method : " + list.toString());

        for (Node n : list) {
            if (n instanceof Item) {
                Item i = (Item) n;
                addToWorkspace(i);
            } else if (n instanceof BoundLine) {
                BoundLine l = (BoundLine) n;
                addLineToWorkspace(l);
            }
//            workspace.getDesignRenderer().getChildren().add(i);
        }

    }

    public void redo() {
        Workspace workspace = (Workspace) app.getWorkspaceComponent();
        workspace.getDesignRenderer().getChildren().clear();

        ArrayList<Node> list = memento.getRedoState();
        System.out.println("*******Memento get children in redo method : " + list.toString());

        for (Node n : list) {
            System.out.println("*******Node in redo method : " + n.toString());

            if (n instanceof Item) {
                Item i = (Item) n;
                addToWorkspace(i);
            } else if (n instanceof Line) {
                BoundLine l = (BoundLine) n;
                addLineToWorkspace(l);
                System.out.println("*******Add BoundLine : " + workspace.getDesignRenderer().getChildren().toString());

            }
//            workspace.getDesignRenderer().getChildren().add(i);
        }
    }

    public void snap() {

        Workspace w = (Workspace) app.getWorkspaceComponent();
        for (Node n : w.getDesignRenderer().getChildren()) {
            if (n instanceof Item) {
//            Item i = (Item) n;
                int x = (int) (n.getLayoutX() / 20);
                int y = (int) (n.getLayoutY() / 20);
                int offX = x * 20;
                int offY = y * 20;
                n.setLayoutX(offX);
                n.setLayoutY(offY);
            }
        }
    }

    public JClassDesignerMemento getMemento() {
        return memento;
    }

    public DataManager deepCopyDataManager() {
        DataManager w = this;
        DataManager r = null;
        try {
            r = (DataManager) w.clone();
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        return r;
    }

    public void create() {
        JClass threadExample = new JClass();
        threadExample.setName("ThreadExample");
        Variable START_TEXT = new Variable();
        START_TEXT.setName("START_TEXT");
        START_TEXT.setAccess("public");
        START_TEXT.setType("String");
        START_TEXT.setS(true);
        Variable PAUSE_TEXT = new Variable();
        PAUSE_TEXT.setName("PAUSE_TEXT");
        PAUSE_TEXT.setAccess("public");
        PAUSE_TEXT.setType("String");
        PAUSE_TEXT.setS(true);
        Variable window = new Variable();
        window.setName("window");
        window.setAccess("private");
        window.setType("Stage");
        Variable appPane = new Variable();
        appPane.setName("appPane");
        appPane.setAccess("private");
        appPane.setType("BorderPane");
        Variable topPane = new Variable();
        topPane.setName("topPane");
        topPane.setAccess("private");
        topPane.setType("FlowPane");
        Variable startButton = new Variable();
        startButton.setName("startButton");
        startButton.setAccess("private");
        startButton.setType("Button");
        Variable pauseButton = new Variable();
        pauseButton.setName("pauseButton");
        pauseButton.setAccess("private");
        pauseButton.setType("Button");
        Variable scrollPane = new Variable();
        scrollPane.setName("scrollPane");
        scrollPane.setAccess("private");
        scrollPane.setType("ScrollPane");
        Variable textArea = new Variable();
        textArea.setName("textArea");
        textArea.setAccess("private");
        textArea.setType("TextArea");
        Variable dateThread = new Variable();
        dateThread.setName("dateThread");
        dateThread.setAccess("private");
        dateThread.setType("Thread");
        Variable dateTask = new Variable();
        dateTask.setName("dateTask");
        dateTask.setAccess("private");
        dateTask.setType("Task");
        Variable counterThread = new Variable();
        counterThread.setName("counterThread");
        counterThread.setAccess("private");
        counterThread.setType("Thread");
        Variable counterTask = new Variable();
        counterTask.setName("counterTask");
        counterTask.setAccess("private");
        counterTask.setType("Task");
        Variable work = new Variable();
        work.setName("work");
        work.setAccess("private");
        work.setType("boolean");
        Method start = new Method();
        start.setName("start");
        start.setAccess("public");
        start.setType("void");
        start.addArg("Stage primaryStage");
        Method startWork = new Method();
        startWork.setName("startWork");
        startWork.setAccess("public");
        startWork.setType("void");
        Method pauseWork = new Method();
        pauseWork.setName("startWork");
        pauseWork.setAccess("public");
        pauseWork.setType("void");
        Method doWork = new Method();
        doWork.setName("doWork");
        doWork.setAccess("public");
        doWork.setType("boolean");
        Method appendText = new Method();
        appendText.setName("appendText");
        appendText.setAccess("public");
        appendText.setType("void");
        appendText.addArg("String textToAppend");
        Method sleep = new Method();
        sleep.setName("sleep");
        sleep.setAccess("public");
        sleep.setType("void");
        sleep.addArg("int timeToSleep");
        Method initLayout = new Method();
        initLayout.setName("initLayout");
        initLayout.setAccess("private");
        initLayout.setType("void");
        Method initHandlers = new Method();
        initHandlers.setName("initHandlers");
        initHandlers.setAccess("private");
        initHandlers.setType("void");
        Method initWindow = new Method();
        initWindow.setName("initWindow");
        initWindow.setAccess("private");
        initWindow.setType("void");
        initWindow.addArg("Stage initPrimaryStage");
        Method initThreads = new Method();
        initThreads.setName("initThreads");
        initThreads.setAccess("private");
        initThreads.setType("void");
        Method main = new Method();
        main.setName("main");
        main.setAccess("public");
        main.setS(true);
        main.setType("void");
        main.addArg("String[] args");
        threadExample.addVariable(START_TEXT);
        threadExample.addVariable(PAUSE_TEXT);
        threadExample.addVariable(window);
        threadExample.addVariable(appPane);
        threadExample.addVariable(topPane);
        threadExample.addVariable(startButton);
        threadExample.addVariable(pauseButton);
        threadExample.addVariable(scrollPane);
        threadExample.addVariable(textArea);
        threadExample.addVariable(dateThread);
        threadExample.addVariable(dateTask);
        threadExample.addVariable(counterTask);
        threadExample.addVariable(work);
        threadExample.addMethod(start);
        threadExample.addMethod(startWork);
        threadExample.addMethod(pauseWork);
        threadExample.addMethod(doWork);
        threadExample.addMethod(appendText);
        threadExample.addMethod(sleep);
        threadExample.addMethod(initLayout);
        threadExample.addMethod(initHandlers);
        threadExample.addMethod(initWindow);
        threadExample.addMethod(initThreads);
        threadExample.addMethod(main);

        JClass counterTaskClass = new JClass();
        counterTaskClass.setName("CounterThread");
        Variable appp = new Variable();
        appp.setName("app");
        appp.setAccess("private");
        appp.setType("ThreadExample");
        Variable counter = new Variable();
        counter.setName("counter");
        counter.setType("int");
        counter.setAccess("private");
        Method counterTaskMethod = new Method();
        counterTaskMethod.setName("");
        counterTaskMethod.setType("CounterTask");
        counterTaskMethod.setAccess("public");
        counterTaskMethod.addArg("ThreadExample initApp");
        Method call = new Method();
        call.setAccess("protected");
        call.setName("call");
        call.setType("void");
        counterTaskClass.addVariable(appp);
        counterTaskClass.addVariable(counter);
        counterTaskClass.addMethod(counterTaskMethod);
        counterTaskClass.addMethod(call);

        JClass dateTaskClass = new JClass();
        dateTaskClass.setName("DateTask");
        Variable apppp = new Variable();
        appp.setName("app");
        appp.setType("ThreadExample");
        appp.setAccess("private");
        Variable now = new Variable();
        appp.setName("now");
        appp.setType("Date");
        appp.setAccess("private");
        Method dateTaskMethod = new Method();
        dateTaskMethod.setType("DateTask");
        dateTaskMethod.setName("");
        dateTaskMethod.setAccess("public");
        dateTaskMethod.addArg("ThreadExample initApp");
        Method callMethod = new Method();
        callMethod.setAccess("protected");
        callMethod.setName("call");
        callMethod.setType("void");
        dateTaskClass.addVariable(apppp);
        dateTaskClass.addVariable(now);
        dateTaskClass.addMethod(dateTaskMethod);
        dateTaskClass.addMethod(callMethod);

        JClass pauseHandler = new JClass();
        pauseHandler.setName("PauseHandler");
        Variable appPauseHandler = new Variable();
        appPauseHandler.setType("ThreadExample");
        appPauseHandler.setAccess("public");
        appPauseHandler.setName("app");
        Method pauseHandlerMethod = new Method();
        pauseHandlerMethod.setName("");
        pauseHandlerMethod.setType("PauseHandler");
        pauseHandlerMethod.setAccess("public");
        pauseHandlerMethod.addArg("ThreadExample initApp");
        Method handlerPause = new Method();
        handlerPause.setName("handle");
        handlerPause.setAccess("public");
        handlerPause.setType("void");
        handlerPause.addArg("Event event");
        pauseHandler.addVariable(appPauseHandler);
        pauseHandler.addMethod(pauseHandlerMethod);
        pauseHandler.addMethod(handlerPause);

        JClass startHandlerHandler = new JClass();
        startHandlerHandler.setName("StartHandler");
        Variable appStartHandler = new Variable();
        appStartHandler.setName("app");
        appStartHandler.setAccess("private");
        appStartHandler.setType("ThreadExample");
        Method startHandler = new Method();
        startHandler.setName("");
        startHandler.setType("StartHandler");
        startHandler.addArg("ThreadExample initApp");
        startHandler.setAccess("public");
        Method startHandle = new Method();
        startHandle.setName("handle");
        startHandle.addArg("Event event");
        startHandle.setAccess("public");
        startHandler.setType("void");
        startHandlerHandler.addVariable(appStartHandler);
        startHandlerHandler.addMethod(startHandler);
        startHandlerHandler.addMethod(startHandle);

//        items.add(threadExample);
//        items.add(counterTaskClass);
//        items.add(dateTaskClass);
//        items.add(pauseHandler);
//        items.add(startHandlerHandler);
        this.addClass(threadExample);
        this.addClass(counterTaskClass);
        this.addClass(dateTaskClass);
        this.addClass(pauseHandler);
        this.addClass(startHandlerHandler);

    }

    class BoundLine extends Line {

        DoubleProperty startX;
        DoubleProperty startY;
        DoubleProperty endX;
        DoubleProperty endY;
        ArrayList<Circle> circleList = new ArrayList();

        BoundLine(DoubleProperty startX, DoubleProperty startY, DoubleProperty endX, DoubleProperty endY) {
            this.startX = startX;
            this.startY = startY;
            this.endX = endX;
            this.endY = endY;
            startXProperty().bind(startX);
            startYProperty().bind(startY);
            endXProperty().bind(endX);
            endYProperty().bind(endY);
            setStrokeWidth(5);
            setStroke(Color.GRAY.deriveColor(0, 1, 1, 0.5));
            setStrokeLineCap(StrokeLineCap.BUTT);
            getStrokeDashArray().setAll(10.0, 5.0);

            initHandler();

//            setMouseTransparent(true);
        }

        public void initPointHandler(Circle c) {

            c.setOnMousePressed(t -> {
                orgSceneX = t.getSceneX();
                orgSceneY = t.getSceneY();
                orgTranslateX = ((Circle) (t.getSource())).getCenterX();
                orgTranslateY = ((Circle) (t.getSource())).getCenterY();

            });

            c.setOnMouseDragged(t -> {
                double offsetX = t.getSceneX() - orgSceneX;
                double offsetY = t.getSceneY() - orgSceneY;
//                double newTranslateX = orgTranslateX + offsetX;
//                double newTranslateY = orgTranslateY + offsetY;
                double newTranslateX = orgTranslateX + offsetX;
                double newTranslateY = orgTranslateY + offsetY;

//                ((Workspace) app.getWorkspaceComponent()).getDesignRenderer().getChildren().removeAll(l, l2);
//                Line l3 = new BoundLine(i.layoutXProperty(), i.layoutYProperty(), c.centerXProperty(), c.centerYProperty());
//                Line l4 = new BoundLine(c.centerXProperty(), c.centerYProperty(), this.getSelectedItem().layoutXProperty(), this.getSelectedItem().layoutYProperty());
//                ((Circle) (t.getSource())).setLayoutX(newTranslateX);
//                ((Circle) (t.getSource())).setLayoutY(newTranslateY);
                ((Circle) (t.getSource())).setCenterX(newTranslateX);
                ((Circle) (t.getSource())).setCenterY(newTranslateY);

                System.out.println("t get scene x " + t.getSceneX());
                System.out.println("t get scene y " + t.getSceneY());
                System.out.println("orig scene x " + orgSceneX);
                System.out.println("orig scene y " + orgSceneY);
                System.out.println("orig Translate x " + orgTranslateX);
                System.out.println("orig Translate y " + orgTranslateY);

                System.out.println("******** circle 1 translate x*****" + ((Circle) (t.getSource())).getTranslateX());
                System.out.println("******** circle 1 translate y *****" + ((Circle) (t.getSource())).getTranslateY());

//                c.centerXProperty().bind(line.startXProperty().add(line.endXProperty()).divide(2));
//                c.centerYProperty().bind(line.startYProperty().add(line.endYProperty()).divide(2));
//                l.endXProperty().bind(c.translateXProperty());
//                l.endYProperty().bind(c.translateYProperty());
//                l2.startXProperty().bind(c.translateXProperty());
//                l2.startYProperty().bind(c.translateYProperty());
//                    System.out.println("******** line 1 *****" + l.toString());
//                    System.out.println("******** line 2 *****" + l2.toString());
//                    System.out.println("******** circle 1 *****" + c.toString());
//                ((Workspace) app.getWorkspaceComponent()).getDesignRenderer().getChildren().addAll(l3, l4);
            });

        }

        public void initHandler() {

            this.setOnMousePressed(e -> {
                System.out.println("****Click line works******");
                Circle c = new Circle(7);

//            double midpointX = line.startXProperty().add(line.endXProperty()).divide(2);
//            c.centerXProperty().bind(line.startXProperty().add(line.endXProperty()).divide(2));
//            c.centerYProperty().bind(line.startYProperty().add(line.endYProperty()).divide(2));
//                c.setCenterX((this.getStartX() + this.getEndX()) / 2);
//                c.setCenterY((this.getStartY() + this.getEndY()) / 2);
//                ((Workspace) app.getWorkspaceComponent()).getDesignRenderer().getChildren().remove(this);
//                Line l = new BoundLine(startX, startY, c.centerXProperty(), c.centerYProperty());
//                Line l2 = new BoundLine(c.centerXProperty(), c.centerYProperty(), endX, endY);
//                System.out.println("******** line 1 *****" + l.toString());
//                System.out.println("******** line 2 *****" + l2.toString());
//
//                ((Workspace) app.getWorkspaceComponent()).getDesignRenderer().getChildren().addAll(l, l2, c);
                initPointHandler(c);

                this.addPoint(c);
                Workspace workspace = (Workspace) app.getWorkspaceComponent();
                memento.add(workspace.getDesignRenderer().getChildren());

                System.out.println("****** BoundLine initHandler this.toString *****" + this.toString());

                int clickCount = e.getClickCount();
                if (clickCount == 2) {
                    System.out.println("****Double Click line works******");
                }
            });

        }

        public void setPoints(ArrayList<Circle> circleList) {

            this.circleList = circleList;

        }

        public ArrayList<Circle> getCircleList() {
            return circleList;
        }

        public void addPoint(Circle c) {
            this.circleList.add(c);
            System.out.println("****** BoundLine addPoint number of poinst *****" + this.toString());

            bindPoints(c);
        }

        public void bindPoints(Circle c) {
            c.setCenterX((this.getStartX() + this.getEndX()) / 2);
            c.setCenterY((this.getStartY() + this.getEndY()) / 2);
            ((Workspace) app.getWorkspaceComponent()).getDesignRenderer().getChildren().remove(this);
            Line l = new BoundLine(startX, startY, c.centerXProperty(), c.centerYProperty());
            Line l2 = new BoundLine(c.centerXProperty(), c.centerYProperty(), endX, endY);
            System.out.println("******** line 1 *****" + l.toString());
            System.out.println("******** line 2 *****" + l2.toString());
            ((Workspace) app.getWorkspaceComponent()).getDesignRenderer().getChildren().addAll(l, l2, c);
        }

        public BoundLine deepCopy() {
            System.out.println("****** BoundLine deepCopy this.toString *****" + this.toString());

            BoundLine l = new BoundLine(this.startX, this.startY, this.endX, this.endY);
//            if (this.getCircleList().size() > 0) {
            System.out.println("****** BoundLine deepCopy number of poinst before for loop *****" + this.getCircleList().size());

            for (Circle c : this.getCircleList()) {

                l.addPoint(c);
                System.out.println("****** BoundLine deepCopy number of poinst *****" + l.getCircleList().size());

//                l.bindPoints(c);
            }

//            }
//            l.setPoints(this.getCircleList());
//            l.initHandler();
            return l;
        }
        
        @Override
        public String toString() {
            String s = new String();
            s = " Start x: " + startX.toString()  + "Start y: " + startY.toString() + "End x: " + endX + " End y: " + endY + "Mem adress: " + Integer.toHexString(System.identityHashCode(this)) +
                    "Point List: " + this.circleList.size();
            return s;
        }
    }

}
