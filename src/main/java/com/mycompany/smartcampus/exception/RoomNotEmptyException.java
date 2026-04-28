package com.mycompany.smartcampus.exception;

public class RoomNotEmptyException extends RuntimeException {
    public RoomNotEmptyException(String roomID) {
        super("Room "+roomID+" The room still has sensors. Remove all sensors before deleting the room.");
    }
    
}
