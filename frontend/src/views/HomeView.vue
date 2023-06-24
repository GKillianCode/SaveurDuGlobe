
<script>
import favoriteComponent from "@/components/favoriteComponent.vue";

export default{
    components: {
        favoriteComponent
    },
    data(){
        return {

        }
    }
}
</script>

<template>
    <div>
        <favoriteComponent id="1"></favoriteComponent>
        <favoriteComponent id="2"></favoriteComponent>
        <favoriteComponent id="3"></favoriteComponent>
    </div>
</template>

<style lang="scss">
div{
    margin: 10rem;   
}
</style>