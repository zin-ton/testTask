package com.example.demo.dto;

import java.util.List;

public record RepoDto(String repositoryName, String onwerUsername, List<BranchDto> branches) {
}
