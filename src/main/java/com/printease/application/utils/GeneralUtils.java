package com.printease.application.utils;

public class GeneralUtils {
    public static Double calculateDistance(Double longitude1, Double latitude1,
                                           Double longitude2, Double latitude2) {
        final double R = 6371; // Earth's radius in km
        double dLat = Math.toRadians(latitude2 - latitude1);
        double dLon = Math.toRadians(longitude2 - longitude1);
        double lat1 = Math.toRadians(latitude1);
        double lat2 = Math.toRadians(latitude2);

        double a = Math.pow(Math.sin(dLat / 2), 2) +
                Math.pow(Math.sin(dLon / 2), 2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c;
    }


}
