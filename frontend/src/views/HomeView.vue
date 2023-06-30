
<script>
import searchBarComponent from '@/components/searchBarComponent.vue';
import categoryCardComponent from '@/components/categoryCardComponent.vue';
import recipeCardComponent from '@/components/recipeCardComponent.vue';
import footerComponent from '@/components/footerComponent.vue';
// import bookCardComponent from '@/components/bookCardComponent.vue';

export default {
    components: {
        searchBarComponent,
        categoryCardComponent,
        recipeCardComponent,
        footerComponent
        // bookCardComponent
    },
    data() {
        return {

        }
    }
}
</script>

<template>
    <main class="home">
        <section class="section home-banner">

        </section>

        <section class="section home-searchbar">
            <header class="section-header">
                <div class="container">
                    <h2 class="section-header-title">
                        Rechercher
                    </h2>
                </div>
            </header>

            <div class="container">
                <searchBarComponent class="searchbar"></searchBarComponent>

                <nav class="home-searchbar-nav">
                    <ul class="home-searchbar-list">
                        <li v-for="i in 6" :key="i" class="home-searchbar-item">
                            <categoryCardComponent class="categoryCard" :category="parseInt(i)" />
                        </li>
                    </ul>
                </nav>
            </div>
        </section>

        <section class="section home-recipe-popular">
            <header class="section-header">
                <div class="container">
                    <h2 class="section-header-title">
                        Recettes populaires
                    </h2>
                </div>
            </header>

            <div class="container">
                <div class="cardSlider">
                    <div class="cardSlider-buttons">
                        <div class="cardSlider-buttons-button cardSlider-beforebtn">
                            <img src="../assets/icons/arrow.svg" alt="Icon flèche">
                        </div>
                        <div class="cardSlider-buttons-button cardSlider-afterbtn">
                            <img src="../assets/icons/arrow.svg" alt="Icon flèche">
                        </div>
                    </div>

                    <div class="cardSlider-recipes">
                        <div class="cardSlider-recipes-content">
                            <recipeCardComponent v-for="i in 6" :key="i"></recipeCardComponent>
                        </div>
                    </div>
                </div>
            </div>
        </section>

        <section class="section home-recipe-speed">
            <header class="section-header">
                <div class="container">
                    <h2 class="section-header-title">
                        Recettes rapides
                    </h2>
                </div>
            </header>

            <div class="container">
                <div class="cardSlider">
                    <div class="cardSlider-buttons">
                        <div class="cardSlider-buttons-button cardSlider-beforebtn">
                            <img src="../assets/icons/arrow.svg" alt="Icon flèche">
                        </div>
                        <div class="cardSlider-buttons-button cardSlider-afterbtn">
                            <img src="../assets/icons/arrow.svg" alt="Icon flèche">
                        </div>
                    </div>
                    <div class="cardSlider-recipes">
                        <div class="cardSlider-recipes-content">
                            <recipeCardComponent v-for="i in 6" :key="i"></recipeCardComponent>
                        </div>
                    </div>
                </div>
            </div>
        </section>

        <!-- <section class="section home-books">
            <header class="section-header">
                <div class="container">
                    <h2 class="section-header-title">
                        Carnets de recettes populaires
                    </h2>
                </div>
            </header>

            <div class="container">
                <div class="cardSlider">
                    <div class="cardSlider-buttons">
                        <div class="cardSlider-buttons-button cardSlider-beforebtn">
                            <img src="../assets/icons/arrow.svg" alt="Icon flèche">
                        </div>
                        <div class="cardSlider-buttons-button cardSlider-afterbtn">
                            <img src="../assets/icons/arrow.svg" alt="Icon flèche">
                        </div>
                    </div>

                    <div class="cardSlider-recipes">
                        <div class="cardSlider-recipes-content">
                            <bookCardComponent v-for="i in 6" :key="i"></bookCardComponent>
                        </div>
                    </div>
                </div>
            </div>
        </section> -->

        <!-- Section PUB -->

        <section class="section home-recipe-season">
            <header class="section-header">
                <div class="container">
                    <h2 class="section-header-title">
                        Recettes de saison
                    </h2>
                </div>
            </header>

            <div class="container">
                <div class="home-recipe-season-recipes">
                    <recipeCardComponent v-for="i in 8" :key="i"></recipeCardComponent>
                </div>
                <button class="home-recipe-season-button">Voir d’avantage de recettes</button>
            </div>
        </section>

        <footerComponent></footerComponent>
    </main>
</template>

<style lang="scss">
.home {

    &-banner {
        width: 100%;
        height: 32rem;
        background: url("../assets/banner.png");
        background-size: cover;
        background-position: center;
    }

    @media screen and (max-width: 102.4rem) {
        &-banner {
            height: 30rem;
        }
    }

    @media screen and (max-width: 80rem) {
        &-banner {
            height: 15rem;
        }
    }

    .searchbar {
        height: 4rem;
        background: #e0e0e0;

        button {
            padding-left: 3rem;
            padding-right: 3rem;
        }
    }

    &-searchbar {
        display: flex;
        flex-direction: column;

        &-nav {
            margin-top: 5rem;
        }

        &-list {
            display: flex;
            flex-direction: row;
            flex-wrap: wrap;
            gap: 2.5rem;
        }
    }

    &-recipe-popular,
    &-recipe-speed,
    &-books {
        .container {
            display: flex;
        }
    }

    &-recipe-season {
        &-recipes {
            width: 100%;
            display: flex;
            flex-direction: row;
            flex-wrap: wrap;
            gap: 2.5rem;
        }

        &-button {
            display: block;
            margin-left: auto;
            margin-right: auto;
            margin-top: 3rem;
            background-color: var(--mainColor);
            border-radius: .8rem;
            color: #FFF;
            padding: 1.3rem 1.6rem;
            border: none;
        }
    }
}
</style>