package com.example.llt_project_separate;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Utils {
    private static final String ALL_CATEGORIES = "all_categories";
    private static final String FAVORITE_CATEGORIES = "favorite_categories";
    private static final String SHARED_PREF_FILE_NAME = "project_shared_preferences_test1";

    private static Utils instance;
    private SharedPreferences sharedPreferences;

    private Utils(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(ALL_CATEGORIES);
        Gson gson = new Gson();

        if(getAllCategories() == null) {
            initData();
        }
        if(getFavoriteCategories() == null) {
            editor.putString(FAVORITE_CATEGORIES, gson.toJson(new ArrayList<Category>()));
            editor.apply();
        }
    }

    private void initData() {
        ArrayList<Category> categories = new ArrayList<>();
        categories.add(new Category(1, "A", R.drawable.caine));
        categories.add(new Category(2, "Ă", R.drawable.caine));
        categories.add(new Category(3, "Â", R.drawable.caine));
        categories.add(new Category(4, "B", R.drawable.caine));
        categories.add(new Category(5, "C", R.drawable.caine));
        categories.add(new Category(6, "D", R.drawable.caine));
        categories.add(new Category(7, "E", R.drawable.caine));
        categories.add(new Category(8, "F", R.drawable.caine));
        categories.add(new Category(9, "G", R.drawable.caine));
        categories.add(new Category(10, "H", R.drawable.caine));
        categories.add(new Category(11, "I", R.drawable.caine));
        categories.add(new Category(12, "Î", R.drawable.caine));
        categories.add(new Category(13, "J", R.drawable.caine));
        categories.add(new Category(14, "K", R.drawable.caine));
        categories.add(new Category(15, "L", R.drawable.caine));
        categories.add(new Category(16, "M", R.drawable.caine));
        categories.add(new Category(17, "N", R.drawable.caine));
        categories.add(new Category(18, "O", R.drawable.caine));
        categories.add(new Category(19, "P", R.drawable.caine));
        categories.add(new Category(20, "Q", R.drawable.caine));
        categories.add(new Category(21, "R", R.drawable.caine));
        categories.add(new Category(22, "S", R.drawable.caine));
        categories.add(new Category(23, "Ş", R.drawable.caine));
        categories.add(new Category(24, "T", R.drawable.caine));
        categories.add(new Category(25, "Ţ", R.drawable.caine));
        categories.add(new Category(26, "U", R.drawable.caine));
        categories.add(new Category(27, "V", R.drawable.caine));
        categories.add(new Category(28, "W", R.drawable.caine));
        categories.add(new Category(29, "X", R.drawable.caine));
        categories.add(new Category(30, "Y", R.drawable.caine));
        categories.add(new Category(31, "Z", R.drawable.caine));
        
        categories.add(new Category(32, "UNU", R.drawable.caine));
        categories.add(new Category(33, "DOI", R.drawable.caine));
        categories.add(new Category(34, "TREI", R.drawable.caine));
        categories.add(new Category(35, "PATRU", R.drawable.caine));
        categories.add(new Category(36, "CINCI", R.drawable.caine));
        categories.add(new Category(37, "ŞASE", R.drawable.caine));
        categories.add(new Category(38, "ŞAPTE", R.drawable.caine));
        categories.add(new Category(39, "OPT", R.drawable.caine));
        categories.add(new Category(40, "NOUĂ", R.drawable.caine));
        categories.add(new Category(41, "ZERO", R.drawable.caine));
        
        categories.add(new Category(42, "ROŞU", R.drawable.caine));
        categories.add(new Category(43, "PORTOCALIU", R.drawable.caine));
        categories.add(new Category(44, "GALBEN", R.drawable.caine));
        categories.add(new Category(45, "VERDE", R.drawable.caine));
        categories.add(new Category(46, "ALBASTRU", R.drawable.caine));
        categories.add(new Category(47, "INDIGO", R.drawable.caine));
        categories.add(new Category(48, "VIOLET", R.drawable.caine));
        categories.add(new Category(49, "MARO", R.drawable.caine));
        categories.add(new Category(50, "ROZ", R.drawable.caine));
        categories.add(new Category(51, "TURCOAZ", R.drawable.caine));
        
        categories.add(new Category(52, "CÂINE", R.drawable.caine));
        categories.add(new Category(53, "PISICĂ", R.drawable.pisica));
        categories.add(new Category(54, "PORC", R.drawable.caine));
        categories.add(new Category(55, "CAPRĂ", R.drawable.pisica));
        categories.add(new Category(56, "GĂINĂ", R.drawable.caine));
        categories.add(new Category(57, "IEPURE", R.drawable.pisica));
        categories.add(new Category(58, "PUI", R.drawable.caine));
        categories.add(new Category(59, "VACĂ", R.drawable.pisica));
        categories.add(new Category(60, "CAL", R.drawable.caine));
        categories.add(new Category(61, "COCOŞ", R.drawable.pisica));
        categories.add(new Category(62, "GÂSCĂ", R.drawable.caine));
        categories.add(new Category(63, "OAIE", R.drawable.pisica));
        categories.add(new Category(64, "RAŢĂ", R.drawable.pisica));

        categories.add(new Category(65, "LUP", R.drawable.elefant));
        categories.add(new Category(66, "VULPE", R.drawable.elefant));
        categories.add(new Category(67, "ARICI", R.drawable.elefant));
        categories.add(new Category(68, "LEU", R.drawable.elefant));
        categories.add(new Category(69, "VEVERIŢĂ", R.drawable.elefant));
        categories.add(new Category(70, "ZIMBRU", R.drawable.elefant));
        categories.add(new Category(71, "URS POLAR", R.drawable.urs_polar));
        categories.add(new Category(72, "ELEFANT", R.drawable.elefant));
        categories.add(new Category(73, "CROCODIL", R.drawable.elefant));

        categories.add(new Category(74, "PAT", R.drawable.caine));
        categories.add(new Category(75, "TELEVIZOR", R.drawable.caine));
        categories.add(new Category(76, "CALCULATOR", R.drawable.caine));
        categories.add(new Category(77, "DULAP", R.drawable.caine));
        categories.add(new Category(78, "LINGURĂ", R.drawable.caine));
        categories.add(new Category(79, "SCAUN", R.drawable.caine));
        categories.add(new Category(80, "MASĂ", R.drawable.caine));
        categories.add(new Category(81, "FRIGIDER", R.drawable.caine));
        categories.add(new Category(82, "CUŢIT", R.drawable.caine));

        categories.add(new Category(83, "TOPOR", R.drawable.caine));
        categories.add(new Category(84, "POARTĂ", R.drawable.caine));
        categories.add(new Category(85, "GEAM", R.drawable.caine));
        categories.add(new Category(86, "FURCĂ", R.drawable.caine));
        categories.add(new Category(87, "COPAC", R.drawable.caine));
        categories.add(new Category(88, "GARD", R.drawable.caine));
        categories.add(new Category(89, "COASĂ", R.drawable.caine));
        categories.add(new Category(90, "BEC", R.drawable.caine));
        categories.add(new Category(91, "LOPATĂ", R.drawable.caine));
        categories.add(new Category(92, "UŞĂ", R.drawable.caine));

        categories.add(new Category(93, "MANUAL", R.drawable.caine));
        categories.add(new Category(94, "PIX", R.drawable.caine));
        categories.add(new Category(95, "DICŢIONAR", R.drawable.caine));
        categories.add(new Category(96, "CARTE", R.drawable.caine));
        categories.add(new Category(97, "BANCĂ", R.drawable.caine));
        categories.add(new Category(98, "TEMĂ", R.drawable.caine));
        categories.add(new Category(99, "VIDEO PROIECTOR", R.drawable.caine));
        categories.add(new Category(100, "TEST", R.drawable.caine));
        categories.add(new Category(101, "STILOU", R.drawable.caine));
        categories.add(new Category(102, "TEST GRILĂ", R.drawable.caine));
        categories.add(new Category(103, "RADIERĂ", R.drawable.caine));
        categories.add(new Category(104, "CRETĂ", R.drawable.caine));
        categories.add(new Category(105, "CATEDRĂ", R.drawable.caine));
        categories.add(new Category(106, "CREION", R.drawable.caine));
        categories.add(new Category(107, "CAIET", R.drawable.caine));

        categories.add(new Category(108, "EURO", R.drawable.caine));
        categories.add(new Category(109, "DOLAR", R.drawable.caine));
        categories.add(new Category(110, "MONEDĂ", R.drawable.caine));
        categories.add(new Category(111, "BANI", R.drawable.caine));
        categories.add(new Category(112, "BANCNOTĂ", R.drawable.caine));

        categories.add(new Category(113, "SUC", R.drawable.caine));
        categories.add(new Category(114, "PRĂJITURĂ", R.drawable.caine));
        categories.add(new Category(115, "MORCOV", R.drawable.caine));
        categories.add(new Category(116, "CIOCOLATĂ", R.drawable.caine));
        categories.add(new Category(117, "PÂINE", R.drawable.caine));
        categories.add(new Category(118, "CEAPĂ", R.drawable.caine));
        categories.add(new Category(119, "ULEI", R.drawable.caine));
        categories.add(new Category(120, "CASTRAVEŢI", R.drawable.caine));
        categories.add(new Category(121, "CARTOFI", R.drawable.caine));
        categories.add(new Category(122, "CAŞCAVAL", R.drawable.caine));
        categories.add(new Category(123, "ARDEI", R.drawable.caine));
        categories.add(new Category(124, "BRÂNZĂ", R.drawable.caine));

        categories.add(new Category(125, "ŞCOALĂ", R.drawable.caine));
        categories.add(new Category(126, "LEAGĂN", R.drawable.caine));
        categories.add(new Category(127, "SPITAL", R.drawable.caine));
        categories.add(new Category(128, "AUTOBUZ", R.drawable.caine));
        categories.add(new Category(129, "STÂLP", R.drawable.caine));
        categories.add(new Category(130, "BLOC", R.drawable.caine));
        categories.add(new Category(131, "TAXI", R.drawable.caine));
        categories.add(new Category(132, "LOC DE PARCARE", R.drawable.caine));
        categories.add(new Category(133, "TRAMVAI", R.drawable.caine));
        categories.add(new Category(134, "POLIŢIE", R.drawable.caine));

        categories.add(new Category(135, "TATĂ", R.drawable.caine));
        categories.add(new Category(136, "MAMĂ", R.drawable.caine));
        categories.add(new Category(137, "FRATE", R.drawable.caine));
        categories.add(new Category(138, "SORĂ", R.drawable.caine));
        categories.add(new Category(139, "UNCHI", R.drawable.caine));
        categories.add(new Category(140, "MĂTUŞĂ", R.drawable.caine));
        categories.add(new Category(141, "BUNIC", R.drawable.caine));
        categories.add(new Category(142, "NAŞĂ", R.drawable.caine));

        categories.add(new Category(143, "EU", R.drawable.caine));
        categories.add(new Category(144, "TU", R.drawable.caine));
        categories.add(new Category(145, "EL", R.drawable.caine));
        categories.add(new Category(146, "EA", R.drawable.caine));
        categories.add(new Category(147, "NOI", R.drawable.caine));
        categories.add(new Category(148, "VOI", R.drawable.caine));
        categories.add(new Category(149, "EI", R.drawable.caine));
        categories.add(new Category(150, "ELE", R.drawable.caine));

        categories.add(new Category(151, "VESEL", R.drawable.caine));
        categories.add(new Category(152, "SĂTURAT", R.drawable.caine));
        categories.add(new Category(153, "ÎNCRUNTAT", R.drawable.caine));
        categories.add(new Category(154, "TRIST", R.drawable.caine));
        categories.add(new Category(155, "DEZAMĂGIT", R.drawable.caine));
        categories.add(new Category(156, "NERVOS", R.drawable.caine));
        categories.add(new Category(157, "FERICIT", R.drawable.caine));
        categories.add(new Category(158, "SUPĂRAT", R.drawable.caine));
        categories.add(new Category(159, "ÎNDURERAT", R.drawable.caine));
        categories.add(new Category(160, "ENTUZIASMAT", R.drawable.caine));
        categories.add(new Category(161, "ÎNDRĂGOSTIT", R.drawable.caine));
        categories.add(new Category(162, "LINIŞTIT", R.drawable.caine));
        categories.add(new Category(163, "CUMINTE", R.drawable.caine));

        categories.add(new Category(164, "CONSTRUI", R.drawable.caine));
        categories.add(new Category(165, "SCRIE", R.drawable.caine));
        categories.add(new Category(166, "TRAGE", R.drawable.caine));
        categories.add(new Category(167, "CITI", R.drawable.caine));
        categories.add(new Category(168, "STA JOS", R.drawable.caine));
        categories.add(new Category(169, "LĂSA", R.drawable.caine));
        categories.add(new Category(170, "APĂSA", R.drawable.caine));
        categories.add(new Category(171, "PUNE", R.drawable.caine));
        categories.add(new Category(172, "STRÂNGE", R.drawable.caine));
        categories.add(new Category(173, "SCOATE", R.drawable.caine));
        categories.add(new Category(174, "SPUNE", R.drawable.caine));
        categories.add(new Category(175, "SE UITA", R.drawable.caine));

        categories.add(new Category(176, "VĂ ROG", R.drawable.caine));
        categories.add(new Category(177, "MULŢUMESC", R.drawable.caine));
        categories.add(new Category(178, "SCUZE", R.drawable.caine));
        categories.add(new Category(179, "HAI", R.drawable.caine));

        /*
        categories.add(new Category(180, "DOMESTICE", R.drawable.animale_domestice));
        categories.add(new Category(181, "SĂLBATICE", R.drawable.animale_salbatice));
        categories.add(new Category(182, "ACASĂ", R.drawable.animale_domestice));
        categories.add(new Category(183, "AFARĂ", R.drawable.animale_domestice));
        categories.add(new Category(184, "CLASĂ", R.drawable.animale_domestice));
        categories.add(new Category(185, "MAGAZIN", R.drawable.animale_domestice));
        categories.add(new Category(186, "ORAŞ", R.drawable.animale_domestice));
        categories.add(new Category(187, "MEMBRII FAMILIEI", R.drawable.caine));
        categories.add(new Category(188, "PRONUME", R.drawable.caine));
        categories.add(new Category(189, "BANI", R.drawable.caine));
        categories.add(new Category(190, "PRODUSE", R.drawable.caine));

        categories.add(new Category(1001, "ALFABET", R.drawable.alfabet));
        categories.add(new Category(1002, "NUMERE", R.drawable.numere));
        categories.add(new Category(1003, "CULORI", R.drawable.culori));
        categories.add(new Category(1004, "ANIMALE", R.drawable.animale));
        categories.add(new Category(1005, "OBIECTE", R.drawable.animale));
        categories.add(new Category(1006, "PERSOANE", R.drawable.animale));
        categories.add(new Category(1007, "EMOŢII", R.drawable.animale));
        categories.add(new Category(1008, "VERBE", R.drawable.animale));
        categories.add(new Category(1009, "FORMULE DE ADRESARE", R.drawable.animale));
         */

        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        editor.putString(ALL_CATEGORIES, gson.toJson(categories));
        editor.apply();
    }

    public static synchronized Utils getInstance(Context context) {
        if(instance == null) {
            instance = new Utils(context);
        }
        return instance;
    }

    public ArrayList<Category> getAllCategories() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Category>>(){}.getType();
        ArrayList<Category> categories = gson.fromJson(sharedPreferences.getString(ALL_CATEGORIES, null), type);
        return categories;
    }

    public ArrayList<Category> getFavoriteCategories() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Category>>(){}.getType();
        ArrayList<Category> categories = gson.fromJson(sharedPreferences.getString(FAVORITE_CATEGORIES, null), type);
        return categories;
    }

    public Category getCategoryById(int id) {
        ArrayList<Category> categories = getAllCategories();
        if(categories != null) {
            for(Category category : categories) {
                if(category.getId() == id) {
                    return category;
                }
            }
        }
        return null;
    }

    public boolean addedToFavorite(Category category) {
        ArrayList<Category> categories = getFavoriteCategories();
        if(categories != null) {
            if(categories.add(category)) {
                Gson gson = new Gson();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(FAVORITE_CATEGORIES);
                editor.putString(FAVORITE_CATEGORIES, gson.toJson(categories));
                editor.apply();
                return true;
            }
        }
        return false;
    }

    public boolean removedFromFavorite(Category category) {
        ArrayList<Category> categories = getFavoriteCategories();
        if(categories != null) {
            for(Category currentCategory : categories) {
                if(currentCategory.getId() == category.getId()) {
                    if(categories.remove(currentCategory)) {
                        Gson gson = new Gson();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove(FAVORITE_CATEGORIES);
                        editor.putString(FAVORITE_CATEGORIES, gson.toJson(categories));
                        editor.apply();
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
