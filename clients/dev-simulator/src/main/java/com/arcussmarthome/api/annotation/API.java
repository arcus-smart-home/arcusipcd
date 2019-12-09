package com.arcussmarthome.api.annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to mark a class as an API package.
 * Public methods exposed by the class will appear as 
 * RPC methods on the API package.
 * 
 * Value is the fully qualified package name.  Subpackages
 * are separated by '/'
 * 
 * @author sperry
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface API {
    String value();
}