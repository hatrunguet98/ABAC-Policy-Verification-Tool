/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uet.toolCheckPolicyAbac.abac.service;

import com.uet.toolCheckPolicyAbac.abac.model.description.Policy;

import java.io.StringReader;
import javax.swing.JOptionPane;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 * @author Ha Trung
 */
public class DescriptionService {

    public Policy convertDescriptionToPolicy(String descript) {
        JAXBContext jaxbContext;
        try {
            jaxbContext = JAXBContext.newInstance(Policy.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Policy policy = (Policy) jaxbUnmarshaller.unmarshal(new StringReader(descript));
            return policy;
        } catch (JAXBException e) {
            JOptionPane.showMessageDialog(null,
                    "Can't read policy from description.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }
}
