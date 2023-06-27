<script>
export default{
    data(){
        return{
            
        }
    }
}
</script>

<template>
    <router-link to="/" class="ab">
        <div class="ab-content">
            <div class="ab-icon">
                <svg viewBox="0 0 109.999 126.843" xml:space="preserve" xmlns="http://www.w3.org/2000/svg"><path style="stroke-width:.264583" d="M228.596-273.763c-.483-.238-1.197-.851-1.587-1.362l-.71-.93-.08-11.927c-.087-12.745.053-15.129 1.127-19.261 3.015-11.607 12.556-21.148 24.163-24.163 4.485-1.166 5.952-1.218 31.412-1.113 23.351.097 24.144.117 26.326.653 7.12 1.751 12.009 4.426 16.819 9.203 5.635 5.597 9.022 12.543 9.92 20.342.167 1.453.245 7.159.197 14.34l-.081 11.927-.713.934c-1.498 1.963 3.332 1.803-54.228 1.796-47.314-.006-51.76-.043-52.565-.439zm49.694-67.41c-.218-.052-1.29-.24-2.38-.418-7.334-1.192-14.948-6.116-19.134-12.373-6.117-9.144-6.863-20.056-2.038-29.818 4.176-8.45 11.433-13.985 21.039-16.048 2.044-.44 8.787-.442 10.848-.004 10.955 2.326 19.399 9.774 22.7 20.021 1.122 3.484 1.362 5.094 1.362 9.128 0 4.035-.24 5.644-1.362 9.128-3.245 10.073-11.627 17.6-22.134 19.876-1.736.376-7.973.732-8.9.508z" transform="translate(-226.198 400.16)"/></svg>
            </div>

            <p class="ab-name">
                Se connecter
            </p>
        </div>
    </router-link>
</template>

<style lang="scss" scoped>
.ab{
    display: block;
    min-width: 11.1rem;
    padding-left: 1.4rem;
    margin-right: 1.4rem;
    height: 6rem;

    text-decoration: none;
    color: var(--black);

    &-content{
        width: 100%;
        height: 100%;
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
    }

    &-icon{
        display: flex;
        justify-content: center;
        align-items: center;

        width: 3rem;
        height: 3rem;
        padding: .6rem;
        background: var(--gray);
        border-radius: 50%;
        transition: .2s;

        svg{
            width: 100%;
            height: 100%;
            fill: var(--mainColor);
            transition: .2s;
        }
    }

    &:hover &-icon{
        background: var(--mainColor);
        svg{
            fill: var(--gray);
        }
    }
}
</style>