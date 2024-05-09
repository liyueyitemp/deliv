package com.sky.annotation;

import com.sky.enumeration.OperationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//this autofill is to avoid setting variables such as updatetime and createtime for multiple charts in multiple locations ...
//this is to define the annotation

//@Target is to make sure where the autofill is targeted
//METHOD means this only apply to methods
@Target(ElementType.METHOD)

//need to research this one .... standard practice for now
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoFill {
    //mark what kind of method the autofill is used
    OperationType value();

}
