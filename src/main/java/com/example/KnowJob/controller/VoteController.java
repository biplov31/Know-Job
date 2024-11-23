package com.example.KnowJob.controller;

import com.example.KnowJob.dto.VoteRequestDto;
import com.example.KnowJob.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/vote")
public class VoteController {

    private final VoteService voteService;

    @PostMapping
    public void addVote(VoteRequestDto voteRequestDto) {
        voteService.addVote(voteRequestDto);
    }

}
