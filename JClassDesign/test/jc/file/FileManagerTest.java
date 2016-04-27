/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.file;

import java.util.ArrayList;
import javax.json.JsonArray;
import javax.json.JsonObject;
import jc.data.DataManager;
import jc.data.Interface;
import jc.data.Item;
import jc.data.Method;
import jc.data.Variable;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import saf.AppTemplate;
import saf.components.AppDataComponent;

/**
 *
 * @author Steve
 */
public class FileManagerTest {
    
    public FileManagerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of saveData method, of class FileManager.
     */
    @Test
    public void testSaveData() throws Exception {
        System.out.println("saveData");
        AppDataComponent data = null;
        String filePath = "";
        FileManager instance = new FileManager();
        instance.saveData(data, filePath);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of loadData method, of class FileManager.
     */
    @Test
    public void testLoadData_3args() throws Exception {
        System.out.println("loadData");
        AppDataComponent data = null;
        String filePath = "";
        AppTemplate initApp = null;
        FileManager instance = new FileManager();
        instance.loadData(data, filePath, initApp);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of loadData method, of class FileManager.
     */
    @Test
    public void testLoadData_String_AppTemplate() throws Exception {
        System.out.println("loadData");
        String filePath = "";
        AppTemplate initApp = null;
        FileManager instance = new FileManager();
        instance.loadData(filePath, initApp);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of loadData method, of class FileManager.
     */
    @Test
    public void testLoadData_String() throws Exception {
        System.out.println("loadData");
        String filePath = "";
        FileManager instance = new FileManager();
        instance.loadData(filePath);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDataAsDouble method, of class FileManager.
     */
    @Test
    public void testGetDataAsDouble() {
        System.out.println("getDataAsDouble");
        JsonObject json = null;
        String dataName = "";
        FileManager instance = new FileManager();
        double expResult = 0.0;
        double result = instance.getDataAsDouble(json, dataName);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of loadItem method, of class FileManager.
     */
    @Test
    public void testLoadItem() {
        System.out.println("loadItem");
        JsonObject jsonItem = null;
        FileManager instance = new FileManager();
        Item expResult = null;
        Item result = instance.loadItem(jsonItem);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of loadItemTest method, of class FileManager.
     */
    @Test
    public void testLoadItemTest() {
        System.out.println("loadItemTest");
        JsonObject jsonItem = null;
        FileManager instance = new FileManager();
        Item expResult = null;
        Item result = instance.loadItemTest(jsonItem);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of exportData method, of class FileManager.
     */
    @Test
    public void testExportData() throws Exception {
        System.out.println("exportData");
        AppDataComponent data = null;
        String filePath = "";
        FileManager instance = new FileManager();
        instance.exportData(data, filePath);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of importData method, of class FileManager.
     */
    @Test
    public void testImportData() throws Exception {
        System.out.println("importData");
        AppDataComponent data = null;
        String filePath = "";
        FileManager instance = new FileManager();
        instance.importData(data, filePath);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of buildFileStruct method, of class FileManager.
     */
    @Test
    public void testBuildFileStruct() {
        System.out.println("buildFileStruct");
        FileManager instance = new FileManager();
        instance.buildFileStruct();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of makeJOSNMethodArray method, of class FileManager.
     */
    @Test
    public void testMakeJOSNMethodArray() {
        System.out.println("makeJOSNMethodArray");
        ArrayList<Method> methods = null;
        FileManager instance = new FileManager();
        JsonArray expResult = null;
        JsonArray result = instance.makeJOSNMethodArray(methods);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of makeJOSNVariableArray method, of class FileManager.
     */
    @Test
    public void testMakeJOSNVariableArray() {
        System.out.println("makeJOSNVariableArray");
        ArrayList<Variable> variables = null;
        FileManager instance = new FileManager();
        JsonArray expResult = null;
        JsonArray result = instance.makeJOSNVariableArray(variables);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of makeJOSNArgsArray method, of class FileManager.
     */
    @Test
    public void testMakeJOSNArgsArray() {
        System.out.println("makeJOSNArgsArray");
        ArrayList<String> args = null;
        FileManager instance = new FileManager();
        JsonArray expResult = null;
        JsonArray result = instance.makeJOSNArgsArray(args);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of makeJOSNParentInterfaceArray method, of class FileManager.
     */
    @Test
    public void testMakeJOSNParentInterfaceArray() {
        System.out.println("makeJOSNParentInterfaceArray");
        ArrayList<Interface> parentInterfaces = null;
        FileManager instance = new FileManager();
        JsonArray expResult = null;
        JsonArray result = instance.makeJOSNParentInterfaceArray(parentInterfaces);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of makeJSONMethodObject method, of class FileManager.
     */
    @Test
    public void testMakeJSONMethodObject() {
        System.out.println("makeJSONMethodObject");
        Method m = null;
        FileManager instance = new FileManager();
        JsonObject expResult = null;
        JsonObject result = instance.makeJSONMethodObject(m);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of makeJSONArgsObject method, of class FileManager.
     */
    @Test
    public void testMakeJSONArgsObject() {
        System.out.println("makeJSONArgsObject");
        String argString = "";
        FileManager instance = new FileManager();
        JsonObject expResult = null;
        JsonObject result = instance.makeJSONArgsObject(argString);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of makeJSONVariableObject method, of class FileManager.
     */
    @Test
    public void testMakeJSONVariableObject() {
        System.out.println("makeJSONVariableObject");
        Variable v = null;
        FileManager instance = new FileManager();
        JsonObject expResult = null;
        JsonObject result = instance.makeJSONVariableObject(v);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of makeJSONParentInterfaceObject method, of class FileManager.
     */
    @Test
    public void testMakeJSONParentInterfaceObject() {
        System.out.println("makeJSONParentInterfaceObject");
        Interface i = null;
        FileManager instance = new FileManager();
        JsonObject expResult = null;
        JsonObject result = instance.makeJSONParentInterfaceObject(i);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of create method, of class FileManager.
     */
    @Test
    public void testCreate() {
        System.out.println("create");
        DataManager dm = null;
        FileManager instance = new FileManager();
        instance.create(dm);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
