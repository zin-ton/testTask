package com.example.demo.controller;

import com.example.demo.dto.RepoDto;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.ErrorResponse;
import com.example.demo.service.GithubService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/github")
public class GithubController {
    private final GithubService githubService;

    public GithubController(GithubService githubService) {
        this.githubService = githubService;
    }

    @GetMapping("/{username}/repos")
    public ResponseEntity<?> getRepos(@PathVariable String username) {
        try {
            List<RepoDto> repos = githubService.getRepos(username);
            return ResponseEntity.ok(repos);
        }
        catch (UserNotFoundException e) {
            return ResponseEntity.status(404).body(
                    new ErrorResponse(404, e.getMessage())
            );
        }


    }
}
