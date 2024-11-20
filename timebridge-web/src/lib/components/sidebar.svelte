<script lang="ts">
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
		<Label class="text-base">Filter Events</Label>
		<div class="flex flex-col space-y-2 rounded border border-dashed p-3 pt-2">
			<div class="flex flex-col space-y-2">
				<Select.Root type="multiple" name="selectedCourses" bind:value={cValue}>
					<div>
						<Label for="selectedCourses">Course Code</Label>
						<p class="text-xs text-muted-foreground">Select the course codes to filter events by.</p>
					</div>
					<Select.Trigger>
						<div class="flex gap-1">
							{#if cValue.length > 2}
								{#each cValue.slice(0, Math.min(1, cValue.length)) as v}
									<Badge>{courses.find((c) => c.value === v)?.label}</Badge>
								{/each}
								<Badge variant="outline">{cValue.length - 1} Others</Badge>
							{:else if cValue.length > 0}
								{#each cValue as v}
									<Badge>{courses.find((c) => c.value === v)?.label}</Badge>
								{/each}
							{:else}
								Select courses
							{/if}
						</div>
					</Select.Trigger>
					<Select.Content>
						<Select.Group>
							<Select.GroupHeading>Course</Select.GroupHeading>
							{#each courses as course}
								<Select.Item value={course.value} label={course.label}>{course.label}</Select.Item>
							{/each}
						</Select.Group>
					</Select.Content>
				</Select.Root>
				<Select.Root type="multiple" name="selectedActivities" bind:value={aValue}>
					<div>
						<Label for="selectedActivities">Activity</Label>
						<p class="text-xs text-muted-foreground">Select the activities to filter events by.</p>
					</div>
					<Select.Trigger>
						<div class="scrollbar-hide flex gap-1 overflow-x-auto">
							{#if aValue.length > 2}
								{#each aValue.slice(0, Math.min(1, aValue.length)) as v}
									<Badge>{activities.find((c) => c.value === v)?.label}</Badge>
								{/each}
								<Badge variant="outline">{aValue.length - 1} Others</Badge>
							{:else if aValue.length > 0}
								{#each aValue as v}
									<Badge>{activities.find((c) => c.value === v)?.label}</Badge>
								{/each}
							{:else}
								Select activities
							{/if}
						</div>
					</Select.Trigger>
					<Select.Content>
						<Select.Group>
							<Select.GroupHeading>Activity</Select.GroupHeading>
							{#each activities as activity}
								<Select.Item value={activity.value} label={activity.label}
									>{activity.label}</Select.Item
								>
							{/each}
						</Select.Group>
					</Select.Content>
				</Select.Root>
			</div>
		</div>
		<Label class="text-base">Event Format</Label>
		<div class="flex flex-col space-y-2 rounded border border-dashed p-3 pt-2">
			<Select.Root type="multiple" name="selectedCourses" bind:value={sValue}>
				<div>
					<Label for="selectedCourses">Summary</Label>
					<p class="text-xs text-muted-foreground">Select fields to include in the event summary, in order.</p>
				</div>
				<Select.Trigger>
					<div class="flex gap-1">
						{#if sValue.length > 2}
							{#each sValue.slice(0, Math.min(1, sValue.length)) as v}
								<Badge>{fields.find((c) => c.value === v)?.label}</Badge>
							{/each}
							<Badge variant="outline">{sValue.length - 1} Others</Badge>
						{:else if sValue.length > 0}
							{#each sValue as v}
								<Badge>{fields.find((c) => c.value === v)?.label}</Badge>
							{/each}
						{:else}
							Select fields
						{/if}
					</div>
				</Select.Trigger>
				<Select.Content>
					<Select.Group>
						<Select.GroupHeading>Field</Select.GroupHeading>
						{#each fields as field}
							<Select.Item value={field.value} label={field.label}>{field.label}</Select.Item>
						{/each}
					</Select.Group>
				</Select.Content>
			</Select.Root>
			<Select.Root type="multiple" name="selectedCourses" bind:value={dValue}>
				<div>
					<Label for="selectedCourses">Description</Label>
					<p class="text-xs text-muted-foreground">Select fields to include in the event description, in order.</p>
				</div>
				<Select.Trigger>
					<div class="flex gap-1">
						{#if dValue.length > 2}
							{#each dValue.slice(0, Math.min(1, dValue.length)) as v}
								<Badge>{fields.find((c) => c.value === v)?.label}</Badge>
							{/each}
							<Badge variant="outline">{dValue.length - 1} Others</Badge>
						{:else if dValue.length > 0}
							{#each dValue as v}
								<Badge>{fields.find((c) => c.value === v)?.label}</Badge>
							{/each}
						{:else}
							Select fields
						{/if}
					</div>
				</Select.Trigger>
				<Select.Content>
					<Select.Group>
						<Select.GroupHeading>Field</Select.GroupHeading>
						{#each fields as field}
							<Select.Item value={field.value} label={field.label}>{field.label}</Select.Item>
						{/each}
					</Select.Group>
				</Select.Content>
			</Select.Root>
			<div>
				<Label for="locationFormat">Location</Label>
				<p class="text-xs text-muted-foreground">Specify details to include in the event location.</p>
			</div>
			<div class="flex flex-col space-y-2">
				<div class="flex items-center space-x-2">
					<Checkbox id="terms" bind:checked={rChecked} aria-labelledby="terms-label" />
					<Label
						id="terms-label"
						for="terms"
						class="text-sm font-medium leading-none peer-disabled:cursor-not-allowed peer-disabled:opacity-70"
					>
						Buildings
					</Label>
				</div>
				<div class="flex items-center space-x-2">
					<Checkbox id="terms" bind:checked={bChecked} aria-labelledby="terms-label" />
					<Label
						id="terms-label"
						for="terms"
						class="text-sm font-medium leading-none peer-disabled:cursor-not-allowed peer-disabled:opacity-70"
					>
						Rooms
					</Label>
				</div>
			</div>
		</div>
	</Sidebar.Content>
	<Sidebar.Footer class="flex-row items-center">
		<ModeToggle />
		<Separator orientation="vertical" class="min-h-px h-5/6" />
		<Button class="flex-grow">Export Calendar</Button>
	</Sidebar.Footer>
</Sidebar.Root>
