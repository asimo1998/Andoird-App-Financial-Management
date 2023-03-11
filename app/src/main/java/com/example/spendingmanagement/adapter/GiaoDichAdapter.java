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
        phanLoaiDAO = new PhanLoaiDAO(context);
        giaoDichDAO = new GiaoDichDAO(context);
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
}
