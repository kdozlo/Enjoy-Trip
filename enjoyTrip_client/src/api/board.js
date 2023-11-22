import { localAxios } from "@/util/http-commons";

const local = localAxios();

function listArticle(param, success, fail) {
    local.get(`/board`, { params: param }).then(success).catch(fail);
}

function detailArticle(articleno, success, fail) {
    local.get(`/board/${articleno}`).then(success).catch(fail);
}

function registArticle(article, success, fail) {
    console.log("boardjs article", article);
    local.post(`/board`, article).then(success).catch(fail);
}

function getModifyArticle(articleno, success, fail) {
    local.get(`/board/modify/${articleno}`).then(success).catch(fail);
}

function modifyArticle(article, success, fail) {
    local.defaults.headers["Authorization"] =
        sessionStorage.getItem("accessToken");
    local.put(`/board`, JSON.stringify(article)).then(success).catch(fail);
}

function deleteArticle(userid, articleno, success, fail) {
    local.delete(`/board/${userid}/${articleno}`).then(success).catch(fail);
}

export {
    listArticle,
    detailArticle,
    registArticle,
    getModifyArticle,
    modifyArticle,
    deleteArticle,
};
