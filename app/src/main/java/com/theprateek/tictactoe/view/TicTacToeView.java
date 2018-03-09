package com.theprateek.tictactoe.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import com.theprateek.tictactoe.R;


/**
 * Created by a3logics on 9/3/18.
 */

public class TicTacToeView extends View {

    private int matrixSize;
    private float boxSize;
    private float seperatorWidth;
    private Paint paint;
    private Drawable xImage;
    private Drawable oImage;
    private ArrayList<Rect> xArray;
    private ArrayList<Rect> oArray;
    private int [][] status;
    private String player = "Player 1 (X)";
    private int wonBy = 0;

    public TicTacToeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        xImage = context.getResources().getDrawable(R.drawable.x);
        oImage = context.getResources().getDrawable(R.drawable.o);
        xArray = new ArrayList<Rect>();
        oArray = new ArrayList<Rect>();
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.TtView, 0, 0);
        try {
            matrixSize = a.getInteger(R.styleable.TtView_matrixSize, 0);
            seperatorWidth = a.getDimension(R.styleable.TtView_seperatorWidth, 0);
            boxSize = a.getDimension(R.styleable.TtView_boxSize, 0);
            status = new int [matrixSize][matrixSize];
        } finally {
            a.recycle();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int widthHalf = this.getMeasuredWidth()/2;
        int heightHalf = this.getMeasuredHeight()/2;

        int initialX = (int)(widthHalf - ((matrixSize*(boxSize+seperatorWidth))/2));
        int initialY = (int)(heightHalf - ((matrixSize*(boxSize+seperatorWidth))/2));

        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(seperatorWidth);



        for(int i = 0; i < (matrixSize+1); i++){

            int tempStartX = (int)(initialX + (boxSize+seperatorWidth)*i);
            int tempStartY = initialY;

            int tempStopX = (int)(initialX + (boxSize+seperatorWidth)*i);
            int tempStopY = (int)(initialY + (boxSize+seperatorWidth)*matrixSize);

            canvas.drawLine(tempStartX,tempStartY,tempStopX,tempStopY,paint);

        }

        for(int i = 0; i < (matrixSize+1); i++){

            int tempStartX = initialX;
            int tempStartY = (int)(initialY + (boxSize+seperatorWidth)*i);


            int tempStopX = (int)(initialX + (boxSize+seperatorWidth)*matrixSize);
            int tempStopY = (int)(initialY + (boxSize+seperatorWidth)*i);

            canvas.drawLine(tempStartX,tempStartY,tempStopX,tempStopY,paint);

        }
        if(xArray.size() > 0){
            for(Rect rect: xArray){
                xImage.setBounds(rect);
                xImage.draw(canvas);
            }
        }
        if(oArray.size() > 0){
            for(Rect rect: oArray){
                oImage.setBounds(rect);
                oImage.draw(canvas);
            }
        }

        wonBy = checkWin();
        if(wonBy != 0){
            resetGame();

        }
        boolean isGameOver = checkGameOver();
        if(isGameOver){
            wonBy = -1;
            resetGame();
        }


        paint.setColor(Color.BLACK);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(50);
        int textHeightPoint = (int)((heightHalf - ((matrixSize*(boxSize+seperatorWidth))/2)) - 100);
        canvas.drawText("Chance: " + player, widthHalf, textHeightPoint, paint);


    }
    public boolean checkGameOver(){
        boolean isGameOver = true;
        for(int i = 0; i < status.length; i++){
            for(int j = 0; j < status[i].length; j++){
               if(status[i][j] == 0){
                   isGameOver = false;
               }
            }
        }
        return isGameOver;
    }


    public int checkWin(){
        int matchedValue = 0;


        for(int i = 0; i < status.length; i++){
            matchedValue = status[i][0];
            if(matchedValue != 0) {
                for (int j = 0; j < status[i].length; j++) {
                    if (status[i][j] != matchedValue) {
                        matchedValue = 0;
                        break;
                    }
                }
                if(matchedValue != 0){
                    break;
                }
            }
        }
        if(matchedValue != 0){
            return matchedValue;
        }

        for(int i = 0; i < status.length; i++){
            matchedValue = status[0][i];
            if(matchedValue != 0) {
                for (int j = 0; j < status[i].length; j++) {
                    if (status[j][i] != matchedValue) {
                        matchedValue = 0;
                        break;
                    }
                }
                if(matchedValue != 0){
                    break;
                }
            }
        }
        if(matchedValue != 0){
            return matchedValue;
        }

        matchedValue = status[0][0];
        if(matchedValue != 0) {
            for(int i = 0; i < status.length; i++) {
                if (status[i][i] != matchedValue) {
                    matchedValue = 0;
                    break;
                }
            }
        }
        if(matchedValue != 0){
            return matchedValue;
        }

        matchedValue = status[0][status.length-1];
        if(matchedValue != 0) {
            for(int i = 0; i < status.length; i++) {
                if (status[i][status.length-(i+1)] != matchedValue) {
                    matchedValue = 0;
                    break;
                }
            }
        }
        return  matchedValue;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //super.onDraw(canvas);
        int widthHalf = this.getMeasuredWidth()/2;
        int heightHalf = this.getMeasuredHeight()/2;
        int x = (int)event.getX();
        int y = (int)event.getY();
        int initialX = (int)(widthHalf - ((matrixSize*(boxSize+seperatorWidth))/2));
        int initialY = (int)(heightHalf - ((matrixSize*(boxSize+seperatorWidth))/2));
        Toast.makeText(getContext(),x + " : " + y, Toast.LENGTH_LONG);
        int touchedX = -1;
        int touchedY = -1;
        for(int i = 0; i < matrixSize; i++){
            for(int j = 0; j < matrixSize; j++) {
                int tempStartX = (int) (initialX + (boxSize + seperatorWidth) * i);
                int tempStartY = (int) (initialY + (boxSize + seperatorWidth) * j);

                int tempStopX = (int) (tempStartX + (boxSize + seperatorWidth));
                int tempStopY = (int) (tempStartY + (boxSize + seperatorWidth));
                if((x>tempStartX && x < tempStopX) && (y>tempStartY && y < tempStopY)){
                    //Toast.makeText(getContext(),i + " : " + j, Toast.LENGTH_LONG);
                    touchedX = i;
                    touchedY = j;
                }

            }

        }
        if(touchedX != -1 && touchedY != -1){

            if(status[touchedX][touchedY] != 0){
                Log.e("TIC-TAC-TOE","Already Has A Value!" );
            }
            else {
                int tempStartX = (int) (initialX + (boxSize + seperatorWidth) * touchedX);
                int tempStartY = (int) (initialY + (boxSize + seperatorWidth) * touchedY);

                int tempStopX = (int) (tempStartX + boxSize);
                int tempStopY = (int) (tempStartY + boxSize);

                Rect imageBounds = new Rect(tempStartX, tempStartY, tempStopX, tempStopY);
                if(player.equalsIgnoreCase("Player 1 (X)")){
                    xArray.add(imageBounds);
                    player = "Player 2 (O)";
                    status[touchedX][touchedY] = 1;
                } else if(player.equalsIgnoreCase("Player 2 (O)")){
                    oArray.add(imageBounds);
                    player = "Player 1 (X)";
                    status[touchedX][touchedY] = 2;
                }

                invalidate();
                requestLayout();
            }

        }
        return super.onTouchEvent(event);
    }

    public void resetGame(){


        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        if(wonBy > 0){
            builder.setMessage("Hey Player " + wonBy + ", You Won!")
                    .setTitle("Congratulation");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    player = "Player 1 (X)";
                    wonBy = 0;
                    xArray = new ArrayList<Rect>();
                    oArray = new ArrayList<Rect>();
                    for(int i = 0; i < status.length; i++){
                        for(int j = 0; j < status[i].length; j++){
                            status[i][j] = 0;
                        }
                    }
                    invalidate();
                    requestLayout();

                }
            });
        }
        else if(wonBy == 0){
            builder.setMessage("Are you sure to reset game?")
                    .setTitle("Reset Game");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    player = "Player 1 (X)";
                    wonBy = 0;
                    xArray = new ArrayList<Rect>();
                    oArray = new ArrayList<Rect>();
                    for(int i = 0; i < status.length; i++){
                        for(int j = 0; j < status[i].length; j++){
                            status[i][j] = 0;
                        }
                    }
                    invalidate();
                    requestLayout();

                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User cancelled the dialog
                }
            });

        }
        if(wonBy < 0){
            builder.setMessage("No more chances available. Play Again!")
                    .setTitle("Game Over");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    player = "Player 1 (X)";
                    wonBy = 0;
                    xArray = new ArrayList<Rect>();
                    oArray = new ArrayList<Rect>();
                    for(int i = 0; i < status.length; i++){
                        for(int j = 0; j < status[i].length; j++){
                            status[i][j] = 0;
                        }
                    }
                    invalidate();
                    requestLayout();

                }
            });
        }
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
