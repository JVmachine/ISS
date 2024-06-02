package com.example.spital.model.validators;

import com.example.spital.model.Order;

public class OrderValidator implements Validator<Order>{
    @Override
    public void validate(Order entity) throws ValidationException {
        String errors = "";
        if(entity.getMedicines().size() == 0){
            errors += "Invalid order ";
        }
        if(entity.getStatus().equals("")){
            errors += "Status can't be null";
        }
        if(!errors.equals("")){
            throw new ValidationException(errors);
        }
    }
}
