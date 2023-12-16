package com.example.user.service.dto.elastic;

import com.example.user.service.dto.EsHotelInfo;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class HitsArrayDTO {

    private String _index;
    private String _type;
    private String _id;
    private Double _score;
    private EsHotelInfo _source;

}