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
import jc.data.DataManager.BoundLine;
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
    String access = "public";
//    String name = "DefaultClassName";
//    String packageName = "DefaultPackageName";

    boolean implementsInterface = false;
    boolean hasSuperclass = false;
    boolean a = false;

    VBox nameVBox, methodVBox, variableVBox;

    ArrayList<Method> methods = new ArrayList();
    ArrayList<Variable> variables = new ArrayList();
    ArrayList<Interface> parentInterfaces = new ArrayList();
    String parentClassName;

    public JClass() {
//        super(initApp);
//        app = initApp;
        orgSceneX = 0;
        orgSceneY = 0;

        nameLbl = new Label(name);
//        nameLbl.setText(name);
//        Label pkgLbl = new Label(pkg);

        nameVBox = new VBox();
        nameVBox.setMinHeight(this.getHeight()/3);
        nameVBox.setMinWidth(this.getWidth()/3);
        methodVBox = new VBox();
        methodVBox.setMinHeight(this.getHeight()/3);
        methodVBox.setMinWidth(this.getWidth()/3);
        variableVBox = new VBox();
        variableVBox.setMinHeight(this.getHeight()/3);
        variableVBox.setMinWidth(this.getWidth()/3);

        nameVBox.getChildren().add(nameLbl);

        nameVBox.getStyleClass().add("vbox");
        methodVBox.getStyleClass().add("vbox");
//        methodVBox.setMinHeight(30);
//        methodVBox.setMinWidth(30);
        variableVBox.getStyleClass().add("vbox");
//        variableVBox.setMinHeight(30);
//        variableVBox.setMinWidth(30);
//        initHandler();

        getChildren().addAll(nameVBox, variableVBox, methodVBox);

        this.getStyleClass().add("vbox");
//        this.setPrefSize(100, 100);

        this.setMinHeight(100);
        this.setMinWidth(100);
        Variable v = new Variable();
        v.setAccess("public");
        v.setName("s");
        v.setF(true);
        v.setS(true);
        v.setType("VBox");
        System.out.println("Test variable to code: " + v.toCode());
        System.out.println("Test variable to uml: " + v.toUml());

        Method m = new Method();
        m.setAccess("private");
        m.setType("String");
        m.setName("load");
        m.addArg("String b");
        m.addArg("String c");
        m.addArg("int g");

        Method m2 = new Method();
        m2.setAccess("private");
        m2.setType("String");
        m2.setName("l");
        m2.addArg("String b");
        m2.addArg("String c");
        m2.addArg("int g");
        System.out.println("Test method to code: " + m.toCode());
        System.out.println("Test method to uml: " + m.toUml());

        Method m3 = new Method();
        m3.setAccess("private");
        m3.setType("String");
        m3.setName("yoYo");
        m3.addArg("String b");
        m3.addArg("String c");
        m3.addArg("int g");
//        JClass jc = new JClass(app);
//        jc.addMethod(m);
//        jc.addVariable(v);

//        Interface i = new Interface(app);
//        i.setName("GG");
//        i.setAccess("public");
//        i.addMethod(m3);
//        JClass c = new JClass(app);
//        c.setName("Ha");
//        c.setAccess("public");
//        this.addParent(i);
//        
//        this.setAccess("public");
//        this.addMethod(m);
//        this.addMethod(m2);
//        this.addVariable(v);
        System.out.println("Test class to code: " + this.toCode());

//        this.setHeight(100);
//        this.setWidth(100);
    }

//    public JClass() {
//        
//    }
    public ArrayList<Interface> getParentInterfaces() {
        return parentInterfaces;
    }

    public VBox getMethodVBox() {
        return methodVBox;
    }

    public VBox getVariableVBox() {
        return variableVBox;
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
        methodVBox.getChildren().add(new Label(method.toUml()));
    }

    public ArrayList<Method> getMethods() {
        return methods;
    }

    public boolean isA() {
        for(Method m : this.getMethods()) {
            if(m.isA()) {
                this.a = true;
            }
        }
        
        for(Variable v : this.getVariables()) {
            if(v.isA()) {
                this.a = true;
            }
        }
        return a;
    }

    public void setA(boolean a) {
        this.a = a;
    }

    public void addVariable(Variable variable) {
        variables.add(variable);
        variableVBox.getChildren().add(new Label(variable.toUml()));
    }

    public ArrayList<Variable> getVariables() {
        return variables;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public boolean isImplementsInterface() {
        return implementsInterface;
    }

    public void setImplementsInterface(boolean implementsInterface) {
        this.implementsInterface = implementsInterface;
    }

    public boolean isHasSuperclass() {
        return hasSuperclass;
    }

    public void setHasSuperclass(boolean hasSuperclass) {
        this.hasSuperclass = hasSuperclass;
    }

//    public void setLabelName(String n) {
//       Label l = (Label) nameVBox.getChildren().get(0);
//       l.setText(name);
//       nameVBox.getChildren().clear();
//       nameVBox.getChildren().add(l);
//    }
    public String toCode() {
        String s = new String();
        String variables = loadVar();
        String methods = loadMethods();
//        s += getImports() + "\n" + "\n" + "\n" + "\n" + "\n" + access + " class" + " " + name + "  {" + "/n" + "/n" +
//                loadVar() + "\n" + "\n" + loadMethods() + "\n" + "}";
        if (isImplementsInterface() && isA()) {
            s += "package " + this.getPackageName() + ";" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + access + " abstract" + " class" + " " + name + " implements " + loadInterfaces() + "{" + "\n" + "\n"
                    + variables + "\n" + "\n" + methods + "\n" + "}";

        } else if (isImplementsInterface()) {
            s += "package " + this.getPackageName() + ";" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + access + " class" + " " + name + " implements " + loadInterfaces() + "{" + "\n" + "\n"
                    + variables + "\n" + "\n" + methods + "\n" + "}";

        } else if (this.isHasSuperclass() && isA()) {

            s += "package " + this.getPackageName() + ";" + "\n" + "\n" + "\n" + getImports() + "\n" + "\n" + "\n" + "\n" + "\n" + access + " abstract" + " class" + " " + name + " extends " + parentClassName + "{" + "\n" + "\n"
                    + variables + "\n" + "\n" + methods + "\n" + "}";

        } else if (this.isHasSuperclass()) {

            s += "package " + this.getPackageName() + ";" + "\n" + "\n" + "\n" + getImports() + "\n" + "\n" + "\n" + "\n" + "\n" + access + " class" + " " + name + " extends " + parentClassName + "{" + "\n" + "\n"
                    + variables + "\n" + "\n" + methods + "\n" + "}";

        } else if (this.isHasSuperclass() && this.isImplementsInterface() && isA()) {

            s += "package " + this.getPackageName() + ";" + "\n" + "\n" + "\n" + getImports() + "\n" + "\n" + "\n" + "\n" + "\n" + access + " abstract" + " class" + " " + name + " extends " + parentClassName + "implements " + loadInterfaces() + "{" + "\n" + "\n"
                    + variables + "\n" + "\n" + methods + "\n" + "}";

        } else if (this.isHasSuperclass() && this.isImplementsInterface()) {

            s += "package " + this.getPackageName() + ";" + "\n" + "\n" + "\n" + getImports() + "\n" + "\n" + "\n" + "\n" + "\n" + access + " class" + " " + name + " extends " + parentClassName + "implements " + loadInterfaces() + "{" + "\n" + "\n"
                    + variables + "\n" + "\n" + methods + "\n" + "}";

        } else if (isA()) {
            s += "package " + this.getPackageName() + ";" + "\n" + "\n" + "\n" + getImports() + "\n" + "\n" + "\n" + "\n" + "\n" + access + " abstract" + " class" + " " + name + "  {" + "\n" + "\n"
                    + variables + "\n" + "\n" + methods + "\n" + "}";
        } else {
            s += "package " + this.getPackageName() + ";" + "\n" + "\n" + "\n" + getImports() + "\n" + "\n" + "\n" + "\n" + "\n" + access + " class" + " " + name + "  {" + "\n" + "\n"
                    + variables + "\n" + "\n" + methods + "\n" + "}";
        }
        return s;
    }

    private String loadInterfaces() {
        String s = new String();
        if (parentInterfaces.size() == 1) {
            s += parentInterfaces.get(0);
        }

        for (Interface i : parentInterfaces) {
            if (parentInterfaces.size() > 1) {
                s += ", " + i.getName();
            }
        }

        return s;
    }

    private String loadVar() {
        String s = new String();
        for (Variable v : variables) {
            s += v.toCode() + "\n";
        }
        return s;
    }

    private String loadMethods() {
        String s = new String();
        for (Method v : methods) {
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

    public Item getParentItem() {
        return parent;
    }

    public void addParent(Item parent) {
        if (parent instanceof Interface) {
            this.setImplementsInterface(true);
            parentInterfaces.add((Interface) parent);
            for (Interface i : parentInterfaces) {
                ArrayList<Method> methods = i.getMethods();
                for (Method m : methods) {
                    this.addMethod(m);
                }
            }
        } else {
            this.setHasSuperclass(true);
            parentClassName = parent.getName();
            this.parent = (JClass) parent;
        }
//        this.parent = parent;
    }

    public JClass deepCopy() {
        JClass j = new JClass();
        j.setName(this.getName());
        j.setAccess(this.getAccess());
        j.setA(this.isA());
        j.setHasSuperclass(this.isHasSuperclass());
        j.setLayoutX(this.getLayoutX());
        j.setLayoutY(this.getLayoutY());
//        for (BoundLine l : this.getChildLines()) {
//            BoundLine copy = l.deepCopy();
//            copy.setChildNode(j);
//        }
//
//        for (BoundLine l : this.getParentLines()) {
//            BoundLine copy = l.deepCopy();
//            copy.setParentNode(j);
//        }

//        j.addParent(this.getParentItem().deepCopy());
//        ArrayList<Method> m = new ArrayList();
        for (Method me : this.getMethods()) {
            Method met = me.deepCopy();
            j.addMethod(met);
        }

        for (Variable me : this.getVariables()) {
            Variable met = me.deepCopy();
            j.addVariable(met);
        }

        return j;

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
