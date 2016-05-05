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
        items.remove(itemToRemove);
        Workspace workspace = (Workspace) app.getWorkspaceComponent();
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
        workspace.getDesignRenderer().getChildren().add(i);
        workspace.getParentComboBox().getItems().add(i.getName());
        i.initHandler(this, workspace);
//        Workspace workspace = (Workspace) app.getWorkspaceComponent();
//        memento = new JClassDesignerMemento(workspace);
//        memento.add(new WorkspaceData(workspace));
        memento.add(workspace.getDesignRenderer().getChildren());

//        memento.add(deepCopyDataManager());
//        ((Workspace)app.getWorkspaceComponent()).getGroup().getChildren().add(i);
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

    public void linkLines() {
        ComboBox cb = ((Workspace) app.getWorkspaceComponent()).getParentComboBox();
        Pane p = ((Workspace) app.getWorkspaceComponent()).getDesignRenderer();
        if (this.getSelectedItem() instanceof JClass) {
//            if (this.getSelectedItem().getName().equals(cb.getValue())) {
            for (Node n : p.getChildren()) {
                Item i = (Item) n;
                if (i.getName().equals(cb.getValue())) {
                    JClass jC = (JClass) this.getSelectedItem();
                    jC.addParent(i);
                    buildLine(i);
                    buildArrow(i.layoutXProperty(), i.layoutYProperty());

                }
            }
        }
        Workspace workspace = (Workspace) app.getWorkspaceComponent();
//        memento = new JClassDesignerMemento(workspace);
//        memento.add(new WorkspaceData(workspace));
        memento.add(workspace.getDesignRenderer().getChildren());

//        memento.add(deepCopyDataManager());
    }

    public void buildLine(Item i) {
        Line line = new BoundLine(i.layoutXProperty(), i.layoutYProperty(),
                this.getSelectedItem().layoutXProperty(), this.getSelectedItem().layoutYProperty());
        line.setOnMousePressed(e -> {
            System.out.println("****Click line works******");

            int clickCount = e.getClickCount();
            if (clickCount == 2) {
                System.out.println("****Double Click line works******");
            }
        });
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

    public void buildArrow(DoubleProperty x, DoubleProperty y) {
//        Circle point = new Circle(.5);
//        point.centerXProperty().bind(x);
//        point.centerYProperty().bind(y);
//        DoubleProperty topBottomX = new SimpleDoubleProperty( + 50);
//        DoubleProperty topBottomY = new SimpleDoubleProperty(point.centerYProperty().get() + 50);
//        DoubleProperty bottomX = new SimpleDoubleProperty(point.centerXProperty().get() - 50);
//        DoubleProperty bottomY = new SimpleDoubleProperty(point.centerYProperty().get() - 50);

        Polygon arrow = new Polygon();
        arrow.getPoints().addAll(new Double[]{
            0.0, 5.0,
            -5.0, -5.0,
            5.0, -5.0});
//        pl.getPoints().add(point.getCenterY())
        arrow.layoutXProperty().bind(x);
        arrow.layoutYProperty().bind(y);

//        Line top = new BoundLine(point.centerXProperty(), point.centerYProperty(), topBottomX, topBottomY);
//        Line bottom = new BoundLine(point.centerXProperty(), point.centerYProperty(), bottomX, bottomY);
        Line top = new Line();

//         DoubleProperty topCenterX = new SimpleDoubleProperty();
//         DoubleProperty topCenterY = new SimpleDoubleProperty(point.centerYProperty().get());
        top.startXProperty().bind(x);
        top.startYProperty().bind(y);
        DoubleProperty topBottomX = new SimpleDoubleProperty(top.startXProperty().get() + 50);
        DoubleProperty topBottomY = new SimpleDoubleProperty(top.startYProperty().get() + 50);
        top.endXProperty().bind(top.startXProperty().subtract(15));
        top.endYProperty().bind(top.startYProperty().subtract(15));

        Line bottom = new Line();
        bottom.startXProperty().bind(x);
        bottom.startYProperty().bind(y);
//         DoubleProperty bottomX = new SimpleDoubleProperty(top.startXProperty().get() - 50);
//         DoubleProperty bottomY = new SimpleDoubleProperty(top.startYProperty().get() - 50);
        bottom.endXProperty().bind(bottom.startXProperty().add(15));
        bottom.endYProperty().bind(bottom.startYProperty().add(15));

//        ((Workspace) app.getWorkspaceComponent()).getDesignRenderer().getChildren().addAll(bottom, top);
        ((Workspace) app.getWorkspaceComponent()).getDesignRenderer().getChildren().addAll(arrow);
    }

    public void updateParentNames() {
        Workspace workspace = (Workspace) app.getWorkspaceComponent();
        workspace.getParentComboBox().getItems().clear();
        for (Node n : workspace.getDesignRenderer().getChildren()) {
            Item i = (Item) n;
            workspace.getParentComboBox().getItems().add(i.getName());
        }
//        Workspace workspace = (Workspace) app.getWorkspaceComponent();
//        memento = new JClassDesignerMemento(workspace);
//        memento.add(new WorkspaceData(workspace));
        memento.add(workspace.getDesignRenderer().getChildren());

//        memento.add(deepCopyDataManager());
    }

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
        ArrayList<Item> list = memento.getSavedState();
        System.out.println("*******Memento get children in undo method : " + list.size());
        for (Item i : list) {
            addToWorkspace(i);
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

        BoundLine(DoubleProperty startX, DoubleProperty startY, DoubleProperty endX, DoubleProperty endY) {
            startXProperty().bind(startX);
            startYProperty().bind(startY);
            endXProperty().bind(endX);
            endYProperty().bind(endY);
            setStrokeWidth(5);
//            setStroke(Color.GRAY.deriveColor(0, 1, 1, 0.5));
//            setStrokeLineCap(StrokeLineCap.BUTT);
//            getStrokeDashArray().setAll(10.0, 5.0);
//            setMouseTransparent(true);
        }
    }

}
