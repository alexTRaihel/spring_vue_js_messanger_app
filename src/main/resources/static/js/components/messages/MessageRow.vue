<template>
    <div>
        <v-card
                class="mx-auto"
                max-width="800"
        >
            <v-card-text>
                <user-link
                    :user="message.author"
                    size="48"
                >
                </user-link>
                <div>
                    {{message.name}}
                </div>
                <media v-if="message.link" :message="message"></media>
                <v-card-actions cols="1" class="d-flex align-center">
                    <v-icon @click="edit">edit</v-icon>
                    <v-icon @click="del">delete_forever</v-icon>
                </v-card-actions>
            </v-card-text>
        </v-card>
        <comment-list :comments="message.comments" :message-id="message.id"></comment-list>
    </div>
</template>

<script>

    import {mapActions} from 'vuex'
    import Media from 'components/media/Media.vue'
    import CommentList from "../comments/CommentList.vue";
    import UserLink from "../profile/UserLink.vue";

    export default {
        props: ['message','editMessage','deleteMessage'],
        components: {UserLink, CommentList, Media},
        methods: {
            ...mapActions(['removeMessageAction']),
            edit: function () {
                this.editMessage(this.message);
            },

            del: function () {
                this.removeMessageAction(this.message);
            }
        }
    }
</script>

<style>


</style>

