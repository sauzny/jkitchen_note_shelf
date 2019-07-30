<template id="thread-view">
    <div>{{ $javalin.pathParams["users"] }}</div>
</template>
<script>
    Vue.component("thread-view", {
        template: "#thread-view"
    });
</script>