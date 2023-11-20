<script setup>
import { ref, watch } from "vue";
import { storeToRefs } from "pinia";
import { useRoute, useRouter } from "vue-router";
import { useMemberStore } from "@/stores/member";
import { modifyUser } from "@/api/user";

const userInfoDto = ref({
    userName: "",
    userPwd: "",
    emailId: "",
    emailDomain: "",
});

const router = useRouter();
const route = useRoute();

const memberStore = useMemberStore();
const { userInfo } = storeToRefs(memberStore);

function updateUser() {
    console.log(userInfoDto.value.userName + "의 회원정보를 수정하자!!");
    modifyUser(
        userInfoDto.value,
        (response) => {
            let msg = "회원정보 수정 처리시 문제 발생했습니다.";
            if (response.status == 200) msg = "회원정보 수정이 완료되었습니다.";
            alert(msg);
            moveMain();
        },
        (error) => console.log(error)
    );
}

function moveMain() {
    router.push({ name: "main" });
}
</script>

<template>
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-lg-10">
                <h2 class="my-3 py-3 shadow-sm bg-light text-center">
                    <mark class="orange">회원정보 수정</mark>
                </h2>
            </div>
            <div class="col-lg-10 text-start">
                <form @submit.prevent="updateUser">
                    <div class="mb-3">
                        <label for="username" class="form-label">이름 : </label>
                        <input
                            type="text"
                            class="form-control"
                            v-model="userInfoDto.userName"
                            :placeholder="userInfo.userName"
                        />
                    </div>
                    <div class="mb-3">
                        <label for="userpwd" class="form-label">비밀번호 : </label>
                        <input
                            type="password"
                            class="form-control"
                            v-model="userInfoDto.userPwd"
                            placeholder="비밀번호..."
                        />
                    </div>
                    <div class="mb-3">
                        <label for="pwdcheck" class="form-label">비밀번호확인 : </label>
                        <input
                            type="password"
                            class="form-control"
                            id="pwdcheck"
                            placeholder="비밀번호 확인..."
                        />
                    </div>
                    <div class="mb-3">
                        <label for="emailid" class="form-label">이메일 : </label>
                        <div class="input-group">
                            <input
                                type="text"
                                class="form-control"
                                v-model="userInfoDto.emailId"
                                :placeholder="userInfo.emailId"
                            />
                            <span class="input-group-text">@</span>
                            <input
                                type="text"
                                class="form-control"
                                v-model="userInfoDto.emailDomain"
                                :placeholder="userInfo.emailDomain"
                            />
                        </div>
                    </div>
                    <div class="col-auto text-center">
                        <button type="submit" class="btn btn-outline-primary mb-3">
                            회원정보 수정
                        </button>
                        <button type="button" class="btn btn-outline-success ms-1 mb-3">
                            초기화
                        </button>
                        <button
                            type="button"
                            class="btn btn-outline-danger mb-3 ms-1"
                            @click="moveMain"
                        >
                            취소
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</template>

<style scoped></style>
