package com.experiment.wangld.httpapi2;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class IssueAdapter extends RecyclerView.Adapter<IssueAdapter.IssueItemCardViewHolder>{

    private ArrayList<Issues> issues;
    public IssueAdapter(ArrayList<Issues> repos){
        this.issues = repos;
    }
    public IssueAdapter.IssueItemCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        return new IssueAdapter.IssueItemCardViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.github_issue_recycler, parent,false));
    }
    public void updateData(ArrayList<Issues> list){
        this.issues = list;
        notifyDataSetChanged();
    }
    @Override
    public void onBindViewHolder(final IssueAdapter.IssueItemCardViewHolder holder, int position){
        holder.issueTitleContent.setText(issues.get(position).getTitle());
        holder.issueCreatedTimeContent.setText(issues.get(position).getCreated_at());
        holder.issueStateContent.setText(issues.get(position).getState());
        holder.issueDescriptionContent.setText(issues.get(position).getBody());
    }

    @Override
    public int getItemCount() {
        return issues == null ? 0 : issues.size();
    }

    public static class IssueItemCardViewHolder extends RecyclerView.ViewHolder{
        private TextView issueTitleContent, issueCreatedTimeContent, issueStateContent, issueDescriptionContent;
        public IssueItemCardViewHolder(View itemView){
            super(itemView);
            issueTitleContent = (TextView) itemView.findViewById(R.id.issueTitleContent);
            issueCreatedTimeContent = (TextView) itemView.findViewById(R.id.issueCreatedTimeContent);
            issueStateContent = (TextView) itemView.findViewById(R.id.issueStateContent);
            issueDescriptionContent = (TextView) itemView.findViewById(R.id.issueDescriptionContent);
        }
    }
}
