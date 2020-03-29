package com.example.goggolman.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.goggolman.Adapters.BookmarkAdapter;
import com.example.goggolman.Helper.DBHelper;
import com.example.goggolman.Interfaces.FragmentListener;
import com.example.goggolman.Interfaces.ListItemListener;
import com.example.goggolman.R;


public class BookmarkFragment extends Fragment {
    private FragmentListener listener;

    private DBHelper mDbHelper;


    public BookmarkFragment() {
        // Required empty public constructor
    }

    public static BookmarkFragment getNewInstance(DBHelper dbHelper){
        BookmarkFragment fragment = new BookmarkFragment();
        fragment.mDbHelper = dbHelper;

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
        return inflater.inflate(R.layout.fragment_bookmark, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setHasOptionsMenu(true);

        ListView bookmarkList = view.findViewById(R.id.bookmsrkList);
        final BookmarkAdapter adapter = new BookmarkAdapter(getActivity(), mDbHelper.getAllWordFromBookmark());
        bookmarkList.setAdapter(adapter);

        adapter.setOnItemClick(new ListItemListener() {
            @Override
            public void onItemClick(int position) {
                if (listener != null)
                    listener.onItemClick(String.valueOf(adapter.getItem(position)));
            }
        });


        adapter.setOnItemDeleteClick(new ListItemListener() {
            @Override
            public void onItemClick(int position) {
                    adapter.removeItem(position);
                    adapter.notifyDataSetChanged();
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


    public void setOnFragmentListener(FragmentListener listener){
        this.listener = listener;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_clear, menu);


    }
}
