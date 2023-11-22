import { localAxios } from "@/util/http-commons";

const local = localAxios();

function listPhotoArticle(param, success, fail) {
    local.get(`/photoarticle`, { params: param }).then(success).catch(fail);
}

function writePhotoArticle(article, success, fail) {
    console.log("boardjs article", article);
    local.defaults.headers["Content-Type"] = "multipart/form-data";
    local.post(`/photoarticle`, article).then(success).catch(fail);
}
export { listPhotoArticle, writePhotoArticle };
