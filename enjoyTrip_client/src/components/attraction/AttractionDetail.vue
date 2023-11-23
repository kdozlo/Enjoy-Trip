<script setup>
import { ref, onMounted } from "vue";
import { useMemberStore } from "@/stores/member";
import { storeToRefs } from "pinia";
import { detailCommon1 } from "@/api/attraction";

const { VITE_ATTRACTION_API_SERVICE_KEY } = import.meta.env;

const props = defineProps({ selectStation: Object });
console.log("AttractonDeetail - ", props.selectStation);
const searchParam = ref({
    MobileOS: "ETC",
    MobileApp: "EnjoyTrip",
    _type: "json",
    contentId: props.selectStation.contentid,
    contentTypeId: props.selectStation.contenttypeid,
    defaultYN: "Y",
    firstImageYN: "Y",
    addrinfoYN: "Y",
    overviewYN: "Y",
    serviceKey: VITE_ATTRACTION_API_SERVICE_KEY,
});

const attractionDetail = ref([]);

const getAttractionDetail = () => {
    console.log("관광지 상세 정보를 얻어오자!");

    detailCommon1(
        searchParam.value,
        ({ data }) => {
            console.log("detailCommon1 api 호출");
            console.log("detailCommon1 - ", data.response.body.items.item);
            attractionDetail.value = data.response.body.items.item[0];
            console.log("detailCommon1 - ", attractionDetail.value);
            console.log("시도 정보 획득", attractionDetail.value);
        },
        (error) => {
            console.log(error);
        }
    );
};

onMounted(() => {
    //유저 검증
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

    //관광지 관련 정보 받기
    getAttractionDetail();
});
</script>

<template>
    <div class="black-bg">
        <div class="white-bg">
            <div
                style="width: 60vh; height: 30vh"
                v-if="attractionDetail.firstimage2 != ''"
            >
                <img
                    class="img-responsive center-block"
                    id="img"
                    :src="attractionDetail.firstimage2"
                    style="width: 60%; height: 98%"
                />
            </div>
            <div style="width: 60vh; height: 30vh" v-else>
                <img
                    class="img-responsive center-block"
                    id="img"
                    src="@/assets/no-image-removebg.png "
                    style="width: 60%; height: 98%"
                />
            </div>
            <div class="mb-3 row">
                <div class="col-sm-4">
                    <div class="form-control text-center">관광지명</div>
                </div>
                <div class="col-sm-8">{{ attractionDetail.title }}</div>
            </div>
            <div class="mb-3 row" v-if="attractionDetail.tel != ''">
                <div class="col-sm-4">
                    <div class="form-control text-center">📱</div>
                </div>
                <div class="col-sm-8">{{ attractionDetail.tel }}</div>
            </div>
            <div class="mb-3 row">
                <div class="col-sm-4">
                    <div class="form-control text-center">🏠</div>
                </div>
                <div class="col-sm-8">{{ attractionDetail.addr1 }}</div>
            </div>
            <div class="mb-3 row">
                <div class="col-sm-4">
                    <div class="form-control text-center">👀</div>
                </div>
                <div class="col-sm-8">{{ attractionDetail.overview }}</div>
            </div>
            <div class="col-auto text-center">
                <button
                    class="btn btn-outline-danger mb-7 ms-1"
                    @click="$emit('close-modal')"
                >
                    닫기
                </button>
            </div>
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
    width: 38%;
    height: auto;
    margin: 40px auto;
    background: white;
    border-style: solid;
    border-color: black;
    border-radius: 10px;
    border-width: 2px;
    padding: 20px;
}
</style>
