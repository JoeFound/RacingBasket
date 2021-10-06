package com.veracode.research.racingbasket.tunedbasket;


import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;

@RestController
@Scope(WebApplicationContext.SCOPE_APPLICATION)
@RequestMapping("appscopedbasket")
public class BasketControllerScopeApplication {
    private double basketTotal = 0;
    private final double couponValue = 18.93;
    private String validCoupon = "XXX-YYY-ZZZ";

    @GetMapping("enter-coupon")
    @ResponseBody
    public double enterCoupon(@RequestParam String value) {
        basketTotal = 58.93;
        if (value.equals(this.validCoupon)) {
            basketTotal -= couponValue;
        }
        return basketTotal;
    }

    @GetMapping("remove-coupon")
    @ResponseBody
    public double removeCoupon() {
        basketTotal = 58.93;
        return basketTotal;
    }

    @GetMapping("total")
    @ResponseBody
    public double total() {
        basketTotal = 58.93;
        return basketTotal;
    }
}
