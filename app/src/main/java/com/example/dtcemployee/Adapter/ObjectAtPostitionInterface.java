package com.example.dtcemployee.Adapter;

public interface ObjectAtPostitionInterface {

    /**
     * Returns the Object for the provided position, null if position doesn't match an object (i.e. out of bounds)
     **/
    Object getObjectAtPosition(int position);
}
