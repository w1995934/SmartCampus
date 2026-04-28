package com.mycompany.smartcampus.model;

public class Room {
    private String roomID;
    private String name;
    private String building;
    private int floor;

    public Room() {}

    public Room(String roomID, String name, String building, int floor) {
    this.roomID = roomID;
    this.name = name;
    this.building = building;
    this.floor = floor;
}

    public String getRoomID() { return roomID; }
    public void setRoomID(String roomID) { this.roomID = roomID; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getBuilding() { return building; }
    public void setBuilding(String building) { this.building = building; }

    public int getFloor() { return floor; }
    public void setFloor(int floor) { this.floor = floor; }
}