package com.zalizniak.websocketwithredis.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WebSocketInboundMsg {
    private long x;
    private long y;
}
