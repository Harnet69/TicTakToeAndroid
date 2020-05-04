package com.example.tictaktoe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private int[] gameState;
    int[][] winningPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};

    private static final int colsNum = 3;
    private static final int rowsNum = 3;
    private int activePlayer = 1;


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

            }System.out.println(isWin());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addTagsToCells();
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

    // win condition
    public boolean isWin() {
        for (int[] winningPosition : winningPositions) {
            if (gameState[winningPosition[0]] == gameState[winningPosition[1]] && gameState[winningPosition[1]] == gameState[winningPosition[2]] && gameState[winningPosition[0]] != 0) {
                return true;
            }
        }
        return false;
    }
}