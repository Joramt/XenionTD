package com.example.ecole.xeniontd.activities;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;

import android.view.Menu;
import android.view.MenuItem;

import android.util.Log;

import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.ecole.xeniontd.R;
import com.example.ecole.xeniontd.entities.Game;
import com.example.ecole.xeniontd.entities.Tower;
import com.example.ecole.xeniontd.utils.c;

import java.util.ArrayList;

public class MainActivity extends Activity {
    Game currentGame;
    // TOUTES LES CONSTANTES SE TROUVENT DANS LE PCKG UTILS.C

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final LinearLayout mainLayout = (LinearLayout) findViewById(R.id.mainLayout);
        final LinearLayout gameMenu = (LinearLayout) findViewById(R.id.gameInfos);
        final LinearLayout towerMenu = (LinearLayout) findViewById(R.id.towerMenu);



        RelativeLayout gameLayout = (RelativeLayout) findViewById(R.id.gameLayout);
        final RelativeLayout rootLayout = (RelativeLayout) findViewById(R.id.rootLayout);
        final Context ctx = this;


        View mainView = getWindow().getDecorView().getRootView();
        View gameView = findViewById(R.id.gameLayout);


        // Code pour forcer le fullscreen et le mode landscape
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        String season = getIntent().getStringExtra(c.SEASON_KEY);

        final DisplayMetrics displayMetrics = getResources().getDisplayMetrics();

        // Obtention des dimensions de l'écran en DP
        int screenHeightDP = (int) (displayMetrics.heightPixels / displayMetrics.density);
        int screenWidthDP = (int) (displayMetrics.widthPixels / displayMetrics.density);


        // On divise le width et le height par la taille d'une cell ( w:64 h:64 ) afin d'obtenir le nombre requis de cells;
        int nbColonne = 18;
        int nbLigne = 11;

        ArrayList<String> towerCaseNumber = new ArrayList<>(0);
        currentGame = new Game(mainLayout, ctx, mainView, season, rootLayout,"0", 30, 1, towerCaseNumber);

        currentGame.createBoard(nbLigne, nbColonne);
        currentGame.setGameMenu(gameLayout, gameMenu);
        currentGame.setMenuInteraction();
        //currentGame.setProgressBar();
        currentGame.spawnMonstres(0);

    }

    @Override
    protected void onPause() {
        super.onPause();
        currentGame.pauseGame();
        saveGame();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
       currentGame.restartGame();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    ///////// omar /////////////////
    @Override
    protected void onStop() {
        super.onStop();
        saveGame();
    }

    private void saveGame() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        int valeur =  currentGame.getRound();
        int nbTower = currentGame.getTowerView().getTowers().size();
        ArrayList<Tower> towers = currentGame.getTowerView().getTowers();

        for(Tower t:towers){
            editor.putString("tour" + t.getId(), t.getCaseNumber() + "#" + t.getStyle() + "&" + t.getTier());

        }
        String cash = currentGame.getNbCash();
        int nbVie = currentGame.getNbVies();

        editor.putInt("round", valeur);
        editor.putString("cash", cash);
        editor.putInt("vies", nbVie);
        editor.putInt("nbtour",nbTower);
        editor.commit();
    }
    ////////// omar ///////////

}
