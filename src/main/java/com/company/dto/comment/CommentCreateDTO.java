package com.company.dto.comment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentCreateDTO {

    private String homeId;
    private Integer commentId;
    private String content;

}
