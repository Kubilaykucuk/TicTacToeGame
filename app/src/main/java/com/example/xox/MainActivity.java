package com.example.xox;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    boolean playerOneActive;
    TextView score_player1, score_player2, playerstatus;
    Button[] buttons = new Button[9];
    Button reset_btn, play_again_btn;

    int[] gameState = {5,5,5,5,5,5,5,5,5};
    int[][] winningPositions = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};

    int playeronescorecount, playertwoscorecount, rounds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        score_player1 = findViewById(R.id.scoreplayer1);
        score_player2 = findViewById(R.id.scoreplayer2);
        playerstatus = findViewById(R.id.status);
        reset_btn = findViewById(R.id.reset_btn);
        play_again_btn = findViewById(R.id.play_again_btn);

        buttons[0] = findViewById(R.id.btn0);
        buttons[1] = findViewById(R.id.btn1);
        buttons[2] = findViewById(R.id.btn2);
        buttons[3] = findViewById(R.id.btn3);
        buttons[4] = findViewById(R.id.btn4);
        buttons[5] = findViewById(R.id.btn5);
        buttons[6] = findViewById(R.id.btn6);
        buttons[7] = findViewById(R.id.btn7);
        buttons[8] = findViewById(R.id.btn8);

        for(int i = 0;i < buttons.length; i++)
        {
            buttons[i].setOnClickListener(this);
        }
        playerOneActive = true;
        playertwoscorecount = 0;
        playeronescorecount = 0;
        rounds = 0;

        reset_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkWinner() || rounds == 9) {
                    playagain();
                    playeronescorecount = 0;
                    playertwoscorecount = 0;
                    updateScoreboard();
                }
            }
        });
        play_again_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkWinner() || rounds == 9)
                {
                    playagain();
                    updateScoreboard();
                }

            }
        });

    }

    private boolean checkWinner()
    {
        boolean winnerResults = false;
        for(int[] winningPositions: winningPositions)
        {
            if(gameState[winningPositions[0]] == gameState[winningPositions[1]] && gameState[winningPositions[1]] == gameState[winningPositions[2]] && gameState[winningPositions[0]] != 5)
            {
                winnerResults = true;
            }
        }
        return winnerResults;
    }

    private void playagain()
    {
        rounds = 0;
        playerOneActive = true;
        for(int i = 0;i < buttons.length; i++)
        {
            gameState[i] = 5;
            buttons[i].setText("");
        }
        playerstatus.setText("Status");
    }

    private void updateScoreboard()
    {
        score_player1.setText(Integer.toString(playeronescorecount));
        score_player2.setText(Integer.toString(playertwoscorecount));
    }

    @Override
    public void onClick(View view)
    {
        if(!((Button)view).getText().toString().equals(""))
        {
            return;
        }
        else if(checkWinner())
        {
            return;
        }
        String buttonID = view.getResources().getResourceEntryName(view.getId());
        int gameStateInteger = Integer.parseInt(buttonID.substring(buttonID.length()-1,buttonID.length()));

        if(playerOneActive)
        {
            ((Button)view).setText("X");
            gameState[gameStateInteger] = 0;
        }
        else
        {
            ((Button)view).setText("O");
            gameState[gameStateInteger] = 1;
        }
        rounds++;

        if(checkWinner())
        {
            if(playerOneActive)
            {
                playeronescorecount++;
                updateScoreboard();
                playerstatus.setText("Player 1 has won");
            }else
            {
                playertwoscorecount++;
                updateScoreboard();
                playerstatus.setText("Player 2 has won");
            }
        }else if(rounds == 9)
        {
            playerstatus.setText("No Winner");
        }
        else
        {
            playerOneActive = !playerOneActive;
        }

    }


}
