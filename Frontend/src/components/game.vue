<template>
  <div>
    <h1>Tic Tac Toe</h1>
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
      winner: "",
      currentTurn: "X",
      inProgress: true,
      message: "",
      returnList: [],
      movesMade: 0,
    };
  },
  components: {},

  created: function () {
    this.createArray();
  },

  methods: {
    createArray() {
      // this.items = new Array(this.size * this.size).fill(this.squares);

      // for (let step = 0; step < this.size; step++) {
      //   var array = new Array(this.size)
      //     .fill()
      //     .map((s) => ({ value: "", isHighlighted: false }));
      //   this.items[step] = array;
      // }
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
      if (this.inProgress && this.items[i].value == "") {
        this.items[i].value = this.currentTurn;
        this.currentTurn = this.currentTurn == "X" ? "O" : "X";

        // this.returnList[this.movesMade] = "";
        // this.movesMade++;
      }
    },
  },
};
</script>

<style>
@import "../assets/style.css";
</style>
