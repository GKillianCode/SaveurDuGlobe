<script>
export default {
    props: {
        rating: {
            type: Number,
            required: true,
            validator(value) {
                return value >= 0 && value <= 5;
            },
        },
    },
    data() {
        return {

        }
    },
    computed: {
        stars() {
            const fullStarCount = Math.floor(this.rating);
            const halfStarCount = this.rating - fullStarCount === 0.5 ? 1 : 0;
            const emptyStarCount = 5 - fullStarCount - halfStarCount;

            const stars = [];
            for (let i = 0; i < fullStarCount; i++) {
                stars.push(require('@/assets/stars/star.svg'));
            }
            for (let i = 0; i < halfStarCount; i++) {
                stars.push(require('@/assets/stars/halfStar.svg'));
            }
            for (let i = 0; i < emptyStarCount; i++) {
                stars.push(require('@/assets/stars/voidStar.svg'));
            }
            return stars;
        },
    },
}
</script>

<template>
    <div class="note">
        <div class="note-content">
            <div class="note-stars">
                <img v-for="star in stars" :key="star" :src="star" alt="Star" />
            </div>
            <p class="note-nb-note">3137 avis</p>
        </div>
    </div>
</template>

<style lang="scss" scoped>
.note {
    width: 13rem;
    height: 6rem;

    border-radius: 2rem 0;
    background: rgba(217, 217, 217, 0.5);
    box-shadow: 0 .2rem .2rem 0 rgba(0, 0, 0, 0.1);
    backdrop-filter: blur(.2rem);

    display: flex;
    flex-direction: column;
    justify-items: center;
    align-items: center;

    &-stars {
        display: flex;
        padding-top: 1.5rem;
        gap: .2rem;
    }

    &-nb-note {
        color: var(--mainColor);
    }
}
</style>