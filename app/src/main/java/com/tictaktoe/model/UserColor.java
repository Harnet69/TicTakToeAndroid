package com.tictaktoe.model;

public enum UserColor {
    YELLOW("Yellow player"),
    RED("Red player");

    private String kind;

    UserColor(String kind) {
        this.kind = kind;
    }

    @Override
    public String toString() {
        return kind;
    }
}
