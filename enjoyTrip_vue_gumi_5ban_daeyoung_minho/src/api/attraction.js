import { attractionAxios } from "@/util/http-commons";

const attraction = attractionAxios();

function areaCode1 (param, success, fail) {
    attraction.get(`/areaCode1`, { params: param }).then(success).catch(fail);
  }

  function areaBasedList1 (param, success, fail) {
    attraction.get(`/areaBasedList1`, { params: param }).then(success).catch(fail);
  }

  function searchKeyword1 (param, success, fail) {
    attraction.get(`/searchKeyword1`, { params: param }).then(success).catch(fail);
  }

  export {
    sidoList,
    areaBasedList1,
    searchKeyword1,
  };