package com.zalizniak.websocketwithredis.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WebSocketOutboundMsg {

    private long requestedX;
    private long requestedY;
    private String song;
}
