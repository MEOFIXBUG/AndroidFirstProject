package com.ygaps.travelapp.model;

public class comment {
    private String name;

    private String comment;

    private int id;

    private String avatar;

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getComment ()
    {
        return comment;
    }

    public void setComment (String comment)
    {
        this.comment = comment;
    }

    public int getId ()
    {
        return id;
    }

    public void setId (int id)
    {
        this.id = id;
    }

    public String getAvatar ()
    {
        return avatar;
    }

    public void setAvatar (String avatar)
    {
        this.avatar = avatar;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [name = "+name+", comment = "+comment+", id = "+id+", avatar = "+avatar+"]";
    }
}
