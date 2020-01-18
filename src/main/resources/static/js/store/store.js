import Vue from 'vue';
import Vuex from 'vuex';
import messageApi from 'api/messages'
import commentApi from 'api/comment'

Vue.use(Vuex);

export default new Vuex.Store({
    state: {
        messages,
        profile,
        ...frontendData
    },
    getters: {
        sortedMessages: state => (state.messages || []).sort((a,b) => -(a.id - b.id))
    },
    mutations: {
        addMessageMutation(state, message){
            state.messages = [
                ...state.messages,
                message
            ]
        },
        updateMessageMutation(state, message){
            const index = state.messages.findIndex(item => item.id === message.id);
            state.messages = [
                ...state.messages.slice(0, index),
                message,
                ...state.messages.slice(index + 1)
            ]
        },
        removeMessageMutation(state, message){
            const index = state.messages.findIndex(item => item.id === message.id);
            if (index > -1){
                state.messages = [
                    ...state.messages.slice(0, index),
                    ...state.messages.slice(index + 1)
                ]
            }
        },
        addCommentMutation(state, comment){
            const updateIndex = state.messages.findIndex(item => item.id === comment.message.id);
            const updatedMessage = state.messages[updateIndex];
            if (!updatedMessage.comments.find(it => it.id === comment.id)) {
                state.messages = [
                    ...state.messages.slice(0, updateIndex),
                    {
                        ...updatedMessage,
                         comments: [
                            ...updatedMessage.comments || [],
                            comment
                        ]
                    },
                    ...state.messages.slice(updateIndex + 1)
                ]
            }
        },
        addMessagePageMutation(state, messages){
            const targetMessages = state.messages
                .concat(messages)
                .reduce((res, val) => {
                    res[val.id] = val;
                    return res;
                }, {});
            state.messages = Object.values(targetMessages);
        },
        updateTotalPageMutation(state, totalPages){
            state.totalPage = totalPages;
        },
        updateCurrentPageMutation(state, currentPage){
            state.currentPage = currentPage;
        }
    },
    actions: {
        async addMessageAction({commit, state}, message){
            const result =  await messageApi.add(message);
            const data = await result.json();
            const index = state.messages.findIndex(item => item.id === data.id);
            if (index > -1){
                commit('updateMessageMutation', data);
            } else {
                commit('addMessageMutation', data);
            }
        },
        async updateMessageAction({commit}, message){
            const result =  await messageApi.update(message);
            const data = await result.json();
            commit('updateMessageMutation', data);
        },
        async removeMessageAction({commit}, message){
            const result =  await messageApi.remove(message.id);
            if (result.ok) {
                commit('removeMessageMutation', message);
            }
        },
        async addCommentAction({commit}, comment){
            const result = await commentApi.add(comment);
            if (result.ok){
                const data = await result.json();
                commit('addCommentMutation', data);
            }
        },
        async loadPageAction({commit, state}){
            const response = await messageApi.page(state.currentPage + 1);
            if (response.ok){
                const data = await response.json();
                commit('addMessagePageMutation', data.messages);
                commit('updateTotalPageMutation', data.totalPages);
                commit('updateCurrentPageMutation', Math.min(data.currentPage, data.totalPages - 1));
            }
        }
    }
})