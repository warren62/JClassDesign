/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.data;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javax.lang.model.util.ElementFilter;
import jc.gui.Workspace;
import saf.AppTemplate;

/**
 *
 * @author Steve
 */
public class JClass extends Item {

    AppTemplate app;
    double orgSceneX, orgSceneY;
    double orgTranslateX, orgTranslateY;
    String access;

    VBox nameVBox, methodVBox, variableVBox;
//    Label name = new Label("DefaultClassName");
//    Label pkg = new Label("DefaultPackageName");
    ArrayList<Method> methods = new ArrayList();
    ArrayList<Variable> variables = new ArrayList();

    public JClass(AppTemplate initApp) {
        super(initApp);
//        app = initApp;
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
//        initHandler();

        getChildren().addAll(nameVBox, methodVBox, variableVBox);

        this.getStyleClass().add("vbox");
        this.setPrefSize(100, 100);
        
        Variable v = new Variable();
        v.setAccess("public");
        v.setName("s");
        v.setF(true);
        v.setS(true);
        v.setType("File");
        System.out.println("Test variable to code: " + v.toCode());
        System.out.println("Test variable to uml: " + v.toUml());
        
        Method m = new Method();
        m.setAccess("private");
        m.setType("void");
        m.setName("load");
        System.out.println("Test method to code: " + m.toCode());
        System.out.println("Test method to uml: " + m.toUml());
//        JClass jc = new JClass(app);
//        jc.addMethod(m);
//        jc.addVariable(v);
        this.setAccess("public");
        this.addMethod(m);
        this.addVariable(v);
        System.out.println("Test class to code: " + this.toCode());
        
        
//        this.setHeight(100);
//        this.setWidth(100);
    }

//    @Override
//    public void start(int x, int y) {
//        orgSceneX = x;
//        orgSceneY = y;
//        setLayoutX(x);
//        setLayoutY(y);
//        DataManager data = (DataManager) app.getDataComponent();
////        data.setSelectedClass(this);
////        data.highlightItem(this);
//        System.out.println(this.getPrefHeight() + "//" + this.getPrefWidth());
//    }
//    @Override
//    public void drag(int x, int y) {
////        double diffX = x - (getLayoutX() + (getWidth()/2));
////	double diffY = y - (getLayoutY() + (getHeight()/2));
////	double newX = getLayoutX() + diffX;
////	double newY = getLayoutY() + diffY;
////	xProperty().set(newX);
////	yProperty().set(newY);
////	startX = x;
////	startY = y;
////        double offsetX = x - orgSceneX;
////        double offsetY = y - orgSceneY;
////        double newTranslateX = orgTranslateX + offsetX;
////        double newTranslateY = orgTranslateY + offsetY;
////
////        this.setTranslateX(newTranslateX);
////        this.setTranslateX(newTranslateY);
////         this.setTranslateX(x);
////         this.setTranslateY(y);
//
////        initHandler();
//        relocate(x, y);
//
//    }
//    public void initHandler() {
//        this.setOnMouseDragged(e -> {
//            DataManager data = (DataManager) app.getDataComponent();
//            
////            drag((int)e.getX(), (int)e.getY());
//
//            data.setSelectedClass(this);
//            
////            data.highlightItem(this);
//            this.relocate(e.getSceneX() - this.getWidth() / 2, e.getSceneY() - this.getHeight());
//        });
//        this.setOnMousePressed(e -> {
//            DataManager data = (DataManager) app.getDataComponent();
//            Workspace workspace = (Workspace) app.getWorkspaceComponent();
////            ObservableList<Node> list = workspace.getDesignRenderer().getChildren();
////            for (Node n : list) {
////                if (!n.equals(data.getSelectedClass())) {
////                    data.unhighlightItem((Class) n);
////                } 
////            }
//            data.setSelectedClass(this);
////            data.highlightItem(this);
////            workspace.setClassNameText(data.getSelectedClass().getName());
////            workspace.setPackageNameText(data.getSelectedClass().getPackageName());
//            workspace.setClassNameText(this.getName());
//            workspace.setPackageNameText(this.getPackageName());
//        });
//
//    }
    public void clearHandlers() {
        this.setOnMouseDragged(e -> {
//          

        });

        this.setOnMousePressed(e -> {

        });
    }

//    @Override
//    public void size(int x, int y) {
//
////        double width = x - getLayoutX();
////	widthProperty().set(width);
////	double height = y - getLayoutY();
////	heightProperty().set(height);
//    }
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
//    public void setName(String name) {
//        this.name.setText(name);
//    }
//    public void setPackage(String name) {
//        this.pkg.setText(name);
//    }
    public void addMethod(Method method) {
        methods.add(method);
    }

    public ArrayList getMethods() {
        return methods;
    }

    public void addVariable(Variable variable) {
        variables.add(variable);
    }

    public ArrayList getVariables() {
        return variables;
    }
    

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public String toCode() {
        String s = new String();
        String variables = loadVar();
        String methods = loadMethods();
//        s += getImports() + "\n" + "\n" + "\n" + "\n" + "\n" + access + " class" + " " + name + "  {" + "/n" + "/n" +
//                loadVar() + "\n" + "\n" + loadMethods() + "\n" + "}";
        s += "package " + this.getPackageName() + ";" + "\n" + "\n" + "\n" + getImports() + "\n" + "\n" + "\n" + "\n" + "\n" + access + " class" + " " + name.getText() + "  {" + "\n" + "\n" +
                variables + "\n" + "\n" + methods + "\n" + "}";

        return s;
    }
    
    private String loadVar() {
        String s = new String();
        for(Variable v : variables) {
            s += v.toCode() + "\n";
        }
        return s;
    } 
    
    private String loadMethods() {
        String s = new String();
        for(Method v : methods) {
            s += v.toCode() + "\n";
        }
        return s;
    } 

    private String getImports() {
        ArrayList<String> imported = new ArrayList();
        String s = new String();

        for (Variable v : variables) {
            if (v.getType().equals("int") || v.getType().equals("double") || v.getType().equals("char") || v.getType().equals("String") || v.getType().equals("float")
                    || v.getType().equals("Integer") || v.getType().equals("Double") || v.getType().equals("Charachter") || v.getType().equals("Float")) {
                continue;
            }
            if (this.name.equals(v.getType())) {
                continue;
            }

            boolean isImported = false;
            for (String i : imported) {
                if (i.equals(v.getType())) {
                    isImported = true;
                    break;
                }
            }

            if (isImported) {
                continue;
            }

            imported.add(v.getType());

        }

        Package[] packages = Package.getPackages();
        for (String im : imported) {
            for (Package p : packages) {
                try {
                    Class.forName("" + p.getName() + im);
                    s += "import " + p.getName() + "." + im + ";" + "\n";
                    break;
                } catch (ClassNotFoundException ex) {
                    continue;
                }

            }
        }
        for (Method m : methods) {
            if (m.getType().equals("void")) {
                continue;
            }
            if (this.name.equals(m.getType())) {
                continue;
            }

            boolean isImported = false;
            for (String i : imported) {
                if (i.equals(m.getType())) {
                    isImported = true;
                    break;
                }
            }

            if (isImported) {
                continue;
            }

            imported.add(m.getType());

        }

//        Package[] packages = Package.getPackages();
        for (String im : imported) {
            for (Package p : packages) {
                try {
                    Class.forName("" + p.getName() + im);
                    s += "import " + p.getName() + "." + im + ";" + "\n";
                    break;
                } catch (ClassNotFoundException ex) {
                    continue;
                }

            }
        }
        
        return s;
    }

//    public String getName() {
//        return name.getText();
//    }
//    public String getPackageName() {
//        return pkg.getText();
//    }
//    @Override
//    public void setLocationAndSize(double initX, double initY, double initWidth, double initHeight) {
////        xProperty().set(initX);
////	yProperty().set(initY);
////	widthProperty().set(initWidth);
////	heightProperty().set(initHeight);
//    }
//
//    @Override
//    public String getShapeType() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public double getX() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public double getY() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
}
