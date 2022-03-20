import Vue from 'vue'
import VueRouter from 'vue-router';
import VueAxios from 'vue-axios';
import axios from 'axios';
import VueConfetti from "vue-confetti";

import GameComponent from "../components/game.vue"
import GameDetailsComponent from "../components/gameDetails.vue"
import VueApexCharts from 'vue-apexcharts'
Vue.use(VueApexCharts)

Vue.component('apexchart', VueApexCharts)

Vue.use(VueRouter)
Vue.use(VueAxios, axios);
Vue.use(VueConfetti);


export default new VueRouter({
    routes: [
        {
            path: '/',
            redirect: {
                name: "home"
            }
        },
        {
            path: "/home",
            name: "home",
            component: GameComponent
        },
        {
            path: "/game-details",
            name: "game-details",
            component: GameDetailsComponent
        }
    ]
    
})
