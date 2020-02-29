/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uet.toolCheckPolicyAbac.abac.model.description;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Ha Trung
 */
@XmlRootElement(name = "Rule")
@XmlAccessorType(XmlAccessType.FIELD)
public class Rule implements Serializable {
    private static final long serialVersionUID = 1L;

    @XmlElement(name = "Role")
    private String role;
    @XmlElement(name = "Action")
    private String action;
    @XmlElement(name = "Resource")
    private String resource;
    @XmlElement(name = "Condition")
    private Condition condition;

    public Rule() {
        super();
    }

    public Rule(String role, String action, String resource, Condition condition) {
        super();
        this.role = role;
        this.action = action;
        this.resource = resource;
        this.condition = condition;
    }



    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

}
