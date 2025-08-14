package com.mnj.mobile.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResponse {
    private List<Object> payload = Collections.emptyList();
    private List<String> errorMessages = new ArrayList<>();
    private int status = 200;

    public CommonResponse(int status) {
        this.status = status;
    }
}
