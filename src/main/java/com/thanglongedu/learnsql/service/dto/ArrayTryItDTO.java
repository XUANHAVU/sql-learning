package com.thanglongedu.learnsql.service.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class ArrayTryItDTO implements Serializable {
    private ArrayList<HashMap<String, Object>> row;

    private Integer updateCount;

    public ArrayList<HashMap<String, Object>> getRow() {
        return row;
    }

    public void setRow(ArrayList<HashMap<String, Object>> row) {
        this.row = row;
    }

    public Integer getUpdateCount() {
        return updateCount;
    }

    public void setUpdateCount(Integer updateCount) {
        this.updateCount = updateCount;
    }
}
