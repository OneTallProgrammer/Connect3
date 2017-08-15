package com.onetallprogrammer.connect3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    // sentinel to test if neither player has won
    private final int DRAW = -1;

    private boolean gameActive = true;

    private int screenWidth;

    // 1 = Os 2 = Xs
    private int activePlayer  = 0;

    // 2 means square is not taken
    private int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get screen width
        DisplayMetrics metrics = new DisplayMetrics();

        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        this.screenWidth = metrics.widthPixels;

    }

    /**
     * brings in appropriate icon and updates game state based off of where it was placed on the
     * game board
     * @param view view calling method
     */
    public void slideIn(View view){
        ImageView square = (ImageView) view;

        int tappedSquare = Integer.parseInt(square.getTag().toString());

        if(gameState[tappedSquare] == 2 && gameActive){

            square.setTranslationY(-screenWidth);

            if (activePlayer == 0) {

                square.setImageResource(R.drawable.o);

                gameState[tappedSquare] = activePlayer;

                checkForGameEnd();

                activePlayer = 1;

            } else {

                square.setImageResource(R.drawable.x);

                gameState[tappedSquare] = activePlayer;

                checkForGameEnd();

                activePlayer = 0;

            }

            square.animate().translationYBy(screenWidth).rotation(360f).setDuration(500);

        }

    }

    /**
     * resets all game variables to default and re-enable game
     * @param view view calling method
     */
    public void resetGame(View view){
        for(int i = 0; i < gameState.length; i++){

            gameState[i] = 2;

        }

        GridLayout gameBoard = (GridLayout) findViewById(R.id.gameBoard);

        for(int i = 0; i < gameBoard.getChildCount(); i++){

            ImageView child = (ImageView) gameBoard.getChildAt(i);

            child.setImageResource(0);

        }

        activePlayer = 0;

        gameBoard.bringToFront();

        gameActive = true;
    }

    /**
     * handles cases for when a player wins and when the game is a draw
     */
    private void checkForGameEnd() {
        //player has won
        if((gameState[0] == activePlayer && gameState[1] == activePlayer && gameState[2] == activePlayer) ||
                (gameState[3] == activePlayer && gameState[4] == activePlayer && gameState[5] == activePlayer) ||
                (gameState[6] == activePlayer && gameState[7] == activePlayer && gameState[8] == activePlayer) ||
                (gameState[0] == activePlayer && gameState[3] == activePlayer && gameState[6] == activePlayer) ||
                (gameState[1] == activePlayer && gameState[4] == activePlayer && gameState[7] == activePlayer) ||
                (gameState[2] == activePlayer && gameState[5] == activePlayer && gameState[8] == activePlayer) ||
                (gameState[0] == activePlayer && gameState[4] == activePlayer && gameState[8] == activePlayer) ||
                (gameState[2] == activePlayer && gameState[4] == activePlayer && gameState[6] == activePlayer) ){

            gameActive = false;

            displayWinningMessage(activePlayer);

        }
        else if(gameIsDraw()){

            gameActive = false;

            displayWinningMessage(DRAW);

        }

    }

    /**
     *
     * @return true if game is a draw, false otherwise
     */
    private boolean gameIsDraw() {

        boolean isDraw = true;

        for(int number : gameState){

            // not all spaces have been used
            if(number == 2){

                isDraw = false;

                break;
            }
        }

        return isDraw;

    }

    /**
     * formats and brings forward a winning message layout display
     * @param winner 0 for circles, 1, for crosses, 2 for draw
     */
    private void displayWinningMessage(int winner) {
        LinearLayout winningMessageLayout = (LinearLayout) findViewById(R.id.winningMessageLayout);

        TextView winningMessage = (TextView) findViewById(R.id.winningMessage);

        Button playAgainButton = (Button) findViewById(R.id.playAgainButton);

        String message;

        switch(winner){
            case 0:
                message = getString(R.string.circles) + " " + getString(R.string.win);
                break;
            case 1:
                message = getString(R.string.crosses) + " " + getString(R.string.win);
                break;
            case DRAW:
                message = getString(R.string.draw);
                break;
            default:
                message = "";
                break;
        }

        winningMessage.setText(message);

        winningMessageLayout.bringToFront();

        playAgainButton.setEnabled(true);
    }

}
