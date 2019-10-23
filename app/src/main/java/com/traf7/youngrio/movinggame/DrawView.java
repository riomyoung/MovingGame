package com.traf7.youngrio.movinggame;



import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

    public class DrawView extends View {
        Paint paint = new Paint();
        Sprite user_sprite = new Sprite( "bluejeans");
        Sprite badSprite = new Sprite( "chibi1");
        Sprite foodSprite = new Sprite( "health");
//        Sprite foodSprite;

        public DrawView(Context context, @Nullable AttributeSet attrs)
        {
            super(context, attrs);
        }

        @Override
        protected void onLayout(boolean changed, int left, int top, int right, int bottom)
        {
            super.onLayout(changed, left, top, right, bottom);
            //note, at this point, getWidth() and getHeight() will have access the the dimensions
            foodSprite = generateSprite( "health");
            badSprite = generateSprite( "chibi1");
            badSprite.setColor(Color.RED);
            badSprite.setBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.chibi1));
            user_sprite.grow(100);
            user_sprite.setBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.bluejeans));
            foodSprite.setBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.health));
//            badSprite.grow(1000);
        }

        @Override
        protected void onDraw(Canvas canvas)
        {
            super.onDraw(canvas);
            paint.setColor(Color.GRAY);//set paint to gray
            canvas.drawRect(getLeft(),0,getRight(),getBottom(),paint);//paint background gray
            paint.setColor(Color.RED);//set paint to red
            //sprite updates itself
            user_sprite.update(canvas);
            foodSprite.update(canvas);
            badSprite.update(canvas);
            if(RectF.intersects(user_sprite, foodSprite)){
                foodSprite=generateSprite( "health");
                foodSprite.setBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.health));
                user_sprite.grow(10);
            }
            if(RectF.intersects(user_sprite, badSprite)){
                badSprite=generateSprite( "chibi1");
//                badSprite.setColor(Color.RED);
                badSprite.setBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.chibi1));
                user_sprite.grow(-5);
            }
            if(RectF.intersects(foodSprite, badSprite)){
                foodSprite.grow((int)(-foodSprite.width()*.1));//shrink food
                badSprite=generateSprite( "chibi1");//recreate badSprite
                badSprite.setBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.chibi1));

//                badSprite.setColor(Color.GREEN);
            }
            //sprite draws itself
            user_sprite.draw(canvas);
            foodSprite.draw(canvas);
            badSprite.draw(canvas);
            invalidate();  //redraws screen, invokes onDraw()
        }
        private Sprite generateSprite(){
            float x = (float)(Math.random()*(getWidth()-.1*getWidth()));
            float y = (float)(Math.random()*(getHeight()-.1*getHeight()));
            int dX = (int)(Math.random()*11-5);
            int dY = (int)(Math.random()*11-5);
            return new Sprite(x,y,x+.1f*getWidth(),y+.1f*getWidth(),dX,dY,Color.MAGENTA );
        }

        private Sprite generateSprite( String string ) {
            float x = (float) (Math.random() * (getWidth() - .1 * getWidth()));
            float y = (float) (Math.random() * (getHeight() - .1 * getHeight()));
            int dX = (int) (Math.random() * 11 - 5);
            int dY = (int) (Math.random() * 11 - 5);
            return new Sprite(x, y, x + .1f * getWidth(), y + .1f * getWidth(), dX, dY, Color.MAGENTA, string );
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            if(event.getAction()==MotionEvent.ACTION_DOWN){
                if(badSprite.contains(event.getX(),event.getY())){
                    badSprite=generateSprite( "chibi1");
                    badSprite.setColor(Color.GREEN);
                }
            }
            return true;
        }
    }


