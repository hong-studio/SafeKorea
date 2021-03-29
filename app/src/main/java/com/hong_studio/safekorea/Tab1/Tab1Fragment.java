package com.hong_studio.safekorea.Tab1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.gson.Gson;
import com.hong_studio.safekorea.MainActivity;
import com.hong_studio.safekorea.R;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Tab1Fragment extends Fragment {

    ArrayList<Tab1RecyclerItem> items= new ArrayList<>();
    ArrayList<Tab1RecyclerItem> items2= new ArrayList<>();
    ArrayList<Tab1RecyclerCityItem> items3= new ArrayList<>();
    RecyclerView recyclerView, recyclerView2, recyclerView3;
    Tab1RecyclerAdapter adapter;
    Tab1RecyclerAdapter2 adapter2;
    Tab1RecyclerAdapterCity adapterCity;

    TextView tvUpdateTime;
    SwipeRefreshLayout refreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab1_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //툴바 액션바로 세팅
        MaterialToolbar toolbar= view.findViewById(R.id.toolbar);
        ((MainActivity)getActivity()).setSupportActionBar(toolbar);

        //findViewById
        tvUpdateTime= view.findViewById(R.id.tv_updateTime);
        refreshLayout= view.findViewById(R.id.layout_refresh);

        //뷰 create 할때 데이터 불러오기
        loadData();

        //새로고침 할때 데이터 불러오기
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
                refreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        new Thread(){
            @Override
            public void run() {
                String address= "https://api.corona-19.kr/korea/?serviceKey=76zemVQoJkG8NAETlKMRPwUCf9DbBa4hi";
                String address2= "https://api.corona-19.kr/korea/country/new/?serviceKey=76zemVQoJkG8NAETlKMRPwUCf9DbBa4hi";

                try {
                    Gson gson= new Gson();

                    URL url= new URL(address);
                    InputStream is= url.openStream();
                    InputStreamReader isr= new InputStreamReader(is);
                    Amount amount = gson.fromJson(isr, Amount.class);

                    URL url2= new URL(address2);
                    InputStream is2= url2.openStream();
                    InputStreamReader isr2= new InputStreamReader(is2);
                    City city = gson.fromJson(isr2, City.class);

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvUpdateTime.setText(amount.updateTime);

                            items.clear();
                            items.add(new Tab1RecyclerItem("확진환자", amount.TotalCase, "(+"+ city.korea.newCase+")"));
                            items.add(new Tab1RecyclerItem("완치(격리해제)", amount.TotalRecovered, "(+"+ amount.TodayRecovered+")"));
                            items.add(new Tab1RecyclerItem("사망자", amount.TotalDeath, "(+"+ amount.TodayDeath+")"));
                            items.add(new Tab1RecyclerItem("치료중", amount.NowCase, "(+"+ amount.TotalCaseBefore+")"));

                            //확진율을 계산하기 위한 변수
                            double totalCase= Double.parseDouble(amount.TotalCase.replace(",", ""));
                            double totalChecking= Double.parseDouble(amount.TotalChecking.replace(",",""));
                            String casePercent= String.format("%.1f", (totalCase/totalChecking)*100);

                            items2.clear();
                            items2.add(new Tab1RecyclerItem("확진율", casePercent+"%", null));
                            items2.add(new Tab1RecyclerItem("완치율", amount.recoveredPercentage+"%", null));
                            items2.add(new Tab1RecyclerItem("사망률", amount.deathPercentage+"%", null));

                            items3.clear();
                            String cityRecoveringSeoul= String.valueOf((Integer.parseInt(city.seoul.totalCase.replace(",", ""))-Integer.parseInt(city.seoul.recovered.replace(",", ""))-Integer.parseInt(city.seoul.death.replace(",",""))));
                            items3.add(new Tab1RecyclerCityItem("서울", city.seoul.totalCase+" (+"+ city.seoul.newCase+")", city.seoul.recovered, city.seoul.death, cityRecoveringSeoul));
                            String cityRecoveringBusan= String.valueOf((Integer.parseInt(city.busan.totalCase.replace(",", ""))-Integer.parseInt(city.busan.recovered.replace(",", ""))-Integer.parseInt(city.busan.death.replace(",",""))));
                            items3.add(new Tab1RecyclerCityItem("부산", city.busan.totalCase+" (+"+ city.busan.newCase+")", city.busan.recovered, city.busan.death, cityRecoveringBusan));
                            String cityRecoveringDaegu= String.valueOf((Integer.parseInt(city.daegu.totalCase.replace(",", ""))-Integer.parseInt(city.daegu.recovered.replace(",", ""))-Integer.parseInt(city.daegu.death.replace(",",""))));
                            items3.add(new Tab1RecyclerCityItem("대구", city.daegu.totalCase+" (+"+ city.daegu.newCase+")", city.daegu.recovered, city.daegu.death, cityRecoveringDaegu));
                            String cityRecoveringIncheon= String.valueOf((Integer.parseInt(city.incheon.totalCase.replace(",", ""))-Integer.parseInt(city.incheon.recovered.replace(",", ""))-Integer.parseInt(city.incheon.death.replace(",",""))));
                            items3.add(new Tab1RecyclerCityItem("인천", city.incheon.totalCase+" (+"+ city.incheon.newCase+")", city.incheon.recovered, city.incheon.death, cityRecoveringIncheon));
                            String cityRecoveringGwangju= String.valueOf((Integer.parseInt(city.gwangju.totalCase.replace(",", ""))-Integer.parseInt(city.gwangju.recovered.replace(",", ""))-Integer.parseInt(city.gwangju.death.replace(",",""))));
                            items3.add(new Tab1RecyclerCityItem("광주", city.gwangju.totalCase+" (+"+ city.gwangju.newCase+")", city.gwangju.recovered, city.gwangju.death, cityRecoveringGwangju));

                            String cityRecoveringDaejeon= String.valueOf((Integer.parseInt(city.daejeon.totalCase.replace(",", ""))-Integer.parseInt(city.daejeon.recovered.replace(",", ""))-Integer.parseInt(city.daejeon.death.replace(",",""))));
                            items3.add(new Tab1RecyclerCityItem("대전", city.daejeon.totalCase+" (+"+ city.daejeon.newCase+")", city.daejeon.recovered, city.daejeon.death, cityRecoveringDaejeon));
                            String cityRecoveringUlsan= String.valueOf((Integer.parseInt(city.ulsan.totalCase.replace(",", ""))-Integer.parseInt(city.ulsan.recovered.replace(",", ""))-Integer.parseInt(city.ulsan.death.replace(",",""))));
                            items3.add(new Tab1RecyclerCityItem("울산", city.ulsan.totalCase+" (+"+ city.ulsan.newCase+")", city.ulsan.recovered, city.ulsan.death, cityRecoveringUlsan));
                            String cityRecoveringSejong= String.valueOf((Integer.parseInt(city.sejong.totalCase.replace(",", ""))-Integer.parseInt(city.sejong.recovered.replace(",", ""))-Integer.parseInt(city.sejong.death.replace(",",""))));
                            items3.add(new Tab1RecyclerCityItem("세종", city.sejong.totalCase+" (+"+ city.sejong.newCase+")", city.sejong.recovered, city.sejong.death, cityRecoveringSejong));
                            String cityRecoveringGyeonggi= String.valueOf((Integer.parseInt(city.gyeonggi.totalCase.replace(",", ""))-Integer.parseInt(city.gyeonggi.recovered.replace(",", ""))-Integer.parseInt(city.gyeonggi.death.replace(",",""))));
                            items3.add(new Tab1RecyclerCityItem("경기", city.gyeonggi.totalCase+" (+"+ city.gyeonggi.newCase+")", city.gyeonggi.recovered, city.gyeonggi.death, cityRecoveringGyeonggi));
                            String cityRecoveringGangwon= String.valueOf((Integer.parseInt(city.gangwon.totalCase.replace(",", ""))-Integer.parseInt(city.gangwon.recovered.replace(",", ""))-Integer.parseInt(city.gangwon.death.replace(",",""))));
                            items3.add(new Tab1RecyclerCityItem("강원", city.gangwon.totalCase+" (+"+ city.gangwon.newCase+")", city.gangwon.recovered, city.gangwon.death, cityRecoveringGangwon));

                            String cityRecoveringChungbuk= String.valueOf((Integer.parseInt(city.chungbuk.totalCase.replace(",", ""))-Integer.parseInt(city.chungbuk.recovered.replace(",", ""))-Integer.parseInt(city.chungbuk.death.replace(",",""))));
                            items3.add(new Tab1RecyclerCityItem("충북", city.chungbuk.totalCase+" (+"+ city.chungbuk.newCase+")", city.chungbuk.recovered, city.chungbuk.death, cityRecoveringChungbuk));
                            String cityRecoveringChungnam= String.valueOf((Integer.parseInt(city.chungnam.totalCase.replace(",", ""))-Integer.parseInt(city.chungnam.recovered.replace(",", ""))-Integer.parseInt(city.chungnam.death.replace(",",""))));
                            items3.add(new Tab1RecyclerCityItem("충남", city.chungnam.totalCase+" (+"+ city.chungnam.newCase+")", city.chungnam.recovered, city.chungnam.death, cityRecoveringChungnam));
                            String cityRecoveringJeonbuk= String.valueOf((Integer.parseInt(city.jeonbuk.totalCase.replace(",", ""))-Integer.parseInt(city.jeonbuk.recovered.replace(",", ""))-Integer.parseInt(city.jeonbuk.death.replace(",",""))));
                            items3.add(new Tab1RecyclerCityItem("전북", city.jeonbuk.totalCase+" (+"+ city.jeonbuk.newCase+")", city.jeonbuk.recovered, city.jeonbuk.death, cityRecoveringJeonbuk));
                            String cityRecoveringJeonnam= String.valueOf((Integer.parseInt(city.jeonnam.totalCase.replace(",", ""))-Integer.parseInt(city.jeonnam.recovered.replace(",", ""))-Integer.parseInt(city.jeonnam.death.replace(",",""))));
                            items3.add(new Tab1RecyclerCityItem("전남", city.jeonnam.totalCase+" (+"+ city.jeonnam.newCase+")", city.jeonnam.recovered, city.jeonnam.death, cityRecoveringJeonnam));
                            String cityRecoveringGyeongbuk= String.valueOf((Integer.parseInt(city.gyeongbuk.totalCase.replace(",", ""))-Integer.parseInt(city.gyeongbuk.recovered.replace(",", ""))-Integer.parseInt(city.gyeongbuk.death.replace(",",""))));
                            items3.add(new Tab1RecyclerCityItem("경북", city.gyeongbuk.totalCase+" (+"+ city.gyeongbuk.newCase+")", city.gyeongbuk.recovered, city.gyeongbuk.death, cityRecoveringGyeongbuk));

                            String cityRecoveringGyeongnam= String.valueOf((Integer.parseInt(city.gyeongnam.totalCase.replace(",", ""))-Integer.parseInt(city.gyeongnam.recovered.replace(",", ""))-Integer.parseInt(city.gyeongnam.death.replace(",",""))));
                            items3.add(new Tab1RecyclerCityItem("경남", city.gyeongnam.totalCase+" (+"+ city.gyeongnam.newCase+")", city.gyeongnam.recovered, city.gyeongnam.death, cityRecoveringGyeongnam));
                            String cityRecoveringJeju= String.valueOf((Integer.parseInt(city.jeju.totalCase.replace(",", ""))-Integer.parseInt(city.jeju.recovered.replace(",", ""))-Integer.parseInt(city.jeju.death.replace(",",""))));
                            items3.add(new Tab1RecyclerCityItem("제주", city.jeju.totalCase+" (+"+ city.jeju.newCase+")", city.jeju.recovered, city.jeju.death, cityRecoveringJeju));



                            recyclerView= getActivity().findViewById(R.id.recyclerView);
                            adapter= new Tab1RecyclerAdapter(getActivity(), items);
                            recyclerView.setAdapter(adapter);

                            recyclerView2= getActivity().findViewById(R.id.recyclerView2);
                            adapter2= new Tab1RecyclerAdapter2(getActivity(), items2);
                            recyclerView2.setAdapter(adapter2);

                            recyclerView3= getActivity().findViewById(R.id.recyclerView3);
                            adapterCity= new Tab1RecyclerAdapterCity(getActivity(), items3);
                            recyclerView3.setAdapter(adapterCity);
                        }
                    });

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
