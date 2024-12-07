<script lang="ts">
	import { Label } from '$lib/components/ui/label/index.js';
	import * as Select from '$lib/components/ui/select/index.js';
	import { Badge } from '$lib/components/ui/badge/index.js';
	import { calendar } from '$lib/store.svelte';
	import { modifyCalendar } from '$lib/api.svelte';

	let courseFilter = $state([]);
	let activityFilter = $state([]);

	// Watch for changes in the courseFilter and activityFilter
	$inspect(courseFilter, activityFilter).with((type, courseFilter, activityFilter) => {
		console.log(type, courseFilter, activityFilter);
		if (type === 'update') {
			modifyCalendar(courseFilter, activityFilter);
		}
	});
</script>

<div class="flex flex-col space-y-2 rounded border border-dashed p-3 pt-2">
	<div class="flex flex-col space-y-2">
		<Select.Root type="multiple" name="selectedCourses" bind:value={courseFilter}>
			<div>
				<Label for="selectedCourses">Course Code</Label>
				<p class="text-muted-foreground text-xs">Select the course codes to filter events by.</p>
			</div>
			<Select.Trigger>
				<div class="flex gap-1">
					{#if courseFilter.length > 2}
						{#each courseFilter.slice(0, Math.min(1, courseFilter.length)) as v}
							<Badge>{calendar.courseCodes().find((c) => c === v)}</Badge>
						{/each}
						<Badge variant="outline">{courseFilter.length - 1} Others</Badge>
					{:else if courseFilter.length > 0}
						{#each courseFilter as v}
							<Badge>{calendar.courseCodes().find((c) => c === v)}</Badge>
						{/each}
					{:else}
						Select courses
					{/if}
				</div>
			</Select.Trigger>
			<Select.Content>
				<Select.Group>
					<Select.GroupHeading>Course</Select.GroupHeading>
					{#each calendar.courseCodes() as code}
						<Select.Item value={code}>{code}</Select.Item>
					{/each}
				</Select.Group>
			</Select.Content>
		</Select.Root>
		<Select.Root type="multiple" name="selectedActivities" bind:value={activityFilter}>
			<div>
				<Label for="selectedActivities">Activity</Label>
				<p class="text-muted-foreground text-xs">Select the activities to filter events by.</p>
			</div>
			<Select.Trigger>
				<div class="scrollbar-hide flex gap-1 overflow-x-auto">
					{#if activityFilter.length > 2}
						{#each activityFilter.slice(0, Math.min(1, activityFilter.length)) as v}
							<Badge>{calendar.courseActivities().find((c) => c === v)}</Badge>
						{/each}
						<Badge variant="outline">{activityFilter.length - 1} Others</Badge>
					{:else if activityFilter.length > 0}
						{#each activityFilter as v}
							<Badge>{calendar.courseActivities().find((c) => c === v)}</Badge>
						{/each}
					{:else}
						Select activities
					{/if}
				</div>
			</Select.Trigger>
			<Select.Content>
				<Select.Group>
					<Select.GroupHeading>Activity</Select.GroupHeading>
					{#each calendar.courseActivities() as activity}
						<Select.Item value={activity}>{activity}</Select.Item>
					{/each}
				</Select.Group>
			</Select.Content>
		</Select.Root>
	</div>
</div>
