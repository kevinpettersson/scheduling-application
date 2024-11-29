<script lang="ts">
	import { Checkbox } from '$lib/components/ui/checkbox/index.js';
	import { Label } from '$lib/components/ui/label/index.js';
	import * as Select from '$lib/components/ui/select/index.js';
	import { Badge } from '$lib/components/ui/badge/index.js';
	import { calendar } from '$lib/store.svelte';

	const fields = [
		{ value: 'code', label: 'Course Code' },
		{ value: 'name', label: 'Course Name' },
		{ value: 'activity', label: 'Activity' }
	];

	let summaryFormat = $state(['code', 'activity']);
	let descFormat = $state(['name']);
	let includeRoom = $state(true);
	let includeBuilding = $state(false);

	let format = $derived({
		summary: summaryFormat,
		description: descFormat,
		location: [...(includeBuilding ? ['building'] : []), ...(includeRoom ? ['room'] : [])]
	});

	$effect(() => {
		calendar.setFormat(format);
	});
</script>

<div class="flex flex-col space-y-2 rounded border border-dashed p-3 pt-2">
	<Select.Root type="multiple" name="selectedCourses" bind:value={summaryFormat}>
		<div>
			<Label for="selectedCourses">Summary</Label>
			<p class="text-xs text-muted-foreground">
				Select fields to include in the event summary, in order.
			</p>
		</div>
		<Select.Trigger>
			<div class="flex gap-1">
				{#if summaryFormat.length > 2}
					{#each summaryFormat.slice(0, Math.min(1, summaryFormat.length)) as v}
						<Badge>{fields.find((c) => c.value === v)?.label}</Badge>
					{/each}
					<Badge variant="outline">{summaryFormat.length - 1} Others</Badge>
				{:else if summaryFormat.length > 0}
					{#each summaryFormat as v}
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
	<Select.Root type="multiple" name="selectedCourses" bind:value={descFormat}>
		<div>
			<Label for="selectedCourses">Description</Label>
			<p class="text-xs text-muted-foreground">
				Select fields to include in the event description, in order.
			</p>
		</div>
		<Select.Trigger>
			<div class="flex gap-1">
				{#if descFormat.length > 2}
					{#each descFormat.slice(0, Math.min(1, descFormat.length)) as v}
						<Badge>{fields.find((c) => c.value === v)?.label}</Badge>
					{/each}
					<Badge variant="outline">{descFormat.length - 1} Others</Badge>
				{:else if descFormat.length > 0}
					{#each descFormat as v}
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
			<Checkbox id="terms" bind:checked={includeBuilding} aria-labelledby="terms-label" />
			<Label
				id="terms-label"
				for="terms"
				class="text-sm font-medium leading-none peer-disabled:cursor-not-allowed peer-disabled:opacity-70"
			>
				Buildings
			</Label>
		</div>
		<div class="flex items-center space-x-2">
			<Checkbox id="terms" bind:checked={includeRoom} aria-labelledby="terms-label" />
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
