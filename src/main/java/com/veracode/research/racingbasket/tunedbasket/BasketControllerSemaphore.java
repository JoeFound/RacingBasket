package com.veracode.research.racingbasket.tunedbasket;


import org.springframework.web.bind.annotation.*;

import java.util.concurrent.Semaphore;

@RestController
@RequestMapping("semaphorebasket")
public class BasketControllerSemaphore {
    private double basketTotal = 0;
    private final double couponValue = 18.93;
    private String validCoupon = "XXX-YYY-ZZZ";

    private Semaphore basketTotalSemaphore = new Semaphore(1);

    @GetMapping("enter-coupon")
    @ResponseBody
    public double enterCoupon(@RequestParam String value) {
        try {
            basketTotalSemaphore.acquire();

            basketTotal = 58.93;
            if (value.equals(this.validCoupon)) {
                basketTotal -= couponValue;
            }
            return basketTotal;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return 58.93;
        } finally {
            basketTotalSemaphore.release();
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
