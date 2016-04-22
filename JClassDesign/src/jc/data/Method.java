/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.data;

import java.util.ArrayList;

/**
 *
 * @author Steve
 */
public class Method {

   

    
    public ArrayList<String> args = new ArrayList();
    private String access;
    private String accessUML;
    public String name;
    public String type;
    public final String FINAL_STRING = "static";
    public final String STATIC_STRING = "final";
    private boolean f;
    private boolean s;

    
     public ArrayList<String> getArgs() {
        return args;
    }

    public void setArgs(ArrayList<String> args) {
        this.args = args;
    }
    
    public void addArg(String arg) {
        if(args.size() >= 1) {
            args.add(", " + arg);
        }else {
            args.add(arg);
        }
        
    }
    
    public String argsString() {
        String s = new String();
        
        for(int i = 0; i < args.size(); i++) {
            if(args == null) {
                return "";
            }else {
                s += args.get(i);
            }
        }
        return s;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isF() {
        return f;
    }

    public void setF(boolean f) {
        this.f = f;
    }

    public boolean isS() {
        return s;
    }

    public void setS(boolean s) {
        this.s = s;
    }

    public String toCode() {
        if (isF()) {
            return access + " " + FINAL_STRING + " " + type + " " + name + "(" + argsString() + ")" + "  {" + "\n"
                    + "   throw new UnsupportedOperationException(\"Not supported yet.\"); //To change body of generated methods, choose Tools | Templates." + "\n"
                    + "}";
        }
        if (isS()) {
            return access + " " + STATIC_STRING + " " + type + " " + name + "(" + argsString() + ")" + "  {" + "\n"
                    + "   throw new UnsupportedOperationException(\"Not supported yet.\"); //To change body of generated methods, choose Tools | Templates." + "\n"
                    + "}";
        }
        if (isS() && isF()) {
            return access + " " + STATIC_STRING + " " + FINAL_STRING + " " + type + " " + name + "(" + argsString() + ")" + "  {" + "\n"
                    + "   throw new UnsupportedOperationException(\"Not supported yet.\"); //To change body of generated methods, choose Tools | Templates." + "\n"
                    + "}";
        }

        return access + " " + type + " " + name + "(" + argsString() + ")" + "  {" + "\n"
                + "   throw new UnsupportedOperationException(\"Not supported yet.\"); //To change body of generated methods, choose Tools | Templates." + "\n"
                + "}";
    }

    public String toUml() {
        return accessUML + " " + name + " : " + type;
    }

}
