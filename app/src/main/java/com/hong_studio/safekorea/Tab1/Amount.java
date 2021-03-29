package com.hong_studio.safekorea.Tab1;

public class Amount {
    String resultCode;
    String TotalCase;
    String TotalRecovered;
    String TotalDeath;
    String NowCase;
    String city1n;
    String city2n;
    String city3n;
    String city4n;
    String city5n;
    String city1p;
    String city2p;
    String city3p;
    String city4p;
    String city5p;

    double recoveredPercentage;
    double deathPercentage;
    String checkingCounter;
    String checkingPercentage;
    String caseCount;
    String casePercentage;
    String notcaseCount;
    String notcasePercentage;
    String TotalChecking;
    String TodayRecovered;
    String TodayDeath;
    String TotalCaseBefore;
    String source;
    String updateTime;
    String resultMessage;

    public Amount() {
    }

    public Amount(String resultCode, String totalCase, String totalRecovered, String totalDeath, String nowCase, String city1n, String city2n, String city3n, String city4n, String city5n, String city1p, String city2p, String city3p, String city4p, String city5p, double recoveredPercentage, double deathPercentage, String checkingCounter, String checkingPercentage, String caseCount, String casePercentage, String notcaseCount, String notcasePercentage, String totalChecking, String todayRecovered, String todayDeath, String totalCaseBefore, String source, String updateTime, String resultMessage) {
        this.resultCode = resultCode;
        TotalCase = totalCase;
        TotalRecovered = totalRecovered;
        TotalDeath = totalDeath;
        NowCase = nowCase;
        this.city1n = city1n;
        this.city2n = city2n;
        this.city3n = city3n;
        this.city4n = city4n;
        this.city5n = city5n;
        this.city1p = city1p;
        this.city2p = city2p;
        this.city3p = city3p;
        this.city4p = city4p;
        this.city5p = city5p;
        this.recoveredPercentage = recoveredPercentage;
        this.deathPercentage = deathPercentage;
        this.checkingCounter = checkingCounter;
        this.checkingPercentage = checkingPercentage;
        this.caseCount = caseCount;
        this.casePercentage = casePercentage;
        this.notcaseCount = notcaseCount;
        this.notcasePercentage = notcasePercentage;
        TotalChecking = totalChecking;
        TodayRecovered = todayRecovered;
        TodayDeath = todayDeath;
        TotalCaseBefore = totalCaseBefore;
        this.source = source;
        this.updateTime = updateTime;
        this.resultMessage = resultMessage;
    }
}






