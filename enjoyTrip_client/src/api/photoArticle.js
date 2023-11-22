import { localAxios } from "@/util/http-commons";

const local = localAxios();

function listPhotoArticle(param, success, fail) {
    local.get(`/photoarticle`, { params: param }).then(success).catch(fail);
}

function writPhotoArticle(article, success, fail) {
    console.log("boardjs article", article);
    local
        .post(`/photoarticle`, JSON.stringify(article))
        .then(success)
        .catch(fail);
}
export { listPhotoArticle, writPhotoArticle };
