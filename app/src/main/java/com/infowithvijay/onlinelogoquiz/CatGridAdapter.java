package com.infowithvijay.onlinelogoquiz;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

class CatGridAdapter extends BaseAdapter {

    private List<String> catList;

    public CatGridAdapter(List<String> catList){

        this.catList = catList;

    }

    @Override
    public int getCount() {
        return catList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        View view;
        if (convertView == null){

            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cat_item_layout,parent,false);


        }else {
            view = convertView;
        }

        ((TextView) view.findViewById(R.id.catName)).setText(catList.get(position));


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(parent.getContext(),LevelsActivity.class);
                intent.putExtra("CATEGORY",catList.get(position));
                intent.putExtra("CATEGROY_ID",position + 1);

                parent.getContext().startActivity(intent);


            }
        });


       return view;
    }

}
