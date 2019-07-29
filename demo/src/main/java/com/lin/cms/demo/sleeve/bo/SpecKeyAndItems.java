package com.lin.cms.demo.sleeve.bo;

import com.lin.cms.demo.sleeve.model.SpecKey;
import com.lin.cms.demo.sleeve.model.SpecValue;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class SpecKeyAndItems {
    private Long id;

    private String name;

    private String unit;

    private Integer standard;

    private String description;

    private List<SpecValue> items;

    public SpecKeyAndItems(SpecKey specKey, List<SpecValue> items) {
        BeanUtils.copyProperties(specKey, this);
        this.setItems(items);
    }
}
