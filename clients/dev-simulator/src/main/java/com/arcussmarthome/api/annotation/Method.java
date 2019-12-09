package com.arcussmarthome.api.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Denotes a public method on an API package marked class
 * that should be exposed as a remote procedure call.
 * 
 * Value is the name of the exposed function.  If null,
 * the name of the method will be used.
 * 
 * @author sperry
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Method {
	String value();    
}