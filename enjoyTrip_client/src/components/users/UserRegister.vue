<script setup>
import { ref, watch } from "vue";
import { useRoute, useRouter } from "vue-router";
import { registUser, idCheck } from "@/api/user";

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

const userNameDiv = ref("");
const userNameMsg = ref("");

const userIdDiv = ref("");
const userIdMsg = ref("");

const userPwdDiv = ref("");
const userPwdMsg = ref("");

const userPwdCheckDiv = ref("");
const userPwdCheckMsg = ref("");

const emailIdDiv = ref("");
const emailIdMsg = ref("");

const emailDomainDiv = ref("");
const emailDomainMsg = ref("");

watch(
    () => userInfoDto.value.userName,
    (value) => {
        let len = value.length;
        if (len > 15) {
            userNameDiv.value = "red";
            userNameMsg.value = "이름을 15자 이하로 작성해 주세요.";
        } else if (len == 0) {
            userNameDiv.value = "red";
            userNameMsg.value = "이름을 작성해 주세요.";
        } else {
            userNameDiv.value = "green";
            userNameMsg.value = "가능합니다.";
        }
    },
    { immediate: true }
);

watch(
    () => userInfoDto.value.userId,
    (value) => {
        let len = value.length;
        if (len > 15) {
            userIdDiv.value = "red";
            userIdMsg.value = "아이디를 15자 이하로 작성해 주세요.";
        } else if (len == 0) {
            userIdDiv.value = "red";
            userIdMsg.value = "아이디를 작성해 주세요.";
        } else {
            //아이디 중복 체크 하기
            idCheck(
                userInfoDto.value.userId,
                (response) => {
                    console.log(response);
                    if (response.data === "사용 가능한 아이디 입니다.") {
                        userIdDiv.value = "green";
                        userIdMsg.value = "가능합니다.";
                    } else {
                        userIdDiv.value = "red";
                        userIdMsg.value = "이미 존재하는 아이디 입니다.";
                    }
                },
                (error) => console.log(error)
            );
        }
    },
    { immediate: true }
);
watch(
    () => userInfoDto.value.userPwd,
    (value) => {
        let len = value.length;
        if (len < 4) {
            userPwdDiv.value = "red";
            userPwdMsg.value = "비밀번호를 4자 이상 작성해 주세요.";
        } else {
            userPwdDiv.value = "green";
            userPwdMsg.value = "가능합니다.";
        }
    },
    { immediate: true }
);

watch(
    () => userInfoDto.value.userPwdCheck,
    (value) => {
        if (
            userPwdDiv.value === "green" &&
            value === userInfoDto.value.userPwd
        ) {
            userPwdCheckDiv.value = "green";
            userPwdCheckMsg.value = "가능합니다.";
        } else {
            userPwdCheckDiv.value = "red";
            userPwdCheckMsg.value = "비밀번호가 일치하지 않습니다.";
        }
    },
    { immediate: true }
);

watch(
    () => userInfoDto.value.emailId,
    (value) => {
        let len = value.length;
        if (len > 15) {
            emailIdDiv.value = "red";
            emailIdMsg.value = "이메일 아이디를 15자 이하로 작성해 주세요.";
        } else if (len == 0) {
            emailIdDiv.value = "red";
            emailIdMsg.value = "이메일 아이디를 작성해 주세요.";
        } else {
            emailIdDiv.value = "green";
            emailIdMsg.value = "이메일 아이디를 작성했습니다.";
        }
    },
    { immediate: true }
);

watch(
    () => userInfoDto.value.emailDomain,
    (value) => {
        let len = value.length;
        if (len == 0 || email_check(value) == false) {
            emailDomainDiv.value = "red";
            emailDomainMsg.value = "이메일 도메인을 작성해 주세요.";
        } else {
            emailDomainDiv.value = "green";
            emailDomainMsg.value = "이메일 도메인을 작성했습니다.";
        }
    },
    { immediate: true }
);

function email_check(emailDomain) {
    var reg = /[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;

    return reg.test(emailDomain);
}

function updateUser() {
    if (
        userNameDiv.value === "red" ||
        userIdDiv.value === "red" ||
        userPwdDiv === "red" ||
        userPwdCheckDiv === "red" ||
        emailIdDiv === "red" ||
        emailDomainDiv === "red"
    ) {
        alert("회원 가입 조건을 다시 확인해 주세요.");
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

function reset() {
    userInfoDto.value = {
        userId: "",
        userName: "",
        userPwd: "",
        userPwdCheck: "",
        emailId: "",
        emailDomain: "",
    };
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
            <div class="col-lg-7 text-start">
                <form @submit.prevent="updateUser">
                    <div class="row mb-4">
                        <label for="username" class="form-label">이름 : </label>
                        <div class="col-7">
                            <input
                                type="text"
                                class="form-control"
                                v-model="userInfoDto.userName"
                                placeholder="이름..."
                            />
                        </div>
                        <div class="col-5" :id="userNameDiv">
                            {{ userNameMsg }}
                        </div>
                    </div>
                    <div class="row mb-4">
                        <label for="userid" class="form-label">아이디 : </label>
                        <div class="col-7">
                            <input
                                type="text"
                                class="form-control"
                                v-model="userInfoDto.userId"
                                placeholder="아이디..."
                            />
                        </div>
                        <div class="col-5" :id="userIdDiv">
                            {{ userIdMsg }}
                        </div>
                    </div>
                    <div class="row mb-4">
                        <label for="userpwd" class="form-label"
                            >비밀번호 :
                        </label>
                        <div class="col-7">
                            <input
                                type="password"
                                class="form-control"
                                v-model="userInfoDto.userPwd"
                                placeholder="비밀번호..."
                            />
                        </div>
                        <div class="col-5" :id="userPwdDiv">
                            {{ userPwdMsg }}
                        </div>
                    </div>
                    <div class="row mb-4">
                        <label for="pwdcheck" class="form-label"
                            >비밀번호 확인 :
                        </label>
                        <div class="col-7">
                            <input
                                type="password"
                                class="form-control"
                                id="pwdcheck"
                                v-model="userInfoDto.userPwdCheck"
                                placeholder="비밀번호 확인..."
                            />
                        </div>
                        <div class="col-5" :id="userPwdCheckDiv">
                            {{ userPwdCheckMsg }}
                        </div>
                    </div>
                    <div class="mb-3">
                        <label for="emailid" class="form-label"
                            >이메일 :
                        </label>
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
                        <div :id="emailIdDiv">
                            {{ emailIdMsg }}
                        </div>
                        <div class="mb-3" :id="emailDomainDiv">
                            {{ emailDomainMsg }}
                        </div>
                    </div>
                    <div class="col-auto text-center">
                        <button
                            type="submit"
                            class="btn btn-outline-primary mb-3"
                        >
                            회원가입
                        </button>
                        <button
                            type="button"
                            class="btn btn-outline-success ms-1 mb-3"
                            @click="reset"
                        >
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

<style scoped>
#red {
    color: red;
}
#green {
    color: green;
}
</style>
