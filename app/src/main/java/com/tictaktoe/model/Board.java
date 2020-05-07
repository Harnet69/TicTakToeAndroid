package com.tictaktoe.model;

public class Board {
    private Cell[][] gameState;
    private int colsNum;
    private int rowsNum;

    public Board(int colsNum, int rowsNum) {
        this.colsNum = colsNum;
        this.rowsNum = rowsNum;
        gameState = new Cell[colsNum][rowsNum];
        createGameStageArray();
    }

    public Cell[][] getGameState() {
        return gameState;
    }

    public void setGameState(Cell[][] gameState) {
        this.gameState = gameState;
    }

    // build game stage array according to border size
    private void createGameStageArray(){
        for(int col = 0; col < colsNum; col++){
//            gameState[col] = [];
            for(int row = 0;row < rowsNum; row++){
                gameState[col][row] = null;
            }
        }
    }
}
