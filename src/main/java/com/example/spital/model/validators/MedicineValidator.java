package com.example.spital.model.validators;

import com.example.spital.model.Medicine;

import java.time.LocalDate;
import java.sql.Date;

public class MedicineValidator implements Validator<Medicine> {
    @Override
    public void validate(Medicine entity) throws ValidationException {
        String errors = "";
        if(entity.getName().equals("") || isNotString(entity.getName())){
            errors += "Invalid name ";
        }
        Date localDate = Date.valueOf(LocalDate.now());
        if(entity.getExpirationDate().before(localDate)){
            errors += "This medicine is expired ";
        }
        if(entity.getQuantity() < 0){
            errors += "Quantity can't be negative ";
        }
        if(!errors.equals("")) throw new ValidationException(errors);
    }

    private boolean isNotString(String name) {
        for(int i = 0;i < name.length();i++){
            char c = name.charAt(i);
            if(c == ' ')
                return false;
            if(!(c >= 'A' && c <= 'Z') && !(c >= 'a' && c <= 'z'))
                return true;

        }
        return false;
    }
}
