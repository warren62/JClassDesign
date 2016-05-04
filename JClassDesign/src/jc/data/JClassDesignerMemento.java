/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.data;

import java.util.ArrayList;
import java.util.Stack;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import jc.gui.Workspace;

/**
 *
 * @author Steve
 */
public class JClassDesignerMemento {

    private Workspace workspace;
    BorderPane appPane;
    Stack<WorkspaceData> undoStack = new Stack();
    Stack<Workspace> redoStack = new Stack();

    Stack<DataManager> undoStackData = new Stack();

    Stack<ArrayList> undoStackItems = new Stack();

    public JClassDesignerMemento() {

    }

//    public JClassDesignerMemento(BorderPane appPane) {
//        this.appPane = appPane;
//        undoStack.add(appPane);
//        System.out.println("Stack is added to*******************");
//        System.out.println("*******Size of stack : " + undoStack.size());
//    }
    public ArrayList getSavedState() {
//        redoStack.add(undoStack.pop());
//        System.out.println("*******Size of stack getSavedState : " + undoStack.size());
//        Workspace testWorkspace = undoStack.pop();
//        Workspace testWorkspace2 = undoStack.pop();
//        Workspace testWorkspace3 = undoStack.pop();
//        System.out.println("*******Size of workspace getSavedState : " + testWorkspace.getDesignRenderer().getChildren().size());
//        System.out.println("*******Size of workspace2 getSavedState : " + testWorkspace2.getDesignRenderer().getChildren().size());
//        System.out.println("*******Size of workspace3 getSavedState : " + testWorkspace3.getDesignRenderer().getChildren().size());
//        return new WorkspaceData(undoStack.pop().workspace);
//        System.out.println("*******Size of stack : " + undoStack.size());
//        undoStackItems.pop();
        return undoStackItems.pop();
    }

//    public void add(WorkspaceData workspace) {
//        System.out.println("*******Size of workspace add : " + workspace.getDesignRenderer().getChildren().size());
//        undoStack.add(workspace);
////        Workspace testWorkspace = undoStack.pop();
////        System.out.println("*******Size of workspace add pop : " + testWorkspace.getDesignRenderer().getChildren());
//        System.out.println("*******Size of stack : " + undoStack.size());
//    }
//    public void add(DataManager dm) {
//        undoStackData.add(dm);
//    }
    public void add(ObservableList<Node> items) {
        ArrayList<Item> list = new ArrayList();
        for (Node n : items) {
          if(n instanceof JClass || n instanceof Interface){
            Item i = (Item) n;
            
            if(i instanceof JClass) {
                JClass j = (JClass) i;
                JClass copy = j.deepCopy();
                list.add(copy);
            } else {
                Interface in = (Interface) i;
                Interface copy = in.deepCopy();
                list.add(copy);
            }
          }
            
        }
        undoStackItems.add(list);
    }

//    public void add(ObservableList<Item> items) {
//        ArrayList<Item> list = (ArrayList<Item>) items;
//    }
//    public DataManager getSavedStateData() {
//        return undoStackData.pop();
//    }
}
