package com.jun.backend.constants;

import com.jun.backend.utils.PathUtils;

public class Constants {
    public static final String CODE_200 = "200";//success
    public static final String CODE_500 = "500";// system error
    public static final String NO_RESULT = "510";//not find result
    public static final String CODE_401 = "401";//not Permissions
    public static final String TOKEN_ERROR = "401";//token invalid
    public static final String CODE_403 = "403";//Refusal to execute
    //file local storage address
    public static final String fileFolderPath = PathUtils.getClassLoadRootPath() + "/file/";
    public static final String avatarFolderPath =  PathUtils.getClassLoadRootPath() + "/avatar/";
}
