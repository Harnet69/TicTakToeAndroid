package com.tictaktoe;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private View allView;
    private int[] gameState;
    private TextView gameStateDisplay;
    private ImageView codeCool;
    private Button restartBtn;
    int[][] winningPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};
    private int activePlayer = 1;
    private String colorOfWinner = "#A9E9F1";
    private int degreesPerSecond = 45;
    private MediaPlayer mediaPlayer = new MediaPlayer();
    private MediaPlayer clickSound;
    private MediaPlayer winSound;
    private MediaPlayer restartSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        allView = findViewById(R.id.allView);
        gameStateDisplay = findViewById(R.id.gameStateDisplay);
        restartBtn = findViewById(R.id.restartBtn);
        codeCool = findViewById(R.id.codecoolLogo);
        addTagsToCells();
        clickSound = MediaPlayer.create(this, R.raw.click);
        winSound = MediaPlayer.create(this, R.raw.win);
        restartSound = MediaPlayer.create(this, R.raw.restart);
        rotate(codeCool, degreesPerSecond);

        // get saved state
        if (savedInstanceState != null) {
            gameState = savedInstanceState.getIntArray("gameState");
            gameStateDisplay.setText(savedInstanceState.getString("gameStateDisplay"));
            colorOfWinner = savedInstanceState.getString("colorWinner");
            degreesPerSecond = savedInstanceState.getInt("degreesPerSecond");
            allView.setBackgroundColor(Color.parseColor(colorOfWinner));
            rotate(codeCool, degreesPerSecond);
            addImageToImageView();
            if(!gameStateDisplay.getText().equals("")){
                restartBtn.setVisibility(View.VISIBLE);
            }

        }
    }

    // initial adding a tag with cell number to ImageView
    public void addTagsToCells() {
        GridLayout yourLayout = findViewById(R.id.gridLayout);
        Log.i("Num col", String.valueOf(yourLayout.getColumnCount()));
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
            clickSound.start();
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
//                spinChips(); // TODO now, it works only once
                degreesPerSecond = 360;
                rotate(codeCool, degreesPerSecond);
                colorEmptyCells();
                restartBtn.setVisibility(View.VISIBLE);

            }
            else if (!isEmptyCellExist()) {
                gameStateDisplay.setText("It's a DRAW!!!");
                restartBtn.setVisibility(View.VISIBLE);
            }

        } else {
            counter.animate().rotationYBy(360).setDuration(1000);
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
                winSound.start();
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
        for (int i = 0; i < yourLayout.getChildCount(); i++) {
            View subView = yourLayout.getChildAt(i);
            if (subView instanceof ImageView) {
                ImageView imageView = (ImageView) subView;

                // colors all cells to a winner's color
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
                    colorOfWinner = "#ff0000";
                } else if (activePlayer == 2) {
                    colorOfWinner = "#FFFF00";
                }
                allView.setBackgroundColor(Color.parseColor(colorOfWinner));
            }
        }
    }

    // save game condition to prevent its reset when orientation change
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putIntArray("gameState", gameState);
        outState.putString("gameStateDisplay", (String) gameStateDisplay.getText());
        outState.putString("colorWinner", colorOfWinner);
        outState.putInt("degreesPerSecond", degreesPerSecond);
    }

    // restart game
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void restart(View view){
        ViewGroup gameField = (ViewGroup) findViewById(R.id.gridLayout);
        for(int i = 0; i < gameState.length; i++){
            gameState[i] = 0;
        }
        clearCells(gameField);
        restartBtn.setVisibility(View.INVISIBLE);
        gameStateDisplay.setText("");
        restartSound.start();
        degreesPerSecond = 45;
        rotate(codeCool, degreesPerSecond);
        colorOfWinner = "#A9E9F1";
        allView.setBackgroundColor(Color.parseColor(colorOfWinner));

    }


    // clear all cells fom images
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void clearCells(ViewGroup yourLayout){
        for (int i = 0; i < yourLayout.getChildCount(); i++) {
            View subView = yourLayout.getChildAt(i);
            if (subView instanceof ImageView) {
                ImageView imageView = (ImageView) subView;
                imageView.setImageDrawable(null);
                imageView.setBackground(null);
            }
        }
    }

    private void spinChips(){
        GridLayout yourLayout = findViewById(R.id.gridLayout);
        gameState = new int[yourLayout.getChildCount()];
        for (int i = 0; i < yourLayout.getChildCount(); i++) {
            View subView = yourLayout.getChildAt(i);
            if (subView instanceof ImageView) {
                ImageView imageView = (ImageView) subView;
                imageView.animate().rotation(imageView.getRotation()+720).setDuration(1000).setInterpolator(new LinearInterpolator());
            }
        }
    }

    public void rotate(View view, int degreesPerSecond){
        int loops = 1000000;
        view.animate().rotationBy(degreesPerSecond * loops).setDuration(loops * 1000)
                .setInterpolator(new LinearInterpolator());
    }


    private void clearAnimation(){
        GridLayout yourLayout = findViewById(R.id.gridLayout);
        gameState = new int[yourLayout.getChildCount()];
        for (int i = 0; i < yourLayout.getChildCount(); i++) {
            View subView = yourLayout.getChildAt(i);
            if (subView instanceof ImageView) {
                ImageView imageView = (ImageView) subView;
                imageView.animate().rotation(0);
            }
        }
    }

//    @Override
//    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//
//        savedInstanceState.getIntArray("gameState");
//    }
}