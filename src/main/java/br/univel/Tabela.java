package br.univel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//Informa que a anota��o ser� lida em tempo de execu��o
@Retention(RetentionPolicy.RUNTIME)//
public @interface Tabela {
	String value() default "_notset";
}
