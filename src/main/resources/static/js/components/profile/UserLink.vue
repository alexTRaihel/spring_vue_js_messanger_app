<template>
    <router-link :to="`/user/${user.id}`"
        class="px-3">
        <v-avatar v-if="user && user.userpic"
                  :size="`${size || 36}px`">
            <img :src="user.userpic"
                 :alt="user.name">
        </v-avatar>
        <v-avatar
                :size="`${size || 36}px`"
                v-else color="indigo">
            <v-icon dark>mdi-account-circle</v-icon>
        </v-avatar>
        <span>{{authorName}}</span>
    </router-link>
</template>

<script>

    export default {
        name: "UserLink",
        props: ['user', 'size'],
        computed: {
            authorName(){
                return this.user.name ? this.user.name : 'unknown'
            }
        }
    }
</script>

<style scoped>

</style>