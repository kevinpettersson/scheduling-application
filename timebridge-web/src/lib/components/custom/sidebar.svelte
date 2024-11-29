<script lang="ts">
	import * as Tabs from '$lib/components/ui/tabs/index.js';
	import * as Sidebar from '$lib/components/ui/sidebar/index.js';
	import ModeToggle from './mode.svelte';
	import { Button, buttonVariants } from '$lib/components/ui/button/index.js';
	import Separator from '$lib/components/ui/separator/separator.svelte';
	import FilterSettings from './filter-settings.svelte';
	import FormatSettings from './format-settings.svelte';
	import { downloadCalendar } from '$lib/api.svelte';
</script>

<Sidebar.Root variant="floating">
	<Sidebar.Content class="p-2">
		<Tabs.Root value="filter">
			<Tabs.List class="grid w-full grid-cols-2">
				<Tabs.Trigger value="filter">Filter</Tabs.Trigger>
				<Tabs.Trigger value="format">Format</Tabs.Trigger>
			</Tabs.List>
			<Tabs.Content value="filter">
				<FilterSettings />
			</Tabs.Content>
			<Tabs.Content value="format">
				<FormatSettings />
			</Tabs.Content>
		</Tabs.Root>
	</Sidebar.Content>
	<Sidebar.Footer class="flex-row items-center">
		<ModeToggle />
		<Separator orientation="vertical" class="h-5/6 min-h-px" />
		<button on:click={downloadCalendar} class={`${buttonVariants({ variant: "default" })} flex-grow`}>
			Export Calendar
		</button>
	</Sidebar.Footer>
</Sidebar.Root>
