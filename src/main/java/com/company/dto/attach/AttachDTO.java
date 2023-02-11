package com.company.dto.attach;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class AttachDTO {

    private String id;
    private String originalName;
    private String extension;
    private Long size;
    private String fileName;
    private LocalDateTime createdDate;
    private String url;

    public AttachDTO(String id, String fileName) {
        this.id = id;
        this.fileName = fileName;
    }
}
