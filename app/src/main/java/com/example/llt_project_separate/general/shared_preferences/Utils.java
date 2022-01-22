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
    private static final String SHARED_PREF_FILE_NAME = "final_project_shared_preferences";

    private static Utils instance;
    private final SharedPreferences sharedPreferences;

    private Utils(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(ALL_CATEGORIES);
        Gson gson = new Gson();

        if(getAllCategories() == null) {
            initData(context);
        }
        if(getFavoriteCategories() == null) {
            editor.putString(FAVORITE_CATEGORIES, gson.toJson(new ArrayList<Category>()));
            editor.apply();
        }
    }

    private void initData(Context context) {
        ArrayList<Category> categories = new ArrayList<>();
        categories.add(new Category(1, getStringResource(context, R.string.A), R.drawable.a));
        // categories.add(new Category(2, getStringResource(context, R.string.A), R.drawable.a));
        // categories.add(new Category(3, getStringResource(context, R.string.A), R.drawable.a));
        categories.add(new Category(4, getStringResource(context, R.string.B), R.drawable.b));
        categories.add(new Category(5, getStringResource(context, R.string.C), R.drawable.c));
        categories.add(new Category(6, getStringResource(context, R.string.D), R.drawable.d));
        categories.add(new Category(7, getStringResource(context, R.string.E), R.drawable.e));
        categories.add(new Category(8, getStringResource(context, R.string.F), R.drawable.f));
        categories.add(new Category(9, getStringResource(context, R.string.G), R.drawable.g));
        categories.add(new Category(10, getStringResource(context, R.string.H), R.drawable.h));
        categories.add(new Category(11, getStringResource(context, R.string.I), R.drawable.i));
        // categories.add(new Category(12, getStringResource(context, R.string.I), R.drawable.i));
        categories.add(new Category(13, getStringResource(context, R.string.J), R.drawable.j));
        categories.add(new Category(14, getStringResource(context, R.string.K), R.drawable.k));
        categories.add(new Category(15, getStringResource(context, R.string.L), R.drawable.l));
        categories.add(new Category(16, getStringResource(context, R.string.M), R.drawable.m));
        categories.add(new Category(17, getStringResource(context, R.string.N), R.drawable.n));
        categories.add(new Category(18, getStringResource(context, R.string.O), R.drawable.o));
        categories.add(new Category(19, getStringResource(context, R.string.P), R.drawable.p));
        categories.add(new Category(20, getStringResource(context, R.string.Q), R.drawable.q));
        categories.add(new Category(21, getStringResource(context, R.string.R), R.drawable.r));
        categories.add(new Category(22, getStringResource(context, R.string.S), R.drawable.s));
        // letters.add(new Category(23, getStringResource(context, R.string.S),R.drawable.s));
        categories.add(new Category(24, getStringResource(context, R.string.T), R.drawable.t));
        // letters.add(new Category(25, getStringResource(context, R.string.T), R.drawable.t));
        categories.add(new Category(26, getStringResource(context, R.string.U), R.drawable.u));
        categories.add(new Category(27, getStringResource(context, R.string.V), R.drawable.v));
        categories.add(new Category(28, getStringResource(context, R.string.W), R.drawable.w));
        categories.add(new Category(29, getStringResource(context, R.string.X), R.drawable.x));
        categories.add(new Category(30, getStringResource(context, R.string.Y), R.drawable.y));
        categories.add(new Category(31, getStringResource(context, R.string.Z), R.drawable.z));

        // categories.add(new Category(32, getStringResource(context, R.string.ZERO), R.drawable.zero));
        categories.add(new Category(33, getStringResource(context, R.string.UNU), R.drawable.unu));
        categories.add(new Category(34, getStringResource(context, R.string.DOI), R.drawable.doi));
        categories.add(new Category(35, getStringResource(context, R.string.TREI), R.drawable.trei));
        categories.add(new Category(36, getStringResource(context, R.string.PATRU), R.drawable.patru));
        categories.add(new Category(37, getStringResource(context, R.string.CINCI), R.drawable.cinci));
        categories.add(new Category(38, getStringResource(context, R.string.SASE), R.drawable.sase));
        categories.add(new Category(39, getStringResource(context, R.string.SAPTE), R.drawable.sapte));
        categories.add(new Category(40, getStringResource(context, R.string.OPT), R.drawable.opt));
        categories.add(new Category(41, getStringResource(context, R.string.NOUA), R.drawable.noua));
        categories.add(new Category(42, getStringResource(context, R.string.ZECE), R.drawable.zece));
        
        categories.add(new Category(43, getStringResource(context, R.string.ROSU), R.drawable.rosu));
        categories.add(new Category(44, getStringResource(context, R.string.PORTOCALIU), R.drawable.portocaliu));
        categories.add(new Category(45, getStringResource(context, R.string.GALBEN), R.drawable.galben));
        categories.add(new Category(46, getStringResource(context, R.string.VERDE), R.drawable.verde));
        categories.add(new Category(47, getStringResource(context, R.string.ALBASTRU), R.drawable.albastru));
        // categories.add(new Category(48, getStringResource(context, R.string.INDIGO), R.drawable.indigo));
        // categories.add(new Category(49, getStringResource(context, R.string.VIOLET), R.drawable.violet));
        categories.add(new Category(48, getStringResource(context, R.string.ALB), R.drawable.alb));
        categories.add(new Category(49, getStringResource(context, R.string.GRI), R.drawable.gri));
        categories.add(new Category(50, getStringResource(context, R.string.MARO), R.drawable.maro));
        categories.add(new Category(51, getStringResource(context, R.string.ROZ), R.drawable.roz));
        // categories.add(new Category(52, getStringResource(context, R.string.TURCOAZ), R.drawable.turcoaz));
        categories.add(new Category(52, getStringResource(context, R.string.NEGRU), R.drawable.negru));
        
        categories.add(new Category(53, getStringResource(context, R.string.CAINE), R.drawable.caine));
        categories.add(new Category(54, getStringResource(context, R.string.PISICA), R.drawable.pisica));
        categories.add(new Category(55, getStringResource(context, R.string.PORC), R.drawable.porc));
        categories.add(new Category(56, getStringResource(context, R.string.CAPRA), R.drawable.capra));
        categories.add(new Category(57, getStringResource(context, R.string.GAINA), R.drawable.gaina));
        categories.add(new Category(58, getStringResource(context, R.string.IEPURE), R.drawable.iepure));
        categories.add(new Category(59, getStringResource(context, R.string.PUI), R.drawable.pui));
        categories.add(new Category(60, getStringResource(context, R.string.VACA), R.drawable.vaca));
        categories.add(new Category(61, getStringResource(context, R.string.CAL), R.drawable.cal));
        categories.add(new Category(62, getStringResource(context, R.string.COCOS), R.drawable.cocos));
        categories.add(new Category(63, getStringResource(context, R.string.GASCA), R.drawable.gasca));
        categories.add(new Category(64, getStringResource(context, R.string.OAIE), R.drawable.oaie));
        categories.add(new Category(65, getStringResource(context, R.string.RATA), R.drawable.rata));

        categories.add(new Category(66, getStringResource(context, R.string.LUP), R.drawable.lup));
        categories.add(new Category(67, getStringResource(context, R.string.VULPE), R.drawable.vulpe));
        categories.add(new Category(68, getStringResource(context, R.string.ARICI), R.drawable.arici));
        categories.add(new Category(69, getStringResource(context, R.string.LEU), R.drawable.leu));
        categories.add(new Category(70, getStringResource(context, R.string.VEVERITA), R.drawable.veverita));
        categories.add(new Category(71, getStringResource(context, R.string.ZIMBRU), R.drawable.zimbru));
        categories.add(new Category(72, getStringResource(context, R.string.URS_POLAR), R.drawable.urs_polar));
        categories.add(new Category(73, getStringResource(context, R.string.ELEFANT), R.drawable.elefant));
        categories.add(new Category(74, getStringResource(context, R.string.CROCODIL), R.drawable.crocodil));

        categories.add(new Category(75, getStringResource(context, R.string.PAT), R.drawable.pat));
        categories.add(new Category(76, getStringResource(context, R.string.TELEVIZOR), R.drawable.televizor));
        categories.add(new Category(77, getStringResource(context, R.string.CALCULATOR), R.drawable.calculator));
        categories.add(new Category(78, getStringResource(context, R.string.DULAP), R.drawable.dulap));
        categories.add(new Category(79, getStringResource(context, R.string.LINGURA), R.drawable.lingura));
        categories.add(new Category(80, getStringResource(context, R.string.SCAUN), R.drawable.scaun));
        categories.add(new Category(81, getStringResource(context, R.string.MASA), R.drawable.masa));
        categories.add(new Category(82, getStringResource(context, R.string.FRIGIDER), R.drawable.frigider));
        categories.add(new Category(83, getStringResource(context, R.string.CUTIT), R.drawable.cutit));

        categories.add(new Category(84, getStringResource(context, R.string.TOPOR), R.drawable.topor));
        categories.add(new Category(85, getStringResource(context, R.string.POARTA), R.drawable.poarta));
        categories.add(new Category(86, getStringResource(context, R.string.GEAM), R.drawable.geam));
        categories.add(new Category(87, getStringResource(context, R.string.FURCA), R.drawable.furca));
        categories.add(new Category(88, getStringResource(context, R.string.COPAC), R.drawable.copac));
        categories.add(new Category(89, getStringResource(context, R.string.GARD), R.drawable.gard));
        categories.add(new Category(90, getStringResource(context, R.string.COASA), R.drawable.coasa));
        categories.add(new Category(91, getStringResource(context, R.string.BEC), R.drawable.bec));
        categories.add(new Category(92, getStringResource(context, R.string.LOPATA), R.drawable.lopata));
        categories.add(new Category(93, getStringResource(context, R.string.USA), R.drawable.usa));

        categories.add(new Category(94, getStringResource(context, R.string.MANUAL), R.drawable.manual));
        categories.add(new Category(95, getStringResource(context, R.string.PIX), R.drawable.pix));
        categories.add(new Category(96, getStringResource(context, R.string.DICTIONAR), R.drawable.dictionar));
        categories.add(new Category(97, getStringResource(context, R.string.CARTE), R.drawable.carte));
        categories.add(new Category(98, getStringResource(context, R.string.BANCA), R.drawable.banca));
        categories.add(new Category(99, getStringResource(context, R.string.TEMA), R.drawable.tema));
        categories.add(new Category(100, getStringResource(context, R.string.VIDEO_PROIECTOR), R.drawable.video_proiector));
        categories.add(new Category(101, getStringResource(context, R.string.TEST), R.drawable.test));
        categories.add(new Category(102, getStringResource(context, R.string.STILOU), R.drawable.stilou));
        categories.add(new Category(103, getStringResource(context, R.string.TEST_GRILA), R.drawable.test_grila));
        categories.add(new Category(104, getStringResource(context, R.string.RADIERA), R.drawable.radiera));
        categories.add(new Category(105, getStringResource(context, R.string.CRETA), R.drawable.creta));
        categories.add(new Category(106, getStringResource(context, R.string.CATEDRA), R.drawable.catedra));
        categories.add(new Category(107, getStringResource(context, R.string.CREION), R.drawable.creion));
        categories.add(new Category(108, getStringResource(context, R.string.CAIET), R.drawable.caiet));

        categories.add(new Category(109, getStringResource(context, R.string.EURO), R.drawable.euro));
        categories.add(new Category(110, getStringResource(context, R.string.DOLAR), R.drawable.dolar));
        categories.add(new Category(111, getStringResource(context, R.string.MONEDA), R.drawable.moneda));
        // categories.add(new Category(112, getStringResource(context, R.string.BANI), R.drawable.bani));
        categories.add(new Category(113, getStringResource(context, R.string.BANCNOTA), R.drawable.bancnota));

        categories.add(new Category(114, getStringResource(context, R.string.SUC), R.drawable.suc));
        categories.add(new Category(115, getStringResource(context, R.string.PRAJITURA), R.drawable.prajitura));
        categories.add(new Category(116, getStringResource(context, R.string.MORCOV), R.drawable.morcov));
        categories.add(new Category(117, getStringResource(context, R.string.CIOCOLATA), R.drawable.ciocolata));
        categories.add(new Category(118, getStringResource(context, R.string.PAINE), R.drawable.paine));
        categories.add(new Category(119, getStringResource(context, R.string.CEAPA), R.drawable.ceapa));
        categories.add(new Category(120, getStringResource(context, R.string.ULEI), R.drawable.ulei));
        categories.add(new Category(121, getStringResource(context, R.string.CASTRAVETI), R.drawable.castraveti));
        categories.add(new Category(122, getStringResource(context, R.string.CARTOFI), R.drawable.cartofi));
        categories.add(new Category(123, getStringResource(context, R.string.CASCAVAL), R.drawable.cascaval));
        categories.add(new Category(124, getStringResource(context, R.string.ARDEI), R.drawable.ardei));
        categories.add(new Category(125, getStringResource(context, R.string.BRANZA), R.drawable.branza));

        categories.add(new Category(126, getStringResource(context, R.string.SCOALA), R.drawable.scoala));
        categories.add(new Category(127, getStringResource(context, R.string.LEAGAN), R.drawable.leagan));
        categories.add(new Category(128, getStringResource(context, R.string.SPITAL), R.drawable.spital));
        categories.add(new Category(129, getStringResource(context, R.string.AUTOBUZ), R.drawable.autobuz));
        categories.add(new Category(130, getStringResource(context, R.string.STALP), R.drawable.stalp));
        categories.add(new Category(131, getStringResource(context, R.string.BLOC), R.drawable.bloc));
        categories.add(new Category(132, getStringResource(context, R.string.TAXI), R.drawable.taxi));
        categories.add(new Category(133, getStringResource(context, R.string.LOC_DE_PARCARE), R.drawable.loc_de_parcare));
        categories.add(new Category(134, getStringResource(context, R.string.TRAMVAI), R.drawable.tramvai));
        categories.add(new Category(135, getStringResource(context, R.string.POLITIE), R.drawable.politie));

        categories.add(new Category(136, getStringResource(context, R.string.TATA), R.drawable.tata));
        categories.add(new Category(137, getStringResource(context, R.string.MAMA), R.drawable.mama));
        categories.add(new Category(138, getStringResource(context, R.string.FRATE), R.drawable.frate));
        categories.add(new Category(139, getStringResource(context, R.string.SORA), R.drawable.sora));
        categories.add(new Category(140, getStringResource(context, R.string.UNCHI), R.drawable.unchi));
        categories.add(new Category(141, getStringResource(context, R.string.MATUSA), R.drawable.matusa));
        categories.add(new Category(142, getStringResource(context, R.string.BUNIC_BUNICA), R.drawable.bunic));
        categories.add(new Category(143, getStringResource(context, R.string.NAS_NASA), R.drawable.nasa));

        categories.add(new Category(144, getStringResource(context, R.string.EU)));
        categories.add(new Category(145, getStringResource(context, R.string.TU)));
        categories.add(new Category(146, getStringResource(context, R.string.EL)));
        categories.add(new Category(147, getStringResource(context, R.string.EA)));
        categories.add(new Category(148, getStringResource(context, R.string.NOI)));
        categories.add(new Category(149, getStringResource(context, R.string.VOI)));
        categories.add(new Category(150, getStringResource(context, R.string.EI_ELE)));

        categories.add(new Category(151, getStringResource(context, R.string.VESEL), R.drawable.vesel));
        categories.add(new Category(152, getStringResource(context, R.string.SATURAT), R.drawable.saturat));
        categories.add(new Category(153, getStringResource(context, R.string.INCRUNTAT), R.drawable.incruntat));
        categories.add(new Category(154, getStringResource(context, R.string.TRIST), R.drawable.trist));
        categories.add(new Category(155, getStringResource(context, R.string.DEZAMAGIT), R.drawable.dezamagit));
        categories.add(new Category(156, getStringResource(context, R.string.NERVOS), R.drawable.nervos));
        categories.add(new Category(157, getStringResource(context, R.string.FERICIT), R.drawable.fericit));
        categories.add(new Category(158, getStringResource(context, R.string.SUPARAT), R.drawable.suparat));
        categories.add(new Category(159, getStringResource(context, R.string.INDURERAT), R.drawable.indurerat));
        categories.add(new Category(160, getStringResource(context, R.string.ENTUZIASMAT), R.drawable.entuziasmat));
        categories.add(new Category(161, getStringResource(context, R.string.INDRAGOSTIT), R.drawable.indragostit));
        categories.add(new Category(162, getStringResource(context, R.string.LINISTIT), R.drawable.linistit));
        categories.add(new Category(163, getStringResource(context, R.string.CUMINTE), R.drawable.cuminte));

        categories.add(new Category(164, getStringResource(context, R.string.CONSTRUI), R.drawable.construi));
        categories.add(new Category(165, getStringResource(context, R.string.SCRIE), R.drawable.scrie));
        categories.add(new Category(166, getStringResource(context, R.string.TRAGE), R.drawable.trage));
        categories.add(new Category(167, getStringResource(context, R.string.CITI), R.drawable.citi));
        categories.add(new Category(168, getStringResource(context, R.string.STA_JOS), R.drawable.sta_jos));
        // categories.add(new Category(169, getStringResource(context, R.string.LASA), R.drawable.lasa));
        categories.add(new Category(170, getStringResource(context, R.string.APASA), R.drawable.apasa));
        // categories.add(new Category(171, getStringResource(context, R.string.PUNE), R.drawable.pune));
        // categories.add(new Category(172, getStringResource(context, R.string.STRANGE), R.drawable.strange));
        // categories.add(new Category(173, getStringResource(context, R.string.SCOATE), R.drawable.scoate));
        categories.add(new Category(174, getStringResource(context, R.string.SPUNE), R.drawable.spune));
        categories.add(new Category(175, getStringResource(context, R.string.SE_UITA), R.drawable.se_uita));

        categories.add(new Category(176, getStringResource(context, R.string.VA_ROG), R.drawable.va_rog));
        categories.add(new Category(177, getStringResource(context, R.string.MULTUMESC), R.drawable.multumesc));
        categories.add(new Category(178, getStringResource(context, R.string.SCUZE), R.drawable.scuze));
        categories.add(new Category(179, getStringResource(context, R.string.HAI), R.drawable.hai));

        categories.add(new Category(180, getStringResource(context, R.string.DOMESTICE), R.drawable.animale_domestice));
        categories.add(new Category(181, getStringResource(context, R.string.SALBATICE), R.drawable.animale_salbatice));
        categories.add(new Category(182, getStringResource(context, R.string.ACASA), R.drawable.acasa));
        categories.add(new Category(183, getStringResource(context, R.string.AFARA), R.drawable.afara));
        categories.add(new Category(184, getStringResource(context, R.string.CLASA), R.drawable.clasa));
        categories.add(new Category(185, getStringResource(context, R.string.MAGAZIN), R.drawable.magazin));
        categories.add(new Category(186, getStringResource(context, R.string.ORAS), R.drawable.oras));
        categories.add(new Category(187, getStringResource(context, R.string.BANI), R.drawable.bani));
        categories.add(new Category(188, getStringResource(context, R.string.PRODUSE), R.drawable.produse));
        categories.add(new Category(189, getStringResource(context, R.string.MEMBRII_FAMILIEI), R.drawable.membrii_familiei));
        categories.add(new Category(190, getStringResource(context, R.string.PRONUME), R.drawable.pronume));

        categories.add(new Category(191, getStringResource(context, R.string.aceasta_este_prietena_mea), R.drawable.aceasta_este_prietena_mea));
        categories.add(new Category(192, getStringResource(context, R.string.acesta_este_colegul_meu), R.drawable.acesta_este_colegul_meu));
        categories.add(new Category(193, getStringResource(context, R.string.acesta_este_sotul_meu), R.drawable.acesta_este_sotul_meu));
        categories.add(new Category(194, getStringResource(context, R.string.am_inteles), R.drawable.am_inteles));
        categories.add(new Category(195, getStringResource(context, R.string.am_sa_va_fac_cunostinta), R.drawable.am_sa_va_fac_cunostinta));
        categories.add(new Category(196, getStringResource(context, R.string.as_dori_sa_iti_cer_sfatul), R.drawable.as_dori_sa_iti_cer_sfatul));

        categories.add(new Category(1001, getStringResource(context, R.string.ALFABET), R.drawable.alfabet));
        categories.add(new Category(1002, getStringResource(context, R.string.NUMERE), R.drawable.numere));
        categories.add(new Category(1003, getStringResource(context, R.string.CULORI), R.drawable.culori));
        categories.add(new Category(1004, getStringResource(context, R.string.ANIMALE), R.drawable.animale));
        categories.add(new Category(1005, getStringResource(context, R.string.OBIECTE), R.drawable.obiecte));
        categories.add(new Category(1006, getStringResource(context, R.string.PERSOANE), R.drawable.persoane));
        categories.add(new Category(1007, getStringResource(context, R.string.EMOTII), R.drawable.emotii));
        categories.add(new Category(1008, getStringResource(context, R.string.VERBE), R.drawable.verbe));
        categories.add(new Category(1009, getStringResource(context, R.string.FORMULE_DE_ADRESARE), R.drawable.formule_de_adresare));

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

    String getStringResource(Context context, int intResource) { return context.getResources().getString(intResource); }
}
