package com.example.taher.fish_game;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View ;
import android.widget.Toast;

/**
 * Created by Taher on 1/23/2019.
 */

public class FlyingFishView extends View
{
    private Bitmap fish[] = new Bitmap[2];
    private int fishx =10;
    private int fishy;
    private int fishspeed ;
    private int canvaswidth , canvashight ;
    private int yellowx , yellowy , yellowspeed=10;
    private Paint yellowpaint = new Paint();

    private int greenx , greeny , greenspeed=20;
    private Paint greempaint = new Paint();

    private int redx , redy , redspeed=20;
    private Paint redpaint = new Paint();


    private int score,lifecounteroffish;
    private boolean touch = false;

    private Bitmap backgroundImage;
    private Paint scorepaint = new Paint();
    private Bitmap life[] = new Bitmap[2];

    public FlyingFishView(Context context) {
        super(context);
        fish[0] = BitmapFactory.decodeResource(getResources(),R.drawable.fish1);
        fish[1] = BitmapFactory.decodeResource(getResources(),R.drawable.fish2);

        backgroundImage = BitmapFactory.decodeResource(getResources(),R.drawable.background);
        yellowpaint.setColor(Color.YELLOW);
        yellowpaint.setAntiAlias(false);

        greempaint.setColor(Color.GREEN);
        greempaint.setAntiAlias(false);


        redpaint.setColor(Color.RED);
        redpaint.setAntiAlias(false);


        scorepaint.setColor(Color.WHITE);
        scorepaint.setTextSize(50);
        scorepaint.setTypeface(Typeface.DEFAULT_BOLD);
        scorepaint.setAntiAlias(true);
        life[0] = BitmapFactory.decodeResource(getResources(),R.drawable.hearts);
        life[1] = BitmapFactory.decodeResource(getResources(),R.drawable.heart_grey);
        fishy=550;
        score =0;
        lifecounteroffish=3;
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        canvaswidth=canvas.getWidth();
        canvashight=canvas.getHeight();
        canvas.drawBitmap(backgroundImage,0,0,null);
        int minfishy = fish[0].getHeight();
        int maxfixhy = canvashight-fish[0].getHeight()*1;
        fishy=fishy+fishspeed;

        if(fishy<minfishy){
            fishy=minfishy;

        }
        if(fishy > maxfixhy ){
            fishy=maxfixhy;

        }
        fishspeed=fishspeed+2;
        if(touch){

            canvas.drawBitmap(fish[1],fishx,fishy,null);
            touch=false;
        }
        else{

            canvas.drawBitmap(fish[0],fishx,fishy,null);
        }

        yellowx=yellowx-yellowspeed;
        if(hitballchecker(yellowx,yellowy))
        {

            score=score+10;
            yellowx=-100;

        }
        if(yellowx<0)
        {
            yellowx=canvaswidth+21;
            yellowy=(int) Math.floor(Math.random()*(maxfixhy-minfishy))+70;

        }
        canvas.drawCircle(yellowx,yellowy,15,yellowpaint);


        greenx=greenx-greenspeed;
        if(hitballchecker(greenx,greeny))
        {

            score=score+20;
            greenx=-100;

        }
        if(greenx<0)
        {
            greenx=canvaswidth+21;
            greeny=(int) Math.floor(Math.random()*(maxfixhy-minfishy))+70;

        }
        canvas.drawCircle(greenx,greeny,15,greempaint);


        redx=redx-redspeed;
        if(hitballchecker(redx,redy))
        {

            redx=-100;
            lifecounteroffish--;
            if(lifecounteroffish==0)
            {
                Toast.makeText(getContext(),"Game Over",Toast.LENGTH_SHORT).show();
                Intent gameover = new Intent(getContext(), GameOverActivity.class);
                gameover.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                getContext().startActivity(gameover);


            }

        }
        if(redx<0)
        {
            redx=canvaswidth+21;
            redy=(int) Math.floor(Math.random()*(maxfixhy-minfishy))+70;

        }
        canvas.drawCircle(redx,redy,20,redpaint);
        canvas.drawText("Score : "+score,20,60,scorepaint);
        int x=300;
        int y=30;


        for(int i=0;i<3;i++){

            if(i<lifecounteroffish)
            {


                canvas.drawBitmap(life[0],x,y,null);

            }
            else{

                canvas.drawBitmap(life[1],x,y,null);

            }
            x=x+60;




        }





    }

    public boolean hitballchecker(int x , int y)
    {
        if(fishx <x && x<fishx+fish[0].getWidth()&&fishy<y&& y<fishy+fish[0].getHeight())
        {
            return true;

        }
        return false;

    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(event.getAction()==MotionEvent.ACTION_DOWN){

            touch=true;
            fishspeed = -22;

        }
        return true;



    }
}
