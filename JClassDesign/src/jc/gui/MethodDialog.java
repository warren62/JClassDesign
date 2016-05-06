/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.gui;

import java.lang.reflect.Field;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jc.data.DataManager;
import jc.data.Interface;
import jc.data.Item;
import jc.data.JClass;
import jc.data.Method;
import saf.AppTemplate;

/**
 *
 * @author Steve
 */
public class MethodDialog extends Stage {
    
    Scene dialogScene;
    CheckBox finalCheckBox;
    CheckBox staticCheckBox;
    CheckBox abstractCheckBox;
    ComboBox accessComboBox;
    TextField nameField;
    TextField typeField;
    Label nameLbl;
    Label typeLbl;
    Label finalLbl;
    Label staticLbl;
    Label abstractLbl;
    Label accessLbl;
    HBox nameHB;
    HBox typeHB;
    HBox finalHB;
    HBox staticHB;
    HBox abstractHB;
    HBox accessHB;
    HBox argsHB;
    
    Button plusBtn;
    Button minusBtn;
    
    VBox mainVB;
    VBox argsVB;
    
    AppTemplate app;
    
    public MethodDialog(Stage stage, AppTemplate initApp) {
        
        app = initApp;
        
        initModality(Modality.WINDOW_MODAL);
        initOwner(stage);
        
        mainVB = new VBox(10);
        mainVB.setAlignment(Pos.CENTER);
        argsVB = new VBox();
        argsVB.setAlignment(Pos.CENTER);
        
        nameHB = new HBox(10);
        nameHB.setAlignment(Pos.CENTER);
        typeHB = new HBox(10);
        typeHB.setAlignment(Pos.CENTER);
        finalHB = new HBox(10);
        finalHB.setAlignment(Pos.CENTER);
        staticHB = new HBox(10);
        staticHB.setAlignment(Pos.CENTER);
        abstractHB = new HBox(10);
        abstractHB.setAlignment(Pos.CENTER);
        accessHB = new HBox(10);
        accessHB.setAlignment(Pos.CENTER);
        argsHB = new HBox();
        argsHB.setAlignment(Pos.CENTER);
        
        plusBtn = new Button("Add Arg");
        minusBtn = new Button("Delete Arg");
        
        nameField = new TextField();
        typeField = new TextField();
        
        abstractLbl = new Label("Abstract");
        nameLbl = new Label("Name");
        staticLbl = new Label("Static");
        accessLbl = new Label("Access");
        typeLbl = new Label("Type");
        finalLbl = new Label("Final");
        
        finalCheckBox = new CheckBox();
        staticCheckBox = new CheckBox();
        abstractCheckBox = new CheckBox();
        
        accessComboBox = new ComboBox();
        accessComboBox.setValue("");
        accessComboBox.getItems().addAll("public", "private", "protected");
        
        nameHB.getChildren().addAll(nameLbl, nameField);
        typeHB.getChildren().addAll(typeLbl, typeField);
        finalHB.getChildren().addAll(finalLbl, finalCheckBox);
        staticHB.getChildren().addAll(staticLbl, staticCheckBox);
        abstractHB.getChildren().addAll(abstractLbl, abstractCheckBox);
        accessHB.getChildren().addAll(accessLbl, accessComboBox);
        argsHB.getChildren().addAll(plusBtn, minusBtn);
        
        mainVB.getChildren().addAll(finalHB, staticHB, abstractHB, nameHB, typeHB, accessHB, argsHB, argsVB);
        plusBtn.setOnAction(e -> {
            HBox hb = new HBox();
            hb.getChildren().add(new TextField("Argument"));
            hb.setAlignment(Pos.CENTER);
            argsVB.getChildren().add(hb);            
        });
        
        dialogScene = new Scene(mainVB, 300, 300);
        this.setScene(dialogScene);
    }
    
    public void showDialog() {
        this.showAndWait();
    }
    
    public void addData(Item i, TableView t) {
        boolean f = finalCheckBox.isSelected();
        boolean s = staticCheckBox.isSelected();
        boolean a = abstractCheckBox.isSelected();
        String type = typeField.getText();
        String name = nameField.getText();
        String access = accessComboBox.getValue().toString();
        
        Method m = new Method();
        m.setName(name);
        m.setType(type);
        m.setA(a);
        m.setF(f);
        m.setS(s);
        m.setAccess(access);
        
        DataManager data = (DataManager) app.getDataComponent();
        Workspace w = (Workspace) app.getWorkspaceComponent();
//        JClass jc = new JClass();
//        ArrayList<Method> methodList;
//        ArrayList<JClass> classList = new ArrayList();
//        for (Node n : w.getDesignRenderer().getChildren()) {
//            if (n instanceof JClass) {
//                jc = (JClass) n;
//                classList.add(jc);
//
//                for (int z = 0; z < classList.size(); z++) {
//
//                }
//                System.out.println("*****Print class name ***** " + jc.getName());
//                methodList = jc.getMethods();
//                System.out.println("*****Print method list***** " + methodList.toString());
//
////                addLines(methodList, jc, (JClass) i);
//            }
//        }

        if (i instanceof JClass) {
            ((JClass) i).addMethod(m);
//            t.setItems((ObservableList) ((JClass) i).getMethods());
            ObservableList<Method> ol = FXCollections.observableArrayList(((JClass) i).getMethods());
            t.setItems(ol);
        } else if (i instanceof Interface) {
            ((Interface) i).addMethod(m);
            ObservableList<Method> ol = FXCollections.observableArrayList(((Interface) i).getMethods());
            t.setItems(ol);
        }

//        Class c = this.getClass();
//        c.getField("name").getType().getPackage();
//        Field fi = new Field();
    }

//    public void addLines(ArrayList<Method> methodList, JClass jc, JClass j) {
//        DataManager data = (DataManager) app.getDataComponent();
//        for (Method me : methodList) {
//            System.out.println("*****for loop in add lines method in method dialog***** ");
//            System.out.println("*****Method get type and jc get name in add lines method of method dialog***** " + jc.getName() + "//" + me.getType());
//            if (me.getType().equals(jc.getName()) && !me.getType().equals("void")) {
//
//                data.buildLine(jc);
//                data.buildArrow(jc.layoutXProperty(), jc.layoutYProperty(), j);
//                System.out.println("*****Draw diamond in method dialog***** ");
//            }
//        }
//    }
    public void generate(Item i) {
        Workspace w = (Workspace) app.getWorkspaceComponent();
        DataManager data = (DataManager) app.getDataComponent();
        for (Node n : w.getDesignRenderer().getChildren()) {
            if (n instanceof JClass) {
                JClass jc = (JClass) n;
                if (i instanceof JClass) {
                    JClass selJC = (JClass) i;
                    ArrayList<Method> list = ((JClass) i).getMethods();
                    for (int z = 0; z < list.size(); z++) {
                        if (list.get(z).getType().equals(jc.getName())) {
                            data.buildLine(jc);
                            
                            data.buildFeatheredArrow(jc.layoutXProperty(), jc.layoutYProperty(), i);
                        }
                    }
                    
                }
                
            }
        }
        
    }
    
}
