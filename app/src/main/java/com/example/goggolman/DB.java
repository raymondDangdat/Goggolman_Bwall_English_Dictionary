package com.example.goggolman;

public class DB {
    public static String[] getData(int id){
        if (id == R.id.action_bwall_english){
            getBallEng();
        }else if (id == R.id.action_eng_bwall){
            getEngBwall();
        }
        return new String[0];
    }

    public static String[] getBallEng(){
        String[] source = new String[]{
                "a(I)"
                ,"a(II)"
                ,"aah(I)"
                ,"aah(II)"
                ,"aah(III)"
                ,"aahaha(I)"
                ,"aahaha(II)"
                ,"aan"
                ,"aan-wall"
                ,"smart"
                ,"lawumis"
        };
        return source;
    }

    public static String[] getEngBwall(){
        String[] source = new String[]{
                "a(I)"
                ,"a(II)"
                ,"aah(I)"
                ,"aah(II)"
                ,"aah(III)"
                ,"aahaha(I)"
                ,"aahaha(II)"
                ,"aan"
                ,"aan-wall"
                ,"smart"
                ,"lawumis",
                "a",
                "aa",
                "abandon",
                "good"
        };
        return source;
    }
}
