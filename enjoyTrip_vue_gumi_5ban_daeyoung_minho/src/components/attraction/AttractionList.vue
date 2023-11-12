<script setup>
import { ref, onMounted } from "vue";
import { areaCode1 } from "@/api/attraction.js";
import { areaBasedList1 } from "@/api/attraction.js";
import { searchKeyword1 } from "@/api/attraction.js";

const { VITE_ATTRACTION_API_SERVICE_KEY } = import.meta.env;

const sidos = ref([]);

const searchParam = ref({
    serviceKey: VITE_ATTRACTION_API_SERVICE_KEY,
    MobileOS: "ETC",
    MobileApp: "EnjoyTrip",
    _type: "json",
    listYN: "Y",
    arrange: "A",
    numOfRows: "",
    pageNo: "",
    areaCode: "",
    contentTypeId: "",
    keyword: "",
});

onMounted(() => {
    getSidoList();
});

const getSidoList = () => {
    console.log("시도 정보를 얻어오자!");
    sidos.value.push({"rnum" : null, "code" : '', "name" : "검색 할 지역 선택"});
    areaCode1(
        {
            serviceKey: VITE_ATTRACTION_API_SERVICE_KEY,
            MobileOS: "ETC",
            MobileApp: "EnjoyTrip",
            _type: "json",
            numOfRows: "20",
        }
    ,
        ({ data }) => {
            sidos.value.push(...data.response.body.items.item);
            console.log("시도 정보 획득", sidos.value);
        },
        (error) => {
            console.log(error);
        }
    );
};
</script>

<script>
import KakaoMap from "./KakaoMap.vue";

export default {
    name: "App",
    components: {
        KakaoMap,
    },
};
</script>

<template>
    <div class="container">
        <div class="row justify-content-center">
            <div class="container text-center mt-3">
                <div class="alert alert-info" role="alert">전국 관광지 정보</div>
            </div>
            <div class="col-lg-10">
                <!-- 관광지 검색 start -->
                <form class="d-flex my-3" onsubmit="return false;" role="search">
                    <select id="search-area" class="form-select me-2">
                        <option v-for="sido in sidos" :key="sido.code" :sido="sido"
                        :disabled="sido.code === '' ? true : false"
                        :selected="sido.code === '' ? true : false">
                            {{ sido.name }}
                        </option>
                    </select>
                    <select id="search-content-id" class="form-select me-2">
                        <option value="0" selected>관광지 유형</option>
                        <option value="12">관광지</option>
                        <option value="14">문화시설</option>
                        <option value="15">축제공연행사</option>
                        <option value="25">여행코스</option>
                        <option value="28">레포츠</option>
                        <option value="32">숙박</option>
                        <option value="38">쇼핑</option>
                        <option value="39">음식점</option>
                    </select>
                    <input id="search-keyword" class="form-control me-2" type="search" placeholder="검색어" aria-label="검색어" />
                    <button id="btn-search" class="btn btn-outline-success" type="button">
                        검색
                    </button>
                </form>
                <!-- kakao map start -->
                <KakaoMap />
                <!-- kakao map end -->
                <!-- 관광지 검색 start -->
                <div class="row">
                    <table class="table table-striped" style="display: none">
                        <thead>
                            <tr>
                                <th>대표이미지</th>
                                <th>관광지명</th>
                                <th>주소</th>
                                <th>위도</th>
                                <th>경도</th>
                            </tr>
                        </thead>
                        <tbody id="trip-list"></tbody>
                    </table>
                </div>
                <!-- 관광지 검색 end -->
            </div>
        </div>
    </div>
</template>

<style scoped></style>
