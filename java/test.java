package Placementprep.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class test {
    public static void main(String[] args) {
        ArrayList<ArrayList<Integer>> arrayList = new ArrayList<>();
        arrayList.add(new ArrayList<>(Arrays.asList(1,2,3,4,5,6)));
        arrayList.add(new ArrayList<>(Arrays.asList(21,22,23,24,25,26)));
        arrayList.add(new ArrayList<>(Arrays.asList(31,32,33,34,35,36)));

        System.out.println(arrayList.stream().flatMap(arr -> arr.stream()).collect(Collectors.toList()));

        Integer[][] arr= {
            {1,2,3},
            {11,22,33},
            {111,222,333}
        };
        System.out.println(Arrays.stream(arr).flatMap(ar -> Arrays.stream(ar)).collect(Collectors.toList()));
    }
}
