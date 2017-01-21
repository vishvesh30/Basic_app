package com.example.vishvraj.basic_app;

/**
 * Created by vishvraj on 20-01-2017.
 */

public class data_info {
    private String name,surname,email,password,phnno,gender,city,hobby;

    data_info(String name,String surname,String email,String password,String phnno,String gender,String city,String hobby){
        this.name=name;
        this.surname=surname;
        this.email=email;
        this.password=password;
        this.phnno=phnno;
        this.gender=gender;
        this.city=city;
        this.hobby=hobby;
    }

    public String getName(){
        return name;
    }
    public String getSurname(){
        return surname;
    }

    public String getEmail(){
        return email;
    }

    public String getPassword(){
        return password;
    }

    public String getPhnno(){
        return phnno;
    }

    public String getGender(){
        return gender;
    }

    public String getCity(){
        return city;
    }

    public String getHobby(){
        return hobby;
    }
}
