package com.edu.erpapp;

import java.io.Serializable;

public class ClassModel implements Serializable {
    String Id, Text;

    public ClassModel() {
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }

    @Override
    public String toString() {
        return Text;
    }
}
