package br.univel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//Informa que a anota��o ser� lida em tempo de execu��o
@Retention(RetentionPolicy.RUNTIME)//
//Pode ser aplicado em um atributo ou propriedade
@Target(ElementType.FIELD)
public @interface Coluna {
	String nome() default "";
	String tipo() default "";
	boolean pk() default false;
}
