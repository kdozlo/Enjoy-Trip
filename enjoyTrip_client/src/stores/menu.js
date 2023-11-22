import { ref } from "vue";
import { defineStore } from "pinia";

export const useMenuStore = defineStore("menuStore", () => {
    const menuList = ref([
        { name: "회원가입", show: true, routeName: "user-join" },
        { name: "로그인", show: true, routeName: "user-login" },
        { name: "내정보", show: false, routeName: "user-mypage" },
        { name: "로그아웃", show: false, routeName: "user-logout" },
    ]);

    if (
        localStorage.getItem("menuList") == null ||
        sessionStorage.getItem("memberStore") == null
    )
        localStorage.setItem("menuList", JSON.stringify(menuList.value));

    const changeMenuState = () => {
        menuList.value = menuList.value.map((item) => ({
            ...item,
            show: !item.show,
        }));
        localStorage.setItem("menuList", JSON.stringify(menuList.value));
    };

    console.log(JSON.parse(localStorage.getItem("menuList")));
    menuList.value = JSON.parse(localStorage.getItem("menuList"));

    return {
        menuList,
        changeMenuState,
    };
});
