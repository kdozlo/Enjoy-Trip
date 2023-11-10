<script setup>
import { RouterView } from "vue-router";
import TheHeadingNavbar from "@/components/layout/TheHeadingNavbar.vue";
import Footer from "@/components/layout/Footer.vue";
</script>

<template>
    <div>
        <TheHeadingNavbar></TheHeadingNavbar>
        <router-view></router-view>
        <Footer></Footer>
    </div>
</template>

<style scoped>
Footer {
    /* footer를 aside위에 올리기 위해 사용(부유객체) */
    position: absolute;
    height: 60px;
    width: 100%;
    padding: 0 25px;
    line-height: 60px;
    /* border-top: 1px solid #dee5e7; */
}
</style>
