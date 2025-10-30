package com.appShala.userGroupService.Enum;

public enum GroupSortBy {
    //  Enum Constants calling the constructor
    byName("name"),
    byDateModified("lastModifiedAt");

    // Private final field to store the String argument
    private final String dbField;
    // Private Constructor to initialize the field
    GroupSortBy(String dbField)
    {
        this.dbField = dbField;
    }
    // Public Getter Method to retrieve the value
    public String getDbField()
    {
        return dbField;
    }
}
