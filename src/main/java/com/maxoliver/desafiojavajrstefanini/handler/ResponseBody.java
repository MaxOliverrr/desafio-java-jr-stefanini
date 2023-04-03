package com.maxoliver.desafiojavajrstefanini.handler;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseBody {
    private String title;
    private String detail;
    private String type;
    private int status;

}
