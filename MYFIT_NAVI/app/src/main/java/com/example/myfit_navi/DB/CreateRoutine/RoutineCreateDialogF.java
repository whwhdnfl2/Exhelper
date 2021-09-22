package com.example.myfit_navi.DB.CreateRoutine;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

import com.example.myfit_navi.DB.QueryClass;
import com.example.myfit_navi.R;
import com.example.myfit_navi.cfg.Config;

public class RoutineCreateDialogF extends DialogFragment {

    private static RoutineCreateListener RoutineCreateListener;

    private EditText Exercise_nameEditText;
    private EditText Set_numEditText;
    private EditText Repeat_numEditText;
    private EditText Rest_timeEditText;
    private Button createButton;
    private Button cancelButton;

    private String Exercise_name = "";
    private int Set_num = -1;
    private int Repeat_num = -1;
    private int Rest_time = -1;

    public RoutineCreateDialogF() {
        // Required empty public constructor
    }

    public static RoutineCreateDialogF newInstance(String title, RoutineCreateListener listener){
        RoutineCreateListener = listener;
        RoutineCreateDialogF RoutineCreateDialogF = new RoutineCreateDialogF();
        Bundle args = new Bundle();
        args.putString("title", title);
        RoutineCreateDialogF.setArguments(args);

        RoutineCreateDialogF.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);

        return RoutineCreateDialogF;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.routine_create_dialog_f, container, false);

        Exercise_nameEditText = view.findViewById(R.id.Exercise_nameEditText);
        Set_numEditText = view.findViewById(R.id.Set_numEditText);
        Repeat_numEditText = view.findViewById(R.id.Repeat_numEditText);
        Rest_timeEditText = view.findViewById(R.id.Rest_timeEditText);
        createButton = view.findViewById(R.id.createButton);
        cancelButton = view.findViewById(R.id.cancelButton);

        String title = getArguments().getString(Config.TITLE);
        getDialog().setTitle(title);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Exercise_name = Exercise_nameEditText.getText().toString();
                Set_num = Integer.parseInt(Set_numEditText.getText().toString());
                Repeat_num = Integer.parseInt(Repeat_numEditText.getText().toString());
                Rest_time = Integer.parseInt(Rest_timeEditText.getText().toString());

                Routine Routine = new Routine(-1, Exercise_name, Set_num, Repeat_num, Rest_time);

                QueryClass databaseQueryClass = new QueryClass(getContext());

                long id = databaseQueryClass.insertRoutine(Routine);

                if(id>0){
                    Routine.setId(id);
                    RoutineCreateListener.onRoutineCreated(Routine);
                    getDialog().dismiss();
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            //noinspection ConstantConditions
            dialog.getWindow().setLayout(width, height);
        }
    }

}