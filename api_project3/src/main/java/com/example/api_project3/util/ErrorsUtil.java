package com.example.api_project3.util;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

public class ErrorsUtil {
    public static void returnErrorsToClient(BindingResult result) {
        StringBuilder errorsMsg = new StringBuilder();

        List<FieldError> errors = result.getFieldErrors();
        for (FieldError error : errors) {
            errorsMsg.append(error.getField())
                    .append(" - ").append(error.getDefaultMessage()==null?"":error.getDefaultMessage()).append(";");
        }
        throw new MeasurementException(errorsMsg.toString());
    }
}
