package com.lin.cms.demo.bo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class UploadFileBO {
    private Long id;

    private String url;

    private String key;

    private String path;
}
