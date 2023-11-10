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
        <div style="height: 70px"></div>
        <div class="row">
            <div class="col-md-9">
                <div class="alert alert-primary mt-3 text-center fw-bold" role="alert">
                    전국 관광지 정보
                </div>
                <!-- 관광지 검색 start -->
                <form class="d-flex my-3" onsubmit="return false;" role="search">
                    <select id="search-area" class="form-select me-2">
                        <option value="0" selected>검색 할 지역 선택</option>
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
                    <input
                        id="search-keyword"
                        class="form-control me-2"
                        type="search"
                        placeholder="검색어"
                        aria-label="검색어"
                    />
                    <button id="btn-search" class="btn btn-outline-success" type="button">
                        검색
                    </button>
                </form>
                <!-- kakao map start -->
                <!-- <div id="map" class="mt-3" style="width: 100%; height: 400px"></div> -->
                <KakaoMap />
                <!-- kakao map end -->
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
