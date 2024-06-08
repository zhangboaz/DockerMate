package com.boaz.dockermate;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.boaz.dockermate.entity.Container;
import com.boaz.dockermate.entity.Server;

import java.util.List;

public class ContainerAdapter extends RecyclerView.Adapter<ContainerAdapter.ViewHolder>{

    List<Container> containers;
    Server server;
    public ContainerAdapter(List<Container> containers, Server server)
    {
        this.containers = containers;
        this.server = server;
    }
    @NonNull
    @Override
    public ContainerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater from = LayoutInflater.from(parent.getContext());
        View view = from.inflate(R.layout.container_item, parent, false);
        return new ContainerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContainerAdapter.ViewHolder holder, int position) {
        Container container = containers.get(position);
        holder.containerName.setText(container.getName());
        holder.status.setText(container.getStatus());
        holder.imageName.setText(container.getImage());
        holder.ports.setText(container.getPorts());

    }

    @Override
    public int getItemCount() {
        return containers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView containerName;
        TextView status;
        TextView imageName;
        TextView ports;
        LinearLayout containerItem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            containerName = itemView.findViewById(R.id.containerName);
            status = itemView.findViewById(R.id.status);
            imageName = itemView.findViewById(R.id.imageName);
            ports = itemView.findViewById(R.id.ports);
            containerItem = itemView.findViewById(R.id.containerItem);
            containerItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Container container = containers.get(getAdapterPosition());
                    Intent intent = new Intent(view.getContext(), ContainerInfo.class);
                    intent.putExtra("container", container);
                    intent.putExtra("server", server);
                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }
}
