package com.example.cuahangdienthoai;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DienThoai2 extends ArrayAdapter<DienThoai> {
    Activity context;
    int resource;
    int position=-1;
    public DienThoai2(Activity context, int resource) {
        super(context, resource);
        this.context=context;
        this.resource=resource;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View customView=this.context.getLayoutInflater().inflate(this.resource,null);

        ImageView imgHinhdt1=(ImageView)customView.findViewById(R.id.imgHinhDT);
        TextView txtTendt1=(TextView)customView.findViewById(R.id.txtTenDT);
        TextView txtGiadt1=(TextView)customView.findViewById(R.id.txtGiaDT);
        TextView txtNhasanxuat1=(TextView)customView.findViewById(R.id.txtNhaSX);

        DienThoai sot=getItem(position);
        imgHinhdt1.setImageResource(sot.getHinhanhdt());
        txtTendt1.setText(sot.getTendt());
        txtGiadt1.setText(sot.getGiadt()+"VND");
        txtNhasanxuat1.setText(sot.getNhasanxuat());
        return customView;
    }
}
