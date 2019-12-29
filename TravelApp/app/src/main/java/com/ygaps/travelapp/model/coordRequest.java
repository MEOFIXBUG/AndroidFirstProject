package com.ygaps.travelapp.model;

import java.util.List;

public class coordRequest {
    private boolean hasOneCoordinate;

    private List<CoordSet> coordList;

    public boolean getHasOneCoordinate ()
    {
        return hasOneCoordinate;
    }

    public void setHasOneCoordinate (boolean hasOneCoordinate)
    {
        this.hasOneCoordinate = hasOneCoordinate;
    }

    public List<CoordSet> getCoordList ()
    {
        return coordList;
    }

    public void setCoordList (List<CoordSet> coordList)
    {
        this.coordList = coordList;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [hasOneCoordinate = "+hasOneCoordinate+", coordList = "+coordList+"]";
    }
}
