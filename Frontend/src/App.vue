<template>
  <div id="app">
    <div id="nav">
      <router-link to="/home"></router-link>
    </div>
    <router-view @authenticated="true" />
  </div>
</template>

<script>
export default {
  name: "App",
  data() {
    return {
      board: {
        size: 3,
        winner: "",
      },
    };
  },
  methods: {},
  created() {
    document.title = "Tic Tac Toe";
  },
};
</script>

<style>
body {
  background-color: #d4e6f5;
}
h1 {
  padding: 0;
  margin-top: 0;
}
#app {
  width: 1024px;
  margin: auto;
}
</style>
