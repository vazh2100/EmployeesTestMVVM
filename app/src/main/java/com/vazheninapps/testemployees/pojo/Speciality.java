package com.vazheninapps.testemployees.pojo;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "specialities")
public class Speciality {

    @SerializedName("specialty_id")
    @Expose
    @PrimaryKey
    private int specialtyId;
    @SerializedName("name")
    @Expose
    private String name;

    public int getSpecialtyId() {
        return specialtyId;
    }

    public void setSpecialtyId(int specialtyId) {
        this.specialtyId = specialtyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return  name + "("+ specialtyId +")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Speciality that = (Speciality) o;

        if (specialtyId != that.specialtyId) return false;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        int result = specialtyId;
        result = 31 * result + name.hashCode();
        return result;
    }
}
