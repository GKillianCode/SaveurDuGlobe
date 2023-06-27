<script>
import categoryCardComponent from './categoryCardComponent.vue';
import accountButtonComponent from './accountButtonComponent.vue';
import themeSwitchComponent from './themeSwitchComponent.vue';
import searchBarComponent from './searchBarComponent.vue';
export default {
    components: {
        categoryCardComponent,
        accountButtonComponent,
        themeSwitchComponent,
        searchBarComponent,
    },
    data() {
        return {

        }
    },
    methods: {
        toggleMenu() {
            const burger = document.querySelector('.menu-burger');
            const pane = document.querySelector('.menu-pane');
            burger.classList.toggle('menu-burger-open')
            pane.classList.toggle('menu-pane-open')
        }
    }
}
</script>

<template>
    <header class="menu">
        <header class="menu-header">
            <div class="menu-container">
                <div @click="toggleMenu" class="menu-burger">
                    <span class="menu-burger-span"></span><span class="menu-burger-span"></span><span
                        class="menu-burger-span"></span>
                </div>
                <router-link to="/" class="menu-title">
                    SaveurDuGlobe
                </router-link>
            </div>
        </header>

        <div class="menu-pane">
            <div class="menu-pane-buttons">
                <accountButtonComponent />
                <themeSwitchComponent />
            </div>

            <search-bar-component class="menu-pane-searchbar searchbar"></search-bar-component>

            <nav class="menu-pane-navigation">
                <ul class="menu-pane-navigation-list">
                    <li v-for="i in 6" :key="i" class="menu-pane-navigation-item">
                        <categoryCardComponent class="categoryCard" :category="parseInt(i)" />
                    </li>
                </ul>
            </nav>
        </div>
    </header>
</template>

<style lang="scss" scoped>
.menu {
    position: sticky;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;

    &-header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        width: 100%;
        height: 8rem;
        background: var(--white);
        box-shadow: 0 .1rem .1rem 0 rgba(0, 0, 0, 0.10);
    }

    &-container {
        width: 100%;
        height: 100%;
        max-width: 100rem;
        padding-left: 2rem;
        padding-right: 2rem;
        display: flex;
        align-items: center;
        gap: 2rem;
    }

    &-title {
        color: var(--black);
        font-size: 2.6rem;
        font-family: var(--logoFont);
        font-weight: 600;
        text-decoration: none;
    }

    &-burger {
        display: flex;
        flex-direction: column;
        gap: .8rem;
        cursor: pointer;

        &-span {
            display: block;
            width: 3rem;
            height: 0.2rem;
            background: var(--black);
            border-radius: 1rem;
            transition: .2s;
        }
    }

    &-burger-open {

        span:first-child,
        span:last-child {
            position: relative;
        }

        span:first-child {
            top: .5rem;
            transform: rotateZ(45deg);
        }

        span:nth-child(2) {
            display: none;
        }

        span:last-child {
            bottom: .5rem;
            transform: rotateZ(-45deg);
        }
    }

    &-pane {
        transition: .2s;

        position: absolute;
        top: 8rem;
        left: -35rem;
        width: 35rem;
        height: calc(100vh - 8rem);

        display: flex;
        flex-direction: column;
        align-items: center;

        border-top: .1rem solid rgba(0, 0, 0, 0.10);
        border-right: .1rem solid rgba(0, 0, 0, 0.10);
        background: var(--white);

        &-navigation {
            margin-top: 4rem;
        }

        &-navigation-list {
            display: flex;
            flex-wrap: wrap;
            justify-content: center;
            gap: 2.5rem;
        }

        &-buttons {
            display: flex;
            justify-content: center;
            align-items: center;
            gap: 5rem;
            margin-top: 2rem;
        }

        &-searchbar {
            width: 31rem;
            height: 3rem;
            margin-top: 3rem;
        }
    }

    &-pane-open {
        left: 0;
    }
}
</style>