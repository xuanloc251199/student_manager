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
import com.example.sinhvienapp.dao.MonHocDao;
import com.example.sinhvienapp.model.MonHoc;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MonHocAdapter extends BaseAdapter implements Filterable {
    Activity context;
    int layout;
    ArrayList<MonHoc> dsMonhoc;
    ArrayList<MonHoc> dsSortMonhoc;
    private Filter monhocFilter;
    MonHocDao monDao;
    Animation animation;


    public MonHocAdapter(Activity context, int layout, ArrayList<MonHoc> dsMonhoc) {
        this.context = context;
        this.layout = layout;
        this.dsMonhoc = dsMonhoc;
        this.dsSortMonhoc = dsSortMonhoc;
    }


    @Override
    public int getCount() {
        return dsMonhoc.size();

    }

    @Override
    public Object getItem(int position) {
        return dsMonhoc.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public void changeDataSet(ArrayList<MonHoc> dsl) {
        this.dsMonhoc = dsl;
        notifyDataSetChanged();
    }

    public void resetData() {
        this.dsMonhoc = dsSortMonhoc;
    }

    @Override
    public Filter getFilter() {
        if (monhocFilter == null) {
            monhocFilter = new MonHocAdapter.CustomFilter();
        }
        return monhocFilter;
    }

    public class CustomFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint == null || constraint.length() == 0) {
                results.values = dsSortMonhoc;
                results.count = dsSortMonhoc.size();
            } else {
                ArrayList<MonHoc> dscnmoi = new ArrayList<MonHoc>();
                for (MonHoc monHoc : dsMonhoc) {
                    if (monHoc.getTenmonhoc().toUpperCase().startsWith(constraint.toString().toUpperCase())) {
                        dscnmoi.add(monHoc);
                    }
                }
                results.values = dscnmoi;
                results.count = dsMonhoc.size();
            }
            return results;

        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.count == 0) {
                notifyDataSetInvalidated();
            } else {
                dsMonhoc = (ArrayList<MonHoc>) results.values;
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
        MonHocAdapter.ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(layout, null);
            holder = new MonHocAdapter.ViewHolder();
            holder.linearLayout=convertView.findViewById(R.id.linearLayoutSuaLop);
            holder.tvMaChuuyenNganh = convertView.findViewById(R.id.tvMaChuuyenNganh);
            holder.tvTenChuyenNganh = convertView.findViewById(R.id.tvTenChuyenNganh);
            holder.ivDelete = convertView.findViewById(R.id.imageViewdeletelop);
            holder.imageViewhinh = convertView.findViewById(R.id.imageView);
            holder.ivEdit = convertView.findViewById(R.id.imageeditlop);
            convertView.setTag(holder);
        } else {
            holder = (MonHocAdapter.ViewHolder) convertView.getTag();
        }
        final MonHoc monHoc = dsMonhoc.get(position);


        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                monDao = new MonHocDao(context);
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                View view = inflater.inflate(R.layout.edit_monhoc, null);
                final EditText etmanganh = view.findViewById(R.id.edteditMaLop);
                final EditText ettennganh = view.findViewById(R.id.edteditTenLop);
                Button btnSua = view.findViewById(R.id.btnCapNhat);
                Button btnLai = view.findViewById(R.id.btnEdNhapLai);


                //Đổ dữ liệu
                etmanganh.setText(monHoc.getMaMH());
                ettennganh.setText(monHoc.getTenmonhoc());


                builder.setView(view);
                final AlertDialog alertDialog = builder.create();
                alertDialog.show();

                btnSua.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            String ma = etmanganh.getText().toString();
                            String ten = ettennganh.getText().toString();

                            MonHoc monHoc1 = new MonHoc(ma, ten);

                            //Update vào sql
                            if (monDao.update(monHoc1)) {
                                Toast.makeText(context, "Sửa thành công!", Toast.LENGTH_SHORT).show();
                                dsMonhoc.clear();
                                dsMonhoc.addAll(monDao.getAll());
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
                builder.setMessage("Bạn có chắc chắn xóa ngành  "+ monHoc.getTenmonhoc()+" không ? \nCảnh báo nếu bạn xóa lớp "+
                        monHoc.getTenmonhoc()+  " thì hệ thống sẽ xóa điểm sinh viên trong môn học này  ");
                //  final Lop lop = dslop.get(position);
                builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        monDao = new MonHocDao(context);
                        if (monDao.delete(monHoc.getMaMH())) {
                            Toast.makeText(context, "Xóa thành công!", Toast.LENGTH_SHORT).show();
                            dsMonhoc.clear();
                            dsMonhoc.addAll(monDao.getAll());
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


        holder.tvMaChuuyenNganh.setText(monHoc.getMaMH());
        holder.tvTenChuyenNganh.setText(monHoc.getTenmonhoc());
        holder.imageViewhinh.setImageResource(R.drawable.logo);
        return convertView;
    }
}
