package com.example.alexandrareinhart.taskmanagerapp;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Parcelable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.alexandrareinhart.TaskManagerApp.ViewFragments.AddNewFragment;
import com.example.alexandrareinhart.TaskManagerApp.ViewFragments.ViewAllFragment;
import com.example.alexandrareinhart.TaskManagerApp.ViewFragments.ViewCompletedFragment;
import com.example.alexandrareinhart.TaskManagerApp.ViewFragments.ViewIncompleteFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements TaskAdapter.AdapterCallback, AddNewFragment.ActivityCallback {


    private ViewPagerAdapter viewPagerAdapter;
    private TaskDatabase taskDatabase;
    private TaskAdapter taskAdapter;
    private LinearLayoutManager linearLayoutManager;
    private List<Task> allTasksList;
    private List<Task> incompleteTasksList;
    private List<Task> completedTasksList;
    private AddNewFragment addNewFragment;
    private ViewAllFragment viewAllFragment;
    public static final String ALL_TASKS_LIST = "all_tasks_list";

    @BindView(R.id.tab_layout)
    protected TabLayout tabLayout;
    @BindView(R.id.view_pager)
    protected ViewPager viewPager;
    @BindView(R.id.main_recycler_view)
    protected RecyclerView mainRecycler;
//    @BindView(R.id.view_all_recycler)
//    protected RecyclerView viewAllRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        allTasksList = new ArrayList<>();
        incompleteTasksList = new ArrayList<>();
        completedTasksList = new ArrayList<>();

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        AddNewFragment addNewFragment = AddNewFragment.newInstance();
        addNewFragment.attachParent(this);
        viewPagerAdapter.AddFragment(addNewFragment, "NEW");
        viewPagerAdapter.AddFragment(new ViewAllFragment(), "ALL");
        viewPagerAdapter.AddFragment(new ViewIncompleteFragment(), "INCOMPLETE");
        viewPagerAdapter.AddFragment(new ViewCompletedFragment(), "COMPLETED");

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_add_task);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_view_all);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_view_incomplete);
        tabLayout.getTabAt(3).setIcon(R.drawable.ic_view_completed);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setElevation(0);

        linearLayoutManager = new LinearLayoutManager(this);
//        taskAdapter = new TaskAdapter(taskDatabase.taskDao().getTasks(), this);
        taskDatabase = ((TaskApplication) getApplication()).getDatabase();

        mainRecycler.setLayoutManager(linearLayoutManager);
        mainRecycler.setAdapter(taskAdapter);
    }

    @Override
    public Context getContext() {

        return getApplicationContext();
    }

    private void setUpRecyclerView() {


        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        taskAdapter = new TaskAdapter(taskDatabase.taskDao().getTasks(), this);

        taskAdapter.notifyDataSetChanged();
    }

    @Override
    public void addClicked() {

        setUpRecyclerView();
        AddNewFragment addNewFragment = AddNewFragment.newInstance();

        addNewFragment.attachParent(this);

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(ALL_TASKS_LIST, (ArrayList<? extends Parcelable>) allTasksList);
        viewAllFragment.setArguments(bundle);

        mainRecycler.setAdapter(taskAdapter);
        taskAdapter = new TaskAdapter(allTasksList, this);
        taskAdapter.updateList(taskDatabase.taskDao().getTasks());


        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void rowClicked(Task task) {
        if(task.isCompleted()){
            markIncomplete(task);
        } else {
            markCompleted(task);
        }
    }

    @Override
    public void rowLongClicked(final Task task) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Task Completed?")
                .setMessage("Are you sure you want to mark this task \"completed\"?")
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        taskDatabase.taskDao().deleteTask(task); //delete task from database
                        taskAdapter.updateList(taskDatabase.taskDao().getTasks()); //adapter updates view
                        Toast.makeText(MainActivity.this, "TASK COMPLETED", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }

    @Override
    public void saveTask(Task task){
        allTasksList.add(task);
        Toast.makeText(this, "TASK ADDED", Toast.LENGTH_LONG).show();
    }

    private void markIncomplete(final Task task) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Mark task incomplete?")
                .setMessage("Are you sure you want to mark this task as \"incomplete\"?")
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        task.setCompleted(false);
                        taskDatabase.taskDao().updateTask(task); //update task
                        taskAdapter.updateList(taskDatabase.taskDao().getTasks()); //adapter updates view
                        Toast.makeText(MainActivity.this, "TASK INCOMPLETE", Toast.LENGTH_LONG).show(); //Toast to confirm incomplete
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void markCompleted(final Task task) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Mark task complete?")
                .setMessage("Are you sure you want to mark this task as \"completed\"?")
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        task.setCompleted(true);
                        //update database with task info
                        taskDatabase.taskDao().updateTask(task);
                        //inform adapter; adapter updates view accordingly
                        taskAdapter.updateList(taskDatabase.taskDao().getTasks());

                        Toast.makeText(MainActivity.this, "TASK COMPLETED", Toast.LENGTH_LONG).show(); //Toast to confirm completed
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
