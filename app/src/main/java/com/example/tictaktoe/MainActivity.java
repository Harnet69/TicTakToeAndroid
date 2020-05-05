package com.example.tictaktoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;

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
            if (isWinCombination()) {
                if (activePlayer == 2) {
                    gameStateDisplay.setText("Yellow Player WON");
                } else {
                    gameStateDisplay.setText("Red Player WON");
                }
//                colorEmptyCells();
            }
            if (!isEmptyCellExist()) {
                gameStateDisplay.setText("It's a DRAW!!!");
            }
        } else {
            counter.animate().rotationYBy(360).setDuration(1000);
            System.out.println("Cell is occupied");
        }
        System.out.println(Arrays.toString(gameState));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addTagsToCells();
        gameStateDisplay = findViewById(R.id.gameStateDisplay);

        if (savedInstanceState != null) {
            gameState = savedInstanceState.getIntArray("gameState");
            gameStateDisplay.setText(savedInstanceState.getString("gameStateDisplay"));
            addImageToImageView();
        }
    }

    // initial adding tag with cell number to ImageView
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

    // add images to ImageView after changing orientation
    public void addImageToImageView() {
        ViewGroup yourLayout = (ViewGroup) findViewById(R.id.gridLayout);
        for (int i = 0; i < yourLayout.getChildCount(); i++) {
            View subView = yourLayout.getChildAt(i);
            if (subView instanceof ImageView) {
                ImageView imageView = (ImageView) subView;
                if (gameState[i] == 1) {
                    imageView.setImageResource(R.drawable.yellow);
                } else if(gameState[i] == 2) {
                    imageView.setImageResource(R.drawable.red);
                }
            }
        }
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
            if (value == 0 || value == 404) {
                return true;
            }
        }
        return false;
    }

    // color empty cells
    private void colorEmptyCells() {
        ViewGroup yourLayout = (ViewGroup) findViewById(R.id.gridLayout);
        gameState = new int[yourLayout.getChildCount()];
        for (int i = 0; i < yourLayout.getChildCount(); i++) {
            View subView = yourLayout.getChildAt(i);
            if (subView instanceof ImageView) {
                ImageView imageView = (ImageView) subView;
//                System.out.println(imageView.getTag());
                for (int cell : gameState) {
                    if (cell == 0) {
                        gameState[(int) imageView.getTag()] = 404;
                    }
                }
                if (activePlayer == 1) {
                    imageView.setBackgroundColor(Color.parseColor("#ff0000"));
                } else {
                    imageView.setBackgroundColor(Color.parseColor("#FFFF00"));
                }

            }
        }
    }

    // save game condition to prevent its reset when orientation change
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putIntArray("gameState", gameState);
        outState.putString("gameStateDisplay", (String) gameStateDisplay.getText());
    }

//    @Override
//    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//
//        savedInstanceState.getIntArray("gameState");
//    }
}