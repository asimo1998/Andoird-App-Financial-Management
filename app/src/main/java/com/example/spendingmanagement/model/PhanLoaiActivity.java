package com.example.spendingmanagement.model;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.spendingmanagement.R;
import com.example.spendingmanagement.adapter.PhanLoaiAdapter;
import com.example.spendingmanagement.databaseaccessobject.PhanLoaiDAO;
import com.example.spendingmanagement.databinding.PhanloaiAddBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class PhanLoaiActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    PhanLoaiDAO phanLoaiDAO;
    ArrayList<PhanLoai> list = new ArrayList<>();
    PhanLoaiAdapter phanLoaiAdapter;
    FloatingActionButton floatingActionButtonPhanLoai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.orange)));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phan_loai);
        recyclerView = findViewById(R.id.recyclerview_phanloai);
        layoutManager = new LinearLayoutManager(PhanLoaiActivity.this);
        recyclerView.setLayoutManager(layoutManager);

        phanLoaiDAO = new PhanLoaiDAO(PhanLoaiActivity.this);
        list = phanLoaiDAO.getAll();

        phanLoaiAdapter = new PhanLoaiAdapter(PhanLoaiActivity.this, list);
        recyclerView.setAdapter(phanLoaiAdapter);

        floatingActionButtonPhanLoai = findViewById(R.id.folating_add_phanloai);
        floatingActionButtonPhanLoai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialogAdd();
            }
        });

    }

    public void openDialogAdd() {
        // khoi tao doi tuong dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(PhanLoaiActivity.this);

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

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(PhanLoaiActivity.this,
                R.array.planets_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        viewBinding.spinerPhanloai.setAdapter(adapter);

        viewBinding.buttonThemmoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getName = viewBinding.edittextTenloai.getText().toString();
                String getSelected = (String)viewBinding.spinerPhanloai.getSelectedItem();

                if (phanLoaiDAO.insert(getName, getSelected)){
                    Toast.makeText(PhanLoaiActivity.this, "Thêm mới thành công", Toast.LENGTH_LONG).show();
                    dialog.dismiss();

                    list.clear();;
                    list.addAll(phanLoaiDAO.getAll());
                    phanLoaiAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(PhanLoaiActivity.this, "Thêm mới thất bại mời bạn nhập lại", Toast.LENGTH_SHORT).show();
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