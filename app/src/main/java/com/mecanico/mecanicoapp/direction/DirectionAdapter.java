package com.mecanico.mecanicoapp.direction;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.common.util.JsonUtils;
import com.mecanico.mecanicoapp.R;

import org.w3c.dom.ls.LSOutput;

import java.util.ArrayList;

public class DirectionAdapter extends FirebaseRecyclerAdapter<Direction, DirectionAdapter.DirectionViewHolder> {
    private DirectionAdapter.OnItemClickListener mListener;
    private Context c;
    private ArrayList<Direction> list;
    private String dirs;

    public DirectionAdapter(@NonNull FirebaseRecyclerOptions<Direction> options, Context c, ArrayList<Direction> list) {
        super(options);
        this.c = c;
        this.list = list;
    }


    @NonNull
    @Override
    public DirectionAdapter.DirectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.direction, parent, false);

        return new DirectionAdapter.DirectionViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull DirectionAdapter.DirectionViewHolder holder, int position, @NonNull Direction direction) {
        holder.Name.setText(direction.getName());
        holder.DirC.setText(direction.getDirComplete());

        dirs = direction.getDirComplete();
    }


    public class DirectionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {


        TextView Name, DirC;

        DirectionViewHolder(@NonNull View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.Name);
            DirC = itemView.findViewById(R.id.DirC);



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
                String dir = dirs;
                if (position != RecyclerView.NO_POSITION) {
                    switch (item.getItemId()) {
                        case 1:
                            mListener.onTomarPedido(position);
                            return true;
                        case 2:
                            mListener.onDeleteClick(position);

                            System.out.println(dir + "click");
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

    void setOnItemClickListener(DirectionAdapter.OnItemClickListener listener) {
        mListener = listener;
    }


}
