package com.vazheninapps.testemployees.screens.employee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vazheninapps.testemployees.R;
import com.vazheninapps.testemployees.pojo.Employee;
import com.vazheninapps.testemployees.pojo.Speciality;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EmployeeDetailedActivity extends AppCompatActivity {

    @BindView(R.id.textViewName)
    TextView textViewName;
    @BindView(R.id.textViewLastName)
    TextView textViewLastName;
    @BindView(R.id.textViewAge)
    TextView textViewAge;
    @BindView(R.id.textViewBirthDay)
    TextView textViewBirthDay;
    @BindView(R.id.textViewEmployeeSpecialities)
    TextView textViewEmployeeSpecialities;
    @BindView(R.id.imageViewEmployee)
    ImageView imageViewEmployee;

    private EmployeeDetailedViewModel viewModel;

    int employeeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initItems();
        setItems();
    }

    void initItems(){
        setContentView(R.layout.activity_employee_detailed);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        employeeId = intent.getIntExtra(EmployeeListActivity.INTENT_EXTRA_EMPLOYEE_ID, -1);
        viewModel = new EmployeeDetailedViewModel(getApplication());

    }

   void setItems(){
       viewModel.getEmployeeById(employeeId).observe(this, new Observer<Employee>() {
           @Override
           public void onChanged(Employee employee) {
               String avatarUrl = employee.getAvatarUrl();
               if(avatarUrl!=null && !avatarUrl.isEmpty()){
                   Picasso.get().load(employee.getAvatarUrl()).placeholder(R.drawable.avatar_place_holder).into(imageViewEmployee);}
               textViewName.setText(employee.getName());
               textViewLastName.setText(employee.getLastName());
               textViewAge.setText(employee.birthDayToAge());
               textViewBirthDay.setText(employee.getBirthday());
               List<Speciality> employeeSpecialities = employee.getSpecialities();
               StringBuilder builder = new StringBuilder();
               for(Speciality speciality:employeeSpecialities){
                   builder.append(speciality.getName()).append("\n");
               }
               textViewEmployeeSpecialities.setText(builder.toString());
           }
       });
    }
}
