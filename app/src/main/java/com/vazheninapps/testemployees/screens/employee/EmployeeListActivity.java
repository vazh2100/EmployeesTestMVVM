package com.vazheninapps.testemployees.screens.employee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import com.vazheninapps.testemployees.R;
import com.vazheninapps.testemployees.adapters.EmployeeAdapter;
import com.vazheninapps.testemployees.pojo.Employee;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class EmployeeListActivity extends AppCompatActivity {

    @BindView(R.id.recyclerViewEmployees)
    RecyclerView recyclerViewEmployees;

    private String specialityName;
    private EmployeeListViewModel viewModel;

    private EmployeeAdapter adapter;

    public static final String INTENT_EXTRA_EMPLOYEE_ID = "employeeId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initItems();
        setItems();
    }

    private void initItems() {
        setContentView(R.layout.activity_employee_list);
        ButterKnife.bind(this);
        adapter = new EmployeeAdapter();
        Intent intent = getIntent();
        specialityName = intent.getStringExtra(SpecialityListActivity.INTENT_EXTRA_SPECIALITY_NAME);
        viewModel = new EmployeeListViewModel(getApplication(), specialityName);
    }

    private void setItems() {
        recyclerViewEmployees.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerViewEmployees.setAdapter(adapter);

        adapter.setOnEmployeeClickListener(new EmployeeAdapter.OnEmployeeClickListener() {
            @Override
            public void onEmployeeClick(int position) {
                Intent intent = new Intent(EmployeeListActivity.this, EmployeeDetailedActivity.class);
                intent.putExtra(INTENT_EXTRA_EMPLOYEE_ID, adapter.getEmployees().get(position).getId());
                startActivity(intent);
            }
        });

        viewModel.getEmployeesOfSpecialityLiveData().observe(this, new Observer<List<Employee>>() {
            @Override
            public void onChanged(List<Employee> employees) {
                adapter.setEmployees(employees);
            }
        });
    }
}

