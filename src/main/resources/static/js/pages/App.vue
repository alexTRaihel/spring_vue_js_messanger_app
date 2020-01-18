<template>
    <v-app>
        <v-app-bar app  color="deep-purple accent-4"
                   dark>
            <v-app-bar-nav-icon></v-app-bar-nav-icon>
            <v-toolbar-title>App</v-toolbar-title>
            <div class="flex-grow-1">
                <v-btn text v-if="profile" @click="showPage('/')" :disabled="$route.path === '/'">Messages</v-btn>
            </div>
            <v-btn text v-if="profile" @click="showPage('/user')" :disabled="$route.path === '/user'">Profile</v-btn>
            <v-btn v-else icon href="/login">
                <v-icon>supervisor_account</v-icon>
            </v-btn>
            <v-btn v-if="profile" icon href="/logout">
                <v-icon>exit_to_app</v-icon>
            </v-btn>
        </v-app-bar>
        <v-content>
            <router-view></router-view>
        </v-content>
    </v-app>
</template>

<script>
    import {addHandler} from "util/ws";
    import {mapMutations, mapState} from 'vuex'

    export default {
        computed: mapState(['profile']),
        methods: {
            ...mapMutations([
                'addMessageMutation',
                'updateMessageMutation',
                'removeMessageMutation',
                'addCommentMutation']),
            showPage(page){
                this.$router.push(page);
            }
        },
        created() {
            addHandler(data=> {

                if (data.objectType === "MESSAGE"){

                    switch (data.eventType) {
                        case 'CREATE':
                            this.addMessageMutation(data.body);
                            break;
                        case 'UPDATE':
                            this.updateMessageMutation(data.body);
                            break;
                        case 'REMOVE':
                            this.removeMessageMutation(data.body);
                            break;
                        default:
                            console.error('Unknown event type');
                    }

                } if (data.objectType === "COMMENT"){

                    switch (data.eventType) {
                        case 'CREATE':
                            this.addCommentMutation(data.body);
                            break;
                        default:
                            console.error('Unknown event type');
                    }
                }

                else {
                    console.error('Unknown object type');
                }

            })
        },
        beforeMount() {
            if (!this.profile){
                this.$router.replace('/auth')
            }
        }
    }
</script>

<style>


</style>