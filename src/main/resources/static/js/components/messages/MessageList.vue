<template>
    <div class="app-messages">
        <div>
            <message-form
                    :messageAttr="message">
            </message-form>
        </div>
        <div>
            <message-row v-for="message in sortedMessages"
                        :key="message.id"
                        :message="message"
                        :editMessage="editMessage">
            </message-row>
            <lazy-loader></lazy-loader>
        </div>
    </div>
</template>

<script>
    import MessageRow from 'components/messages/MessageRow.vue'
    import MessageForm from 'components/messages/MessageForm.vue'
    import { mapGetters } from 'vuex'
    import LazyLoader from "../loader/LazyLoader.vue";

    export default {

        components: {
            LazyLoader,
            MessageRow,
            MessageForm
        },

        data() {
            return{
                message: null,
            }
        },

        computed: mapGetters(['sortedMessages']),

        methods: {

            editMessage(message) {
                this.message = message;
            }
        }
    }
</script>

<style>
.app-messages{
    max-width: 600px;
    margin: auto;
}

</style>