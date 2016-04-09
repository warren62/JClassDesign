/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.data;

import java.util.ArrayList;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 *
 * @author Steve
 */
public class Interface extends VBox implements Draggable{
    
    double orgSceneX, orgSceneY;
    double orgTranslateX, orgTranslateY;
    VBox  nameVBox, methodVBox;
    String name = "DefaultClassName";
    ArrayList<String> methods;
     

    

    public Interface() {
        orgSceneX = 0;
        orgSceneY = 0;
        
        nameVBox = new VBox();
        methodVBox = new VBox();
        
        nameVBox.getChildren().add(new Label(name));
        
        getChildren().addAll(nameVBox, methodVBox);
        
        this.getStyleClass().add("vbox");
    }
    
    @Override
    public void start(int x, int y) {   
        orgSceneX = x;
	orgSceneY = y;
	setLayoutX(x);
	setLayoutY(y);
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
        double offsetX = x - orgSceneX;
        double offsetY = y - orgSceneY;
        double newTranslateX = orgTranslateX + offsetX;
        double newTranslateY = orgTranslateY + offsetY;


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
        this.name = name;
    }
    
    public void addMethod(String method) {
        methods.add(method);
    }
    
    public ArrayList getMethods() {
        return methods;
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
