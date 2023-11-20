import { localAxios } from "@/util/http-commons";

const local = localAxios();

async function userConfirm(param, success, fail) {
    console.log("param", param);
    await local.post(`/memberapi/login`, param).then(success).catch(fail);
    console.log("userConfirm ok");
}

async function findById(userid, success, fail) {
    local.defaults.headers["Authorization"] = sessionStorage.getItem("accessToken");
    await local.get(`/memberapi/info/${userid}`).then(success).catch(fail);
}

async function tokenRegeneration(user, success, fail) {
    local.defaults.headers["refreshToken"] = sessionStorage.getItem("refreshToken"); //axios header에 refresh-token 셋팅
    await local.post(`/memberapi/refresh`, user).then(success).catch(fail);
}

async function logout(userid, success, fail) {
    await local.get(`/memberapi/logout/${userid}`).then(success).catch(fail);
}

async function registUser(user, success, fail) {
    local.defaults.headers["Authorization"] = sessionStorage.getItem("accessToken");
    await local.post(`/memberapi/join`, user).then(success).catch(fail);
}

async function modifyUser(user, success, fail) {
    local.defaults.headers["Authorization"] = sessionStorage.getItem("accessToken");
    await local.put(`/memberapi/updatemember`, user).then(success).catch(fail);
}

export { userConfirm, findById, tokenRegeneration, logout, registUser, modifyUser };
