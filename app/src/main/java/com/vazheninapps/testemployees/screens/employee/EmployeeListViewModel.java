package com.vazheninapps.testemployees.screens.employee;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.vazheninapps.testemployees.data.AppDatabase;
import com.vazheninapps.testemployees.pojo.Employee;
import com.vazheninapps.testemployees.pojo.Speciality;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

 class EmployeeListViewModel extends AndroidViewModel {

    private static AppDatabase database;
    private MutableLiveData<List<Employee>> employeesOfSpecialityLiveData;

    private List<Employee> allEmployees;

    EmployeeListViewModel(@NonNull Application application, String specialityName) {
        super(application);
        database = AppDatabase.getInstance(application);
        allEmployees = getAllEmployees();
        List<Employee> employeesOfSpecialityList = new ArrayList<>();
        for (Employee employee: allEmployees){
            List<Speciality> specialitiesOfEmployee  = employee.getSpecialities();
             for (Speciality speciality: specialitiesOfEmployee){
                if(speciality.getName().equals(specialityName)) {
                    employeesOfSpecialityList.add(employee);
                }
             }

        }
        employeesOfSpecialityLiveData = new MutableLiveData<>(employeesOfSpecialityList);
    }

     LiveData<List<Employee>> getEmployeesOfSpecialityLiveData() {
        return employeesOfSpecialityLiveData;
    }

    private List<Employee> getAllEmployees(){
        List<Employee> result = new ArrayList<>();
        try {
            return new GetAllEmployeesTask().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static class GetAllEmployeesTask extends AsyncTask<Void, Void, List<Employee>>{

        @Override
        protected List<Employee> doInBackground(Void... voids) {
            return database.employeeDao().getAllEmployees();
        }
    }

}
