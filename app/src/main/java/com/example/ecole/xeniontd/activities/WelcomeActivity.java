package com.example.ecole.xeniontd.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.ecole.xeniontd.R;
import com.example.ecole.xeniontd.utils.c;

import java.util.ArrayList;

public class WelcomeActivity extends Activity {

    final CharSequence[] items = {" Automne ", " Été ", " Hiver "};
    private String season;
    private int choiceSelected = 0;
    Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ctx = this;
        Button startGame = (Button) findViewById(R.id.startgame);
        final Context ctx = this;

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(ctx);

                builder.setTitle("Choissisez votre décor");

                builder.setSingleChoiceItems(items, choiceSelected, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {

                        switch (item) {
                            case 0:
                                choiceSelected = 0;
                                setSeason("automne");
                                dialog.dismiss();
                                break;
                            case 1:
                                choiceSelected = 1;
                                setSeason("ete");
                                dialog.dismiss();
                                break;
                            case 2:
                                choiceSelected = 2;
                                setSeason("hiver");
                                dialog.dismiss();
                                break;
                            default:
                                setSeason("autumn");
                                break;

                        }

                        Intent launch = new Intent(WelcomeActivity.this, MainActivity.class);
                        launch.putExtra(c.SEASON_KEY, getSeason());
                        startActivity(launch);
                    }
                });

                builder.show();
            }
        });

/////////// Omar
        Button boutonReprise = (Button) findViewById(R.id.buttonReprise);
        boutonReprise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctx);

                String cash = preferences.getString("cash", "");
                int nbVie = preferences.getInt("vies", 0);
                int nbTour = preferences.getInt("nbtour",0);
                int round = preferences.getInt("round", 0);


                ArrayList<String> towerData = new ArrayList<String>(nbTour);

                for(int j =0; j < nbTour; j++){
                    towerData.add(preferences.getString("tour" + (j + 1), ""));

                }





                Intent intent = new Intent(WelcomeActivity.this , RepriseJeu.class);
                intent.putExtra("round", round);
                intent.putExtra("cash", cash);
                intent.putExtra("vies",nbVie);
                intent.putExtra("nbtour",nbTour);
                intent.putStringArrayListExtra("data",towerData);

                startActivity(intent);

            }
        });

    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }
}
