package com.example.goggolman;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.goggolman.Interfaces.FragmentListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class DictionaryFragment extends Fragment {
    private String value = "Hello Mr. Smart";
    private FragmentListener listener;

    ArrayAdapter<String> adapter;
    ListView dictList;
    private ArrayList<String> mSource = new ArrayList<String>();


    public DictionaryFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dictionary, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        dictList = view.findViewById(R.id.dictionaryList);
        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, mSource);

        dictList.setAdapter(adapter);

        dictList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (listener != null)
                listener.onItemClick(mSource.get(position));
            }
        });


    }

    public void resetDataSource(ArrayList<String> source){
        mSource = source;
        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, source);
        dictList.setAdapter(adapter);

    }

    public void filterValue(String value){
        //adapter.getFilter().filter(value);
        int size = adapter.getCount();
        for (int i = 0; i < size; i++){
            if (adapter.getItem(i).startsWith(value)){
                dictList.setSelection(i);
                break;
            }
        }
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
}
