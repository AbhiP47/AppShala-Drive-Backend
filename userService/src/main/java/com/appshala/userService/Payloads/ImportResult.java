package com.appshala.userService.Payloads;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
public class ImportResult {

    private String status;
    private String message;
    private int processedCount;
    private int errorCount;
    private List<Map<String , String>> errorDetails;
}
