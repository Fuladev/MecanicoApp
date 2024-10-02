package com.mecanico.mecanicoapp.pedidos;


import android.content.Context;
import android.graphics.Bitmap;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.mecanico.mecanicoapp.R;

import java.util.ArrayList;

public class PedidoAdapter extends FirebaseRecyclerAdapter<Pedido, PedidoAdapter.PedidoViewHolder> {
    private OnItemClickListener mListener;
    private Context c;
    private ArrayList<Pedido> list;


    PedidoAdapter(@NonNull FirebaseRecyclerOptions<Pedido> options, Context c, ArrayList<Pedido> list) {
        super(options);
        this.c = c;
        this.list = list;
    }

    @Override
    protected void onBindViewHolder(@NonNull PedidoViewHolder holder, int position, @NonNull Pedido pedido) {

        System.out.println("Entro al holder");

        String type = pedido.getTipo();
        System.out.println(type);

        //  Home
         if (type.equals("Lav.")){

             holder.imgType.setImageResource(R.drawable.ic_lav);
             String text = "Lavadora " + pedido.getMarca();
             holder.Marca.setText(text);

        }
        if (type.equals("Nev.")){

            holder.imgType.setImageResource(R.drawable.ic_nev);
            String text = "Nevera " + pedido.getMarca();
            holder.Marca.setText(text);

        }
        if (type.equals("Air.")){

            holder.imgType.setImageResource(R.drawable.ic_aire);
            String text = "Aire " + pedido.getMarca();
            holder.Marca.setText(text);

        }
        if (type.equals("Cer.")){

            holder.imgType.setImageResource(R.drawable.ic_cerr);
            String text = pedido.getMarca();
            holder.Marca.setText(text);

        }
        if (type.equals("Mueb.")){

            holder.imgType.setImageResource(R.drawable.ic_mueb);
            String text = pedido.getMarca();
            holder.Marca.setText(text);

        }
        if (type.equals("MCoser")){

            holder.imgType.setImageResource(R.drawable.ic_coser2);
            String text = pedido.getMarca();
            holder.Marca.setText(text);

        }
        if (type.equals("Reformas")){

            holder.imgType.setImageResource(R.drawable.ic_obras2);
            String text = pedido.getMarca();
            holder.Marca.setText(text);

        }
        if (type.equals("Equipos")){

            holder.imgType.setImageResource(R.drawable.ic_pc2);
            String text = pedido.getMarca();
            holder.Marca.setText(text);

        }
        if (type.equals("Belleza")){

            holder.imgType.setImageResource(R.drawable.ic_belleza2);
            String text = pedido.getMarca();
            holder.Marca.setText(text);

        }
        if (type.equals("ServMedico")){

            holder.imgType.setImageResource(R.drawable.ic_doctor2);
            String text = pedido.getMarca();
            holder.Marca.setText(text);

        }


        //  Motor

        if (type.equals("Auto")){

            holder.imgType.setImageResource(R.drawable.ic_car2);
            String text = pedido.getMarca();
            holder.Marca.setText(text);

        }
        if (type.equals("Moto")){

            holder.imgType.setImageResource(R.drawable.ic_mbike2);
            String text = pedido.getMarca();
            holder.Marca.setText(text);


        }
        if (type.equals("Bici")){

            holder.imgType.setImageResource(R.drawable.ic_bike2);
            String text = pedido.getMarca();
            holder.Marca.setText(text);

        }
        if (type.equals("MAmarilla")){

            holder.imgType.setImageResource(R.drawable.ic_maquinaria2);
            String text = pedido.getMarca();
            holder.Marca.setText(text);

        }
        if (type.equals("Transporte")){

            holder.imgType.setImageResource(R.drawable.ic_transporte2);
            String text = pedido.getMarca();
            holder.Marca.setText(text);

        }


        holder.Date.setText(pedido.getDate());
        holder.State.setText(pedido.getState());


    }



    @NonNull
    @Override
    public PedidoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pedido, parent, false);

        return new PedidoViewHolder(view);
    }


    public class PedidoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {


        TextView Tipo, Marca, Date, State;
        ImageView imgType;

        PedidoViewHolder(@NonNull View itemView) {
            super(itemView);

            Marca = itemView.findViewById(R.id.Marca);
            Date = itemView.findViewById(R.id.Date);
            State = itemView.findViewById(R.id.Sate);
            imgType = itemView.findViewById(R.id.img_type);


            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View v) {

        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Select Action");
            MenuItem doPedido = menu.add(Menu.NONE, 1, 1, "Tomar pedido");
            MenuItem delete = menu.add(Menu.NONE, 2, 2, "No tomar");
            doPedido.setOnMenuItemClickListener(this);
            delete.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    switch (item.getItemId()) {
                        case 1:
                            mListener.onTomarPedido(position);
                            return true;
                        case 2:
                            mListener.onDeleteClick(position);
                            return true;
                    }
                }
            }
            return false;
        }
    }

    public interface OnItemClickListener {
        void onTomarPedido(int position);

        void onDeleteClick(int position);

    }

    void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


}
/*
   public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    private Context mContext;
    private List<Upload> mUploads;
    private OnItemClickListener mListener;
    public ImageAdapter(Context context, List<Upload> uploads) {
        mContext = context;
        mUploads = uploads;
    }
    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.image_item, parent, false);
        return new ImageViewHolder(v);
    }
    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        Upload uploadCurrent = mUploads.get(position);
        holder.textViewName.setText(uploadCurrent.getName());
        Picasso.with(mContext)
                .load(uploadCurrent.getImageUrl())
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(holder.imageView);
    }
    @Override
    public int getItemCount() {
        return mUploads.size();
    }
    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        public TextView textViewName;
        public ImageView imageView;
        public ImageViewHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.text_view_name);
            imageView = itemView.findViewById(R.id.image_view_upload);
            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
        }
        @Override
        public void onClick(View v) {
            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    mListener.onItemClick(position);
                }
            }
        }
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Select Action");
            MenuItem doWhatever = menu.add(Menu.NONE, 1, 1, "Do whatever");
            MenuItem delete = menu.add(Menu.NONE, 2, 2, "Delete");
            doWhatever.setOnMenuItemClickListener(this);
            delete.setOnMenuItemClickListener(this);
        }
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    switch (item.getItemId()) {
                        case 1:
                            mListener.onWhatEverClick(position);
                            return true;
                        case 2:
                            mListener.onDeleteClick(position);
                            return true;
                    }
                }
            }
            return false;
        }
    }
    public interface OnItemClickListener {
        void onItemClick(int position);
        void onWhatEverClick(int position);
        void onDeleteClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
}*/
