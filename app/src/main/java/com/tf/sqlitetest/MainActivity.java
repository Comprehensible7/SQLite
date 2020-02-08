package com.tf.sqlitetest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import myutil.MyUtil;
import service.SQLitePersonDao;
import vo.PersonVo;

public class MainActivity extends AppCompatActivity {

    SQLitePersonDao dao=null;
    ArrayList<PersonVo> p_list=null;
    ListView lv_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv_list =findViewById(R.id.lv_list);

        dao = new SQLitePersonDao(this);

        display_person_list();
        //Sample Data Insert
        //PersonVo vo = new PersonVo("이길동","010-222-1111");
        //dao.insert(vo);

        //조회
        //ArrayList<PersonVo> p_list = dao.selectList();
    }

    //등록버튼 클릭시
    public void onClick(View view){

        Intent intent = new Intent(this, PersonInsertActivity.class);
        startActivityForResult(intent,1);

    }

    @Override
    protected void onActivityResult(
            int requestCode,
            int resultCode,
            @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1){//등록작업의 결과
            if(resultCode== Activity.RESULT_OK){

                PersonVo vo = (PersonVo) data.getSerializableExtra("vo");

                //SQLite에 등록
                dao.insert(vo);

                //갱신목록을 출력
                display_person_list();
            }
        }else if(requestCode==2){//수정작업의 결과
            if(resultCode== Activity.RESULT_OK){

                PersonVo vo = (PersonVo) data.getSerializableExtra("vo");

                //SQLite에 수정
                dao.update(vo);

                //갱신목록을 출력
                display_person_list();
            }
        }


    }

    public void display_person_list(){
        //데이터 가져오기
        p_list = dao.selectList();
        lv_list.setAdapter(new PersonAdapter());
    }

    //ListView 배치 어뎁터
    class PersonAdapter extends BaseAdapter
    {
        @Override
        public int getCount() {
            return p_list.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView==null){
                convertView = getLayoutInflater().inflate(R.layout.person,null);
            }

            //컨트롤의 참조값
            TextView tv_name    = convertView.findViewById(R.id.tv_name);
            TextView tv_tel     = convertView.findViewById(R.id.tv_tel);
            Button   bt_del     = convertView.findViewById(R.id.bt_del);
            Button   bt_update  = convertView.findViewById(R.id.bt_update);

            //배치해야될 데이터 1개 얻어오기
            final PersonVo vo = p_list.get(position);

            //컨트롤에 배치
            tv_name.setText(vo.getName());
            tv_tel.setText(vo.getTel());

            //버튼이벤트
            //삭제버튼 클릭시
            bt_del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Handler handler = new Handler(){
                        @Override
                        public void handleMessage(@NonNull Message msg) {
                            super.handleMessage(msg);
                            if(msg.what==1){// yes 선택
                                //삭제
                                dao.delete(vo.getIdx());

                                //삭제후 데이터목록 갱신
                                display_person_list();
                            }
                        }
                    };//end handler

                    MyUtil.showConfirmDialog(MainActivity.this,
                                              "[삭제]",
                                          "정말 삭제하시겠습니까?",
                                               handler);


                }
            });

            //수정버튼 클릭시
            bt_update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(MainActivity.this,PersonUpdateActivity.class);
                    //현재 선택된 데이터 넘겨준다
                    intent.putExtra("vo",vo);

                    //결과값을 가져오기...
                    startActivityForResult(intent,2);
                }
            });


            return convertView;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }


    }


}
