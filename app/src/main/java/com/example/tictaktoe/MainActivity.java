package com.example.tictaktoe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private int[] gameState;
    private static final int colsNum = 3;
    private static final int rowsNum = 3;
    private int activePlayer = 1;


    public void dropIn(View view){
        ImageView counter = (ImageView) view;
        counter.setTranslationY(-1500);
        gameState[(int) counter.getTag()] = activePlayer;
        if(activePlayer == 1) {
            counter.setImageResource(R.drawable.yellow);
            activePlayer = 2;
        }else{
            counter.setImageResource(R.drawable.red);
            activePlayer = 1;
        }
        counter.animate().translationYBy(1500);
        System.out.println(Arrays.toString(gameState));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addTagsToCells();
    }

    public void addTagsToCells(){
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
}
