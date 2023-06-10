package com.logicea.cards.models;

public enum Status {
    TODO("To Do"),
    PROGRESS("Progress"),
    DONE("Done");
    private String status;

    Status(String status) {
        this.status = status;
    }
    public String getStatus() {
        return status;
    }
}
