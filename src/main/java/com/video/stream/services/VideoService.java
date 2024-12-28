package com.video.stream.services;

import com.video.stream.Video;
import com.video.stream.errors.BadRequest;
import com.video.stream.repository.VideoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
@AllArgsConstructor
@Service
public class VideoService {
    VideoRepository videoRepository;
    public Video findVideoById (String id) {
        var video =  videoRepository.findById(id);
        if (video.isEmpty()) {
            throw new BadRequest("Sorry this tiltle can not be found at the moment");
        }
        return video.get();
    }
}
