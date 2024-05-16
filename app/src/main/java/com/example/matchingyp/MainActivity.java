package com.example.matchingyp;


import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Collections;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button shuffle;
    int[] cat_images;
    int clicked = 0;
    List<ImageButton> buttons;
    ImageButton firstCard, secondCard;
    int firstCardIndex, secondCardIndex;
    boolean isBusy = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        shuffle = findViewById(R.id.shuffleBtn);

        buttons = new ArrayList<>();
        loadCards((ArrayList<ImageButton>) buttons);

        cat_images = new int[]{R.drawable.cat1, R.drawable.cat2, R.drawable.cat3, R.drawable.cat4, R.drawable.cat5, R.drawable.cat6,
                R.drawable.cat1, R.drawable.cat2, R.drawable.cat3, R.drawable.cat4, R.drawable.cat5, R.drawable.cat6};

        for (int i = 0; i < buttons.size(); i++) {
            int finalI = i;
            buttons.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isBusy) return;
                    buttons.get(finalI).setBackgroundResource(cat_images[finalI]);

                    if (clicked == 0) {
                        firstCard = buttons.get(finalI);
                        firstCardIndex = finalI;
                        clicked++;
                    } else if (clicked == 1) {
                        secondCard = buttons.get(finalI);
                        secondCardIndex = finalI;
                        if (firstCardIndex != secondCardIndex && cat_images[firstCardIndex] == cat_images[secondCardIndex]) {
                            // Matched pair
                            firstCard.setEnabled(false);
                            secondCard.setEnabled(false);
                            clicked = 0;
                            checkIfGameFinished();
                        } else {
                            // Not a match
                            isBusy = true;
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    firstCard.setBackgroundResource(R.drawable.place);
                                    secondCard.setBackgroundResource(R.drawable.place);
                                    clicked = 0;
                                    isBusy = false;
                                }
                            }, 1000);
                        }
                    }
                }
            });
        }

        shuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int i;
                for (i = 0; i < buttons.size(); i++)
                    buttons.get(i).setBackgroundResource(cat_images[i]);
                Collections.shuffle(buttons, new Random());
                Toast.makeText(MainActivity.this, "Game reset", Toast.LENGTH_SHORT).show();

                shuffleGame();

            }
        });
    }

    private void loadCards(ArrayList<ImageButton> buttons) {
        buttons.add((ImageButton) findViewById(R.id.imageButton1));
        buttons.add((ImageButton) findViewById(R.id.imageButton2));
        buttons.add((ImageButton) findViewById(R.id.imageButton3));
        buttons.add((ImageButton) findViewById(R.id.imageButton4));
        buttons.add((ImageButton) findViewById(R.id.imageButton5));
        buttons.add((ImageButton) findViewById(R.id.imageButton6));
        buttons.add((ImageButton) findViewById(R.id.imageButton7));
        buttons.add((ImageButton) findViewById(R.id.imageButton8));
        buttons.add((ImageButton) findViewById(R.id.imageButton9));
        buttons.add((ImageButton) findViewById(R.id.imageButton10));
        buttons.add((ImageButton) findViewById(R.id.imageButton11));
        buttons.add((ImageButton) findViewById(R.id.imageButton12));

        Collections.shuffle(buttons, new Random());
    }

    private void shuffleGame() {
        Collections.shuffle(Arrays.asList(cat_images), new Random());
        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).setBackgroundResource(R.drawable.place);
            buttons.get(i).setEnabled(true);
        }
        Toast.makeText(MainActivity.this, "Game reset", Toast.LENGTH_SHORT).show();
    }

    private void checkIfGameFinished() {
        boolean allMatched = true;
        for (ImageButton button : buttons) {
            if (button.isEnabled()) {
                allMatched = false;
                break;
            }
        }
        if (allMatched) {
            Toast.makeText(MainActivity.this, "You have finished the game!", Toast.LENGTH_SHORT).show();
            shuffleGame();
        }
    }
}