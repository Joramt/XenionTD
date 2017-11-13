package com.example.ecole.xeniontd.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ecole.xeniontd.R;
import com.example.ecole.xeniontd.entities.Game;
import com.example.ecole.xeniontd.utils.c;

import java.util.ArrayList;

public class RepriseJeu extends Activity {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reprise_jeu2);

        Intent intentRetour = getIntent();
        int round = intentRetour.getIntExtra("round", 0);
        int nbVie = intentRetour.getIntExtra("vies",0);
        int nbTour = intentRetour.getIntExtra("nbtour", 0);
        String cash = intentRetour.getStringExtra("cash");
        ArrayList<String> towerData = new ArrayList<>(nbTour);
        towerData= intentRetour.getStringArrayListExtra("data");


        final LinearLayout mainLayout = (LinearLayout) findViewById(R.id.mainLayout);
        final LinearLayout gameMenu = (LinearLayout) findViewById(R.id.gameInfos);



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

        Game currentGame = new Game(mainLayout, ctx, mainView, "ete", rootLayout,cash, nbVie, round, towerData);

        currentGame.createBoard(nbLigne, nbColonne);
        currentGame.setGameMenu(gameLayout, gameMenu);
        currentGame.setMenuInteraction();
        //currentGame.setProgressBar();
        currentGame.spawnMonstres(0);




    }
}
