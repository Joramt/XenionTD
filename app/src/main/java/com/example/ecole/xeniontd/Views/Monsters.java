package com.example.ecole.xeniontd.Views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import com.example.ecole.xeniontd.utils.c;

/**
 * Created by Ecole on 1/11/2016.
 */
public class Monsters extends View implements Runnable {

    private static final int BMP_ROWS = 4;
    private static final int BMP_COLUMNS = 3;
    private Bitmap monsterImg;
    private int stepX;
    private int stepY;
    private int xAxis, yAxis;
    private int screenWidth, screenHeight;
    private int monsterWidthInsideSprite, monsterHeightInsideSprite;
    private int retour;
    private int currentFrame;
    private int srcX, srcY;
    private int walk, directionX;
    private Handler hand;
    Context ctx;
    int idMonster;
    private int nbStep;
    private int FPS;



    int beginX;
    int beginY;

    boolean isAlive;
    boolean isOnField;
    boolean isFinished;
    private float currentLifePoints;
    private float initalLifePoints;

    private int line;
    private int column;

    private TowerView tw;
    int cellWidth;
    int cellHeight;
    private int currentCell;
    private long debutMillis;

    Paint pHealthBar = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint pStroke = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint pDOutside = new Paint(Paint.ANTI_ALIAS_FLAG);

    Rect srcMonster = new Rect();
    Rect dstMonster = new Rect();

    RectF Stroke = new RectF();
    RectF healthBar = new RectF();
    RectF DesignOutside = new RectF();
    RectF DesignInside = new RectF();


    public Monsters(Context context, int idMonster, int beginX, int beginY, Handler hand, Bitmap monsterImg, int lifePoints, TowerView tw) {
        super(context);
        this.ctx = context;
        this.hand = hand;
        this.idMonster = idMonster;
        this.tw = tw;

        DisplayMetrics m = context.getResources().getDisplayMetrics();
        this.screenWidth = m.widthPixels;
        this.screenHeight = m.heightPixels;

        this.monsterImg = monsterImg;
        this.stepX = 5;
        this.stepY = 5;
        this.currentFrame = 1;

        this.monsterWidthInsideSprite = monsterImg.getWidth() / BMP_COLUMNS;
        this.monsterHeightInsideSprite = monsterImg.getHeight() / BMP_ROWS;

        this.directionX = 0;
        this.isFinished = false;
        this.isAlive = true;
        this.initalLifePoints = lifePoints;
        this.currentLifePoints = lifePoints;

        this.cellWidth = (ctx.getResources().getDisplayMetrics().widthPixels / 18);
        this.cellHeight = (ctx.getResources().getDisplayMetrics().heightPixels / 11);

        this.xAxis = (ctx.getResources().getDisplayMetrics().widthPixels / 18) * 9 - (ctx.getResources().getDisplayMetrics().widthPixels / 36);
        this.yAxis = (ctx.getResources().getDisplayMetrics().heightPixels / 11) * -1;

        this.beginX = beginX;
        this.beginY = beginY;

        this.nbStep = 0;
        this.FPS = 50;
        debutMillis = System.currentTimeMillis();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        retour = Math.min(screenWidth, screenHeight);
        setMeasuredDimension(screenWidth, screenHeight);
    }

    @Override
    public void onDraw(Canvas canvas) {

        // On determine la direction du personnage en fonction de son emplacement en DP
        setDirection(xAxis, yAxis);
        // On determine l'endroit
        this.srcX = currentFrame * monsterWidthInsideSprite;
        this.srcY = getWalk() * monsterHeightInsideSprite;

        srcMonster.left = srcX;
        srcMonster.top = srcY;
        srcMonster.right = srcX + monsterWidthInsideSprite;
        srcMonster.bottom = srcY + monsterHeightInsideSprite;

        dstMonster.left = xAxis;
        dstMonster.top = yAxis;
        dstMonster.right = xAxis + monsterWidthInsideSprite;
        dstMonster.bottom = yAxis + monsterHeightInsideSprite;

        drawLifePoints(canvas);
        canvas.drawBitmap(monsterImg, srcMonster, dstMonster, null);

    }

    private void drawLifePoints(Canvas canvas) {

        float percentageLifeLost = determineLost();

        pHealthBar.setColor(Color.parseColor("#FF56C33B"));

        if (getCurrentLifePoints() < initalLifePoints / 1.6 && getCurrentLifePoints() > initalLifePoints / 4)
            pHealthBar.setColor(Color.parseColor("#FFEB6D25"));
        else if (getCurrentLifePoints() < initalLifePoints / 4)
            pHealthBar.setColor(Color.parseColor("#FFD82D16"));

        pStroke.setStyle(Paint.Style.STROKE);
        pDOutside.setStyle(Paint.Style.STROKE);
        pStroke.setColor(Color.WHITE);
        pDOutside.setColor(Color.BLACK);
        pStroke.setStrokeWidth(10);
        pDOutside.setStrokeWidth(5);

        Stroke.left = xAxis - (cellWidth / 5);
        Stroke.top = yAxis - 65;

        Stroke.right = xAxis + cellWidth + (cellWidth / 5);

        Stroke.bottom = yAxis - 25;

        healthBar.left = xAxis - (cellWidth / 5);
        healthBar.top = yAxis - 60;
        healthBar.right = xAxis + (cellWidth / 5) + (int) (cellWidth * percentageLifeLost);
        healthBar.bottom = yAxis - 20;

        DesignInside.left = xAxis - 19;
        DesignInside.top = yAxis - 59;
        DesignInside.right = xAxis + cellWidth + 19;
        DesignInside.bottom = yAxis - 31;

        DesignOutside.left = xAxis - 27;
        DesignOutside.top = yAxis - 67;
        DesignOutside.right = xAxis + cellWidth + 27;
        DesignOutside.bottom = yAxis - 23;


        //RectF Stroke = new RectF(xAxis - 25 ,yAxis - 65, xAxis + monsterWidthInsideSprite + 25, yAxis - 25);
        //RectF DesignOutside = new RectF(xAxis - 27 ,yAxis - 67, xAxis + monsterWidthInsideSprite + 27, yAxis - 23);
        //RectF DesignInside = new RectF( ,, , );
        //RectF healthBar = new RectF(xAxis - 20 ,yAxis - 60, xAxis - 20 +  (int) ( 170 * percentageLifeLost ) , yAxis - 20);

        canvas.drawRoundRect(healthBar, 5, 5, pHealthBar);
        canvas.drawRoundRect(Stroke, 5, 5, pStroke);
        //canvas.drawRoundRect(DesignOutside, 5, 5, pDOutside);
        //canvas.drawRoundRect(DesignInside, 5, 5, pDOutside);

    }

    private float determineLost() {
        return ((currentLifePoints * 100) / initalLifePoints) / 100;
    }


    @Override
    public void run() {

        sendInfosToGame();
        update();
        checkTimeElapsedSinceHit();
        hand.postDelayed(this, FPS);
        isOnField = true;
        invalidate();
        tw.invalidate();

    }

    private void checkTimeElapsedSinceHit() {
        long checking = System.currentTimeMillis();
        long tDelta = checking - debutMillis;
        int elapsed = (int) tDelta / 1000;

            Log.d("DDDD", "" + elapsed%5);
        if(elapsed%5 == 0){
            this.setFPS(50);
        }
    }

    private void sendInfosToGame() {


        if (isFinished()) {
            String[] messageString = new String[3];
            Message message = hand.obtainMessage();
            messageString[0] = "death";
            messageString[1] = String.valueOf(idMonster);
            messageString[2] = String.valueOf(c.MONSTER_FINISHED);
            message.obj = messageString;
            hand.sendMessage(message);
        }

        if (!isAlive()) {
            String[] messageString = new String[3];
            Message message = hand.obtainMessage();
            messageString[0] = "death";
            messageString[1] = String.valueOf(idMonster);
            messageString[2] = String.valueOf(c.MONSTER_DEAD);
            message.obj = messageString;
            hand.sendMessage(message);
        }


    }

    private void update() {

        switch (getWalk()) {
            case 1:
                xAxis = xAxis - stepX;
                break;
            case 2:
                xAxis = xAxis + stepX;
                break;
            case 3:
                yAxis = yAxis - stepY;
                break;
            case 0:
                yAxis = yAxis + stepY;
                break;
            default:
                yAxis = yAxis + stepY;
                break;
        }
        nbStep++;
        currentFrame = ++currentFrame % BMP_COLUMNS;

    }

    public int getWalk() {
        return walk;
    }

    public void setWalk(int walk) {
        this.walk = walk;
    }

    public void setDirection(int x, int y) {


        int cellWidth = this.cellWidth / 2;
        int cellHeight = this.cellHeight / 2;

        setCase(x, y);

        if (((x >= cellWidth * 17 && x <= cellWidth * 18) && (y >= cellHeight * 6 && y <= cellHeight * 7)) || (x == 1)) {
            // turn left
            setWalk(1);
            directionX = getWalk();
        } else if ((x <= cellWidth && (y >= cellHeight * 6 && y <= cellHeight * 7)) || ((x >= ((cellWidth * 23)) && x <= ((cellWidth * 24))) && (y >= cellHeight * 12 && y <= cellHeight * 13))) {
            // turn down
            setWalk(0);
            directionX = getWalk();
        } else if (((x >= (cellWidth * 13) && x <= (cellWidth * 14)) && (y >= cellHeight * 18 && y <= cellHeight * 19)) || (x >= ((cellWidth * 29)) && y >= cellHeight * 18)) {
            // turn up
            setWalk(3);
            directionX = 3;
        } else if ((x <= cellWidth && y >= cellHeight * 18) || ((x >= (cellWidth * 13) && x <= ((cellWidth * 14))) && (y >= cellHeight * 12 && y <= (cellHeight * 12) + 6)) || ((x >= ((cellWidth * 23)) && x <= ((cellWidth * 24))) && y >= cellHeight * 18)) {
            // turn right
            setWalk(2);
            directionX = 2;
        } else if (x >= ((cellWidth * 29)) && y <= cellHeight) {
            isOnField = false;
            isAlive = true;
            isFinished = true;
        }

    }

    private void setCase(int x, int y) {

        this.column = (x / cellWidth);
        this.line = (y / cellHeight);
        setCurrentCell((line * 18) + column);
    }

    public boolean isCellNumberInArray(String tab[], String cellNumber) {
        int compt = 0;
        while (compt < tab.length && !cellNumber.equals(tab[compt])) {
            compt++;
        }
        return compt < tab.length;
    }

    public int getDirectionX() {
        return directionX;
    }

    public static int getBmpRows() {
        return BMP_ROWS;
    }

    public static int getBmpColumns() {
        return BMP_COLUMNS;
    }

    public Bitmap getMonsterImg() {
        return monsterImg;
    }

    public void setMonsterImg(Bitmap monsterImg) {
        this.monsterImg = monsterImg;
    }

    public int getStepX() {
        return stepX;
    }

    public void setStepX(int stepX) {
        this.stepX = stepX;
    }

    public int getStepY() {
        return stepY;
    }

    public void setStepY(int stepY) {
        this.stepY = stepY;
    }


    public void setxAxis(int xAxis) {
        this.xAxis = xAxis;
    }

    public void setyAxis(int yAxis) {
        this.yAxis = yAxis;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public void setScreenHeight(int screenHeight) {
        this.screenHeight = screenHeight;
    }

    public int getMonsterWidthInsideSprite() {
        return monsterWidthInsideSprite;
    }

    public void setMonsterWidthInsideSprite(int monsterWidthInsideSprite) {
        this.monsterWidthInsideSprite = monsterWidthInsideSprite;
    }

    public int getMonsterHeightInsideSprite() {
        return monsterHeightInsideSprite;
    }

    public void setMonsterHeightInsideSprite(int monsterHeightInsideSprite) {
        this.monsterHeightInsideSprite = monsterHeightInsideSprite;
    }

    public int getRetour() {
        return retour;
    }

    public void setRetour(int retour) {
        this.retour = retour;
    }

    public int getCurrentFrame() {
        return currentFrame;
    }

    public void setCurrentFrame(int currentFrame) {
        this.currentFrame = currentFrame;
    }

    public int getSrcX() {
        return srcX;
    }

    public void setSrcX(int srcX) {
        this.srcX = srcX;
    }

    public int getSrcY() {
        return srcY;
    }

    public void setSrcY(int srcY) {
        this.srcY = srcY;
    }

    public void setDirectionX(int directionX) {
        this.directionX = directionX;
    }

    public Handler getHand() {
        return hand;
    }

    public void setHand(Handler hand) {
        this.hand = hand;
    }

    public Context getCtx() {
        return ctx;
    }

    public void setCtx(Context ctx) {
        this.ctx = ctx;
    }

    public int getIdMonster() {
        return idMonster;
    }

    public void setIdMonster(int idMonster) {
        this.idMonster = idMonster;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setIsAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    public int getxAxis() {
        return xAxis;
    }

    public int getyAxis() {
        return yAxis;
    }

    public boolean isOnField() {
        return isOnField;
    }

    public void setIsOnField(boolean isOnField) {
        this.isOnField = isOnField;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setIsFinished(boolean isFinished) {
        this.isFinished = isFinished;
    }

    public float getCurrentLifePoints() {
        return currentLifePoints;
    }

    public void setCurrentLifePoints(float currentLifePoints) {
        this.currentLifePoints = currentLifePoints;
        if (this.currentLifePoints == 0) {

            setIsAlive(false);


        }
    }

    private void resetMonstre() {


    }

    public void doDamage(int dmg) {
        if (dmg >= this.currentLifePoints) {
            setCurrentLifePoints(0);
        } else
            setCurrentLifePoints(getCurrentLifePoints() - dmg);

    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getBeginX() {
        return beginX;
    }

    public void setBeginX(int beginX) {
        this.beginX = beginX;
    }

    public int getBeginY() {
        return beginY;
    }

    public void setBeginY(int beginY) {
        this.beginY = beginY;
    }

    public float getInitalLifePoints() {
        return initalLifePoints;
    }

    public void setInitalLifePoints(float initalLifePoints) {
        this.initalLifePoints = initalLifePoints;
    }

    public Paint getpHealthBar() {
        return pHealthBar;
    }

    public void setpHealthBar(Paint pHealthBar) {
        this.pHealthBar = pHealthBar;
    }

    public Paint getpStroke() {
        return pStroke;
    }

    public void setpStroke(Paint pStroke) {
        this.pStroke = pStroke;
    }

    public Paint getpDOutside() {
        return pDOutside;
    }

    public void setpDOutside(Paint pDOutside) {
        this.pDOutside = pDOutside;
    }

    public Rect getSrcMonster() {
        return srcMonster;
    }

    public void setSrcMonster(Rect srcMonster) {
        this.srcMonster = srcMonster;
    }

    public Rect getDstMonster() {
        return dstMonster;
    }

    public void setDstMonster(Rect dstMonster) {
        this.dstMonster = dstMonster;
    }

    public RectF getStroke() {
        return Stroke;
    }

    public void setStroke(RectF stroke) {
        Stroke = stroke;
    }

    public RectF getHealthBar() {
        return healthBar;
    }

    public void setHealthBar(RectF healthBar) {
        this.healthBar = healthBar;
    }

    public int getCurrentCell() {
        return currentCell;
    }

    public void setCurrentCell(int currentCell) {

        this.currentCell = currentCell;
        String[] messageString = new String[3];
        Message message = hand.obtainMessage();
        messageString[0] = "position";
        messageString[1] = String.valueOf(idMonster);
        messageString[2] = String.valueOf(this.currentCell);
        message.obj = messageString;
        hand.sendMessage(message);

    }

    public int getFPS() {
        return FPS;
    }

    public void setFPS(int FPS) {
        this.FPS = FPS;

       // Log.d("temps","" + debutMillis);

    }

    public int getNbStep() {
        return nbStep;
    }

    public void setNbStep(int nbStep) {
        this.nbStep = nbStep;
    }
}
