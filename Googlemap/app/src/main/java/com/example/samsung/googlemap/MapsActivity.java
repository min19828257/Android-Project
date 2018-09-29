package com.example.samsung.googlemap;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private final int MY_PERMISSION_REQUEST_ACCESS_FINE_LOCATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        // 화면 가져오기
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //화면의 초기화면 이동위치 나타내기
        LatLng startingPoint = new LatLng(37.555744, 126.970431);// Starting 포인트 설정
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(startingPoint,16)); // newLatingZoom 첫번째 매개변수: 스타팅포인트 두번째 매개변수: 줌레벨

        // 사용자가 권한 승인시 내현위치기능 활성화, 권한 안할시 권한요청창 띄우기
        if(checkPermission()) {
            mMap.setMyLocationEnabled(true);
        }else
        {
            requestPermissions();
        }

        mMap.getUiSettings().setZoomControlsEnabled(true);  //줌 확대가능한지여부 띄우기 + -
        mMap.getUiSettings().setMyLocationButtonEnabled(true);  //나의 현재위치 알아내기

        // Add a marker in Sydney and move the camera 실제 이동방향의 화살표의 위치
        LatLng sydney = new LatLng(37.555744, 126.970431);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));


    }

    private boolean checkPermission() {
        return (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(
                this,
                new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
                MY_PERMISSION_REQUEST_ACCESS_FINE_LOCATION
        );
    }
}
