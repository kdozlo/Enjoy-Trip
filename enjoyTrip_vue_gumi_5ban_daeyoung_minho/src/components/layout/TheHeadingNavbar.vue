<template>
    <nav class="navbar navbar-expand-lg bg-body-tertiary sticky-top">
        <div class="container-fluid">
            <div class="d-flex justify-content-start align-items-center">
                <router-link :to="{ name: 'main' }" class="navbar-brand text-primary fw-bold">
                    <img src="@/assets/logo.jpg" class="rounded mx-auto" alt="..." width="80" />
                    LikeLike
                </router-link>
                <button
                    class="navbar-toggler"
                    type="button"
                    data-bs-toggle="collapse"
                    data-bs-target="#navbarScroll"
                    aria-controls="navbarScroll"
                    aria-expanded="false"
                    aria-label="Toggle navigation"
                >
                    <span class="navbar-toggler-icon"></span>
                </button>
            </div>
            <div class="collapse navbar-collapse" id="navbarScroll">
                <ul
                    class="navbar-nav ms-auto my-2 my-lg-0 navbar-nav-scroll"
                    style="--bs-scroll-height: 100px"
                >
                    <li class="nav-item">
                        <router-link :to="{ name: 'attraction' }" class="nav-link"
                            >지역별관광지</router-link
                        >
                    </li>
                    <li class="nav-item">
                        <router-link :to="{ name: 'board' }" class="nav-link"
                            >여행정보공유</router-link
                        >
                    </li>
                    <li class="nav-item dropdown" style="margin-right: 50px">
                        <a
                            class="nav-link dropdown-toggle"
                            href="#"
                            role="button"
                            data-bs-toggle="dropdown"
                            aria-expanded="false"
                        >
                            <img
                                src="@/assets/profile.png"
                                class="rounded mx-auto"
                                alt="..."
                                width="20"
                            />
                        </a>
                        <ul class="dropdown-menu">
                            <li><a class="dropdown-item" href="#">로그인</a></li>
                            <li><hr class="dropdown-divider" /></li>
                            <li><a class="dropdown-item" href="#">회원가입</a></li>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
</template>
