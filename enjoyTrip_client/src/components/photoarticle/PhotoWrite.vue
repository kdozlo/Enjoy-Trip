<script setup>
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { useMemberStore } from "@/stores/member";
import { storeToRefs } from "pinia";
import $ from "jquery";
import { writePhotoArticle, listPhotoArticle } from "@/api/photoArticle";

const router = useRouter();
const route = useRoute();

const photoArticle = ref({
    userId: "",
    content: "",
    file: null,
});
0;
onMounted(() => {
    async (to, from, next) => {
        const memberStore = useMemberStore();
        const { userInfo, isValidToken } = storeToRefs(memberStore);
        const { getUserInfo } = memberStore;

        let token = sessionStorage.getItem("accessToken");

        if (userInfo.value != null && token) {
            await getUserInfo(token);
        }
        if (!isValidToken.value || userInfo.value === null) {
            next({ name: "user-login" });
        } else {
            next();
        }
    };
});

const memberStore = useMemberStore();
const { userInfo } = storeToRefs(memberStore);

photoArticle.value.userId = userInfo.value.userId;

function onSubmit() {
    console.log("포토글을 등록하자!!", photoArticle.value);

    const formData = new FormData();
    const photoArticleDto = {
        userId: photoArticle.value.userId,
        content: photoArticle.value.content,
    };

    formData.append(
        "photoArticleDto",
        new Blob([JSON.stringify(photoArticleDto)], {
            type: "application/json",
        })
    );
    formData.append("file", photoArticle.value.file);
    console.log(photoArticle.value.file);
    console.log(formData);
    writePhotoArticle(
        formData,
        (response) => {
            let msg = "글등록 처리시 문제 발생했습니다.";
            if (response.status == 201) msg = "글등록이 완료되었습니다.";
            getlistPhotoArticle();
            alert(msg);
        },
        (error) => console.log(error)
    );
}

function handleFileChange(event) {
    $("#imagePreview").empty();
    // 파일 선택 시 호출되는 함수
    photoArticle.value.file = event.target.files[0];

    if (!photoArticle.value.file.type.match("image/.*")) {
        alert("이미지 확장자만 업로드 가능합니다.");
        return;
    }
    var reader = new FileReader();
    reader.onload = function (e) {
        var html = `<img src=${e.target.result} style="width: 60%; height: 80%"/>`;
        $("#imagePreview").append(html);
    };
    reader.readAsDataURL(photoArticle.value.file);

    console.log("file - ", photoArticle.value.file);
}
</script>

<template>
    <div class="black-bg">
        <div class="white-bg">
            <form @submit.prevent="onSubmit">
                <div class="mb-3 row">
                    <div class="col-sm-4">
                        <div class="form-control text-center">작성자 ID</div>
                    </div>
                    <div class="col-sm-8">
                        <input
                            type="text"
                            class="form-control text-center"
                            name="userid"
                            :value="photoArticle.userId"
                            readonly
                        />
                    </div>
                </div>
                <div class="mb-3 row">
                    <input type="file" @change="handleFileChange" />
                    <div id="imagePreview" style="width: 60vh; height: 35vh">
                        <img
                            id="img"
                            src="@/assets/no-image-removebg.png "
                            style="width: 60%; height: 80%"
                        />
                    </div>
                </div>
                <div class="mb-3">
                    <textarea
                        class="form-control"
                        name="content"
                        v-model="photoArticle.content"
                        rows="2"
                        placeholder="30자 이내로 내용 입력 해주세요..."
                    ></textarea>
                </div>
                <div class="col-auto text-center">
                    <button type="submit" class="btn btn-outline-primary mb-7">
                        글작성
                    </button>
                    <button
                        class="btn btn-outline-danger mb-7 ms-1"
                        @click="$emit('close-modal')"
                    >
                        닫기
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>

<style scoped>
.black-bg {
    width: 100%;
    height: 100%;
    position: fixed;
    left: 0;
    top: 2%;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    background: rgba(255, 255, 255, 0.25);
    box-shadow: 0 8px 32px 0 rgba(31, 38, 135, 0.37);
    backdrop-filter: blur(1.5px);
    -webkit-backdrop-filter: blur(1.5px);
    border-radius: 10px;
    border: 1px solid rgba(255, 255, 255, 0.18);
    z-index: 10000;
}
.white-bg {
    width: 40%;
    height: 70%;
    margin: 40px;
    background: white;
    border-style: solid;
    border-color: black;
    border-radius: 10px;
    border-width: 2px;
    padding: 20px;
}
</style>
