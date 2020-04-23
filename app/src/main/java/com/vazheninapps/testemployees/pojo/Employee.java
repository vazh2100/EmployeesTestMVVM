package com.vazheninapps.testemployees.pojo;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vazheninapps.testemployees.converters.Converter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;


@Entity(tableName = "employees")
@TypeConverters(value = Converter.class)
public class Employee {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @SerializedName("f_name")
    @Expose
    private String name;
    @SerializedName("l_name")
    @Expose
    private String lastName;
    @SerializedName("birthday")
    @Expose
    private String birthday;
    @SerializedName("avatr_url")
    @Expose
    private String avatarUrl;
    @SerializedName("specialty")
    @Expose
    private List<Speciality> specialities = null;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String fName) {
        this.name = fName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lName) {
        this.lastName = lName;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public List<Speciality> getSpecialities() {
        return specialities;
    }

    public void setSpecialities(List<Speciality> specialities) {
        this.specialities = specialities;
    }


    public String birthDayToAge(){
        Date currentDate = Calendar.getInstance().getTime();
        Locale localeRu = new Locale("ru");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", localeRu);
        SimpleDateFormat format2 = new SimpleDateFormat("dd-MM-yyyy", localeRu);
        try {
            Date birthDate = format.parse(birthday);
            TimeUnit timeUnit = TimeUnit.DAYS;
            long diffInMillies = 0;
            if (birthDate != null) {
                diffInMillies = currentDate.getTime() - birthDate.getTime();
            }
            int days = (int) timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
            int years = days/365;
            if(years>1000){
                birthDate = format2.parse(birthday);
                if (birthDate != null) {
                    diffInMillies = currentDate.getTime() - birthDate.getTime();
                }
                days = (int) timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
                years = days/365;
                return "" + years;
            }
            return "" + years;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "n/a";
    }
}
