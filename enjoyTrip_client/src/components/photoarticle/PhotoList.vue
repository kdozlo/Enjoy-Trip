<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { listPhotoArticle, bestListPhotoArticle } from "@/api/photoArticle.js";
import photoWrite from "@/components/photoarticle/PhotoWrite.vue";

import VSelect from "@/components/common/VSelect.vue";
import PhotoListItem from "@/components/photoarticle/item/PhotoListItem.vue";
import PageNavigation from "@/components/common/PageNavigation.vue";

const router = useRouter();

onMounted(() => {
    getListPhotoArticle();
});

const showModal = ref(false);

const selectOption = ref([
    { text: "검색조건", value: "" },
    { text: "내용", value: "content" },
    { text: "작성자아이디", value: "user_id" },
]);

const articles = ref([]);
const currentPage = ref(1);
const totalPage = ref(0);
const { VITE_ARTICLE_LIST_SIZE } = import.meta.env;
const param = ref({
    pgno: currentPage.value,
    spp: 9,
    key: "",
    word: "",
});

const changeKey = (val) => {
    console.log("BoarList에서 선택한 조건 : " + val);
    param.value.key = val;
};

const getListPhotoArticle = () => {
    console.log("서버에서 글목록 얻어오자!!!", param.value);
    listPhotoArticle(
        param.value,
        ({ data }) => {
            articles.value = data.photoArticles;
            currentPage.value = data.currentPage;
            totalPage.value = data.totalPageCount;
        },
        (error) => {
            console.log(error);
        }
    );
};

const onPageChange = (val) => {
    console.log(val + "번 페이지로 이동 준비 끝!!!");
    currentPage.value = val;
    param.value.pgno = val;
    getListPhotoArticle();
};

const moveWrite = () => {
    showModal.value = true;
};
</script>

<template>
    <photoWrite v-if="showModal" @close-modal="showModal = false"></photoWrite>
    <div class="container" style="width: 100vw">
        <div class="row justify-content-center">
            <div class="col-lg-10">
                <div class="container text-center mt-3">
                    <h1>
                        <span>포</span>
                        <span>토</span>
                        <span>를</span>
                        <span>&nbsp;</span>
                        <span>올</span>
                        <span>려</span>
                        <span>&nbsp;</span>
                        <span>봐</span>
                        <span>요</span>
                        <span>.</span>
                    </h1>
                </div>
            </div>
            <div class="col-lg-10">
                <div class="row align-self-center mb-2">
                    <div class="col-md-2 text-start">
                        <button
                            type="button"
                            class="btn btn-outline-success"
                            @click="moveWrite"
                        >
                            글쓰기
                        </button>
                    </div>
                    <div class="col-md-5 offset-5">
                        <form class="d-flex">
                            <VSelect
                                :selectOption="selectOption"
                                @onKeySelect="changeKey"
                            />
                            <div class="input-group input-group-sm">
                                <input
                                    type="text"
                                    class="form-control"
                                    v-model="param.word"
                                    placeholder="검색어..."
                                />
                                <button
                                    class="btn btn-outline-success"
                                    type="button"
                                    @click="getListPhotoArticle"
                                >
                                    검색
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
                <div class="container-fluid">
                    <div class="row gy-4 justify-content-center">
                        <PhotoListItem
                            v-for="article in articles"
                            :key="article.photoArticleId"
                            :article="article"
                        ></PhotoListItem>
                    </div>
                </div>
            </div>
            <div class="mt-5">
                <PageNavigation
                    :current-page="currentPage"
                    :total-page="totalPage"
                    @pageChange="onPageChange"
                ></PageNavigation>
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
