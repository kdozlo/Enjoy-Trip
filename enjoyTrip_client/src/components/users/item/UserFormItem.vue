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
    emailId: "",
    emailDomain: "",
    joinDate: "",
    refreshToken: "",
});

const placeholderValue = ref({
    userId: "아이디...",
    userName: "이름...",
    emailId: "이메일 아이디...",
    emailDomain: "선택",
});

if (props.type === "modify") {
    const memberStore = useMemberStore();
    const { userInfo } = storeToRefs(memberStore);
    placeholderValue.value = userInfo.value;
    userInfoDto = userInfo.value;
}

const userIdErrMsg = ref("");
const userPwdErrMsg = ref("");
const userNameErrMsg = ref("");
const emailIdNameErrMsg = ref("");
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
    () => article.value.userPwd,
    (value) => {
        let len = value.length;
        if (len < 4) {
            userPwdErrMsg.value = "비밀번호는 4자리 이상 작성해 주세요!!!";
        } else userPwdErrMsg.value = "";
    },
    { immediate: true }
);

watch(
    () => article.value.userName,
    (value) => {
        let len = value.length;
        if (len == 0 || len > 15) {
            userNameErrMsg.value = "이름을 확인해 주세요!!!";
        } else userNameErrMsg.value = "";
    },
    { immediate: true }
);

watch(
    () => article.value.emailId,
    (value) => {
        let len = value.length;
        if (len == 0 || len > 15) {
            emailIdErrMsg.value = "이메일 아이디를 확인해 주세요!!!";
        } else emailIdErrMsg.value = "";
    },
    { immediate: true }
);

watch(
    () => article.value.emailDomain,
    (value) => {
        let len = value.length;
        if (len == 0) {
            emailDomainErrMsg.value = "이메일 도메인을 확인해 주세요!!!";
        } else emailDomainErrMsg.value = "";
    },
    { immediate: true }
);

function onSubmit() {
    // event.preventDefault();

    if (userIdErrMsg.value) {
        alert(userIdErrMsg.value);
    } else if (userPwdErrMsg.value) {
        alert(userPwdErrMsg.value);
    } else if (userNameErrMsg.value) {
        alert(userNameErrMsg.value);
    } else if (emailIdNameErrMsg.value) {
        alert(emailIdNameErrMsg.value);
    } else if (emailDomainErrMsg.value) {
        alert(emailDomainErrMsg.value);
    } else {
        props.type === "modify" ? registUser() : modifyUser();
    }
}

function registUser() {
    console.log("회원가입을 하자!!!", userInfoDto.value);
    registUser(
        userInfoDto.value,
        (response) => {
            let msg = "회원가입 처리시 문제 발생했습니다.";
            if (response.status == 201) msg = "회원가입이 완료되었습니다.";
            alert(msg);
            moveMain();
        },
        (error) => console.log(error)
    );
}

function modifyUser() {
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
    <form>
        <div class="mb-3">
            <label for="username" class="form-label">이름 : </label>
            <input type="text" class="form-control" :placeholder="placeholderValue.userName" />
        </div>

        <div class="mb-3" v-if="type === 'register'">
            <label for="userid" class="form-label">아이디 : </label>
            <input type="text" class="form-control" :placeholder="placeholderValue.userId" />
        </div>

        <div class="mb-3">
            <label for="userpwd" class="form-label">비밀번호 : </label>
            <input type="text" class="form-control" placeholder="비밀번호..." />
        </div>
        <div class="mb-3">
            <label for="pwdcheck" class="form-label">비밀번호확인 : </label>
            <input type="text" class="form-control" id="pwdcheck" placeholder="비밀번호 확인..." />
        </div>
        <div class="mb-3">
            <label for="emailid" class="form-label">이메일 : </label>
            <div class="input-group">
                <input type="text" class="form-control" :placeholder="placeholderValue.emailId" />
                <span class="input-group-text">@</span>
                <select class="form-select" aria-label="이메일 도메인 선택">
                    <option selected>{{ placeholderValue.emailDomain }}</option>
                    <option value="ssafy.com">싸피</option>
                    <option value="google.com">구글</option>
                    <option value="naver.com">네이버</option>
                    <option value="kakao.com">카카오</option>
                </select>
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
