package com.vazheninapps.testemployees.screens.employee;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.vazheninapps.testemployees.data.AppDatabase;
import com.vazheninapps.testemployees.pojo.Employee;


class EmployeeDetailedViewModel extends AndroidViewModel {

    private static AppDatabase database;

    EmployeeDetailedViewModel(@NonNull Application application) {
        super(application);
        database = AppDatabase.getInstance(application);
    }

    LiveData<Employee> getEmployeeById(int id) {
        return database.employeeDao().getEmployeeById(id);
    }

}
