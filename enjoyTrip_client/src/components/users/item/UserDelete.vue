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

    await deleteUser(
        { data: loginUser.value },
        (response) => {
            let msg = "회원탈퇴 과정에서 문제가 생겼습니다.";
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
        (error) => console.log(error)
    );
};
</script>

<template>
    <div class="black-bg">
        <div class="white-bg">
            <div class="row justify-content-center" id="modal-content">
                <div class="col-lg-10">
                    <h2 class="my-3 py-3 shadow-sm bg-light text-center">
                        <mark class="orange">회원 탈퇴</mark>
                    </h2>
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
    background: rgba(0, 0, 0, 0.519);
    position: fixed;
    z-index: 1000;
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
</style>
