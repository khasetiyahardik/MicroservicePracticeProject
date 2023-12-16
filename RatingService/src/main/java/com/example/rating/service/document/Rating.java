package com.example.rating.service.document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ratings")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Rating {
    @Id
    private String ratingId;
    private Long userId;
    private Long hotelId;
    private Integer rating;
    private String feedback;

}
