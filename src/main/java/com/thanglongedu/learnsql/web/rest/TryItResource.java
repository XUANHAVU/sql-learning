package com.thanglongedu.learnsql.web.rest;

import com.thanglongedu.learnsql.service.dto.ArrayTryItDTO;
import com.thanglongedu.learnsql.service.dto.TryItQueryDTO;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TryItResource {

    @PostMapping("/try-it")
    public List<ArrayTryItDTO> dataQuery(@RequestBody TryItQueryDTO tryItQueryDTO) {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            String PASSWORD = "123456aA@";
            String USER_NAME = "root";
            String DB_URL = "jdbc:mysql://localhost:3306/LearnSQL?allowMultiQueries=true&?useUnicode=true&characterEncoding=utf8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC";
            // String DB_URL = "jdbc:mysql://localhost:3306/LearnSQL";
            Connection conn = DriverManager.getConnection(DB_URL,
                USER_NAME, PASSWORD);

            // create statement
            Statement stm = null;
            stm = conn.createStatement();

            //query
            ResultSet result = null;

            List<ArrayTryItDTO> list = new ArrayList<>();

            boolean hasMoreResultSets = stm.execute(tryItQueryDTO.getQuery());

            READING_QUERY_RESULTS: // label
            while ( hasMoreResultSets || stm.getUpdateCount() != -1 ) {
                if ( hasMoreResultSets ) {
                    ArrayTryItDTO arrayTryItDTO = new ArrayTryItDTO();

                    result = stm.getResultSet();

                    //get metadata
                    ResultSetMetaData meta = null;
                    meta = result.getMetaData();

                    Integer columnCount = meta.getColumnCount();

                    //get column names
                    ArrayList<String> cols = new ArrayList<String>();
                    for (int index = 1; index <= columnCount; index++)
                        cols.add(meta.getColumnName(index));

                    //fetch out rows
                    ArrayList<HashMap<String, Object>> rows =
                        new ArrayList<HashMap<String, Object>>();

                    while (result.next()) {
                        HashMap<String, Object> row = new HashMap<String, Object>();
                        for (String colName : cols) {
                            Object val = result.getObject(colName);
                            row.put(colName, val);
                        }
                        rows.add(row);
                    }
                    arrayTryItDTO.setRow(rows);
                    arrayTryItDTO.setUpdateCount(0);
                    list.add(arrayTryItDTO);

                    // handle your rs here
                } // if has rs
                else { // if ddl/dml/...
                    int queryResult = stm.getUpdateCount();
                    if ( queryResult == -1 ) { // no more queries processed
                        break READING_QUERY_RESULTS;
                    } // no more queries processed
                    // handle success, failure, generated keys, etc here
                    ArrayTryItDTO arrayTryItDTO = new ArrayTryItDTO();
                    arrayTryItDTO.setRow(new ArrayList<>());
                    arrayTryItDTO.setUpdateCount(stm.getUpdateCount());
                    list.add(arrayTryItDTO);
                } // if ddl/dml/...

                // check to continue in the loop
                hasMoreResultSets = stm.getMoreResults();
            } // while results

//            stm.getUpdateCount()

//            if (hasMoreResultSets)
//                result = stm.getResultSet();
//            else
//                return new ArrayList<>();

            //close statement
            stm.close();

            return list;
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
            return new ArrayList<>();
        }
    }
}
