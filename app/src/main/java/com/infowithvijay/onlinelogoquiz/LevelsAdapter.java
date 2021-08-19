package com.infowithvijay.onlinelogoquiz;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

class LevelsAdapter extends BaseAdapter {

    private int numofLevels;

    public LevelsAdapter(int numofLevels){

        this.numofLevels = numofLevels;
    }


    @Override
    public int getCount() {
        return numofLevels;
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

            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.level_item_layout,parent,false);


        }else {
            view = convertView;
        }


        ((TextView) view.findViewById(R.id.level_name)).setText(String.valueOf(position+1));

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(parent.getContext(),QuestionActivity.class);

                intent.putExtra("LEVELNO",position + 1);

                parent.getContext().startActivity(intent);

            }
        });

        return view;
    }
}
