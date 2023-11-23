<script setup>
import { useMenuStore } from "@/stores/menu";
import { useMemberStore } from "@/stores/member";
import { storeToRefs } from "pinia";

const menuStore = useMenuStore();
const memberStore = useMemberStore();

// 반응형을 유지하면서 스토어에서 속성을 추출하려면, storeToRefs()를 사용
// https://pinia.vuejs.kr/core-concepts/
const { menuList } = storeToRefs(menuStore);
const { changeMenuState } = menuStore;

const { userInfo } = storeToRefs(memberStore);
const { userLogout } = memberStore;

const logout = async () => {
    console.log("로그아웃!!!!");
    console.log(userInfo);
    await userLogout(userInfo.value.userid);
    changeMenuState();
};
</script>
<template>
    <nav class="navbar navbar-expand-lg bg-body-tertiary sticky-top">
        <div class="container-fluid">
            <div class="d-flex justify-content-start align-items-center">
                <router-link
                    :to="{ name: 'main' }"
                    class="navbar-brand text-primary fw-bold"
                >
                    <span>Kmarble </span>
                    <img
                        src="https://raw.githubusercontent.com/Tarikul-Islam-Anik/Animated-Fluent-Emojis/master/Emojis/Travel%20and%20places/Globe%20Showing%20Europe-Africa.png"
                        alt="..."
                        width="60"
                        height="60"
                    />
                    <span> LikeLike</span>
                </router-link>
                <button
                    class="navbar-toggler"
                    type="button"
                    data-bs-toggle="collapse"
                    data-bs-target="#navbarScroll"
                    aria-controls="navbarScroll"
                    aria-expanded="false"
                    aria-label="Toggle navigation"
                >
                    <span class="navbar-toggler-icon"></span>
                </button>
            </div>

            <div class="collapse navbar-collapse" id="navbarScroll">
                <ul
                    class="navbar-nav ms-auto my-2 my-lg-0 navbar-nav-scroll"
                    style="--bs-scroll-height: 100px"
                >
                    <li class="nav-item">
                        <router-link
                            :to="{ name: 'attraction' }"
                            class="nav-link"
                            >지역별관광지</router-link
                        >
                    </li>
                    <li class="nav-item">
                        <router-link
                            :to="{ name: 'photoArticle' }"
                            class="nav-link"
                            >포토게시판</router-link
                        >
                    </li>
                    <li class="nav-item">
                        <router-link :to="{ name: 'board' }" class="nav-link"
                            >자유게시판</router-link
                        >
                    </li>
                    <li class="nav-item dropdown" style="margin-right: 50px">
                        <a
                            class="nav-link dropdown-toggle"
                            href="#"
                            role="button"
                            data-bs-toggle="dropdown"
                            aria-expanded="false"
                        >
                            <img
                                src="@/assets/profile.png"
                                class="rounded mx-auto"
                                alt="..."
                                width="20"
                            />
                        </a>
                        <ul
                            class="dropdown-menu"
                            style="--bs-scroll-height: 100px"
                        >
                            <template
                                v-for="menu in menuList"
                                :key="menu.routeName"
                            >
                                <template v-if="menu.show">
                                    <template
                                        v-if="menu.routeName === 'user-logout'"
                                    >
                                        <li class="nav-item">
                                            <router-link
                                                to="/"
                                                @click.prevent="logout"
                                                class="nav-link"
                                                >{{ menu.name }}</router-link
                                            >
                                        </li>
                                    </template>
                                    <template v-else>
                                        <li class="nav-item">
                                            <router-link
                                                :to="{ name: menu.routeName }"
                                                class="nav-link"
                                                >{{ menu.name }}</router-link
                                            >
                                        </li>
                                    </template>
                                </template>
                            </template>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
</template>

<style scoped>
span {
    position: relative;
    top: 8px;
    display: inline-block;
    font-size: 50px;
    color: #4f89db;
    text-shadow: 0 1px 0 #ccc, 0 2px 0 #ccc, 0 3px 0 #ccc, 0 4px 0 #ccc,
        0 5px 0 #ccc, 0 6px 0 transparent, 0 7px 0 transparent,
        0 8px 0 transparent, 0 9px 0 transparent, 0 10px 10px rgba(0, 0, 0, 0.4);
}
</style>
