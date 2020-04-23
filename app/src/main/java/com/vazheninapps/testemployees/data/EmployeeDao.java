package com.vazheninapps.testemployees.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.vazheninapps.testemployees.pojo.Employee;
import com.vazheninapps.testemployees.pojo.Speciality;

import java.util.List;

@Dao
public interface EmployeeDao {

    @Query("SELECT * FROM specialities")
    LiveData<List<Speciality>> getAllSpecialitiesLD();

    @Insert(entity = Speciality.class, onConflict = OnConflictStrategy.REPLACE)
    void insertSpecialities(List<Speciality> specialities);

    @Query("DELETE FROM specialities")
    void deleteAllSpecialities();


    @Query("SELECT * FROM employees")
    List<Employee> getAllEmployees();

    @Insert(entity = Employee.class, onConflict = OnConflictStrategy.REPLACE)
    void insertEmployees(List<Employee> employees);

    @Query("DELETE FROM employees")
    void deleteAllEmployees();

    @Query("SELECT * FROM employees WHERE id ==:id")
    LiveData<Employee> getEmployeeById(int id);

}
