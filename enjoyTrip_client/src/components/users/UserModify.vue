<script setup>
import { ref, watch } from "vue";
import { useRoute, useRouter } from "vue-router";
import { modifyUser } from "@/api/user";

const userInfoDto = ref({
    userId: "",
    userName: "",
    userPwd: "",
    userPwdCheck: "",
    emailId: "",
    emailDomain: "",
});

const userInfo = ref({});

userInfo.value = JSON.parse(sessionStorage.getItem("memberStore")).userInfo;

userInfoDto.value = {
    userId: userInfo.value.userId,
    userName: userInfo.value.userName,
    userPwd: "",
    userPwdCheck: "",
    emailId: userInfo.value.emailId,
    emailDomain: userInfo.value.emailDomain,
};

console.log("초기화 됐니? ", userInfoDto.value);

const router = useRouter();
const route = useRoute();

const userNameDiv = ref("");
const userNameMsg = ref("");

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
        } else if (value === userInfo.value.userName) {
            userNameDiv.value = "blue";
            userNameMsg.value = "이름 변경 사항 없습니다.";
        } else {
            userNameDiv.value = "green";
            userNameMsg.value = "변경 가능합니다.";
        }
    },
    { immediate: true }
);

watch(
    () => userInfoDto.value.userPwd,
    (value) => {
        let len = value.length;
        if (len == 0) {
            userPwdDiv.value = "blue";
            userPwdMsg.value = "비밀번호 변경 사항 없습니다.";
        } else if (len < 4) {
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
        let len = value.length;
        if (len == 0 && userPwdDiv.value === "blue") {
            userPwdCheckDiv.value = "blue";
            userPwdCheckMsg.value = "비밀번호 변경 사항 없습니다.";
        } else if (
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
        } else if (value === userInfo.value.emailId) {
            emailIdDiv.value = "blue";
            emailIdMsg.value = "이메일 아이디 변경 사항이 없습니다.";
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
        } else if (value === userInfo.value.emailDomain) {
            emailDomainDiv.value = "blue";
            emailDomainMsg.value = "이메일 도메인 변경 사항 없습니다.";
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
    console.log(userInfoDto.value.userName + "의 회원정보를 수정하자!!");

    if (
        userNameDiv.value === "red" ||
        userPwdDiv.value === "red" ||
        userPwdCheckDiv.value === "red" ||
        emailIdDiv.value === "red" ||
        emailDomainDiv.value === "red"
    ) {
        alert("회원 가입 조건을 다시 확인해 주세요.");
    } else if (
        userNameDiv.value === "blue" &&
        userPwdDiv.value === "blue" &&
        userPwdCheckDiv.value === "blue" &&
        emailIdDiv.value === "blue" &&
        emailDomainDiv.value === "blue"
    ) {
        alert("회원정보 변경 사항이 없습니다.");
    } else {
        if (userPwdDiv.value === "blue" && userPwdCheckDiv.value === "blue")
            userInfoDto.value.userPwd = null;
        modifyUser(
            {
                userId: userInfoDto.value.userId,
                userName: userInfoDto.value.userName,
                userPwd: userInfoDto.value.userPwd,
                emailId: userInfoDto.value.emailId,
                emailDomain: userInfoDto.value.emailDomain,
            },
            (response) => {
                let msg = "회원정보 수정 처리시 문제 발생했습니다.";
                if (response.status == 200)
                    msg = "회원정보 수정이 완료되었습니다.";
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
        userId: userInfo.value.userId,
        userName: userInfo.value.userName,
        userPwd: "",
        userPwdCheck: "",
        emailId: userInfo.value.emailId,
        emailDomain: userInfo.value.emailDomain,
    };
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
            <div class="col-lg-7 text-start">
                <form @submit.prevent="updateUser">
                    <div class="row mb-4">
                        <label for="username" class="form-label">이름 : </label>
                        <div class="col-7">
                            <input
                                type="text"
                                class="form-control"
                                v-model="userInfoDto.userName"
                                :placeholder="userInfo.userName"
                            />
                        </div>
                        <div class="col-5" :id="userNameDiv">
                            {{ userNameMsg }}
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
                            회원정보 수정
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
#blue {
    color: blue;
}
</style>
