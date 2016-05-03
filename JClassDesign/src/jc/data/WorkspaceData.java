/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.data;

import java.util.ArrayList;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import jc.gui.Workspace;

/**
 *
 * @author Steve
 */
public class WorkspaceData {
    
    Workspace workspace;
    
    public WorkspaceData(Workspace w) {
        this.workspace = w;
    }
    
    public Pane getDesignRenderer() {
        return workspace.getDesignRenderer();
    }
    
    public ArrayList getItems() {
        ArrayList<Item> list = new ArrayList();
        for(Node n : workspace.getDesignRenderer().getChildren()) {
            Item i = (Item) n;
            list.add(i);
        }
        
        return list;
    }
    
}
