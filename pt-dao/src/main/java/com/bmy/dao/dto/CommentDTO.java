package com.bmy.dao.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class CommentDTO {

    private Long dynId;

    private Long toUid;

    private String content;

}
