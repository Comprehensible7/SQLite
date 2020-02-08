package com.tf.sqlitetest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import myutil.MyUtil;
import vo.PersonVo;

public class PersonUpdateActivity extends AppCompatActivity {

    EditText et_name_update;
    EditText et_tel_update;
    PersonVo vo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_update);

        //호출시 넘겨준 vo 받기
        vo = (PersonVo) getIntent().getSerializableExtra("vo");

        //참조값
        et_name_update = findViewById(R.id.et_name_update);
        et_tel_update  = findViewById(R.id.et_tel_update);

        //원본데이터 출력
        et_name_update.setText(vo.getName());
        et_tel_update.setText(vo.getTel());
        
    }

    public void onClickOk(View view){

        //입력값 얻어오기
        String name = et_name_update.getText().toString().trim();
        String tel  = et_tel_update.getText().toString().trim();

        //비어있으면
        if(name.isEmpty()){
            MyUtil.showMessageDialog(this,"","이름을 입력하세요");
            et_name_update.setText("");
            et_name_update.requestFocus();
            return;
        }

        if(tel.isEmpty()){
            MyUtil.showMessageDialog(this,"","전화번호를 입력하세요");
            et_tel_update.setText("");
            et_tel_update.requestFocus();
            return;
        }

        //수정할 데이터 변경
        vo.setName(name);
        vo.setTel(tel);


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
