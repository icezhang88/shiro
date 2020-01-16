package com.shiro;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.security.SecureRandom;
import java.util.Arrays;

@SpringBootApplication
public class ShiroApplication {

    public static void main(String[] args) {


        SpringApplication.run(ShiroApplication.class, args);
    }

}
