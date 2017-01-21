package com.example.vishvraj.basic_app;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vishvraj on 20-01-2017.
 */

public class dataadapter extends RecyclerView.Adapter<dataadapter.myViewHolder> {

    private List<data_info> data_list=new ArrayList<>();
    public class myViewHolder extends RecyclerView.ViewHolder{
        public TextView name,surname,email,password,phnno,city,gender,hobby;
        public myViewHolder(View itemView) {
            super(itemView);
            name=(TextView) itemView.findViewById(R.id.editNameDemo);
            surname=(TextView) itemView.findViewById(R.id.editSurnamedemo);
            email=(TextView) itemView.findViewById(R.id.editEmailDemo);
            password=(TextView) itemView.findViewById(R.id.editPasswordDemo);
            phnno=(TextView) itemView.findViewById(R.id.editPhoneDemo);
            city=(TextView)itemView.findViewById(R.id.spinnerCityDemo);
            hobby=(TextView)itemView.findViewById(R.id.checkBox2Demo);
            gender=(TextView)itemView.findViewById(R.id.radioButtonMaleDemo);

        }
    }
    public dataadapter(List<data_info> data_list)
    {
        this.data_list=data_list;
    }
    @Override
    public dataadapter.myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview= LayoutInflater.from(parent.getContext()).inflate(R.layout.show_data_format,parent,false);
        return new myViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(dataadapter.myViewHolder holder, int position) {
        data_info data=data_list.get(position);
        holder.name.setText(data.getName());
        holder.surname.setText(data.getSurname());
        holder.email.setText(data.getEmail());
        holder.password.setText(data.getPassword());
        holder.phnno.setText(data.getPhnno());
        holder.gender.setText(data.getGender());
        holder.city.setText(data.getCity());
        holder.hobby.setText(data.getHobby());
    }

    @Override
    public int getItemCount() {
        return data_list.size();
    }
}
