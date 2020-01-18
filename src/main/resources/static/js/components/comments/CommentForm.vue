<template>
    <v-text-field
            label="Comment"
            v-model="text"
            append-outer-icon="mdi-send"
            @click:append-outer="save"
            @keyup.enter="save"
    ></v-text-field>
</template>

<script>

    import {mapActions} from 'vuex'

    export default {
        name: "CommentForm",
        props: ["messageId"],
        data(){
            return{
                text: '',
            }
        },
        methods: {
            ...mapActions(['addCommentAction']),
            async save() {
                if (this.text.length > 0) {
                    await this.addCommentAction({
                        text: this.text,
                        message: {
                            id: this.messageId
                        }
                    })
                }

                this.text = ''
            }
        }
    }
</script>

<style scoped>

</style>