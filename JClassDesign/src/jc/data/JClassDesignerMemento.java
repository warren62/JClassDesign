/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.data;

import java.util.Stack;
import jc.gui.Workspace;

/**
 *
 * @author Steve
 */
public class JClassDesignerMemento {
    
    private final Workspace workspace;
    Stack undoStack;
    
    public JClassDesignerMemento(Workspace workspace) {
        this.workspace = workspace;
    }
    
    public Workspace getSavedState() {
        return workspace;
    }
    
}
