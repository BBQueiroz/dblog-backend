package com.dovisen.dblog.controller;

import com.dovisen.dblog.domain.Journey.JourneyDTO;
import com.dovisen.dblog.domain.Journey.JourneyNode;
import com.dovisen.dblog.domain.Journey.JourneyNodeService;
import com.dovisen.dblog.domain.post.Post;
import com.dovisen.dblog.domain.post.PostDTO;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journey")
public class JourneyController {

    private final JourneyNodeService journeyNodeService;

    public JourneyController(JourneyNodeService journeyNodeService){
        this.journeyNodeService = journeyNodeService;
    }

    @GetMapping
    public ResponseEntity<List<JourneyNode>> getAllJourneyRoots(){
        List<JourneyNode> journeys = journeyNodeService.getAllJourneyRoots();
        return ResponseEntity.status(HttpStatus.OK).body(journeys);
    }

    @GetMapping("/{rootId}")
    public ResponseEntity<List<JourneyNode>> getAllJourney(@PathVariable(value = "rootId") UUID id){
        List<JourneyNode> journeys = journeyNodeService.getOneJourney(id);
        return ResponseEntity.status(HttpStatus.OK).body(journeys);
    }

    @GetMapping("/{rootId}/{id}")
    public ResponseEntity<JourneyNode> getJourneyNode(@PathVariable(value = "rootId") UUID rootId, @PathVariable(value = "id") UUID id){
        JourneyNode journeyNode = journeyNodeService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(journeyNode);
    }

    @PostMapping
    public ResponseEntity<Object> saveJourneyNode(@RequestBody @Valid JourneyDTO journeyDTO){
        JourneyNode savedJourney;
        try{
            savedJourney = journeyNodeService.save(journeyDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{root}/{id}")
                .buildAndExpand(savedJourney.getRoot().getId(), savedJourney.getId())
                .toUri();
        return ResponseEntity.status(HttpStatus.CREATED).body("201 Created");
    }

}
