package com.grid.appy.citizenrights.config;

/**
 * Created by Appy on 24-Jul-17.
 */

public class AppConfig {
    // Server user login url



   public static String ipaddress="http://192.168.1.101/Grid/";

    public static String URL_LOGIN = ipaddress+"login.php";

    public static String URL_LOGIN1 = ipaddress+"loginreg.php";
    // Server user register url
    public static String URL_REGISTER = ipaddress+"register.php";

    public static String URL_DREGISTER = ipaddress+"register1.php";

    public static String SERVER_URL = ipaddress+"upload.php";

    public static String UPLOADMYSQL_URL = ipaddress+"uploadmysql.php";

    public static String GET_JSON_DATA_HTTP_URL = ipaddress+"ImageJsonData.php";

    public static String GET_IMAGEJSON_HTTP_URL = ipaddress+"ImageJsonData1.php";

    public static String GET_JSON_DATA_HTTP_URL1 = ipaddress+"homejson.php";

    public static String GET_JSON_DATA_HTTP_URL2 = ipaddress+"issuehistory.php?useremail=";

    public static String GET_JSON_DATA_HTTP_URL3 = ipaddress+"viewdept.php?dept=";

    public  static  String PATH=ipaddress+"uploads/";

    public static String GET_MEMBERVIEW_DATA =ipaddress+"memberview.php?deptmail=";

    public static String GET_ISSUE_DATA = ipaddress+"issuedetails.php";

    public static String UPDATE_MEMBER = ipaddress+"updatedeptmembers.php";

    public static String PASS_RESET = ipaddress+"resetpassword.php";

    public static String EDIT_ISSUE = ipaddress+"editissue.php";

    public static String DELETE_ISSUE=ipaddress+"deleteissue.php";

    public static String GET_DESIG=ipaddress+"designationdm.php";

   public static String GET_DEPTNAME=ipaddress+"deptspinner.php";

 public static String REPLY=ipaddress+"replyview.php?issueid=";

 public static String ADDREPLY=ipaddress+"addreply.php";

 public static String PHONECHECK=ipaddress+"phonecheck.php";


}


