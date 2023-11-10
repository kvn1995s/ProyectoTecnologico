package com.proyecto_citas_medicas.medicos;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.proyecto_citas_medicas.R;

import java.util.ArrayList;

public class Adapter_medicos extends RecyclerView.Adapter<Adapter_medicos.ViewHolder> {
    //create variable
    private ArrayList<ListMedicos> listMedicosArrayList;
    private OnItemClickListener clickListener;
    private Context context;
    //generate constructor
    public Adapter_medicos(ArrayList<ListMedicos> listMedicosArrayList, Context context) {
        this.listMedicosArrayList = listMedicosArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public Adapter_medicos.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //layout inflater
        View view= LayoutInflater.from ( parent.getContext () ).inflate ( R.layout.list_med,parent,false );
        return new ViewHolder (view);
    }
    @Override
    public void onBindViewHolder(@NonNull Adapter_medicos.ViewHolder holder, int position) {
        //get position
        ListMedicos listMedicos=listMedicosArrayList.get( position );
        holder.nomMed.setText ("Nombres: "+listMedicos.getNombre()+"\n");
        holder.apellidoPaternoM.setText ("Apellido Materno: "+listMedicos.getApellidoPaterno()+"\n");
        holder.apellidoMaternoM.setText ("Apellido Paterno: "+listMedicos.getApellidoMaterno()+"\n");
        holder.correoMed.setText ("Correo: "+listMedicos.getCorreo()+"\n");
        holder.dniMe.setText ("Dni: "+listMedicos.getDni()+"\n");
        holder.cmpMedico.setText ("CMP: "+listMedicos.getCMP()+"\n");

        // Agregar un manejador de clic a la vista de list_med
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clickListener != null) {
                    clickListener.onItemClick(holder.getBindingAdapterPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
         return listMedicosArrayList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nomMed,apellidoPaternoM,apellidoMaternoM,correoMed,dniMe,cmpMedico;
        public ViewHolder(@NonNull View itemView) {
            super ( itemView );
            nomMed=itemView.findViewById ( R.id.tvNom );
            apellidoPaternoM=itemView.findViewById ( R.id.tvApeP );
            apellidoMaternoM=itemView.findViewById ( R.id.tvApeM );
            correoMed=itemView.findViewById ( R.id.tvCorreo );
            dniMe=itemView.findViewById ( R.id.tvDni );
            cmpMedico=itemView.findViewById ( R.id.tvCmp );
        }
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.clickListener = listener;
    }
}

