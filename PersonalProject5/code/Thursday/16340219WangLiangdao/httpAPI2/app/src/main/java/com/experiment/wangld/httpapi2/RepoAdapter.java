package com.experiment.wangld.httpapi2;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.ArrayList;

import io.reactivex.internal.util.ArrayListSupplier;

public class RepoAdapter extends RecyclerView.Adapter<RepoAdapter.RepoItemCardViewHolder>{

    private ArrayList<Repo> repos;
    OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public RepoAdapter(ArrayList<Repo> repos){
        this.repos = repos;
    }
    public RepoAdapter.RepoItemCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        return new RepoAdapter.RepoItemCardViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.github_recycler, parent,false));
    }
    public void updateData(ArrayList<Repo> list){
        this.repos = list;
        notifyDataSetChanged();
    }
    @Override
    public void onBindViewHolder(final RepoItemCardViewHolder holder, final int position){
        holder.repoNameContent.setText(repos.get(position).getName());
        holder.repoIdContent.setText(String.valueOf(repos.get(position).getId()));
        holder.repoProblemContent.setText(String.valueOf(repos.get(position).getOpen_issues()));
        holder.repoDescriptionContent.setText(repos.get(position).getDescription());
        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onClick(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return repos == null ? 0 : repos.size();
    }

    public interface OnItemClickListener{
        void onClick( int position);
    }

    public static class RepoItemCardViewHolder extends RecyclerView.ViewHolder{
        private TextView repoNameContent, repoIdContent, repoProblemContent, repoDescriptionContent;
        public RepoItemCardViewHolder(View itemView){
            super(itemView);
            repoNameContent = (TextView) itemView.findViewById(R.id.repoContent);
            repoIdContent = (TextView) itemView.findViewById(R.id.repoIdContent);
            repoProblemContent = (TextView) itemView.findViewById(R.id.repoProblemContent);
            repoDescriptionContent = (TextView) itemView.findViewById(R.id.repoDescriptionContent);
        }
    }
}
