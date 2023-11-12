import { attractionAxios } from "@/util/http-commons";

const attraction = attractionAxios();

function sidoList (param, success, fail) {
    attraction.get(`/areaCode1`, { params: param }).then(success).catch(fail);
  }

  export {
    sidoList,
  };