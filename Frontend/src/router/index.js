import Vue from 'vue'
import VueRouter from 'vue-router';
import VueAxios from 'vue-axios';
import axios from 'axios';
import VueConfetti from "vue-confetti";
import VueApexCharts from 'vue-apexcharts'

import GameComponent from "../components/game.vue"
import GameDetailsComponent from "../components/gameDetails.vue"

Vue.use(VueRouter)
Vue.use(VueAxios, axios);
Vue.use(VueConfetti);
Vue.use(VueApexCharts)

Vue.component('apexchart', VueApexCharts)

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
