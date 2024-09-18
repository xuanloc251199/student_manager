package com.example.sinhvienapp.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sinhvienapp.R;
import com.example.sinhvienapp.dao.ChuyenNganhDao;
import com.example.sinhvienapp.model.ChuyenNganh;


import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChuyenNganhAdapter extends BaseAdapter implements Filterable {
    Activity context;
    int layout;
    ArrayList<ChuyenNganh> dsChuyenNganh;
    ArrayList<ChuyenNganh> dsSortChuyenNganh;
    private Filter chuyenNFilter;
    ChuyenNganhDao chuyenDao;
    // ArrayList<SinhVien>dssv=new ArrayList<>();
    Animation animation;


    public ChuyenNganhAdapter(Activity context, int layout, ArrayList<ChuyenNganh> dsChuyenNganh) {
        this.context = context;
        this.layout = layout;
        this.dsChuyenNganh = dsChuyenNganh;
        this.dsSortChuyenNganh = dsSortChuyenNganh;
    }


    @Override
    public int getCount() {
        return dsChuyenNganh.size();

    }

    @Override
    public Object getItem(int position) {
        return dsChuyenNganh.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public void changeDataSet(ArrayList<ChuyenNganh> dsl) {
        this.dsChuyenNganh = dsl;
        notifyDataSetChanged();
    }

    public void resetData() {
        this.dsChuyenNganh = dsSortChuyenNganh;
    }

    @Override
    public Filter getFilter() {
        if (chuyenNFilter == null) {
            chuyenNFilter = new ChuyenNganhAdapter.CustomFilter();
        }
        return chuyenNFilter;
    }

    public class CustomFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint == null || constraint.length() == 0) {
                results.values = dsSortChuyenNganh;
                results.count = dsSortChuyenNganh.size();
            } else {
                ArrayList<ChuyenNganh> dscnmoi = new ArrayList<ChuyenNganh>();
                for (ChuyenNganh chuyenNganh : dsChuyenNganh) {
                    if (chuyenNganh.getTenChuyenNganh().toUpperCase().startsWith(constraint.toString().toUpperCase())) {
                        dscnmoi.add(chuyenNganh);
                    }
                }
                results.values = dscnmoi;
                results.count = dsChuyenNganh.size();
            }
            return results;

        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.count == 0) {
                notifyDataSetInvalidated();
            } else {
                dsChuyenNganh = (ArrayList<ChuyenNganh>) results.values;
                notifyDataSetChanged();
            }
        }
    }

    private class ViewHolder {
        TextView tvMaChuuyenNganh, tvTenChuyenNganh;
        CircleImageView imageViewhinh;
        ImageView ivDelete, ivEdit;
        LinearLayout linearLayout;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ChuyenNganhAdapter.ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(layout, null);
            holder = new ChuyenNganhAdapter.ViewHolder();
            holder.linearLayout=convertView.findViewById(R.id.linearLayoutSuaLop);
            holder.tvMaChuuyenNganh = convertView.findViewById(R.id.tvMaChuuyenNganh);
            holder.tvTenChuyenNganh = convertView.findViewById(R.id.tvTenChuyenNganh);
            holder.ivDelete = convertView.findViewById(R.id.imageViewdeletelop);
            holder.imageViewhinh = convertView.findViewById(R.id.imageView);
            holder.ivEdit = convertView.findViewById(R.id.imageeditlop);
            convertView.setTag(holder);
        } else {
            holder = (ChuyenNganhAdapter.ViewHolder) convertView.getTag();
        }
        final ChuyenNganh chuyenNganh = dsChuyenNganh.get(position);


        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                chuyenDao = new ChuyenNganhDao(context);
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                View view = inflater.inflate(R.layout.edit_nganh, null);
                final EditText etmanganh = view.findViewById(R.id.edteditMaLop);
                final EditText ettennganh = view.findViewById(R.id.edteditTenLop);
                Button btnSua = view.findViewById(R.id.btnCapNhat);



                //Đổ dữ liệu
                etmanganh.setText(chuyenNganh.getMaChuyenNganh());
                ettennganh.setText(chuyenNganh.getTenChuyenNganh());


                builder.setView(view);
                final AlertDialog alertDialog = builder.create();
                alertDialog.show();

                btnSua.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            String ma = etmanganh.getText().toString();
                            String ten = ettennganh.getText().toString();

                            ChuyenNganh chuyenNganh1 = new ChuyenNganh(ma, ten);

                            //Update vào sql
                            if (chuyenDao.update(chuyenNganh1)) {
                                Toast.makeText(context, "Sửa thành công!", Toast.LENGTH_SHORT).show();
                                dsChuyenNganh.clear();
                                dsChuyenNganh.addAll(chuyenDao.getAll());
                                notifyDataSetChanged();
                                alertDialog.dismiss();
                            } else {
                                Toast.makeText(context, "Sửa thất bại!", Toast.LENGTH_SHORT).show();

                            }

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                });
            }
        });
        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Thông báo");
                builder.setMessage("Bạn có chắc chắn xóa ngành  "+ chuyenNganh.getTenChuyenNganh()+" không ? \nCảnh báo nếu bạn xóa lớp "+
                        chuyenNganh.getTenChuyenNganh()+  " thì hệ thống sẽ xóa toàn bộ sinh viên trong ngành  "+ chuyenNganh.getTenChuyenNganh());
                //  final Lop lop = dslop.get(position);
                builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        chuyenDao = new ChuyenNganhDao(context);
                        if (chuyenDao.delete(chuyenNganh.getMaChuyenNganh())) {
                            Toast.makeText(context, "Xóa thành công!", Toast.LENGTH_SHORT).show();
                            dsChuyenNganh.clear();
                            dsChuyenNganh.addAll(chuyenDao.getAll());
                            notifyDataSetChanged();
                            dialog.dismiss();
                        } else {
                            Toast.makeText(context, "Xóa thất bại!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                final AlertDialog myAlert = builder.create();
                myAlert.show();
            }
        });


        holder.tvMaChuuyenNganh.setText(chuyenNganh.getMaChuyenNganh());
        holder.tvTenChuyenNganh.setText(chuyenNganh.getTenChuyenNganh());
        holder.imageViewhinh.setImageResource(R.drawable.logo);
        return convertView;
    }
}
