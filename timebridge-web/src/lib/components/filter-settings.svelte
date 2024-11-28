<script lang="ts">

	import { Label } from '$lib/components/ui/label/index.js';
	import * as Select from '$lib/components/ui/select/index.js';
	import { Badge } from '$lib/components/ui/badge/index.js';


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