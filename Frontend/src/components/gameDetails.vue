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

    <table class="winStatsTurn">
      <thead>
        <tr>
          <th width="20%">Turn</th>
          <th>Winner</th>
          <th>Winning Percentage</th>
        </tr>
      </thead>
      <tbody>
        <tr>
          <td width="20%">1st</td>
          <td>{{ player1 }}</td>
          <td>
            <div class="container">
              <div
                class="percentage x-1st-x-win"
                v-bind:style="{ width: computedWidth_bar0 }"
              >
                {{ winStatsTurn[0] }}%
              </div>
            </div>
          </td>
        </tr>
        <tr>
          <td width="20%">1st</td>
          <td>{{ player2 }}</td>
          <td>
            <div class="container">
              <div
                class="percentage o-1st-o-win"
                v-bind:style="{ width: computedWidth_bar2 }"
              >
                {{ winStatsTurn[2] }}%
              </div>
            </div>
          </td>
        </tr>
        <tr>
          <td width="20%">2nd</td>
          <td>{{ player1 }}</td>
          <td>
            <div class="container">
              <div
                class="percentage x-2nd-x-win"
                v-bind:style="{ width: computedWidth_bar1 }"
              >
                {{ winStatsTurn[1] }}%
              </div>
            </div>
          </td>
        </tr>
        <tr>
          <td width="20%">2nd</td>
          <td>{{ player2 }}</td>
          <td>
            <div class="container">
              <div
                class="percentage o-2nd-o-win"
                v-bind:style="{ width: computedWidth_bar3 }"
              >
                {{ winStatsTurn[3] }}%
              </div>
            </div>
          </td>
        </tr>
      </tbody>
    </table>
    <div class="pieChart">
      <apexchart
        type="pie"
        width="260"
        :options="chartOptions"
        :series="series"
      ></apexchart>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      gameDetails: [],
      winStatsTurn: [],
      player1: "CROSS",
      player2: "CIRCLE",

      herokuUri: "https://tictactoe-shameme-backend.herokuapp.com/",
      localUri: "http://localhost:4023/",
      uriInUse: "",

      series: [0, 0, 0],
      chartOptions: {
        chart: {
          width: "100%",
          type: "pie",
        },

        labels: ["CROSS", "CIRCLE", "DRAW"],
        theme: {
          monochrome: {
            enabled: true,
          },
        },
        fill: {
          colors: ["#0f6e9b", "#00b3ff", "#23b7c4"],
        },
        plotOptions: {
          pie: {
            dataLabels: {
              offset: -10,
            },
          },
        },
        title: {
          text: "Winning Percentage of All Games",
          style: {
            fontSize: "15px",
            fontWeight: "bold",
            fontFamily: "cursive",
            color: "#06a8ad",
          },
        },
        dataLabels: {
          formatter(val, opts) {
            const name = opts.w.globals.labels[opts.seriesIndex];
            return [name, val.toFixed(1) + "%"];
          },
        },
        legend: {
          show: false,
        },
      },
    };
  },
  created: function () {
    this.uriInUse = this.herokuUri;
    this.getGameDetails();
    this.getWinStatsTurn();
    this.getWinStats();
    this.setPlayers();
  },
  computed: {
    computedWidth_bar0: function () {
      if (this.winStatsTurn[0] != 0) return this.winStatsTurn[0] + "%";
    },
    computedWidth_bar1: function () {
      if (this.winStatsTurn[1] != 0) return this.winStatsTurn[1] + "%";
    },
    computedWidth_bar2: function () {
      if (this.winStatsTurn[2] != 0) return this.winStatsTurn[2] + "%";
    },
    computedWidth_bar3: function () {
      if (this.winStatsTurn[3] != 0) return this.winStatsTurn[3] + "%";
    },
  },
  methods: {
    getGameDetails() {
      let uri = this.uriInUse + "gameDetails";
      this.axios.get(uri).then((response) => {
        this.gameDetails = response.data;
      });
    },

    getWinStatsTurn() {
      let uri = this.uriInUse + "winStats-based-on-turn";
      this.axios.get(uri).then((response) => {
        this.winStatsTurn = response.data;
      });
    },

    getWinStats() {
      let uri = this.uriInUse + "winStats";
      this.axios.get(uri).then((response) => {
        this.series = response.data;
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
