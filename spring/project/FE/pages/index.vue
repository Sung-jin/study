<template>
  <v-card>
    <v-card-title>hi</v-card-title>
    <logo></logo>
  </v-card>
</template>

<script lang="ts">
import { Component, Vue } from 'nuxt-property-decorator'
import Logo from '~/components/Logo.vue'

@Component({
  components: {
    Logo
  }
})
export default class extends Vue {

}
</script>

<style>
</style>
