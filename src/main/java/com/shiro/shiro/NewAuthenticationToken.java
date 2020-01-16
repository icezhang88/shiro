package com.shiro.shiro;

import org.apache.shiro.authc.AuthenticationToken;


//去除所有警告
@SuppressWarnings(value= {"all"})
public class NewAuthenticationToken implements AuthenticationToken {



	// 密钥
    private String token;

    public NewAuthenticationToken(String token) {
        this.token = token;
    }

    @Override
    public Object getCredentials() {
    	//凭证保存token信息
        return token;
    }

	@Override
	public Object getPrincipal() {
		//主体就保存用户信息
		return token;
	}
}
