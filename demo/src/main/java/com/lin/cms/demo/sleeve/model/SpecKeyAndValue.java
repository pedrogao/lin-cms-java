package com.lin.cms.demo.sleeve.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SpecKeyAndValue {

    @JSONField(name = "key_id")
    private Long keyId;

    @JSONField(name = "value_id")
    private Long valueId;

    private String value;

    private String key;
}
