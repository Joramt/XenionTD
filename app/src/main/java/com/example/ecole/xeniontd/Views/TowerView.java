package com.example.ecole.xeniontd.Views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.example.ecole.xeniontd.R;
import com.example.ecole.xeniontd.entities.GameCell;
import com.example.ecole.xeniontd.entities.Tower;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by Ecole on 1/17/2016.
 */
public class TowerView extends View {

    private ArrayList<Tower> Towers;

    private int cellWidth;
    private int cellHeight;
    private Context ctx;
    private Rect highlighted;
    private int cx;
    private int cy;
    private int radius;
    private Paint p;
    private Integer[] monstrePos;
    private Handler hand;

    public TowerView(Context ctx, int cellWidth, int cellHeight, Handler hand){
        super(ctx);
        this.ctx = ctx;
        Towers = new ArrayList<Tower>();
        this.cellWidth = cellWidth;
        this.cellHeight = cellHeight;
        highlighted = new Rect(0,0,0,0);
        p = new Paint(Paint.ANTI_ALIAS_FLAG);
        cx = 0;
        cy = 0;
        radius = 0;
        this.monstrePos = new Integer[20];
        this.hand = hand;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(ctx.getResources().getDisplayMetrics().widthPixels, ctx.getResources().getDisplayMetrics().heightPixels);
    }

    public void addTower(Tower T){

        Log.d("T", "" + Towers.size());
        T.setOffsetX((cellWidth / 9));
        T.setOffsetY((cellHeight / 2));
        T.setId(Towers.size() + 1);
        Towers.add(T);
        Log.d("T", "" + Towers.size());

        clear();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(15);
        p.setColor(Color.parseColor("#D4246BAA"));
        p.setAlpha(2);
        canvas.drawRect(highlighted, p);

        p.setStyle(Paint.Style.FILL);

        p.setColor(Color.parseColor("#D4A8CBEA"));
        p.setAlpha(150);
        canvas.drawRect(highlighted, p);



        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(15);
        p.setColor(Color.parseColor("#D4C32C21"));

        canvas.drawCircle(cx, cy, radius, p);

        p.setStyle(Paint.Style.FILL);

        p.setColor(Color.parseColor("#D4EFA46B"));
        p.setAlpha(150);

        canvas.drawCircle(cx, cy, radius, p);

        Collections.sort(Towers);

        for(Tower t:Towers){
            Bitmap img = t.getTowerImg();
            Rect src = new Rect(0,0,img.getWidth(),img.getHeight());
            Rect dest = new Rect(t.getPosX()+ t.getOffsetX(),t.getPosY()- t.getOffsetY(), t.getPosX() + img.getWidth()+ t.getOffsetX(), t.getPosY() + img.getHeight()-t.getOffsetY());
            canvas.drawBitmap(img,src,dest,null);
        }

    }

    public Tower findTowerById(int id) {
        int i = 0;
        Tower t = new Tower();

        while(Towers.get(i).getId() != id) i++;

        if(i < Towers.size())
            t = Towers.get(i);

        return t;
    }

    public Tower findTowerByCaseNumber(int cellNumber) {
        int i = 0;
        Tower t = new Tower();

        while(Towers.get(i).getCaseNumber() != cellNumber) i++;

        if(i < Towers.size())
            t = Towers.get(i);

        return t;
    }

    public void upgradeTower(Tower t, int range, int dmg) {
        Tower towerToChange = findTowerByCaseNumber(t.getCaseNumber());
        //Log.d("dddedewffwe", "ICIIIITEEEE" + towerToChange.getStyle());

        //if(towerToChange.getStyle() == 12 || towerToChange.getStyle() == 112)
        t.setOffsetY(cellHeight);

        towerToChange.setTier(t.getTier() + 1);
        towerToChange.setRange(range);
        towerToChange.setDmg(dmg);
        clear();
        drawRange(towerToChange);

    }

    public void highlightCell(GameCell cell) {
        highlighted = new Rect(cell.getCoordX(), cell.getCoordY(),cell.getCoordX() + cellWidth, cell.getCoordY()+cellHeight);
        radius = 0;
        invalidate();

    }

    public void clear(){
        highlighted = new Rect(0,0,0,0);
        invalidate();
    }

    public void drawRange(Tower tow) {
        highlighted = new Rect(0,0,0,0);
        cx = tow.getPosX() + ( tow.getTowerImg().getWidth() /2 );
        cy = tow.getPosY() + (tow.getTowerImg().getHeight() /2) - (  tow.getOffsetY() /2 );
        radius = tow.getRange() * cellHeight;
        invalidate();

    }

    public void clearAll() {
        radius = 0;
        clear();
    }

    public ArrayList<Tower> getTowers() {
        return Towers;
    }

    public void setTowers(ArrayList<Tower> towers) {
        Towers = towers;
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

    public Context getCtx() {
        return ctx;
    }

    public void setCtx(Context ctx) {
        this.ctx = ctx;
    }

    public Rect getHighlighted() {
        return highlighted;
    }

    public void setHighlighted(Rect highlighted) {
        this.highlighted = highlighted;
    }

    public int getCx() {
        return cx;
    }

    public void setCx(int cx) {
        this.cx = cx;
    }

    public int getCy() {
        return cy;
    }

    public void setCy(int cy) {
        this.cy = cy;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public Paint getP() {
        return p;
    }

    public void setP(Paint p) {
        this.p = p;
    }

    public void removeTower(Tower t) {
        Tower towerToChange = findTowerByCaseNumber(t.getCaseNumber());
        Towers.remove(towerToChange);
        radius =0;
        clear();

    }

    public void addMonstrePosition(String monstreId, String monstreCell) {
        int monstreID = Integer.parseInt(monstreId);
        int monstreCurrentCell = Integer.parseInt(monstreCell);
        monstrePos[monstreID] = monstreCurrentCell;
        askTowerIfMonstreInRange(monstreID, monstreCurrentCell);
    }

    private void askTowerIfMonstreInRange(int monstreID, int monstreCell) {
        for(Tower t:Towers) {
            t.checkIfMonstreInRange(monstreID, monstreCell);
        }
    }

    public void resetTowers(ArrayList<String>  towerData) {

        Integer[] towerCell = new Integer[towerData.size()];
        Integer[] towerStyle = new Integer[towerData.size()];
        Integer[] towerTier = new Integer[towerData.size()];

        Bitmap towerImg  = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.basic1);
        for(int i =0; i < towerData.size();i++) {
            Log.d("dede","test" + towerData.get(i));
            towerCell[i] = Integer.valueOf(towerData.get(i).substring(0, towerData.get(i).indexOf("#")));
            towerStyle[i] = Integer.valueOf(towerData.get(i).substring(towerData.get(i).indexOf("#") + 1, towerData.get(i).indexOf("&")));
            towerTier[i] = Integer.valueOf(towerData.get(i).substring(towerData.get(i).indexOf("&") + 1, towerData.get(i).length()));
            Log.d("dede", "emplacement " + towerCell[i]);
            Tower t = new Tower();
            t.setRange(0);
            t.setTier(towerTier[i]);
            t.setStyle(towerStyle[i]);
            t.setCaseNumber(towerCell[i]);
            determinePos(t,towerCell[i]);
            if(towerTier[i] == 1){
                t.setRange(2);
                t.setFPS(45);
                t.setCashInvesti(50);
                t.setTier(1);
                if(towerStyle[i] == 1) {
                    t.setDmg(30);
                    t.setStyle(1);
                    towerImg = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.basic1);

                }else{
                    t.setDmg(40);
                    t.setStyle(2);
                    towerImg = BitmapFactory.decodeResource(ctx.getResources(),R.drawable.basic2);
                }

            }else if(towerTier[i] == 2){
                if(towerStyle[i] == 11){
                    t.setRange(2);
                    t.setDmg(55);
                    t.setCashInvesti(150);
                    t.setFPS(35);
                    t.setStyle(11);
                    towerImg = BitmapFactory.decodeResource(ctx.getResources(),R.drawable.upgrade11);
                }else if(towerStyle[i] == 21){
                    t.setRange(2);
                    t.setDmg(55);
                    t.setCashInvesti(170);
                    t.setFPS(35);
                    t.setStyle(21);
                    towerImg = BitmapFactory.decodeResource(ctx.getResources(),R.drawable.upgrade21);
                }else if(towerStyle[i] == 12){
                    t.setRange(2);
                    t.setDmg(62);
                    t.setCashInvesti(140);
                    t.setFPS(35);
                    t.setStyle(12);
                    towerImg = BitmapFactory.decodeResource(ctx.getResources(),R.drawable.upgrade12);
                }else if(towerStyle[i] == 22){
                    t.setRange(2);
                    t.setDmg(62);
                    t.setCashInvesti(180);
                    t.setFPS(35);
                    t.setStyle(22);
                    towerImg = BitmapFactory.decodeResource(ctx.getResources(),R.drawable.upgrade22);
                }
            }
            else{
                if(towerStyle[i] == 11){
                    t.setRange(4);
                    t.setDmg(105);
                    t.setCashInvesti(600);
                    t.setFPS(15);
                    t.setStyle(111);
                    towerImg = BitmapFactory.decodeResource(ctx.getResources(),R.drawable.upgrade111);
                }else if(towerStyle[i] == 21){
                    t.setRange(4);
                    t.setDmg(105);
                    t.setCashInvesti(640);
                    t.setFPS(15);
                    t.setStyle(211);
                    towerImg = BitmapFactory.decodeResource(ctx.getResources(),R.drawable.upgrade211);
                }else if(towerStyle[i] == 12){
                    t.setRange(4);
                    t.setDmg(80 );
                    t.setCashInvesti(620);
                    t.setFPS(15);
                    t.setStyle(112);
                    towerImg = BitmapFactory.decodeResource(ctx.getResources(),R.drawable.upgrade112);
                }else if(towerStyle[i] == 22){
                    t.setRange(4);
                    t.setDmg(250);
                    t.setCashInvesti(640);
                    t.setFPS(15);
                    t.setStyle(222);
                    towerImg = BitmapFactory.decodeResource(ctx.getResources(),R.drawable.upgrade212);
                }
            }
            t.setCtx(ctx);
            t.configureReachableCell(t.getRange());
            t.setHand(this.hand);

            t.setTowerImg(towerImg);
            addTower(t);
            invalidate();
        }

    }

    private void determinePos(Tower t, Integer caseNumber) {
        int line = (caseNumber / 18);

        int column = caseNumber - (18 * line );
        Log.d("dedetE","case : " + caseNumber + " line : " + line);
        Log.d("dedetE","case : " + caseNumber + " colum : " + column);
        t.setPosX(column*cellWidth);
        t.setPosY(line * cellHeight);
        invalidate();
    }
}
