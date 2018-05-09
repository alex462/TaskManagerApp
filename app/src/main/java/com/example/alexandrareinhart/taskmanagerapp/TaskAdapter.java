package com.example.alexandrareinhart.taskmanagerapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private List<Task> taskList;
    private AdapterCallback adapterCallback;

    public TaskAdapter(List<Task> taskList, AdapterCallback adapterCallback){

        this.taskList = taskList;
        this.adapterCallback = adapterCallback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.bindTask(taskList.get(position));
        holder.itemView.setOnClickListener(holder.onClick(taskList.get(position)));
        holder.itemView.setOnLongClickListener(holder.onLongClick(taskList.get(position)));
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public void updateList(List<Task> list) {

        taskList = list;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.task_item_row_layout)
        protected ConstraintLayout rowLayout;
        //TODO - complete item_task.xml then bind all items

        public ViewHolder(View itemView){

            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindTask(Task task){

            //2:30:00 and last video 9:49
            //TODO - bind task, code method
        }

        public View.OnClickListener onClick(final Task task) {
            return new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    adapterCallback.rowClicked(task);
                }
            };
        }

        public View.OnLongClickListener onLongClick(final Task task) {
            return new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    adapterCallback.rowLongClicked(task);
                    return true;
                }
            };
        }
    }

    public interface AdapterCallback {

        Context getContext();
        void rowClicked(Task task);
        void rowLongClicked(Task task);
    }
}
