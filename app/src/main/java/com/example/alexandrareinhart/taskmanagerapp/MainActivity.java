package com.example.alexandrareinhart.taskmanagerapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alexandrareinhart.taskmanagerapp.ViewPagerTabs.ViewAllFragment;
import com.example.alexandrareinhart.taskmanagerapp.ViewPagerTabs.ViewCompletedFragment;
import com.example.alexandrareinhart.taskmanagerapp.ViewPagerTabs.ViewIncompleteFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements TaskAdapter.AdapterCallback, TaskCreatorFragment.ActivityCallback, GestureDetector.OnDoubleTapListener, GestureDetector.OnGestureListener {

    @BindView(R.id.task_recycler_view)
    protected RecyclerView recyclerView;

    private LinearLayoutManager linearLayoutManager;
    private TaskCreatorFragment taskCreatorFragment;
    private TaskDatabase taskDatabase;
    private TaskAdapter taskAdapter;
    private List<Task> taskList;
    private List<Task> incompleteTasks;
    private List<Task> completedTasks;
    private GestureDetectorCompat gestureDetectorCompat;

    private SectionsPagerAdapter sectionsPagerAdapter;
    private ViewPager viewPager;


    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //TODO - create method to return home
                    return true;
                case R.id.navigation_add_new:
//                    mTextMessage.setText(R.string.title_add_new);
                    addNewButtonClicked();
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

//        Toolbar toolbar = (Toolbar) findViewById(R.id.navigation);
//        setSupportActionBar(toolbar);

        sectionsPagerAdapter= new SectionsPagerAdapter(getSupportFragmentManager());

//TODO - FIX THIS SHIT 
        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        
//        setUpRecyclerView();

//        this.gestureDetectorCompat = new GestureDetectorCompat(this, this);
//        gestureDetectorCompat.setOnDoubleTapListener(this);

//        mTextMessage = (TextView) findViewById(R.id.message);
//        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
//        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.navigation_home || id == R.id.navigation_add_new || id == R.id.navigation_view_all || id == R.id.navigation_view_incomplete || id == R.id.navigation_view_completed) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void setUpRecyclerView() {

        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        taskAdapter = new TaskAdapter(taskDatabase.taskDao().getTasks(), this);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(taskAdapter);

        taskAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.navigation_add_new)
    protected void addNewButtonClicked() {

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

        setUpRecyclerView();

        getSupportFragmentManager().beginTransaction().remove(taskCreatorFragment).commit();

        taskAdapter.updateList(taskDatabase.taskDao().getTasks());

        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }


    }

    //selects task to view details
    @Override
    public void rowClicked(Task task) {
        
    }

    //prompt alert dialog to confirm task completed
    @Override
    public void rowLongClicked(Task task) {

    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }
}

class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                ViewAllFragment allTab = new ViewAllFragment();
                return allTab;
            case 1:
                ViewIncompleteFragment incompleteTab = new ViewIncompleteFragment();
                return incompleteTab;
            case 2:
                ViewCompletedFragment completedTab = new ViewCompletedFragment();
                return completedTab;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}