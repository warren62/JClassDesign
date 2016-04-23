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
import java.math.BigDecimal;
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
        create(dataManager);

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
                ArrayList<Interface> parentInterfaces = c.getParentInterfaces();
                JsonArray parentInterfaceArray = makeJOSNParentInterfaceArray(parentInterfaces);
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
//                        .add("parentInterfaces", parentInterfaceArray)
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
            JsonArray parentInterfaceJsonArray = jsonItem.getJsonArray("parentInterfaces");
            for (JsonValue j : methodJsonArray) {
                Method m = new Method();
                JsonObject o = (JsonObject) j;
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
                for (JsonValue jv : argsJsonArray) {
                    JsonObject oj = (JsonObject) jv;
                    m.addArg(oj.getString("arg"));
//                    args.add(oj.getString("arg"));
                }
                c.addMethod(m);

//                System.out.println(j.toString() + "       break");
            }
            for (JsonValue j : variableJsonArray) {
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
            for (JsonValue j : methodJsonArray) {
                Method m = new Method();
                JsonObject o = (JsonObject) j;
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
                for (JsonValue jv : argsJsonArray) {
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
        create(dm);
        ArrayList<String> pkgArray = new ArrayList();
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
            nested = false;

            if (i.getPackageName().contains(".")) {
                String[] pkgSplit = i.getPackageName().split("\\.");
//                for (String s : pkgSplit) {
                String path = "";
                for (int m = 0; m < pkgSplit.length; m++) {
                    path += "\\" + pkgSplit[m];
                    pkgFile = new File(fileTemp.getAbsolutePath() + path + "\\");
                    if (!pkgFile.exists()) {
                        pkgFile.mkdir();
                    }
                }

            } else {
                pkgFile = new File(fileTemp.getAbsolutePath() + "\\" + i.getPackageName() + "\\");
                if (!pkgFile.exists()) {
                    pkgFile.mkdir();
                }
            }

            if (i instanceof JClass) {
                classFile = new File(pkgFile.getAbsolutePath() + "\\" + i.getName() + ".java\\");
                saveFile(((JClass) i).toCode(), classFile);
            } else if (i instanceof Interface) {
                classFile = new File(pkgFile.getAbsolutePath() + "\\" + i.getName() + ".java\\");
                saveFile(((Interface) i).toCode(), classFile);
            }
        }
//            for (String p : packages) {
//
//                pkgFile = new File(fileTemp.getAbsolutePath() + "\\" + p + "\\");
//                if (!pkgFile.exists()) {
//                    pkgFile.mkdir();
//                }
//                if (nested) {
//                    for (int g = 1; g < pkgArray.size(); g++) {
//                        pkgFile = new File(pkgFile.getAbsolutePath() + "\\" + pkgArray.get(g - 1) + "\\");
//                        if (!pkgFile.exists()) {
//                            pkgFile.mkdir();
//                        }
//                    }
//                    if (i instanceof JClass) {
//                        classFile = new File(pkgFile.getAbsolutePath() + "\\" + i.getName() + ".java\\");
//                        saveFile(((JClass) i).toCode(), classFile);
//                    } else if (i instanceof Interface) {
//                        classFile = new File(pkgFile.getAbsolutePath() + "\\" + i.getName() + ".java\\");
//                        saveFile(((Interface) i).toCode(), classFile);
//                    }
//
//                } else if (i instanceof JClass) {
//                    classFile = new File(pkgFile.getAbsolutePath() + "\\" + i.getName() + ".java\\");
//                    saveFile(((JClass) i).toCode(), classFile);
//                } else if (i instanceof Interface) {
//                    classFile = new File(pkgFile.getAbsolutePath() + "\\" + i.getName() + ".java\\");
//                    saveFile(((Interface) i).toCode(), classFile);
//                }
//            }
//        }
//        for (Item i : items) {
//            nested = false;
//
//            if (i.getPackageName().contains(".")) {
//                String[] pkgSplit = i.getPackageName().split("\\.");
//                for(String s : pkgSplit) {
//                    pkgArray.add(s);
//                }
//               
//                nested = true;
//            } else {
//                pkgArray.add(i.getPackageName());
//
//            }
//
//            for (String p : pkgArray) {
//
//                pkgFile = new File(fileTemp.getAbsolutePath() + "\\" + p + "\\");
//                if (!pkgFile.exists()) {
//                    pkgFile.mkdir();
//                }
//
//                if (nested) {
//                    for (int g = 1; g < pkgArray.size(); g++) {
//
//                        pkgFile = new File(pkgFile.getAbsolutePath() + "\\" + pkgArray.get(g - 1) + "\\");
//                        if (!pkgFile.exists()) {
//                            pkgFile.mkdir();
//                        }
//
//                    }
//                    if (i instanceof JClass) {
//                        classFile = new File(pkgFile.getAbsolutePath() + "\\" + i.getName() + ".java\\");
//                        saveFile(((JClass) i).toCode(), classFile);
//                    }else if(i instanceof Interface) {
//                        classFile = new File(pkgFile.getAbsolutePath() + "\\" + i.getName() + ".java\\");
//                        saveFile(((Interface) i).toCode(), classFile);
//                    }
//                } else if (i instanceof JClass) {
//                    classFile = new File(pkgFile.getAbsolutePath() + "\\" + i.getName() + ".java\\");
//                    saveFile(((JClass) i).toCode(), classFile);
//                } else if (i instanceof Interface) {
//                    classFile = new File(pkgFile.getAbsolutePath() + "\\" + i.getName() + ".java\\");
//                    saveFile(((Interface) i).toCode(), classFile);
//                }
//            }
//
//        }

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
            Logger.getLogger(FileManager.class
                    .getName()).log(Level.SEVERE, null, ex);
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

    public JsonArray makeJOSNParentInterfaceArray(ArrayList<Interface> parentInterfaces) {

        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (Interface i : parentInterfaces) {
            JsonObject parentInterfaceJson = makeJSONParentInterfaceObject(i);
            arrayBuilder.add(parentInterfaceJson);
        }

        JsonArray parentInterfaceJsonArray = arrayBuilder.build();

        return parentInterfaceJsonArray;
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

    public JsonObject makeJSONParentInterfaceObject(Interface i) {
        String name = i.getName();
        ArrayList<Method> methods = i.getMethods();
        JsonArray methodJsonArray = makeJOSNMethodArray(methods);

        JsonObject parentInterfaceJson = Json.createObjectBuilder()
                .add("name", name)
                .add("methods", methodJsonArray)
                .build();
        return parentInterfaceJson;
    }

    private JsonObject loadJSONFile(String jsonFilePath) throws IOException {
        InputStream is = new FileInputStream(jsonFilePath);
        JsonReader jsonReader = Json.createReader(is);
        JsonObject json = jsonReader.readObject();
        jsonReader.close();
        is.close();
        return json;
    }
    
    public void create(DataManager dm) {
        JClass threadExample = new JClass(app);
        threadExample.setName("ThreadExample");
        threadExample.setAccess("public");
        Variable START_TEXT = new Variable();
        START_TEXT.setName("START_TEXT");
        START_TEXT.setAccess("public");
        START_TEXT.setType("String");
        START_TEXT.setS(true);
        Variable PAUSE_TEXT = new Variable();
        PAUSE_TEXT.setName("PAUSE_TEXT");
        PAUSE_TEXT.setAccess("public");
        PAUSE_TEXT.setType("String");
        PAUSE_TEXT.setS(true);
        Variable window = new Variable();
        window.setName("window");
        window.setAccess("private");
        window.setType("Stage");
        Variable appPane = new Variable();
        appPane.setName("appPane");
        appPane.setAccess("private");
        appPane.setType("BorderPane");
        Variable topPane = new Variable();
        topPane.setName("topPane");
        topPane.setAccess("private");
        topPane.setType("FlowPane");
        Variable startButton = new Variable();
        startButton.setName("startButton");
        startButton.setAccess("private");
        startButton.setType("Button");
        Variable pauseButton = new Variable();
        pauseButton.setName("pauseButton");
        pauseButton.setAccess("private");
        pauseButton.setType("Button");
        Variable scrollPane = new Variable();
        scrollPane.setName("scrollPane");
        scrollPane.setAccess("private");
        scrollPane.setType("ScrollPane");
        Variable textArea = new Variable();
        textArea.setName("textArea");
        textArea.setAccess("private");
        textArea.setType("TextArea");
        Variable dateThread = new Variable();
        dateThread.setName("dateThread");
        dateThread.setAccess("private");
        dateThread.setType("Thread");
        Variable dateTask = new Variable();
        dateTask.setName("dateTask");
        dateTask.setAccess("private");
        dateTask.setType("Task");
        Variable counterThread = new Variable();
        counterThread.setName("counterThread");
        counterThread.setAccess("private");
        counterThread.setType("Thread");
        Variable counterTask = new Variable();      
        counterTask.setName("counterTask");
        counterTask.setAccess("private");
        counterTask.setType("Task");
        Variable work = new Variable();
        work.setName("work");
        work.setAccess("private");
        work.setType("boolean");
        Method start = new Method();
        start.setName("start");
        start.setAccess("public");
        start.setType("void");
        start.addArg("Stage primaryStage");
        Method startWork = new Method();
        startWork.setName("startWork");
        startWork.setAccess("public");
        startWork.setType("void");
        Method pauseWork = new Method();
        pauseWork.setName("startWork");
        pauseWork.setAccess("public");
        pauseWork.setType("void");
        Method doWork = new Method();
        doWork.setName("doWork");
        doWork.setAccess("public");
        doWork.setType("boolean");
        Method appendText = new Method();
        appendText.setName("appendText");
        appendText.setAccess("public");
        appendText.setType("void");
        appendText.addArg("String textToAppend");
        Method sleep = new Method();
        sleep.setName("sleep");
        sleep.setAccess("public");
        sleep.setType("void");
        sleep.addArg("int timeToSleep");
        Method initLayout = new Method();
        initLayout.setName("initLayout");
        initLayout.setAccess("private");
        initLayout.setType("void");
        Method initHandlers = new Method();
        initHandlers.setName("initHandlers");
        initHandlers.setAccess("private");
        initHandlers.setType("void");
        Method initWindow = new Method();
        initWindow.setName("initWindow");
        initWindow.setAccess("private");
        initWindow.setType("void");
        initWindow.addArg("Stage initPrimaryStage");
        Method initThreads = new Method();
        initThreads.setName("initThreads");
        initThreads.setAccess("private");
        initThreads.setType("void");
        Method main = new Method();
        main.setName("main");
        main.setAccess("public");
        main.setS(true);
        main.setType("void");
        main.addArg("String[] args");
        threadExample.addVariable(START_TEXT);
        threadExample.addVariable(PAUSE_TEXT);
        threadExample.addVariable(window);
        threadExample.addVariable(appPane);
        threadExample.addVariable(topPane);
        threadExample.addVariable(startButton);
        threadExample.addVariable(pauseButton);
        threadExample.addVariable(scrollPane);
        threadExample.addVariable(textArea);
        threadExample.addVariable(dateThread);
        threadExample.addVariable(dateTask);
        threadExample.addVariable(counterTask);
        threadExample.addVariable(work);
        threadExample.addMethod(start);
        threadExample.addMethod(startWork);
        threadExample.addMethod(pauseWork);
        threadExample.addMethod(doWork);
        threadExample.addMethod(appendText);
        threadExample.addMethod(sleep);
        threadExample.addMethod(initLayout);
        threadExample.addMethod(initHandlers);
        threadExample.addMethod(initWindow);
        threadExample.addMethod(initThreads);
        threadExample.addMethod(main);
        
        
        JClass counterTaskClass = new JClass(app);
        counterTaskClass.setName("CounterTask");
        counterTaskClass.setAccess("public");
        Variable appp = new Variable();
        appp.setName("app");
        appp.setAccess("private");
        appp.setType("ThreadExample");
        Variable counter = new Variable();
        counter.setName("counter");
        counter.setType("int");
        counter.setAccess("private");
        Method counterTaskMethod = new Method();
        counterTaskMethod.setName("");
        counterTaskMethod.setAccess("public");
        counterTaskMethod.addArg("ThreadExample initApp");
        counterTaskMethod.setType("CounterTask");
        Method call = new Method();
        call.setAccess("protected");
        call.setName("call");
        call.setType("void");
        counterTaskClass.addVariable(appp);
        counterTaskClass.addVariable(counter);
        counterTaskClass.addMethod(counterTaskMethod);
        counterTaskClass.addMethod(call);
        
        
        JClass dateTaskClass = new JClass(app);
        dateTaskClass.setName("DateTask");
        dateTaskClass.setAccess("public");
        Variable apppp = new Variable();
        appp.setName("app");
        appp.setType("ThreadExample");
        appp.setAccess("private");
        Variable now = new Variable();
        appp.setName("now");
        appp.setType("Date");
        appp.setAccess("private");
        Method dateTaskMethod = new Method();
        dateTaskMethod.setName("");
        dateTaskMethod.setType("DateTask");
        dateTaskMethod.setAccess("public");
        dateTaskMethod.addArg("ThreadExample initApp");
        Method callMethod = new Method();
        callMethod.setAccess("protected");
        callMethod.setName("call");
        callMethod.setType("void");
        dateTaskClass.addVariable(apppp);
        dateTaskClass.addVariable(now);
        dateTaskClass.addMethod(dateTaskMethod);
        dateTaskClass.addMethod(callMethod);
        
        
        JClass pauseHandler = new JClass(app);
        pauseHandler.setName("PauseHandler");
        pauseHandler.setAccess("public");
        Variable appPauseHandler = new Variable();
        appPauseHandler.setType("ThreadExample");
        appPauseHandler.setAccess("public");
        appPauseHandler.setName("app");
        Method pauseHandlerMethod = new Method();
        pauseHandlerMethod.setName("");
        pauseHandlerMethod.setType("PauseHandler");
        pauseHandlerMethod.setAccess("public");
        pauseHandlerMethod.addArg("ThreadExample initApp");
        Method handlerPause = new Method();
        handlerPause.setName("handle");
        handlerPause.setAccess("public");
        handlerPause.setType("void");
        handlerPause.addArg("Event event");
        pauseHandler.addVariable(appPauseHandler);
        pauseHandler.addMethod(pauseHandlerMethod);
        pauseHandler.addMethod(handlerPause);
        
        
        JClass startHandlerHandler = new JClass(app);
        startHandlerHandler.setName("StartHandler");
        startHandlerHandler.setAccess("public");
        Variable appStartHandler = new Variable();
        appStartHandler.setName("app");
        appStartHandler.setAccess("private");
        appStartHandler.setType("ThreadExample");
        Method startHandler = new Method();
        startHandler.setName("");
        startHandler.setType("StartHandler");
        startHandler.addArg("ThreadExample initApp");
        startHandler.setAccess("public");
        Method startHandle = new Method();
        startHandle.setName("handle");
        startHandle.addArg("Event event");
        startHandle.setAccess("public");
        startHandle.setType("void");
        startHandlerHandler.addVariable(appStartHandler);
        startHandlerHandler.addMethod(startHandler);
        startHandlerHandler.addMethod(startHandle);
        
//        items.add(threadExample);
//        items.add(counterTaskClass);
//        items.add(dateTaskClass);
//        items.add(pauseHandler);
//        items.add(startHandlerHandler);
        
        dm.addClass(threadExample);
        dm.addClass(counterTaskClass);
        dm.addClass(dateTaskClass);
        dm.addClass(pauseHandler);
        dm.addClass(startHandlerHandler);
        System.out.println();
    }
}
