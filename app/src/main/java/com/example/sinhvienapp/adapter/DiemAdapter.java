package com.example.sinhvienapp.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sinhvienapp.R;
import com.example.sinhvienapp.dao.DiemDAO;
import com.example.sinhvienapp.dao.MonHocDao;
import com.example.sinhvienapp.model.DiemDTO;

import java.util.ArrayList;
import java.util.List;

public class DiemAdapter  extends BaseAdapter {
    Activity context;
    int layout;
    List<DiemDTO> dsDiem;
    DiemDAO diemDAO;
    String maSV;



    public DiemAdapter(Activity context, int layout, List<DiemDTO> dsDiem,String maSV) {
        this.context = context;
        this.layout = layout;
        this.dsDiem = dsDiem;
        this.maSV = maSV;
    }


    @Override
    public int getCount() {
        return dsDiem.size();
    }

    @Override
    public Object getItem(int position) {
        return dsDiem.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public void reset(ArrayList<DiemDTO> all) {
        this.dsDiem = all;
    }


    private class ViewHolder {
        TextView tvTenMonHoc, tvDiem;
        ImageView ivDelete, ivEdit;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        DiemAdapter.ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(layout, null);
            holder = new DiemAdapter.ViewHolder();
            holder.tvTenMonHoc = convertView.findViewById(R.id.tvTenMH);
            holder.tvDiem = convertView.findViewById(R.id.tvDiem);
            holder.ivDelete = convertView.findViewById(R.id.imageViewdeletelop);
            holder.ivEdit = convertView.findViewById(R.id.imageeditlop);
            convertView.setTag(holder);
        } else {
            holder = (DiemAdapter.ViewHolder) convertView.getTag();
        }
        final DiemDTO diemDTO = dsDiem.get(position);


        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                diemDAO = new DiemDAO(context);
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                View view = inflater.inflate(R.layout.edit_diem, null);
                final EditText etDiem = view.findViewById(R.id.editdiem);
                Button btnSua = view.findViewById(R.id.btnCapNhat);



                //Đổ dữ liệu
                etDiem.setText(String.valueOf(diemDTO.getDiem()));



                builder.setView(view);
                final AlertDialog alertDialog = builder.create();
                alertDialog.show();

                btnSua.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            String diem = etDiem.getText().toString();


                            DiemDTO diemDTO1  = diemDTO;
                            diemDTO1.setDiem(Float.valueOf(diem));

                            //Update vào sql
                            if (diemDAO.update(diemDTO1)) {
                                Toast.makeText(context, "Sửa thành công!", Toast.LENGTH_SHORT).show();
                                dsDiem.clear();
                                dsDiem.addAll(diemDAO.getAll(maSV));
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
                builder.setMessage("Bạn muốn xóa điểm học phần này ?");
                //  final Lop lop = dslop.get(position);
                builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        diemDAO = new DiemDAO(context);
                        if (diemDAO.delete(diemDTO)) {
                            Toast.makeText(context, "Xóa thành công!", Toast.LENGTH_SHORT).show();
                            dsDiem.clear();
                            dsDiem.addAll(diemDAO.getAll(maSV));
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

        MonHocDao monHocDao = new MonHocDao(context);
        holder.tvTenMonHoc.setText(monHocDao.getMonHoc(diemDTO.getMaMH()).getTenmonhoc());
        holder.tvDiem.setText(String.valueOf(diemDTO.getDiem()));

        return convertView;
    }
}
