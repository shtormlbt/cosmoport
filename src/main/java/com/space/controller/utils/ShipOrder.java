package com.space.controller.utils;

public enum ShipOrder {
    ID("id"), // default
    SPEED("speed"),
    DATE("prodDate"),
    RATING("rating");

    private String fieldName;

    ShipOrder(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }
}