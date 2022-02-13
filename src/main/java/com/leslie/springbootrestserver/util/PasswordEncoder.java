package com.leslie.springbootrestserver.util;

/*
* This class creates encrypted passwords for testing and DB initialization
* */

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoder {

    public static BCryptPasswordEncoder bCryptPasswordEncoder;

    public static void main(String[] args){
        bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String password = "password2";
        String encodedPassword = bCryptPasswordEncoder.encode(password);

        System.out.println(" password decrypted : " + password);
        System.out.println(" password encrypted : " + encodedPassword);
        System.out.println(" the password is hashed : " + bCryptPasswordEncoder.matches(password,encodedPassword));
    }
}
