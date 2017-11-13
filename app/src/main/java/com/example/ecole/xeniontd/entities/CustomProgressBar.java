package com.example.ecole.xeniontd.entities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.ecole.xeniontd.R;

/**
 * Created by Ecole on 1/17/2016.
 */
public class CustomProgressBar extends View {

    LinearLayout progressBar;
    Context ctx;
    int nbMonstre;
    int cellWidth;
    int cellHeight;
    Bitmap progressImg;
    View mainView;

    int stepX;

    public CustomProgressBar(Context ctx, LinearLayout progressBar, int nbMonstre, int cellWidth, int cellHeight, View mainView) {
        super(ctx);

        this.progressBar = progressBar;
        //setupProgressBar();
        this.ctx = ctx;
        this.nbMonstre = nbMonstre;
        this.cellHeight = cellHeight;
        this.cellWidth = cellWidth;
        this.mainView = mainView;
        progressBar.addView(this);
        this.bringToFront();
        this.progressImg = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.helmet);
        setupProgressBar();

    }//

    private void setupProgressBar() {
        progressBar.getLayoutParams().width = ( cellWidth * 8)+ progressImg.getWidth();
        progressBar.getLayoutParams().height = (int) (cellHeight * 1.5);
        progressBar.setOrientation(LinearLayout.HORIZONTAL);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(cellWidth * 8, (int) (cellHeight * 1.5));
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        progressBar.setLayoutParams(params);
        this.stepX = (( cellWidth * 7 ) ) / 20 ;
        Log.d("ddd", "Length total : " + cellWidth * 8);
        Log.d("ddd", "Step: " + stepX);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(Color.WHITE);
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(6);
        RectF bar = new RectF(0,3*(cellHeight/5),(cellWidth*8),cellHeight);
        Paint p2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        p2.setColor(Color.RED);

        if(nbMonstre < 15 && nbMonstre > 5)
            p2.setColor(Color.parseColor("#FFEB6D25"));
        else if(nbMonstre <= 5)
            p2.setColor(Color.parseColor("#FF56C33B"));





        Rect rSrc = new Rect(0,0,progressImg.getWidth(),progressImg.getHeight());
        Rect rDest = new Rect(((nbMonstre) * stepX) - 40 ,0,((nbMonstre) * stepX) + progressImg.getWidth() - 40 ,progressImg.getHeight());
        RectF inside = new RectF(0,3*(cellHeight/5),((nbMonstre-1) * stepX)+ progressImg.getWidth(),cellHeight);

        if(nbMonstre < 20)
            inside = new RectF(0,3*(cellHeight/5),((nbMonstre-2) * stepX) + progressImg.getWidth(),cellHeight);

        Log.d("t", "dessin debut = " + ((nbMonstre * stepX) - progressImg.getWidth()));
        Log.d("t", "dessin fin = " + nbMonstre * stepX);
        canvas.drawRoundRect(inside, 15, 15, p2);
        canvas.drawRoundRect(bar, 15, 15, p);

        p.setColor(Color.GRAY);
        canvas.drawBitmap(progressImg,rSrc, rDest, p);
    }

    public LinearLayout getProgressBar() {
        return progressBar;
    }

    public void setProgressBar(LinearLayout progressBar) {
        this.progressBar = progressBar;
    }

    public Context getCtx() {
        return ctx;
    }

    public void setCtx(Context ctx) {
        this.ctx = ctx;
    }

    public int getNbMonstre() {
        return nbMonstre;
    }

    public void setNbMonstre(int nbMonstre) {
        this.nbMonstre = nbMonstre;
        invalidate();
    }

    public int getCellWidth() {
        return cellWidth;
    }

    public void setCellWidth(int cellWidth) {
        this.cellWidth = cellWidth;
    }

    public int getCellHeight() {
        return cellHeight;
    }

    public void setCellHeight(int cellHeight) {
        this.cellHeight = cellHeight;
    }

    public Bitmap getProgressImg() {
        return progressImg;
    }

    public void setProgressImg(Bitmap progressImg) {
        this.progressImg = progressImg;
    }

    public View getMainView() {
        return mainView;
    }

    public void setMainView(View mainView) {
        this.mainView = mainView;
    }

    public int getStepX() {
        return stepX;
    }

    public void setStepX(int stepX) {
        this.stepX = stepX;
    }
}
