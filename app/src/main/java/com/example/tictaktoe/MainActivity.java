package com.example.tictaktoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private int[] gameState;
    private TextView gameStateDisplay;
    private Button restartBtn;
    int[][] winningPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};
    private int activePlayer = 1;
    private boolean refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(refresh){
            super.onCreate(null);
        }else{
            super.onCreate(savedInstanceState);
        }
        setContentView(R.layout.activity_main);
        gameStateDisplay = findViewById(R.id.gameStateDisplay);
        restartBtn = findViewById(R.id.restartBtn);
        addTagsToCells();

        // get saved state
        if (savedInstanceState != null && !refresh) {
            gameState = savedInstanceState.getIntArray("gameState");
            gameStateDisplay.setText(savedInstanceState.getString("gameStateDisplay"));
            addImageToImageView();
            System.out.println("Refresh!!");
        }else{
//            super.onCreate(savedInstanceState);
            refresh = false;
        }
    }

    // initial adding a tag with cell number to ImageView
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
                colorEmptyCells();
            }
            else if (!isEmptyCellExist()) {
                gameStateDisplay.setText("It's a DRAW!!!");
            }

        } else {
            counter.animate().rotationYBy(360).setDuration(1000);
            System.out.println("Cell is occupied");
        }
//        System.out.println(Arrays.toString(gameState));
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
            if (value == 0) {
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
                        if(activePlayer == 1){
                            gameState[(int) imageView.getTag()] = 2;
                        }
                        else if(activePlayer == 2){
                            gameState[(int) imageView.getTag()] = 1;
                        }
                    }
                }
                if (activePlayer == 1) {
                    imageView.setBackgroundColor(Color.parseColor("#ff0000"));
                } else if (activePlayer == 2) {
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



    public void restart(View view){
        System.out.println("Restart!");
        for(int i = 0; i < gameState.length; i++){
            gameState[i] = 0;
        }
        ViewGroup yourLayout = (ViewGroup) findViewById(R.id.gridLayout);
        for (int i = 0; i < yourLayout.getChildCount(); i++) {
            View subView = yourLayout.getChildAt(i);
            if (subView instanceof ImageView) {
                ImageView imageView = (ImageView) subView;
                imageView.setImageDrawable(null);
            }
        }
        refresh = true;
        System.out.println(Arrays.toString(gameState));
//        ViewGroup vg = findViewById (R.id.allView);
//        vg.invalidate();
//        public void onCreate(Bundle savedInstanceState){
//            super.onCreate(null);
//        }
    }

//    @Override
//    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//
//        savedInstanceState.getIntArray("gameState");
//    }
}