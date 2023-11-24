<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import { useMemberStore } from "@/stores/member";
import { photoarticlelikes } from "@/api/photoArticle.js";
import { storeToRefs } from "pinia";

const props = defineProps({ article: Object });
console.log("photolistitem - ", props.article);

const router = useRouter();

const memberStore = useMemberStore();
const { userInfo } = storeToRefs(memberStore);

const imgUrl = ref("");
// imgUrl.value =
//     "@/../../imgServer/" +
//     props.article.saveFolder +
//     "/" +
//     props.article.saveFile;

imgUrl.value =
    "/@fs/Users/kdozlo/Documents/ssafy/enjoy-trip-kmarble/imgServer/" +
    props.article.saveFolder +
    "/" +
    props.article.saveFile;
console.log("imgUrl - ", imgUrl.value);

const clickHeart = () => {
    photoarticlelikes(
        {
            userId: userInfo.value.userId,
            photoArticleId: props.article.photoArticleId,
        },
        ({ data }) => {},
        (error) => {
            console.log(error);
        }
    );
    router.go();
};
</script>

<template>
    <div class="card" style="width: 18rem">
        <img
            v-if="imgUrl"
            referrerpolicy="no-referrer"
            :src="imgUrl"
            class="card-img-top"
            @click="clickHeart"
        />
        <div class="card-body">
            <p class="card-text">작성자 : {{ props.article.userName }}</p>
            <p class="card-text">내용 : {{ props.article.content }}</p>
            <p class="card-text">❤️ : {{ props.article.likesCount }}</p>
        </div>
    </div>
</template>

<style scoped>
a {
    text-decoration: none;
}
</style>
