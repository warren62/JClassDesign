/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import saf.AppTemplate;

/**
 *
 * @author Steve
 */
public class ImportDialog extends Stage {
    
    AppTemplate app;
    HBox mainHB;
    TextField tf = new TextField();
    Label l = new Label("Enter package: ");
    Scene dialogScene;
    
    public ImportDialog(Stage stage, AppTemplate initApp) {
        app = initApp;
        mainHB = new HBox();
        mainHB.setAlignment(Pos.CENTER);
        
        
        mainHB.getChildren().addAll(l, tf);
        
        dialogScene = new Scene(mainHB, 300, 300);
        this.setScene(dialogScene);
        
    }
    
    public void showDialog() {
        this.showAndWait();
    }
    
    public String getText() {
        return tf.getText();
    }
    
}
