package com.example.ecole.xeniontd.entities;

import android.content.Context;
import android.widget.ImageView;

import com.example.ecole.xeniontd.R;
import com.example.ecole.xeniontd.utils.c;

/**
 * Created by Ecole on 1/6/2016.
 */
public class GameCell extends ImageView {

    private int cellNumber;
    private Context ctx;
    private ImageView iv;
    private boolean emptyCell;
    private String cellType;
    private String cellImg;
    private int coordY;
    private int coordX;

    public GameCell(int cellNumber, Context ctx, int coordY, int coordX) {
        super(ctx);
        this.ctx = ctx;
        this.cellNumber = cellNumber;
        this.coordY = coordY;
        this.coordX = coordX;
        this.emptyCell = true;
    }

    public void configureCell(String season) {
        ImageView iv = new ImageView(this.ctx);
        if (season.equals("automne")) {
            if (isCellNumberInArray(c.ARRAY_ARBRES_ROUGE, this.cellNumber))
                iv.setImageResource(R.drawable.arbrerouge_red);
            else if (isCellNumberInArray(c.ARRAY_ARBRES_BLEU, this.cellNumber))
                iv.setImageResource(R.drawable.arbrebleu_red);
            else if (isCellNumberInArray(c.ARRAY_ARBRES_VERT, this.cellNumber))
                iv.setImageResource(R.drawable.arbrevert_red);

            else if (isCellNumberInArray(c.ARRAY_CHEMIN_LEFT, this.cellNumber))
                iv.setImageResource(R.drawable.chemin_left_red);
            else if (isCellNumberInArray(c.ARRAY_CHEMIN_RIGHT, this.cellNumber))
                iv.setImageResource(R.drawable.chemin_right_red);
            else if (isCellNumberInArray(c.ARRAY_CHEMIN_TOP, this.cellNumber))
                iv.setImageResource(R.drawable.chemin_top_red);
            else if (isCellNumberInArray(c.ARRAY_CHEMIN_BOTTOM, this.cellNumber))
                iv.setImageResource(R.drawable.chemin_bottom_red);

            else if (isCellNumberInArray(c.ARRAY_TERRAIN_FEUILLES, this.cellNumber))
                iv.setImageResource(R.drawable.terrainfeuilles_red);

            else if (isCellNumberInArray(c.ARRAY_CORNER_TOPTOLEFT_OUTSIDE, this.cellNumber))
                iv.setImageResource(R.drawable.corner_toptoleft_outside_red);
            else if (isCellNumberInArray(c.ARRAY_CORNER_TOPTORIGHT_OUTSIDE, this.cellNumber))
                iv.setImageResource(R.drawable.corner_toptoright_outside_red);
            else if (isCellNumberInArray(c.ARRAY_CORNER_BOTTOMTOLEFT_OUTSIDE, this.cellNumber))
                iv.setImageResource(R.drawable.corner_bottomtoleft_outside_red);
            else if (isCellNumberInArray(c.ARRAY_CORNER_BOTTOMTORIGHT_OUTSIDE, this.cellNumber))
                iv.setImageResource(R.drawable.corner_bottomtoright_outside_red);

            else if (isCellNumberInArray(c.ARRAY_CORNER_TOPTOLEFT_INSIDE, this.cellNumber))
                iv.setImageResource(R.drawable.corner_toptoleft_inside_red);
            else if (isCellNumberInArray(c.ARRAY_CORNER_TOPTORIGHT_INSIDE, this.cellNumber))
                iv.setImageResource(R.drawable.corner_toptoright_inside_red);
            else if (isCellNumberInArray(c.ARRAY_CORNER_BOTTOMTOLEFT_INSIDE, this.cellNumber))
                iv.setImageResource(R.drawable.corner_bottomtoleft_inside_red);
            else if (isCellNumberInArray(c.ARRAY_CORNER_BOTTOMTORIGHT_INSIDE, this.cellNumber))
                iv.setImageResource(R.drawable.corner_bottomtoright_inside_red);
            else
                iv.setImageResource(R.drawable.defaultimg_red);
        } else if (season.equals("ete")) {
            if (isCellNumberInArray(c.ARRAY_ARBRES_ROUGE, this.cellNumber))
                iv.setImageResource(R.drawable.arbrerouge_green);
            else if (isCellNumberInArray(c.ARRAY_ARBRES_BLEU, this.cellNumber))
                iv.setImageResource(R.drawable.arbrebleu_green);
            else if (isCellNumberInArray(c.ARRAY_ARBRES_VERT, this.cellNumber))
                iv.setImageResource(R.drawable.arbrevert_green);

            else if (isCellNumberInArray(c.ARRAY_CHEMIN_LEFT, this.cellNumber))
                iv.setImageResource(R.drawable.chemin_left_green);
            else if (isCellNumberInArray(c.ARRAY_CHEMIN_RIGHT, this.cellNumber))
                iv.setImageResource(R.drawable.chemin_right_green);
            else if (isCellNumberInArray(c.ARRAY_CHEMIN_TOP, this.cellNumber))
                iv.setImageResource(R.drawable.chemin_top_green);
            else if (isCellNumberInArray(c.ARRAY_CHEMIN_BOTTOM, this.cellNumber))
                iv.setImageResource(R.drawable.chemin_bottom_green);

            else if (isCellNumberInArray(c.ARRAY_TERRAIN_FEUILLES, this.cellNumber))
                iv.setImageResource(R.drawable.terrainfeuilles_green);

            else if (isCellNumberInArray(c.ARRAY_CORNER_TOPTOLEFT_OUTSIDE, this.cellNumber))
                iv.setImageResource(R.drawable.corner_toptoleft_outside_green);
            else if (isCellNumberInArray(c.ARRAY_CORNER_TOPTORIGHT_OUTSIDE, this.cellNumber))
                iv.setImageResource(R.drawable.corner_toptoright_outside_green);
            else if (isCellNumberInArray(c.ARRAY_CORNER_BOTTOMTOLEFT_OUTSIDE, this.cellNumber))
                iv.setImageResource(R.drawable.corner_bottomtoleft_outside_green);
            else if (isCellNumberInArray(c.ARRAY_CORNER_BOTTOMTORIGHT_OUTSIDE, this.cellNumber))
                iv.setImageResource(R.drawable.corner_bottomtoright_outside_green);

            else if (isCellNumberInArray(c.ARRAY_CORNER_TOPTOLEFT_INSIDE, this.cellNumber))
                iv.setImageResource(R.drawable.corner_toptoleft_inside_green);
            else if (isCellNumberInArray(c.ARRAY_CORNER_TOPTORIGHT_INSIDE, this.cellNumber))
                iv.setImageResource(R.drawable.corner_toptoright_inside_green);
            else if (isCellNumberInArray(c.ARRAY_CORNER_BOTTOMTOLEFT_INSIDE, this.cellNumber))
                iv.setImageResource(R.drawable.corner_bottomtoleft_inside_green);
            else if (isCellNumberInArray(c.ARRAY_CORNER_BOTTOMTORIGHT_INSIDE, this.cellNumber))
                iv.setImageResource(R.drawable.corner_bottomtoright_inside_green);
            else
                iv.setImageResource(R.drawable.defaultimg_green);
        } else if (season.equals("hiver")) {
            if (isCellNumberInArray(c.ARRAY_ARBRES_ROUGE, this.cellNumber))
                iv.setImageResource(R.drawable.arbrerouge_blue);
            else if (isCellNumberInArray(c.ARRAY_ARBRES_BLEU, this.cellNumber))
                iv.setImageResource(R.drawable.arbrebleu_blue);
            else if (isCellNumberInArray(c.ARRAY_ARBRES_VERT, this.cellNumber))
                iv.setImageResource(R.drawable.arbrevert_blue);

            else if (isCellNumberInArray(c.ARRAY_CHEMIN_LEFT, this.cellNumber))
                iv.setImageResource(R.drawable.chemin_left_blue);
            else if (isCellNumberInArray(c.ARRAY_CHEMIN_RIGHT, this.cellNumber))
                iv.setImageResource(R.drawable.chemin_right_blue);
            else if (isCellNumberInArray(c.ARRAY_CHEMIN_TOP, this.cellNumber))
                iv.setImageResource(R.drawable.chemin_top_blue);
            else if (isCellNumberInArray(c.ARRAY_CHEMIN_BOTTOM, this.cellNumber))
                iv.setImageResource(R.drawable.chemin_bottom_blue);

            else if (isCellNumberInArray(c.ARRAY_TERRAIN_FEUILLES, this.cellNumber))
                iv.setImageResource(R.drawable.terrainfeuilles_blue);

            else if (isCellNumberInArray(c.ARRAY_CORNER_TOPTOLEFT_OUTSIDE, this.cellNumber))
                iv.setImageResource(R.drawable.corner_toptoleft_outside_blue);
            else if (isCellNumberInArray(c.ARRAY_CORNER_TOPTORIGHT_OUTSIDE, this.cellNumber))
                iv.setImageResource(R.drawable.corner_toptoright_outside_blue);
            else if (isCellNumberInArray(c.ARRAY_CORNER_BOTTOMTOLEFT_OUTSIDE, this.cellNumber))
                iv.setImageResource(R.drawable.corner_bottomtoleft_outside_blue);
            else if (isCellNumberInArray(c.ARRAY_CORNER_BOTTOMTORIGHT_OUTSIDE, this.cellNumber))
                iv.setImageResource(R.drawable.corner_bottomtoright_outside_blue);

            else if (isCellNumberInArray(c.ARRAY_CORNER_TOPTOLEFT_INSIDE, this.cellNumber))
                iv.setImageResource(R.drawable.corner_toptoleft_inside_blue);
            else if (isCellNumberInArray(c.ARRAY_CORNER_TOPTORIGHT_INSIDE, this.cellNumber))
                iv.setImageResource(R.drawable.corner_toptoright_inside_blue);
            else if (isCellNumberInArray(c.ARRAY_CORNER_BOTTOMTOLEFT_INSIDE, this.cellNumber))
                iv.setImageResource(R.drawable.corner_bottomtoleft_inside_blue);
            else if (isCellNumberInArray(c.ARRAY_CORNER_BOTTOMTORIGHT_INSIDE, this.cellNumber))
                iv.setImageResource(R.drawable.corner_bottomtoright_inside_blue);
            else
                iv.setImageResource(R.drawable.defaultimg_blue);
        }
        this.iv = iv;
    }

    public boolean isCellNumberInArray(int tab[], int cellNumber) {
        int compt = 0;
        while (compt < tab.length && cellNumber != tab[compt]) compt++;
        return compt < tab.length;
    }





    public ImageView getIv() {
        return iv;
    }

    public void setIv(ImageView iv) {
        this.iv = iv;
    }

    public int getCellNumber() {
        return cellNumber;
    }

    public void setCellNumber(int cellNumber) {
        this.cellNumber = cellNumber;
    }

    public boolean isEmptyCell() {
        return emptyCell;
    }

    public void setEmptyCell(boolean emptyCell) {
        this.emptyCell = emptyCell;
    }

    public String getCellType() {
        return cellType;
    }

    public void setCellType(String cellType) {
        this.cellType = cellType;
    }

    public String getCellImg() {
        return cellImg;
    }

    public void setCellImg(String cellImg) {
        this.cellImg = cellImg;
    }

    public Context getCtx() {
        return ctx;
    }

    public void setCtx(Context ctx) {
        this.ctx = ctx;
    }

    public int getCoordY() {
        return coordY;
    }

    public void setCoordY(int coordY) {
        this.coordY = coordY;
    }

    public int getCoordX() {
        return coordX;
    }

    public void setCoordX(int coordX) {
        this.coordX = coordX;
    }

    @Override
    public String toString() {
        return "GameCell{" +
                "cellNumber=" + cellNumber +
                ", ctx=" + ctx +
                ", iv=" + iv +
                ", emptyCell=" + emptyCell +
                ", cellType='" + cellType + '\'' +
                ", cellImg='" + cellImg + '\'' +
                ", coordY=" + coordY +
                ", coordX=" + coordX +
                '}';
    }
}
