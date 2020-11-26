package com.example.rehabilitation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.lang.Math.abs;

public class Angles {
    public List<Double> valuesX;
    public List<Double> valuesY;
    public List<Double> valuesZ;

    public Angles( List valuesX, List valuesY, List valuesZ) {
        this.valuesX = valuesX;
        this.valuesY = valuesY;
        this.valuesZ = valuesZ;
    }

    public double[] calculatePitchKnee(){

        List<Double> katy = new ArrayList();
        for (int i=0; i<valuesX.size();i++){
            katy.add(Math.toDegrees(Math.atan2(valuesX.get(i),valuesZ.get(i))));
        }
        double max = Collections.min(katy);
        double min = Collections.max(katy);

//        double maxX = Collections.max(valuesX);
//        double minX = Collections.min(valuesX);
//        double maxZ = Collections.max(valuesZ);
//        double minZ = Collections.min(valuesZ);
//
//
//        double Gpx_max = maxX;
//        double Gpz_max = maxZ;
//        double maxAngleRad = Math.atan2(Gpx_max,Gpz_max);
//        double maxAngleDeg = Math.toDegrees(maxAngleRad);
//
//        double Gpx_min = minX;
//        double Gpz_min = minZ;
//        double minAngleRad = Math.atan2(Gpx_min,Gpz_min);
//        double minAngleDeg = Math.toDegrees(minAngleRad);
        //System.out.println("maxX: "+maxX+"\n"+"maxZ: " +maxZ+"\n"+"minX: "+minX+"\n"+"minZ: "+minZ+"\n"+"minAngle: "+minAngleDeg+"\n"+"maxAngle: "+maxAngleDeg);
        double range = min-max;
        double[] angles = {max,min,range};
        System.out.println(Arrays.toString(angles));
        return angles;
    }


    public double[] calculateYawHip(){ //prywodzenie odwodzenie
        List<Double> katy = new ArrayList();
        for (int i=0; i<valuesX.size();i++){
            katy.add(Math.toDegrees(Math.atan2(valuesX.get(i),valuesY.get(i))));
        }
        double max = Collections.min(katy);
        double min = Collections.max(katy);
        double range = min-max;
        double[] angles = {max,min,range};
        System.out.println(Arrays.toString(angles));

        return angles;
    }


    public double[] calculatePitchHip(){ //zgięcie DZIAŁA
        List<Double> katy = new ArrayList();
        List<Double> bottom = new ArrayList();

        //lista pomocnicza dla określenia położenia początkowego i maksymalnej wartosci dodatniej
        for (int i=0; i<valuesX.size();i++){
                bottom.add(Math.toDegrees(Math.atan2(valuesX.get(i),valuesZ.get(i))));
            }
        double maxBottom = Collections.min(bottom);

        for (int i=0; i<valuesX.size();i++){
            if(Math.toDegrees(Math.atan2(valuesX.get(i),valuesZ.get(i)))<0){
                katy.add(maxBottom+abs(Math.toDegrees(Math.atan2(valuesX.get(i), valuesZ.get(i)))));
            }else {
                katy.add(Math.toDegrees(Math.atan2(valuesX.get(i),valuesZ.get(i))));
            }
        }
        double max = Collections.min(katy);
        double min = Collections.max(katy);
        double range = min-max;
        double[] angles = {max,min,range};
        System.out.println(Arrays.toString(angles));

        return angles;
    }

    public double[] calculateRotHip(){ //rotacja
        List<Double> katy = new ArrayList();
        for (int i=0; i<valuesX.size();i++){

                katy.add(Math.toDegrees(Math.atan2(valuesY.get(i), valuesZ.get(i))));

        }
        double max = Collections.min(katy);
        double min = Collections.max(katy);
        double range = min-max;
        double[] angles = {max,min,range};
        System.out.println(Arrays.toString(angles));

        return angles;

    }

}
