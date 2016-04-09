/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.data;


import java.util.ArrayList;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import jc.gui.Workspace;
import saf.AppTemplate;

/**
 *
 * @author Steve
 */
public class Class extends VBox implements Draggable{
    
    AppTemplate app;
    double orgSceneX, orgSceneY;
    double orgTranslateX, orgTranslateY;
    VBox  nameVBox, methodVBox, variableVBox;
    Label name = new Label("DefaultClassName");
    Label pkg = new Label("DefaultPackageName");
    ArrayList<String> methods;
    ArrayList<String> variables;
    
     

    

    public Class(AppTemplate initApp) {
        app = initApp;
        orgSceneX = 0;
        orgSceneY = 0;
        
        nameVBox = new VBox();
        methodVBox = new VBox();
        variableVBox = new VBox();
        
        nameVBox.getChildren().add(name);
        
        nameVBox.getStyleClass().add("vbox");
        methodVBox.getStyleClass().add("vbox");
        methodVBox.setMinHeight(30);
        methodVBox.setMinWidth(30);
        variableVBox.getStyleClass().add("vbox");
//        variableVBox.setMinHeight(30);
//        variableVBox.setMinWidth(30);
        initHandler();
        
        getChildren().addAll(nameVBox, methodVBox, variableVBox);
        
        this.getStyleClass().add("vbox");
//        this.setPrefSize(100, 100);
        this.setHeight(100);
        this.setWidth(100);
    }
    
    @Override
    public void start(int x, int y) {   
        orgSceneX = x;
	orgSceneY = y;
	setLayoutX(x);
	setLayoutY(y);
        System.out.println(this.getPrefHeight() + "//" + this.getPrefWidth());
    }

    @Override
    public void drag(int x, int y) {
//        double diffX = x - (getLayoutX() + (getWidth()/2));
//	double diffY = y - (getLayoutY() + (getHeight()/2));
//	double newX = getLayoutX() + diffX;
//	double newY = getLayoutY() + diffY;
//	xProperty().set(newX);
//	yProperty().set(newY);
//	startX = x;
//	startY = y;
//        double offsetX = x - orgSceneX;
//        double offsetY = y - orgSceneY;
//        double newTranslateX = orgTranslateX + offsetX;
//        double newTranslateY = orgTranslateY + offsetY;
//
//        this.setTranslateX(newTranslateX);
//        this.setTranslateX(newTranslateY);
         this.setTranslateX(x);
         this.setTranslateY(y);

    }
    
    public void initHandler() {
        this.setOnMouseDragged(e -> {
//            drag((int)e.getX(), (int)e.getY());
           DataManager data = (DataManager)app.getDataComponent();
//           data.setSelectedClass(this);
//           data.highlightItem(this);
           this.relocate(e.getSceneX(), e.getSceneY());
        });
        this.setOnMousePressed(e -> {
            DataManager data = (DataManager)app.getDataComponent();
            Workspace workspace = (Workspace)app.getWorkspaceComponent();
            data.setSelectedClass(this);
            workspace.setClassNameText(data.getSelectedClass().getName());
            workspace.setPackageNameText(data.getSelectedClass().getPackageName());
    });

    }

    @Override
    public void size(int x, int y) {
        
//        double width = x - getLayoutX();
//	widthProperty().set(width);
//	double height = y - getLayoutY();
//	heightProperty().set(height);
    }

//    @Override
//    public double getX() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public double getY() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public double getWidth() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public double getHeight() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
    
    public void setName(String name) {
        this.name.setText(name);
    }
    
    public void setPackage(String name) {
        this.pkg.setText(name);
    }
    
    public void addMethod(String method) {
        methods.add(method);
    }
    
    public ArrayList getMethods() {
        return methods;
    }
    
    public void addVariable(String variable) {
        variables.add(variable);
    }
    
    public ArrayList getVariables() {
        return variables;
    }
    
    public String getName() {
        return name.getText();
    }
    
    public String getPackageName() {
        return pkg.getText();
    }

    @Override
    public void setLocationAndSize(double initX, double initY, double initWidth, double initHeight) {
//        xProperty().set(initX);
//	yProperty().set(initY);
//	widthProperty().set(initWidth);
//	heightProperty().set(initHeight);
    }

    @Override
    public String getShapeType() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double getX() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double getY() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
