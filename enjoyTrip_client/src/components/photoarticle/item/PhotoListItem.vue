<script setup>
import { ref } from "vue";
const props = defineProps({ article: Object });
console.log("photolistitem - ", props.article);

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
</script>

<template>
    <div class="card" style="width: 18rem">
        <img
            v-if="imgUrl"
            referrerpolicy="no-referrer"
            :src="imgUrl"
            class="card-img-top"
        />
        <div class="card-body">
            <p class="card-text">작성자 : {{ props.article.userName }}</p>
            <p class="card-text">내용 : {{ props.article.content }}</p>
        </div>
    </div>
</template>

<style scoped>
a {
    text-decoration: none;
}
</style>
