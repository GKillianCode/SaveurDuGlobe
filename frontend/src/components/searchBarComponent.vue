<script>
export default {
    date() {
        return {

        }
    }
}
</script>

<template>
    <div class="searchbar">
        <input type="text" id="search" placeholder="Rechercher une recette, un ingrédient ..." class="searchbar-input" />
        <button class="searchbar-button">Rechercher</button>
    </div>
</template>

<style lang="scss" scoped>
.searchbar {
    display: flex;
    justify-content: space-between;
    gap: .2rem;
    padding: .2rem;

    border-radius: 5rem;
    border: .1rem solid rgba(0, 0, 0, 0.10);
    background: #EFEFEF;

    &-input {
        display: inline-block;
        width: 100%;
        background: none;
        border: none;
        font-size: 1.4rem;
        padding-left: 1rem;
    }

    &-button {
        display: flex;
        align-items: center;
        text-align: center;
        height: 100%;
        padding: .2rem 1rem;
        border-radius: 5rem;
        background: var(--mainColor);
        color: #fff;
        border: none;
    }
}
</style>