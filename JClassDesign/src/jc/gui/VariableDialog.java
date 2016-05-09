/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.gui;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jc.data.DataManager;
import jc.data.Interface;
import jc.data.Item;
import jc.data.JClass;
import jc.data.Method;
import jc.data.Variable;
import saf.AppTemplate;

/**
 *
 * @author Steve
 */
public class VariableDialog extends Stage {

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

    VBox mainVB;
    
    AppTemplate app;

    public VariableDialog(Stage stage, AppTemplate initApp) {
        app = initApp;
        initModality(Modality.WINDOW_MODAL);
        initOwner(stage);

        mainVB = new VBox(10);
        mainVB.setAlignment(Pos.CENTER);
        
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
        accessComboBox.getItems().addAll("", "public", "private", "protected");

        nameHB.getChildren().addAll(nameLbl, nameField);
        typeHB.getChildren().addAll(typeLbl, typeField);
        finalHB.getChildren().addAll(finalLbl, finalCheckBox);
        staticHB.getChildren().addAll(staticLbl, staticCheckBox);
        abstractHB.getChildren().addAll(abstractLbl, abstractCheckBox);
        accessHB.getChildren().addAll(accessLbl, accessComboBox);

        

        mainVB.getChildren().addAll(finalHB, staticHB, abstractHB, nameHB, typeHB, accessHB);
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
        
        Variable m = new Variable();
        m.setName(name);
        m.setType(type);
        m.setA(a);
        m.setF(f);
        m.setS(s);
        m.setAccess(access);
        
        if(i instanceof JClass) {
            ((JClass) i).addVariable(m);
//            t.setItems((ObservableList) ((JClass) i).getMethods());
            ObservableList<Variable> ol = FXCollections.observableArrayList(((JClass) i).getVariables());
            t.setItems(ol);
        }
        
        
//        Class c = this.getClass();
//        c.getField("name").getType().getPackage();
//        Field fi = new Field();
        
    }
    
    public void generate(Item i) {
        Workspace w = (Workspace) app.getWorkspaceComponent();
        DataManager data = (DataManager) app.getDataComponent();
        for (Node n : w.getDesignRenderer().getChildren()) {
            if (n instanceof JClass) {
                JClass jc = (JClass) n;
                if (i instanceof JClass) {
                    JClass selJC = (JClass) i;
                    ArrayList<Variable> list = ((JClass) i).getVariables();
                    for (int z = 0; z < list.size(); z++) {
                        if (list.get(z).getType().equals(jc.getName())) {
                            data.buildLine(jc, data.getSelectedItem());
                            
                            data.buildDiamond(jc.layoutXProperty(), jc.layoutYProperty(), i);
                        }
                    }
                    
                }else if(i instanceof Interface) {
                    ArrayList<Method> list = ((Interface) i).getMethods();
                    for (int z = 0; z < list.size(); z++) {
                        if (list.get(z).getType().equals(jc.getName())) {
                            data.buildLine(jc, data.getSelectedItem());
                            
                            data.buildFeatheredArrow(jc.layoutXProperty(), jc.layoutYProperty(), i);
                        }
                    }
                }
                
            }else if(n instanceof Interface) {
                Interface in = (Interface) n;
                if(i instanceof Interface) {
                    
                    ArrayList<Method> list = ((Interface) i).getMethods();
                    for (int z = 0; z < list.size(); z++) {
                        if (list.get(z).getType().equals(in.getName())) {
                            data.buildLine(in, data.getSelectedItem());
                            
                            data.buildFeatheredArrow(in.layoutXProperty(), in.layoutYProperty(), i);
                        }
                    }
                }else if(i instanceof JClass) {
                    ArrayList<Variable> list = ((JClass) i).getVariables();
                    for (int z = 0; z < list.size(); z++) {
                        if (list.get(z).getType().equals(in.getName())) {
                            data.buildLine(in, data.getSelectedItem());
                            
                            data.buildDiamond(in.layoutXProperty(), in.layoutYProperty(), i);
                        }
                    }
                }
            }
        }
        
    }
}
