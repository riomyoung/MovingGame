package com.traf7.youngrio.movinggame;



import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.Timer;
import java.util.TimerTask;

public class DrawView extends View {
        Paint paint = new Paint();
        Sprite user_sprite = new Sprite( "bluejeans");
        Sprite badSprite = new Sprite( "bully");
        Sprite foodSprite = new Sprite( "angel");

//        Sprite foodSprite;
        private static final int MAX_STREAMS = 100;
        private int soundIdPunch;
        private int soundIdHeal;
        private int health = 3;

        public boolean gameEnd = false;

        private boolean soundPoolLoaded;
        private SoundPool soundPool;



        public DrawView(Context context, @Nullable AttributeSet attrs)
        {
            super(context, attrs);
            this.initSoundPool();


        }

        @Override
        protected void onLayout(boolean changed, int left, int top, int right, int bottom)
        {
            super.onLayout(changed, left, top, right, bottom);
            //note, at this point, getWidth() and getHeight() will have access the the dimensions
            foodSprite = generateSprite( "angel");
            badSprite = generateSprite( "bully");
            badSprite.setColor(Color.RED);
            badSprite.setBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.bully));
            user_sprite.grow(100);
            user_sprite.setBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.bluejeans));
            foodSprite.setBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.angel));

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
                foodSprite=generateSprite( "angel");
                foodSprite.setBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.angel));
                playSoundHeal();
                if ( health < 3 )
                {
                    health++;
                }
//                Context context = MainActivity.getApplicationContext();
//                CharSequence text = "Hello toast!";
//                int duration = Toast.LENGTH_SHORT;
//
//                Toast toast = Toast.makeText(context, text, duration);
//                toast.show();
            }
            if(RectF.intersects(user_sprite, badSprite)){
                //badSprite=generateSprite( "bully");
                user_sprite = generateSprite( "bluejeans");
//                badSprite.setColor(Color.RED);
                playSoundPunch();
                System.out.println( "PUNCH");
                user_sprite.setBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.bluejeans));
                health--;
                if (health < 0 )
                {
                    gameEnd = true;
                }
                //user_sprite.grow(-5);
            }
            if(RectF.intersects(foodSprite, badSprite)){
                //foodSprite.grow((int)(-foodSprite.width()*.1));//shrink food
                playSoundPunch();
                System.out.println( "PUNCH");
                foodSprite = generateSprite( "angel");
                foodSprite.setBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.angel));
//                badSprite=generateSprite( "bully");//recreate badSprite
//                badSprite.setBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.bully));

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
            int dX = (int)(Math.random()*6 + 1);
            int dY = (int)(Math.random()*6 + 1);
            return new Sprite(x,y,x+.1f*getWidth(),y+.1f*getWidth(),dX,dY,Color.MAGENTA );
        }

        private Sprite generateSprite( String string ) {
            float x = (float) (Math.random() * (getWidth() - .1 * getWidth()));
            float y = (float) (Math.random() * (getHeight() - .1 * getHeight()));
            int dX = (int) (Math.random() * 6 + 1);
            int dY = (int) (Math.random() * 6 + 1);
            return new Sprite(x, y, x + .1f * getWidth(), y + .1f * getWidth(), dX, dY, Color.MAGENTA, string );
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            if(event.getAction()==MotionEvent.ACTION_DOWN){
                if(badSprite.contains(event.getX(),event.getY())){
                    badSprite=generateSprite( "bully");
                    badSprite.setColor(Color.GREEN);
                }
            }
            return true;
        }


        private void initSoundPool() {
            //With Android API 21
            if (Build.VERSION.SDK_INT >= 21) {
                AudioAttributes audioAttributes = new AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_GAME)
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .build();

                SoundPool.Builder builder = new SoundPool.Builder();
                builder.setAudioAttributes(audioAttributes).setMaxStreams(MAX_STREAMS);

                this.soundPool = builder.build();
            } else {
                //SoundPool(int maxStreams, int streamType, int srcQuality)
                this.soundPool = new SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC, 0);
            }

            //When SoundPool load complete
            this.soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                @Override
                public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                    soundPoolLoaded = true;


                }
            });


            //Load the sound punch.wav into SoundPool
            this.soundIdPunch = this.soundPool.load(this.getContext(), R.raw.punch, 1);
            this.soundIdHeal = this.soundPool.load(this.getContext(), R.raw.heal, 1);
        }

        public void playSoundPunch() {
            if (this.soundPoolLoaded) {
                float leftVolume = 0.8f;
                float rightVolume = 0.8f;

                //Play sound explosion.wav
                int streamId = this.soundPool.play(this.soundIdPunch, leftVolume, rightVolume, 1, 0, 1f);
                System.out.println( streamId );
            }
        }

    public void playSoundHeal() {
        if (this.soundPoolLoaded) {
            float leftVolume = 0.8f;
            float rightVolume = 0.8f;

            //Play sound explosion.wav
            int streamId = this.soundPool.play(this.soundIdHeal, leftVolume, rightVolume, 1, 0, 1f);
            System.out.println( streamId );
        }
    }

    public void cancel()
    {
        badSprite.setdX(0);
        badSprite.setdY(0);
        foodSprite.setdX(0);
        foodSprite.setdY(0);
        user_sprite.setdX(0);
        user_sprite.setdY(0);
    }
}




