<script>
export default {
    data() {
        return {
            isActive: false
        }
    },
    props: {
        id: String,
    },
    methods: {
        changeFavorite() {
            const svgHeartVoid = document.querySelector(".icon-"+this.id+"-heart-void");
            const svgHeartFull = document.querySelector(".icon-"+this.id+"-heart-full");

            svgHeartVoid.classList.toggle('icon-disabled');
            svgHeartFull.classList.toggle('icon-disabled');

            this.isActive = !this.isActive;
        }
    }
}
</script>

<template>
    <div class="favorite">
        <svg @click="changeFavorite" class="icon" :class="'icon-'+id+'-heart-void'" fill="none" xmlns="http://www.w3.org/2000/svg">
            <g clip-path="url(#a)">
                <path
                    d="M9.6 17.718c-.205-.076-.212-.081-2.396-1.957-3.477-2.985-4.454-3.909-5.4-5.107C1.195 9.883.627 8.893.367 8.151c-.582-1.659-.466-3.563.31-5.1C1.484 1.452 2.9.378 4.603.07c.514-.093 1.47-.095 1.924-.003 1.328.267 2.402.963 3.365 2.18l.108.135.107-.136c.964-1.216 2.038-1.912 3.366-2.18.454-.09 1.41-.09 1.924.004 1.703.308 3.12 1.382 3.926 2.98.776 1.537.892 3.441.31 5.1-.167.478-.534 1.2-.865 1.701-.747 1.135-1.7 2.163-3.489 3.759-1.1.982-4.563 3.937-4.728 4.034-.14.083-.233.105-.489.115-.204.008-.368-.007-.463-.042Zm2.298-2.729c2.444-2.096 3.375-2.93 4.168-3.735.903-.914 1.23-1.297 1.688-1.98.69-1.024 1.03-2.006 1.073-3.098.09-2.233-1.143-4.173-3.057-4.807-.901-.298-1.95-.278-2.798.055-.72.283-1.453.889-2.013 1.664l-.368.511a.72.72 0 0 1-.591.304.72.72 0 0 1-.591-.304l-.368-.51c-.886-1.228-1.964-1.86-3.284-1.923-2.637-.128-4.7 2.128-4.584 5.01.06 1.51.692 2.836 2.085 4.38.469.52 1.518 1.54 2.264 2.2.958.848 4.447 3.842 4.477 3.842.013 0 .868-.724 1.9-1.609Z"
                    fill="#fff" />
            </g>
            <defs>
                <clipPath id="a">
                    <path fill="#fff" d="M0 0h20v17.762H0z" />
                </clipPath>
            </defs>
        </svg>

        <svg @click="changeFavorite" class="icon icon-disabled" :class="'icon-'+id+'-heart-full'" fill="none"
            xmlns="http://www.w3.org/2000/svg">
            <g clip-path="url(#a)">
                <g clip-path="url(#b)">
                    <path
                        d="M9.599 18.718a1.396 1.396 0 0 1-.293-.166c-.207-.157-3.948-3.372-4.586-3.94-1.313-1.172-2.279-2.151-2.916-2.958C1.195 10.883.627 9.893.367 9.151c-.582-1.659-.466-3.563.31-5.1.807-1.598 2.223-2.672 3.926-2.98.514-.093 1.47-.095 1.924-.003 1.328.267 2.402.963 3.365 2.18l.108.135.107-.136c.964-1.216 2.038-1.912 3.366-2.18.454-.09 1.41-.09 1.924.004 1.703.308 3.119 1.382 3.926 2.98.776 1.537.892 3.441.31 5.1-.26.742-.828 1.73-1.437 2.503-.63.798-1.608 1.79-2.917 2.957-1.114.995-4.564 3.938-4.728 4.035-.14.082-.233.104-.489.114-.205.008-.368-.007-.463-.042Z"
                        fill="#fff" />
                </g>
            </g>
            <defs>
                <clipPath id="a">
                    <path fill="#fff" transform="translate(0 .762)" d="M0 0h20v17.762H0z" />
                </clipPath>
                <clipPath id="b">
                    <path fill="#fff" transform="translate(0 1)" d="M0 0h20v17.762H0z" />
                </clipPath>
            </defs>
        </svg>
    </div>
</template>

<style lang="scss" scoped>
.favorite {
    width: 3rem;
    height: 3rem;
    background-color: var(--favoriteColor);
    border-radius: 50%;
    box-shadow: .1rem .01rem .01rem rgba(#000000, .25);

    position: absolute;
}

.icon {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);

    width: 2rem;
    height: 1.8rem;

    cursor: pointer;

    &-disabled {
        display: none;
    }
}
</style>