<script setup>
import { ref, onMounted } from "vue";
import { areaCode1 } from "@/api/attraction.js";
import { areaBasedList1 } from "@/api/attraction.js";
import { searchKeyword1 } from "@/api/attraction.js";
import VKakaoMap from "@/components/common/VKakaoMap.vue";

const { VITE_ATTRACTION_API_SERVICE_KEY } = import.meta.env;

const sidos = ref([]);

const attractionList = ref([]);

const selectStation = ref({});

const searchParam = ref({
    MobileOS: "ETC",
    MobileApp: "EnjoyTrip",
    _type: "json",
    listYN: "Y",
    arrange: "A",
    contentTypeId: "0",
    areaCode: "",
    serviceKey: VITE_ATTRACTION_API_SERVICE_KEY,
    numOfRows: "",
    pageNo: "",
    keyword: "",
});

onMounted(() => {
    getSidoList();
});

const getSidoList = () => {
    console.log("시도 정보를 얻어오자!");
    sidos.value.push({ rnum: null, code: "", name: "검색 할 지역 선택" });
    areaCode1(
        {
            serviceKey: VITE_ATTRACTION_API_SERVICE_KEY,
            MobileOS: "ETC",
            MobileApp: "EnjoyTrip",
            _type: "json",
            numOfRows: "20",
        },
        ({ data }) => {
            console.log("areaCode1 api 호출");
            sidos.value.push(...data.response.body.items.item);
            console.log("시도 정보 획득", sidos.value);
        },
        (error) => {
            console.log(error);
        }
    );
};

const getAttractionList = () => {
    console.log(searchParam.value);
    if (searchParam.value.keyword === "")
        areaBasedList1(
            {
                MobileOS: "ETC",
                MobileApp: "EnjoyTrip",
                _type: "json",
                listYN: "Y",
                arrange: "A",
                contentTypeId: searchParam.value.contentTypeId,
                areaCode: searchParam.value.areaCode,
                serviceKey: VITE_ATTRACTION_API_SERVICE_KEY,
                numOfRows: searchParam.value.numOfRows,
                pageNo: searchParam.value.pageNo,
            },
            ({ data }) => {
                console.log("areaBasedList1 api 호출");

                attractionList.value = data.response.body.items.item;

                console.log("위치 기반 관광지 정보 획득", attractionList.value);
            },
            (error) => {
                console.log(error);
            }
        );
    else
        searchKeyword1(
            searchParam.value,
            ({ data }) => {
                console.log("searchKeyword1 api 호출");
                if (typeof data.response.body.items.item != "undefined") {
                    attractionList.value = data.response.body.items.item;
                } else {
                    attractionList.value.length = 0;
                }
                console.log("키워드 기반 관광지 정보 획득", attractionList.value);
            },
            (error) => {
                console.log(error);
            }
        );
};

const getAttraction = (attraction) => {
    selectStation.value = attraction;
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
                    <select
                        id="search-area"
                        class="form-select me-2"
                        v-model="searchParam.areaCode"
                    >
                        <option
                            v-for="sido in sidos"
                            :key="sido.code"
                            :sido="sido"
                            :value="sido.code"
                            :disabled="sido.code === '' ? true : false"
                            :selected="sido.code === '' ? true : false"
                        >
                            {{ sido.name }}
                        </option>
                    </select>
                    <select
                        id="search-content-id"
                        class="form-select me-2"
                        v-model="searchParam.contentTypeId"
                    >
                        <option :value="0" :disabled="true">관광지 유형</option>
                        <option :value="12">관광지</option>
                        <option :value="14">문화시설</option>
                        <option :value="15">축제공연행사</option>
                        <option :value="25">여행코스</option>
                        <option :value="28">레포츠</option>
                        <option :value="32">숙박</option>
                        <option :value="38">쇼핑</option>
                        <option :value="39">음식점</option>
                    </select>
                    <div class="input-group input-group-sm">
                        <input
                            id="search-keyword"
                            class="form-control"
                            type="search"
                            placeholder="검색어"
                            aria-label="검색어"
                            v-model="searchParam.keyword"
                        />
                        <button
                            id="btn-search"
                            class="btn btn-outline-success"
                            type="button"
                            @click="getAttractionList"
                        >
                            검색
                        </button>
                    </div>
                </form>
            </div>
            <div class="row mb-2">
                <div class="col">
                    <!-- kakao map start -->
                    <VKakaoMap :attractionList="attractionList" :selectStation="selectStation" />
                    <!-- kakao map end -->
                </div>
                <!-- 관광지 검색 start -->
                <div class="col overflow-auto" style="max-height: 700px">
                    <table class="table table-striped">
                        <thead class="text-center">
                            <tr>
                                <th>대표이미지</th>
                                <th>관광지명</th>
                                <th>주소</th>
                                <th>위도</th>
                                <th>경도</th>
                            </tr>
                        </thead>

                        <tbody id="trip-list" v-show="attractionList.length == 0">
                            <tr class="text-center">
                                <td></td>
                                <td>조회 데이터가 없습니다.</td>
                                <td></td>
                                <td></td>
                                <td></td>
                            </tr>
                        </tbody>
                        <tbody id="trip-list" v-show="attractionList.length != 0">
                            <tr
                                class="text-center"
                                v-for="attraction in attractionList"
                                :key="attraction.contentid"
                                :attraction="attraction"
                                @click="getAttraction(attraction)"
                            >
                                <td>
                                    <img :src="attraction.firstimage" style="width: 100px" />
                                </td>
                                <td>{{ attraction.title }}</td>
                                <td>{{ attraction.addr1 }}</td>
                                <td>{{ attraction.mapx }}</td>
                                <td>{{ attraction.mapy }}</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
            <!-- 관광지 검색 end -->
        </div>
    </div>
</template>

<style scoped></style>
