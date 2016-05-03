/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

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

    public VariableDialog(Stage stage) {
        initModality(Modality.WINDOW_MODAL);
        initOwner(stage);

        mainVB = new VBox();
        mainVB.setAlignment(Pos.CENTER);
        
        nameHB = new HBox();
        nameHB.setAlignment(Pos.CENTER);
        typeHB = new HBox();
        typeHB.setAlignment(Pos.CENTER);
        finalHB = new HBox();
        finalHB.setAlignment(Pos.CENTER);
        staticHB = new HBox();
        staticHB.setAlignment(Pos.CENTER);
        abstractHB = new HBox();
        abstractHB.setAlignment(Pos.CENTER);
        accessHB = new HBox();
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

        nameHB.getChildren().addAll(nameLbl, nameField);
        typeHB.getChildren().addAll(typeLbl, typeField);
        finalHB.getChildren().addAll(finalLbl, finalCheckBox);
        staticHB.getChildren().addAll(staticLbl, staticCheckBox);
        abstractHB.getChildren().addAll(abstractLbl, abstractCheckBox);
        accessHB.getChildren().addAll(accessLbl, accessComboBox);

        

        mainVB.getChildren().addAll(finalHB, staticHB, abstractHB, nameHB, accessHB);
        dialogScene = new Scene(mainVB);
        this.setScene(dialogScene);
    }

    public void showDialog() {
        this.showAndWait();
    }
}
