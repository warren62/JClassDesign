/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.data;

import jc.file.FileManager;
import saf.AppTemplate;
import saf.components.AppDataComponent;

/**
 *
 * @author Steve
 */
public class DataManager implements AppDataComponent {

    
     // THIS IS A SHARED REFERENCE TO THE APPLICATION
    AppTemplate app;
    
    public DataManager(AppTemplate initApp) throws Exception {
        
        app = initApp;
        
        
        
    }
    @Override
    public void reset() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
