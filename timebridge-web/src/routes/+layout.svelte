<script lang="ts">
	import '../app.css';
	import * as Sidebar from '$lib/components/ui/sidebar/index.js';
	import AppSidebar from '$lib/components/custom/sidebar.svelte';
	import { page } from '$app/stores';

	let { children } = $props();
</script>

{#if $page.url.pathname === '/'}
	<!-- Only render this on the root page (/) -->
	{@render children?.()}
{:else}
	<!-- Render this on all other pages -->
	<Sidebar.Provider>
		<AppSidebar/>
		<main class="h-dvh w-full">
			{@render children?.()}
		</main>
	</Sidebar.Provider>
{/if}
