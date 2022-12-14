package com.example.sqldatabase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.zip.Inflater;

public class DSBanAdapter extends BaseAdapter {

    private MainActivity context;
    private int layout;
    private List<DSBan> dsBanList;

    public DSBanAdapter(MainActivity context, int layout, List<DSBan> dsBanList) {
        this.context = context;
        this.layout = layout;
        this.dsBanList = dsBanList;
    }

    @Override
    public int getCount() {
        return dsBanList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder{
        TextView txtTen;
        ImageView imgDelete, imgEdit;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;

        if (view == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder.txtTen = (TextView) view.findViewById(R.id.textviewTen);
            holder.imgDelete = (ImageView) view.findViewById(R.id.imageviewDelete);
            holder.imgEdit = (ImageView) view.findViewById(R.id.imageviewEdit);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        final DSBan dsBan = dsBanList.get(i);

        holder.txtTen.setText(dsBan.getTenBan());

        // bắt sự kiện xóa và sửa
        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.DialogSuaBan(dsBan.getTenBan(), dsBan.getIdBan());
            }
        });

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.DialogXoaBan(dsBan.getTenBan(), dsBan.getIdBan());
            }
        });

        return view;
    }
}
