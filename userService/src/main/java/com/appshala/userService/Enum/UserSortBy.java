package com.appshala.userService.Enum;

public enum UserSortBy {

    //  Enum Constants calling the constructor
    byName("name"),
    byDateModified("updatedAt");

    // Private final field to store the String argument
    private final String dbField;

    // Private Constructor to initialize the field
    UserSortBy(String dbField) {
        this.dbField = dbField;
    }

    // Public Getter Method to retrieve the value
    public String getDbField() {
        return dbField;
    }
}