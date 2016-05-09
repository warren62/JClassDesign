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
import javafx.scene.shape.Circle;
import jc.data.DataManager.BoundLine;
import jc.gui.Workspace;

/**
 *
 * @author Steve
 */
public class JClassDesignerMemento {

    private Workspace workspace;
    BorderPane appPane;
    Stack<WorkspaceData> undoStack = new Stack();
    Stack<ArrayList> redoStack = new Stack();

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
        ArrayList popped = undoStackItems.pop();
        redoStack.add(popped);
        return undoStackItems.get(undoStackItems.size() - 1);
    }

    public ArrayList getRedoState() {
        ArrayList popped = redoStack.pop();
        undoStackItems.add(popped);
        System.out.println("*******ArrayList redo stack ******" + popped.toString());
        return popped;
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

    public Stack<ArrayList> getRedoStack() {
        return redoStack;
    }

    public Stack<ArrayList> getUndoStackItems() {
        return undoStackItems;
    }
    
    
    public void add(ObservableList<Node> items, Node node) {
        ArrayList<Node> list = new ArrayList();
        for(Node n : items) {
            if(node != n) {
                list.add(n);
            }
        }
        JClass j = null;
        Interface in;
        BoundLine l = null;
        JClass jCopy = null;
        Interface inCopy;
        BoundLine lCopy;
        Node n = node;
       // for (Node n : items) {

            if (n instanceof JClass) {
                j = (JClass) n;

                jCopy = j.deepCopy();
                list.add(jCopy);
            } else if (n instanceof Interface) {
                in = (Interface) n;

                inCopy = in.deepCopy();
                list.add(inCopy);
            } else if (n instanceof BoundLine) {
                l = (BoundLine) n;
//                lCopy = l.deepCopy();
                
//                if(l.getChildNode().getLayoutX() == j.getLayoutX()) {
//                    lCopy.setChildNode(jCopy);
//                }
//                if(l.getParentNode().getLayoutX() == j.getLayoutX()) {
//                    lCopy.setChildNode(jCopy);
//                }

//                for (int i = 0; i < j.getChildLines().size(); i++) {
//                    if (j.getChildLines().get(i) == l) {
//                        lCopy.setChildNode(jCopy);
//                    }
//                }
//                
//                for (int i = 0; i < j.getParentLines().size(); i++) {
//                    if (j.getParentLines().get(i) == l) {
//                        lCopy.setParentNode(jCopy);
//                    }
//                }

                System.out.println("****** BoundLine in add memento l.toString() *****" + l.toString());

//                System.out.println("****** BoundLine in add memento copy.toString() *****" + copy.toString());
//
//                System.out.println("****** BoundLine in add memento *****" + copy.getCircleList().size());
                list.add(l);
            } else if (n instanceof Circle) {
                Circle c = (Circle) n;

                list.add(c);
            }

            System.out.println("****** nodes in ArrayList in undo stack *****" + list.toString());
       //}
        undoStackItems.add(list);
    }

//    public void add(ObservableList<Item> items) {
//        ArrayList<Item> list = (ArrayList<Item>) items;
//    }
//    public DataManager getSavedStateData() {
//        return undoStackData.pop();
//    }
}
