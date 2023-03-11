package com.example.spendingmanagement.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spendingmanagement.R;
import com.example.spendingmanagement.adapter.PhanLoaiAdapter;
import com.example.spendingmanagement.databaseaccessobject.PhanLoaiDAO;
import com.example.spendingmanagement.databinding.PhanloaiAddBinding;
import com.example.spendingmanagement.model.PhanLoai;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class PhanLoaiFragment extends Fragment {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    PhanLoaiDAO phanLoaiDAO;
    ArrayList<PhanLoai> list = new ArrayList<>();
    PhanLoaiAdapter phanLoaiAdapter;
    FloatingActionButton floatingActionButtonPhanLoai;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_phan_loai, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerview_phanloai);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        phanLoaiDAO = new PhanLoaiDAO(getContext());
        list = phanLoaiDAO.getAll();

        phanLoaiAdapter = new PhanLoaiAdapter(getContext(), list);
        recyclerView.setAdapter(phanLoaiAdapter);

        floatingActionButtonPhanLoai = view.findViewById(R.id.folating_add_phanloai);
        floatingActionButtonPhanLoai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialogAdd();
            }
        });
    }

    private void openDialogAdd() {
        // khoi tao doi tuong dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        //nap giao dien
        PhanloaiAddBinding viewBinding = PhanloaiAddBinding.inflate(getLayoutInflater());
        View view = viewBinding.getRoot();
        builder.setView(view);

        Dialog dialog = builder.create();
        dialog.show();

        //khoi tao phuong thuc thu chi
//        String[] status = {"Thu", "Chi"};
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(PhanLoaiActivity.this,
//                android.R.layout.simple_spinner_item, status);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerThemMoi.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.planets_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        viewBinding.spinerPhanloai.setAdapter(adapter);

        viewBinding.buttonThemmoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getName = viewBinding.edittextTenloai.getText().toString();
                String getSelected = (String) viewBinding.spinerPhanloai.getSelectedItem();

                if (phanLoaiDAO.insert(getName, getSelected)) {
                    Toast.makeText(getContext(), "Thêm mới thành công", Toast.LENGTH_LONG).show();
                    dialog.dismiss();

                    list.clear();
                    ;
                    list.addAll(phanLoaiDAO.getAll());
                    phanLoaiAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "Thêm mới thất bại mời bạn nhập lại", Toast.LENGTH_SHORT).show();
                }
            }
        });
        viewBinding.buttonHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
}
