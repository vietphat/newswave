package com.vietphat.newswave.service;

public interface UniqueFieldsService {

    /**
     * Checks whether or not a given value exists for a given field
     *
     * @param object The value to check for
     * @param fieldNames The name of the field for which to check if the value exists
     * @return True if the value exists for the field; false otherwise
     * @throws UnsupportedOperationException
     */
    boolean fieldValuesExist(Object object, String[] fieldNames) throws UnsupportedOperationException, NoSuchFieldException, IllegalAccessException;
}
