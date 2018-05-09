package com.example.alexandrareinhart.taskmanagerapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements TaskAdapter.AdapterCallback, TaskCreatorFragment.ActivityCallback {

    @BindView(R.id.task_recycler_view)
    protected RecyclerView recyclerView;

    private LinearLayoutManager linearLayoutManager;
    private TaskCreatorFragment taskCreatorFragment;
    private TaskDatabase taskDatabase;
    private TaskAdapter taskAdapter;
    private List<Task> taskList;

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_view_all:
                    mTextMessage.setText(R.string.title_view_all);
                    return true;
                case R.id.navigation_view_incomplete:
                    mTextMessage.setText(R.string.title_view_incomplete);
                    return true;
                case R.id.navigation_view_completed:
                    mTextMessage.setText(R.string.title_view_completed);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        taskDatabase = ((TaskApplication) getApplicationContext()).getDatabase();
        
        setUpRecyclerView();

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void setUpRecyclerView() {

        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        taskAdapter = new TaskAdapter(taskDatabase.taskDao().getTasks(), this);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(taskAdapter);

        taskAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.submit_button)
    protected void submitButtonClicked() {

        taskCreatorFragment = TaskCreatorFragment.newInstance();
        taskCreatorFragment.attachParent(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder, taskCreatorFragment).commit();
    }

    @Override
    public Context getContext() {

        return getApplicationContext();
    }

    @Override
    public void addClicked() {

        getSupportFragmentManager().beginTransaction().remove(taskCreatorFragment).commit();
        taskAdapter.updateList(taskDatabase.taskDao().getTasks());
    }

    //selects task to view details
    @Override
    public void rowClicked(Task task) {
        
    }

    //prompt alert dialog to confirm task completed
    @Override
    public void rowLongClicked(Task task) {

    }
}
