package com.muyun.admin.service;

import com.muyun.admin.exception.ArgumentNotValidException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.executable.ExecutableValidator;
import javax.validation.metadata.BeanDescriptor;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author muyun
 * @date 2020/6/13
 */
@Service
@RequiredArgsConstructor
public class ValidatorService {

    private final Validator validator;

    private <T> void verify(Set<ConstraintViolation<T>> violations) {
        if (!violations.isEmpty()) {
            List<String> messages = violations.stream()
                    .map(ConstraintViolation::getMessage).collect(Collectors.toList());
            throw new ArgumentNotValidException(messages);
        }
    }

    public <T> void validate(T object, Class<?>... groups) {
        verify(validator.validate(object, groups));
    }

    public <T> void validateProperty(T object, String propertyName, Class<?>... groups) {
        verify(validator.validateProperty(object, propertyName, groups));
    }

    public <T> void validateValue(Class<T> beanType, String propertyName, Object value, Class<?>... groups) {
        verify(validator.validateValue(beanType, propertyName, value, groups));
    }

    public BeanDescriptor getConstraintsForClass(Class<?> clazz) {
        return validator.getConstraintsForClass(clazz);
    }

    public <T> T unwrap(Class<T> type) {
        return validator.unwrap(type);
    }

    public ExecutableValidator forExecutables() {
        return validator.forExecutables();
    }
}
