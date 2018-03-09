package com.theprateek.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.theprateek.tictactoe.view.TicTacToeView;
import com.theprateek.tictactoe.R;

public class MainActivity extends AppCompatActivity {

    private TicTacToeView ticTacToeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ticTacToeView = (TicTacToeView)findViewById(R.id.ticTacToeView);
    }

    public void resetGame(View view){
        ticTacToeView.resetGame();
    }
}
