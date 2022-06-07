package com.example.solldientu;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.solldientu.Api.ApiTaiKhoan;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    ImageView visible_img;
    EditText account_edt, pass_edt;
    Button login_btn;
    int is_visible = 1;
    String maSV = "";

    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Init();
        Events();
//        getAllDaa();
    }

    private void getAllDaa() {
        ApiTaiKhoan.apiService.getAll().enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                Toast.makeText(LoginActivity.this, "dataSuccess : " + response.body().toString(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Fail !!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void Events() {
        visible_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (is_visible == 1) {
                    is_visible = 0;
                    visible_img.setImageResource(R.drawable.ic_visibility_20);
                    pass_edt.setInputType(InputType.TYPE_CLASS_TEXT);
                } else {
                    is_visible = 1;
                    visible_img.setImageResource(R.drawable.ic_visibility_off_20);
                    pass_edt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login();
            }
        });
    }

    private void Login() {
        pd=new ProgressDialog(this);
        pd.setMessage("Đang đăng nhập...");
        pd.show();

        if (account_edt.getText().toString().equals("") || pass_edt.getText().toString().equals("")) {
            pd.dismiss();
            Toast.makeText(LoginActivity.this, "Tài khoản và mật khẩu không được để trống !", Toast.LENGTH_SHORT).show();
        } else {
            ApiTaiKhoan.apiService.getMa(account_edt.getText().toString(), pass_edt.getText().toString()).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    pd.dismiss();
                    if (response.isSuccessful()) {
                        maSV = response.body();
                        Toast.makeText(LoginActivity.this, "Đăng nhập thành công !", Toast.LENGTH_SHORT).show();
                        Log.d("EEE", "onResponse: "+ response.body());

                        Intent it = new Intent(LoginActivity.this, MainActivity.class);
                        it.putExtra("maSV", maSV);
                        startActivity(it);
                    } else {
                        Toast.makeText(LoginActivity.this, "Tài khoản hoặc mật khẩu không chính xác !", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    pd.dismiss();
                    Toast.makeText(LoginActivity.this, "Tài khoản hoặc mật khẩu không chính xác!", Toast.LENGTH_SHORT).show();
                    Log.d("EEE", "onResponse: "+ t.toString());
                }
            });
        }
    }

    private void Init() {
        visible_img = findViewById(R.id.visible_img);
        account_edt = findViewById(R.id.account_edt);
        pass_edt = findViewById(R.id.pass_edt);
        login_btn = findViewById(R.id.login_btn);

        account_edt = (EditText) findViewById(R.id.account_edt);
        pass_edt = (EditText) findViewById(R.id.pass_edt);

    }
}