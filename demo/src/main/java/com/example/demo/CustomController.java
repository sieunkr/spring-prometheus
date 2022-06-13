package com.example.demo;

import io.micrometer.core.annotation.Timed;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/custom")
public class CustomController {

    @GetMapping(value = "/prometheus", produces="text/plain;charset=utf-8")
    public String test() {

        var help = "help!!";
        var name = "test";
        var type = "gauge";
        var value = 27.0;

        String result = "# HELP " + help + "\n";
        result += "# TYPE " + name + " " + type + "\n";
        result += name + " " + value + "\n";
        return result;
    }


    @GetMapping("/time")
    @Timed(value = "eddy.time", description = "Time taken to return...", percentiles = {0.5, 0.90})
    public String greeting() {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "test";
    }
}