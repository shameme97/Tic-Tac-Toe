<template>
  <div>
    <div class="sidenav">
      <h2>Scoreboard</h2>
      <div class="scoreBoard">
        <table>
          <tr>
            <td>CROSS</td>
            <td>:</td>
            <td>{{ score[0] }}</td>
          </tr>
          <tr>
            <td>CIRCLE</td>
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

    <div id="resultMessage">
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
      message: "",
      beginMessage: "Let's play tic-tac-toe!",
      movesList: [],
      movesMade: 0,
      score: [],
      squareSize: "33.33%",
      boardSize: "580px",
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
  },

  methods: {
    confettiStart() {
      this.$confetti.start();
    },

    confettiStop() {
      this.$confetti.stop();
    },

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
        if (response.data == "") return;
        this.message = response.data;
        if (response.data != "Match In Progress!") {
          this.inProgress = false;
          this.getScore();
          this.getWinningMoves();
          this.confettiStart();
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

    showGameStats() {
      // this.$router.push("game-details");
      let routeData = this.$router.resolve({ name: "game-details" });
      window.open(routeData.href, "_blank");
    },
  },
};
</script>

<style>
@import "../assets/style.css";
</style>
