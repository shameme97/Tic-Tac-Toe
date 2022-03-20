<a name="go-to-top"></a>
# Tic-Tac-Toe

A full-stack REST API application for a game of tic tac toe with backend working with Spring Boot and frontend with Vue.js and MongoDB NoSQL as its database. This app has been integrated with Swagger and tested using JUnit. The project has been hosted on Heroku at the following link: https://tictactoe-shameme.herokuapp.com/


Table of Contents
=================

* [About the project](#about-the-project)
* [Features](#features)
* [Usage](#usage)
  * [REST Service Endpoints](#rest-service-endpoints)
  * [UI](#ui)
* [Structure](#structure)
  * [Directory](#directory)
    * [Backend](#backend)
    * [Frontend](#frontend)
  * [Dependencies](#dependencies)
    * [Backend Dependencies](#backend-dependencies)
    * [Frontend Dependencies](#frontend-dependencies)
* [TechStack](#techstack)

<a name="about-the-project"></a>
## About the project

This version of Tic-Tac-Toe represents a Tic-Tac-Toe tournament-style game with multiple matches
between same two players. The matches are set with an NxN sized
board where the users can pick any value where 3 <= N <= 12. The **Reset Score** button needs to be clicked
to start a new tournament.
The UI allows the users to make moves by clicking on the board.
Afterwards, by clicking on the **Submit** button, the moves are sent as commands
from the frontend to the backend for evaluation as a single chunk
using the POST method. The frontend then displays the result
(Draw / CIRCLE Wins! / CROSS Wins!) based on
the backend api call response. If the commands sent are not
enough to determine the
output of the match, the frontend then displays
'Match In Progress!'. The rules of the game is still the same.
As in a traditional 3x3 game, the users must match
three of the same signs (circle/row).

In this game, playing in an NxN board where N>3 decreases
the probability of draws as the previous winning rule still
applies. If both players are to play optimally, whoever moves
first should mark in any square except those at the border
of the board, followed by a move adjacent to the first move
but not opposite of opponent move.
Even if second player plays optimally, first player should
have a remaining adjacent square which would ensure their win.
The following image shows one instance of such case. The number indicates the i-th move.
<img width="382" alt="image" src="https://user-images.githubusercontent.com/61234823/159158262-a5749a39-8413-4c97-b6a9-d0720533fcfa.png">

<a name="features"></a>
## Features

Some features have been added to the game to make it more user-friendly.

* Users can change their player name to their preference.
  ![image](https://user-images.githubusercontent.com/61234823/159158444-16112dae-b1b8-4d5e-9c07-24f72e222c7b.png)

* After a winner is declared, the winning move is highlighted,
  and any extra moves submitted after that are removed from the
  board.
  ![image](https://user-images.githubusercontent.com/61234823/159158379-bebb6e67-077a-45fc-917d-da2b273662cb.png)

* When clicked on "Game Stats" button, a new tab is opened which
  shows some details and statistics of the previous games.
  ![image](https://user-images.githubusercontent.com/61234823/159158494-414a59f7-1ca7-48fd-b702-125cc21f2ac6.png)


<a name="usage"></a>
## Usage

<a name="rest-service-endpoints"></a>
- REST Service Endpoints:

  + **POST**: <https://tictactoe-shameme-backend.herokuapp.com/{size}/submitMoves/{inProgress}> :
    Submit moves for evaluating result. Returns the winner / match status.
    Sample JSON for this request:
    ```
    [
    "CROSS 2 0",
    "CIRCLE 0 0",
    "CROSS 0 1",
    "CIRCLE 0 2",
    "CROSS 1 0",
    "CIRCLE 1 1",
    "CROSS 1 2",
    "CIRCLE 2 1",
    "CROSS 2 2"
    ]
    ```
  + **POST**: <https://tictactoe-shameme-backend.herokuapp.com/setPlayerNames/{player1}/{player2}> :
    Set player names given input by users.
  + **GET**: <https://tictactoe-shameme-backend.herokuapp.com/results> :
    Returns the score of all matches so far.
  + **POST**: <https://tictactoe-shameme-backend.herokuapp.com/reset> :
    Resets the gaming session.
  + **GET**: <https://tictactoe-shameme-backend.herokuapp.com/winningMoves> :
    Returns the matched / winning move.
  + **GET**: <https://tictactoe-shameme-backend.herokuapp.com/gameDetails> :
    Returns the details of all previous games.
  + **GET**: <https://tictactoe-shameme-backend.herokuapp.com/winStats-based-on-turn> :
    Returns the percentage of winner based on their initial turn.
  + **GET**: <https://tictactoe-shameme-backend.herokuapp.com/winStats> :
    Returns the percentage of winner based on all matches played so far.
  + **GET**: <https://tictactoe-shameme-backend.herokuapp.com/lastFirstTurn> :
    Returns player who made first move in previous match.
  + **GET**: <https://tictactoe-shameme-backend.herokuapp.com/crossName> :
    Returns the name set by player1.
  + **GET**: <https://tictactoe-shameme-backend.herokuapp.com/circleName> :
    Returns the name set by player2.
  + **GET**: <https://tictactoe-shameme-backend.herokuapp.com/numberOfMovesMade> :
    Returns the number of moves taken to win the match.

<a name="ui"></a>
- UI:
  - Swagger is integrated and can be access for interacting with API at
    : <https://tictactoe-shameme-backend.herokuapp.com/swagger-ui.html>
    ![image](https://user-images.githubusercontent.com/61234823/159158520-ff532baa-fcaf-418c-945a-4fbc88be3455.png)
  - Database at mongoDB compass :
    ![image](https://user-images.githubusercontent.com/61234823/159158580-722b7746-a970-4208-b560-b06eabbe6e83.png)

<a name="structure"></a>
## Structure

<a name="directory"></a>
### Directory

<a name="backend"></a>
<details>
<summary>Backend (Click to expand!)</summary>

```
.
├── src/
│   ├── main/
│   │   ├── java/com/example/TicTacToe/
│   │   │   ├── Config/
│   │   │   │   ├── MongoDBConfig.java
│   │   │   │   └── SwaggerConfig.java
│   │   │   ├── Controller/
│   │   │   │   └── GameController.java
│   │   │   ├── Model/
│   │   │   │   ├── Board.java
│   │   │   ├── Repository/
│   │   │   │   └── GameRepository.java
│   │   │   ├── Service/
│   │   │   │   ├── GameService.java
│   │   │   │   └── GameServiceImpl.java
│   │   │   └── TicTacToeApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/java/com/example/TicTacToe/
│       ├── GameServiceImplTest.java
│       └── TicTacToeApplicationTests.java
└── pom.xml
```
</details>

<a name="frontend"></a>
<details>
<summary>Frontend (Click to expand!)</summary>

```
.
├── Procfile
├── src/
│   ├── App.vue
│   ├── assets/
│   │   ├── style.css
│   │   ├── bnw2.png
│   │   └── logo.png
│   ├── components/
│   │   ├── game.vue
│   │   └── gameDetails.vue
│   ├── main.js
│   └── router/
│       └── index.js
├── server.js
└── package.json
```
</details>

<a name="dependencies"></a>
### Dependencies

<a name="backend-dependencies"></a>
+ Backend Dependencies in Spring Boot:
  - Spring Data MongoDB
  - Spring Web
  - Spring Boot Starter Test
  - Lombok
  - Java JSON Web Token
  - Jakarta XML Binding API
  - Junit
  - Springfox-swagger

<a name="frontend-dependencies"></a>
+ Frontend dependencies in Vue.js
  - Axios
  - Vue-Router
  - VueApexCharts
  - VueConfetti

<a name="techstack"></a>
## Tech Stack:
- Java
- Spring Boot
- Vue.js
- mongoDB Compass
- Postman
- IntelliJ (Editor)
- Visual Studio Code

[Go to Top](#go-to-top)
