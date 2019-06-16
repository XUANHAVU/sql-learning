package com.thanglongedu.learnsql.service.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

public class TryItQueryDTO implements Serializable {
    @NotNull
    private String query;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TryItQueryDTO categoryDTO = (TryItQueryDTO) o;
        if (categoryDTO.getQuery() == null || getQuery() == null) {
            return false;
        }
        return Objects.equals(getQuery(), categoryDTO.getQuery());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getQuery());
    }

    @Override
    public String toString() {
        return "TryItQueryDTO{" +
            "query=" + getQuery() +
            "}";
    }
}
