/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;
import jc.data.JClass;
import jc.data.DataManager;
import jc.data.Interface;
import jc.data.Item;
import jc.data.Method;
import jc.data.Variable;
import saf.AppTemplate;
import saf.components.AppDataComponent;
import saf.components.AppFileComponent;

/**
 *
 * @author Steve
 */
public class FileManager implements AppFileComponent {

    AppTemplate app;

    @Override
    public void saveData(AppDataComponent data, String filePath) throws IOException {

        DataManager dataManager = (DataManager) data;

        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        ArrayList<Item> items = dataManager.getItems();
        for (Item i : items) {
            if (i instanceof JClass) {
                JClass c = (JClass) i;
                String type = "Class";
                String name = c.getName();
                String packageName = c.getPackageName();
                double x = c.getLayoutX();
                double y = c.getLayoutY();
                ArrayList<Variable> variables = c.getVariables();
                ArrayList<Method> methods = c.getMethods();
                JsonArray methodJson = makeJOSNMethodArray(methods);
                JsonArray variableJson = makeJOSNVariableArray(variables);

                JsonObject classJson = Json.createObjectBuilder()
                        .add("type", type)
                        .add("name", name)
                        .add("package_name", packageName)
                        .add("x", x)
                        .add("y", y)
                        .add("methods", methodJson)
                        .add("variables", variableJson)
                        .build();
                arrayBuilder.add(classJson);
            } else if (i instanceof Interface) {
                Interface c = (Interface) i;
                String type = "Interface";
                String name = c.getName();
                String packageName = c.getPackageName();
                double x = c.getLayoutX();
                double y = c.getLayoutY();
                
                ArrayList<Method> methods = c.getMethods();
                JsonArray methodJson = makeJOSNMethodArray(methods);

                JsonObject interfaceJson = Json.createObjectBuilder()
                        .add("type", type)
                        .add("name", name)
                        .add("package_name", packageName)
                        .add("x", x)
                        .add("y", y)
                        .add("methods", methodJson)
                        .build();
                arrayBuilder.add(interfaceJson);
            }

        }
        JsonArray umJsonArray = arrayBuilder.build();

        JsonObject dataManagerJSO = Json.createObjectBuilder()
                .add("uml", umJsonArray)
                .build();

        Map<String, Object> properties = new HashMap<>(1);
        properties.put(JsonGenerator.PRETTY_PRINTING, true);
        JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
        StringWriter sw = new StringWriter();
        JsonWriter jsonWriter = writerFactory.createWriter(sw);
        jsonWriter.writeObject(dataManagerJSO);
        jsonWriter.close();

        OutputStream os = new FileOutputStream(filePath);
        JsonWriter jsonFileWriter = Json.createWriter(os);
        jsonFileWriter.writeObject(dataManagerJSO);
        String prettyPrinted = sw.toString();
        PrintWriter pw = new PrintWriter(filePath + ".json");
        pw.write(prettyPrinted);
        pw.close();

    }

    @Override
    public void loadData(AppDataComponent data, String filePath, AppTemplate initApp) throws IOException {
        app = initApp;
        DataManager dataManager = (DataManager) data;
        dataManager.reset();

        JsonObject json = loadJSONFile(filePath);

        JsonArray jsonItemArray = json.getJsonArray("uml");
        for (int i = 0; i < jsonItemArray.size(); i++) {
            JsonObject jsonItem = jsonItemArray.getJsonObject(i);
            Item item = loadItem(jsonItem);
            dataManager.addToWorkspace(item);
        }

    }

    public double getDataAsDouble(JsonObject json, String dataName) {
        JsonValue value = json.get(dataName);
        JsonNumber number = (JsonNumber) value;
        return number.bigDecimalValue().doubleValue();
    }

    public Item loadItem(JsonObject jsonItem) {

        String type = jsonItem.getString("type");
        Item i;

        if (type.equalsIgnoreCase("class")) {
//            i = new JClass(app);
            JClass c = new JClass(app);
            String name = jsonItem.getString("name");
            String packageName = jsonItem.getString("package_name");
            ArrayList<Method> methods = new ArrayList();
            ArrayList<Variable> variables = new ArrayList();
            ArrayList<String> args = new ArrayList();
            JsonArray methodJsonArray = jsonItem.getJsonArray("methods");
            JsonArray variableJsonArray = jsonItem.getJsonArray("variables");
            for(JsonValue j : methodJsonArray) {
                Method m = new Method();
                JsonObject o = (JsonObject)j;
                String n = o.getString("mname");
                String a = o.getString("access");
                String t = o.getString("mtype");
                System.out.println("load method type test  : " + o.getString("mtype"));
                boolean f = o.getBoolean("final");
                boolean s = o.getBoolean("static");
//                String f = o.getString("final");
//                String s = o.getString("static");
                m.setAccess(a);
                m.setName(n);
                m.setType(t);
                m.setF(f);
                m.setS(s);
                
                JsonArray argsJsonArray = o.getJsonArray("args");
                for(JsonValue jv : argsJsonArray) {
                    JsonObject oj = (JsonObject) jv;
                    m.addArg(oj.getString("arg"));
//                    args.add(oj.getString("arg"));
                }
                c.addMethod(m);
                
//                System.out.println(j.toString() + "       break");
                
            }
            for(JsonValue j : variableJsonArray) {
                Variable v = new Variable();
                JsonObject o = (JsonObject) j;
                String n = o.getString("vname");
                String a = o.getString("access");
                String t = o.getString("vtype");
                boolean f = o.getBoolean("final");
                boolean s = o.getBoolean("static");
                v.setAccess(a);
                v.setName(n);
                v.setType(t);
                v.setF(f);
                v.setS(s);
                c.addVariable(v);
            }
//            for(int z = 0; z < methodJsonArray.size(); z++) {
//                
//            }
            double x = getDataAsDouble(jsonItem, "x");
            double y = getDataAsDouble(jsonItem, "y");
            
            
            
            
            c.setName(name);
            c.setPackage(packageName);
            c.setLayoutX(x);
            c.setLayoutY(y);
            System.out.println("To code after load test: " + c.toCode());
            i = c;

        } else {
//            i = new Interface(app);
            Interface in = new Interface(app);
            String name = jsonItem.getString("name");
            String packageName = jsonItem.getString("package_name");
            
            JsonArray methodJsonArray = jsonItem.getJsonArray("methods");
            for(JsonValue j : methodJsonArray) {
                Method m = new Method();
                JsonObject o = (JsonObject)j;
                String n = o.getString("mname");
                String a = o.getString("access");
                String t = o.getString("mtype");
                System.out.println("load method type test  : " + o.getString("mtype"));
                boolean f = o.getBoolean("final");
                boolean s = o.getBoolean("static");
//                String f = o.getString("final");
//                String s = o.getString("static");
                m.setAccess(a);
                m.setName(n);
                m.setType(t);
                m.setF(f);
                m.setS(s);
                
                JsonArray argsJsonArray = o.getJsonArray("args");
                for(JsonValue jv : argsJsonArray) {
                    JsonObject oj = (JsonObject) jv;
                    m.addArg(oj.getString("arg"));
//                    args.add(oj.getString("arg"));
                }
                in.addMethod(m);
                
//                System.out.println(j.toString() + "       break");
                
            }
            
            double x = getDataAsDouble(jsonItem, "x");
            double y = getDataAsDouble(jsonItem, "y");
            in.setName(name);
            in.setPackage(packageName);
            in.setLayoutX(x);
            in.setLayoutY(y);
            System.out.println("To code after load test: " + in.toCode());
            i = in;
        }
        
        
        return i;
    }

    @Override
    public void exportData(AppDataComponent data, String filePath) throws IOException {
        DataManager dm = (DataManager) data;
        String[] pkgArray = null;
        File pkgFile = null;
        File classFile = null;

        boolean nested = false;

        ArrayList<Item> items = dm.getItems();
        ArrayList<String> packages = new ArrayList();

        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdir();
        }
        File fileTemp = new File(file.getAbsolutePath() + "\\src\\");
        if (!fileTemp.exists()) {
            fileTemp.mkdir();
        }
        for (Item i : items) {

            if (i.getPackageName().contains(".")) {
                pkgArray = i.getPackageName().split("\\.");
                nested = true;
            } else {
                packages.add(i.getPackageName());

            }

            for (String p : packages) {

                pkgFile = new File(fileTemp.getAbsolutePath() + "\\" + p + "\\");
                if (!pkgFile.exists()) {
                    pkgFile.mkdir();
                }

                if (nested) {
                    for (int g = 1; g < pkgArray.length; g++) {

                        pkgFile = new File(pkgFile.getAbsolutePath() + "\\" + pkgArray[g] + "\\");
                        if (!pkgFile.exists()) {
                            pkgFile.mkdir();
                        }

                    }
                    if (i instanceof JClass) {
                        classFile = new File(pkgFile.getAbsolutePath() + "\\" + i.getName() + ".java\\");
                        saveFile(((JClass) i).toCode(), classFile);
                    }else if(i instanceof Interface) {
                        classFile = new File(pkgFile.getAbsolutePath() + "\\" + i.getName() + ".java\\");
                        saveFile(((Interface) i).toCode(), classFile);
                    }
                } else if (i instanceof JClass) {
                    classFile = new File(pkgFile.getAbsolutePath() + "\\" + i.getName() + ".java\\");
                    saveFile(((JClass) i).toCode(), classFile);
                } else if (i instanceof Interface) {
                    classFile = new File(pkgFile.getAbsolutePath() + "\\" + i.getName() + ".java\\");
                    saveFile(((Interface) i).toCode(), classFile);
                }
            }

        }
        file.createNewFile();
//        FileOutputStream fos = new FileOutputStream(file);
    }

    @Override
    public void importData(AppDataComponent data, String filePath) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void buildFileStruct() {

    }

    private void saveFile(String content, File file) {

        FileWriter fileWriter = null;

        try {
            fileWriter = new FileWriter(file);
            fileWriter.write(content);
            fileWriter.close();
        } catch (IOException ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public JsonArray makeJOSNMethodArray(ArrayList<Method> methods) {

        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (Method m : methods) {
            JsonObject methodJson = makeJSONMethodObject(m);
            arrayBuilder.add(methodJson);
        }

        JsonArray methodJsonArray = arrayBuilder.build();

        return methodJsonArray;
    }
    
    public JsonArray makeJOSNVariableArray(ArrayList<Variable> variables) {

        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (Variable v : variables) {
            JsonObject variableJson = makeJSONVariableObject(v);
            arrayBuilder.add(variableJson);
        }

        JsonArray variableJsonArray = arrayBuilder.build();

        return variableJsonArray;
    }

    public JsonArray makeJOSNArgsArray(ArrayList<String> args) {

        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (String a : args) {
            JsonObject argJson = makeJSONArgsObject(a);
            arrayBuilder.add(argJson);
        }

        JsonArray methodJsonArray = arrayBuilder.build();

        return methodJsonArray;
    }

    public JsonObject makeJSONMethodObject(Method m) {
        String name = m.getName();
        String type = m.getType();
        String access = m.getAccess();
        boolean f = m.isF();
        boolean s = m.isS();
//        String f = new String();
//        String s = new String();

        ArrayList<String> args = m.getArgs();
        JsonArray argsJsonArray = makeJOSNArgsArray(args);

//        if (m.isF()) {
//            f += "final";
//        }
//        if (m.isS()) {
//            s += "final";
//        }


        JsonObject methodsJson = Json.createObjectBuilder()
                .add("mname", name)
                .add("mtype", type)
                .add("access", access)
                .add("final", f)
                .add("static", s)
                .add("args", argsJsonArray)
                .build();

        return methodsJson;
    }

    public JsonObject makeJSONArgsObject(String argString) {
        JsonObject methodsJson = Json.createObjectBuilder()
                .add("arg", argString)
                .build();

        return methodsJson;
    }

    public JsonObject makeJSONVariableObject(Variable v) {
       String name = v.getName();
       String type = v.getType();
       String access = v.getAccess();
//       String f = new String();
//       String s = new String();
       boolean f = v.isF();
       boolean s = v.isS();
       
//       if(v.isF()) {
//           f += "final";
//       }
//       
//       if(v.isS()) {
//           s += "static";
//       }

        JsonObject variableJson = Json.createObjectBuilder()
                .add("vname", name)
                .add("vtype", type)
                .add("access", access)
                .add("final", f)
                .add("static", s)
                
                .build();

        return variableJson;
    }

    private JsonObject loadJSONFile(String jsonFilePath) throws IOException {
        InputStream is = new FileInputStream(jsonFilePath);
        JsonReader jsonReader = Json.createReader(is);
        JsonObject json = jsonReader.readObject();
        jsonReader.close();
        is.close();
        return json;
    }
}
