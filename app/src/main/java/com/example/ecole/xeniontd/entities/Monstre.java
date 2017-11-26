package com.example.ecole.xeniontd.entities;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.util.Log;

public class Monstre {

    private static final int BMP_ROWS = 4;
    private static final int BMP_COLUMNS = 3;
    Context ctx;
    Handler hand;

    int idMonster;
    Bitmap img;
    int currentCase;

    int initialLifePoints;
    int currentLifePoint;

    boolean isAlive;
    boolean isOnTheField;
    boolean isAtTheEnd;

    int monsterWidth;
    int monsterHeight;
    int directionMarche;

    int posX;
    int posY;
    int currentBmpFrame;

    int stepX = 5;
    int stepY = 5;

    int cellWidth;
    int cellHeight;

    public Monstre(Context ctx, int idMonster, int initialLifePoints, Bitmap img, Handler hand, int beginX, int beginY, int cellWidth, int cellHeight, String owner){

        this.hand = hand;
        this.monsterWidth = img.getWidth() / BMP_COLUMNS;
        this.monsterHeight = img.getHeight() / BMP_ROWS;

        this.ctx = ctx;
        this.idMonster = idMonster;
        this.img = img;
        this.currentCase = 9;

        this.initialLifePoints = initialLifePoints;
        this.currentLifePoint = initialLifePoints;

        this.isAlive = true;
        this.isOnTheField = true;
        this.isAtTheEnd = false;

        this.cellWidth = cellWidth;
        this.cellHeight = cellHeight;


        this.directionMarche = 0;
        this.posX =  beginX;
        this.posY =  beginY;
        Log.d("posX","POS X " + posX);
        Log.d("posX","POS X " + posY);
        Log.d("Owned ", "owned " + owner);


    }

    public void setCase(int x, int y) {
        //x -= ;
        //Log.d("t","je suis en X: " + x + " et en Y: " + y);
        int column =(int) Math.ceil( ( x   / cellWidth ) +0.1 );
        int line = y / cellHeight ;
        //Log.d("t","je suis donc sur la ligne: " + line + " et sur la colomne : " + column);
        this.currentCase = ( line * 18 ) + column;
    }


    /*private void sendInfosToGame() {

        if(isAtTheEnd()) {
            String[] messageString = new String[2];
            Message message = hand.obtainMessage();
            messageString[0]=String.valueOf(idMonster);
            messageString[1]=String.valueOf(c.MONSTER_FINISHED);
            message.obj = messageString;
            hand.sendMessage(message);
        }

        if(!isAlive()){
            String[] messageString = new String[2];
            Message message = hand.obtainMessage();
            messageString[0]=String.valueOf(idMonster);
            messageString[1]=String.valueOf(c.MONSTER_DEAD);
            message.obj = messageString;
            hand.sendMessage(message);
        }

    }*/

    public void doDamage(int dmg){
        if(dmg >= this.currentLifePoint)
            setCurrentLifePoint(0);
        else
            setCurrentLifePoint(getCurrentLifePoint() - dmg);

    }


   /* private void update() {

        switch (getDirectionMarche()) {
            case 1:
                posX -= stepX;
                break;
            case 2:
                posX+= stepX;
                break;
            case 3:
                posY -= stepY;
                break;
            case 0:
                posY += stepY;
                break;
            default:
                posY += stepY;
                break;
        }

        changeFrame();
        setDirection(posX, posY);
    }*/

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

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }

    public int getInitialLifePoints() {
        return initialLifePoints;
    }

    public void setInitialLifePoints(int initialLifePoints) {
        this.initialLifePoints = initialLifePoints;
    }

    public int getCurrentLifePoint() {
        return currentLifePoint;
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

    public int getCurrentBmpFrame() {
        return currentBmpFrame;
    }

    public void setCurrentBmpFrame(int currentBmpFrame) {
        this.currentBmpFrame = currentBmpFrame;
    }

    public int getDirectionMarche() {
        return directionMarche;
    }

    public void setDirection(int x,int y){

        int cellWidth = this.cellWidth/2;
        int cellHeight = this.cellHeight/2;

        setCase(x, y);

        //Log.d("c", "Case numero : " + this.currentCase);
        Log.d("t", "X monstre : " + x + " Y monstre : " + y);
        Log.d("t", "X break : " + ((cellWidth * 31) + 1) + " Y break : " + cellHeight * 12);

        if( ( x == cellWidth*17 && y == cellHeight*6  ) ||  (x==1) ) {
            // turn left
            setDirectionMarche(1);
        }
        else if(( x == cellWidth+1 && y == cellHeight*6  ) || ( x == ((cellWidth*23)-1) && y == cellHeight*12  )){
            // turn down
            setDirectionMarche(0);
        }
        else if(( x == ((cellWidth*13)-1) && y == cellHeight*18  )  || ( x == ((cellWidth*29)-2) && y == cellHeight*18  )){
            // turn up
            setDirectionMarche(3);

        }
        else if(( x == cellWidth+1 && y == cellHeight*18  ) || ( x == ((cellWidth*13)-1) && y == cellHeight*12  ) || ( x == ((cellWidth*23)-1) && y == cellHeight*18  )){
            // turn right
            setDirectionMarche(2);

        }else if( x == ((cellWidth*29)-2) && y == cellHeight  ){
            isOnTheField = false;
            isAlive = true;
            isAtTheEnd = true;
        }

    }
    public int getMonsterWidth() {
        return monsterWidth;
    }

    public void setMonsterWidth(int monsterWidth) {
        this.monsterWidth = monsterWidth;
    }

    public int getMonsterHeight() {
        return monsterHeight;
    }

    public void setMonsterHeight(int monsterHeight) {
        this.monsterHeight = monsterHeight;
    }

    public void setCurrentLifePoint(int currentLifePoint) {
        this.currentLifePoint = currentLifePoint;
        if(this.currentLifePoint == 0) {
            setIsAlive(false);
        }
    }

    public int getCurrentCase() {
        return currentCase;
    }

    public void setCurrentCase(int currentCase) {
        this.currentCase = currentCase;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setIsAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    public boolean isOnTheField() {
        return isOnTheField;
    }

    public void setIsOnTheField(boolean isOnTheField) {
        this.isOnTheField = isOnTheField;
    }

    public boolean isAtTheEnd() {
        return isAtTheEnd;
    }

    public void setIsAtTheEnd(boolean isAtTheEnd) {
        this.isAtTheEnd = isAtTheEnd;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
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

    public void setDirectionMarche(int directionMarche) {
        this.directionMarche = directionMarche;
    }

    public void changeFrame() {
        currentBmpFrame = ++currentBmpFrame % BMP_COLUMNS;
    }
}
