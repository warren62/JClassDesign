/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.gui;

import java.util.ArrayList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jc.data.DataManager;
import jc.data.Item;
import jc.data.JClass;
import saf.AppTemplate;

/**
 *
 * @author Steve
 */
public class ParentDialog extends Stage {

    AppTemplate app;
    VBox mainVB;
    Scene dialogScene;
    ArrayList<Item> itemList = new ArrayList();

    public ParentDialog(Stage stage, AppTemplate initApp) {
        initModality(Modality.WINDOW_MODAL);
        initOwner(stage);

        app = initApp;

        mainVB = new VBox(5);
        mainVB.setAlignment(Pos.CENTER);

        Workspace w = (Workspace) app.getWorkspaceComponent();

        if (w.getDesignRenderer().getChildren().size() > 1) {
            for (Node n : w.getDesignRenderer().getChildren()) {
                Item i = (Item) n;
                itemList.add(i);
//                HBox hb = new HBox(10);
//                hb.setAlignment(Pos.CENTER);
//
//                hb.getChildren().addAll(new Label(i.getName()), new CheckBox());
                mainVB.getChildren().add(new CheckBox(i.getName()));
            }
        }

//        addData(d.getSelectedItem());
        dialogScene = new Scene(mainVB, 300, 300);
        this.setScene(dialogScene);

    }

    public void showDialog() {
        this.showAndWait();
    }

    public void addData(Item i) {
        for (Node n : mainVB.getChildren()) {
            CheckBox cb = (CheckBox) n;
            if (cb.isSelected()) {
                for (int z = 0; z < itemList.size(); z++) {
                    Item it = itemList.get(z);
                    if (it.getName() == cb.getText()) {
                        if (i instanceof JClass) {
                            DataManager d = (DataManager) app.getDataComponent();

                            ((JClass) i).addParent(it);
                            d.buildLine(it);
                            d.buildArrow(it.layoutXProperty(), it.layoutYProperty());

                        }
                    }
                }
            }
        }
    }

}
