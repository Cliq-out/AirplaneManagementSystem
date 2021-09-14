package utils;

public class DistanceUtil {


    public int distanceBetweenAirports (double lat1, double lon1, double lat2, double lon2) {


        double theta = lon1 - lon2;
        double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
        dist = Math.acos(dist);
        dist = Math.toDegrees(dist);
        dist = dist * 60 * 1.1515;
        dist = dist * 1.609344;
        int distanceAirports = (int)dist;
        return distanceAirports;
    }

    public static void main(String[] args){
        double la1=7.8103;
        double la2=9.85606;
        double lo1=36.8236;
        double lo2=57.0952  ;
        DistanceUtil du = new DistanceUtil();
        int di= du.distanceBetweenAirports(la1,lo1,la2,lo2);
        System.out.println(di) ;
    }
}
//7.8103	36.8236 El Merah
// 9.85606	57.0952                 Albor9