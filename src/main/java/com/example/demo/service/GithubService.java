package com.example.demo.service;

import com.example.demo.dto.BranchDto;
import com.example.demo.dto.RepoDto;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.GithubBranchResponse;
import com.example.demo.model.GithubRepoResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class GithubService {
    private final RestTemplate restTemplate;

    public GithubService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<RepoDto> getRepos(String username) {
        String url = "https://api.github.com/users/" + username + "/repos";
        GithubRepoResponse[] repos;
        try {
            repos = restTemplate.getForObject(url, GithubRepoResponse[].class);

        }
        catch (HttpClientErrorException.NotFound e){
            throw new UserNotFoundException("GitHub user not found");
        }

        if(repos == null) return List.of();

        return Arrays.stream(repos)
                .filter(repo -> !repo.fork())
                .map(repo -> {
                    String branchesUrl = "https://api.github.com/repos/" + username + "/" + repo.name() + "/branches";
                    GithubBranchResponse[] branches = restTemplate.getForObject(branchesUrl, GithubBranchResponse[].class);

                    List<BranchDto> branchDtos = branches != null
                            ? Arrays.stream(branches)
                            .map(branch -> new BranchDto(branch.name(), branch.commit().sha()))
                            .toList()
                            : List.of();
                    return new RepoDto(repo.name(), repo.owner().login(), branchDtos);
                })
                .toList();
    }
}
