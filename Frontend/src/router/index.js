import Vue from 'vue'
import VueRouter from 'vue-router';
import VueAxios from 'vue-axios';
import axios from 'axios';

import GameComponent from "../components/game.vue"

Vue.use(VueRouter)
Vue.use(VueAxios, axios);


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
        }
    ]
})
