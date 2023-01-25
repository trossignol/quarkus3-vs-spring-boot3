package com.example.aggregator;

public class LabelResult {
    private String key;

    private String label;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public LabelResult(String key, String label) {
        this.key = key;
        this.label = label;
    }

    public LabelResult() {

    }


    
}