package com.example.spendingmanagement.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import com.example.spendingmanagement.databaseaccessobject.PhanLoaiDAO;
import com.example.spendingmanagement.databinding.PhanloaiAddBinding;
import com.example.spendingmanagement.model.PhanLoai;
import com.example.spendingmanagement.model.PhanLoaiActivity;

import java.util.ArrayList;

public class PhanLoaiAdapter extends RecyclerView.Adapter<PhanLoaiAdapter.PhanLoaiViewholder> {
    Context context;
    ArrayList<PhanLoai> list;
    PhanLoaiDAO phanLoaiDAO;

    public PhanLoaiAdapter(Context context, ArrayList<PhanLoai> list) {
        this.context = context;
        this.list = list;
        phanLoaiDAO = new PhanLoaiDAO(context);
    }

    @NonNull
    @Override
    public PhanLoaiViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.phanloai_view_item, parent, false);
        PhanLoaiViewholder viewholder = new PhanLoaiViewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull PhanLoaiViewholder holder, int position) {
        PhanLoai phanLoai = list.get(position);
        holder.tenLoai.setText(phanLoai.getTenLoai());
        holder.trangThai.setText(phanLoai.getTrangThai());

        holder.editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialogUpdate(phanLoai);
            }
        });

        holder.deleteText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (phanLoaiDAO.delete(phanLoai.getMaLoai())) {
                    Toast.makeText(context, "Xoá thành công", Toast.LENGTH_LONG).show();
                    //refresdata
                    list.clear();
                    list.addAll(phanLoaiDAO.getAll());
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

    public class PhanLoaiViewholder extends RecyclerView.ViewHolder {
        TextView tenLoai;
        TextView trangThai;
        ImageView editText;
        ImageView deleteText;
        CardView cardViewPhanLoai;

        public PhanLoaiViewholder(@NonNull View itemView) {
            super(itemView);
            tenLoai = itemView.findViewById(R.id.textview_tenloai);
            trangThai = itemView.findViewById(R.id.textview_trangthai);
            editText = itemView.findViewById(R.id.edit_text);
            deleteText = itemView.findViewById(R.id.delete_text);
            cardViewPhanLoai = itemView.findViewById(R.id.cardview_phanloai);
        }
    }

    public void openDialogUpdate(PhanLoai phanLoai) {
        // khoi tao doi tuong dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        //nap giao dien
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.phanloai_update, null);
        builder.setView(view);

        Dialog dialog = builder.create();
        dialog.show();

        EditText editTextUpdate = view.findViewById(R.id.edittext_capnhat_tenloai);
        Button buttonUpdate = view.findViewById(R.id.button_capnhat_phanloai);
        Button buttonCancel = view.findViewById(R.id.button_huy_capnhat_phanloai);
        Spinner spinnerUpdate = view.findViewById(R.id.spiner_capnhat_phanloai);

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
        editTextUpdate.setText(phanLoai.getTenLoai());

        String[] status = {"Phiếu thu", "Phiếu chi"};
        for (int i = 0; i < status.length; i++) {
            if (phanLoai.getTrangThai().equalsIgnoreCase(status[i])) {
                spinnerUpdate.setSelection(i);
            }
        }

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phanLoai.setTenLoai(editTextUpdate.getText().toString());
                phanLoai.setTrangThai((String) spinnerUpdate.getSelectedItem());

                if (phanLoaiDAO.update(phanLoai)) {
                    Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_LONG).show();
                    dialog.dismiss();

                    list.clear();
                    list.addAll(phanLoaiDAO.getAll());
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
