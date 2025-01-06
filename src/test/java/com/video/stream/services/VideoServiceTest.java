package com.video.stream.services;

import com.video.stream.Video;
import com.video.stream.repository.VideoRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class VideoServiceTest {
    @Mock
    VideoRepository videoRepository;
    @InjectMocks
    VideoService videoService;
    @Test
    public void findVideoByIdTest() {
        var vid = new Video();
        vid.setId("1");
        when(videoRepository.findById("1")).thenReturn(Optional.of(vid));
        var videoresp = videoService.findVideoById("1");

        verify(videoRepository, times(1)).findById("1");
        assertEquals("", videoresp.getId(), vid.getId());
    }
}
