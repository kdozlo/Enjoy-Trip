<script setup>
import { ref, onMounted } from "vue";
import { bestListPhotoArticle } from "@/api/photoArticle.js";
import PhotoListItem from "@/components/photoarticle/item/PhotoListItem.vue";

const bestarticles = ref([]);

onMounted(() => {
    getBestListPhotoArticle();
});

const getBestListPhotoArticle = () => {
    bestListPhotoArticle(
        ({ data }) => {
            console.log("data.photoArticles - ", data.photoArticles);
            bestarticles.value = data.photoArticles;
        },
        (error) => {
            console.log(error);
            bestarticles.value = error.data.photoArticles;
        }
    );
};

console.log("bestarticles - ", bestarticles.value);
</script>

<template>
    <div class="container text-center mt-3" style="width: 100vw">
        <div>
            <h1>
                <span>여</span>
                <span>행</span>
                <span>을</span>
                <span>&nbsp;</span>
                <span>떠</span>
                <span>나</span>
                <span>&nbsp;</span>
                <span>봐</span>
                <span>요</span>
                <span>.</span>
            </h1>
        </div>
        <img src="@/assets/main.jpg" class="img-fluid mx-auto d-block" />

        <div class="mt-5">
            <h4 class="text-secondary">[ 포토 게시판 인기글 ]</h4>
        </div>

        <div class="container-fluid">
            <div class="row gy-4 justify-content-center">
                <PhotoListItem
                    v-for="article in bestarticles"
                    :key="article.photoArticleId"
                    :article="article"
                ></PhotoListItem>
            </div>
        </div>
        <router-view></router-view>
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
