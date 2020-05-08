package com.medco.mymedicallog.entities;

public class SelectorItem {
    public String img;
    public String title;
    public boolean selected;

    public SelectorItem(String type) {
        title = type;
    }
}
