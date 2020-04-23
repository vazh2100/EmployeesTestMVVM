package com.vazheninapps.testemployees.screens.employee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.vazheninapps.testemployees.R;
import com.vazheninapps.testemployees.adapters.EmployeeAdapter;
import com.vazheninapps.testemployees.pojo.Employee;
import com.vazheninapps.testemployees.pojo.Speciality;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SpecialityListActivity extends AppCompatActivity {

    @BindView(R.id.listViewSpecialities)
    ListView listViewSpecialities;

    private SpecialityListViewModel viewModel;
    private static List<Speciality> specialities;
    private ArrayAdapter<Speciality> adapter;

    public static final String INTENT_EXTRA_SPECIALITY_NAME = "specialityName";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initItems();
        setItems();
    }

    private void initItems() {
        setContentView(R.layout.activity_speciality_list);
        ButterKnife.bind(this);
        viewModel = new SpecialityListViewModel(getApplication());
        specialities = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, R.layout.speciality_item, R.id.textViewSpecialityItem, specialities);

    }

    private void setItems() {
        viewModel.loadData();
        listViewSpecialities.setAdapter(adapter);
        viewModel.getSpecialities().observe(this, new Observer<List<Speciality>>() {
            @Override
            public void onChanged(List<Speciality> specialities) {
                SpecialityListActivity.specialities = specialities;
                adapter.clear();
                adapter.addAll(specialities);
                adapter.notifyDataSetChanged();
            }
        });

        listViewSpecialities.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SpecialityListActivity.this, EmployeeListActivity.class);
                String specialityName = specialities.get(position).getName();
                intent.putExtra(INTENT_EXTRA_SPECIALITY_NAME, specialityName);
                startActivity(intent);
            }
        });
    }


}
