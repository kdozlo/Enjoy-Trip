<script setup>
import { ref, watch } from "vue";
import { useRoute, useRouter } from "vue-router";
import { useMemberStore } from "@/stores/member";
import { registUser } from "@/api/user";

const router = useRouter();
const route = useRoute();

const userInfoDto = ref({
    userId: "",
    userName: "",
    userPwd: "",
    userPwdCheck: "",
    emailId: "",
    emailDomain: "",
});

const userIdErrMsg = ref("");
const userPwdErrMsg = ref("");
const userNameErrMsg = ref("");
const emailIdErrMsg = ref("");
const emailDomainErrMsg = ref("");

watch(
    () => userInfoDto.value.userId,
    (value) => {
        let len = value.length;
        if (len == 0 || len > 15) {
            userIdErrMsg.value = "아이디를 확인해 주세요!!!";
        } else userIdErrMsg.value = "";
    },
    { immediate: true }
);
watch(
    () => userInfoDto.value.userPwd,
    (value) => {
        let len = value.length;
        if (len < 4) {
            userPwdErrMsg.value = "비밀번호는 4자리 이상 작성해 주세요!!!";
        } else userPwdErrMsg.value = "";
    },
    { immediate: true }
);

watch(
    () => userInfoDto.value.userPwdCheck,
    (value) => {
        console.log(value !== userInfoDto.value.userPwd);
        if (value === userInfoDto.value.userPwd) {
            userPwdErrMsg.value = "";
        } else {
            userPwdErrMsg.value = "비밀번호가 일치하지 않습니다.";
        }
    },
    { immediate: true }
);

watch(
    () => userInfoDto.value.userName,
    (value) => {
        let len = value.length;
        if (len == 0 || len > 15) {
            userNameErrMsg.value = "이름을 확인해 주세요!!!";
        } else userNameErrMsg.value = "";
    },
    { immediate: true }
);

watch(
    () => userInfoDto.value.emailId,
    (value) => {
        let len = value.length;
        if (len == 0 || len > 15) {
            emailIdErrMsg.value = "이메일 아이디를 확인해 주세요!!!";
        } else emailIdErrMsg.value = "";
    },
    { immediate: true }
);

watch(
    () => userInfoDto.value.emailDomain,
    (value) => {
        let len = value.length;
        if (len == 0) {
            emailDomainErrMsg.value = "이메일 도메인을 확인해 주세요!!!";
        } else emailDomainErrMsg.value = "";
    },
    { immediate: true }
);

function updateUser() {
    if (userIdErrMsg.value) {
        alert(userIdErrMsg.value);
    } else if (userPwdErrMsg.value) {
        alert(userPwdErrMsg.value);
    } else if (userNameErrMsg.value) {
        alert(userNameErrMsg.value);
    } else if (emailIdErrMsg.value) {
        alert(emailIdErrMsg.value);
    } else if (emailDomainErrMsg.value) {
        alert(emailDomainErrMsg.value);
    } else {
        console.log("회원가입을 하자!!!", userInfoDto.value);
        registUser(
            {
                userId: userInfoDto.value.userId,
                userName: userInfoDto.value.userName,
                userPwd: userInfoDto.value.userPwd,
                emailId: userInfoDto.value.emailId,
                emailDomain: userInfoDto.value.emailDomain,
            },
            (response) => {
                console.log(response);
                let msg = "회원가입 처리시 문제 발생했습니다.";
                if (response.status == 200) msg = "회원가입이 완료되었습니다.";
                alert(msg);
                moveMain();
            },
            (error) => console.log(error)
        );
    }
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
                    <mark class="orange">회원가입</mark>
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
                            placeholder="이름..."
                        />
                    </div>

                    <label for="userid" class="form-label">아이디 : </label>
                    <input
                        type="text"
                        class="form-control"
                        v-model="userInfoDto.userId"
                        placeholder="아이디..."
                    />
                    <div class="mb-3">
                        <label for="userpwd" class="form-label">비밀번호 : </label>
                        <input
                            type="text"
                            class="form-control"
                            v-model="userInfoDto.userPwd"
                            placeholder="비밀번호..."
                        />
                    </div>
                    <div class="mb-3">
                        <label for="pwdcheck" class="form-label">비밀번호확인 : </label>
                        <input
                            type="text"
                            class="form-control"
                            id="pwdcheck"
                            v-model="userInfoDto.userPwdCheck"
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
                                placeholder="이메일 아이디..."
                            />
                            <span class="input-group-text">@</span>
                            <input
                                type="text"
                                class="form-control"
                                v-model="userInfoDto.emailDomain"
                                placeholder="이메일 도메인..."
                            />
                        </div>
                    </div>
                    <div class="col-auto text-center">
                        <button type="submit" class="btn btn-outline-primary mb-3">회원가입</button>
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
