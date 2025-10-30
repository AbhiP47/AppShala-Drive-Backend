package com.appShala.userGroupService.Enum;

import org.springframework.data.domain.Sort;

public enum SortDirection {
    A_TO_Z(Sort.Direction.ASC),       // Ascending (A to Z, 0 to 9, Oldest to Newest)
    Z_TO_A(Sort.Direction.DESC),      // Descending (Z to A, 9 to 0, Newest to Oldest)
    NEW_TO_OLD(Sort.Direction.DESC),  // Descending (for date fields: Newest/Most Recent first)
    OLD_TO_NEW(Sort.Direction.ASC);   // Ascending (for date fields: Oldest first)

    private final Sort.Direction direction;

    SortDirection(Sort.Direction direction)
    {
        this.direction = direction;
    }
    public Sort.Direction getDirection()
    {
        return direction;
    }
}
