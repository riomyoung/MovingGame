package com.traf7.youngrio.movinggame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    DrawView drawView;
    Timer timer = new Timer();
    int duration = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawView = findViewById(R.id.drawView);
//        while (drawView.gameEnd != true )
//        {
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread( new Runnable()  {
                        @Override
                        public void run() {
                            duration++;
                            if ( drawView.gameEnd == true )
                            {
                                timer.cancel();
                                drawView.cancel();
                                Context context = getApplicationContext();
                                CharSequence text = "You lasted " + duration + " seconds!";
                                int duration = Toast.LENGTH_LONG;

                                Toast toast = Toast.makeText(context, text, duration);
                                toast.show();
                            }
                        }
                    });
                }
            }, 1000, 1000);
        }
//    }

    public void moveLeft(View view)
    {
        drawView.user_sprite.setdX(-3);//set horizontal speed to move left
        drawView.user_sprite.setdY(0);
    }

    public void moveRight(View view)
    {
        drawView.user_sprite.setdX(3);//set horizontal speed to move right
        drawView.user_sprite.setdY(0);
    }

    public void moveUp(View view)
    {
        drawView.user_sprite.setdY(-3);//set vertical speed to move up
        drawView.user_sprite.setdX(0);
    }

    public void moveDown(View view)
    {
        drawView.user_sprite.setdY(3);//set vertical speed to move down
        drawView.user_sprite.setdX(0);
    }



//    public void redCheckBoxClicked(View view)
//    {
//        setColor();
//    }
//
//    public void greenCheckBoxClicked(View view)
//    {
//        setColor();
//    }
//    public void setColor()
//    {
//        CheckBox greenCheckBox = findViewById(R.id.greenCheckBox);
//        CheckBox redCheckBox = findViewById(R.id.redCheckBox);
//
//        if(redCheckBox.isChecked())
//        {
//            if ( greenCheckBox.isChecked() )
//            {
//                drawView.sprite.setColor(Color.YELLOW);
//            }
//            else
//            {
//                drawView.sprite.setColor(Color.RED);
//            }
//        }
//        else if ( greenCheckBox.isChecked() )
//        {
//            drawView.sprite.setColor(Color.GREEN);
//        }
//        else
//        {
//            drawView.sprite.setColor(Color.BLUE);
//        }
//    }
}
