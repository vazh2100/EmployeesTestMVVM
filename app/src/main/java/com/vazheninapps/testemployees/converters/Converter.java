package com.vazheninapps.testemployees.converters;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.vazheninapps.testemployees.pojo.Speciality;

import java.util.ArrayList;
import java.util.List;

public class Converter {
    @TypeConverter
    public String listSpecialityToString(List<Speciality> specialities) {
        return new Gson().toJson(specialities);
    }

    @TypeConverter
    public List<Speciality> stringToListSpeciality(String specialitiesAsString) {
        Gson gson = new Gson();
        ArrayList objects = gson.fromJson(specialitiesAsString, ArrayList.class);
        ArrayList<Speciality> specialities = new ArrayList<>();
        for (Object object : objects) {
            specialities.add(gson.fromJson(object.toString(), Speciality.class));
        }
        return specialities;
    }
}
