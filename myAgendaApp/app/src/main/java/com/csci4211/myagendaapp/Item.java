package com.csci4211.myagendaapp;

public class Item {
    private int id;
    private String name;
    private String date;
    private String time;
    private String description;
    private boolean highPriority;

    // constructor
    public Item(int id, String name, String date, String time, String description, boolean highPriority)
    {
        this.id = id;
        this.name = name;
        this.date = date;
        this. time = time;
        this.description = description;
        this.highPriority = highPriority;
    }

    // getters & setters
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public String getDate()
    {
        return date;
    }
    public void setDate(String date)
    {
        this.date = date;
    }
    public String getTime()
    {
        return time;
    }
    public void setTime(String time)
    {
        this.time = time;
    }
    public String getDescription()
    {
        return description;
    }
    public void setDescription(String description)
    {
        this.description = description;
    }
    public boolean getPriority()
    {
        return highPriority;
    }
    public void setPriority(boolean priority)
    {
        this.highPriority = priority;
    }
}
