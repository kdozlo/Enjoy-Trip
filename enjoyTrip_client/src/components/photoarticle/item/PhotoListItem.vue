<script setup>
import { ref } from "vue";
defineProps({ article: Object });

console.log("hihi", article.value);

const imgUrl = ref("");
imgUrl.value =
    "@/../imgServer/" + article.value.saveFolder + "/" + article.value.saveFile;
</script>

<template>
    <div class="card" style="width: 18rem">
        <img
            src="@/../../imgServer/231123/ad09d9f5-5b15-4acc-a9ea-2c71c825ce29.png"
            class="card-img-top"
            alt="..."
        />
        <!-- <img :src="imgUrl" class="card-img-top" alt="..." /> -->
        <div class="card-body">
            <p class="card-text">
                {{ article.content }}
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
