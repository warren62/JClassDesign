/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.data;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import jc.gui.Workspace;
import saf.AppTemplate;

/**
 *
 * @author Steve
 */
public class Item extends VBox implements Draggable {
    
    AppTemplate app;
    double orgSceneX, orgSceneY;
    double orgTranslateX, orgTranslateY;
    double x = 0;

    
    double y = 0;
    boolean ext;
    boolean implementsInterface;
    StringProperty nameProp = new SimpleStringProperty("gg");
    Label nameLbl;
    Label pkgLbl;
    String name = "DefaultClassName";
    String pkg = "DefaultPackageName";
    
    Item parent;

    
    
    public Item (AppTemplate initApp) {
        
        app = initApp;
        initHandler();
//        nameLbl = new Label(name);
    }
    
    public Item() {
        
    }
    
//    public String getNameString() {
//        return nameString;
//    }
//
//    public void setNameString(String nameString) {
//        this.nameString = nameString;
//    }
//
//    public String getPkgString() {
//        return pkgString;
//    }
//
//    public void setPkgString(String pkgString) {
//        this.pkgString = pkgString;
//    }
    
    public boolean isExt() {
        return ext;
    }

    public void setExt(boolean ext) {
        this.ext = ext;
    }

    public boolean isImplementsInterface() {
        return implementsInterface;
    }

    public void setImplementsInterface(boolean implementsInterface) {
        this.implementsInterface = implementsInterface;
    }
    
    public void setName(String name) {
        
//            this.name.setText(name);
           this.name = name;

        
    }
    
    public String getName() {
//        return name.getText();
        return name;
    }
    
    public void setPackage(String name) {
//        this.pkg.setText(name);
        this.pkg = name;
    }
    
    public String getPackageName() {
//        return pkg.getText();
        return pkg;
    }

    
    
    
    
    

    @Override
    public void start(int x, int y) {
//        orgSceneX = x;
//        orgSceneY = y;
        
        setLayoutX(x);
        setLayoutY(y);
        DataManager data = (DataManager) app.getDataComponent();
//        data.setSelectedClass(this);
//        data.highlightItem(this);
        System.out.println(this.getPrefHeight() + "//" + this.getPrefWidth());
    }

    @Override
    public void drag(int x, int y) {
        relocate(x, y);
    }
    
     public void initHandler() {
         
          this.setOnMousePressed(e -> {
            
            orgSceneX = e.getSceneX();
            orgSceneY = e.getSceneY();
            
             x = getLayoutX();
             y = getLayoutY();


            
//            orgTranslateY = ((Item)(e.getSource())).getTranslateY();
//            orgTranslateY = ((Item)(e.getSource())).getTranslateY();
            DataManager data = (DataManager) app.getDataComponent();
            Workspace workspace = (Workspace) app.getWorkspaceComponent();
//            ObservableList<Node> list = workspace.getDesignRenderer().getChildren();
//            for (Node n : list) {
//                if (!n.equals(data.getSelectedClass())) {
//                    data.unhighlightItem((Class) n);
//                } 
//            }
               for(Node n : workspace.getDesignRenderer().getChildren()) {
                   Item i = (Item) n;
                   data.unhighlightItem(i);
               }
            data.highlightItem(this);
            data.setSelectedItem(this);
//            data.highlightItem(this);
//            workspace.setClassNameText(data.getSelectedClass().getName());
//            workspace.setPackageNameText(data.getSelectedClass().getPackageName());
            workspace.setClassNameText(this.getName());
            workspace.setPackageNameText(this.getPackageName());
        });

        this.setOnMouseDragged(e -> {
            
            x += e.getSceneX() - orgSceneX;
            y += e.getSceneY() - orgSceneY;
            
            setLayoutX(x);
            setLayoutY(y);
            
            orgSceneX = e.getSceneX();
            orgSceneY = e.getSceneY();

            
//            double offsetX = e.getSceneX() - orgSceneX;
//            double offsetY = e.getSceneY() - orgSceneY;
//            double newTranslateX = orgTranslateX + offsetX;
//            double newTranslateY = orgTranslateY + offsetY;
            DataManager data = (DataManager) app.getDataComponent();
            
//            drag((int)e.getX(), (int)e.getY());

            data.setSelectedItem(this);
//            ((Item)(e.getSource())).setTranslateX(newTranslateX);
//            ((Item)(e.getSource())).setTranslateY(newTranslateY);
//            data.highlightItem(this);
//            this.relocate(newTranslateX, newTranslateY);
        });

    }

    @Override
    public void size(int x, int y) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double getX() {
        
        return x;
        
    }

    @Override
    public double getY() {
        
        return y;
        
    }

    @Override
    public void setLocationAndSize(double initX, double initY, double initWidth, double initHeight) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getShapeType() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void updateNameLabel() {
        nameLbl.setText(name);
    }
    
}
