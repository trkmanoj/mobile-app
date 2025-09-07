package com.mnj.mobile.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardDTO {
    private int id;
    private String title;
    private Long description;
    private String bgColor;
    private String bgImageColor;
}
