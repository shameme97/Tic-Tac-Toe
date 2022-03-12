<template>
  <div>
    <h1>Tic Tac Toe</h1>

    <div class="sidenav">
      <h2>Scoreboard</h2>
      <div class="scoreBoard">
        Cross : {{ score[0] }} <br />
        Circle : {{ score[1] }} <br />
        Draw : {{ score[2] }}
      </div>
      <button @click="resetScore()">Reset Game</button>
    </div>

    <div class="topdiv">
      <label for="selectBoard">Select board size:</label>
      <select class="selectBoard" name="selectBoard" v-model="size">
        <option v-for="(item, index) in sizeList" v-bind:key="index">
          {{ item }}
        </option>
      </select>
      &nbsp;
      <button @click="beginGame(size)">Begin</button>
    </div>
    <br />
    <div id="game-view">
      <div id="game-view-info">
        {{ this.message }} <br />It is {{ this.currentTurn }}'s turn
      </div>
      <div id="game-view-squares">
        <div
          v-for="(square, i) in items"
          v-bind:key="i"
          v-on:click="makeMove(i)"
          v-bind:class="{ hightlighted: square.isHighlighted }"
          class="game-view-square"
        >
          {{ square.value }}
        </div>
      </div>
      <div id="buttonsDiv">
        <button v-on:click="submitMoves()">Submit</button>
        &nbsp;
        <button v-on:click="rematch()">Rematch</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      size: 3,
      // squares: {
      //   value: "A",
      //   isHightlighted: false,
      // },
      items: [],
      sizeList: [3, 4, 5, 6, 7, 8, 9, 10, 11, 12],
      currentTurn: "X",
      inProgress: true,
      message: "",
      returnList: [],
      movesMade: 0,
      score: [],
    };
  },
  components: {},

  created: function () {
    this.createArray();
    this.getScore();
  },

  methods: {
    createArray() {
      this.items = new Array(this.size * this.size)
        .fill()
        .map((s) => ({ value: "", isHighlighted: false }));
    },

    beginGame(size) {
      this.size = size;
      this.createArray();
      let uri = "http://localhost:4023/begin/" + size;
      this.axios.post(uri);
    },

    makeMove(i) {
      if (this.inProgress) {
        var row = Math.floor(i / this.size);
        var col = i % this.size;

        if (this.inProgress && this.items[i].value == "") {
          this.items[i].value = this.currentTurn;
          var string = this.currentTurn == "X" ? "CROSS " : "CIRCLE ";
          string += row + " " + col;
          this.returnList[this.movesMade] = string;
          this.movesMade++;
          this.currentTurn = this.currentTurn == "X" ? "O" : "X";
        }
      }
    },

    submitMoves() {
      let uri = "http://localhost:4023/submitMoves";
      this.axios.post(uri, this.returnList).then((response) => {
        this.message = response.data;
        if (response.data != "Match In Progress!") {
          this.inProgress = false;
          this.getScore();
        }
      });
    },

    rematch() {
      this.createArray();
      this.inProgress = true;
      this.message = "";
      this.returnList = [];
      this.movesMade = 0;
      let uri = "http://localhost:4023/begin/" + this.size;
      this.axios.post(uri);
    },

    resetScore() {
      let uri = "http://localhost:4023/reset";
      this.axios.post(uri);
      this.getScore();
      this.rematch();
    },

    getScore() {
      let uri = "http://localhost:4023/results";
      this.axios.get(uri).then((response) => {
        this.score = response.data;
      });
    },
  },
};
</script>

<style>
@import "../assets/style.css";
</style>
