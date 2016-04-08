/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.data;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.paint.Color;
import jc.file.FileManager;
import jc.gui.Workspace;
import saf.AppTemplate;
import saf.components.AppDataComponent;

/**
 *
 * @author Steve
 */
public class DataManager implements AppDataComponent {

    
     // THIS IS A SHARED REFERENCE TO THE APPLICATION
    AppTemplate app;
    
    JClassDesignerState state;
    
    ObservableList<Node> items;
    
    Class newItem;
    
    Class selectedItem;
    
    // USE THIS WHEN THE SHAPE IS SELECTED
    Effect highlightedEffect;
    
    public DataManager(AppTemplate initApp) throws Exception {
        
        app = initApp;
        
        DropShadow dropShadowEffect = new DropShadow();
	dropShadowEffect.setOffsetX(0.0f);
	dropShadowEffect.setOffsetY(0.0f);
	dropShadowEffect.setSpread(1.0);
	dropShadowEffect.setColor(Color.YELLOW);
	dropShadowEffect.setBlurType(BlurType.GAUSSIAN);
	dropShadowEffect.setRadius(15);
	highlightedEffect = dropShadowEffect;
        
        
    }
    
    public JClassDesignerState getState() {
	return state;
    }

    public void setState(JClassDesignerState initState) {
	state = initState;
    }
    
    public Class getNewItem() {
	return newItem;
    }

    public Class getSelectedItem() {
	return selectedItem;
    }

    public void setSelectedItem(Class initSelectedItem) {
	selectedItem = initSelectedItem;
    }


    public boolean isInState(JClassDesignerState testState) {
	return state == testState;
    }
    
    public void unhighlightItem(Class item) {
	selectedItem.setEffect(null);
    }
    
    public void highlightItem(Class item) {
	item.setEffect(highlightedEffect);
    }
    
    public void startNewItem(int x, int y) {
	Class newItem = new Class();
	newItem.start(x, y);
	newItem = newItem;
	initNewItem();
    }
    
    public void initNewItem() {
	// DESELECT THE SELECTED SHAPE IF THERE IS ONE
	if (selectedItem != null) {
	    unhighlightItem(selectedItem);
	    selectedItem = null;
	}

	// USE THE CURRENT SETTINGS FOR THIS NEW SHAPE
	Workspace workspace = (Workspace)app.getWorkspaceComponent();
	
	
	// ADD THE SHAPE TO THE CANVAS
	items.add(newItem);
	
	// GO INTO SHAPE SIZING MODE
	
    }
    
     public Class selectTopItem(int x, int y) {
	Class item = getTopItem(x, y);
	if (item == selectedItem)
	    return item;
	
	if (selectedItem != null) {
	    unhighlightItem(selectedItem);
	}
	if (item != null) {
	    highlightItem(item);
	    Workspace workspace = (Workspace)app.getWorkspaceComponent();
//	    workspace.loadSelectedShapeSettings(item);
	}
	selectedItem = item;
	if (item != null) {
	    ((Draggable)item).start(x, y);
	}
	return item;
    }

    public Class getTopItem(int x, int y) {
	for (int i = items.size() - 1; i >= 0; i--) {
	    Class item = (Class)items.get(i);
	    if (item.contains(x, y)) {
		return item;
	    }
	}
	return null;
    }

    public void addShape(Class itemToAdd) {
	items.add(itemToAdd);
    }

    public void removeItem(Class itemToRemove) {
	items.remove(itemToRemove);
    }
    
    @Override
    public void reset() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
