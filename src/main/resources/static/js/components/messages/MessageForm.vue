<template>
    <div>
        <v-text-field
                label="Message"
                v-model="name"
                append-outer-icon="mdi-send"
                @click:append-outer="save"
                @keyup.enter="save"
        ></v-text-field>
    </div>
</template>
<script>

    import { mapActions } from 'vuex'

    export default {
        props: ["messageAttr","messages"],
        data: function(){
            return {
                name: '',
                id: ''
            }
        },
        watch: {
            messageAttr: function (newVal) {
                this.name = newVal.name;
                this.id = newVal.id;
            }
        },
        methods: {
            ...mapActions(['updateMessageAction','addMessageAction']),
            save: function () {
                if (this.name.length <= 0){
                    return;
                }

                const message = {
                    id: this.id,
                    name: this.name
                };

                if(message.id){
                    this.updateMessageAction(message);
                } else {
                    this.addMessageAction(message);
                }
                this.name = '';
                this.id = '';
            }
        }
    }
</script>
<style></style>