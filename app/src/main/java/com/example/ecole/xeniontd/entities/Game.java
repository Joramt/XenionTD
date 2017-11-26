package com.example.ecole.xeniontd.entities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ecole.xeniontd.R;
import com.example.ecole.xeniontd.Views.Monsters;
import com.example.ecole.xeniontd.Views.TowerView;
import com.example.ecole.xeniontd.utils.c;

import java.util.ArrayList;

public class Game extends View {

    private GameCell board[][];
    private Context ctx;
    private RelativeLayout rootLayout;

    private View mainView;
    private ImageView heartView;

    private ArrayList<Monsters> tabMonstres;
    private Bitmap[] monstresBmp;

    private LinearLayout mainLayout;
    private RelativeLayout monsterLayout;

    private String season;

    private LinearLayout gameMenu;
    private RelativeLayout gameLayout;
    private RelativeLayout.LayoutParams gameMenuParams;
    private LinearLayout progressBar;
    private boolean isMenuClosed;

    private TextView cashLbl;
    private TextView monsterLbl;
    private TextView vieLbl;
    private TextView roundLbl;

    private String nbCash;
    private int nbMonstre;
    private int nbVies;

    private ArrayList<Tower> mesTours;              // AJOUT NICOLAS
    private TowerView towerView;
    private LinearLayout towerMenu;

    private int round;
    private int roundDuration;
    private Handler monsterHand;

    private int cellWidth;
    private int cellHeight;
    CustomProgressBar customPGB;
    boolean restart;
    private ArrayList<String>  towerCaseNumber;


    public Game(LinearLayout mainLayout, Context ctx, View mainView, String season, RelativeLayout rootLayout, String nbCash, int nbVies, int round, ArrayList<String> towerCaseNumber) {
        super(ctx);
        this.mainLayout = mainLayout;
        this.ctx = ctx;
        this.mainView = mainView;
        this.season = season;
        this.rootLayout = rootLayout;
        this.nbCash = nbCash;
        this.nbMonstre = 20;
        this.nbVies = nbVies;
        this.tabMonstres = new ArrayList<>(20);
        this.monstresBmp = new Bitmap[5];
        this.monsterLayout = new RelativeLayout(ctx);
        this.heartView = new ImageView(ctx);
        heartView.setImageResource(R.drawable.heart30);
        configureHandler();
        setMonstresImg();

        this.mesTours = new ArrayList<Tower>();    // AJOUT NICOLAS
        this.roundDuration = 5000;
        this.round = round;
        this.towerCaseNumber = towerCaseNumber;
        this.cellWidth = (ctx.getResources().getDisplayMetrics().widthPixels / 18);
        this.cellHeight = (ctx.getResources().getDisplayMetrics().heightPixels / 11);

        this.towerView = new TowerView(ctx, cellWidth, cellHeight, monsterHand);
        this.restart = true;

        //on rajoute la vue menu



    }

    private void configureHandler() {
        monsterHand = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                //TODO: Handle different types of messages
                EntityStatus entitiesStatus = (EntityStatus) msg.obj;

                if (entitiesStatus.getEntityState().equals(c.ENTITY_STATE_CODE_DEATH)) {

                    nbMonstre--;
                    customPGB.setNbMonstre(nbMonstre);

                    if (entitiesStatus.getEntityCode() == c.MONSTER_FINISHED) {
                        setNbVies(getNbVies() - 1);
                    } else if (entitiesStatus.getEntityCode() == c.MONSTER_DEAD)
                        setNbCash(String.valueOf(Integer.parseInt(getNbCash()) + 50));

                    monsterLayout.removeView(tabMonstres.get(entitiesStatus.getMonsterID()));
                    monsterHand.removeCallbacks(tabMonstres.get(entitiesStatus.getMonsterID()));

                    if (nbMonstre == 0) {
                        round++;
                        nbMonstre = 20;
                        resetMonstres();
                    }
                    updateGameMenu();
                }
                else if(entitiesStatus.getEntityState().equals(c.ENTITY_CODE_POSITION)){
                    //Log.d("d","Le montre " + status[1] + "est a la case" + status[2]);
                    towerView.addMonstrePosition(
                            String.valueOf(entitiesStatus.getMonsterID()),
                            String.valueOf(entitiesStatus.getEntityCode())
                            );
                }
                else if ( entitiesStatus.getEntityType().equals(c.ENTITY_TYPE_TOUR)){
                    //Log.d("d","Le monstre " + status[2] + "est il dans le range de la tour " + status[3] + " ? " + status[1]);
                    if(String.valueOf(entitiesStatus.getEntityState()).equals("true")){
                        Monsters m = tabMonstres.get(entitiesStatus.getMonsterID());
                        Tower t = towerView.findTowerById(entitiesStatus.getTowerID());
                        //Log.d("haha","La tour " + t.getId() + "Tire sur le monstre numero " + m.getIdMonster());
                        if(m.isAlive())
                            t.fire(m);

                    }
                }
            }
        };

    }

    private void resetMonstres() {

        for (Monsters m : tabMonstres) {
            m.setInitalLifePoints(60 + 40 * round);
            m.setCurrentLifePoints(60 + 40 * round);
            m.setCurrentFrame(1);
            m.setWalk(0);
            m.setDirectionX(m.getWalk());
            m.setIsAlive(true);
            m.setIsOnField(false);
            m.setIsFinished(false);
            m.setxAxis((ctx.getResources().getDisplayMetrics().widthPixels / 18) * 9 - (ctx.getResources().getDisplayMetrics().widthPixels / 36));
            m.setyAxis((ctx.getResources().getDisplayMetrics().heightPixels / 11) * -1);
            m.setMonsterImg(monstresBmp[(int) (Math.random() * 5)]);
            customPGB.setNbMonstre(20);
        }
        spawnMonstres(roundDuration);
    }

    private void setMonstres() {
        if (restart) {
            GameCell cellDepart = getCellByNumber(10);
            for (int i = 0; i < nbMonstre; i++) {
                tabMonstres.add(new Monsters(ctx, i, cellDepart.getCoordX(), cellDepart.getCoordY(), monsterHand, monstresBmp[(int) (Math.random() * 5)], 100, towerView));
            }
        }


    }

    private void setMonstresImg() {

        monstresBmp[0] = BitmapFactory.decodeResource(getResources(), R.drawable.fire);
        monstresBmp[1] = BitmapFactory.decodeResource(getResources(), R.drawable.aqua);
        monstresBmp[2] = BitmapFactory.decodeResource(getResources(), R.drawable.darkghost);
        monstresBmp[3] = BitmapFactory.decodeResource(getResources(), R.drawable.evil);
        monstresBmp[4] = BitmapFactory.decodeResource(getResources(), R.drawable.orc);
    }

    public void slide(boolean isClosing) {
        TranslateAnimation animate;
        Float openingWidth = 300 * ctx.getResources().getDisplayMetrics().density;
        RelativeLayout gameLayout = (RelativeLayout) mainView.findViewById(R.id.gameLayout);

        if (!isClosing && isMenuClosed) {
            isMenuClosed = false;
            animate = new TranslateAnimation(0, openingWidth, gameMenuParams.height, gameMenuParams.height);
            animate.setDuration(1000);
            gameLayout.removeView(gameMenu);

        } else if (isClosing && !isMenuClosed) {
            isMenuClosed = true;
            animate = new TranslateAnimation(openingWidth, 0, gameMenuParams.height, gameMenuParams.height);
            animate.setDuration(500);

            gameLayout.addView(gameMenu);
        } else
            return;

        animate.setFillAfter(true);
        gameMenu.startAnimation(animate);
    }

    public void createBoard(int nbLigne, int nbColonne) {

        this.board = new GameCell[nbLigne][nbColonne];
        int cellNumber = 1;
        ImageView iv;

        for (int i = 0; i < nbLigne; i++) {
            LinearLayout tempLayout = new LinearLayout(ctx);
            tempLayout.setOrientation(LinearLayout.HORIZONTAL);


            for (int j = 0; j < nbColonne; j++) {
                final int p = cellNumber;
                GameCell tempCell = new GameCell(cellNumber, ctx, (i * cellWidth), (j * cellHeight));
                tempCell.configureCell(this.getSeason());

                iv = tempCell.getIv();

                this.board[i][j] = tempCell;
                cellNumber++;
                tempLayout.addView(iv);
                iv.getLayoutParams().width = cellWidth;
                iv.getLayoutParams().height = cellHeight;
                iv.setScaleType(ImageView.ScaleType.FIT_XY);
            }
            mainLayout.addView(tempLayout);
        }

    }

    public GameCell getCellByNumber(int cellNumber) {
        int x = board[0].length;
        int y = board.length;
        GameCell retour = new GameCell(0, ctx, 0, 0);

        for (int i = 0; i < y; i++)
            for (int j = 0; j < x; j++)
                if (board[i][j].getCellNumber() == cellNumber) {
                    retour = board[i][j];
                    break;
                }


        return retour;
    }

    public Context getCtx() {
        return ctx;
    }

    public void setCtx(Context ctx) {
        this.ctx = ctx;
    }

    public LinearLayout getMainLayout() {
        return mainLayout;
    }

    public void setMainLayout(LinearLayout mainLayout) {
        this.mainLayout = mainLayout;
    }

    public LinearLayout getGameMenu() {
        return gameMenu;
    }

    public void setGameMenu(RelativeLayout gameLayout, LinearLayout gameMenu) {
        this.gameMenu = gameMenu;
        this.gameLayout = gameLayout;
        this.gameMenuParams = (RelativeLayout.LayoutParams) gameMenu.getLayoutParams();
        this.isMenuClosed = true;

        LinearLayout cashInfo = new LinearLayout(ctx);
        LinearLayout monsterInfo = new LinearLayout(ctx);
        LinearLayout vieInfo = new LinearLayout(ctx);
        LinearLayout roundInfo = new LinearLayout(ctx);

        ImageView cashImg = new ImageView(ctx);
        ImageView monsterImg = new ImageView(ctx);
        ImageView vieImg = new ImageView(ctx);
        ImageView roundImg = new ImageView(ctx);

        cashLbl = new TextView(ctx);
        monsterLbl = new TextView(ctx);
        vieLbl = new TextView(ctx);
        roundLbl = new TextView(ctx);

        cashImg.setImageResource(R.drawable.coin);
        monsterImg.setImageResource(R.drawable.monster);
        vieImg.setImageResource(R.drawable.heart30);
        roundImg.setImageResource(R.drawable.timer);

        addToMenu(gameMenu, cashInfo, cashImg, cashLbl);
        addToMenu(gameMenu, monsterInfo, monsterImg, monsterLbl);
        addToMenu(gameMenu, vieInfo, vieImg, vieLbl);
        addToMenu(gameMenu, roundInfo, roundImg, roundLbl);


        updateGameMenu();
    }


    public void setMenuInteraction() {
        slide(c.CLOSE);
        final GestureDetector gdt = new GestureDetector(new GestureListener(ctx, this, this.board, this.rootLayout, this.towerCaseNumber, this.monsterHand));

        mainView.findViewById(R.id.gameLayout).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(final View view, final MotionEvent event) {
                gdt.onTouchEvent(event);
                return true;
            }
        });


    }


    public void spawnMonstres(int roundPrep) {

        setMonstres();
        int delay = roundPrep;
        Log.d("dede","test" + tabMonstres.size());
        for (final Monsters m : tabMonstres) {
            monsterLayout.addView(m);

            m.setIsOnField(false);
            m.setIsAlive(true);
            m.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
            m.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
            monsterHand.postDelayed(m, delay);
            delay += 2200;
        }
        if (restart) {
            this.rootLayout.addView(monsterLayout);
            this.rootLayout.addView(heartView);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            params.addRule(RelativeLayout.CENTER_HORIZONTAL);
            params.setMargins((int) (23 * ctx.getResources().getDisplayMetrics().density), (int) (10 * ctx.getResources().getDisplayMetrics().density), 0, 0);
            heartView.setLayoutParams(params);
            monsterLayout.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
            monsterLayout.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
            setProgressBar();
            this.restart = false;
        }

    }


    public void emptyMonstres() {
        for (Monsters m : tabMonstres) {
            m.setIsOnField(false);
            m.setIsAlive(false);
            monsterLayout.removeView(m);

        }
    }

    public void killMonstreById(int monstreId) {

        Monsters monstreATuer = tabMonstres.get(monstreId);
        monstreATuer.setIsAlive(false);
        monstreATuer.setIsOnField(false);
        monsterLayout.removeView(monstreATuer);
        tabMonstres.remove(monstreATuer);
    }

    private void addToMenu(LinearLayout gameMenu, LinearLayout LLayout, ImageView newImg, TextView newTextView) {
        LLayout.addView(newImg);
        LLayout.addView(newTextView);
        gameMenu.addView(LLayout);

        LLayout.getLayoutParams().width = LinearLayout.LayoutParams.MATCH_PARENT;
        LLayout.getLayoutParams().height = (int) (90 * ctx.getResources().getDisplayMetrics().density);
        LLayout.setPadding(0, (int) (10 * ctx.getResources().getDisplayMetrics().density), 0, (int) (10 * ctx.getResources().getDisplayMetrics().density));
        LLayout.setOrientation(LinearLayout.VERTICAL);
        LLayout.setGravity(Gravity.CENTER_HORIZONTAL);

        newImg.getLayoutParams().width = LinearLayout.LayoutParams.WRAP_CONTENT;
        newImg.getLayoutParams().height = LinearLayout.LayoutParams.WRAP_CONTENT;

        newTextView.getLayoutParams().width = LinearLayout.LayoutParams.WRAP_CONTENT;
        newTextView.getLayoutParams().height = LinearLayout.LayoutParams.WRAP_CONTENT;
        newTextView.setTextSize(9 * ctx.getResources().getDisplayMetrics().density);
        newTextView.setTextColor(Color.parseColor("#FF10202E"));
    }

    public void updateGameMenu() {
        this.cashLbl.setText(this.getNbCash());
        this.monsterLbl.setText(String.valueOf(nbMonstre));
        this.vieLbl.setText(String.valueOf(nbVies));
        this.roundLbl.setText(String.valueOf(round));
    }

    public RelativeLayout.LayoutParams getGameMenuParams() {
        return gameMenuParams;
    }

    public void setGameMenuParams(RelativeLayout.LayoutParams gameMenuParams) {
        this.gameMenuParams = gameMenuParams;
    }

    public GameCell getCell(int nbLigne, int nbColonne) {
        return board[nbLigne][nbColonne];
    }

    public int getRoundDuration() {
        return roundDuration;
    }

    public void setRoundDuration(int roundDuration) {
        this.roundDuration = roundDuration;
    }

    public GameCell[][] getBoard() {
        return board;
    }

    public void setBoard(GameCell[][] board) {
        this.board = board;
    }

    public View getMainView() {
        return mainView;
    }

    public void setMainView(View mainView) {
        this.mainView = mainView;
    }

    public boolean isMenuClosed() {
        return isMenuClosed;
    }

    public void setIsMenuClosed(boolean isMenuClosed) {
        this.isMenuClosed = isMenuClosed;
    }

    public String getNbCash() {
        return nbCash;
    }

    public void setNbCash(String nbCash) {
        this.nbCash = nbCash;
        updateGameMenu();
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public TextView getCashLbl() {
        return cashLbl;
    }

    public void setCashLbl(TextView cashLbl) {
        this.cashLbl = cashLbl;
    }

    public TextView getMonsterLbl() {
        return monsterLbl;
    }

    public void setMonsterLbl(TextView monsterLbl) {
        this.monsterLbl = monsterLbl;
    }

    public int getNbMonstre() {
        return nbMonstre;
    }

    public void setNbMonstre(int nbMonstre) {
        this.nbMonstre = nbMonstre;
    }


    public void setGameMenu(LinearLayout gameMenu) {
        this.gameMenu = gameMenu;
    }

    public RelativeLayout getGameLayout() {
        return gameLayout;
    }

    public void setGameLayout(RelativeLayout gameLayout) {
        this.gameLayout = gameLayout;
    }

    //ajout nicolas
    public ArrayList<Tower> getMesTours() {
        return mesTours;
    }

    public void setMesTours(ArrayList<Tower> mesTours) {
        this.mesTours = mesTours;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    // methode
    public void removeTower(int id) {
        Tower maTour;
        for (int i = 0; i < mesTours.size(); i++) {
            if (id == mesTours.get(i).getId()) {
                maTour = mesTours.get(i);
                mesTours.remove(maTour);
            }
        }
    }

    public Tower getTowerById(int id) {
        Tower maTour;
        maTour = new Tower();
        for (int i = 0; i < mesTours.size(); i++) {
            if (id == mesTours.get(i).getId()) {
                maTour = mesTours.get(i);
            }
        }
        return maTour;
    }

    public void addTower(int id, int tier, int style) {
        Tower maTour = new Tower(id, tier, style);
        mesTours.add(maTour);
    }


    public ArrayList<Monsters> getTabMonstres() {
        return tabMonstres;
    }

    public void setTabMonstres(ArrayList<Monsters> tabMonstres) {
        this.tabMonstres = tabMonstres;
    }

    public Bitmap[] getMonstresBmp() {
        return monstresBmp;
    }

    public void setMonstresBmp(Bitmap[] monstresBmp) {
        this.monstresBmp = monstresBmp;
    }

    public Handler getHand() {
        return monsterHand;
    }

    public void setHand(Handler hand) {
        this.monsterHand = monsterHand;
    }

    public RelativeLayout getRootLayout() {
        return rootLayout;
    }

    public void setRootLayout(RelativeLayout rootLayout) {
        this.rootLayout = rootLayout;
    }

    public RelativeLayout getMonsterLayout() {
        return monsterLayout;
    }

    public void setMonsterLayout(RelativeLayout monsterLayout) {
        this.monsterLayout = monsterLayout;
    }

    public int getNbVies() {
        return nbVies;
    }

    public void setNbVies(int nbVies) {
        this.nbVies = nbVies;

        switch (nbVies) {
            case 25:
                heartView.setImageResource(R.drawable.heart25);
                break;
            case 20:
                heartView.setImageResource(R.drawable.heart20);
                break;
            case 15:
                heartView.setImageResource(R.drawable.heart15);
                break;
            case 10:
                heartView.setImageResource(R.drawable.heart10);
                break;
            case 5:
                heartView.setImageResource(R.drawable.heart5);
                break;
            case 0:
                heartView.setImageResource(R.drawable.heart0);
                break;
        }
    }

    public TextView getVieLbl() {
        return vieLbl;
    }

    public void setVieLbl(TextView vieLbl) {
        this.vieLbl = vieLbl;
    }

    public TowerView getTowerView() {
        return towerView;
    }

    public void setTowerView(TowerView towerView) {
        this.towerView = towerView;
    }

    public void pauseGame() {
        for (Monsters m : tabMonstres) {
            monsterHand.removeCallbacks(m);
        }
    }

    public void restartGame() {
        int delay = 2000;
        for (Monsters m : tabMonstres) {
            if (m.isOnField() && m.isAlive())
                monsterHand.post(m);
            else if (m.isAlive()) {
                monsterHand.postDelayed(m, delay);
                delay += 2000;
            }
        }
    }

    public void findMonsterByCell(int column, int line) {

        for (Monsters m : tabMonstres) {
            //Log.d("test", "Monste col : " + m.getColumn() + " line : " + m.getLine());
            //Log.d("test", "click col : " + column + " line : " + line);
            if ((m.getColumn() >= column - 1 && m.getColumn() <= column + 1) && (m.getLine() == line)) {

                //Toast.makeText(ctx,"touche",Toast.LENGTH_SHORT).show();
                //killMonstreById(m.getIdMonster());
                m.doDamage(30);

            }
        }
    }

    public void setProgressBar() {
        this.progressBar = new LinearLayout(ctx);
        this.rootLayout.addView(progressBar);
        progressBar.bringToFront();
        this.customPGB = new CustomProgressBar(ctx, progressBar, nbMonstre, cellWidth, cellHeight, mainView);


    }


    public boolean isRestart() {
        return restart;
    }

    public void setRestart(boolean restart) {
        this.restart = restart;
    }


    public LinearLayout getTowerMenu() {
        return towerMenu;
    }

    public void setTowerMenu(LinearLayout towerMenu) {
        this.towerMenu = towerMenu;
    }


}
