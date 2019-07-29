package com.lin.cms.demo.sleeve.bo;

import com.lin.cms.demo.sleeve.model.Banner;
import com.lin.cms.demo.sleeve.model.BannerItem;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class BannerAndItems {
    private Long id;

    private String name;

    private String description;

    List<BannerItem> items;

    public BannerAndItems(Banner banner, List<BannerItem> items) {
        BeanUtils.copyProperties(banner, this);
        this.setItems(items);
    }
}
