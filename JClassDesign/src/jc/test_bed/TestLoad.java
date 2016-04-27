/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.test_bed;

import java.io.IOException;
import java.util.ArrayList;
import jc.data.DataManager;
import jc.data.Interface;
import jc.data.Item;
import jc.data.JClass;
import jc.file.FileManager;

/**
 *
 * @author Steve
 */
public class TestLoad {

    public static void main(String[] args) throws IOException {

//        TestSave a = new TestSave();
//        a.create();
//        a.save();
//	launch(args);
        DataManager dm = new DataManager();
        FileManager fm = new FileManager();

        String filePath = "./work/DesignSaveTest";
//        fm.loadData(filePath, dm.getApp());
//        fm.loadData(dm.getApp().getDataComponent(), filePath, dm.getApp());
        fm.loadData(filePath);
        ArrayList<Item> items = dm.getItems();
        for(Item i : items) {
            if(i instanceof JClass) {
                
                System.out.println(((JClass) i).toCode());
            }else if(i instanceof Interface){
                System.out.println(((Interface) i).toCode());
            }
        }

    }

    public void doWork() {

    }

}
