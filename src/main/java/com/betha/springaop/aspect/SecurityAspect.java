package com.betha.springaop.aspect;

import com.betha.springaop.utils.Secured;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;

@Aspect
@Component
public class SecurityAspect {

    @Pointcut("@annotation(secured)")
    public void verificarAcesso(Secured secured) {}

    @Around(value = ("verificarAcesso(secured)"), argNames = "joinPoint,secured")
    public Object checkarAcesso(ProceedingJoinPoint joinPoint, Secured secured) throws Throwable {
        boolean temRolePermitido = SecurityContextHolder.getContext()
                .getAuthentication()
                .getAuthorities()
                .stream()
                .anyMatch(grantedAuthority ->
                        Arrays.stream(secured.value())
                                .anyMatch(permitido -> permitido.equals(grantedAuthority.toString())));

        if (temRolePermitido) {
            return joinPoint.proceed();
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }
}
