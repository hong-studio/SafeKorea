package com.hong_studio.safekorea;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.messaging.FirebaseMessaging;
import com.hong_studio.safekorea.Tab1.Tab1Fragment;
import com.hong_studio.safekorea.Tab2.Tab2Fragment;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bnv;
    Fragment[] fragments= new Fragment[2];
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if(!task.isSuccessful()){
                    Toast.makeText(MainActivity.this, "푸시서비스 등록 실패", Toast.LENGTH_SHORT).show();
                    return;
                }

                String token= task.getResult();

                //추후 이 토큰값을 dothome 서버에서 사용하고자 하기에 Log로 출력해보기
                //그리고 화면에 보기 위해 Toast도 출력
//                Toast.makeText(MainActivity.this, ""+token, Toast.LENGTH_SHORT).show();
//                Log.i("TOKEN", token);

                //원래는 이 token 값을 웹서버(dothome 같은)에 전송하여
                //회원정보를 DB에 저장하듯이 token값도 DB에 저장해 놓아야 함
            }
        });

        //키 해시값 얻어오느 기능메소드 호출(카카오지도)
//        String keyHash= Utility.getKeyHash(this);
//        Log.i("KEY", keyHash);

        fragmentManager= getSupportFragmentManager();
        FragmentTransaction tran= fragmentManager.beginTransaction();
        fragments[0]= new Tab1Fragment();
        tran.add(R.id.container, fragments[0]);
        tran.commit();

        bnv= findViewById(R.id.bottom_navigation_view);
        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction tran= fragmentManager.beginTransaction();
                if(fragments[0]!=null) tran.hide(fragments[0]);
                if(fragments[1]!=null) tran.hide(fragments[1]);

                switch (item.getItemId()){
                    case R.id.bnv_tab1:
                        tran.show(fragments[0]);
                        break;
                    case R.id.bnv_tab2:
                        if(fragments[1]==null){
                            fragments[1]= new Tab2Fragment();
                            tran.add(R.id.container, fragments[1]);
                        }
                        tran.show(fragments[1]);
                        break;
                }
                tran.commit();
                return true;
            }
        });
    }
}