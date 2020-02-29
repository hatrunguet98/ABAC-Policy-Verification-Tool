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

@XmlRootElement(name = "Policys")
@XmlAccessorType(XmlAccessType.FIELD)
public class Policy  implements Serializable {
    @XmlElement(name = "Rule")
    List<Rule> rule;

    public Policy() {
    }

    public Policy(List<Rule> rule) {
        this.rule = rule;
    }

    public List<Rule> getRule() {
        return rule;
    }

    public void setRule(List<Rule> rule) {
        this.rule = rule;
    }
}
