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
                <h2 class="my-3 py-3 shadow-sm bg-light text-center">
                    <mark class="orange">내정보</mark>
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

<style scoped></style>
