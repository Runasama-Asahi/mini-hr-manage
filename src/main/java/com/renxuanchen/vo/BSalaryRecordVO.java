package com.renxuanchen.vo;

import com.renxuanchen.entity.BSalaryRecord;
import lombok.Data;

@Data
public class BSalaryRecordVO extends BSalaryRecord {
    private String name;
    private String deptname;
}
