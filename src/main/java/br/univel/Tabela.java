package br.univel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//Informa que a anotação será lida em tempo de execução
@Retention(RetentionPolicy.RUNTIME)//
@Target(ElementType.TYPE)
//ElementType.TYPE será uma anotação de classe, interface ou enum
public @interface Tabela {
	String value() default "_notset";
}
