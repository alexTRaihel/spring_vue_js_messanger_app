<template>
    <v-list two-line>
        <v-subheader>
            Comments
        </v-subheader>
        <template v-for="(item, index) in comments">
            <v-divider v-if="index > 0" :key="index"></v-divider>
            <comment-item :key="item + index" :comment="item"></comment-item>
        </template>
        <comment-form :message-id="messageId"></comment-form>
    </v-list>
</template>

<script>

    import CommentItem from "./CommentItem.vue";
    import CommentForm from "./CommentForm.vue";

    export default {
        name: "CommentList",
        props: ["comments", "messageId"],
        components: {
            CommentItem,
            CommentForm
        }
    }
</script>

<style scoped>

</style>