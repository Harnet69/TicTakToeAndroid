package com.tictaktoe.model;

public class Cell {
    private User occupier;

    public Cell() {
        this.occupier = null;
    }

    public User getOccupier() {
        return occupier;
    }

    public void setOccupier(User occupier) {
        this.occupier = occupier;
    }
}
