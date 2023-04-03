package com.example.spendingmanagement.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spendingmanagement.R;
import com.example.spendingmanagement.databaseaccessobject.GiaoDichDAO;
import com.example.spendingmanagement.databaseaccessobject.PhanLoaiDAO;
import com.example.spendingmanagement.model.GiaoDich;
import com.example.spendingmanagement.model.PhanLoai;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class GiaoDichAdapter extends RecyclerView.Adapter<GiaoDichAdapter.GiaoDichViewholder> {
    Context context;
    ArrayList<GiaoDich> list;
    PhanLoaiDAO phanLoaiDAO;
    GiaoDichDAO giaoDichDAO;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy,MM,dd");

    public GiaoDichAdapter(Context context, ArrayList<GiaoDich> list) {
        this.context = context;
        this.list = list;
//        phanLoaiDAO = new PhanLoaiDAO(context);
//        giaoDichDAO = new GiaoDichDAO(context);
    }

    @NonNull
    @Override
    public GiaoDichViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.giaodich_view_item, parent, false);
        GiaoDichViewholder viewholder = new GiaoDichViewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull GiaoDichViewholder holder, int position) {
        PhanLoaiDAO phanLoaiDAO = new PhanLoaiDAO(context);
        GiaoDich giaoDich = list.get(position);
        int maLoai = giaoDich.getMaGD();
        PhanLoai phanLoai = phanLoaiDAO.getMaLoaiById(maLoai);

        holder.tieuDeGiaoDich.setText(giaoDich.getTieuDe());
        holder.trangThaiGiaoDich.setText(phanLoai.getTenLoai());
        holder.tienGiaoDich.setText(giaoDich.getTien() + "");
        holder.ngayGiaoDich.setText(simpleDateFormat.format(giaoDich.getNgay()));
        holder.motaGiaoDich.setText(giaoDich.getMoTa());

        holder.editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialogUpdate(giaoDich);
            }
        });
        holder.deleteText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (giaoDichDAO.delete(giaoDich.getMaLoai())) {
                    Toast.makeText(context, "Xoá thành công", Toast.LENGTH_LONG).show();
                    //refresdata
                    list.clear();
                    list.addAll(giaoDichDAO.getAll());
                    notifyDataSetChanged();
                } else {
                    Toast.makeText(context, "Xóa không thành công, vui lòng kiểm tra lại",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class GiaoDichViewholder extends RecyclerView.ViewHolder {
        TextView tieuDeGiaoDich, ngayGiaoDich, tienGiaoDich, motaGiaoDich, trangThaiGiaoDich;
        ImageView editText;
        ImageView deleteText;
        CardView cardViewGiaodich;

        public GiaoDichViewholder(@NonNull View itemView) {
            super(itemView);
            tieuDeGiaoDich = itemView.findViewById(R.id.textview_tieude_giaodich);
            ngayGiaoDich = itemView.findViewById(R.id.textview_ngaythang_giaodich);
            tienGiaoDich = itemView.findViewById(R.id.textview_tien_giaodich);
            motaGiaoDich = itemView.findViewById(R.id.textview_mota_giaodich);
            trangThaiGiaoDich = itemView.findViewById(R.id.textview_trangthai_giaodich);

            editText = itemView.findViewById(R.id.edit_giaodich);
            deleteText = itemView.findViewById(R.id.delete_giaodich);
            cardViewGiaodich = itemView.findViewById(R.id.cardview_giaodich);
        }
    }
    public void openDialogUpdate(GiaoDich giaoDich) {
        // khoi tao doi tuong dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        //nap giao dien
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.giaodich_update, null);
        builder.setView(view);

        Dialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();

        EditText updateTieuDe = view.findViewById(R.id.edittext_capnhat_tenloai);
        EditText updateNgayThang = view.findViewById(R.id.edittext_capnhat_ngaythang);
        EditText updateSoTien = view.findViewById(R.id.edittext_capnhat_sotien);
        EditText updateMoTa = view.findViewById(R.id.edittext_capnhat_mota_giaodich);

        Button buttonUpdate = view.findViewById(R.id.button_capnhat_giaodich);
        Button buttonCancel = view.findViewById(R.id.button_capnhat_huy_giaodich);
        Spinner spinnerUpdate = view.findViewById(R.id.spiner_capnhat_loaithuchi);

        //khoi tao phuong thuc thu chi
//        String[] status = {"Thu", "Chi"};
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(PhanLoaiActivity.this,
//                android.R.layout.simple_spinner_item, status);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerThemMoi.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                R.array.planets_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerUpdate.setAdapter(adapter);

        //xet text
        updateTieuDe.setText(giaoDich.getTieuDe());
        updateNgayThang.setText(giaoDich.getNgay().toString());
        updateSoTien.setText(giaoDich.getTien() + "");
        updateMoTa.setText(giaoDich.getTieuDe());

        for (int i = 0; i < 2; i++) {
                spinnerUpdate.setSelection(i);
        }

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                giaoDich.setMoTa(updateMoTa.getText().toString());
                try {
                    giaoDich.setNgay(simpleDateFormat.parse(updateNgayThang.getText().toString()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                giaoDich.setTien(Double.parseDouble(updateSoTien.getText().toString()));
                giaoDich.setMoTa(updateMoTa.getText().toString());
                giaoDich.setMaLoai( (int)spinnerUpdate.getSelectedItem());

                if (giaoDichDAO.update(giaoDich)) {
                    Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_LONG).show();
                    dialog.dismiss();

                    list.clear();
                    list.addAll(giaoDichDAO.getAll());
                    notifyDataSetChanged();
                } else {
                    Toast.makeText(context, "Cập nhật thất bại mời bạn nhập lại", Toast.LENGTH_SHORT).show();
                }
            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
}
