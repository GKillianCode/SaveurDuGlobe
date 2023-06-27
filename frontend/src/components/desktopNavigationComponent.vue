
<script>
import AccountButtonComponent from './accountButtonComponent.vue'
import navCategoryCardComponent from './navCategoryCardComponent.vue'
import SearchBarComponent from './searchBarComponent.vue'
import ThemeSwitchComponent from './themeSwitchComponent.vue'

export default {
    components: { 
        navCategoryCardComponent,
        ThemeSwitchComponent,
        AccountButtonComponent,
        SearchBarComponent
    },
}
</script>

<template>
    <header class="menu">
        <div class="menu-topbar">
            <div class="container">
                <router-link to="/" class="menu-topbar-title">SaveurDuGlobe</router-link>

                <search-bar-component class="menu-searchbar"></search-bar-component>
            </div>
        </div>
        <div class="menu-navigation">
            <div class="container">

                <nav class="menu-navigation-nav">
                    <ul class="menu-navigation-list">
                        <li class="menu-navigation-item" v-for="i in 6" :key="i">
                            <navCategoryCardComponent class="menu-navigation-link" :category="parseInt(i)" />
                        </li>
                    </ul>
                </nav>

                <div class="menu-buttons">
                    <theme-switch-component></theme-switch-component>
                    <account-button-component class="menu-buttons-account"></account-button-component>
                </div>

            </div>
        </div>
    </header>
</template>

<style lang="scss" scoped>
.menu-topbar,
.menu-navigation {
    background: var(--white);

    .container {
        display: flex;
        justify-content: space-between;
        align-items: center;

        height: 100%;
    }

    &-nav{
        height: 100%;
    }
}

.menu-topbar {
    height: 8rem;
    border-bottom: .1rem solid rgba(0, 0, 0, 0.10);

    &-title {
        font-size: 3rem;
        font-family: var(--logoFont);
        font-weight: 600;

        text-decoration: none;
        color: var(--black);
    }
}

.menu-navigation {
    height: 6rem;
    box-shadow: 0 .1rem .1rem 0 rgba(0, 0, 0, 0.10);

    &-list{
        height: 100%;
        display: flex;
    }

    &-item{
        display: block;
        height: 100%;
    }
}

.menu-searchbar{
    display: flex;
    align-items: center;
    width: 40rem;
    height: 3rem;
}

.menu-buttons{
    display: flex;
    align-items: center;
    gap: 1.5rem;

    &-account{
        border-left: .1rem solid rgba(0, 0, 0, 0.10);
    }
}
</style>