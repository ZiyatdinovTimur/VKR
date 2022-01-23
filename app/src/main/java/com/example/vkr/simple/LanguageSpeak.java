package com.example.vkr.simple;

import com.example.vkr.R;

import java.util.ArrayList;

public class LanguageSpeak  {

    private static ArrayList<LanguageSpeak> userArrayList = new ArrayList<>();

    private String id;
    private String name;

    public LanguageSpeak(String id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static void initUsers()
    {
        LanguageSpeak user1 = new LanguageSpeak("0", "English");
        userArrayList.add(user1);

        LanguageSpeak user2 = new LanguageSpeak("1", "Spanish");
        userArrayList.add(user2);
        LanguageSpeak user3 = new LanguageSpeak("2", "Chinese");
        userArrayList.add(user3);


        LanguageSpeak user4 = new LanguageSpeak("3", "Hindi");
        userArrayList.add(user4);

        LanguageSpeak user5 = new LanguageSpeak("4", "Arab");
        userArrayList.add(user5);

        LanguageSpeak user6 = new LanguageSpeak("5", "Bengal");
        userArrayList.add(user6);

        LanguageSpeak user7 = new LanguageSpeak("6", "Portuguese");
        userArrayList.add(user7);

        LanguageSpeak user8 = new LanguageSpeak("7", "Russian");
        userArrayList.add(user8);

        LanguageSpeak user9 = new LanguageSpeak("8", "Japanese");
        userArrayList.add(user9);

        LanguageSpeak user10= new LanguageSpeak("9", "Lakhnda");
        userArrayList.add(user10);

        LanguageSpeak user11 = new LanguageSpeak("10", "Malay");
        userArrayList.add(user11);

        LanguageSpeak user12 = new LanguageSpeak("11", "Turkish");
        userArrayList.add(user12);

        LanguageSpeak user13 = new LanguageSpeak("12", "Korean");
        userArrayList.add(user13);

        LanguageSpeak user14= new LanguageSpeak("13", "French");
        userArrayList.add(user14);

        LanguageSpeak user15 = new LanguageSpeak("14", "German");
        userArrayList.add(user15);

        LanguageSpeak user16 = new LanguageSpeak("15", "Vietnamese");
        userArrayList.add(user16);

        LanguageSpeak user17 = new LanguageSpeak("16", "Javanese");
        userArrayList.add(user17);

        LanguageSpeak user18 = new LanguageSpeak("17", "Italian");
        userArrayList.add(user18);

        LanguageSpeak user19 = new LanguageSpeak("18", "Persian");
        userArrayList.add(user19);

        LanguageSpeak user20 = new LanguageSpeak("19", "Polish");
        userArrayList.add(user20);

    }

    public int getImage()
    {
        switch (getId())
        {

            case "0":
                return R.drawable.g_b;

            case "1":
                return R.drawable.sp;
            case "2":
                return R.drawable.ch;
            case "3":
                return R.drawable.ind;
            case "4":
                return R.drawable.s_a;
            case "5":
                return R.drawable.bn;
            case "6":
                return R.drawable.pt;
            case "7":
                return R.drawable.ru;
            case "8":
                return R.drawable.jp;
            case "9":
                return R.drawable.pk;
            case "10":
                return R.drawable.ml;
            case "11":
                return R.drawable.tr;
            case "12":
                return R.drawable.kr;
            case "13":
                return R.drawable.fr;
            case "14":
                return R.drawable.gr;
            case "15":
                return R.drawable.vt;
            case "16":
                return R.drawable.indonesia;
            case "17":
                return R.drawable.it;
            case "18":
                return R.drawable.ir;
            case "19":
                return R.drawable.pl;
        }
        return R.drawable.g_b;
    }

    public static ArrayList<LanguageSpeak> getUserArrayList()
    {
        return userArrayList;
    }
}
