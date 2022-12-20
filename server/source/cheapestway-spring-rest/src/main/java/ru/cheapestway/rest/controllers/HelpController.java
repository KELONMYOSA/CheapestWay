package ru.cheapestway.rest.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HelpController {
    @GetMapping
    @ResponseBody
    private String index() {
        return "<h3>Request list:</h3>" +
                "<ul>" +
                    "<li>/cities</li>" +
                    "<li>/pricesondate</li>" +
                        "<ul>" +
                        "<li>departure={code_origin}</li>" +
                        "<li>arrival={code_destination}</li>" +
                        "<li>date={date_yyyy-mm-dd}</li>" +
                        "</ul>" +
                "</ul>";
    }
}
