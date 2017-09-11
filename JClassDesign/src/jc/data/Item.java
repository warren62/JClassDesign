/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.data;

import java.util.ArrayList;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import jc.data.DataManager.BoundLine;
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

    ArrayList<BoundLine> parentLines = new ArrayList();
    ArrayList<BoundLine> childLines = new ArrayList();

    ArrayList<Shape> parentShapes = new ArrayList();
    ArrayList<Shape> childShapes = new ArrayList();
    
    ArrayList<String> imports = new ArrayList();

    Item parent;

    public Item() {

//        app = initApp;
//        initHandler();
//        nameLbl = new Label(name);
    }

//    public Item() {
//        
//    }
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

    public ArrayList<BoundLine> getParentLines() {
        return parentLines;
    }

    public void setParentLines(ArrayList<BoundLine> parentLines) {
        this.parentLines = parentLines;
    }

    public ArrayList<BoundLine> getChildLines() {
        return childLines;
    }

    public void setChildLines(ArrayList<BoundLine> childLines) {
        this.childLines = childLines;
    }

    public void addParentLine(BoundLine l) {
        parentLines.add(l);
    }

    public void addChildLine(BoundLine l) {
        childLines.add(l);
    }

    public ArrayList<Shape> getParentShapes() {
        return parentShapes;
    }

    public void setParentShapes(ArrayList<Shape> parentShapes) {
        this.parentShapes = parentShapes;
    }

    public ArrayList<Shape> getChildShapes() {
        return childShapes;
    }

    public void setChildShapes(ArrayList<Shape> childShapes) {
        this.childShapes = childShapes;
    }

    public void addParentShape(Shape s) {
        parentShapes.add(s);
    }

    public void addChildShape(Shape s) {
        childShapes.add(s);
    }

    public ArrayList<String> getImportsItem() {
        return imports;
    }

    public void setImportsItem(ArrayList<String> imports) {
        this.imports = imports;
    }
    
    public void addImport(String s) {
        imports.add("import " + s +";");
    }
    
    public String generateImports() {
        String y = new String();
        for(String s : imports) {
            y += s + "\n";
        }
        return y;
    }
    

    @Override
    public void start(int x, int y) {
//        orgSceneX = x;
//        orgSceneY = y;

        setLayoutX(x);
        setLayoutY(y);
//        DataManager data = (DataManager) app.getDataComponent();
//        data.setSelectedClass(this);
//        data.highlightItem(this);
        System.out.println(this.getPrefHeight() + "//" + this.getPrefWidth());
    }

    @Override
    public void drag(int x, int y) {
        relocate(x, y);
    }

    public void initHandler(DataManager data, Workspace workspace) {

        this.setOnMousePressed(e -> {

            orgSceneX = e.getSceneX();
            orgSceneY = e.getSceneY();

            x = getLayoutX();
            y = getLayoutY();

//            orgTranslateY = ((Item)(e.getSource())).getTranslateY();
//            orgTranslateY = ((Item)(e.getSource())).getTranslateY();
//            DataManager data = (DataManager) app.getDataComponent();
//            Workspace workspace = (Workspace) app.getWorkspaceComponent();
//            ObservableList<Node> list = workspace.getDesignRenderer().getChildren();
//            for (Node n : list) {
//                if (!n.equals(data.getSelectedClass())) {
//                    data.unhighlightItem((Class) n);
//                } 
//            }
//               for(Node n : workspace.getDesignRenderer().getChildren()) {
//                   Item i = (Item) n;
//                   data.unhighlightItem(i);
//               }
//            data.highlightItem(this);
            data.setSelectedItem(this);
//            data.highlightItem(this);
//            workspace.setClassNameText(data.getSelectedClass().getName());
//            workspace.setPackageNameText(data.getSelectedClass().getPackageName());
            workspace.setClassNameText(this.getName());
            workspace.setPackageNameText(this.getPackageName());
//            data.getMemento().add(workspace.getDesignRenderer().getChildren(), this);
            if (this instanceof JClass) {
                JClass j = (JClass) this;
                ObservableList<Method> ol = FXCollections.observableArrayList(((JClass) this).getMethods());
//                workspace.getMethodTable().getItems().clear();
                workspace.getMethodTable().setItems(ol);

                ObservableList<Variable> olv = FXCollections.observableArrayList(((JClass) this).getVariables());
//                workspace.getMethodTable().getItems().clear();
                workspace.getVariableTable().setItems(olv);
            } else if (this instanceof Interface) {
                Interface j = (Interface) this;
                ObservableList<Method> ol = FXCollections.observableArrayList(((Interface) this).getMethods());
                workspace.getMethodTable().setItems(ol);
            }

        });

        initDrag(data, workspace);

//        this.setOnMouseDragged(e -> {
//            if (workspace.isSnap()) {
//                x += e.getSceneX() - orgSceneX;
//                y += e.getSceneY() - orgSceneY;
//
//                setLayoutX(x);
//                setLayoutY(y);
//
//                orgSceneX = e.getSceneX();
//                orgSceneY = e.getSceneY();
//                data.snap();
//            } else {
//                x += e.getSceneX() - orgSceneX;
//                y += e.getSceneY() - orgSceneY;
//
//                setLayoutX(x);
//                setLayoutY(y);
//
//                orgSceneX = e.getSceneX();
//                orgSceneY = e.getSceneY();
//            }
//
////            double offsetX = e.getSceneX() - orgSceneX;
////            double offsetY = e.getSceneY() - orgSceneY;
////            double newTranslateX = orgTranslateX + offsetX;
////            double newTranslateY = orgTranslateY + offsetY;
////            DataManager data = (DataManager) app.getDataComponent();
////            drag((int)e.getX(), (int)e.getY());
//            data.setSelectedItem(this);
////            ((Item)(e.getSource())).setTranslateX(newTranslateX);
////            ((Item)(e.getSource())).setTranslateY(newTranslateY);
////            data.highlightItem(this);
////            this.relocate(newTranslateX, newTranslateY);
//        });
    }

    public void initDrag(DataManager data, Workspace workspace) {
        this.setOnMouseDragged(e -> {
            if (workspace.isSnap()) {
                x += e.getSceneX() - orgSceneX;
                y += e.getSceneY() - orgSceneY;

                setLayoutX(x);
                setLayoutY(y);

                orgSceneX = e.getSceneX();
                orgSceneY = e.getSceneY();
                data.snap();
            } else {
                x += e.getSceneX() - orgSceneX;
                y += e.getSceneY() - orgSceneY;

                setLayoutX(x);
                setLayoutY(y);

                orgSceneX = e.getSceneX();
                orgSceneY = e.getSceneY();
            }
            this.setOnMouseReleased(t -> {
                data.getMemento().add(workspace.getDesignRenderer().getChildren(), this);
                System.out.println("*****Init drag******");
            });

//            double offsetX = e.getSceneX() - orgSceneX;
//            double offsetY = e.getSceneY() - orgSceneY;
//            double newTranslateX = orgTranslateX + offsetX;
//            double newTranslateY = orgTranslateY + offsetY;
//            DataManager data = (DataManager) app.getDataComponent();
//            drag((int)e.getX(), (int)e.getY());
            data.setSelectedItem(this);
//            ((Item)(e.getSource())).setTranslateX(newTranslateX);
//            ((Item)(e.getSource())).setTranslateY(newTranslateY);
//            data.highlightItem(this);
//            this.relocate(newTranslateX, newTranslateY);
        });
//        this.setOnMouseReleased(e -> {
//            data.getMemento().add(workspace.getDesignRenderer().getChildren());
//            System.out.println("*****Init drag******");
//        });

    }

//    public void initPressed()
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

//    public Item deepCopy() {
////        Item i = new Item();
//        if(this instanceof JClass) {
//             JClass j = this;
//            
//        }
//    }
}
