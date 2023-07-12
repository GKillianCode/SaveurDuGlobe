package com.killiangodet.recette.image.model;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"image", "description"})
public class ImageDTO {
    private String image;
    private String description;
}
