package com.ygaps.travelapp.model;

import java.util.ArrayList;
import java.util.List;

public class CoordSet {
    private List<Coord> coordinateSet =new ArrayList<>();
    public CoordSet(Coord a, Coord b){
        coordinateSet.add(a);
        coordinateSet.add(b);
    }
}
