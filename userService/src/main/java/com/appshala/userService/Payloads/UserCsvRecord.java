package com.appshala.userService.Payloads;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;

@Data

public class UserCsvRecord {

    @CsvBindByName(column = "Name")
    private String name;

    @CsvBindByName(column = "Role")
    private String role;

    @CsvBindByName(column = "Email")
    private String email;
}
