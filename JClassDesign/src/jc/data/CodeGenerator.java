/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.data;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Steve
 */
public class CodeGenerator {
    
    ArrayList<Item> items = new ArrayList();
    
    public File buldPackages(File root) throws IOException {
        ArrayList<String> packages = new ArrayList();
        File f;
        for(Item i : items) {
            packages.add(i.getPackageName());
        }
        String[] dirList = root.list();
        for(String s : packages) {
            for(int i = 0; i < root.list().length; i++) {
                if(dirList[i].contains(s)) {
                    f = new File(root.getAbsoluteFile() + s);
                    f.createNewFile();
                }
            }
        }
       return f; 
    }
    
}
