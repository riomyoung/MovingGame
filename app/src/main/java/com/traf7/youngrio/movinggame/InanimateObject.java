package com.traf7.youngrio.movinggame;

import android.graphics.Bitmap;
import android.graphics.RectF;

public class InanimateObject extends RectF {
    private int positionX, positionY, color;
    private String image;
    private Bitmap bitmap;

    public InanimateObject( int x, int y, String imageName )
    {
        positionX = x;
        positionY = y;
        image = imageName;
    }

    public void setBitmap(Bitmap bitmap)
    {
        this.bitmap = bitmap;
    }
}
