package com.example.alexandrareinhart.taskmanagerapp;

import android.arch.persistence.room.Ignore;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.alexandrareinhart.TaskManagerApp.ViewFragments.ViewAllFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder>  {

    private List<Task> allTasksList;
    private AdapterCallback adapterCallback;


    public TaskAdapter(List<Task> allTasksList, AdapterCallback adapterCallback) {
        this.allTasksList = allTasksList;
        this.adapterCallback = adapterCallback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindTask(allTasksList.get(position));
        holder.title.setText(allTasksList.get(position).getTaskTitle());
//        holder.dueDate.setText(tasksList.get(position).getDate());
        holder.details.setText(allTasksList.get(position).getTaskDetails());
        holder.itemView.setOnClickListener(holder.onClick(allTasksList.get(position)));
        holder.itemView.setOnLongClickListener(holder.onLongClick(allTasksList.get(position)));
    }

    @Override
    public int getItemCount() {
        return allTasksList.size();
    }

    public void updateList(List<Task> list) {

        allTasksList = list;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_row)
        protected LinearLayout rowLayout;
        @BindView(R.id.task_title_textView)
        protected TextView title;
        @BindView(R.id.instructions_for_details_textView)
        protected TextView details;
        @BindView(R.id.due_date_textView)
        protected TextView dueDate;
        @BindView(R.id.date_completed_textView)
        protected TextView completedDate;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindTask(Task task){

            //2:30:00 and last video 9:49
            //TODO - bind task, code method
//            adapterCallback.getContext().getString(date)
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(task.getDate());
            Date date = calendar.getTime();
            SimpleDateFormat formatter = new SimpleDateFormat("MMMM yyyy", Locale.US);
            title.setText(task.getTaskTitle());
            dueDate.setText(adapterCallback.getContext().getString(R.string.complete_by_date, formatter.format(date)));

//            if(task.isPriority()) {
//                rowLayout.setBackgroundResource(R.color.paleRed);
//            }

            if(task.isCompleted()) {
                completedDate.setVisibility(View.VISIBLE);
//                Calendar calendar = Calendar.getInstance();
                calendar.setTime(task.getDate());
//                Date date = calendar.getTime();
//                SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/YYYY", Locale.US);
                completedDate.setText(adapterCallback.getContext().getString(R.string.completed_on_date, formatter.format(date)));
                rowLayout.setBackgroundResource(R.color.paleBlue);
//                completedTasks.add(task);

            } else {
                rowLayout.setBackgroundResource(R.color.paleGreen);
                completedDate.setVisibility(View.INVISIBLE);
//                incompleteTasks.add(task);
            }
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
