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
    int[] smile_images;
    int clicked = 0;
    List<ImageButton> buttons;
    ImageButton firstCard, secondCard;
    int firstCardPick, secondCardPick;
    boolean isBusy = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        shuffle = findViewById(R.id.shuffleBtn);

        buttons = new ArrayList<>();
        loadCards((ArrayList<ImageButton>) buttons);

        smile_images = new int[]{R.drawable.smile1, R.drawable.smile2, R.drawable.smile3, R.drawable.smile4, R.drawable.smile5, R.drawable.smile6,
                R.drawable.smile1, R.drawable.smile2, R.drawable.smile3, R.drawable.smile4, R.drawable.smile5, R.drawable.smile6};

        for (int i = 0; i < buttons.size(); i++) {
            int finalI = i;
            buttons.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isBusy) return;
                    buttons.get(finalI).setBackgroundResource(smile_images[finalI]);

                    if (clicked == 0) {
                        firstCard = buttons.get(finalI);
                        firstCardPick = finalI;
                        clicked++;
                    } else if (clicked == 1) {
                        secondCard = buttons.get(finalI);
                        secondCardPick = finalI;
                        if (firstCardPick != secondCardPick && smile_images[firstCardPick] == smile_images[secondCardPick]) {

                            firstCard.setEnabled(false);
                            secondCard.setEnabled(false);
                            clicked = 0;
                            checkIfGameFinished();
                        } else {

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
                    buttons.get(i).setBackgroundResource(smile_images[i]);
                Collections.shuffle(buttons, new Random());

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
        Collections.shuffle(Arrays.asList(smile_images), new Random());
        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).setBackgroundResource(R.drawable.place);
            buttons.get(i).setEnabled(true);
        }
        Toast.makeText(MainActivity.this, "Game has been ", Toast.LENGTH_SHORT).show();
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