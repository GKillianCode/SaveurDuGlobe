<script>
import favoriteComponent from "@/components/favoriteComponent.vue";
import noteCardComponent from "./noteCardComponent.vue";

export default{
    props: {
        image: String,
        imageDescription: String
    },
    components: {
        favoriteComponent,
        noteCardComponent
    },
    data(){
        return{
            rating: 0,
        }
    }
}
</script>

<template>
    <div class="card" :style="`background-image: url(${image});`">
        <noteCardComponent :rating="rating" />
        <favoriteComponent class="card-favorite" id="1" />
        <div class="card-name">
            <p>Ma super recette</p>
        </div>
    </div>
</template>

<style lang="scss" scoped>
.card{
    position: relative;
    width: 25.5rem;
    height: 24rem;
    border-radius: 2rem;
    box-shadow: 0 0 .4rem rgba(#000000, .5);
    background-size: cover;
    background-position: center;

    &-favorite{
        position: absolute;
        top: .8rem;
        right: .8rem;
    }

    &-name{
        position: absolute;
        left: 0;
        bottom: 0;
        width: 100%;
        padding: 1rem;
        background: rgba(17, 17, 17, 0.45);
        border-radius: 0 0 2rem 2rem;
        color: #fff;
        box-shadow: 0px -1px 2px 0px rgba(0, 0, 0, 0.25);
    }
}
</style>