package com.example.tictaktoe;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private int[] gameState;
    int[][] winningPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};
    private TextView gameStateDisplay;
    private static final int colsNum = 3;
    private static final int rowsNum = 3;
    private int activePlayer = 1;

    @SuppressLint("SetTextI18n")
    public void dropIn(View view) {
        ImageView counter = (ImageView) view;
        if (gameState[(int) counter.getTag()] == 0) {
            counter.setTranslationY(-1500);
            gameState[(int) counter.getTag()] = activePlayer;
            if (activePlayer == 1) {
                counter.setImageResource(R.drawable.yellow);
                activePlayer = 2;
            } else {
                counter.setImageResource(R.drawable.red);
                activePlayer = 1;
            }
            counter.animate().translationYBy(1500);
//            System.out.println(Arrays.toString(gameState));
            if (isWinCombination()) {
                gameStateDisplay.setText("Player WON!!!");
                System.out.println("Win");
            }
            if(!isEmptyCellExist()){
                gameStateDisplay.setText("It's a DRAW!!!");
                System.out.println("It's a draw!");
            }
        } else {
            System.out.println("Cell is occupied");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addTagsToCells();
        gameStateDisplay = findViewById(R.id.gameStateDisplay);

    }

    // initial adding tagg with cell number to ImageView
    public void addTagsToCells() {
        ViewGroup yourLayout = (ViewGroup) findViewById(R.id.gridLayout);
        gameState = new int[yourLayout.getChildCount()];
        for (int i = 0; i < yourLayout.getChildCount(); i++) {
            View subView = yourLayout.getChildAt(i);
            if (subView instanceof ImageView) {
                ImageView imageView = (ImageView) subView;
                imageView.setTag(i);
                gameState[i] = 0;
            }
        }
    }

    public boolean winCondition(){
        if(isWinCombination()){
            return true;
        }
        return false;
    }

    // win condition
    public boolean isWinCombination() {
        for (int[] winningPosition : winningPositions) {
            if (gameState[winningPosition[0]] == gameState[winningPosition[1]] && gameState[winningPosition[1]] == gameState[winningPosition[2]] && gameState[winningPosition[0]] != 0) {
                return true;
            }
        }
        return false;
    }

    // is empty cells exist
    public boolean isEmptyCellExist() {
        for (int value : gameState) {
            if (value == 0) {
                return true;
            }
        }
        return false;
    }
}