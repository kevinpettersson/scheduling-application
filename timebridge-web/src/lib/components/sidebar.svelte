<script lang="ts">
	import * as Tabs from "$lib/components/ui/tabs/index.js";
    import * as Card from "$lib/components/ui/card/index.js";
    import { Input } from "$lib/components/ui/input/index.js";
	import * as Sidebar from '$lib/components/ui/sidebar/index.js';
	import ModeToggle from './mode.svelte';
	import { Button } from '$lib/components/ui/button/index.js';
	import * as Collapsible from '$lib/components/ui/collapsible/index.js';
	import { BadgeEuro, ChevronDown } from 'lucide-svelte';
	import ChevronsUpDown from 'lucide-svelte/icons/chevrons-up-down';
	import { buttonVariants } from '$lib/components/ui/button/index.js';
	import { Checkbox } from '$lib/components/ui/checkbox/index.js';
	import { Label } from '$lib/components/ui/label/index.js';
	import * as Select from '$lib/components/ui/select/index.js';
	import { Badge } from '$lib/components/ui/badge/index.js';
	import { badgeVariants } from '$lib/components/ui/badge/index.js';
	import Separator from './ui/separator/separator.svelte';
	import Filter from './filter-settings.svelte';
	import Format from './format-settings.svelte';

	const courses = [
		{ value: 'dit123', label: 'DIT123' },
		{ value: 'dat456', label: 'DAT456' },
		{ value: 'dit678', label: 'DIT678' },
		{ value: 'dat901', label: 'DAT901' },
		{ value: 'dit234', label: 'DIT234' }
	];

	const activities = [
		{ value: 'lecture', label: 'Lecture' },
		{ value: 'lab', label: 'Lab' },
		{ value: 'seminar', label: 'Seminar' },
		{ value: 'workshop', label: 'Workshop' },
		{ value: 'assignment', label: 'Assignment' }
	];

	const fields = [
		{ value: 'courseCode', label: 'Code' },
		{ value: 'courseName', label: 'Course' },
		{ value: 'activity', label: 'Activity' }
	];

	let cValue = $state([]);
	let aValue = $state([]);
	let rChecked = $state(true);
	let bChecked = $state(true);
	let sValue = $state([]);
	let dValue = $state([]);
</script>

<Sidebar.Root variant="floating">
	<Sidebar.Content class="p-3">
		<Tabs.Root value="filter">
			<Tabs.List class="grid w-full grid-cols-2">
			  <Tabs.Trigger value="filter">Filter</Tabs.Trigger>
			  <Tabs.Trigger value="format">Format</Tabs.Trigger>
			</Tabs.List>
			<Tabs.Content value="filter">
				<Filter /> 
			</Tabs.Content>
			<Tabs.Content value="format">
				<Format />
			</Tabs.Content>
		  </Tabs.Root>
	</Sidebar.Content>
	<Sidebar.Footer class="flex-row items-center">
		<ModeToggle />
		<Separator orientation="vertical" class="min-h-px h-5/6" />
		<Button class="flex-grow">Export Calendar</Button>
	</Sidebar.Footer>
</Sidebar.Root>
