package com.example.vkr.simple;

import com.example.vkr.R;

import java.util.ArrayList;

public class LanguageLearn {

    private static ArrayList<LanguageLearn> userArrayList = new ArrayList<>();

    private String id;
    private String name;

    public LanguageLearn(String id, String name)
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
        LanguageLearn user1 = new LanguageLearn("0", "English");
        userArrayList.add(user1);

        LanguageLearn user2 = new LanguageLearn
                ("1", "Spanish");
        userArrayList.add(user2);
        LanguageLearn user3 = new LanguageLearn("2", "Chinese");
        userArrayList.add(user3);

        LanguageLearn user4 = new LanguageLearn("3", "Hindi");
        userArrayList.add(user4);

        LanguageLearn user5 = new LanguageLearn("4", "Arab");
        userArrayList.add(user5);

        LanguageLearn user6 = new LanguageLearn("5", "Bengal");
        userArrayList.add(user6);

        LanguageLearn user7 = new LanguageLearn("6", "Portuguese");
        userArrayList.add(user7);

        LanguageLearn user8 = new LanguageLearn("7", "Russian");
        userArrayList.add(user8);

        LanguageLearn user9 = new LanguageLearn("8", "Japanese");
        userArrayList.add(user9);

        LanguageLearn user10= new LanguageLearn("9", "Lakhnda");
        userArrayList.add(user10);

        LanguageLearn user11 = new LanguageLearn("10", "Malay");
        userArrayList.add(user11);

        LanguageLearn user12 = new LanguageLearn("11", "Turkish");
        userArrayList.add(user12);

        LanguageLearn user13 = new LanguageLearn("12", "Korean");
        userArrayList.add(user13);

        LanguageLearn user14= new LanguageLearn("13", "French");
        userArrayList.add(user14);

        LanguageLearn user15 = new LanguageLearn("14", "German");
        userArrayList.add(user15);

        LanguageLearn user16 = new LanguageLearn("15", "Vietnamese");
        userArrayList.add(user16);

        LanguageLearn user17 = new LanguageLearn("16", "Javanese");
        userArrayList.add(user17);

        LanguageLearn user18 = new LanguageLearn("17", "Italian");
        userArrayList.add(user18);

        LanguageLearn user19 = new LanguageLearn("18", "Persian");
        userArrayList.add(user19);

        LanguageLearn user20 = new LanguageLearn("19", "Polish");
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

    public static ArrayList<LanguageLearn> getUserArrayList()
    {
        return userArrayList;
    }
}
