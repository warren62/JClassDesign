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

    
    
    private String access;
    private String accessUML;
    public String type;
    private boolean f;
    private boolean s;
    private boolean fUML;
    private boolean sUML;

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
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
    
    public String toCode() {
        return access + " " + s + " " + f + " " + type;
    }
    
    public String toUml() {
        
    }
}
