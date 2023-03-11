package com.example.spendingmanagement.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
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
import com.example.spendingmanagement.model.PhanLoai;

import java.util.ArrayList;

public class PhanLoaiAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    ArrayList<PhanLoai> list;
    PhanLoaiDAO phanLoaiDAO;

    public static int TYPE_ITEM = 0;
    public static int TYPE_TEST = 1;

    public PhanLoaiAdapter(Context context, ArrayList<PhanLoai> list) {
        this.context = context;
        this.list = list;
        phanLoaiDAO = new PhanLoaiDAO(context);
    }

    public void addItem(PhanLoai item) {
        list.add(item);
        notifyItemInserted(list.size() - 1);
    }

    public void removeItem(PhanLoai item) {
        int index = 1;
    }

//    @NonNull
//    @Override
//    public PhanLoaiViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
//        View view = inflater.inflate(R.layout.phanloai_view_item, parent, false);
//        PhanLoaiViewholder viewholder = new PhanLoaiViewholder(view);
//        return viewholder;
//    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.phanloai_view_item, parent, false);
            return new PhanLoaiViewholder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_test, parent, false);
            return new TestViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TestViewHolder) {
            ((TestViewHolder) holder).binData();
        } else {
            PhanLoai phanLoai;
            if (position > 2) {
                phanLoai = list.get(position - 1);
            } else {
                phanLoai = list.get(position);
            }
            ((PhanLoaiViewholder) holder).bindData(phanLoai);
        }
        Log.e("onBindViewHolder" , position + "");
    }


    @Override
    public int getItemCount() {
        if (list.size() > 3) {
            return list.size() + 1;
        } else {
            return list.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (list.size() > 3) {
            if (position == 2) {
                return TYPE_TEST;
            } else {
                return TYPE_ITEM;
            }
        } else {
            return TYPE_ITEM;
        }
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

        public void bindData(PhanLoai phanLoai) {
            tenLoai.setText(phanLoai.getTenLoai());
            trangThai.setText(phanLoai.getTrangThai());

            editText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openDialogUpdate(phanLoai);
                }
            });

            deleteText.setOnClickListener(new View.OnClickListener() {
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
    }

    public class TestViewHolder extends RecyclerView.ViewHolder {
        public TestViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void binData() {

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
