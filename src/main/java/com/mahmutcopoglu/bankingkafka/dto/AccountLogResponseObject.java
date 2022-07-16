package com.mahmutcopoglu.bankingkafka.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountLogResponseObject {
    private String log;
}
