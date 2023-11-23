<script setup>
import { ref } from "vue";
import { storeToRefs } from "pinia";
import { useRouter } from "vue-router";
import { useMemberStore } from "@/stores/member";
import { useMenuStore } from "@/stores/menu";

const router = useRouter();

const memberStore = useMemberStore();

const { isLogin } = storeToRefs(memberStore);
const { userLogin, getUserInfo } = memberStore;
const { changeMenuState } = useMenuStore();

const loginUser = ref({
    userId: "",
    userPwd: "",
});

const login = async () => {
    console.log("login ing!!!! !!!");
    await userLogin(loginUser.value);
    let token = sessionStorage.getItem("accessToken");
    console.log("111. ", token);
    console.log("isLogin: ", isLogin);
    if (isLogin) {
        console.log("로그인 성공아닌가???");
        getUserInfo(token);
        changeMenuState();
    }
    router.push("/");
};

function moveJoin() {
    router.push({ name: "user-join" });
}
</script>

<template>
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-lg-10">
                <div class="container text-center mt-3">
                    <h1>
                        <span>로</span>
                        <span>그</span>
                        <span>인</span>
                        <span></span>
                        <span>&nbsp;</span>
                        <span>해</span>
                        <span>&nbsp;</span>
                        <span>봐</span>
                        <span>요</span>
                        <span>.</span>
                    </h1>
                </div>
            </div>
            <div class="col-lg-6">
                <form>
                    <div class="form-check mb-3 float-end">
                        <input class="form-check-input" type="checkbox" />
                        <label class="form-check-label" for="saveid">
                            아이디저장
                        </label>
                    </div>
                    <div class="mb-3 text-start">
                        <label for="userid" class="form-label">아이디 : </label>
                        <input
                            type="text"
                            class="form-control"
                            v-model="loginUser.userId"
                            placeholder="아이디..."
                        />
                    </div>
                    <div class="mb-3 text-start">
                        <label for="userpwd" class="form-label"
                            >비밀번호 :
                        </label>
                        <input
                            type="password"
                            class="form-control"
                            v-model="loginUser.userPwd"
                            @keyup.enter="login"
                            placeholder="비밀번호..."
                        />
                    </div>
                    <div class="col-auto text-center">
                        <button
                            type="button"
                            class="btn btn-outline-primary mb-3"
                            @click="login"
                        >
                            로그인
                        </button>
                        <button
                            type="button"
                            class="btn btn-outline-success ms-1 mb-3"
                            @click="moveJoin"
                        >
                            회원가입
                        </button>
                    </div>
                </form>
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
