package com.mieker.ifpr.shelfie.config;

import java.util.regex.Pattern;

// TODO:
//  Implementar validação de senha
public class Validation {

    public static boolean emailValidation(String emailAddress) {
//        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9\\+_-]+(\\.[A-Za-z0-9\\+_-]+)*@[^-][A-Za-z0-9\\+-]+(\\.[A-Za-z0-9\\+-]+)*(\\.[A-Za-z]{2,})$";
        String regexPattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";


        return Pattern.compile(regexPattern)
                .matcher(emailAddress)
                .matches();
    }

}
