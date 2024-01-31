package com.sora.ecommerce.annotations;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.lang.annotation.*;

@Target({ ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Valid
@NotNull
public @interface ValidId {
}
