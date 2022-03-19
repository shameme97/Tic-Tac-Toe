<template>
  <div>
    <table class="gameDetails">
      <thead>
        <tr>
          <th>Game no.</th>
          <th>Board Size</th>
          <th>1st Turn</th>
          <th>Winner</th>
          <th>Winner's Symbol</th>
          <th>Number of Moves</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(game, i) in gameDetails" v-bind:key="i">
          <td v-for="(info, j) in game" v-bind:key="j">
            {{ info }}
          </td>
        </tr>
      </tbody>
    </table>

    <table class="winStats">
      <thead>
        <tr>
          <th>Turn</th>
          <th>Winner</th>
          <th>Winning Percentage</th>
        </tr>
      </thead>
      <tbody>
        <tr>
          <td>1st</td>
          <td>{{ player1 }}</td>
          <td>
            <div class="container">
              <div
                class="percentage x-1st-x-win"
                v-bind:style="{ width: computedWidth_bar0 }"
              >
                {{ winStats[0] }}%
              </div>
            </div>
          </td>
        </tr>
        <tr>
          <td>1st</td>
          <td>{{ player2 }}</td>
          <td>
            <div class="container">
              <div
                class="percentage o-1st-o-win"
                v-bind:style="{ width: computedWidth_bar2 }"
              >
                {{ winStats[2] }}%
              </div>
            </div>
          </td>
        </tr>
        <tr>
          <td>2nd</td>
          <td>{{ player1 }}</td>
          <td>
            <div class="container">
              <div
                class="percentage x-2nd-x-win"
                v-bind:style="{ width: computedWidth_bar1 }"
              >
                {{ winStats[1] }}%
              </div>
            </div>
          </td>
        </tr>
        <tr>
          <td>2nd</td>
          <td>{{ player2 }}</td>
          <td>
            <div class="container">
              <div
                class="percentage o-2nd-o-win"
                v-bind:style="{ width: computedWidth_bar3 }"
              >
                {{ winStats[3] }}%
              </div>
            </div>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
export default {
  name: "gameDetails",
  data() {
    return {
      gameDetails: [],
      winStats: [],
      player1: "CROSS",
      player2: "CIRCLE",
      herokuUri: "https://tictactoe-shameme-backend.herokuapp.com/",
      localUri: "http://localhost:4023/",
      uriInUse: "",
    };
  },
  created: function () {
    this.uriInUse = this.localUri;
    this.getGameDetails();
    this.getWinStats();
    this.setPlayers();
  },
  computed: {
    computedWidth_bar0: function () {
      if (this.winStats[0] != 0) return this.winStats[0] + "%";
    },
    computedWidth_bar1: function () {
      if (this.winStats[1] != 0) return this.winStats[1] + "%";
    },
    computedWidth_bar2: function () {
      if (this.winStats[2] != 0) return this.winStats[2] + "%";
    },
    computedWidth_bar3: function () {
      if (this.winStats[3] != 0) return this.winStats[3] + "%";
    },
  },
  methods: {
    getGameDetails() {
      let uri = this.uriInUse + "gameDetails";
      this.axios.get(uri).then((response) => {
        this.gameDetails = response.data;
      });
    },

    getWinStats() {
      let uri = this.uriInUse + "winStats";
      this.axios.get(uri).then((response) => {
        this.winStats = response.data;
      });
    },

    setPlayers() {
      let uri = this.uriInUse + "crossName";
      this.axios.get(uri).then((response) => {
        this.player1 = response.data;
      });
      uri = this.uriInUse + "circleName";
      this.axios.get(uri).then((response) => {
        this.player2 = response.data;
      });
    },
  },
};
</script>

<style>
@import "../assets/style.css";
</style>
