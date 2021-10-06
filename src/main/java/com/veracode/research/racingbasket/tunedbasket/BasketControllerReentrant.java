package com.veracode.research.racingbasket.tunedbasket;


import org.springframework.web.bind.annotation.*;

import java.util.concurrent.locks.ReentrantLock;

@RestController
@RequestMapping("reentrantbasket")
public class BasketControllerReentrant {
    private double basketTotal = 0;
    private final double couponValue = 18.93;
    private String validCoupon = "XXX-YYY-ZZZ";

    private ReentrantLock basketTotalMutex = new ReentrantLock();


    @GetMapping("enter-coupon")
    @ResponseBody
    public double enterCoupon(@RequestParam String value) {
        try {
            basketTotalMutex.lock();

            basketTotal = 58.93;
            if (value.equals(this.validCoupon)) {
                basketTotal -= couponValue;
            }
            return basketTotal;
        } finally {
            basketTotalMutex.unlock();
        }
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
