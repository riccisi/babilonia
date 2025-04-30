package it.riccisi.babilonia.infrastructure.security;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.lang.annotation.*;

/**
 * Annotation per iniettare direttamente, nei controller,
 * l’oggetto User sincronizzato dal JWT corrente.
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@AuthenticationPrincipal(expression = "@users.getCurrent(#this)")
public @interface CurrentUser {
}