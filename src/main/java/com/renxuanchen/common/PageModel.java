package com.renxuanchen.common;

import lombok.Data;

@Data
public class PageModel {
    private Long page = 1L;
    private Long limit = 10L;
}
