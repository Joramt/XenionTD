package com.example.ecole.xeniontd.entities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.ecole.xeniontd.R;
import com.example.ecole.xeniontd.Views.TowerView;
import com.example.ecole.xeniontd.utils.c;

import java.util.ArrayList;

public class GestureListener extends GestureDetector.SimpleOnGestureListener {

    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    private Context ctx;
    private Game currentGame;
    private GameCell board[][];
    private boolean isTowerMenuOpen;
    private int tempTowerStyle;
    private int tempTowerTier;
    private ImageView btnImg;
    private RelativeLayout rootLayout;
    private TowerView towerView;
    private int cellWidth;
    private int cellHeight;


    public GestureListener(Context ctx, Game currentGame, GameCell board[][], RelativeLayout rootLayout, ArrayList<String> towerCaseNumber, Handler hand) {
        this.ctx = ctx;
        this.currentGame = currentGame;
        this.board = board;
        this.rootLayout = rootLayout;
        this.cellWidth = ctx.getResources().getDisplayMetrics().widthPixels / 18;
        this.cellHeight = ctx.getResources().getDisplayMetrics().heightPixels / 11;
        this.towerView = new TowerView(ctx, cellWidth, cellHeight, hand);

        if(towerCaseNumber.size() > 0) {

            towerView.resetTowers(towerCaseNumber);

        }
        rootLayout.addView(towerView);

    }
    // methode qui sert a s'assurer que l'on est pas dans le chemin
    private boolean canBuildTower(int cellNumber) {
        boolean retour = true;
        for (int test = 0; test < c.NO_TOWERS_ALLOWED.length; test++) {
            if (cellNumber == c.NO_TOWERS_ALLOWED[test]) {
                retour = false;
            }
        }
        return retour;
    }


    @Override
    public boolean onSingleTapUp(MotionEvent e) {

        int column =(int) (e.getX() / cellWidth);
        int line = (int) (e.getY() /  cellHeight);
        //Log.d("test", "column " + column + " et line" + line);
        final GameCell cell = board[line][column];
        if(canBuildTower(cell.getCellNumber()))
            this.towerView.highlightCell(cell);
        else
        towerView.clear();

        currentGame.findMonsterByCell(column, line);
        //Toast.makeText(ctx, "Je suis" + cell.getCellNumber(), Toast.LENGTH_SHORT).show();



        //*************************************************************TOWERS***************************************************

        // boutons a rajouter sur les differents menus
        Button addTowerBtnTower1 = new Button(ctx);
        Button addTowerBtnTower2 = new Button(ctx);
        Button addTowerBtnCancel = new Button(ctx);
        Button addTowerBtnUpgrade1 = new Button(ctx);
        Button addTowerBtnUpgrade2 = new Button(ctx);
        Button addTowerBtnRefund = new Button(ctx);
        Button addTowerBtnUpgradeFinal = new Button(ctx);
        final RelativeLayout gameLayout = currentGame.getGameLayout();
        currentGame.setTowerView(towerView);
        // menu 1 pour ajouter nouvelles tours
        final LinearLayout towerMenu = (LinearLayout) rootLayout.findViewById(R.id.towerMenu);


        addTowerBtnTower1.setText("Archer Tower");
        addTowerBtnTower2.setText("Fire Tower");
        //addTowerBtnUpgrade1.setText("Crossbow Tower");
        //addTowerBtnUpgrade2.setText("Magic Tower");
        //addTowerBtnUpgradeFinal.setText("Ultimate Tower");
        addTowerBtnCancel.setText("Cancel");
        addTowerBtnRefund.setText("Refund");

        final Tower t = new Tower(currentGame.getHand(),currentGame.getCtx(), currentGame.getGameLayout(), cellHeight, cellWidth);



        towerView.getLayoutParams().width = ctx.getResources().getDisplayMetrics().widthPixels;
        towerView.getLayoutParams().width = ctx.getResources().getDisplayMetrics().widthPixels;



        // DEFINITION DES ONCLICKS POUR CHACUN DES BOUTONS
        addTowerBtnTower1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // changement de l'image, ajout de la tower, gestion de l'argent
                // btnImg.setImageResource(R.drawable.basic1);
                Bitmap towerImg = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.basic1);
                t.setTowerImg(towerImg);
                t.setPosX(cell.getCoordX());
                t.setPosY(cell.getCoordY());
                t.setTier(1);
                t.setStyle(1);
                t.setCaseNumber(cell.getCellNumber());
                t.setRange(2);
                t.setDmg(30);
                t.setCashInvesti(50);
                t.setFPS(45);
                towerView.addTower(t);
                cell.setEmptyCell(false);

                /*cell.setIv(btnImg);


                currentGame.addTower(cell.getCellNumber(), 1, 1);       // le cellNumber devient le Id de la nouvelle tower
                currentGame.setNbCash(String.valueOf(Integer.parseInt(currentGame.getNbCash()) + 1));*/
                //Toast.makeText(ctx, "You've purchased a " + /*nom de la tour*/ "tower for", Toast.LENGTH_SHORT).show();


                // on referme le menu
                towerMenu.removeAllViews();
                isTowerMenuOpen = false;
            }
        });
        addTowerBtnTower2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* btnImg.setImageResource(R.drawable.basic2);
                cell.setIv(btnImg);
                cell.setEmptyCell(false);

                currentGame.addTower(cell.getCellNumber(), 1, 2);
                currentGame.setNbCash(String.valueOf(Integer.parseInt(currentGame.getNbCash()) + 1));*/
                //Toast.makeText(ctx, "You've purchased a " + /*nom de la tour*/ "tower for", Toast.LENGTH_SHORT).show();

                Bitmap towerImg = BitmapFactory.decodeResource(ctx.getResources(),R.drawable.basic2);
                t.setTowerImg(towerImg);
                t.setPosX(cell.getCoordX());
                t.setPosY(cell.getCoordY());
                t.setTier(1);
                t.setStyle(2);
                t.setCaseNumber(cell.getCellNumber());
                t.setRange(2);
                t.setDmg(40);
                t.setFPS(45);
                t.setCashInvesti(50);
                towerView.addTower(t);
                cell.setEmptyCell(false);

                towerMenu.removeAllViews();
                isTowerMenuOpen = false;
            }
        });
        addTowerBtnUpgrade1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // le nouveau choix depends de lancienne tour
                Tower t = towerView.findTowerByCaseNumber(cell.getCellNumber());

                Bitmap towerImg;
                switch (t.getTier()) {

                    case 1:
                        //btnImg.setImageResource(R.drawable.upgrade11);
                        if(t.getStyle() == 1) {
                            towerImg = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.upgrade11);
                            t.setTowerImg(towerImg);
                            t.setStyle(11);
                            towerView.upgradeTower(t, 2, 55);

                            t.setCashInvesti(t.getCashInvesti() + 100);
                        }

                        else if(t.getStyle() == 2){
                            towerImg = BitmapFactory.decodeResource(ctx.getResources(),R.drawable.upgrade21);

                            t.setTowerImg(towerImg);
                            t.setStyle(21);
                            towerView.upgradeTower(t, 2, 55);
                            t.setCashInvesti(t.getCashInvesti() + 120);
                        }

                        t.setFPS(35);


                        /*cell.setIv(btnImg);
                        currentGame.addTower(cell.getCellNumber(), 2, 11);*/
                        //Toast.makeText(ctx, "You've upgraded your tower to a /*nom de la tour*/", Toast.LENGTH_SHORT).show();

                        break;

                    case 2:
                        //btnImg.setImageResource(R.drawable.upgrade21);
                        towerImg = BitmapFactory.decodeResource(ctx.getResources(),R.drawable.upgrade21);
                        t.setTowerImg(towerImg);
                        t.setStyle(21);
                        towerView.upgradeTower(t, 3, 75);
                        t.setFPS(25);
                        t.setCashInvesti(t.getCashInvesti() + 200);
                       /* btnImg.setImageResource(R.drawable.upgrade21);
                        cell.setIv(btnImg);
                        currentGame.addTower(cell.getCellNumber(), 2, 21);*/
                        //Toast.makeText(ctx, "You've upgraded your tower to a /*nom de la tour*/", Toast.LENGTH_SHORT).show();
                        break;
                }
                towerMenu.removeAllViews();
                isTowerMenuOpen = false;
            }
        });
        addTowerBtnUpgrade2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tower t = towerView.findTowerByCaseNumber(cell.getCellNumber());
                Bitmap towerImg;
                // le nouveau choix depends de lancienne tour
                switch (t.getTier()) {

                    case 1:
                        //btnImg.setImageResource(R.drawable.upgrade12);
                        if(t.getStyle() == 1) {
                            towerImg = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.upgrade12);
                            t.setTowerImg(towerImg);
                            t.setStyle(12);
                            towerView.upgradeTower(t, 2, 62);
                            t.setCashInvesti(t.getCashInvesti() + 90);
                        }
                        else if(t.getStyle() == 2){
                            towerImg = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.upgrade22);
                            t.setTowerImg(towerImg);
                            t.setStyle(22);
                            towerView.upgradeTower(t, 2, 62);
                            t.setCashInvesti(t.getCashInvesti() + 130);
                        }
                        t.setFPS(35);
                        /*btnImg.setImageResource(R.drawable.upgrade12);
                        currentGame.addTower(cell.getCellNumber(), 2, 12);*/
                        //Toast.makeText(ctx, "You've upgraded your tower to a /*nom de la tour*/", Toast.LENGTH_SHORT).show();
                        break;

                    case 2:
                        //btnImg.setImageResource(R.drawable.upgrade22);
                        towerImg = BitmapFactory.decodeResource(ctx.getResources(),R.drawable.upgrade11);
                        t.setTowerImg(towerImg);
                        t.setStyle(22);
                        t.setFPS(25);
                        towerView.upgradeTower(t, 3, 70);
                        t.setCashInvesti(t.getCashInvesti() + 220);
                        /*btnImg.setImageResource(R.drawable.upgrade22);
                        currentGame.addTower(cell.getCellNumber(), 2, 22);*/
                        //Toast.makeText(ctx, "You've upgraded your tower to a /*nom de la tour*/", Toast.LENGTH_SHORT).show();
                        break;
                }
                //cell.setIv(btnImg);

                towerMenu.removeAllViews();
                isTowerMenuOpen = false;
            }
        });
        addTowerBtnUpgradeFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tower t = towerView.findTowerByCaseNumber(cell.getCellNumber());
                Bitmap towerImg;
                // le nouveau choix depends de lancienne tour
                switch (t.getStyle()) {

                    case 11:
                        //btnImg.setImageResource(R.drawable.upgrade12);
                        //TO DO : CHANGER L'IMAGE
                        towerImg = BitmapFactory.decodeResource(ctx.getResources(),R.drawable.upgrade111);
                        t.setTowerImg(towerImg);
                        t.setStyle(111);
                        t.setCashInvesti(t.getCashInvesti() + 450);
                        towerView.upgradeTower(t,4,105);
                        /*
                        btnImg.setImageResource(R.drawable.upgrade12);
                        currentGame.addTower(cell.getCellNumber(), 2, 120);*/
                        //Toast.makeText(ctx, "You've upgraded your tower to a /*nom de la tour*/", Toast.LENGTH_SHORT).show();
                        break;

                    case 12:
                        //btnImg.setImageResource(R.drawable.upgrade12);
                        //TO DO : CHANGER L'IMAGE
                        towerImg = BitmapFactory.decodeResource(ctx.getResources(),R.drawable.upgrade112);
                        t.setTowerImg(towerImg);
                        t.setStyle(112);
                        t.setCashInvesti(t.getCashInvesti() + 480);
                        towerView.upgradeTower(t, 4, 80);

                        /*btnImg.setImageResource(R.drawable.upgrade22);
                        currentGame.addTower(cell.getCellNumber(), 2, 220);*/
                        //Toast.makeText(ctx, "You've upgraded your tower to a /*nom de la tour*/", Toast.LENGTH_SHORT).show();
                        break;
                    case 21:
                        towerImg = BitmapFactory.decodeResource(ctx.getResources(),R.drawable.upgrade211);
                        t.setTowerImg(towerImg);
                        t.setStyle(211);
                        t.setCashInvesti(t.getCashInvesti() + 470);
                        towerView.upgradeTower(t, 4, 105);/*
                        btnImg.setImageResource(R.drawable.upgrade12);
                        currentGame.addTower(cell.getCellNumber(), 2, 120);*/
                        //Toast.makeText(ctx, "You've upgraded your tower to a /*nom de la tour*/", Toast.LENGTH_SHORT).show();
                        break;

                    case 22:
                        towerImg = BitmapFactory.decodeResource(ctx.getResources(),R.drawable.upgrade212);
                        t.setTowerImg(towerImg);
                        t.setStyle(222);
                        t.setCashInvesti(t.getCashInvesti() + 460);
                        towerView.upgradeTower(t, 4, 250);

                        /*btnImg.setImageResource(R.drawable.upgrade22);
                        currentGame.addTower(cell.getCellNumber(), 2, 220);*/
                        //Toast.makeText(ctx, "You've upgraded your tower to a /*nom de la tour*/", Toast.LENGTH_SHORT).show();
                        break;
                }
                //t.setTier(3);
                t.setFPS(15);
                cell.setIv(btnImg);
                towerMenu.removeAllViews();
                isTowerMenuOpen = false;
            }
        });
        addTowerBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                towerMenu.removeAllViews();
                isTowerMenuOpen = false;
                towerView.clearAll();
            }
        });
        addTowerBtnRefund.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                cell.setEmptyCell(true);

                // on remets la case vierge dependemment de la saison
                switch (currentGame.getSeason()) {
                    case "automne":
                        btnImg.setImageResource(R.drawable.defaultimg_red);
                        break;
                    case "hiver":
                        btnImg.setImageResource(R.drawable.defaultimg_blue);
                        break;
                    case "ete":
                        btnImg.setImageResource(R.drawable.defaultimg_green);
                        break;
                }
                cell.setIv(btnImg);
                Tower t = towerView.findTowerByCaseNumber(cell.getCellNumber());
                currentGame.setNbCash(String.valueOf((t.getCashInvesti() / 2)));            // a modifier
                towerView.removeTower(t);
                Toast.makeText(ctx, "You've refunded your tower for ", Toast.LENGTH_SHORT).show();
                cell.setEmptyCell(true);
                towerMenu.removeAllViews();
                isTowerMenuOpen = false;
            }
        });



        // on ferme tout menu qui aurait ete ouvert au prealable


            // avant d'afficher le menu on verifie que la cell ne fait pas partie du chemin
            if (canBuildTower(cell.getCellNumber())) {
                Log.d("YOOOO", "is tower menu open" + isTowerMenuOpen);
                if (isTowerMenuOpen) {

                    isTowerMenuOpen = false;
                    towerMenu.removeAllViews();
                }


                //on ouvre le menu et on recupere le imageView
                isTowerMenuOpen = true;
                btnImg = cell.getIv();

                // maintenant on regarde si la case possede deja une tour
                if (cell.isEmptyCell()) {

                    //ajout des boutons concernant les cases vides
                    towerMenu.addView(addTowerBtnTower1);
                    towerMenu.addView(addTowerBtnTower2);
                    towerMenu.addView(addTowerBtnCancel);

                // nous sommes sur une case occupee par une tour
                } else {

                    //on garde en memoire le style/tier de la tour, puis on la supprime
                    /*tempTowerStyle = currentGame.getTowerById(cell.getCellNumber()).getStyle();
                    tempTowerTier = currentGame.getTowerById(cell.getCellNumber()).getTier();
                    currentGame.removeTower(cell.getCellNumber());*/
                    Tower tow = towerView.findTowerByCaseNumber(cell.getCellNumber());
                    towerView.drawRange(tow);

                    // nous regardons d'abord de quelle tier est la tour
                    switch(tow.getTier()) {
                        case 1:
                            // on rajoute les boutons concernant les tower t1
                            addTowerBtnUpgrade1.setText("Crossbow Tower");
                            addTowerBtnUpgrade2.setText("Elite Archer Tower");

                            if(tow.getStyle() > 1) {
                                addTowerBtnUpgrade1.setText("Light Tower");
                                addTowerBtnUpgrade2.setText("Dark Tower");
                            }
                            towerMenu.addView(addTowerBtnUpgrade1);
                            towerMenu.addView(addTowerBtnUpgrade2);
                            break;

                        case 2:
                            // boutons concernant les tower t2
                            addTowerBtnUpgradeFinal.setText("Elite Canon Tower");

                            if (tow.getStyle() == 12)
                                addTowerBtnUpgradeFinal.setText("Elite Archer Tower");

                            else if (tow.getStyle() == 21)
                                addTowerBtnUpgradeFinal.setText("Pure Energy Tower");
                            else if (tow.getStyle() == 22)
                                addTowerBtnUpgradeFinal.setText("Dark Matter Tower");

                            towerMenu.addView(addTowerBtnUpgradeFinal);
                            break;

                        case 3:
                            // on ne peux ameliorer les t3
                            break;
                    }
                    // options refund et cancel
                    towerMenu.addView(addTowerBtnRefund);
                    towerMenu.addView(addTowerBtnCancel);
                }
            } else {
                //lorsque l'on clique sur le chemin
                //Toast.makeText(ctx, "No actions possible", Toast.LENGTH_SHORT).show();
            }

        //

        return false;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
            Toast.makeText(ctx, "Right to left ", Toast.LENGTH_SHORT).show();
            currentGame.slide(c.OPEN);
            return false; // Right to left
        } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
            Toast.makeText(ctx, "Left to right ", Toast.LENGTH_SHORT).show();
            currentGame.slide(c.CLOSE);
            return false; // Left to right
        }

        if (e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
            return false; // Bottom to top
        } else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
            return false; // Top to bottom
        }
        return false;
    }


}
