/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.test_bed;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import static javafx.application.Application.launch;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import jc.data.DataManager;
import jc.data.Item;
import jc.data.JClass;
import jc.data.Method;
import jc.data.Variable;
import jc.file.FileManager;
import saf.AppTemplate;

/**
 *
 * @author Steve
 */
public class TestSave {
    
//    DataManager dm = new DataManager();
//    FileManager fm = new FileManager();
    AppTemplate app;
    ArrayList<Item> items = new ArrayList();
    String filePath  = "./work/";
    
    public static void main(String[] args) throws IOException {
	
//        TestSave a = new TestSave();
//        a.create();
//        a.save();
//	launch(args);
        String filePath  = "./work/";
        FileManager fm = new FileManager();
        DataManager dm = new DataManager();
        dm.create();
        fm.saveData(dm, filePath);
        
    }
    
//    public void save() throws IOException {
//        fm.saveData(dm, filePath);
//    }
    
//    public void create() {
//        JClass threadExample = new JClass(app);
//        threadExample.setName("ThreadExample");
//        Variable START_TEXT = new Variable();
//        START_TEXT.setName("START_TEXT");
//        START_TEXT.setAccess("public");
//        START_TEXT.setType("String");
//        START_TEXT.setS(true);
//        Variable PAUSE_TEXT = new Variable();
//        PAUSE_TEXT.setName("PAUSE_TEXT");
//        PAUSE_TEXT.setAccess("public");
//        PAUSE_TEXT.setType("String");
//        PAUSE_TEXT.setS(true);
//        Variable window = new Variable();
//        window.setName("window");
//        window.setAccess("private");
//        window.setType("Stage");
//        Variable appPane = new Variable();
//        appPane.setName("appPane");
//        appPane.setAccess("private");
//        appPane.setType("BorderPane");
//        Variable topPane = new Variable();
//        topPane.setName("topPane");
//        topPane.setAccess("private");
//        topPane.setType("FlowPane");
//        Variable startButton = new Variable();
//        startButton.setName("startButton");
//        startButton.setAccess("private");
//        startButton.setType("Button");
//        Variable pauseButton = new Variable();
//        pauseButton.setName("pauseButton");
//        pauseButton.setAccess("private");
//        pauseButton.setType("Button");
//        Variable scrollPane = new Variable();
//        scrollPane.setName("scrollPane");
//        scrollPane.setAccess("private");
//        scrollPane.setType("ScrollPane");
//        Variable textArea = new Variable();
//        textArea.setName("textArea");
//        textArea.setAccess("private");
//        textArea.setType("TextArea");
//        Variable dateThread = new Variable();
//        dateThread.setName("dateThread");
//        dateThread.setAccess("private");
//        dateThread.setType("Thread");
//        Variable dateTask = new Variable();
//        dateTask.setName("dateTask");
//        dateTask.setAccess("private");
//        dateTask.setType("Task");
//        Variable counterThread = new Variable();
//        counterThread.setName("counterThread");
//        counterThread.setAccess("private");
//        counterThread.setType("Thread");
//        Variable counterTask = new Variable();      
//        counterTask.setName("counterTask");
//        counterTask.setAccess("private");
//        counterTask.setType("Task");
//        Variable work = new Variable();
//        work.setName("work");
//        work.setAccess("private");
//        work.setType("boolean");
//        Method start = new Method();
//        start.setName("start");
//        start.setAccess("public");
//        start.setType("void");
//        start.addArg("Stage primaryStage");
//        Method startWork = new Method();
//        startWork.setName("startWork");
//        startWork.setAccess("public");
//        startWork.setType("void");
//        Method pauseWork = new Method();
//        pauseWork.setName("startWork");
//        pauseWork.setAccess("public");
//        pauseWork.setType("void");
//        Method doWork = new Method();
//        doWork.setName("doWork");
//        doWork.setAccess("public");
//        doWork.setType("boolean");
//        Method appendText = new Method();
//        appendText.setName("appendText");
//        appendText.setAccess("public");
//        appendText.setType("void");
//        appendText.addArg("String textToAppend");
//        Method sleep = new Method();
//        sleep.setName("sleep");
//        sleep.setAccess("public");
//        sleep.setType("void");
//        sleep.addArg("int timeToSleep");
//        Method initLayout = new Method();
//        initLayout.setName("initLayout");
//        initLayout.setAccess("private");
//        initLayout.setType("void");
//        Method initHandlers = new Method();
//        initHandlers.setName("initHandlers");
//        initHandlers.setAccess("private");
//        initHandlers.setType("void");
//        Method initWindow = new Method();
//        initWindow.setName("initWindow");
//        initWindow.setAccess("private");
//        initWindow.setType("void");
//        initWindow.addArg("Stage initPrimaryStage");
//        Method initThreads = new Method();
//        initThreads.setName("initThreads");
//        initThreads.setAccess("private");
//        initThreads.setType("void");
//        Method main = new Method();
//        main.setName("main");
//        main.setAccess("public");
//        main.setS(true);
//        main.setType("void");
//        main.addArg("String[] args");
//        threadExample.addVariable(START_TEXT);
//        threadExample.addVariable(PAUSE_TEXT);
//        threadExample.addVariable(window);
//        threadExample.addVariable(appPane);
//        threadExample.addVariable(topPane);
//        threadExample.addVariable(startButton);
//        threadExample.addVariable(pauseButton);
//        threadExample.addVariable(scrollPane);
//        threadExample.addVariable(textArea);
//        threadExample.addVariable(dateThread);
//        threadExample.addVariable(dateTask);
//        threadExample.addVariable(counterTask);
//        threadExample.addVariable(work);
//        threadExample.addMethod(start);
//        threadExample.addMethod(startWork);
//        threadExample.addMethod(pauseWork);
//        threadExample.addMethod(doWork);
//        threadExample.addMethod(appendText);
//        threadExample.addMethod(sleep);
//        threadExample.addMethod(initLayout);
//        threadExample.addMethod(initHandlers);
//        threadExample.addMethod(initWindow);
//        threadExample.addMethod(initThreads);
//        threadExample.addMethod(main);
//        
//        
//        JClass counterTaskClass = new JClass(app);
//        counterTaskClass.setName("CounterTask");
//        Variable appp = new Variable();
//        appp.setName("app");
//        appp.setAccess("private");
//        appp.setType("ThreadExample");
//        Variable counter = new Variable();
//        counter.setName("counter");
//        counter.setType("int");
//        counter.setAccess("private");
//        Method counterTaskMethod = new Method();
//        counterTaskMethod.setName("CounterTask");
//        counterTaskMethod.setAccess("public");
//        counterTaskMethod.addArg("ThreadExample initApp");
//        Method call = new Method();
//        call.setAccess("protected");
//        call.setName("call");
//        call.setType("void");
//        counterTaskClass.addVariable(appp);
//        counterTaskClass.addVariable(counter);
//        counterTaskClass.addMethod(counterTaskMethod);
//        counterTaskClass.addMethod(call);
//        
//        
//        JClass dateTaskClass = new JClass(app);
//        Variable apppp = new Variable();
//        appp.setName("app");
//        appp.setType("ThreadExample");
//        appp.setAccess("private");
//        Variable now = new Variable();
//        appp.setName("now");
//        appp.setType("Date");
//        appp.setAccess("private");
//        Method dateTaskMethod = new Method();
//        dateTaskMethod.setType("DateTask");
//        dateTaskMethod.setAccess("public");
//        dateTaskMethod.addArg("ThreadExample initApp");
//        Method callMethod = new Method();
//        callMethod.setAccess("protected");
//        callMethod.setName("call");
//        callMethod.setType("void");
//        dateTaskClass.addVariable(apppp);
//        dateTaskClass.addVariable(now);
//        dateTaskClass.addMethod(dateTaskMethod);
//        dateTaskClass.addMethod(callMethod);
//        
//        
//        JClass pauseHandler = new JClass(app);
//        pauseHandler.setName("PauseHandler");
//        Variable appPauseHandler = new Variable();
//        appPauseHandler.setType("ThreadExample");
//        appPauseHandler.setAccess("public");
//        appPauseHandler.setName("app");
//        Method pauseHandlerMethod = new Method();
//        pauseHandlerMethod.setType("PauseHandler");
//        pauseHandlerMethod.setAccess("public");
//        pauseHandlerMethod.addArg("ThreadExample initApp");
//        Method handlerPause = new Method();
//        handlerPause.setName("handle");
//        handlerPause.setAccess("public");
//        handlerPause.setType("void");
//        handlerPause.addArg("Event event");
//        pauseHandler.addVariable(appPauseHandler);
//        pauseHandler.addMethod(pauseHandlerMethod);
//        pauseHandler.addMethod(handlerPause);
//        
//        
//        JClass startHandlerHandler = new JClass(app);
//        startHandlerHandler.setName("StartHandler");
//        Variable appStartHandler = new Variable();
//        appStartHandler.setName("app");
//        appStartHandler.setAccess("private");
//        appStartHandler.setType("ThreadExample");
//        Method startHandler = new Method();
//        startHandler.setType("StartHandler");
//        startHandler.addArg("ThreadExample initApp");
//        startHandler.setAccess("public");
//        Method startHandle = new Method();
//        startHandle.setType("handle");
//        startHandle.addArg("Event event");
//        startHandle.setAccess("public");
//        startHandler.setType("void");
//        startHandlerHandler.addVariable(appStartHandler);
//        startHandlerHandler.addMethod(startHandler);
//        startHandlerHandler.addMethod(startHandle);
//        
//        items.add(threadExample);
//        items.add(counterTaskClass);
//        items.add(dateTaskClass);
//        items.add(pauseHandler);
//        items.add(startHandlerHandler);
//        
//        dm.addClass(threadExample);
//        dm.addClass(counterTaskClass);
//        dm.addClass(dateTaskClass);
//        dm.addClass(pauseHandler);
//        dm.addClass(startHandlerHandler);
//        
//    }
}
