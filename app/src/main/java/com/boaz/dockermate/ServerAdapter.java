package com.boaz.dockermate;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.boaz.dockermate.entity.Server;

import java.util.List;

public class ServerAdapter extends RecyclerView.Adapter<ServerAdapter.ViewHolder> {

    private List<Server> servers;
    public ServerAdapter(List<Server> servers) {
        this.servers = servers;
    }

    @NonNull
    @Override
    public ServerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater from = LayoutInflater.from(parent.getContext());
        View view = from.inflate(R.layout.server_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServerAdapter.ViewHolder holder, int position) {
        Server server = servers.get(position);
        holder.serverName.setText(server.getServerName());
        holder.serverIp.setText(server.getIp());
        holder.serverUser.setText(server.getUsername());
    }

    @Override
    public int getItemCount() {
        return servers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView serverName;
        TextView serverIp;
        TextView serverUser;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            serverName = itemView.findViewById(R.id.serverName);
            serverIp = itemView.findViewById(R.id.serverIp);
            serverUser = itemView.findViewById(R.id.serverUser);
        }
    }
}
