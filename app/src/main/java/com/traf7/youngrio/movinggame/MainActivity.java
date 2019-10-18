package com.traf7.youngrio.movinggame;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

public class MainActivity extends AppCompatActivity {
    DrawView drawView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawView=findViewById(R.id.drawView);
    }

    public void moveLeft(View view)
    {
        drawView.sprite.setdX(-3);//set horizontal speed to move left
        drawView.sprite.setdY(0);
    }

    public void moveRight(View view)
    {
        drawView.sprite.setdX(3);//set horizontal speed to move right
        drawView.sprite.setdY(0);
    }

    public void moveUp(View view)
    {
        drawView.sprite.setdY(-3);//set vertical speed to move up
        drawView.sprite.setdX(0);
    }

    public void moveDown(View view)
    {
        drawView.sprite.setdY(3);//set vertical speed to move down
        drawView.sprite.setdX(0);
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
