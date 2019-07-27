package com.lin.cms.demo.sleeve.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SpecKeyAndValue {

    private Long keyId;

    private Long valueId;

    private String specValue;

    private String specKey;
}
