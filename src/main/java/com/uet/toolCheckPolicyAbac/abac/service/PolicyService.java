/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uet.toolCheckPolicyAbac.abac.service;

import com.uet.toolCheckPolicyAbac.abac.model.ResultCheckPolicy;
import com.uet.toolCheckPolicyAbac.abac.model.description.Policy;
import com.uet.toolCheckPolicyAbac.abac.model.description.Rule;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ha Trung
 */
public class PolicyService {

    public ResultCheckPolicy checkAvailability(Policy policyDescription, Policy policyApp) {
        List<Rule> ruleDescriptionList = new ArrayList<>(policyDescription.getRule());
        List<Rule> ruleAppList = new ArrayList<>(policyApp.getRule());
        this.compareTowPolicy(ruleDescriptionList, ruleAppList);
        if (ruleDescriptionList.size() > 0) {
            String errorMessage = "The application system does not guarantee availability . The unauthorized rules:\n";
            for (Rule rule : ruleDescriptionList) {
                errorMessage = this.setError(rule, errorMessage);
            }
            return new ResultCheckPolicy(false, errorMessage);
        }
        return new ResultCheckPolicy(true, "Application system ensures availability .");
    }

    public ResultCheckPolicy checkConfidentialityIntegrity(Policy policyDescription, Policy policyApp, String keyCheck) {
        List<Rule> ruleDescriptionList = new ArrayList<>(policyDescription.getRule());
        List<Rule> ruleAppList = new ArrayList<>(policyApp.getRule());
        this.compareTowPolicy(ruleDescriptionList, ruleAppList);
        String error = this.checkErrorConfidentialityIntegrity(ruleAppList, keyCheck);
        if (error != "") {
            return new ResultCheckPolicy(false, error);
        }
        return new ResultCheckPolicy(true, "Application system ensures " + keyCheck + ".");
    }

    private String checkErrorConfidentialityIntegrity(List<Rule> ruleAppList, String keyCheck) {
        String errorMessage = "The application system does not guarantee " + keyCheck + ". The unauthorized rules:\n";
        boolean isConfidentiality = true;
        boolean isIntegrity = true;
        for (Rule rule : ruleAppList) {
            if (keyCheck.equals("confidentiality") && !rule.getAction().equalsIgnoreCase("Read")) {
                continue;
            }
            if (keyCheck.equals("integrity") && rule.getAction().equalsIgnoreCase("Read")) {
                continue;
            }
            if (rule.getAction().equalsIgnoreCase("Read")) {
                isConfidentiality = false;
            }
            if (!rule.getAction().equalsIgnoreCase("Read")) {
                isIntegrity = false;
            }
            errorMessage = this.setError(rule, errorMessage);
        }
        if (!isConfidentiality || !isIntegrity) {
            return errorMessage;
        }
        return "";
    }

    private String setError(Rule rule, String errorMessage) {
        errorMessage = errorMessage.concat("Role: " + rule.getRole() + ".\n");
        errorMessage = errorMessage.concat("Action: " + rule.getAction() + ".\n");
        errorMessage = errorMessage.concat("Resource: " + rule.getResource() + ".\n");
        errorMessage = errorMessage.concat("Condition: " + rule.getCondition().getRestriction().toString() + ".\n");
        errorMessage = errorMessage.concat("    ==============    \n");
        return errorMessage;
    }

    private void compareTowPolicy(List<Rule> ruleDescriptionList, List<Rule> ruleAppList) {
        List<Rule> ruleSame = new ArrayList<>();
        for (Rule ruleApp : ruleAppList) {
            for (Rule ruleDescription : ruleDescriptionList) {
                if (ruleApp.getRole().equalsIgnoreCase(ruleDescription.getRole())
                        && ruleApp.getAction().equalsIgnoreCase(ruleDescription.getAction())
                        && ruleApp.getResource().equalsIgnoreCase(ruleDescription.getResource())
                        && this.compareCondition(ruleApp, ruleDescription)) {
                    ruleSame.add(ruleDescription);
                    ruleSame.add(ruleApp);
                }
            }
        }
        ruleAppList.removeAll(ruleSame);
        ruleDescriptionList.removeAll(ruleSame);
    }

    private boolean compareCondition(Rule ruleApp, Rule ruleDescription) {
        List<String> restrictionAppList = ruleApp.getCondition().getRestriction();
        List<String> restrictionDescriptionList = ruleDescription.getCondition().getRestriction();
        List<String> restrictionSame = new ArrayList<>();
        if (restrictionAppList == null && restrictionDescriptionList == null) {
            return true;
        }
        if ((restrictionAppList == null && restrictionDescriptionList != null)
                || (restrictionAppList != null && restrictionDescriptionList == null)
                || (restrictionAppList.size() != restrictionDescriptionList.size())) {
            return false;
        }
        for (String restrictionApp : restrictionAppList) {
            for (String restrictionDescription : restrictionDescriptionList) {
                restrictionDescription = restrictionDescription.replaceAll("\\s+", "");
                if (restrictionApp.equalsIgnoreCase(restrictionDescription)) {
                    restrictionSame.add(restrictionApp);
                }
            }
        }
        if (restrictionSame.size() == restrictionAppList.size()) {
            return true;
        }
        return false;
    }
}
