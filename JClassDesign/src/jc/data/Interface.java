/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.data;

import java.util.ArrayList;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import jc.gui.Workspace;
import saf.AppTemplate;

/**
 *
 * @author Steve
 */
public class Interface extends Item {

    AppTemplate app;
    double orgSceneX, orgSceneY;
    double orgTranslateX, orgTranslateY;
    VBox nameVBox, methodVBox;
    String access;

//    Label name = new Label("DefaultClassName");
//    Label pkg = new Label("DefaultPackageName");

    ArrayList<Method> methods = new ArrayList();

    public Interface() {
        
//        super(initApp);
        orgSceneX = 0;
        orgSceneY = 0;

        nameVBox = new VBox();
        methodVBox = new VBox();

        nameVBox.getStyleClass().add("vbox");
        methodVBox.getStyleClass().add("vbox");
        methodVBox.setMinHeight(30);
        methodVBox.setMinWidth(30);
        
        nameLbl = new Label(name);

        nameVBox.getChildren().add(nameLbl);

        getChildren().addAll(nameVBox, methodVBox);

        this.getStyleClass().add("vbox");
//        this.setPrefSize(100, 100);

        Method m = new Method();
        m.setAccess("private");
        m.setType("String");
        m.setName("ggNoob");
        m.addArg("String b");
        m.addArg("String c");
        m.addArg("int g");
        
        Method m2 = new Method();
        m2.setAccess("private");
        m2.setType("String");
        m2.setName("gg");
        m2.addArg("String b");
        m2.addArg("String c");
        m2.addArg("int g");
        System.out.println("Test method to code: " + m.toCode());
        
//        this.setAccess("public");
//        this.addMethod(m);
//        this.addMethod(m2);
        System.out.println("Test class to code: " + this.toCode());

        this.setHeight(100);
        this.setWidth(100);
    }

//    @Override
//    public void start(int x, int y) {
//        orgSceneX = x;
//        orgSceneY = y;
//        setLayoutX(x);
//        setLayoutY(y);
//    }
//
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
//        double offsetX = x - orgSceneX;
//        double offsetY = y - orgSceneY;
//        double newTranslateX = orgTranslateX + offsetX;
//        double newTranslateY = orgTranslateY + offsetY;
//
//    }

//    public void initHandler() {
//        this.setOnMouseDragged(e -> {
////            drag((int)e.getX(), (int)e.getY());
//            DataManager data = (DataManager) app.getDataComponent();
////           data.setSelectedClass(this);
////           data.highlightItem(this);
//            this.relocate(e.getSceneX(), e.getSceneY());
//        });
//        this.setOnMousePressed(e -> {
//            DataManager data = (DataManager) app.getDataComponent();
//            Workspace workspace = (Workspace) app.getWorkspaceComponent();
//            data.setSelectedClass(this);
//            workspace.setClassNameText(data.getSelectedItem().getName());
//            workspace.setPackageNameText(data.getSelectedItem().getPackageName());
//        });
//    }

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
//    
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
    
     public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }
    
    public String toCode() {
        String s = new String();
        String methods = loadMethods();
        
        s += "package " + this.getPackageName() + ";" + "\n" + "\n" + "\n" + getImports() + "\n" + "\n" + "\n" + "\n" + "\n" + access + " interface" + " " + name + "  {" +  "\n" + "\n" + methods + "\n" + "}";
        
        return s;
    }
    
    private String loadMethods() {
        String s = new String();
        for(Method v : methods) {
            s += v.toCodeInterface() + "\n";
        }
        return s;
    } 
    
    private String getImports() {
        ArrayList<String> imported = new ArrayList();
        String s = new String();

//        for (Variable v : variables) {
//            if (v.getType().equals("int") || v.getType().equals("double") || v.getType().equals("char") || v.getType().equals("String") || v.getType().equals("float")
//                    || v.getType().equals("Integer") || v.getType().equals("Double") || v.getType().equals("Charachter") || v.getType().equals("Float")) {
//                continue;
//            }
//            if (this.name.equals(v.getType())) {
//                continue;
//            }
//
//            boolean isImported = false;
//            for (String i : imported) {
//                if (i.equals(v.getType())) {
//                    isImported = true;
//                    break;
//                }
//            }
//
//            if (isImported) {
//                continue;
//            }
//
//            imported.add(v.getType());
//
//        }

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
    
    public Interface deepCopy() {
        Interface i = new Interface();
        i.setAccess(this.getAccess());
        i.setName(this.getName());
        for(Method m : this.getMethods()) {
            Method me = m.deepCopy();
            i.addMethod(me);
        }
        i.setLayoutX(this.getLayoutX());
        i.setLayoutY(this.getLayoutY());
        return i;
    }
    
    
    
//    public String getName() {
//        return name.getText();
//    }
//    
//    public String getPackageName() {
//        return pkg.getText();
//    }
//
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
