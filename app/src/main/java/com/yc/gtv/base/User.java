package com.yc.gtv.base;

import org.json.JSONObject;

public class User {

    private static class LazyHolder {
        private static final User INSTANCE = new User();
    }
    private User() {
    }
    public static final User getInstance() {
        return User.LazyHolder.INSTANCE;
    }


    private boolean login = false;
    private JSONObject userObj;
    private String userId;
    private String sessionId;
    private JSONObject userInfo;

    public JSONObject getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(JSONObject userInfo) {
        this.userInfo = userInfo;
    }

    public void setUserObj(JSONObject userObj) {
        this.userObj = userObj;
    }

    public JSONObject getUserObj() {
        return userObj;
    }

    public void setLogin(boolean login) {
        this.login = login;
    }

    public boolean isLogin() {
        return login;
    }

    public void setUserId(String userId) {
        if (userObj != null) {
            this.userId = userObj.optString("userId");
        }
    }

    public String getUserId() {
        if (userObj == null) {
            return null;
        }
        userId = userObj.optString("userId");
        return userId;
    }

    public void setSessionId(String sessionId) {
        if (userObj != null) {
            this.sessionId = userObj.optString("token");
        }
    }

    public String getSessionId() {
        if (userObj == null) {
            return "";
        }
        sessionId = userObj.optString("token");
        return sessionId;
    }
}
