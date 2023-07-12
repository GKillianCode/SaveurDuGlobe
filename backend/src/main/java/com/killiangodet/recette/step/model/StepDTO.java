package com.killiangodet.recette.step.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"orderId", "description"})
public class StepDTO {
    private Integer orderId;
    private String description;
}
