package com.example.demo.tests;

import com.example.demo.dto.BranchDto;
import com.example.demo.dto.RepoDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GithubIntegrationTest {

    @LocalServerPort
    private int port;

    private final RestTemplate restTemplate = new RestTemplate();

    @Test
    void testGetRepos(){
        String username = "octocat";
        String url = "http://localhost:" + port + "/api/github/" + username + "/repos";
        ResponseEntity<RepoDto[]> response = restTemplate.getForEntity(url, RepoDto[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        RepoDto[] repos = response.getBody();
        assertThat(repos).isNotNull();
        assertThat(repos.length).isGreaterThan(0);

        for (RepoDto repo : repos) {
            assertThat(repo.repositoryName()).isNotBlank();
            assertThat(repo.onwerUsername()).isEqualToIgnoringCase(username);
            List<BranchDto> branches = repo.branches();
            assertThat(branches).isNotNull();
            assertThat(branches.size()).isGreaterThan(0);
            for (BranchDto branch : branches) {
                assertThat(branch.name()).isNotBlank();
                assertThat(branch.lastCommitSha()).isNotBlank();
            }
        }
    }
}
