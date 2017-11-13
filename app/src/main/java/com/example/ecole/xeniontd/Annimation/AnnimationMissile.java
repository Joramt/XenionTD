package com.example.ecole.xeniontd.Annimation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.ecole.xeniontd.Views.Monsters;
import com.example.ecole.xeniontd.entities.Tower;

/**
 * Created by Bouach Omar on 13/01/2016.
 */
public class AnnimationMissile extends View implements Runnable {
    Bitmap missile;

    public static int compteur = 0;

    double step;

    float xmissileInitiale;  // besoin de cette variable pour tracer la courbe (courbe : y =a*x  on va boucler de xmissileInitiale ==> xcible)
    float ymissileInitiale;  // besoin de cette variable pour tracer la courbe (courbe : y =a*x  on va boucler de xmissileInitiale ==> xcible)
    float xCibleInitiale;  // besoin de cette variable pour tracer la courbe (courbe : y =a*x  on va boucler de xmissileInitiale ==> xcible)
    float yCibleInitaile;  // besoin de cette variable pour tracer la courbe (courbe : y =a*x  on va boucler de xmissileInitiale ==> xcible)
    float xmissile;
    float ymissile;

    float xcible;
    float ycible;


    float xcible1;    // coord x cible dans nouveau repère
    float ycible1;    // coord y cible dans nouveau repère


    float a;        // pour tracer la courbe y = a * x
    float x = 0;    // pour tracer la courbe y = a * x
    float y = 0;    // pour tracer la courbe y = a * x


    double angle;
    Handler handler;
    Tower t;
    Monsters m;

    final RelativeLayout rl;   //Le Layout Conteneur

    public AnnimationMissile(Context context, Handler handler, float xmissile, float ymissile, float xcible, float ycible, RelativeLayout rl, Bitmap bitmap, int step, Tower t, Monsters m) {
        super(context);


        missile = bitmap;

        this.handler = handler;

        this.xmissile = xmissile;
        this.ymissile = ymissile;
        this.xcible = xcible;
        this.ycible = ycible;

        xmissileInitiale = xmissile;
        ymissileInitiale = ymissile;
        xCibleInitiale = xcible;
        yCibleInitaile = ycible;


        xcible1 = xcible - xmissile;
        ycible1 = ycible * -1 + ymissile;
        this.t = t;
        this.m = m;
        t.setCanShot(false);

        if (xcible1 != 0) {
            this.step = Math.abs(xmissile - xcible) / step;
            a = ycible1 / xcible1;
            angle = Math.abs(ymissile - ycible) / (Math.sqrt(Math.pow(ymissile - ycible, 2) + Math.pow(xcible - xmissile, 2)));
            double angleInRadians = Math.asin(angle);
            double angleInDegrees = angleInRadians / Math.PI * 180;

            Log.d("angle  : ", "" + angle);
            Log.d("angle en degré : ", "" + angleInDegrees);
            if (xcible > xmissile)
                if (ycible >= ymissile)
                    missile = RotateBitmap(missile, (float) angleInDegrees);
                else
                    missile = RotateBitmap(missile,-1*(float) angleInDegrees );
            else if (ycible >= ymissile)
                missile = RotateBitmap(missile, 180 - (float) angleInDegrees);
            else
                missile = RotateBitmap(missile, 180 + (float) angleInDegrees);
        } else {
            a = 0;
            this.step = Math.abs(ymissile - ycible) / step;
            if (ymissile >= ycible)
                missile = RotateBitmap(missile, (float) -90);
            else
                missile = RotateBitmap(missile, (float) 90);
        }
        setWillNotDraw(false);
        this.rl = rl;
        rl.addView(this);
        handler.post(this);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(missile, xmissile, ymissile, new Paint(Color.WHITE));
    }

    @Override
    public void run() {
        if (a == 0) {
            if (ymissileInitiale < yCibleInitaile) {
                compteur++;
                ymissile += step;
                invalidate();
                Log.d("Compteur  : ", "" + AnnimationMissile.compteur);
                if (ymissile < ycible) {
                    handler.postDelayed(this, 10);
                }
            } else {
                compteur++;
                ymissile -= step;
                invalidate();
                Log.d("Compteur  : ", "" + AnnimationMissile.compteur);
                if (ymissile > ycible) {
                    handler.postDelayed(this, 10);
                }
            }
        } else {
            compteur++;
            y = a * x;
            xmissile = x + xmissileInitiale;
            ymissile = y * -1 + ymissileInitiale;
            invalidate();
            x += (xmissileInitiale - xcible > 0) ? -step : step;
//            Log.d("Compteur  : ", "" + AnnimationMissile.compteur);
            if (!(Math.abs(x) > Math.abs(xmissileInitiale - xcible)))
                handler.postDelayed(this, 10);
            else {
                rl.removeView(this);
                t.setCanShot(true);
                m.doDamage(t.getDmg());
                if(t.getStyle() == 2)
                    m.setFPS(150);
            }
        }
    }

    public final Bitmap RotateBitmap(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }
}