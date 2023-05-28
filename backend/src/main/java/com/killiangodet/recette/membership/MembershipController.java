package com.killiangodet.recette.membership;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/membership")
@Validated
@CrossOrigin(origins = {"${app.api.settings.cross-origin.url}"})
public class MembershipController {

}
