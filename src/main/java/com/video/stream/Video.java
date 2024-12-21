package com.video.stream;

import jakarta.persistence.Id;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.Date;
import java.util.List;

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