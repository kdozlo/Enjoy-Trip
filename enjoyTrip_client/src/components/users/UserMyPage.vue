<script setup>
import { ref } from "vue";
import { storeToRefs } from "pinia";
import { useRoute, useRouter } from "vue-router";
import { useMemberStore } from "@/stores/member";
import userDelete from "@/components/users/item/UserDelete.vue";

const route = useRoute();
const router = useRouter();

const showModal = ref(false);

const memberStore = useMemberStore();
const { userInfo } = storeToRefs(memberStore);

function moveModify() {
    const userid = userInfo.value.userId;
    console.log(userInfo.value.userId);
    router.push({ name: "user-modify", params: { userid } });
}

function moveDelete() {
    showModal.value = true;
}
</script>

<template>
    <userDelete v-if="showModal" @close-modal="showModal = false"></userDelete>
    <div class="container text-center mt-3" style="width: 100vw; height: 80vh">
        <div class="row justify-content-center">
            <div class="col-lg-10">
                <h2 class="my-3 py-3 text-center">
                    <div class="container text-center mt-3">
                        <h1>
                            <span>내</span>
                            <span>정</span>
                            <span>보</span>
                            <span>&nbsp;</span>
                            <span>찾</span>
                            <span>아</span>
                            <span>&nbsp;</span>
                            <span>봐</span>
                            <span>요</span>
                            <span>.</span>
                        </h1>
                    </div>
                </h2>
            </div>
            <div class="col-lg-10">
                <div class="card mt-3 m-auto" style="max-width: 700px">
                    <div class="row g-0">
                        <div class="col-md-4">
                            <img
                                src="https://source.unsplash.com/random/250x250/?food"
                                class="img-fluid rounded-start"
                                alt="..."
                            />
                        </div>
                        <div class="col-md-8">
                            <div class="card-body text-start">
                                <ul class="list-group list-group-flush">
                                    <li class="list-group-item">
                                        {{ userInfo.userId }}
                                    </li>
                                    <li class="list-group-item">
                                        {{ userInfo.userName }}
                                    </li>
                                    <li class="list-group-item">
                                        {{ userInfo.emailId }}@{{
                                            userInfo.emailDomain
                                        }}
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
                <div>
                    <button
                        type="button"
                        class="btn btn-outline-primary mt-2"
                        @click="moveModify"
                    >
                        회원정보 수정
                    </button>
                    <button
                        type="button"
                        class="btn btn-outline-danger ms-1 mt-2"
                        @click="moveDelete"
                    >
                        회원 탈퇴
                    </button>
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped>
h1 {
    height: 40px;
}

h1 span {
    position: relative;
    top: 20px;
    display: inline-block;
    animation: bounce 0.5s ease infinite alternate;
    font-family: "Titan One", cursive;
    font-size: 40px;
    color: #4f89db;
    text-shadow: 0 1px 0 #ccc, 0 2px 0 #ccc, 0 3px 0 #ccc, 0 4px 0 #ccc,
        0 5px 0 #ccc, 0 6px 0 transparent, 0 7px 0 transparent,
        0 8px 0 transparent, 0 9px 0 transparent, 0 10px 10px rgba(0, 0, 0, 0.4);
}

h1 span:nth-child(2) {
    animation-delay: 0.1s;
}
h1 span:nth-child(3) {
    animation-delay: 0.2s;
}
h1 span:nth-child(4) {
    animation-delay: 0.3s;
}
h1 span:nth-child(5) {
    animation-delay: 0.4s;
}
h1 span:nth-child(6) {
    animation-delay: 0.5s;
}
h1 span:nth-child(7) {
    animation-delay: 0.6s;
}
h1 span:nth-child(8) {
    animation-delay: 0.7s;
}

@keyframes bounce {
    100% {
        top: -20px;
        text-shadow: 0 1px 0 #ccc, 0 2px 0 #ccc, 0 3px 0 #ccc, 0 4px 0 #ccc,
            0 5px 0 #ccc, 0 6px 0 #ccc, 0 7px 0 #ccc, 0 8px 0 #ccc, 0 9px 0 #ccc,
            0 50px 25px rgba(0, 0, 0, 0.2);
    }
}
</style>
