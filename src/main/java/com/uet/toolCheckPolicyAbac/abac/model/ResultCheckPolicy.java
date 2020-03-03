/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uet.toolCheckPolicyAbac.abac.model;

/**
 *
 * @author Ha Trung
 */
public class ResultCheckPolicy {
    private boolean isSuccess;
    private String message;

    public ResultCheckPolicy() {
    }

    public ResultCheckPolicy(boolean isSuccess, String message) {
        this.isSuccess = isSuccess;
        this.message = message;
    }

    public boolean isIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
