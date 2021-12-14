package com.example.llt_project_separate.general.shared_preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.llt_project_separate.general.standard_classes.Category;
import com.example.llt_project_separate.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Utils {
    private static final String ALL_CATEGORIES = "all_categories";
    private static final String FAVORITE_CATEGORIES = "favorite_categories";
    private static final String SHARED_PREF_FILE_NAME = "project_shared_preferences_test2";

    private static Utils instance;
    private final SharedPreferences sharedPreferences;

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

        categories.add(new Category(32, "ZERO", R.drawable.zero));
        categories.add(new Category(33, "UNU", R.drawable.unu));
        categories.add(new Category(34, "DOI", R.drawable.doi));
        categories.add(new Category(35, "TREI", R.drawable.trei));
        categories.add(new Category(36, "PATRU", R.drawable.patru));
        categories.add(new Category(37, "CINCI", R.drawable.cinci));
        categories.add(new Category(38, "ŞASE", R.drawable.sase));
        categories.add(new Category(39, "ŞAPTE", R.drawable.sapte));
        categories.add(new Category(40, "OPT", R.drawable.opt));
        categories.add(new Category(41, "NOUĂ", R.drawable.noua));
        categories.add(new Category(42, "ZECE", R.drawable.zece));
        
        categories.add(new Category(43, "ROŞU", R.drawable.rosu));
        categories.add(new Category(44, "PORTOCALIU", R.drawable.portocaliu));
        categories.add(new Category(45, "GALBEN", R.drawable.galben));
        categories.add(new Category(46, "VERDE", R.drawable.verde));
        categories.add(new Category(47, "ALBASTRU", R.drawable.albastru));
        categories.add(new Category(48, "INDIGO", R.drawable.indigo));
        categories.add(new Category(49, "VIOLET", R.drawable.violet));
        categories.add(new Category(50, "MARO", R.drawable.maro));
        categories.add(new Category(51, "ROZ", R.drawable.roz));
        categories.add(new Category(52, "TURCOAZ", R.drawable.turcoaz));
        
        categories.add(new Category(53, "CÂINE", R.drawable.caine));
        categories.add(new Category(54, "PISICĂ", R.drawable.pisica));
        categories.add(new Category(55, "PORC", R.drawable.porc));
        categories.add(new Category(56, "CAPRĂ", R.drawable.capra));
        categories.add(new Category(57, "GĂINĂ", R.drawable.gaina));
        categories.add(new Category(58, "IEPURE", R.drawable.iepure));
        categories.add(new Category(59, "PUI", R.drawable.pui));
        categories.add(new Category(60, "VACĂ", R.drawable.vaca));
        categories.add(new Category(61, "CAL", R.drawable.cal));
        categories.add(new Category(62, "COCOŞ", R.drawable.cocos));
        categories.add(new Category(63, "GÂSCĂ", R.drawable.gasca));
        categories.add(new Category(64, "OAIE", R.drawable.oaie));
        categories.add(new Category(65, "RAŢĂ", R.drawable.rata));

        categories.add(new Category(66, "LUP", R.drawable.lup));
        categories.add(new Category(67, "VULPE", R.drawable.vulpe));
        categories.add(new Category(68, "ARICI", R.drawable.arici));
        categories.add(new Category(69, "LEU", R.drawable.leu));
        categories.add(new Category(70, "VEVERIŢĂ", R.drawable.veverita));
        categories.add(new Category(71, "ZIMBRU", R.drawable.zimbru));
        categories.add(new Category(72, "URS POLAR", R.drawable.urs_polar));
        categories.add(new Category(73, "ELEFANT", R.drawable.elefant));
        categories.add(new Category(74, "CROCODIL", R.drawable.crocodil));

        categories.add(new Category(75, "PAT", R.drawable.pat));
        categories.add(new Category(76, "TELEVIZOR", R.drawable.televizor));
        categories.add(new Category(77, "CALCULATOR", R.drawable.calculator));
        categories.add(new Category(78, "DULAP", R.drawable.dulap));
        categories.add(new Category(79, "LINGURĂ", R.drawable.lingura));
        categories.add(new Category(80, "SCAUN", R.drawable.scaun));
        categories.add(new Category(81, "MASĂ", R.drawable.masa));
        categories.add(new Category(82, "FRIGIDER", R.drawable.frigider));
        categories.add(new Category(83, "CUŢIT", R.drawable.cutit));

        categories.add(new Category(84, "TOPOR", R.drawable.topor));
        categories.add(new Category(85, "POARTĂ", R.drawable.poarta));
        categories.add(new Category(86, "GEAM", R.drawable.geam));
        categories.add(new Category(87, "FURCĂ", R.drawable.furca));
        categories.add(new Category(88, "COPAC", R.drawable.copac));
        categories.add(new Category(89, "GARD", R.drawable.gard));
        categories.add(new Category(90, "COASĂ", R.drawable.coasa));
        categories.add(new Category(91, "BEC", R.drawable.bec));
        categories.add(new Category(92, "LOPATĂ", R.drawable.lopata));
        categories.add(new Category(93, "UŞĂ", R.drawable.usa));

        categories.add(new Category(94, "MANUAL", R.drawable.manual));
        categories.add(new Category(95, "PIX", R.drawable.pix));
        categories.add(new Category(96, "DICŢIONAR", R.drawable.dictionar));
        categories.add(new Category(97, "CARTE", R.drawable.carte));
        categories.add(new Category(98, "BANCĂ", R.drawable.banca));
        categories.add(new Category(99, "TEMĂ", R.drawable.tema));
        categories.add(new Category(100, "VIDEO PROIECTOR", R.drawable.video_proiector));
        categories.add(new Category(101, "TEST", R.drawable.test));
        categories.add(new Category(102, "STILOU", R.drawable.stilou));
        categories.add(new Category(103, "TEST GRILĂ", R.drawable.test_grila));
        categories.add(new Category(104, "RADIERĂ", R.drawable.radiera));
        categories.add(new Category(105, "CRETĂ", R.drawable.creta));
        categories.add(new Category(106, "CATEDRĂ", R.drawable.catedra));
        categories.add(new Category(107, "CREION", R.drawable.creion));
        categories.add(new Category(108, "CAIET", R.drawable.caiet));

        categories.add(new Category(109, "EURO", R.drawable.euro));
        categories.add(new Category(110, "DOLAR", R.drawable.dolar));
        categories.add(new Category(111, "MONEDĂ", R.drawable.moneda));
        categories.add(new Category(112, "BANI", R.drawable.bani));
        categories.add(new Category(113, "BANCNOTĂ", R.drawable.bancnota));

        categories.add(new Category(114, "SUC", R.drawable.suc));
        categories.add(new Category(115, "PRĂJITURĂ", R.drawable.prajitura));
        categories.add(new Category(116, "MORCOV", R.drawable.morcov));
        categories.add(new Category(117, "CIOCOLATĂ", R.drawable.ciocolata));
        categories.add(new Category(118, "PÂINE", R.drawable.paine));
        categories.add(new Category(119, "CEAPĂ", R.drawable.ceapa));
        categories.add(new Category(120, "ULEI", R.drawable.ulei));
        categories.add(new Category(121, "CASTRAVEŢI", R.drawable.castraveti));
        categories.add(new Category(122, "CARTOFI", R.drawable.cartofi));
        categories.add(new Category(123, "CAŞCAVAL", R.drawable.cascaval));
        categories.add(new Category(124, "ARDEI", R.drawable.ardei));
        categories.add(new Category(125, "BRÂNZĂ", R.drawable.branza));

        categories.add(new Category(126, "ŞCOALĂ", R.drawable.scoala));
        categories.add(new Category(127, "LEAGĂN", R.drawable.leagan));
        categories.add(new Category(128, "SPITAL", R.drawable.spital));
        categories.add(new Category(129, "AUTOBUZ", R.drawable.autobuz));
        categories.add(new Category(130, "STÂLP", R.drawable.stalp));
        categories.add(new Category(131, "BLOC", R.drawable.bloc));
        categories.add(new Category(132, "TAXI", R.drawable.taxi));
        categories.add(new Category(133, "LOC DE PARCARE", R.drawable.loc_de_parcare));
        categories.add(new Category(134, "TRAMVAI", R.drawable.tramvai));
        categories.add(new Category(135, "POLIŢIE", R.drawable.politie));

        categories.add(new Category(136, "TATĂ", R.drawable.tata));
        categories.add(new Category(137, "MAMĂ", R.drawable.mama));
        categories.add(new Category(138, "FRATE", R.drawable.frate));
        categories.add(new Category(139, "SORĂ", R.drawable.sora));
        categories.add(new Category(140, "UNCHI", R.drawable.unchi));
        categories.add(new Category(141, "MĂTUŞĂ", R.drawable.matusa));
        categories.add(new Category(142, "BUNIC/BUNICĂ", R.drawable.bunic));
        categories.add(new Category(143, "NAŞ/NAŞĂ", R.drawable.nasa));

        categories.add(new Category(144, "EU"));
        categories.add(new Category(145, "TU"));
        categories.add(new Category(146, "EL"));
        categories.add(new Category(147, "EA"));
        categories.add(new Category(148, "NOI"));
        categories.add(new Category(149, "VOI"));
        categories.add(new Category(150, "EI/ELE"));

        categories.add(new Category(151, "VESEL", R.drawable.vesel));
        categories.add(new Category(152, "SĂTURAT", R.drawable.saturat));
        categories.add(new Category(153, "ÎNCRUNTAT", R.drawable.incruntat));
        categories.add(new Category(154, "TRIST", R.drawable.trist));
        categories.add(new Category(155, "DEZAMĂGIT", R.drawable.dezamagit));
        categories.add(new Category(156, "NERVOS", R.drawable.nervos));
        categories.add(new Category(157, "FERICIT", R.drawable.fericit));
        categories.add(new Category(158, "SUPĂRAT", R.drawable.suparat));
        categories.add(new Category(159, "ÎNDURERAT", R.drawable.indurerat));
        categories.add(new Category(160, "ENTUZIASMAT", R.drawable.entuziasmat));
        categories.add(new Category(161, "ÎNDRĂGOSTIT", R.drawable.indragostit));
        categories.add(new Category(162, "LINIŞTIT", R.drawable.linistit));
        categories.add(new Category(163, "CUMINTE", R.drawable.cuminte));

        categories.add(new Category(164, "CONSTRUI", R.drawable.construi));
        categories.add(new Category(165, "SCRIE", R.drawable.scrie));
        categories.add(new Category(166, "TRAGE", R.drawable.trage));
        categories.add(new Category(167, "CITI", R.drawable.citi));
        categories.add(new Category(168, "STA JOS", R.drawable.sta_jos));
        // categories.add(new Category(169, "LĂSA", R.drawable.lasa));
        categories.add(new Category(170, "APĂSA", R.drawable.apasa));
        // categories.add(new Category(171, "PUNE", R.drawable.pune));
        // categories.add(new Category(172, "STRÂNGE", R.drawable.strange));
        // categories.add(new Category(173, "SCOATE", R.drawable.scoate));
        categories.add(new Category(174, "SPUNE", R.drawable.spune));
        categories.add(new Category(175, "SE UITA", R.drawable.se_uita));

        categories.add(new Category(176, "VĂ ROG", R.drawable.va_rog));
        categories.add(new Category(177, "MULŢUMESC", R.drawable.multumesc));
        categories.add(new Category(178, "SCUZE", R.drawable.scuze));
        categories.add(new Category(179, "HAI", R.drawable.hai));

        categories.add(new Category(180, "DOMESTICE", R.drawable.animale_domestice));
        categories.add(new Category(181, "SĂLBATICE", R.drawable.animale_salbatice));
        categories.add(new Category(182, "ACASĂ", R.drawable.acasa));
        categories.add(new Category(183, "AFARĂ", R.drawable.afara));
        categories.add(new Category(184, "CLASĂ", R.drawable.clasa));
        categories.add(new Category(185, "MAGAZIN", R.drawable.magazin));
        categories.add(new Category(186, "ORAŞ", R.drawable.oras));
        categories.add(new Category(187, "BANI", R.drawable.bani));
        categories.add(new Category(188, "PRODUSE", R.drawable.produse));
        categories.add(new Category(189, "MEMBRII FAMILIEI", R.drawable.membrii_familiei));
        categories.add(new Category(190, "PRONUME", R.drawable.pronume));

        categories.add(new Category(1001, "ALFABET", R.drawable.alfabet));
        categories.add(new Category(1002, "NUMERE", R.drawable.numere));
        categories.add(new Category(1003, "CULORI", R.drawable.culori));
        categories.add(new Category(1004, "ANIMALE", R.drawable.animale));
        categories.add(new Category(1005, "OBIECTE", R.drawable.obiecte));
        categories.add(new Category(1006, "PERSOANE", R.drawable.persoane));
        categories.add(new Category(1007, "EMOŢII", R.drawable.emotii));
        categories.add(new Category(1008, "VERBE", R.drawable.verbe));
        categories.add(new Category(1009, "FORMULE DE ADRESARE", R.drawable.formule_de_adresare));

        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        editor.putString(ALL_CATEGORIES, gson.toJson(categories));
        editor.apply();
    }

    public static synchronized Utils getInstance(Context context) {
        if(instance == null) instance = new Utils(context);
        return instance;
    }

    public ArrayList<Category> getAllCategories() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Category>>(){}.getType();
        return gson.fromJson(sharedPreferences.getString(ALL_CATEGORIES, null), type);
    }

    public ArrayList<Category> getFavoriteCategories() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Category>>(){}.getType();
        return gson.fromJson(sharedPreferences.getString(FAVORITE_CATEGORIES, null), type);
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
