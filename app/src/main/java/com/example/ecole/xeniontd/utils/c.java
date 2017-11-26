package com.example.ecole.xeniontd.utils;

/**
 * Created by Ecole on 1/6/2016.
 */
public final class c {

    public static int CELL_WIDTH = 32;
    public static int CELL_HEIGHT = 32;

    public static int NO_TOWERS_ALLOWED[] = {9, 10, 15, 16, 27, 28, 33, 34, 45, 46, 51, 52,  55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 69, 70, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 87, 88, 91, 92,
            105, 106, 109, 110, 115, 116, 117, 118, 119, 120, 121, 123, 124, 127, 128, 133, 134, 135, 136, 137, 138, 139, 141, 142, 145, 146, 151, 152, 156, 157, 159, 160, 163, 163, 165, 166, 167,
            168, 169, 170, 174, 175, 176, 177, 178, 181, 182, 183, 184, 185, 186, 187, 188, 192, 193, 194, 195, 196};

    public static int ARRAY_ARBRES_ROUGE[] = {24, 13, 158, 19};
    public static int ARRAY_ARBRES_BLEU[] = {44, 98, 191, 86, 143};
    public static int ARRAY_ARBRES_VERT[] = {147, 5, 112, 67, 90};

    public static int ARRAY_TERRAIN_FEUILLES[] = {40, 84, 12, 113, 122, 144, 53, 190, 147};

    public static int ARRAY_CHEMIN_LEFT[] = {9, 27, 45, 91, 109, 127, 141, 123, 105, 87, 69, 51, 73, 33, 15, 145, 163, 151, 133, 156, 174, 159};
    public static int ARRAY_CHEMIN_RIGHT[] = {10, 28, 46, 64, 92, 110, 128, 142, 124, 106, 88, 70, 52, 34, 16, 146, 170, 152, 139, 157, 160, 178};
    public static int ARRAY_CHEMIN_TOP[] = {56, 57, 58, 59, 60, 61, 62, 166, 167, 168, 116, 117, 118, 119, 120, 165, 176};
    public static int ARRAY_CHEMIN_BOTTOM[] = {75, 76, 77, 78, 79, 80, 81, 183, 184, 185, 186, 187, 88, 135, 136, 137, 182, 193, 194, 195};

    public static int ARRAY_CORNER_TOPTOLEFT_OUTSIDE[] = {82, 196, 188};
    public static int ARRAY_CORNER_TOPTORIGHT_OUTSIDE[] = {181, 192};
    public static int ARRAY_CORNER_BOTTOMTOLEFT_OUTSIDE[] = {121};
    public static int ARRAY_CORNER_BOTTOMTORIGHT_OUTSIDE[] = {55, 115};

    public static int ARRAY_CORNER_TOPTOLEFT_INSIDE[] = {63, 169, 177};
    public static int ARRAY_CORNER_TOPTORIGHT_INSIDE[] = {164, 175};
    public static int ARRAY_CORNER_BOTTOMTOLEFT_INSIDE[] = {138};
    public static int ARRAY_CORNER_BOTTOMTORIGHT_INSIDE[] = {74, 134};

    public static String ARRAY_TURN_LEFT_Y[] = {"279115","367319"};
    public static String ARRAY_TURN_DOWN_Y[] = {"14115","380210"};
    public static String ARRAY_TURN_UP_Y[] = {"217308","478308"};
    public static String ARRAY_TURN_RIGHT_Y[] = {"14308","217210","380308"};
    public static String ARRAY_END_OF_BOARD[] = {"4780"};

    public static String ENTITY_STATE_CODE_DEATH = "death";
    public static String ENTITY_CODE_POSITION = "position";
    public static String ENTITY_TYPE_TOUR = "tour";

    public static boolean OPEN = true;
    public static boolean CLOSE = false;

    public static int MONSTER_DEAD = 1;
    public static int MONSTER_FINISHED = 2;
    public static int MONSTER_POSITION = 3;


    public static String SEASON_KEY = "Season";

}
