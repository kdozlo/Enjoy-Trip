<script setup></script>

<template>
    <div class="container text-center mt-3" style="width: 100vw; height: 80vh">
        <div class="alert alert-info" role="alert">여행을 떠나 봐요</div>
        <img src="@/assets/main.jpg" class="img-fluid mx-auto d-block" />

        <router-view></router-view>
    </div>
</template>

<style scoped></style>
