/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uet.toolCheckPolicyAbac.abac.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uet.toolCheckPolicyAbac.abac.model.PolicyRuler;
import com.uet.toolCheckPolicyAbac.abac.model.description.Condition;
import com.uet.toolCheckPolicyAbac.abac.model.description.Policy;
import com.uet.toolCheckPolicyAbac.abac.model.description.Rule;

import javax.swing.*;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Ha Trung
 */
public class SourceCodeService {

    private static final Pattern getTargetElement = Pattern.compile("[^&]+");
    private static final Pattern getValueTargetElement = Pattern.compile("[^==&\"&']+");
    private static final Pattern getvalueActionAndResoucre = Pattern.compile("[^_]+");
    private static final String ROLE_KEY = "subject.roles[0].name";
    private static final String ACTION_KEY = "action";

//    public String getPolicyFile(File sourceCode) throws IOException, Exception {
//        System.out.println("policyFiles");
//        File[] policyFiles = sourceCode.listFiles(new FilenameFilter() {
//            public boolean accept(File dir, String name) {
//                return name.startsWith("pom") && name.endsWith("xml");
//            }
//        });
//        File[] allpolicyFiles = sourceCode.listFiles();
//        if (policyFiles.length > 0) {
//            FileService fileService = new FileService();
//            System.out.println(policyFiles.length);
//            return fileService.getContent(policyFiles[0]);
//        }
//        return null;
//    }

    public List<PolicyRuler> getPolicyRulers(String policyJson) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            List<PolicyRuler> policyRulerList = mapper.readValue(policyJson, new TypeReference<List<PolicyRuler>>() {
            });
            return policyRulerList;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "Can not set policy rule.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    public Policy convertRuleAppToPolicy(String policyJson) {
        List<Rule> ruleList = new ArrayList<>();
        policyJson = policyJson.replaceAll("\\s+", "");
        policyJson = policyJson.replaceAll("&&", "&");
//        policyJson = policyJson.replaceAll(" ", "");
        List<PolicyRuler> policyRulers = this.getPolicyRulers(policyJson);
        for (PolicyRuler policyRuler : policyRulers) {
            Rule rule = new Rule();
            List<String> targetElementList = new ArrayList<>();
            this.setConditionRule(rule, policyRuler.getCondition());
            String target = policyRuler.getTarget();
            Matcher targetElement = getTargetElement.matcher(target);
            while (targetElement.find()) {
                targetElementList.add(targetElement.group());
            }
            for (String valueTarget : targetElementList) {
                Matcher targetValueElement = getValueTargetElement.matcher(valueTarget);
                List<String> group = new ArrayList<>();
                while (targetValueElement.find()) {
                    group.add(targetValueElement.group());
                }
                if (group.get(0).equals(ROLE_KEY)) {
                    rule.setRole(group.get(1));
                } else if (group.get(0).equals(ACTION_KEY)) {
                    this.setActionAndResourceRule(rule, group.get(1));
                }
            }
            ruleList.add(rule);
        }
        return new Policy(ruleList);
    }

    private void setConditionRule(Rule rule, String conditionApp) {
        List<String> restriction = new ArrayList<>();
        Matcher conditionElement = getTargetElement.matcher(conditionApp);
        while (conditionElement.find()) {
            restriction.add(conditionElement.group());
        }
        rule.setCondition(new Condition(restriction));
    }

    private void setActionAndResourceRule(Rule rule, String actionApp) {
        Matcher valueActionApp = getvalueActionAndResoucre.matcher(actionApp);
        valueActionApp.find();
        rule.setAction(valueActionApp.group());
        valueActionApp.find();
        rule.setResource(valueActionApp.group());
    }
}
