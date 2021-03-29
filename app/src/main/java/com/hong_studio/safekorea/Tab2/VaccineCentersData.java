package com.hong_studio.safekorea.Tab2;

public class VaccineCentersData {
    String address;
    String centerName;
    String centerType;
    String facilityName;
    int id;
    String lat;
    String lng;
    String org;
    String sido;
    String sigungu;
    String zipCode;

    public VaccineCentersData() {
    }

    public VaccineCentersData(String address, String centerName, String centerType, String facilityName, int id, String lat, String lng, String org, String sido, String sigungu, String zipCode) {
        this.address = address;
        this.centerName = centerName;
        this.centerType = centerType;
        this.facilityName = facilityName;
        this.id = id;
        this.lat = lat;
        this.lng = lng;
        this.org = org;
        this.sido = sido;
        this.sigungu = sigungu;
        this.zipCode = zipCode;
    }
}
