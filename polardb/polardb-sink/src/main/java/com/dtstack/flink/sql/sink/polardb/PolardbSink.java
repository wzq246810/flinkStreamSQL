package com.dtstack.flink.sql.sink.polardb;

import com.dtstack.flink.sql.sink.IStreamSinkGener;
import com.dtstack.flink.sql.sink.rdb.RdbSink;
import com.dtstack.flink.sql.sink.rdb.format.RetractJDBCOutputFormat;

import java.util.List;
import java.util.Map;

public class PolardbSink extends RdbSink implements IStreamSinkGener<RdbSink> {

    private static final String POLARDB_DRIVER = "com.mysql.cj.jdbc.Driver";

    public PolardbSink() {
    }

    @Override
    public RetractJDBCOutputFormat getOutputFormat() {
        return new RetractJDBCOutputFormat();
    }

    @Override
    public void buildSql(String scheam, String tableName, List<String> fields) {
        buildInsertSql(tableName, fields);
    }

    @Override
    public String buildUpdateSql(String schema, String tableName, List<String> fieldNames, Map<String, List<String>> realIndexes, List<String> fullField) {
        return null;
    }

    private void buildInsertSql(String tableName, List<String> fields) {
        String sqlTmp = "replace into " +  tableName + " (${fields}) values (${placeholder})";
        String fieldsStr = "";
        String placeholder = "";

        for (String fieldName : fields) {
            fieldsStr += ",`" + fieldName + "`";
            placeholder += ",?";
        }

        fieldsStr = fieldsStr.replaceFirst(",", "");
        placeholder = placeholder.replaceFirst(",", "");

        sqlTmp = sqlTmp.replace("${fields}", fieldsStr).replace("${placeholder}", placeholder);
        this.sql = sqlTmp;
    }


    @Override
    public String getDriverName() {
        return POLARDB_DRIVER;
    }
}
