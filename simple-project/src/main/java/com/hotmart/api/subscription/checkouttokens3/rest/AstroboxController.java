package com.hotmart.api.subscription.checkouttokens3.rest;

import com.hotmart.api.subscription.checkouttokens3.feign.AstroboxResponse;
import com.hotmart.api.subscription.checkouttokens3.service.AstroboxService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/astrobox")
public class AstroboxController {
    
    private final AstroboxService astroboxService;
    
    public AstroboxController(AstroboxService astroboxService) {
        this.astroboxService = astroboxService;
    }
    
    @GetMapping("/getCheckoutLoadExample")
    public AstroboxResponse executeRequest() {
        return astroboxService.getCheckoutLoadExample();
    }
}
