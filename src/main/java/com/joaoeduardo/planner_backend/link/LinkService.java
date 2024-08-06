package com.joaoeduardo.planner_backend.link;

import com.joaoeduardo.planner_backend.activity.*;
import com.joaoeduardo.planner_backend.trip.Trip;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LinkService {

    private final LinkRepository linkRepository;

    public LinkResponse registerLink(LinkRequestPayload payload, Trip trip){

        Link newLink = Link.builder()
                .title(payload.title())
                .url(payload.url())
                .trip(trip).build();

        linkRepository.save(newLink);

        return new LinkResponse(newLink.getId());

    }

    public List<LinkData> getAllLinks(UUID tripId) {
        List<Link> links = linkRepository.findByTripId(tripId);

        List<LinkData> linksData = links.stream().map(LinkData::new).toList();

        return linksData;
    }
}
