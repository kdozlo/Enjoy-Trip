import { fileURLToPath, URL } from "node:url";

import { defineConfig } from "vite";
import vue from "@vitejs/plugin-vue";

// https://vitejs.dev/config/
export default defineConfig({
    plugins: [vue()],
    resolve: {
        alias: {
            "@": fileURLToPath(new URL("./src", import.meta.url)),
        },
    },
    server: {
        fs: {
            // 여기에 허용할 디렉토리를 추가합니다.
            allow: [
                "/Users/kdozlo/Documents/ssafy/enjoy-trip-kmarble/imgServer",
                "/Users/kdozlo/Documents/ssafy/enjoy-trip-kmarble/enjoyTrip_client",
            ],
        },
    },
});
