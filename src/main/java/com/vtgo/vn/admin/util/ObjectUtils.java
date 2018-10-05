/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vtgo.vn.admin.util;

import java.lang.reflect.Field;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;

/**
 *
 * @author HP
 */
public class ObjectUtils {

    private static final Logger logger = Logger.getLogger(ObjectUtils.class
            .getSimpleName());

    private static final ModelMapper MAPPER = new ModelMapper();

    public static Map<String, Object> getOriginObject(Object object, DateFormat dateFormat) {
        Map<String, Object> map = new HashMap<>();
        if (dateFormat == null) {
            dateFormat = new SimpleDateFormat("dd/MM/yyyy  HH:mm:ss");
        }
        try {
            for (Field field : object.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                Object value = field.get(object);
                if (value != null && !value.toString().isEmpty()) {
                    if (value.getClass() == field.getType()) {
                        map.put(field.getName(), value);
                    } else {
                        if (field.getType() == Long.class) {
                            map.put(field.getName(), Long.valueOf(value.toString()));
                        } else if (field.getType() == Double.class) {
                            map.put(field.getName(), Double.valueOf(value.toString()));
                        } else if (field.getType() == String.class) {
                            map.put(field.getName(), value.toString());
                        } else if (field.getType() == Integer.class) {
                            map.put(field.getName(), Integer.valueOf(value.toString()));
                        } else if (field.getType() == BigInteger.class) {
                            map.put(field.getName(), new BigInteger(value.toString()));
                        } else if (field.getType() == Boolean.class) {
                            map.put(field.getName(), Boolean.valueOf(value.toString()));
                        } else if (field.getType() == Byte.class) {
                            map.put(field.getName(), Byte.valueOf(value.toString()));
                        } else if (field.getType() == Short.class) {
                            map.put(field.getName(), Short.valueOf(value.toString()));
                        } else if (field.getType() == Float.class) {
                            map.put(field.getName(), Float.valueOf(value.toString()));
                        } else if (field.getType() == Date.class) {
                            Date date = dateFormat.parse(value.toString());
                            map.put(field.getName(), date);
                        } else if (field.getType().equals(List.class)) {
                            map.put(field.getName(), value);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return map;
    }

    public static <D> D mappingEntity(Object source, Class<D> destinationType) {
        return MAPPER.map(source, destinationType);
    }

    public static <T> T setObjectPropertyValue(Map<String, Object> map, Class<T> clazz) throws Exception {
        try {
            T object = clazz.newInstance();
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                Object value = map.get(field.getName());
                if (value != null && !value.toString().isEmpty()) {
                    if (field.getType().isInstance(value)) {
                        field.set(object, value);
                    } else {
                        if (field.getType() == Long.class) {
                            field.set(object, NumberFormat.getInstance().parse(value.toString()).longValue());
                        } else if (field.getType() == Double.class) {
                            field.set(object, NumberFormat.getInstance().parse(value.toString()).doubleValue());
                        } else if (field.getType() == String.class) {
                            field.set(object, value.toString());
                        } else if (field.getType() == Integer.class) {
                            field.set(object, NumberFormat.getInstance().parse(value.toString()).intValue());
                        } else if (field.getType() == BigInteger.class) {
                            field.set(object, NumberFormat.getInstance().parse(value.toString()).intValue());
                        } else if (field.getType() == Boolean.class) {
                            field.set(object, Boolean.valueOf(value.toString()));
                        } else if (field.getType() == byte[].class) {
                            field.set(object, value.toString().getBytes());
                        } else if (field.getType() == Short.class) {
                            field.set(object, Short.valueOf(value.toString()));
                        } else if (field.getType() == Float.class) {
                            field.set(object, NumberFormat.getInstance().parse(value.toString()).floatValue());
                        } else if (field.getType() == Date.class) {
                            if (value instanceof Long) {
                                field.set(object, new Date((Long) value));
                            } else {
                                Date date = dateFormat.parse(value.toString());
                                field.set(object, date);
                            }
                        } else if (field.getType().equals(List.class) && value instanceof List) {
                            field.set(object, value);
                        }
                    }
                }
            }
            return object;
        } catch (InstantiationException | IllegalAccessException | ParseException ex) {
            logger.error(ex.getMessage(), ex);
        }
          return null;
    }

}
