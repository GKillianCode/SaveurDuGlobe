package com.killiangodet.recette.gender;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/gender")
@Validated
@CrossOrigin(origins = {"${app.api.settings.cross-origin.url}"})
public class GenderController {
}
