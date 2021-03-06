package com.twt.service.ui.bind;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.twt.service.R;
import com.twt.service.interactor.BindInteractorImpl;
import com.twt.service.ui.BaseActivity;
import com.twt.service.ui.common.NextActivity;
import com.twt.service.ui.gpa.GpaActivity;
import com.twt.service.ui.login.LoginActivity;
import com.twt.service.ui.main.MainActivity;
import com.twt.service.ui.schedule.ScheduleActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by sunjuntao on 16/1/1.
 */
public class BindActivity extends BaseActivity implements BindView {
    @InjectView(R.id.et_bind_account)
    EditText etBindAccount;
    @InjectView(R.id.et_bind_password)
    EditText etBindPassword;
    @InjectView(R.id.bt_bind)
    Button btBind;
    @InjectView(R.id.pb_bind)
    ProgressBar pbBind;
    private NextActivity nextActivity;
    private BindPresenter presenter;


    public static void actionStart(Context context, NextActivity nextActivity) {
        Intent intent = new Intent(context, BindActivity.class);
        intent.putExtra("nextactivity", nextActivity);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind);
        ButterKnife.inject(this);
        nextActivity = (NextActivity) getIntent().getSerializableExtra("nextactivity");
        presenter = new BindPresenterImpl(this, new BindInteractorImpl(), nextActivity);
        btBind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etBindAccount.getText().toString().isEmpty()) {
                    etBindAccount.setError("不能为空");
                } else if (etBindPassword.getText().toString().isEmpty()) {
                    etBindPassword.setError("不能为空");
                } else {
                    presenter.bind(etBindAccount.getText().toString(), etBindPassword.getText().toString());
                }

            }
        });
    }


    @Override
    public void showProgress() {
        pbBind.setVisibility(View.VISIBLE);
    }

    @Override
    public void hindProgress() {
        pbBind.setVisibility(View.GONE);
    }

    @Override
    public void toastMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void startMainActivity() {
        MainActivity.actionStart(this);
        finish();
    }

    @Override
    public void startGpaActivity() {
        GpaActivity.actionStart(this);
        finish();
    }

    @Override
    public void startLoginActivity() {
        LoginActivity.actionStart(this, nextActivity);
        finish();
    }

    @Override
    public void startScheduleActivity() {
        ScheduleActivity.actionStart(this);
        finish();
    }
}
