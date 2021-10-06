package com.veracode.research.racingbasket.basket;


import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("basket")
public class BasketController {
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
