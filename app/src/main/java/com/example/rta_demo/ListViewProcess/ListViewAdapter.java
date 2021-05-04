package com.example.rta_demo.ListViewProcess;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.rta_demo.R;
import com.example.rta_demo.fileModel;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {
    private ArrayList<fileModel> files = new ArrayList<fileModel>();
    public ListViewAdapter(ArrayList<fileModel> files){
        this.files = files;
    }
    @Override
    public int getCount() {
        return files.size();
    }

    @Override
    public Object getItem(int position) {
        return files.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View viewFile;
        if (convertView == null) {
            viewFile = View.inflate(parent.getContext(), R.layout.item, null);
        } else viewFile = convertView;

        //Bind sữ liệu phần tử vào View
        fileModel file = (fileModel) getItem(position);
        ((TextView) viewFile.findViewById(R.id.textName)).setText(files.get(position).getName());
        ((TextView) viewFile.findViewById(R.id.textContent)).setText(files.get(position).getContent());
        ((TextView) viewFile.findViewById(R.id.textPath)).setText(files.get(position).getPath());


        return viewFile;
    }
}
