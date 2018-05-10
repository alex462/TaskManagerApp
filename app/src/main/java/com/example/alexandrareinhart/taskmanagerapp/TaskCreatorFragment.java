package com.example.alexandrareinhart.taskmanagerapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;
import java.util.zip.Inflater;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TaskCreatorFragment extends Fragment {

    private ActivityCallback activityCallback;
    private TaskDatabase taskDatabase;

    @BindView(R.id.title_input_editText)
    protected TextInputEditText titleEditText;
    @BindView(R.id.due_date_input_editText)
    protected TextInputEditText dateEditText;
    @BindView(R.id.details_input_editText)
    protected TextInputEditText detailsEditText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_creator, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        taskDatabase = ((TaskApplication) getActivity().getApplication()).getDatabase();
    }

    public static TaskCreatorFragment newInstance() {

        Bundle args = new Bundle();

        TaskCreatorFragment fragment = new TaskCreatorFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @OnClick(R.id.submit_button)
    protected void submitButtonClicked() {

        if(titleEditText.getText().toString().isEmpty() || dateEditText.getText().toString().isEmpty() || detailsEditText.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), "ALL FIELDS REQUIRED", Toast.LENGTH_LONG).show();
        } else {
//            Toast.makeText(getActivity(), "submit call made, unsuccessful", Toast.LENGTH_SHORT).show();

            Task task = new Task(titleEditText.getText().toString(), detailsEditText.getText().toString(), new Date());
            addTaskToDatabase(task);
        }
    }

    private void addTaskToDatabase(final Task task) {

//        Toast.makeText(getActivity(), "submit successful, addtodatabase call made, unsuccessful", Toast.LENGTH_LONG).show();
        taskDatabase.taskDao().addTask(task);
        activityCallback.addClicked();

        Toast.makeText(getActivity(), "TASK ADDED SUCCESSFULLY", Toast.LENGTH_LONG).show();
    }

    public void attachParent(ActivityCallback activityCallback) {

        this.activityCallback = activityCallback;
    }

    public interface ActivityCallback {

        void addClicked();
    }
}
