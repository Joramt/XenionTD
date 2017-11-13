package com.example.ecole.xeniontd.entities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.RelativeLayout;

import com.example.ecole.xeniontd.Annimation.AnnimationMissile;
import com.example.ecole.xeniontd.R;
import com.example.ecole.xeniontd.Views.Monsters;

import java.util.Arrays;

/**
 * Created by Nicolas on 2016-01-12.
 */
public class Tower implements Comparable {
    private String nom;
    private int id;
    private Projectile[] armement;
    private String imgSrc;
    private int tier;
    private int style;
    private Bitmap towerImg;
    private int posX;
    private int posY;
    private int cashInvesti;
    private int caseNumber;
    private int offsetX;
    private int offsetY;
    private int cellWidth;
    private int cellHeight;
    private int range;
    private int dmg;
    private Integer[] reachableCell;
    private Handler hand;
    private Context ctx;
    private RelativeLayout gameLayout;
    private boolean canShot;
    private int FPS;




    // constructeurs
    public Tower(int id, int tier, int style) {
        this.tier = tier;
        this.style = style;
        this.id = id;
    }

    public Tower(){}

    public Tower(String nom, int id, Projectile[] armement, String imgSrc) {

        this.nom = nom;
        this.id = id;
        this.armement = armement;
        this.imgSrc = imgSrc;
        this.cashInvesti = 50;
        this.range = 2;


    }

    public Tower(Handler hand, Context ctx, RelativeLayout gameLayout, int cellHeight, int cellWidth){
        this.hand = hand;
        this.ctx = ctx;
        this.gameLayout = gameLayout;
        this.canShot = true;
        this.cellHeight = cellHeight;
        this.cellWidth = cellWidth;
    }

    public Tower(Bitmap towerImg, int posX, int posY){

        this.towerImg = towerImg;
        this.posX = posX;
        this.posY = posY;
        this.range = 2;
    }

    @Override
    public int compareTo(Object another) {
        Tower t = (Tower) another;
        int compareID = ((Tower) another).getCaseNumber();


        return this.caseNumber-compareID;
    }

    // getters setters

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Projectile[] getArmement() {
        return armement;
    }

    public void setArmement(Projectile[] armement) {
        this.armement = armement;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public int getTier() {
        return tier;
    }

    public void setTier(int tier) {
        this.tier = tier;
    }

    public int getStyle() {
        return style;
    }

    public void setStyle(int style) {
        this.style = style;
    }

    public Bitmap getTowerImg() {
        return towerImg;
    }

    public void setTowerImg(Bitmap towerImg) {
        this.towerImg = towerImg;
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


    public int getCashInvesti() {
        return cashInvesti;
    }

    public void setCashInvesti(int cashInvesti) {
        this.cashInvesti = cashInvesti;
    }

    public int getCaseNumber() {
        return caseNumber;
    }

    public void setCaseNumber(int caseNumber) {
        this.caseNumber = caseNumber;
    }

    public int getOffsetX() {
        return offsetX;
    }

    public void setOffsetX(int offsetX) {
        this.offsetX = offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }

    public void setOffsetY(int offsetY) {
        this.offsetY = offsetY;
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

    public int getRange() {
        return range;
    }

    public int getFPS() {
        return FPS;
    }

    public void setFPS(int FPS) {
        this.FPS = FPS;
    }

    public void setRange(int range) {
        if(range != this.range) {
            this.range = range;
            // le nb de case touchable selon le range
            // ex : range = 3 -> 3*(4+(2*(3-1)))
            //                -> 3*(4+4)
            //                -> 3*8
            //                -> 24 cell

            configureReachableCell(range);
        }
    }

    public void configureReachableCell(int range) {
        reachableCell = new Integer[range * (4 + (2 * (range - 1)))];
        switch (range){
            case 2:
                reachableCell[0] = caseNumber + 1;
                reachableCell[1] = caseNumber +2;
                reachableCell[2] = caseNumber -1;
                reachableCell[3] = caseNumber -2;
                reachableCell[4] = caseNumber -18;
                reachableCell[5] = caseNumber -36;
                reachableCell[6] = caseNumber +18;
                reachableCell[7] = caseNumber +36;
                reachableCell[8] = caseNumber +1 + 18;
                reachableCell[9] = caseNumber +1 - 18;
                reachableCell[10] = caseNumber -1 - 18;
                reachableCell[11] = caseNumber -1 + 18;
                break;
            case 3:
                reachableCell[0] = caseNumber + 1;
                reachableCell[1] = caseNumber +2;
                reachableCell[2] = caseNumber -1;
                reachableCell[3] = caseNumber -2;
                reachableCell[4] = caseNumber -18;
                reachableCell[5] = caseNumber -36;
                reachableCell[6] = caseNumber +18;
                reachableCell[7] = caseNumber +36;
                reachableCell[8] = caseNumber +1 + 18;
                reachableCell[9] = caseNumber +1 - 18;
                reachableCell[10] = caseNumber -1 - 18;
                reachableCell[11] = caseNumber -1 + 18;
                reachableCell[12] = caseNumber +3;
                reachableCell[13] = caseNumber -3;
                reachableCell[14] = caseNumber +54;
                reachableCell[15] = caseNumber -54;
                reachableCell[16] = caseNumber +2 + 18;
                reachableCell[17] = caseNumber +2 - 18;
                reachableCell[18] = caseNumber -2 + 18;
                reachableCell[19] = caseNumber -2 - 18;
                reachableCell[20] = caseNumber -1 + 36;
                reachableCell[21] = caseNumber -1 - 36;
                reachableCell[22] = caseNumber +1 + 36;
                reachableCell[23] = caseNumber +1 - 36;
                break;
            case 4:
                reachableCell[0] = caseNumber + 1;
                reachableCell[1] = caseNumber +2;
                reachableCell[2] = caseNumber -1;
                reachableCell[3] = caseNumber -2;
                reachableCell[4] = caseNumber -18;
                reachableCell[5] = caseNumber -36;
                reachableCell[6] = caseNumber +18;
                reachableCell[7] = caseNumber +36;
                reachableCell[8] = caseNumber +1 + 18;
                reachableCell[9] = caseNumber +1 - 18;
                reachableCell[10] = caseNumber -1 - 18;
                reachableCell[11] = caseNumber -1 + 18;
                reachableCell[12] = caseNumber +3;
                reachableCell[13] = caseNumber -3;
                reachableCell[14] = caseNumber +54;
                reachableCell[15] = caseNumber -54;
                reachableCell[16] = caseNumber +2 + 18;
                reachableCell[17] = caseNumber +2 - 18;
                reachableCell[18] = caseNumber -2 + 18;
                reachableCell[19] = caseNumber -2 - 18;
                reachableCell[20] = caseNumber -1 + 36;
                reachableCell[21] = caseNumber -1 - 36;
                reachableCell[22] = caseNumber +1 + 36;
                reachableCell[23] = caseNumber +1 - 36;
                reachableCell[24] = caseNumber +4;
                reachableCell[25] = caseNumber -4;
                reachableCell[26] = caseNumber +72;
                reachableCell[27] = caseNumber -72;
                reachableCell[28] = caseNumber +1 + 54;
                reachableCell[29] = caseNumber +1 - 54;
                reachableCell[30] = caseNumber -1 - 54;
                reachableCell[31] = caseNumber -1 - 54;
                reachableCell[32] = caseNumber +2 + 36;
                reachableCell[33] = caseNumber +2 - 36;
                reachableCell[34] = caseNumber -2 - 36;
                reachableCell[35] = caseNumber -2 + 36;
                reachableCell[36] = caseNumber +3 - 18;
                reachableCell[37] = caseNumber +3 + 18;
                reachableCell[38] = caseNumber -3 - 18;
                reachableCell[39] = caseNumber -3 + 18;
                break;
            case 5:
                reachableCell[0] = caseNumber + 1;
                reachableCell[1] = caseNumber +2;
                reachableCell[2] = caseNumber -1;
                reachableCell[3] = caseNumber -2;
                reachableCell[4] = caseNumber -18;
                reachableCell[5] = caseNumber -36;
                reachableCell[6] = caseNumber +18;
                reachableCell[7] = caseNumber +36;
                reachableCell[8] = caseNumber +1 + 18;
                reachableCell[9] = caseNumber +1 - 18;
                reachableCell[10] = caseNumber -1 - 18;
                reachableCell[11] = caseNumber -1 + 18;
                reachableCell[12] = caseNumber +3;
                reachableCell[13] = caseNumber -3;
                reachableCell[14] = caseNumber +54;
                reachableCell[15] = caseNumber -54;
                reachableCell[16] = caseNumber +2 + 18;
                reachableCell[17] = caseNumber +2 - 18;
                reachableCell[18] = caseNumber -2 + 18;
                reachableCell[19] = caseNumber -2 - 18;
                reachableCell[20] = caseNumber -1 + 36;
                reachableCell[21] = caseNumber -1 - 36;
                reachableCell[22] = caseNumber +1 + 36;
                reachableCell[23] = caseNumber +1 - 36;
                reachableCell[24] = caseNumber +4;
                reachableCell[25] = caseNumber -4;
                reachableCell[26] = caseNumber +72;
                reachableCell[27] = caseNumber -72;
                reachableCell[28] = caseNumber +1 + 54;
                reachableCell[29] = caseNumber +1 - 54;
                reachableCell[30] = caseNumber -1 - 54;
                reachableCell[31] = caseNumber -1 - 54;
                reachableCell[32] = caseNumber +2 + 36;
                reachableCell[33] = caseNumber +2 - 36;
                reachableCell[34] = caseNumber -2 - 36;
                reachableCell[35] = caseNumber -2 + 36;
                reachableCell[36] = caseNumber +3 - 18;
                reachableCell[37] = caseNumber +3 + 18;
                reachableCell[38] = caseNumber -3 - 18;
                reachableCell[39] = caseNumber -3 + 18;
                reachableCell[40] = caseNumber -5;
                reachableCell[41] = caseNumber +90;
                reachableCell[42] = caseNumber -90;
                reachableCell[43] = caseNumber +4 + 18;
                reachableCell[44] = caseNumber -4 + 18;
                reachableCell[45] = caseNumber +4 - 18;
                reachableCell[46] = caseNumber -4 - 18;
                reachableCell[47] = caseNumber -3 + 36;
                reachableCell[48] = caseNumber -3 - 36;
                reachableCell[49] = caseNumber +3 + 36;
                reachableCell[50] = caseNumber +3 - 36;
                reachableCell[51] = caseNumber -2 - 54;
                reachableCell[52] = caseNumber -2 + 54;
                reachableCell[53] = caseNumber +2 + 54;
                reachableCell[54] = caseNumber +2 - 54;
                reachableCell[55] = caseNumber -1 + 72;
                reachableCell[56] = caseNumber -1 - 72;
                reachableCell[57] = caseNumber +1 + 72;
                reachableCell[58] = caseNumber +1 + 72;
                reachableCell[59] = caseNumber +5;
                break;
            case 6:
                reachableCell[0] = caseNumber + 1;
                reachableCell[1] = caseNumber +2;
                reachableCell[2] = caseNumber -1;
                reachableCell[3] = caseNumber -2;
                reachableCell[4] = caseNumber -18;
                reachableCell[5] = caseNumber -36;
                reachableCell[6] = caseNumber +18;
                reachableCell[7] = caseNumber +36;
                reachableCell[8] = caseNumber +1 + 18;
                reachableCell[9] = caseNumber +1 - 18;
                reachableCell[10] = caseNumber -1 - 18;
                reachableCell[11] = caseNumber -1 + 18;
                reachableCell[12] = caseNumber +3;
                reachableCell[13] = caseNumber -3;
                reachableCell[14] = caseNumber +54;
                reachableCell[15] = caseNumber -54;
                reachableCell[16] = caseNumber +2 + 18;
                reachableCell[17] = caseNumber +2 - 18;
                reachableCell[18] = caseNumber -2 + 18;
                reachableCell[19] = caseNumber -2 - 18;
                reachableCell[20] = caseNumber -1 + 36;
                reachableCell[21] = caseNumber -1 - 36;
                reachableCell[22] = caseNumber +1 + 36;
                reachableCell[23] = caseNumber +1 - 36;
                reachableCell[24] = caseNumber +4;
                reachableCell[25] = caseNumber -4;
                reachableCell[26] = caseNumber +72;
                reachableCell[27] = caseNumber -72;
                reachableCell[28] = caseNumber +1 + 54;
                reachableCell[29] = caseNumber +1 - 54;
                reachableCell[30] = caseNumber -1 - 54;
                reachableCell[31] = caseNumber -1 - 54;
                reachableCell[32] = caseNumber +2 + 36;
                reachableCell[33] = caseNumber +2 - 36;
                reachableCell[34] = caseNumber -2 - 36;
                reachableCell[35] = caseNumber -2 + 36;
                reachableCell[36] = caseNumber +3 - 18;
                reachableCell[37] = caseNumber +3 + 18;
                reachableCell[38] = caseNumber -3 - 18;
                reachableCell[39] = caseNumber -3 + 18;
                reachableCell[40] = caseNumber -5;
                reachableCell[41] = caseNumber +90;
                reachableCell[42] = caseNumber -90;
                reachableCell[43] = caseNumber +4 + 18;
                reachableCell[44] = caseNumber -4 + 18;
                reachableCell[45] = caseNumber +4 - 18;
                reachableCell[46] = caseNumber -4 - 18;
                reachableCell[47] = caseNumber -3 + 36;
                reachableCell[48] = caseNumber -3 - 36;
                reachableCell[49] = caseNumber +3 + 36;
                reachableCell[50] = caseNumber +3 - 36;
                reachableCell[51] = caseNumber -2 - 54;
                reachableCell[52] = caseNumber -2 + 54;
                reachableCell[53] = caseNumber +2 + 54;
                reachableCell[54] = caseNumber +2 - 54;
                reachableCell[55] = caseNumber -1 + 72;
                reachableCell[56] = caseNumber -1 - 72;
                reachableCell[57] = caseNumber +1 + 72;
                reachableCell[58] = caseNumber +1 + 72;
                reachableCell[59] = caseNumber +5;
                reachableCell[60] = caseNumber +6;
                reachableCell[61] = caseNumber -6;
                reachableCell[62] = caseNumber +108;
                reachableCell[63] = caseNumber -108;
                reachableCell[64] = caseNumber +5 + 90;
                reachableCell[65] = caseNumber +5 - 90;
                reachableCell[66] = caseNumber -5 + 90;
                reachableCell[67] = caseNumber -5 - 90;
                reachableCell[68] = caseNumber +4 - 72;
                reachableCell[69] = caseNumber +4 + 72;
                reachableCell[70] = caseNumber -4 - 72;
                reachableCell[71] = caseNumber -4 + 72;
                reachableCell[72] = caseNumber +3 + 54;
                reachableCell[73] = caseNumber +3 - 54;
                reachableCell[74] = caseNumber -3 + 54;
                reachableCell[75] = caseNumber -3 - 54;
                reachableCell[76] = caseNumber +2 + 36;
                reachableCell[77] = caseNumber +2 - 36;
                reachableCell[78] = caseNumber -2 - 36;
                reachableCell[79] = caseNumber -2 + 36;
                reachableCell[80] = caseNumber -1 - 18;
                reachableCell[81] = caseNumber -1 + 18;
                reachableCell[82] = caseNumber +1 + 18;
                reachableCell[83] = caseNumber +1 - 18;
                break;
        }
        Arrays.sort(reachableCell);
    }

    public int getDmg() {
        return dmg;
    }

    public boolean isCanShot() {
        return canShot;
    }

    public void setCanShot(boolean canShot) {
        this.canShot = canShot;
    }

    public void setDmg(int dmg) {
        this.dmg = dmg;
    }

    public Integer[] getReachableCell() {
        return reachableCell;
    }

    public void setReachableCell(Integer[] reachableCell) {
        this.reachableCell = reachableCell;
    }

    public Handler getHand() {
        return hand;
    }

    public void setHand(Handler hand) {
        this.hand = hand;
    }

    public void checkIfMonstreInRange(int monstreID, int monstreCell) {
        int i = 0;
        while( i < reachableCell.length && reachableCell[i] != monstreCell){i++;}
        String[] messageString = new String[4];
        Message message = hand.obtainMessage();

        messageString[0] = "tour";

        if(i < reachableCell.length)
            messageString[1] = "true";
        else
            messageString[1] = "false";

        messageString[2] = String.valueOf(monstreID);
        messageString[3] = String.valueOf(this.id);

        message.obj = messageString;
        hand.sendMessage(message);
    }

    public void fire(Monsters m){
        Bitmap bmp = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.arrow);
        Integer[] monstrePos;

        int monstrePosY = 0;
        int demiCellHeight = cellHeight / 2;
        int demiCellWidth = cellWidth /2;

        int distanceX = this.posX - m.getxAxis();
        int distanceY = this.posY - m.getyAxis();
        if(distanceX < 0)
            distanceX*=-1;
        if(distanceY < 0)
            distanceY*=-1;



         monstrePos = getFutureLocations(35,35,distanceX, distanceY, m.getxAxis(), m.getyAxis(), 10,5,5,50);

        if(posX < m.getxAxis() ) {
            monstrePosY = m.getyAxis() - cellHeight;
            //Log.d("d","je tire au dessus");
        }
        else
            monstrePosY = m.getyAxis() + cellHeight;

        if(canShot) {
            AnnimationMissile proj = new AnnimationMissile(ctx, hand, posX + demiCellWidth, posY - cellHeight,m.getxAxis(),  monstrePosY, gameLayout, bmp, getFPS(), this, m);

        }


    }

    public Integer[] getFutureLocations(int stepXprojectile, int stepYprojectile, int distanceX, int distanceY, int posMonstreX, int posMonstreY, int projectileDelay, int stepXmonstre, int stepYmonstre, int monstreDelay){
        Integer[] retour = new Integer[2];

        int nbStepMissileX = distanceX / stepXprojectile;
        int nbStepMissileY = distanceY / stepYprojectile;

        int tempsPourXMissile = nbStepMissileX * projectileDelay;
        int tempsPourYMissle = nbStepMissileY * projectileDelay;

        int nbStepMonstreX = tempsPourXMissile/monstreDelay;
        int nbStepMonstreY = tempsPourYMissle/monstreDelay;


        int distanceXParcourueMonstre = (stepXmonstre * nbStepMonstreX) ;
        int distanceYParcourueMonstre = (stepYmonstre * nbStepMonstreY) ;


        retour[0] = posMonstreX + distanceXParcourueMonstre;
        retour[1] = posMonstreY + distanceYParcourueMonstre;

        return retour;

    }

    public Context getCtx() {
        return ctx;
    }

    public void setCtx(Context ctx) {
        this.ctx = ctx;
    }

    public RelativeLayout getGameLayout() {
        return gameLayout;
    }

    public void setGameLayout(RelativeLayout gameLayout) {
        this.gameLayout = gameLayout;
    }
}
