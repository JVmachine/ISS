package com.example.spital.model.validators;

import com.example.spital.model.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidator implements Validator<User> {
    @Override
    public void validate(User entity) throws ValidationException {
        String errors = "";
        if(entity.getFirstName().equals("") || isNotString(entity.getFirstName())){
            errors += "Invalid first name ";
        }
        if(entity.getLastName().equals("") || isNotString(entity.getLastName())){
            errors += "Invalid last name ";
        }
        if(entity.getAddress().equals("")){
            errors += "Invalid address ";
        }
        if(entity.getPhoneNumber().length() != 10 || !isNotNumber(entity.getPhoneNumber())){
            errors += "Invalid phone number ";
        }
        if(entity.getUsername().equals("")){
            errors += "Invalid username ";
        }
        if(entity.getPassword().equals("")){
            errors += "Invalid password ";
        }
        if(!errors.equals(""))throw new ValidationException(errors);

    }

    private boolean isNotString(String name) {
        for(int i = 0;i < name.length();i++){
            char c = name.charAt(i);
            if(!(c >= 'A' && c <= 'Z') && !(c >= 'a' && c <= 'z'))
                return true;
        }
        return false;
    }
    private boolean isNotNumber(String phoneNumber){
        String regex = "[0-9]+";
        Pattern pattern = Pattern.compile(regex);
        //match between String phoneNumber and regular expression
        Matcher m = pattern.matcher(phoneNumber);
        //return is the string matches the regex
        return m.matches();
    }
}
