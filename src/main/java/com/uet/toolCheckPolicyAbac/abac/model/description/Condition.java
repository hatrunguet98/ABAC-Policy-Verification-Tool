/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uet.toolCheckPolicyAbac.abac.model.description;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Ha Trung
 */
@XmlRootElement(name = "Condition")
@XmlAccessorType(XmlAccessType.FIELD)
public class Condition implements Serializable {

    @XmlElement(name = "Restriction")
    private List<String> restriction;

    public Condition() {
    }

    public Condition(List<String> restriction) {
        this.restriction = restriction;
    }

    public List<String> getRestriction() {
        return restriction;
    }

    public void setRestriction(List<String> restriction) {
        this.restriction = restriction;
    }
}
