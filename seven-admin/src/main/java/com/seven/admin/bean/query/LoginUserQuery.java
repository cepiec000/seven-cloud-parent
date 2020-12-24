package com.seven.admin.bean.query;

import lombok.Data;

@Data
public class LoginUserQuery {
    private String userName;
    private String password;
    private boolean remember;
}
