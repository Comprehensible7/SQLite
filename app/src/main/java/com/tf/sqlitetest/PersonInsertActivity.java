package com.tf.sqlitetest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import myutil.MyUtil;
import vo.PersonVo;

public class PersonInsertActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_insert);

        EditText et_name = findViewById(R.id.et_name);
        et_name.requestFocus();
    }

    public void onClickOk(View view){

        //컨트롤 참조값 구하기
        EditText et_name = findViewById(R.id.et_name);
        EditText et_tel  = findViewById(R.id.et_tel);

        //입력값 얻어오기
        String name = et_name.getText().toString().trim();
        String tel  = et_tel.getText().toString().trim();

        //비어있으면
        if(name.isEmpty()){
            MyUtil.showMessageDialog(this,"","이름을 입력하세요");
            et_name.setText("");
            et_name.requestFocus();
            return;
        }

        if(tel.isEmpty()){
            MyUtil.showMessageDialog(this,"","전화번호를 입력하세요");
            et_tel.setText("");
            et_tel.requestFocus();
            return;
        }

        //결과전송데이터 포장
        PersonVo vo = new PersonVo(name,tel);

        Intent data = new Intent();
        data.putExtra("vo",vo);

        // 호출측 onActivityResult 로 결과 전송
        setResult(Activity.RESULT_OK,data);

        finish();
    }

    public void onClickCancel(View view){
        finish();
    }

}
