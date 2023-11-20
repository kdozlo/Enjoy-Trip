<script setup>
import { ref, watch } from "vue";
import { storeToRefs } from "pinia";
import { useRoute, useRouter } from "vue-router";
import { useMemberStore } from "@/stores/member";
import { registUser, modifyUser } from "@/api/user";

const router = useRouter();
const route = useRoute();

const props = defineProps({ type: String });

const userInfoDto = ref({
    userId: "",
    userName: "",
    userPwd: "",
    userPwdCheck: "",
    emailId: "",
    emailDomain: "",
});

//회원가입 회원정보 수정 시 오류 메시지 체크 해야함
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
        if (value != userInfoDto.value.userPwdCheck) {
            userPwdErrMsg.value = "비밀번호가 일치하지 않습니다.";
        } else userPwdErrMsg.value = "";
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

const placeholderValue = ref({
    userId: "아이디...",
    userName: "이름...",
    emailId: "이메일 아이디...",
    emailDomain: "이메일 도메인...",
});

if (props.type === "modify") {
    const memberStore = useMemberStore();
    const { userInfo } = storeToRefs(memberStore);
    placeholderValue.value = userInfo.value;
    userInfoDto.value = userInfo.value;
}

function onSubmit() {
    // event.preventDefault();

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
        console.log("UserFormitem onSubmit : ", userInfoDto.value);
        props.type === "modify" ? updateUser() : saveUser();
    }
}

function saveUser() {
    console.log("회원가입을 하자!!!", userInfoDto.value);
    registUser(
        {
            userId: "",
            userName: "",
            userPwd: "",
            userPwdCheck: "",
            emailId: "",
            emailDomain: "",
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
    <form @submit.prevent="onSubmit">
        <div class="mb-3">
            <label for="username" class="form-label">이름 : </label>
            <input
                type="text"
                class="form-control"
                v-model="userInfoDto.userName"
                :placeholder="placeholderValue.userName"
            />
        </div>

        <div class="mb-3" v-if="type === 'register'">
            <label for="userid" class="form-label">아이디 : </label>
            <input
                type="text"
                class="form-control"
                v-model="userInfoDto.userId"
                :placeholder="placeholderValue.userId"
            />
        </div>

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
                    :placeholder="placeholderValue.emailId"
                />
                <span class="input-group-text">@</span>
                <input
                    type="text"
                    class="form-control"
                    v-model="userInfoDto.emailDomain"
                    :placeholder="placeholderValue.emailDomain"
                />
            </div>
        </div>
        <div class="col-auto text-center">
            <button type="submit" class="btn btn-outline-primary mb-3" v-if="type === 'modify'">
                회원정보 수정
            </button>
            <button type="submit" class="btn btn-outline-primary mb-3" v-else>회원가입</button>
            <button type="button" class="btn btn-outline-success ms-1 mb-3">초기화</button>
            <button type="button" class="btn btn-outline-danger mb-3 ms-1" @click="moveMain">
                취소
            </button>
        </div>
    </form>
</template>

<style scoped></style>
