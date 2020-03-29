package com.example.goggolman.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.goggolman.Helper.DBHelper;
import com.example.goggolman.R;
import com.example.goggolman.Word;

public class DetailFragment extends Fragment {
    private String value = "";

    private TextView tvWord;
    private ImageButton btnBookkmark, btnVolume;
    private WebView webViewTranslate;
    private DBHelper mDBHelper;
    private int mDicType;

    public DetailFragment() {
        // Required empty public constructor
    }

    public static DetailFragment getNewInstance(String value, DBHelper dbHelper, int dicType){
        DetailFragment fragment = new DetailFragment();

        fragment.mDBHelper =dbHelper;
        fragment.mDicType = dicType;
        fragment.value = value;
        return fragment;

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvWord = view.findViewById(R.id.tvWord);
        webViewTranslate = view.findViewById(R.id.webviewWordTranslate);
        btnBookkmark = view.findViewById(R.id.btnBookmark);
        btnVolume = view.findViewById(R.id.btnVolume);

        final Word word = mDBHelper.getWord(value, mDicType);
        tvWord.setText(word.key);
        webViewTranslate.loadDataWithBaseURL(null, word.value, "text/html","utf-8", null);

        Word bookmarkWord = mDBHelper.getWordFromBookmark(value);
        int isMark = bookmarkWord == null? 0:1;

        btnBookkmark.setTag(isMark);

        int icon = bookmarkWord == null? R.drawable.ic_bookmark_border : R.drawable.ic_bookmark_black;
        btnBookkmark.setImageResource(icon);

        btnBookkmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = (int)btnBookkmark.getTag();

                if (i == 0){
                   btnBookkmark.setImageResource(R.drawable.ic_bookmark_black);
                   btnBookkmark.setTag(1);
                   mDBHelper.addBookmark(word);
                }else if (i == 1){
                    btnBookkmark.setImageResource(R.drawable.ic_bookmark_border);
                    btnBookkmark.setTag(0);
                    mDBHelper.removeBookmark(word);
                }
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

}
