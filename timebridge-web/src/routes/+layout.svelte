<script lang="ts">
	import '../app.css';
	import * as Sidebar from '$lib/components/ui/sidebar/index.js';
	import AppSidebar from '$lib/components/sidebar.svelte';
	import { ModeWatcher } from 'mode-watcher';
	import { page } from '$app/stores';

	let { children } = $props();
	export const ssr = false; // Disable server-side rendering
</script>

{#if $page.url.pathname === '/'}
	<!-- Only render this on the root page (/) -->
	<ModeWatcher />
	{@render children?.()}
{:else}
	<!-- Render this on all other pages -->
	<ModeWatcher />
	<Sidebar.Provider>
		<AppSidebar />
		<main class="h-dvh p-2 pl-0">
			{@render children?.()}
		</main>
	</Sidebar.Provider>
{/if}
