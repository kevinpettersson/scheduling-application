<script lang="ts">
	import { Checkbox } from '$lib/components/ui/checkbox/index.js';
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