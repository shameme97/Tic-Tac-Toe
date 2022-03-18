<template>
  <div>
    <table>
      <thead>
        <tr>
          <th>Game no.</th>
          <th>Board Size</th>
          <th>1st Turn</th>
          <th>Winner</th>
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
  </div>
</template>

<script>
export default {
  name: "game-details",
  data() {
    return {
      gameDetails: [],
    };
  },
  created: function () {
    this.getGameDetails();
  },
  methods: {
    getGameDetails() {
      let uri = "http://localhost:4023/gameDetails";
      this.axios.get(uri).then((response) => {
        this.gameDetails = response.data;
      });
    },
  },
};
</script>

<style>
@import "../assets/style.css";
</style>
