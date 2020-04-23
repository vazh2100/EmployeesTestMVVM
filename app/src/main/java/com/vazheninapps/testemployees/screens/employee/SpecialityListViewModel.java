package com.vazheninapps.testemployees.screens.employee;

import android.app.Application;
import android.os.AsyncTask;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.vazheninapps.testemployees.api.ApiFactory;
import com.vazheninapps.testemployees.api.ApiService;
import com.vazheninapps.testemployees.data.AppDatabase;
import com.vazheninapps.testemployees.pojo.Employee;
import com.vazheninapps.testemployees.pojo.EmployeeResponse;
import com.vazheninapps.testemployees.pojo.Speciality;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SpecialityListViewModel extends AndroidViewModel {

    private static AppDatabase database;
    private LiveData<List<Speciality>> specialities;
    private Disposable disposable;

    SpecialityListViewModel(@NonNull Application application) {
        super(application);
        database = AppDatabase.getInstance(application);
        specialities = database.employeeDao().getAllSpecialitiesLD();
    }

    LiveData<List<Speciality>> getSpecialities() {
        return specialities;
    }

    void loadData() {
        ApiFactory apiFactory = ApiFactory.getInstance();
        ApiService apiService = apiFactory.getApiService();
        disposable = apiService.getEmployees()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<EmployeeResponse>() {
                    @Override
                    public void accept(EmployeeResponse employeeResponse) throws Exception {
                        List<Employee> employees = employeeResponse.getEmployees();
                        deleteAllEmployees();
                        insertEmployees(employees);
                        deleteAllSpecialities();
                        Set<Speciality> specialities = new HashSet<>();
                        for (Employee employee : employees) {
                            List<Speciality> specialitiesOfEmployee = employee.getSpecialities();
                            for (Speciality speciality : specialitiesOfEmployee) {
                                specialities.add(speciality);
                            }
                        }
                        List<Speciality> result = new ArrayList<>(specialities);
                        insertSpecialities(result);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(getApplication(), "Ошибка получения данных из интернета " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @SuppressWarnings("unchecked")
    private void insertSpecialities(List<Speciality> specialities) {
        new InsertSpecialitiesTask().execute(specialities);
    }

    private void deleteAllSpecialities() {
        new DeleteAllSpecialitiesTask().execute();
    }

    @SuppressWarnings("unchecked")
    private void insertEmployees(List<Employee> employees) {
        new InsertEmployeesTask().execute(employees);
    }

    private void deleteAllEmployees() {
        new DeleteAllEmployeesTask().execute();
    }

    private static class InsertSpecialitiesTask extends AsyncTask<List<Speciality>, Void, Void> {

        @SafeVarargs
        @Override
        protected final Void doInBackground(List<Speciality>... lists) {
            database.employeeDao().insertSpecialities(lists[0]);
            return null;
        }
    }

    private static class DeleteAllSpecialitiesTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            database.employeeDao().deleteAllSpecialities();
            return null;
        }
    }

    private static class InsertEmployeesTask extends AsyncTask<List<Employee>, Void, Void> {
        @SafeVarargs
        @Override
        protected final Void doInBackground(List<Employee>... lists) {
            if (lists != null && lists.length > 0) {
                database.employeeDao().insertEmployees(lists[0]);
            }
            return null;
        }
    }

    private static class DeleteAllEmployeesTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            database.employeeDao().deleteAllEmployees();
            return null;
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.dispose();
    }
}
