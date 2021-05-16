package com.example.bigtext.model;

public class Convo {
    private int id;
    private String convoText;
    private int convoSize = 12;

    public Convo(String convoText, int convoSize){
        this.convoText = convoText;
        this.convoSize = convoSize;
    }

    public Convo(int id, String convoText, int convoSize) {
        this.id = id;
        this.convoText = convoText;
        this.convoSize = convoSize;
    }

    public Convo() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getConvoText() {
        return convoText;
    }

    public void setConvoText(String convoText) {
        this.convoText = convoText;
    }

    public int getConvoSize() {
        return convoSize;
    }

    public void setConvoSize(int convoSize) {
        this.convoSize = convoSize;
    }
}
