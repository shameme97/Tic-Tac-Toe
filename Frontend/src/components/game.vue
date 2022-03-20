<template>
  <div>
    <link
      rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"
    />
    <div class="sidenav">
      <h2>Scoreboard</h2>
      <div class="scoreBoard">
        <table>
          <tr>
            <td>{{ player1 }}</td>
            <td>:</td>
            <td>{{ score[0] }}</td>
          </tr>
          <tr>
            <td>{{ player2 }}</td>
            <td>:</td>
            <td>{{ score[1] }}</td>
          </tr>
          <tr>
            <td>DRAW</td>
            <td>:</td>
            <td>{{ score[2] }}</td>
          </tr>
        </table>
      </div>
      <br />
      <button @click="resetScore()">Reset Score</button>
      <br /><br />
      <button @click="showGameStats()">Game Stats</button>
    </div>

    <div class="sidenav2">
      <div class="topdiv">
        <label for="selectBoard">Select board size: </label>
        <select class="selectBoard" name="selectBoard" v-model="size">
          <option v-for="(item, index) in sizeList" v-bind:key="index">
            {{ item }}
          </option>
        </select>
        <br /><br />
        <button v-on:click="submitMoves(size)">Submit</button>
        <br /><br />
        <button v-on:click="rematch()">Rematch</button>
      </div>
    </div>

    <div class="changeName">
      Customize your name!
      <table>
        <tr>
          <td>X</td>
          <td>
            <input
              class="addInput"
              type="text"
              v-model="player1_name"
              placeholder="CROSS"
              onfocus="this.player1_name=''"
            />
          </td>
          <td>
            <button class="saveButton" v-on:click="savePlayer1(player1_name)">
              <i class="fa fa-save"></i>
            </button>
          </td>
        </tr>
        <tr>
          <td>O</td>
          <td>
            <input
              class="addInput"
              type="text"
              v-model="player2_name"
              placeholder="CIRCLE"
              onfocus="this.player2_name=''"
            />
          </td>
          <td>
            <button class="saveButton" v-on:click="savePlayer2(player2_name)">
              <i class="fa fa-save"></i>
            </button>
          </td>
        </tr>
      </table>
    </div>

    <div id="resultMessage" v-bind:class="{ highlighted: gameWon }" class="resultMessage">
      {{ this.message }}
    </div>

    <div id="game-view" v-bind:style="{ width: computedBoard, height: computedBoard }">
      <div id="game-view-info">It is {{ this.currentTurn }}'s turn</div>
      <div
        id="game-view-squares"
        v-bind:style="{ width: computedBoard, height: computedBoard }"
      >
        <div
          v-for="(square, i) in items"
          v-bind:key="i"
          v-on:click="makeMove(i)"
          v-bind:style="{
            width: computedSquare,
            height: computedSquare,
            fontSize: computedMark,
          }"
          v-bind:class="{ highlighted: square.isHighlighted }"
          class="game-view-square"
        >
          {{ square.value }}
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Vue from "vue";
import VueConfetti from "vue-confetti";

Vue.use(VueConfetti);

export default {
  data() {
    return {
      size: 3,
      items: [],
      sizeList: [3, 4, 5, 6, 7, 8, 9, 10, 11, 12],
      currentTurn: "X",
      firstTurn: "X",
      lastFirstTurn: "O",
      inProgress: true,
      gameWon: false,
      message: "",
      beginMessage: "Let's play tic-tac-toe!",
      movesList: [],
      movesMade: 0,
      score: [],

      player1_name: "",
      player2_name: "",
      player1: "CROSS",
      player2: "CIRCLE",

      squareSize: "33.33%",
      boardSize: "560px",
      markSize: "75px",

      herokuUri: "https://tictactoe-shameme-backend.herokuapp.com/",
      localUri: "http://localhost:4023/",
      uriInUse: "",
    };
  },

  computed: {
    computedSquare: function () {
      this.squareSize = 100 / this.size + "%";
      this.setBoard(this.size);
      return this.squareSize;
    },
    computedBoard: function () {
      return this.boardSize;
    },
    computedMark: function () {
      var mark = 300 / this.size;
      this.markSize = mark + "px";
      return this.markSize;
    },
  },

  created: function () {
    this.uriInUse = this.localUri;
    this.createArray();
    this.getScore();
    this.message = this.beginMessage;
    this.getPlayer1Name();
    this.getPlayer2Name();
  },

  methods: {
    createArray() {
      this.items = new Array(this.size * this.size)
        .fill()
        .map((s) => ({ value: "", isHighlighted: false }));
    },

    makeMove(i) {
      if (this.inProgress) {
        var row = Math.floor(i / this.size);
        var col = i % this.size;

        if (this.inProgress && this.items[i].value == "") {
          this.items[i].value = this.currentTurn;
          var string = this.currentTurn == "X" ? "CROSS " : "CIRCLE ";
          string += row + " " + col;
          this.movesList[this.movesMade] = string;
          this.movesMade++;
          this.currentTurn = this.currentTurn == "X" ? "O" : "X";
        }
      }
    },

    submitMoves(size) {
      this.size = size;
      var newGame = this.inProgress;
      let uri = this.uriInUse + size + "/submitMoves/" + newGame;
      this.axios.post(uri, this.movesList).then((response) => {
        if (response.data == "") return; // for empty or multiple submits of same board
        if (response.data == "Match In Progress!") this.message = response.data;
        else {
          if (response.data == "Draw") this.message = "Draw!";
          else {
            if (response.data == "CROSS") this.message = this.player1 + " Wins!";
            else if (response.data == "CIRCLE") this.message = this.player2 + " Wins!";
            this.confettiStart();
            this.gameWon = true;
          }
          this.inProgress = false;
          this.setPlayerNames();
          this.getScore();
          this.getWinningMoves();
        }
      });
    },

    rematch() {
      this.createArray();
      this.inProgress = true;
      this.message = this.beginMessage;
      this.movesList = [];
      this.movesMade = 0;
      this.setFirstTurn();
      this.confettiStop();
      this.gameWon = false;
    },

    resetScore() {
      let uri = this.uriInUse + "reset";
      this.axios.post(uri);
      this.rematch();
      this.score = [0, 0, 0];
    },

    getScore() {
      let uri = this.uriInUse + "results";
      this.axios.get(uri).then((response) => {
        this.score = response.data;
      });
    },

    setBoard(size) {
      this.size = size;
      this.rematch();
    },

    getWinningMoves() {
      let uri = this.uriInUse + "winningMoves";
      var winningMoves = [];
      this.axios.get(uri).then((response) => {
        winningMoves = response.data;
        for (let i = 0; i < winningMoves.length; i++) {
          var move = winningMoves[i].split(" ", 2);
          var index = parseInt(move[0]) * this.size + parseInt(move[1]);
          this.items[index].isHighlighted = true;
        }
      });
    },

    setFirstTurn() {
      this.getLastFirstTurn();
      this.firstTurn = this.lastFirstTurn == "X" ? "O" : "X";
      this.currentTurn = this.firstTurn;
    },

    getLastFirstTurn() {
      let uri = this.uriInUse + "lastFirstTurn";
      this.axios.get(uri).then((response) => {
        this.lastFirstTurn = response.data == "CROSS" ? "X" : "O";
      });
    },

    savePlayer1(player1_name) {
      this.player1 = player1_name == "" ? this.player1 : player1_name;
      this.setPlayerNames();
    },
    savePlayer2(player2_name) {
      this.player2 = player2_name == "" ? this.player2 : player2_name;
      this.setPlayerNames();
    },

    setPlayerNames() {
      let uri = this.uriInUse + "setPlayerNames/" + this.player1 + "/" + this.player2;
      this.axios.post(uri);
    },

    getPlayer1Name() {
      let uri = this.uriInUse + "crossName";
      this.axios.get(uri).then((response) => {
        this.player1 = response.data;
      });
    },
    getPlayer2Name() {
      let uri = this.uriInUse + "circleName";
      this.axios.get(uri).then((response) => {
        this.player2 = response.data;
      });
    },

    showGameStats() {
      let routeData = this.$router.resolve({ name: "game-details" });
      window.open(routeData.href, "_blank");
    },

    confettiStart() {
      this.$confetti.start({
        particles: [
          {
            type: "rect",
            size: 7,
            dropRate: 7,
          },
        ],
        defaultColors: ["DodgerBlue", "#f35fc7", "#11d9c5"],
      });
    },
    confettiStop() {
      this.$confetti.stop();
    },
  },
};
</script>

<style>
@import "../assets/style.css";
</style>
