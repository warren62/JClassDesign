/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.data;

/**
 *
 * @author Steve
 */
public class Variable {

    
    
    private String access = "";
    private String accessUML;
    public String name = "";
    public String type = "";
    public final String FINAL_STRING = "static";
    public final String STATIC_STRING = "final";
    private boolean f;
    private boolean s;

    
    private boolean fUML;
    private boolean sUML;

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        String s = new String();
        if(access.equals("public")) {
            accessUML += "+";
        }else if(access.equals("private")) {
            accessUML  += "-";
        }else if(access.equals("protected")) {
            accessUML += "#";
        }else {
            System.out.println("incorrect access type");
        }
        this.access = access;
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
    
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String toCode() {
        String s = new String();
//        if (isF()) {
//            return access + " " + FINAL_STRING + " " + type + " " + name + ";";
//        }
//        if (isS()) {
//            return access + " " + STATIC_STRING + " " + type + " " + name + ";";
//        }
        if (isS() && isF()) {
            return access + " " + STATIC_STRING + " " + FINAL_STRING + " " + type + " " + name + ";";
        }else if (isS() && !isF()) {
            return access + " " + STATIC_STRING + " " + type + " " + name + ";";
        }else if (isF() && !isS()) {
            return access + " " + FINAL_STRING + " " + type + " " + name + ";";
        }else if(access == null) {
            return type + " " + name + ";";
        }

        return access + " " + type + " " + name + ";";
    }

    public String toUml() {
        return accessUML + " " + name + " : " + type;
    }
}
