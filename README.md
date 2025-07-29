GitHub Repositories API

A simple Spring Boot 3.5 + Java 21 application that exposes a REST endpoint to fetch all public non-fork repositories of a given GitHub user along with their branches and last commit SHAs.
Features

    Lists all GitHub repositories for a user, excluding forks.

    For each repository, shows:

        Repository name

        Owner login

        Branches with their name and last commit SHA

    Returns HTTP 404 with JSON message if user does not exist.

    No pagination.

    Minimal dependencies, no WebFlux.

    One integration test covering the happy path.

Requirements

    Java 21

    Spring Boot 3.5

    Internet connection (to query GitHub API)

How to run

    Clone the repo:
    git clone https://github.com/zin-ton/testTask.git
    cd testTask

Build and run the app:

    ./gradlew bootRun

Access the endpoint:

    GET http://localhost:8080/github/{username}/repositories

API Usage

Request

    GET /github/{username}/repositories

Replace {username} with the GitHub username to query.

    Response (200 OK)

    [
      {
        "repositoryName": "repo1",
        "ownerLogin": "username",
        "branches": [
        {
          "name": "main",
          "lastCommitSha": "abc123..."
        },
        ...
        ]
    },
    ...
    ]

    Response (404 Not Found)

    {
      "status": 404,
      "message": "GitHub user not found"
    }

Testing

Run integration tests with:

    ./gradlew test

Notes

    Uses GitHub REST API v3 (https://developer.github.com/v3).

    No authentication implemented — public data only.

    No pagination support (all repositories returned at once).

    Designed for simplicity and clarity per assignment instructions.
