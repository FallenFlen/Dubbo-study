package com.flz.mydubbo.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Getter
@Setter
public class CommonRpcResponse<T> {
    private Integer code;
    private String traceId;
    private T data;
}
