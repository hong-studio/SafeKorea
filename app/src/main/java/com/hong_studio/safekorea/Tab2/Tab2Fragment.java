package com.hong_studio.safekorea.Tab2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.gson.Gson;
import com.hong_studio.safekorea.MainActivity;
import com.hong_studio.safekorea.R;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class Tab2Fragment extends Fragment implements MapView.POIItemEventListener {

    MapView mapView;
    ImageButton btnZoomIn, btnZoomOut;
    TextView tvFacilityName, tvCenterName, tvAddress;
    LinearLayout infoContainer;
    String address= "https://api.odcloud.kr/api/15077586/v1/centers?serviceKey=LlFZBA1ng4eSS58QsTtIQy%2FMVcOyDEIHXDkXTx%2FYxCXw0MrvCi6JGzP%2B99BwIuEo5cJSYuuiRphKOWtdrz7LKg%3D%3D&page=1&perPage=10000";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab2_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MaterialToolbar toolbar= view.findViewById(R.id.toolbar);
        ((MainActivity)getActivity()).setSupportActionBar(toolbar);

        mapView= new MapView(getActivity());
        RelativeLayout mapContainer= getActivity().findViewById(R.id.map_container);
        mapContainer.addView(mapView);

        //N서울타워 중심으로 잡기
        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(37.552923902193704, 126.98813584242313), true);
        mapView.setZoomLevel(11, true);
        mapView.zoomIn(true);
        mapView.zoomOut(true);

        btnZoomIn= getActivity().findViewById(R.id.btn_zoomIn);
        btnZoomOut= getActivity().findViewById(R.id.btn_zoomOut);
        tvFacilityName= getActivity().findViewById(R.id.tv_facilityName);
        tvCenterName= getActivity().findViewById(R.id.tv_centerName);
        tvAddress= getActivity().findViewById(R.id.tv_address);
        infoContainer= getActivity().findViewById(R.id.info_container);

        setZoomInAndZoomOut();
        loadDataAndSetMarker();
        mapView.setPOIItemEventListener(this);
    }

    private void setZoomInAndZoomOut() {
        btnZoomIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapView.zoomIn(true);
            }
        });
        btnZoomOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapView.zoomOut(true);
            }
        });
    }

    private void loadDataAndSetMarker() {
        new Thread(){
            @Override
            public void run() {

                try {
                    Gson gson= new Gson();
                    URL url= new URL(address);
                    InputStream is= url.openStream();
                    InputStreamReader isr= new InputStreamReader(is);
                    VaccineCenters centers= gson.fromJson(isr, VaccineCenters.class);

                    for(int i=0; i<centers.totalCount; i++) {
                        MapPOIItem marker = new MapPOIItem();
                        marker.setItemName(centers.data[i].facilityName);
                        marker.setTag(i);
                        double lat = Double.parseDouble(centers.data[i].lat);
                        double lng = Double.parseDouble(centers.data[i].lng);
                        MapPoint point = MapPoint.mapPointWithGeoCoord(lat, lng);
                        marker.setMapPoint(point);
                        marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);
                        mapView.addPOIItem(marker);
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    @Override
    public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {

        new Thread(){
            @Override
            public void run() {
                try {
                    Gson gson= new Gson();
                    URL url= new URL(address);
                    InputStream is= url.openStream();
                    InputStreamReader isr= new InputStreamReader(is);
                    VaccineCenters centers= gson.fromJson(isr, VaccineCenters.class);

//                    String markerName= mapPOIItem.getItemName();
                    int markerTag= mapPOIItem.getTag();
                    double lat = Double.parseDouble(centers.data[markerTag].lat);
                    double lng = Double.parseDouble(centers.data[markerTag].lng);
                    mapView.setMapCenterPointAndZoomLevel(MapPoint.mapPointWithGeoCoord(lat, lng), 2, true);

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            infoContainer.setVisibility(View.VISIBLE);
                            tvFacilityName.setText(centers.data[markerTag].facilityName);
                            tvCenterName.setText(centers.data[markerTag].centerName);
                            tvAddress.setText(centers.data[markerTag].address);
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

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {

    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {

    }

    @Override
    public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {

    }
}
