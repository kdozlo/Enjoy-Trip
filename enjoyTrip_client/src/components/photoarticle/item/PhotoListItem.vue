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
            <p class="card-text">
                {{ imgUrl }}
                {{ props.article.content }}
            </p>
        </div>
    </div>
    <!-- <tr class="text-center">
        <th scope="row">{{ article.articleNo }}</th>
        <td class="text-start">
            <router-link
                :to="{
                    name: 'article-view',
                    params: { articleno: article.articleNo },
                }"
                class="article-title link-dark"
            >
                {{ article.subject }}
            </router-link>
        </td>
        <td>{{ article.userName }}</td>
        <td>{{ article.hit }}</td>
        <td>{{ article.registerTime }}</td>
    </tr> -->
</template>

<style scoped>
a {
    text-decoration: none;
}
</style>
