package com.hong_studio.safekorea.Tab1;

public class Tab1RecyclerCityItem {
    String cityName;
    String cityTotalCase;
    String cityRecovered;
    String cityDeath;
    String cityRecovering;

    public Tab1RecyclerCityItem() {
    }

    public Tab1RecyclerCityItem(String cityName, String cityTotalCase, String cityRecovered, String cityDeath, String cityRecovering) {
        this.cityName = cityName;
        this.cityTotalCase = cityTotalCase;
        this.cityRecovered = cityRecovered;
        this.cityDeath = cityDeath;
        this.cityRecovering = cityRecovering;
    }
}
