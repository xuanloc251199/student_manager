package com.example.sinhvienapp.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sinhvienapp.R;
import com.example.sinhvienapp.dao.ChuyenNganhDao;
import com.example.sinhvienapp.dao.LopDao;
import com.example.sinhvienapp.dao.SinhVienDao;
import com.example.sinhvienapp.model.ChuyenNganh;
import com.example.sinhvienapp.model.Lop;
import com.example.sinhvienapp.model.SinhVien;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class SinhVienAdapter extends BaseAdapter implements Filterable {
    private Context context;
    private ArrayList<SinhVien> sinhVienList;
    private ArrayList<SinhVien> filteredList;
    private SinhVienDao sinhVienDao;
    private Filter sinhVienFilter;
    private LopDao lopDao;
    private ChuyenNganhDao chuyenNganhDao;

    public SinhVienAdapter(Context context, ArrayList<SinhVien> sinhVienList) {
        this.context = context;
        this.sinhVienList = sinhVienList;
        this.filteredList = new ArrayList<>(sinhVienList);
        sinhVienDao = new SinhVienDao(context);
    }

    @Override
    public int getCount() {
        return sinhVienList.size();
    }

    @Override
    public SinhVien getItem(int position) {
        return sinhVienList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Filter getFilter() {
        if (sinhVienFilter == null) {
            sinhVienFilter = new SinhVienFilter();
        }
        return sinhVienFilter;
    }

    // Định nghĩa interface cho việc chọn ảnh
    public interface ImageSelectListener {
        void onImageSelect(CircleImageView imgAvata);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.dong_sinhvien, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        SinhVien sinhVien = sinhVienList.get(position);
        holder.bindData(sinhVien);

        holder.ivEdit.setOnClickListener(v -> showEditDialog(sinhVien));
        holder.ivDelete.setOnClickListener(v -> confirmDelete(sinhVien, position));


        return convertView;
    }

    private void showEditDialog(SinhVien sinhVien) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.sua_sinhvien, null);
        EditText etMasv = view.findViewById(R.id.edMaSvedit);
        EditText etTensv = view.findViewById(R.id.edTenSvedit);
        EditText etemail = view.findViewById(R.id.edemail);
        EditText ethinh = view.findViewById(R.id.edhinh);
        CircleImageView imgAvata = view.findViewById(R.id.imageView_avata_edit);
        Spinner spMalop = view.findViewById(R.id.spEdmalop);
        Spinner spNganh = view.findViewById(R.id.spNganh);
        Button btnSua = view.findViewById(R.id.btnEdSua);
        Button btnHuy = view.findViewById(R.id.btnHuyeditSV);

        populateSpinners(spMalop, spNganh);
        populateEditFields(sinhVien, etMasv, etTensv, etemail, ethinh, imgAvata, spMalop, spNganh);

        btnSua.setOnClickListener(v -> updateSinhVien(sinhVien, etMasv, etTensv, etemail, ethinh, spMalop, spNganh));
        btnHuy.setOnClickListener(v -> builder.create().dismiss());

        // Đặt sự kiện click để mở thư viện ảnh

        builder.setView(view).create().show();
    }

    private void populateSpinners(Spinner spMalop, Spinner spNganh) {
        lopDao = new LopDao(context);
        chuyenNganhDao = new ChuyenNganhDao(context);

        ArrayAdapter<Lop> lopAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, lopDao.getAll());
        spMalop.setAdapter(lopAdapter);

        ArrayAdapter<ChuyenNganh> nganhAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, chuyenNganhDao.getAll());
        spNganh.setAdapter(nganhAdapter);
    }

    public void resetData() {
        sinhVienList = new ArrayList<>(filteredList);
        notifyDataSetChanged();
    }

    private void populateEditFields(SinhVien sinhVien, EditText etMasv, EditText etTensv, EditText etemail, EditText ethinh, CircleImageView imgAvata, Spinner spMalop, Spinner spNganh) {
        etMasv.setText(sinhVien.getMaSv());
        etTensv.setText(sinhVien.getTenSv());
        etemail.setText(sinhVien.getEmail());
        ethinh.setText(sinhVien.getHinh());
        imgAvata.setImageResource(context.getResources().getIdentifier(sinhVien.getHinh(), "drawable", context.getPackageName()));
    }

    private void updateSinhVien(SinhVien sinhVien, EditText etMasv, EditText etTensv, EditText etemail, EditText ethinh, Spinner spMalop, Spinner spNganh) {
        sinhVien.setMaSv(etMasv.getText().toString());
        sinhVien.setTenSv(etTensv.getText().toString());
        sinhVien.setEmail(etemail.getText().toString());
        sinhVien.setHinh(ethinh.getText().toString());
        sinhVien.setMaLop(((Lop) spMalop.getSelectedItem()).getMaLop());
        sinhVien.setMaChuyenNganh(((ChuyenNganh) spNganh.getSelectedItem()).getMaChuyenNganh());

        if (sinhVienDao.update(sinhVien)) {
            Toast.makeText(context, "Sửa thành công!", Toast.LENGTH_LONG).show();
            notifyDataSetChanged();
        } else {
            Toast.makeText(context, "Sửa thất bại!", Toast.LENGTH_LONG).show();
        }
    }

    private void confirmDelete(SinhVien sinhVien, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Thông báo")
                .setMessage("Bạn có chắc chắn xóa sinh viên " + sinhVien.getTenSv() + " không?")
                .setNegativeButton("Yes", (dialog, which) -> deleteSinhVien(sinhVien, position))
                .setPositiveButton("No", (dialog, which) -> dialog.dismiss())
                .create().show();
    }

    private void deleteSinhVien(SinhVien sinhVien, int position) {
        if (sinhVienDao.delete(sinhVien)) {
            Toast.makeText(context, "Xóa thành công!", Toast.LENGTH_SHORT).show();
            sinhVienList.remove(position);
            notifyDataSetChanged();
        } else {
            Toast.makeText(context, "Xóa thất bại!", Toast.LENGTH_SHORT).show();
        }
    }

    private class ViewHolder {
        TextView txtTensv, txtMasv, txtemail, txtMalop, txtTenNganh;
        CircleImageView imageView_Avatalist;
        ImageView ivDelete, ivEdit;

        ViewHolder(View view) {
            txtMasv = view.findViewById(R.id.tvmasv);
            txtTensv = view.findViewById(R.id.tvtensv);
            txtemail = view.findViewById(R.id.tvemail);
            txtMalop = view.findViewById(R.id.tvmalop);
            txtTenNganh = view.findViewById(R.id.tvTenNganh);
            imageView_Avatalist = view.findViewById(R.id.imageViewHinh);
            ivDelete = view.findViewById(R.id.imageViewDelete);
            ivEdit = view.findViewById(R.id.imageViewedit);
        }

        void bindData(SinhVien sinhVien) {
            txtMasv.setText(sinhVien.getMaSv());
            txtTensv.setText(sinhVien.getTenSv());
            txtemail.setText(sinhVien.getEmail());
            txtMalop.setText(sinhVien.getMaLop());
            txtTenNganh.setText(sinhVien.getMaChuyenNganh());
            loadImage(sinhVien.getHinh());
        }

        void loadImage(String imageName) {
            try {
                FileInputStream fis = context.openFileInput(imageName);
                Bitmap bitmap = BitmapFactory.decodeStream(fis);
                imageView_Avatalist.setImageBitmap(bitmap);
                fis.close();
            } catch (IOException e) {
                imageView_Avatalist.setImageResource(R.drawable.avatamacdinh);
                Toast.makeText(context, "Không tìm thấy hình ảnh đã lưu.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class SinhVienFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint == null || constraint.length() == 0) {
                results.values = filteredList;
                results.count = filteredList.size();
            } else {
                ArrayList<SinhVien> filteredSinhVien = new ArrayList<>();
                for (SinhVien sv : sinhVienList) {
                    if (sv.getTenSv().toUpperCase().startsWith(constraint.toString().toUpperCase())) {
                        filteredSinhVien.add(sv);
                    }
                }
                results.values = filteredSinhVien;
                results.count = filteredSinhVien.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            sinhVienList = (ArrayList<SinhVien>) results.values;
            notifyDataSetChanged();
        }
    }
}
