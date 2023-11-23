<script setup>
import { ref } from "vue";
import { deleteUser } from "@/api/user";
import { useMenuStore } from "@/stores/menu";
import { useRoute, useRouter } from "vue-router";

const route = useRoute();
const router = useRouter();

const menuStore = useMenuStore();
const { changeMenuState } = menuStore;

const userInfo = ref({});
userInfo.value = JSON.parse(sessionStorage.getItem("memberStore")).userInfo;

const loginUser = ref({
    userId: userInfo.value.userId,
    userPwd: "",
});

const removeUser = async () => {
    //user delete
    console.log("removeUser - ", loginUser.value);
    let msg;
    await deleteUser(
        { data: loginUser.value },
        (response) => {
            msg = "회원탈퇴 과정에서 문제가 생겼습니다.";
            if (loginUser.value.userPwd === "")
                msg = "비밀번호를 입력해 주세요.";
            console.log(response);
            if (response.status == 202) {
                msg = "회원탈퇴가 완료되었습니다.";
                //session storage - accessToken, refreshToken, memberStore clear
                sessionStorage.clear("accessToken");
                sessionStorage.clear("refreshToken");
                sessionStorage.clear("memberStore");

                //local storage -  menuList change
                changeMenuState();

                //move main page
                router.push("/");
            }
            alert(msg);
        },
        (error) => {
            console.log(error);
            msg = "회원 정보가 잘못 되었습니다.";
            alert(msg);
        }
    );
};
</script>

<template>
    <div class="black-bg">
        <div class="white-bg">
            <div class="row justify-content-center" id="modal-content">
                <div class="col-lg-10">
                    <div class="container text-center mt-3">
                        <h1>
                            <span>탈</span>
                            <span>퇴</span>
                            <span>를</span>
                            <span>&nbsp;</span>
                            <span>해</span>
                            <span>&nbsp;</span>
                            <span>보</span>
                            <span>봐</span>
                            <span>요</span>
                            <span>.</span>
                        </h1>
                    </div>
                </div>
                <div class="col-lg-10">
                    <form>
                        <div class="mb-3 text-start">
                            <label for="userid" class="form-label"
                                >아이디 :
                            </label>
                            <input
                                type="text"
                                class="form-control"
                                :value="userInfo.userId"
                                readonly
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
                                btn
                                class="btn btn-outline-danger mb-3"
                                @click="removeUser"
                            >
                                탈퇴하기
                            </button>
                            <button
                                class="btn btn-outline-primary ms-1 mb-3"
                                @click="$emit('close-modal')"
                            >
                                닫기
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped>
.black-bg {
    width: 100%;
    height: 100%;
    position: fixed;
    left: 0;
    top: 2%;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    background: rgba(255, 255, 255, 0.25);
    box-shadow: 0 8px 32px 0 rgba(31, 38, 135, 0.37);
    backdrop-filter: blur(1.5px);
    -webkit-backdrop-filter: blur(1.5px);
    border-radius: 10px;
    border: 1px solid rgba(255, 255, 255, 0.18);
    z-index: 10000;
}

.white-bg {
    width: 50%;
    margin: 40px auto;
    background: white;
    border-style: solid;
    border-color: black;
    border-radius: 10px;
    border-width: 2px;
    padding: 20px;
}

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
