/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.data;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
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
    Label name = new Label("DefaultClassName");
    Label pkg = new Label("DefaultPackageName");
    
    public Item (AppTemplate initApp) {
        
        app = initApp;
        initHandler();
        
    }
    
    public void setName(String name) {
        this.name.setText(name);
    }
    
    public String getName() {
        return name.getText();
    }
    
    public void setPackage(String name) {
        this.pkg.setText(name);
    }
    
    public String getPackageName() {
        return pkg.getText();
    }

    @Override
    public void start(int x, int y) {
        orgSceneX = x;
        orgSceneY = y;
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
        this.setOnMouseDragged(e -> {
            DataManager data = (DataManager) app.getDataComponent();
            
//            drag((int)e.getX(), (int)e.getY());

            data.setSelectedItem(this);
            
//            data.highlightItem(this);
            this.relocate(e.getSceneX() - this.getWidth() / 2, e.getSceneY() - this.getHeight());
        });
        this.setOnMousePressed(e -> {
            DataManager data = (DataManager) app.getDataComponent();
            Workspace workspace = (Workspace) app.getWorkspaceComponent();
//            ObservableList<Node> list = workspace.getDesignRenderer().getChildren();
//            for (Node n : list) {
//                if (!n.equals(data.getSelectedClass())) {
//                    data.unhighlightItem((Class) n);
//                } 
//            }
            data.setSelectedItem(this);
//            data.highlightItem(this);
//            workspace.setClassNameText(data.getSelectedClass().getName());
//            workspace.setPackageNameText(data.getSelectedClass().getPackageName());
            workspace.setClassNameText(this.getName());
            workspace.setPackageNameText(this.getPackageName());
        });

    }

    @Override
    public void size(int x, int y) {
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

    @Override
    public void setLocationAndSize(double initX, double initY, double initWidth, double initHeight) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getShapeType() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
