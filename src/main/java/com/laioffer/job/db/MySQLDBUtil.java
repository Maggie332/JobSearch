package com.laioffer.job.db;

public class MySQLDBUtil {
    private static final String INSTANCE = "xxx.us-west-2.rds.amazonaws.com";
    private static final String PORT_NUM = "xxx";
    public static final String DB_NAME = "xxx";
    private static final String USERNAME = "xxx";
    private static final String PASSWORD = "xxx";
    public static final String URL = "jdbc:mysql://"
            + INSTANCE + ":" + PORT_NUM + "/" + DB_NAME
            + "?user=" + USERNAME + "&password=" + PASSWORD
            + "&autoReconnect=true&serverTimezone=UTC";
}