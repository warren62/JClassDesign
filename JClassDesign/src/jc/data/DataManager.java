/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.data;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import static jc.data.JClassDesignerState.SELECTING_CLASS;
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
    
    ArrayList<Node> items;
    
    Item newItem;
    
    Item selectedItem;
    
    
    
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
        
        items = new ArrayList<Node>();
        
        
    }
    
    public JClassDesignerState getState() {
	return state;
    }

    public void setState(JClassDesignerState initState) {
	state = initState;
    }
    
    public Item getNewClass() {
	return newItem;
    }

    public Item getSelectedItem() {
	return selectedItem;
    }

    public void setSelectedItem(Item initSelectedClass) {
	selectedItem = initSelectedClass;
    }


    public boolean isInState(JClassDesignerState testState) {
	return state == testState;
    }
    
    public void unhighlightItem(Item item) {
	selectedItem.setEffect(null);
    }
    
    public void highlightItem(Item item) {
	item.setEffect(highlightedEffect);
    }
    
    public void startNewClass(int x, int y) {
        System.out.println("start new item");
	Class newItem1 = new Class(app);
	newItem1.start(x, y);
	newItem = newItem1;
	initNewItem();
        System.out.println("start item after");
    }
    
    public void startNewInterface(int x, int y) {
        System.out.println("start new item");
	Interface newItem1 = new Interface(app);
	newItem1.start(x, y);
	newItem = newItem1;
	initNewItem();
        System.out.println("start item after");
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
        System.out.println(items.toString() + "//" + items.size());
	items.add(newItem);
        System.out.println(newItem.toString() + "//" + items.size());
        workspace.getDesignRenderer().getChildren().add(newItem);
//        workspace.getWorkspace().getChildren().add(newItem);
        System.out.println("initnew item debug" + workspace.getWorkspace().getChildren().size());
	
	// GO INTO SHAPE SIZING MODE
	
    }
    
     public Item selectTopItem(int x, int y) {
        System.out.println("select top item before");
//	Class item = getTopItem(x, y);
        Item item = getSelectedItem();
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
//        setSelectedClass(item);
	if (item != null) {
	    ((Draggable)item).start(x, y);
	}
	return item;
    }

    public Item getTopItem(int x, int y) {
	for (int i = items.size() - 1; i >= 0; i--) {
	    Item item = (Item)items.get(i);
	    if (item.contains(x, y)) {
		return item;
	    }
	}
	return null;
    }
    
     public void selectSizedItem() {
	if (selectedItem != null)
	    unhighlightItem(selectedItem);
	selectedItem = newItem;
	highlightItem(selectedItem);
	newItem = null;
    }

    public void addClass(Class itemToAdd) {
	items.add(itemToAdd);
    }
    
//    public void setItems(ObservableList<Node> initItems, Pane pane) {
//	items = initItems;
//    }
    
    public void addInterface(Interface itemToAdd) {
	items.add(itemToAdd);
    }

    public void removeItem(Item itemToRemove) {
	items.remove(itemToRemove);
    }
    
    public ArrayList getItems() {
        return items;
    }
    
    @Override
    public void reset() {

        setState(SELECTING_CLASS);
        newItem = null;
	selectedItem = null;

	
	
	items.clear();
	((Workspace)app.getWorkspaceComponent()).getDesignRenderer().getChildren().clear();
        
    }
    
}
