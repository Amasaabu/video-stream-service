package com.video.stream.utils;


import com.video.stream.errors.BadRequest;
import com.video.stream.models.HttpResponses.AuthResponse;
import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class VerifyToken {
    HttpRequests httpRequests;
    Environment environment;

    public AuthResponse getProfileFroToken (String headerToken) throws Exception {
        //possibly add bearer at this point.
        //do httpRequestWithToken
        try {
            return httpRequests.sendHttpWithToken(environment.getProperty("host.auth") + "/api/user/profile", headerToken).getBody();


        } catch (Exception e) {
            System.out.println("*******");
//            System.out.println(e);
            throw new BadRequest("Unable to validate token");
        }

    }

}
