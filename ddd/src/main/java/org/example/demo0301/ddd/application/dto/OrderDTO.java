package org.example.demo0301.ddd.application.dto;

import lombok.Data;

@Data
public class OrderDTO {

    private Long id;
    private String itemTitle;
    private String detailAddress;
}
