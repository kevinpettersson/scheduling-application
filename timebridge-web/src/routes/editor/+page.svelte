<script lang="ts">
	import EventTable from '$lib/components/custom/event-table/event-table.svelte';
	import AddEvent from '$lib/components/custom/event-table/event-add/add-event.svelte';
</script>

<div class="flex h-full flex-auto flex-col gap-2">
	<div class="br-8 h-full overflow-scroll rounded-md border">
		<EventTable />
	</div>
	<AddEvent />
</div>
