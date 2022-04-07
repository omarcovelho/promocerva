package br.com.promocerva.users.annotations

import org.springframework.test.context.ActiveProfiles
import java.lang.annotation.*
import java.lang.annotation.Retention
import java.lang.annotation.Target

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ActiveProfiles("tc")
annotation class RealInfra()
