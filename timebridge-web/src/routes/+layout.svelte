<script lang="ts">
	import '../app.css';
	import * as Sidebar from '$lib/components/ui/sidebar/index.js';
	import AppSidebar from '$lib/components/ui/sidebar.svelte';
	import { ModeWatcher } from "mode-watcher";
	
	let { children } = $props();
	export const ssr = false; // Disable server-side rendering
</script>

<ModeWatcher />
<Sidebar.Provider>
	<AppSidebar />
	<main class="p-2 pl-0 w-full">
		{@render children?.()}
	</main>
</Sidebar.Provider>
