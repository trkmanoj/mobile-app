package com.mnj.mobile.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "scm_tool")
public class ScmTools {
    private Integer id;
    private String name;
    private boolean status;

}
