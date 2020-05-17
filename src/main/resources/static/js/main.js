import Vue from 'vue'
import 'api/resource'
import '@babel/polyfill'
import store from 'store/store'
import router from 'router/router'
import Vuetify from 'vuetify'
import App from 'pages/App.vue'
import { connect } from 'util/ws'
import 'vuetify/dist/vuetify.min.css'

if (profile){
    connect();
}

Vue.use(Vuetify);

new Vue({
    vuetify: new Vuetify(),
    el: '#app',
    router,
    store,
    render: a => a(App)
});
