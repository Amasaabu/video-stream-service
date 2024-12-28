package com.video.stream;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document("Video")
@Getter
@Setter
@NoArgsConstructor
public class Video {
    @Id
    private String id;
    @Indexed(unique = true)
    private String title;
    private String loction;
    private String originalFileName;
    private List<String> genres;
    private String releasedAt;
    private int uploadedBy;
    @CreationTimestamp
    private Date timestamp;
    @UpdateTimestamp
    private Date updatedOn;
}