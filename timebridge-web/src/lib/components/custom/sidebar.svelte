<script lang="ts">
	import * as Tabs from '$lib/components/ui/tabs/index.js';
	import * as Sidebar from '$lib/components/ui/sidebar/index.js';
	import ModeToggle from './mode.svelte';
	import { Button, buttonVariants } from '$lib/components/ui/button/index.js';
	import Separator from '$lib/components/ui/separator/separator.svelte';
	import FilterSettings from './filter-settings.svelte';
	import FormatSettings from './format-settings.svelte';
	import { downloadCalendar } from '$lib/api.svelte';
	import ExportDrawer from './export-drawer.svelte';
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
		<ExportDrawer />
	</Sidebar.Footer>
</Sidebar.Root>

<style>
	@import url('https://fonts.googleapis.com/css2?family=Bowlby+One&display=swap');
	.bowlby-one-regular {
		font-family: 'Bowlby One', sans-serif;
		font-weight: 400;
		font-style: normal;
	}
</style>
