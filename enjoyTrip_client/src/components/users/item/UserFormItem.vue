<script setup>
import { ref, watch } from "vue";
import { storeToRefs } from "pinia";
import { useRoute, useRouter } from "vue-router";
import { useMemberStore } from "@/stores/member";
// import { registUser, modifyUser } from "@/api/user";

const router = useRouter();
const route = useRoute();

const props = defineProps({ type: String });
const btnMsg = ref("회원가입");

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
    userName: "닉네임...",
    emailId: "이메일 아이디...",
    emailDomain: "선택",
});

if (props.type === "modify") {
    const memberStore = useMemberStore();
    const { userInfo } = storeToRefs(memberStore);
    placeholderValue.value = userInfo.value;
    btnMsg.value = "정보수정";
}

// const subjectErrMsg = ref("");
// const contentErrMsg = ref("");
// watch(
//     () => article.value.subject,
//     (value) => {
//         let len = value.length;
//         if (len == 0 || len > 30) {
//             subjectErrMsg.value = "제목을 확인해 주세요!!!";
//         } else subjectErrMsg.value = "";
//     },
//     { immediate: true }
// );
// watch(
//     () => article.value.content,
//     (value) => {
//         let len = value.length;
//         if (len == 0 || len > 500) {
//             contentErrMsg.value = "내용을 확인해 주세요!!!";
//         } else contentErrMsg.value = "";
//     },
//     { immediate: true }
// );

// function onSubmit() {
//     // event.preventDefault();

//     if (subjectErrMsg.value) {
//         alert(subjectErrMsg.value);
//     } else if (contentErrMsg.value) {
//         alert(contentErrMsg.value);
//     } else {
//         // props.type === "regist" ? registUser() : modifyUser();
//     }
// }

// function registUser() {
//     console.log("글등록하자!!", article.value);
//     registUser(
//         article.value,
//         (response) => {
//             let msg = "글등록 처리시 문제 발생했습니다.";
//             if (response.status == 201) msg = "글등록이 완료되었습니다.";
//             alert(msg);
//             moveList();
//         },
//         (error) => console.log(error)
//     );
// }

// function modifyUser() {
//     console.log(article.value.articleNo + "번글 수정하자!!", article.value);
//     modifyUser(
//         article.value,
//         (response) => {
//             let msg = "글수정 처리시 문제 발생했습니다.";
//             if (response.status == 200) msg = "글정보 수정이 완료되었습니다.";
//             alert(msg);
//             moveList();
//         },
//         (error) => console.log(error)
//     );
// }

// function moveList() {
//     router.push({ name: "article-list" });
// }
</script>

<template>
    <!-- <form @submit.prevent="onSubmit">
        <div class="mb-3">
            <label for="userid" class="form-label">작성자 ID : </label>
            <input
                type="text"
                class="form-control"
                v-model="article.userId"
                :disabled="isUseId"
                placeholder="작성자ID..."
            />
        </div>
        <div class="mb-3">
            <label for="subject" class="form-label">제목 : </label>
            <input
                type="text"
                class="form-control"
                v-model="article.subject"
                placeholder="제목..."
            />
        </div>
        <div class="mb-3">
            <label for="content" class="form-label">내용 : </label>
            <textarea class="form-control" v-model="article.content" rows="10"></textarea>
        </div>
        <div class="col-auto text-center">
            <button type="submit" class="btn btn-outline-primary mb-3" v-if="type === 'regist'">
                글작성
            </button>
            <button type="submit" class="btn btn-outline-success mb-3" v-else>글수정</button>
            <button type="button" class="btn btn-outline-danger mb-3 ms-1" @click="moveList">
                목록으로이동...
            </button>
        </div>
    </form> -->

    <form>
        <div class="mb-3">
            <label for="username" class="form-label">이름 : </label>
            <input type="text" class="form-control" :placeholder="placeholderValue.userName" />
        </div>

        <div class="mb-3" v-if="props.type == 'register'">
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
            <button type="button" class="btn btn-outline-primary mb-3">{{ btnMsg }}</button>
            <button type="button" class="btn btn-outline-success ms-1 mb-3">초기화</button>
        </div>
    </form>
</template>

<style scoped></style>
