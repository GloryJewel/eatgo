package com.gloryjewel.eatgo.interfaces;

import com.gloryjewel.eatgo.application.ReviewService;
import com.gloryjewel.eatgo.domain.Review;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/restaurants/{id}/reviews")
    public ResponseEntity<?> create(
                                    Authentication authentication,
                                    @PathVariable("id") Long id,
                                    @Valid @RequestBody Review resource) throws URISyntaxException {

        Claims claims = null;

        if(authentication != null)
            claims = (Claims) authentication.getPrincipal();

        Review review = reviewService.addReview(id,
                claims == null? resource.getName():claims.get("name",String.class),
                                                resource.getScore(),
                                                resource.getDescription());

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("/restaurants/")
                .append(id)
                .append("/")
                .append("reviews")
                .append("/")
                .append(review.getId());

        URI location = new URI(stringBuilder.toString());
        return ResponseEntity.created(location).body("{}");

    }
}