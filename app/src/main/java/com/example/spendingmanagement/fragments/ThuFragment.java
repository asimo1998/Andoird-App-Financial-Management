package com.example.spendingmanagement.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spendingmanagement.R;
import com.example.spendingmanagement.adapter.GiaoDichAdapter;
import com.example.spendingmanagement.databaseaccessobject.GiaoDichDAO;
import com.example.spendingmanagement.databaseaccessobject.PhanLoaiDAO;
import com.example.spendingmanagement.databinding.PhanloaiAddBinding;
import com.example.spendingmanagement.model.GiaoDich;
import com.example.spendingmanagement.model.PhanLoai;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ThuFragment extends Fragment {
    RecyclerView recyclerView;
    GiaoDichAdapter giaoDichAdapter;
    ArrayList<GiaoDich> list = new ArrayList<>();
    FloatingActionButton floatingActionButton;
    GiaoDichDAO giaoDichDAO;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thu, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerview_thu_fragment);
        floatingActionButton = view.findViewById(R.id.folating_add_thu_fragmentt);

        // hien thi giao dien recyclerview
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        giaoDichDAO = new GiaoDichDAO(getContext());
        list = giaoDichDAO.getAll();

        giaoDichAdapter = new GiaoDichAdapter(getContext(), list);
        recyclerView.setAdapter(giaoDichAdapter);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialogAdd();
            }
        });
    }

    private void openDialogAdd() {
        PhanLoaiDAO phanLoaiDAO = new PhanLoaiDAO(getContext());
        // khoi tao doi tuong dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        //nap giao dien
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.giaodich_add, null);
        builder.setView(view);

        Dialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();

        EditText editTextTieuDeGiaoDich = view.findViewById(R.id.edittext_tenloai);
        EditText editTextNgayThangGiaoDich = view.findViewById(R.id.edittext_ngaythang);
        EditText editTextSoTienGiaoDich = view.findViewById(R.id.edittext_sotien);
        EditText editTextMoTaGiaoDich = view.findViewById(R.id.edittext_mota_giaodich);
        Spinner spinnerLoaiThuChiGiaoDich = view.findViewById(R.id.spiner_loaithuchi);
        Button buttonThemMoiGiaoDich = view.findViewById(R.id.button_themmoi_giaodich);
        Button buttonHuyGiaoDich = view.findViewById(R.id.button_huy_giaodich);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.planets_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerLoaiThuChiGiaoDich.setAdapter(adapter);

        buttonThemMoiGiaoDich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    String tieuDe = editTextTieuDeGiaoDich.getText().toString();
                    Date ngayThang = simpleDateFormat.parse(editTextNgayThangGiaoDich.getEditableText().toString());
                    double tien = Double.parseDouble(editTextSoTienGiaoDich.getEditableText().toString());
                    String moTaGiaoDich = editTextMoTaGiaoDich.getEditableText().toString();
                    PhanLoai phanLoai = (PhanLoai) spinnerLoaiThuChiGiaoDich.getSelectedItem();
                    int maLoai = phanLoai.getMaLoai();

                    GiaoDich giaoDich = new GiaoDich(tieuDe, ngayThang, tien, moTaGiaoDich, maLoai);

                    if (giaoDichDAO.insert(giaoDich)) {
                        Toast.makeText(getContext(), "Thêm mới giao dich thành công", Toast.LENGTH_LONG).show();
                        dialog.dismiss();

                        list.clear();
                        list.addAll(giaoDichDAO.getAll());
                        giaoDichAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getContext(), "Thêm mới thất bại mời bạn nhập lại", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        buttonHuyGiaoDich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
}
